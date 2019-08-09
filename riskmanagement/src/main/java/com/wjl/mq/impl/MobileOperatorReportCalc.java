package com.wjl.mq.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.IndicatorConfig;
import com.wjl.mapper.OverDueReceivableCallsMapper;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @author hqh
 */
@Slf4j
@Component
public class MobileOperatorReportCalc implements ThirdCalc {

    @Autowired
    OverDueReceivableCallsMapper overDueReceivableCallsMapper;

    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        String M40R03 = "N";
        Long   M30R05 = 0L;

        MobileOperatorReport yunyingshangRawData = (MobileOperatorReport) data.get(Constant.MOBILE_OPERATOR_REPORT);
        if (yunyingshangRawData == null || yunyingshangRawData.getReport() == null) {
            return;
        } else {
            Long start = System.currentTimeMillis();
            JSONObject yysRaw = yunyingshangRawData.getReport();
            JSONArray transactions = yysRaw.getJSONArray("transactions");
            for (int i = 0; i < transactions.size(); i++) {
                JSONObject transaction = transactions.getJSONObject(i);
                JSONArray calls = transaction.getJSONArray("calls");
                for (int a = 0; a < calls.size(); a++) {
                    JSONObject call = calls.getJSONObject(a);
                    if (call.getString("init_type") != null && call.getString("init_type").contains("被叫")) {
                        String otherCellPhone = call.getString("other_cell_phone");
                        Integer count = overDueReceivableCallsMapper.getCount(otherCellPhone);
                        if (count != 0) {
                            M40R03 = "Y";
                            log.info(String.format("【------击中催收电话库---------】creditId=%s,number=%s", auditBean.getCreditId(), otherCellPhone));
                            break;
                        }
                    }
                }
                //M30R05指标：近一个月疑似催收电话的通话次数
                for (int a = 0; a < calls.size(); a++) {
                    JSONObject call = calls.getJSONObject(a);
                    if (call.getString("init_type") != null) {
                        Date startDate = call.getDate("start_time");
                        if (startDate != null) {
                            Long startTime = startDate.getTime();
                            if ((start - 86400L * 30L * 1000L) < startTime) {
                                String otherCellPhone = call.getString("other_cell_phone");
                                Integer count = overDueReceivableCallsMapper.getCount(otherCellPhone);
                                if (count != 0) {
                                    M30R05++;
                                }
                            }
                        }
                    }
                }
            }
            fieldValueMap.put(IndicatorConfig.M40R03, M40R03);
            fieldValueMap.put(IndicatorConfig.M30R05, M30R05);
            log.info(String.format("【------运营商原始指标计算完成---------】creditId=%s, use=%s", auditBean.getCreditId(), System.currentTimeMillis() - start));
        }
    }
}
