package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.BigDecimalUtil;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.TencentCloudReport;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author hqh
 */
@Service
@Slf4j
public class M60R27Calc implements ThirdCalc {

    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        String  M10R05;            //申请人在本产品有7天以上逾期历史
        Integer M30R06;            // 手机通讯录人数
        Integer M50R02;            //腾讯云欺诈分
        String  M50R01_6 =  "N";   //腾讯云账号风险_疑似恶意欺诈
        Integer M70R02 =    0;     //同盾-1个月内多头借贷次数
        Integer M70R03 =    0;     //同盾-3个月内多头借贷次数
        Integer M70R05 =    0;     //同盾-12个月内多头借贷次数
        String  M70R08 =    "N";   // 同盾-身份证命中信贷逾期名单
        Integer M80R02 =    0;     //手机号运营商数据持续月份数
        Integer M80R05 =    0;     //近1个月呼入的电话次数
        Float   M80R06 =    0F;    //近1个月凌晨通话的比例（凌晨1-5点通话次数除以总通话次数）

        Integer   AM10R05;
        Integer   AM30R06;
        Integer   AM50R02;
        Integer   AM50R01_6;
        Integer   AM70R02;
        Integer   AM70R03;
        Integer   AM70R05;
        Integer   AM70R08;
        Double    AM80R02;
        Integer   AM80R05;
        Float     AM80R06;
        Double    M60R27 ;

        Object gonggo = data.get(Constant.GONGGO);
        if (gonggo == null) {
            return;
        }

        try {
            //M10R05
            M10R05 =(String) auditBean.getFieldData().get("M10R05");
            if (null == M10R05 || "N".equals(M10R05)) {
                AM10R05 = 1;
            } else {
                AM10R05 = 2;
            }
            //M30R06

            M30R06 = (Integer)auditBean.getFieldData().get("M30R06");
            if (null == M30R06) {
                AM30R06 = 100;
            } else if (M30R06 >= 1110) {
                AM30R06 = 1110;
            } else {
                AM30R06 = M30R06;
            }
            //M50R02
            TencentCloudReport txy = (TencentCloudReport) data.get(Constant.TENCENT_CLOUD_DATA);
            if (txy == null || txy.getReport() == null) {
                AM50R02 = 50;
                AM50R01_6 = 1;
            } else {
                JSONObject txyJson = txy.getReport();
                Integer riskScore = txyJson.getInteger("riskScore");
                if (riskScore != null) {
                    M50R02 = riskScore;
                } else {
                    M50R02 = null;
                }
                if (null == M50R02) {
                    AM50R02 = 50;
                } else if (M50R02 >= 89) {
                    AM50R02 = 89;
                } else {
                    AM50R02 = M50R02;
                }
                //M50R01_6
                JSONArray riskInfo = txyJson.getJSONArray("riskInfo");
                if (riskInfo != null && riskInfo.size() > 0) {
                    for (int i = 0; i < riskInfo.size(); i++) {
                        JSONObject riskInfoJSON = riskInfo.getJSONObject(i);
                        if (riskInfoJSON.getInteger("riskCode").equals(6)) {
                            M50R01_6 = "Y";
                        }
                    }
                }
                if (null == M50R01_6 || "N".equals(M50R01_6)) {
                    AM50R01_6 = 1;
                } else {
                    AM50R01_6 = 2;
                }
            }


            TongDunReport tongdunReport = (TongDunReport) data.get(Constant.TONGDUN_DATA);
            if (tongdunReport == null || tongdunReport.getReport() == null) {
                AM70R02 = 13;
                AM70R03 = 33;
                AM70R05 = 71;
                AM70R08 = 1;
            } else {
                JSONObject tongdunJson = tongdunReport.getReport();
                JSONArray riskItems = tongdunJson.getJSONArray("risk_items");
                for (int i = 0; (riskItems != null && riskItems.size() > 0 && i < riskItems.size()); i++) {
                    //风险项目
                    JSONObject riskItem = riskItems.getJSONObject(i);
                    if (riskItem == null) {
                        continue;
                    }
                    String riskItemName = riskItem.getString("item_name");
                    JSONObject itemDetail = riskItem.getJSONObject("item_detail");
                    if ("1个月内申请人在多个平台申请借款".equals(riskItemName) && itemDetail != null) {
                        Integer platformCount = itemDetail.getInteger("platform_count");
                        M70R02 = platformCount == null ? 0 : platformCount;
                    }
                    if ("3个月内申请人在多个平台申请借款".equals(riskItemName) && itemDetail != null) {
                        Integer platformCount = itemDetail.getInteger("platform_count");
                        M70R03 = platformCount == null ? 0 : platformCount;
                    }
                    if ("12个月内申请人在多个平台申请借款".equals(riskItemName) && itemDetail != null) {
                        Integer platformCount = itemDetail.getInteger("platform_count");
                        M70R05 = platformCount == null ? 0 : platformCount;
                    }
                    if ("身份证命中信贷逾期名单".equals(riskItemName)) {
                        M70R08 = "Y";
                    }

                }
                //M70R02
                if (null == M70R02) {
                    AM70R02 = 13;
                } else if (M70R02 >= 46) {
                    AM70R02 = 46;
                } else {
                    AM70R02 = M70R02;
                }
                //M70R03
                if (null == M70R03) {
                    AM70R03 = 33;
                } else if (M70R03 >= 112) {
                    AM70R03 = 112;
                } else {
                    AM70R03 = M70R03;
                }
                if (null == M70R05) {
                    AM70R05 = 71;
                } else if (M70R05 >= 201) {
                    AM70R05 = 201;
                } else {
                    AM70R05 = M70R05;
                }
                //M70R08
                if (null == M70R08 || "N".equals(M70R08)) {
                    AM70R08 = 1;
                } else {
                    AM70R08 = 2;
                }
            }

            //运营商报表
            MobileOperatorBill yunyingshangData = (MobileOperatorBill) data.get(Constant.MOBILE_OPERATOR_BILL);
            if (yunyingshangData == null) {
                AM80R02 = 53.5;
                AM80R05 = 113;
                AM80R06 = 0.1015f;
            } else {
                JSONObject report = yunyingshangData.getReport();
                JSONArray behaviorCheck = report.getJSONArray("behavior_check");
                JSONArray cellBehavior = report.getJSONArray("cell_behavior");
                JSONArray behavior = cellBehavior.getJSONObject(0).getJSONArray("behavior");
                JSONObject jsonObject = behavior.getJSONObject(1);
                for (int i = 0; i < behaviorCheck.size(); i++) {
                    String checkPoint = behaviorCheck.getJSONObject(i).getString("check_point");
                    if ("contact_night".equals(checkPoint)) {
                        JSONObject jsonObject1 = behaviorCheck.getJSONObject(i);
                        String evidence = jsonObject1.getString("evidence");
                        if (evidence != null) {
                            int index = evidence.indexOf("的");
                            String v = evidence.substring(index + 1);
                            if ("相关记录".equals(v)) {
                                M80R06 = 0F;
                            } else {
                                M80R06 = new Float(v.substring(0, v.length() - 1)) / 100;
                            }
                        }
                    }
                    if ("phone_used_time".equals(checkPoint)) {
                        //手机号运营商数据持续月份数
                        JSONObject jsonObject2 = behaviorCheck.getJSONObject(i);
                        String evidence1 = jsonObject2.getString("evidence");
                        if (evidence1 != null) {
                            int index1 = evidence1.indexOf("用");
                            String substring = evidence1.substring(index1 + 1, evidence1.length() - 2);
                            M80R02 = Integer.valueOf(substring);
                        }
                    }
                }
                Integer callInCnt = jsonObject.getInteger("call_in_cnt");
                if (callInCnt != null) {
                    M80R05 = callInCnt;
                }
                //M80R02
                if (null == M80R02) {
                    AM80R02 = 53.5;
                } else if (M80R02 >= 178) {
                    AM80R02 = 178d;
                } else {
                    AM80R02 = M80R02.doubleValue();
                }
                if (null == M80R05) {
                    AM80R05 = 113;
                } else if (M80R05 >= 616) {
                    AM80R05 = 616;
                } else {
                    AM80R05 = M80R05;
                }
                if (null == M80R06) {
                    AM80R06 = 0.1015f;
                } else if (M80R06 >= 616) {
                    AM80R06 = 0.3563f;
                } else {
                    AM80R06 = M80R06;
                }
            }

            Double xbeta = -5.5594770204664200 + 1.0049196224356100 * AM10R05 + (-0.0004213194459662) * AM30R06
                    + 0.0158126355695843 * AM50R02 + 0.5043341047242700 * AM50R01_6 + (-0.0667508727326000) * AM70R02
                    + (-0.0362355057679689) * AM70R03 + 0.0264807664291184 * AM70R05 + 1.8035520773754500 * AM70R08
                    + (-0.0024757756816344) * AM80R02 + (-0.0019083005146639) * AM80R05 + 2.7262290117794900 * AM80R06;
            M60R27 = Math.exp(xbeta) / (1 + Math.exp(xbeta));
            M60R27 = BigDecimalUtil.round(M60R27, 4);
            log.info("【-----M60R27计算完成-----】" + M60R27);
            fieldValueMap.put(IndicatorConfig.M60R27, M60R27);
        } catch (Exception e) {
            log.error("-----计算M60R27出错-----", e);
        }
    }
}


