package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.wjl.commom.util.DictConfig;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

/**
 * 同盾指标配置
 * @author hqh
 */
@Component
@Log4j
@Data
public class TongdunCalc implements ThirdCalc {


    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {

        String M70R11 = "N";                     //同盾-身份证命中法院失信名单
        Long   M70R01 = 0L;                      //同盾-7天内多头借贷次数
        Long   M70R02 = 0L;                      //同盾-1个月内多头借贷次数
        Long   M70R06 = null;                    //同盾-同盾得分（不能有默认值）
        Long   M70R03 = 0L;                      //同盾-3个月内多头借贷次数
        Long   M70R04 = 0L;                      //同盾-6个月内多头借贷次数
        Long   M70R05 = 0L;                      //同盾-12个月内多头借贷次数
        String M70R08 = "N";                     // 同盾-身份证命中信贷逾期名单
        String M70R07 = "N";                     // 同盾-手机号命中信贷逾期名单
        String M70R09 = "N";                     // 同盾-命中手机号逾期名单（紧急联系人1手机号）
        String M70R10 = "N";                     // 同盾-命中手机号逾期名单（紧急联系人2手机号）


        Long start = System.currentTimeMillis();
        TongDunReport tongdunReport = (TongDunReport) data.get(Constant.TONGDUN_DATA);
        if (tongdunReport == null || tongdunReport.getReport() == null) {
            return;
        } else {
            JSONObject tongdunJson = tongdunReport.getReport();

            JSONArray riskItems = tongdunJson.getJSONArray("risk_items");
            for (int i = 0; (riskItems != null && riskItems.size() > 0 && i < riskItems.size()); i++) {
                JSONObject riskItem = riskItems.getJSONObject(i);
                if (riskItem == null) {
                    continue;
                }
                String riskItemName = riskItem.getString("item_name");
                JSONObject itemDetail = riskItem.getJSONObject("item_detail");
                if (DictConfig.Tongdun_Broken_Items.contains(riskItemName)) {
                    M70R11 = "Y";
                }

                //M70R04 同盾-6个月内多头借贷次数
                if ("6个月内申请人在多个平台申请借款".equals(riskItemName) && itemDetail != null) {
                    Integer platformCount = itemDetail.getInteger("platform_count");
                    M70R04 = platformCount == null ? 0 : platformCount.longValue();
                }
                //M70R05  同盾--12个月内多头借贷次数
                if ("12个月内申请人在多个平台申请借款".equals(riskItemName) && itemDetail != null) {
                    Integer platformCount = itemDetail.getInteger("platform_count");
                    M70R05 = platformCount == null ? 0 : platformCount.longValue();
                }

                if ("身份证命中信贷逾期名单".equals(riskItemName)) {
                    M70R08 = "Y";
                }
                if ("手机号命中信贷逾期名单".equals(riskItemName)) {
                    M70R07 = "Y";
                }
                if ("第一联系人手机号命中信贷逾期名单".equals(riskItemName)) {
                    M70R09 = "Y";
                }
                if ("第二联系人手机号命中信贷逾期名单".equals(riskItemName)) {
                    M70R10 = "Y";
                }
            }
        }
        fieldValueMap.put(IndicatorConfig.M70R11, M70R11);    //同盾-身份证命中法院失信名单(Y/N)
        fieldValueMap.put(IndicatorConfig.M70R01, M70R01);    //同盾-7天内多头借贷次数
        fieldValueMap.put(IndicatorConfig.M70R02, M70R02);    //同盾-1个月内多头借贷次数
        fieldValueMap.put(IndicatorConfig.M70R06, M70R06);    //同盾-同盾得分
        fieldValueMap.put(IndicatorConfig.M70R08, M70R08);    //同盾-身份证命中信贷逾期名单
        fieldValueMap.put(IndicatorConfig.M70R03, M70R03);    //同盾-3个月内多头借贷次数
        fieldValueMap.put(IndicatorConfig.M70R04, M70R04);    //同盾-6个月内多头借贷次数
        fieldValueMap.put(IndicatorConfig.M70R05, M70R05);    //同盾-12个月内多头借贷次数
        fieldValueMap.put(IndicatorConfig.M70R07, M70R07);    //同盾-手机号命中信贷逾期名单
        fieldValueMap.put(IndicatorConfig.M70R09, M70R09);    //同盾-手机号命中信贷逾期名单
        fieldValueMap.put(IndicatorConfig.M70R10, M70R10);    //同盾-手机号命中信贷逾期名单


        log.info(String.format("【------同盾指标计算完成---------】creditId=%s, use=%s", auditBean.getCreditId(), System.currentTimeMillis() - start));
    }


}
