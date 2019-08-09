package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.TencentCloudReport;
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
public class TencntCloudCalc implements ThirdCalc {

    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        //腾讯云账号风险_信贷中介
        String M50R01_1 = "N";
        //腾讯云账号风险_不法分子
        String M50R01_2 = "N";
        //腾讯云账号风险_虚假资料
        String M50R01_3 = "N";
        //腾讯云账号风险_羊毛党
        String M50R01_4 = "N";
        //腾讯云账号风险_身份认证失败
        String M50R01_5 = "N";
        //腾讯云账号风险_疑似恶意欺诈
        String M50R01_6 = "N";
        //腾讯云账号风险_失信名单
        String M50R01_7 = "N";
        //腾讯云账号风险_异常支付行为
        String M50R01_8 = "N";
        //腾讯云异常环境_恶意环境
        String M50R01_9 = "N";
        //腾讯云异常环境_其他行为异常
        String M50R01_10 = "N";
        //腾讯云欺诈分
        Long   M50R02 = 0L;

        Long start = System.currentTimeMillis();
        TencentCloudReport txy =(TencentCloudReport)data.get(Constant.TENCENT_CLOUD_DATA);
        if(txy==null || txy.getReport()==null){
            return;
        }else {
            try {
                JSONObject txyJson = txy.getReport();
                Long riskScore = txyJson.getLong("riskScore");
                if (riskScore != null) {
                    M50R02 = riskScore;
                } else {
                    M50R02 = null;
                }
            }
                catch(Exception e){
                    log.error(String.format("【计算腾讯云指标-获取腾讯云欺诈分异常】：%s", e.getMessage()), e);
                }

             try{
                 JSONObject txyJson = txy.getReport();
                 JSONArray riskInfo = txyJson.getJSONArray("riskInfo");
                if(riskInfo != null && riskInfo.size()>0){
                    for(int i=0;i<riskInfo.size();i++){
                        JSONObject riskInfoJSON = riskInfo.getJSONObject(i);
                        if(riskInfoJSON.getInteger("riskCode").equals(1)){
                            M50R01_1 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(2)){
                            M50R01_2 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(3)){
                            M50R01_3 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(4)){
                            M50R01_4 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(5)){
                            M50R01_5 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(6)){
                            M50R01_6 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(7)){
                            M50R01_7 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(8)){
                            M50R01_8 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(301)){
                            M50R01_9 = "Y";
                        }else if(riskInfoJSON.getInteger("riskCode").equals(503)){
                            M50R01_10 = "Y";
                        }
                    }
                }
             }catch(Exception e){
                log.error(String.format("【计算腾讯云指标-获取腾讯云风险信息异常】：%s", e.getMessage()), e);
            }
            fieldValueMap.put(IndicatorConfig.M50R02,M50R02);
            fieldValueMap.put(IndicatorConfig.M50R01_2,M50R01_2);
            fieldValueMap.put(IndicatorConfig.M50R01_1,M50R01_1);
            fieldValueMap.put(IndicatorConfig.M50R01_3,M50R01_3);
            fieldValueMap.put(IndicatorConfig.M50R01_4,M50R01_4);
            fieldValueMap.put(IndicatorConfig.M50R01_5,M50R01_5);
            fieldValueMap.put(IndicatorConfig.M50R01_6,M50R01_6);
            fieldValueMap.put(IndicatorConfig.M50R01_7,M50R01_7);
            fieldValueMap.put(IndicatorConfig.M50R01_8,M50R01_8);
            fieldValueMap.put(IndicatorConfig.M50R01_9,M50R01_9);
            fieldValueMap.put(IndicatorConfig.M50R01_10,M50R01_10);

            log.info(String.format("【------腾讯云指标计算完成---------】creditId=%s, use=%s", auditBean.getCreditId(), System.currentTimeMillis() - start));
        }
    }
}
