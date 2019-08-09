package com.wjl.service.impl;

import com.wjl.commom.util.IndicatorConfig;
import com.wjl.mapper.RiskLogMapper;
import com.wjl.model.RiskLog;
import com.wjl.model.mq.AuditBean;
import com.wjl.service.RiskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/10
 */
@Service
@Slf4j
public class RiskLogServiceImpl implements RiskLogService {
    @Autowired
    private RiskLogMapper riskLogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRiskLog(Map<String, Object> fieldValueMap, AuditBean auditBean) {

        Long userId = auditBean.getUserId();
        Long userCreditId = auditBean.getCreditId();
        String identification = auditBean.getIdentification();
        Integer M10R01 = (Integer) fieldValueMap.get(IndicatorConfig.M10R01);
        String M10R03 = (String) fieldValueMap.get(IndicatorConfig.M10R03);
        String M10R08 = (String) fieldValueMap.get(IndicatorConfig.M10R08);
        String M10R05 = (String) fieldValueMap.get(IndicatorConfig.M10R05);
        String M10R06 = (String) fieldValueMap.get(IndicatorConfig.M10R06);
        String M10R07 = (String) fieldValueMap.get(IndicatorConfig.M10R07);
        Integer M20R02 = (Integer) fieldValueMap.get(IndicatorConfig.M20R02);
        Integer M20R03 = (Integer) fieldValueMap.get(IndicatorConfig.M20R03);
        Integer M20R04 = (Integer) fieldValueMap.get(IndicatorConfig.M20R04);
        Integer M20R05 = (Integer) fieldValueMap.get(IndicatorConfig.M20R05);
        Integer M20R06 = (Integer) fieldValueMap.get(IndicatorConfig.M20R06);
        String M30R01 = (String) fieldValueMap.get(IndicatorConfig.M30R01);
        String M30R02 = (String) fieldValueMap.get(IndicatorConfig.M30R02);
        String M30R03 = (String) fieldValueMap.get(IndicatorConfig.M30R03);
        Long M30R05 = (Long) fieldValueMap.get(IndicatorConfig.M30R05);
        Integer M30R06 = (Integer) fieldValueMap.get(IndicatorConfig.M30R06);
        BigDecimal AM30R07 = (BigDecimal) fieldValueMap.get(IndicatorConfig.M30R07);
        Double M30R07 = new BigDecimal(AM30R07.toString()).doubleValue();
        Integer M30R08 = (Integer) fieldValueMap.get(IndicatorConfig.M30R08);
        String M40R01 = (String) fieldValueMap.get(IndicatorConfig.M40R01);
        String M40R03 = (String) fieldValueMap.get(IndicatorConfig.M40R03);
        //此处为分期开始，风控为获取全部数据
        String M50R01_1 = (String) fieldValueMap.get(IndicatorConfig.M50R01_1);
        String M50R01_2 = (String) fieldValueMap.get(IndicatorConfig.M50R01_2);
        String M50R01_3 = (String) fieldValueMap.get(IndicatorConfig.M50R01_3);
        String M50R01_4 = (String) fieldValueMap.get(IndicatorConfig.M50R01_4);
        String M50R01_5 = (String) fieldValueMap.get(IndicatorConfig.M50R01_5);
        String M50R01_6 = (String) fieldValueMap.get(IndicatorConfig.M50R01_6);
        String M50R01_7 = (String) fieldValueMap.get(IndicatorConfig.M50R01_7);
        String M50R01_8 = (String) fieldValueMap.get(IndicatorConfig.M50R01_8);
        String M50R01_9 = (String) fieldValueMap.get(IndicatorConfig.M50R01_9);
        String M50R01_10 = (String) fieldValueMap.get(IndicatorConfig.M50R01_10);
        String M50R01 = "Y";
        if(M50R01_1.equals("N") && M50R01_2.equals("N") && M50R01_3.equals("N") && M50R01_4.equals("N") && M50R01_5.equals("N") && M50R01_6.equals("N") && M50R01_7.equals("N") && M50R01_8.equals("N") && M50R01_9.equals("N") && M50R01_10.equals("N")){
            M50R01 = "N";
        }
        Long M50R02 = (Long) fieldValueMap.get(IndicatorConfig.M50R02);
        Double M60R27 = (Double) fieldValueMap.get(IndicatorConfig.M60R27);
        Long M70R01 = (Long) fieldValueMap.get(IndicatorConfig.M70R01);

        Long M70R03 = (Long) fieldValueMap.get(IndicatorConfig.M70R03);

        String M70R07 = (String) fieldValueMap.get(IndicatorConfig.M70R07);
        String M70R08 = (String) fieldValueMap.get(IndicatorConfig.M70R08);
        String M70R09 = (String) fieldValueMap.get(IndicatorConfig.M70R09);
        String M70R10 = (String) fieldValueMap.get(IndicatorConfig.M70R10);
        String M70R11 = (String) fieldValueMap.get(IndicatorConfig.M70R11);
        Long M80R01 = (Long) fieldValueMap.get(IndicatorConfig.M80R01);
        Long M80R02 = (Long) fieldValueMap.get(IndicatorConfig.M80R02);
        Long M80R03 = (Long) fieldValueMap.get(IndicatorConfig.M80R03);
        Long M80R04 = (Long) fieldValueMap.get(IndicatorConfig.M80R04);
        Long M80R05 = (Long) fieldValueMap.get(IndicatorConfig.M80R05);
        Float M80R06 = (Float) fieldValueMap.get(IndicatorConfig.M80R06);
        Double  Sa001 = (Double) fieldValueMap.get(IndicatorConfig.SA001);
        RiskLog riskLog = new RiskLog();
        riskLog.setUserId(userId);
        riskLog.setCreditId(userCreditId);
        riskLog.setIdentification(identification);
        riskLog.setM10R01(M10R01);
        riskLog.setM10R03(M10R03);
        riskLog.setM10R08(M10R08);
        riskLog.setM10R05(M10R05);
        riskLog.setM10R06(M10R06);
        riskLog.setM10R07(M10R07);
        riskLog.setM20R02(M20R02);
        riskLog.setM20R03(M20R03);
        riskLog.setM20R04(M20R04);
        riskLog.setM20R05(M20R05);
        riskLog.setM20R06(M20R06);
        riskLog.setM30R01(M30R01);
        riskLog.setM30R02(M30R02);
        riskLog.setM30R03(M30R03);
        riskLog.setM30R05(M30R05);
        riskLog.setM30R06(M30R06);
        riskLog.setM30R07(M30R07);
        riskLog.setM30R08(M30R08);
        riskLog.setM40R01(M40R01);
        riskLog.setM40R03(M40R03);
        riskLog.setM50R01(M50R01);
        riskLog.setM50R01_1(M50R01_1);
        riskLog.setM50R01_2(M50R01_2);
        riskLog.setM50R01_3(M50R01_3);
        riskLog.setM50R01_4(M50R01_4);
        riskLog.setM50R01_5(M50R01_5);
        riskLog.setM50R01_6(M50R01_6);
        riskLog.setM50R01_7(M50R01_7);
        riskLog.setM50R01_8(M50R01_8);
        riskLog.setM50R01_9(M50R01_9);
        riskLog.setM50R01_10(M50R01_10);
        riskLog.setM50R02(M50R02);
        riskLog.setM60R27(M60R27);
        riskLog.setM70R01(M70R01);
        riskLog.setM70R03(M70R03);
        riskLog.setM70R07(M70R07);
        riskLog.setM70R08(M70R08);
        riskLog.setM70R09(M70R09);
        riskLog.setM70R10(M70R10);
        riskLog.setM70R11(M70R11);
        riskLog.setM80R01(M80R01);
        riskLog.setM80R02(M80R02);
        riskLog.setM80R03(M80R03);
        riskLog.setM80R04(M80R04);
        riskLog.setM80R05(M80R05);
        riskLog.setM80R06(M80R06);
        riskLog.setSa001(Sa001);
        riskLog.setTime(System.currentTimeMillis());
        riskLogMapper.save(riskLog);

    }
}
