package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.BigDecimalUtil;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.DataModel;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import com.wjl.service.DataModelService;
import edu.princeton.cs.algs4.StdStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author hqh
 */
@Slf4j
@Component
public class Sa001Calc  implements ThirdCalc {

    @Autowired
    private TreenetScore treenetScore;

    @Autowired
    private DataModelService dataModelService;

    private Float a = 472.56F;
    private Float b = 43.28F;

    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        try {

            Double  Sa001;
            Integer dmTelNouseBasedays = 0;
            Integer phone2houseplace = 0;
            Double  callInTimeStd;
            Float   eatchCallRate = 0F;
            Integer ypCnt;
            Integer phoneUseTime = 30;

            Long start = System.currentTimeMillis();

            //运营商报表
            MobileOperatorBill yunyingshangData = (MobileOperatorBill) data.get(Constant.MOBILE_OPERATOR_BILL);
            if (yunyingshangData == null) {
                return;             //数据为空不计算指标
            }

            JSONObject report = yunyingshangData.getReport();
            JSONArray behaviorCheck = report.getJSONArray("behavior_check");

            /*
             * call_in_cnt_rsd,被叫次数极差
             */
            JSONObject report1 = report.getJSONObject("report");
            String updateTime = report1.getString("update_time");
            //report生成时间（yyyy-MM）
            String time1 = updateTime.substring(0, 7);
            //report生成的当月日期
            int time2 = Integer.valueOf(updateTime.substring(8, 10));

            JSONArray cellBehavior = report.getJSONArray("cell_behavior");
            JSONObject jsonObject = cellBehavior.getJSONObject(0);
            JSONArray behavior = jsonObject.getJSONArray("behavior");
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < behavior.size(); i++) {
                String behaviorTime = behavior.getJSONObject(i).getString("cell_mth");
                Integer callInCnt = behavior.getJSONObject(i).getInteger("call_in_cnt");
                if (time1.equals(behaviorTime) && time2 < 30) {
                    Double a = Double.valueOf(callInCnt) / time2 * 30;
                    callInCnt = a.intValue();
                }
                list.add(callInCnt);
            }

            Collections.sort(list);
            //call_in_cnt_min
            int callInCntMin = list.get(0);
            //call_in_cnt_max
            int callInCntMax = list.get(behavior.size() - 1);

            //call_in_cnt_rsd
            Integer callInCntRsd = callInCntMax - callInCntMin;

            log.info("----------call_in_cnt_rsd----------:" + callInCntRsd);

            /*
             * 取contact_num字段
             * 根据borrow_apply中的user_id连接contacts_list查询找到count(1)
             */
            Integer contactNum =auditBean.getContactNum();

            /*
             * 取dm_tel_nouse_basedays字段
             * 根据运营商当中behavior_check中的result去截取"164天内有2天无通话记"拿到2
             * 根据拿到的值判断非空 dm_tel_nouse_basedays字段为1，为0则为0，为空则待定
             */
            for (int i = 0; i < behaviorCheck.size(); i++) {
                String checkPoint = behaviorCheck.getJSONObject(i).getString("check_point");
                if ("phone_silent".equals(checkPoint)) {
                    if ("手机关机时间从未超过1天".equals(behaviorCheck.getJSONObject(i).getString("result"))) {
                        dmTelNouseBasedays = 0;
                    } else {
                        dmTelNouseBasedays = 1;
                    }
                }
            }



            /*
             * phone2houseplace
             * 手机号归属地与居住地匹配程度
             */
            TongDunReport tongdunReport = (TongDunReport) data.get(Constant.TONGDUN_DATA);
            String phoneProvince = null;
            String phoneCity = null;
            //获取手机号归属地
            if (null != tongdunReport) {
                JSONObject reportTD = tongdunReport.getReport();
                JSONObject addressDetect = reportTD.getJSONObject("address_detect");
                String mobileAddress = addressDetect.getString("mobile_address");
                if (null != mobileAddress) {
                    //为省份
                    if (mobileAddress.indexOf('省') != -1) {
                        phoneProvince = mobileAddress.substring(0, mobileAddress.indexOf('省'));
                        phoneCity = mobileAddress.substring(mobileAddress.indexOf('省') + 1, mobileAddress.length());
                    }
                    //为直辖市
                    if (mobileAddress.equals("北京市") || mobileAddress.equals("上海市") || mobileAddress.equals("天津市") || mobileAddress.equals("重庆市")) {
                        phoneProvince = mobileAddress.substring(0, mobileAddress.indexOf('市'));
                        phoneCity = phoneProvince;
                    }
                    //为自治区
                    if (mobileAddress.contains("自治区")) {
                        phoneProvince = mobileAddress.substring(0, mobileAddress.indexOf("自治区"));
                        phoneCity = mobileAddress.substring(mobileAddress.indexOf("自治区") + 3, mobileAddress.length());
                    }
                }
            }
            //获取居住地

            String province = auditBean.getProvince();
            if (null != province) {
                //为省份
                if (province.indexOf('省') != -1) {
                    province = province.substring(0, province.indexOf('省'));
                }
                //为直辖市
                if (province.indexOf('市') != -1) {
                    province = province.substring(0, province.indexOf('市'));
                }
                //为自治区
                if (province.contains("自治区")) {
                    province = province.substring(0, province.indexOf("自治区"));
                }
            }
            String city = auditBean.getCity();
            if (null != city) {
                if (city.equals("省直辖县级行政区划")||city.equals("自治区直辖县级行政区划")) {
                    city = auditBean.getDistrict();
                }
            }

            //0：省不匹配 1：省匹配，市不匹配 2：省匹配，市匹配 null：其中一个地址为空
            if (null != phoneProvince && null != phoneCity && null != province && null != city) {
                if (!phoneProvince.equals(province)) {
                    phone2houseplace = 0;
                }
                if (phoneProvince.equals(province) && !phoneCity.equals(city)) {
                    phone2houseplace = 1;
                }
                if (phoneProvince.equals(province) && phoneCity.equals(city)) {
                    phone2houseplace = 2;
                }
            }


            /*
             * 计算被叫时长标准差
             * @param report
             * @return
             */

            List<Double> callInTimeList = new ArrayList<>();

            for (int i = 0; i < behavior.size(); i++) {
                //如果不满30天，则按30天计算
                double behaviorCallInTime = Double.valueOf(behavior.getJSONObject(i).getString("call_in_time"));
                String behaviorTime = behavior.getJSONObject(i).getString("cell_mth");
                if (time1.equals(behaviorTime) && time2 < 30) {
                    behaviorCallInTime = behaviorCallInTime / time2 * 30;
                }
                callInTimeList.add(behaviorCallInTime);
            }
            if (callInTimeList.size() < 2) {
                callInTimeStd = 0.0;
            } else {
                double[] array = new double[callInTimeList.size()];
                for (int i = 0; i < callInTimeList.size(); i++) {
                    array[i] = callInTimeList.get(i);
                }
                BigDecimal b = new BigDecimal(StdStats.stddev(array));
                callInTimeStd = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }


            /*
             * yp_cnt
             */
            ypCnt = auditBean.getYpCnt();

            /*
             * eatch_Call_Rate
             * phone_user_time
             */
            for (int i = 0; i < behaviorCheck.size(); i++) {
                String evidence2 = behaviorCheck.getJSONObject(i).getString("evidence");
                if (evidence2.contains("互通过电话的号码")) {
                    int wei = evidence2.indexOf("为");
                    int baifen = evidence2.indexOf("%");
                    eatchCallRate = Float.valueOf(evidence2.substring(wei + 1, baifen)) / 100;
                }

                if (evidence2.contains("推算该号码使用")) {
                    int wei = evidence2.indexOf("用");
                    int baifen = evidence2.indexOf("个");
                    phoneUseTime = Integer.valueOf(evidence2.substring(wei + 1, baifen));
                }

            }
            Sa001 = calcFinalScore(eatchCallRate, ypCnt, phoneUseTime, contactNum, callInCntRsd, dmTelNouseBasedays, callInTimeStd, phone2houseplace);
            DataModel dataModel = new DataModel();
            dataModel.setAddTime(new Date());
            dataModel.setEatchCallRate(eatchCallRate);
            dataModel.setYpCnt(ypCnt);
            dataModel.setPhoneUseTime(phoneUseTime);
            dataModel.setContactNum(contactNum);
            dataModel.setCallInCntRsd(callInCntRsd);
            dataModel.setDmTelNouseBasedays(dmTelNouseBasedays);
            dataModel.setCallInTimeStd(callInTimeStd);
            dataModel.setPhone2houseplace(phone2houseplace);
            dataModel.setCreditId(auditBean.getCreditId());
            dataModel.setUserId(auditBean.getUserId());
            dataModel.setScore(Sa001);
            dataModel.setIdentification(auditBean.getIdentification());
            dataModelService.save(dataModel);
            //申请评分卡模型
            fieldValueMap.put(IndicatorConfig.SA001, Sa001);
            log.info(String.format("-----Score指标计算完成-----userCredit=%s, use=%s", auditBean.getCreditId(), System.currentTimeMillis() - start));

        }catch (Exception e){
            log.error("Score计算异常",e);
        }
    }

    /**
     * 计算最终得分
     */
    private Double calcFinalScore(Float eatchCallRate,Integer ypCnt,Integer phoneUserTime,Integer contactNum,Integer callInCntRsd,Integer dmTelNouseBasedays,Double callInTimeStd,Integer phone2houseplace){
        double probability = treenetScore.getProbability(eatchCallRate, ypCnt, phoneUserTime, contactNum, callInCntRsd, dmTelNouseBasedays, callInTimeStd, phone2houseplace);

        double log = Math.log(probability / (1 - probability));
        double score = a - (b * log);
        return Math.max(300, Math.min(850, score));
    }

}
