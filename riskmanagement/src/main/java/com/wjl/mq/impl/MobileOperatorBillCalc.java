package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hqh
 */
@Component
@Log4j
public class MobileOperatorBillCalc implements ThirdCalc {
    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        //近1个月总电话通话次数
        Long M80R01 = 0L;
        //近1个月呼出的电话次数
        Long M80R04 = 0L;
        //近1个月呼入的电话次数
        Long M80R05 = 0L;
        //近1个月晚上23-凌晨6点的短信+电话+流量总计/全部的短信+电话+流量总计
        Float M80R06 = 0F;
        //手机号运营商数据持续月份数
        Long M80R02 = 0L;
        //近6个月正常使用的月数（每月有3次以上拨出电话or短信视为正常）
        Long M80R03 = 0L;


        Long start = System.currentTimeMillis();
        MobileOperatorBill yunyingshangData = (MobileOperatorBill)data.get(Constant.MOBILE_OPERATOR_BILL);
        if (yunyingshangData==null || yunyingshangData.getReport()==null){
            return;
        }
        JSONObject report = yunyingshangData.getReport();
        //获取运营商数据整理（cell_behavior）
        JSONArray cellBehavior = report.getJSONArray("cell_behavior");
        JSONArray behavior = cellBehavior.getJSONObject(0).getJSONArray("behavior");

        try {
            //近1个月总电话通话次数
            JSONObject jsonObject = behavior.getJSONObject(1);
            Integer callCnt = jsonObject.getInteger("call_cnt");
            if (callCnt != null) {
                M80R01 = Long.valueOf(callCnt);
            }

            //近1个月呼出的电话次数
            Integer callOutCnt = jsonObject.getInteger("call_out_cnt");
            if (callOutCnt != null) {
                M80R04 = Long.valueOf(callOutCnt);
            }

            //近1个月呼入的电话次数
            Integer callInCnt = jsonObject.getInteger("call_in_cnt");
            if (callInCnt != null) {
                M80R05 = Long.valueOf(callInCnt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            //近1个月凌晨通话的比例（23:00-6:00点通话次数除以总通话次数）
            JSONArray behaviorCheck = report.getJSONArray("behavior_check");
            for(int i=0;i<behaviorCheck.size();i++){
                String checkPoint = behaviorCheck.getJSONObject(i).getString("check_point");
                if("contact_night".equals(checkPoint)){
                    JSONObject jsonObject1 = behaviorCheck.getJSONObject(i);
                    String evidence = jsonObject1.getString("evidence");
                    if (evidence != null) {
                        int index = evidence.indexOf("的");
                        String v = evidence.substring(index + 1);
                        if("相关记录".equals(v)){
                            M80R06 = 0F;
                        }else {
                            M80R06 = new Float(v.substring(0, v.length() - 1)) / 100;
                        }
                    }
                }
                if("phone_used_time".equals(checkPoint)){
                    //手机号运营商数据持续月份数
                    JSONObject jsonObject2 = behaviorCheck.getJSONObject(i);
                    String evidence1 = jsonObject2.getString("evidence");
                    if(evidence1!=null) {
                        int index1 = evidence1.indexOf("用");
                        String substring = evidence1.substring(index1 + 1,evidence1.length()-2);
                        M80R02 = Long.valueOf(substring);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            //统计近6个月正常使用的月数（每月有3次以上拨出电话or短信视为正常）
            int count = 0;
            for (int i = 0; i < 6; i++) {
                JSONObject jsonObject2 = behavior.getJSONObject(i);
                if (jsonObject2.getInteger("call_out_cnt") > 3 || jsonObject2.getInteger("sms_cnt") > 3) {
                    count++;
                }
                M80R03 = Long.valueOf(count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        fieldValueMap.put(IndicatorConfig.M80R01,M80R01);
        fieldValueMap.put(IndicatorConfig.M80R04,M80R04);
        fieldValueMap.put(IndicatorConfig.M80R05,M80R05);
        fieldValueMap.put(IndicatorConfig.M80R06,M80R06);
        fieldValueMap.put(IndicatorConfig.M80R02,M80R02);
        fieldValueMap.put(IndicatorConfig.M80R03,M80R03);

        log.info(String.format("【------运营商指标计算完成---------】userCredit=%s, use=%s", auditBean.getCreditId(), System.currentTimeMillis()- start));
    }
}
