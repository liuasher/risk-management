package com.wjl.service.impl;

import com.wjl.commom.util.IndicatorConfig;
import com.wjl.mapper.RiskRulesHitMapper;
import com.wjl.model.RiskRulesHit;
import com.wjl.model.mq.AuditBean;
import com.wjl.service.RiskRulesHitService;
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
public class RiskRulesHitServiceImpl implements RiskRulesHitService {

    @Autowired
    private RiskRulesHitMapper riskRulesHitMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRiskHitRules(Map<String, Object> fieldValueMap, AuditBean auditBean) {

        Long userCreditId = auditBean.getCreditId();
        Long userId = auditBean.getUserId();
        String identification = auditBean.getIdentification();
        Integer M10R01 = (Integer) fieldValueMap.get(IndicatorConfig.M10R01);
        String M10R03 = (String) fieldValueMap.get(IndicatorConfig.M10R03);
        String M10R08 = (String) fieldValueMap.get(IndicatorConfig.M10R08);
        String M10R05 = (String) fieldValueMap.get(IndicatorConfig.M10R05);
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
        //分期上线为保证获取第三方数据
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
        Double M60R27 = (Double)fieldValueMap.get(IndicatorConfig.M60R27);
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
        Double sa001 = (Double) fieldValueMap.get(IndicatorConfig.SA001);
        if(M10R01<22 || M10R01>45){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M10R01");
            riskRulesHit.setValue(M10R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }

        if(M10R03.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M10R03");
            riskRulesHit.setValue(M10R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }

        if(M10R08.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M10R08");
            riskRulesHit.setValue(M10R08+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M10R05.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M10R05");
            riskRulesHit.setValue(M10R05+"");
            riskRulesHitMapper.save(riskRulesHit);
        }

        if(M20R02>20){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M20R02");
            riskRulesHit.setValue(M20R02+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M20R03>2){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M20R03");
            riskRulesHit.setValue(M20R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M20R04>3){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M20R04");
            riskRulesHit.setValue(M20R04+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M20R05>2){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M20R05");
            riskRulesHit.setValue(M20R05+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M20R06>3){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M20R06");
            riskRulesHit.setValue(M20R06+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R01.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R01");
            riskRulesHit.setValue(M30R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R02.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R02");
            riskRulesHit.setValue(M30R02+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R03.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R03");
            riskRulesHit.setValue(M30R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R05>5){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R05");
            riskRulesHit.setValue(M30R05+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R06<20){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R06");
            riskRulesHit.setValue(M30R06+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R07>0.5){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R07");
            riskRulesHit.setValue(M30R07+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M30R08>5){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M30R08");
            riskRulesHit.setValue(M30R08+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M40R01.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M40R01");
            riskRulesHit.setValue(M40R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M40R03.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M40R03");
            riskRulesHit.setValue(M40R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_1.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_1");
            riskRulesHit.setValue(M50R01_1+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_2.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_2");
            riskRulesHit.setValue(M50R01_2+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_3.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_3");
            riskRulesHit.setValue(M50R01_3+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_4.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_4");
            riskRulesHit.setValue(M50R01_4+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_5.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_5");
            riskRulesHit.setValue(M50R01_5+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_6.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_6");
            riskRulesHit.setValue(M50R01_6+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_7.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_7");
            riskRulesHit.setValue(M50R01_7+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_8.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_8");
            riskRulesHit.setValue(M50R01_8+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_9.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_9");
            riskRulesHit.setValue(M50R01_9+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01_10.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01_10");
            riskRulesHit.setValue(M50R01_10+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R01.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R01");
            riskRulesHit.setValue(M50R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M50R02>80){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M50R02");
            riskRulesHit.setValue(M50R02+"");
            riskRulesHitMapper.save(riskRulesHit);
        }

        if(M60R27>0.5){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M60R27");
            riskRulesHit.setValue(M60R27+"");
            riskRulesHitMapper.save(riskRulesHit);
        }

        if(M70R01>15){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R01");
            riskRulesHit.setValue(M70R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R03>60){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R03");
            riskRulesHit.setValue(M70R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R07.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R07");
            riskRulesHit.setValue(M70R07+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R08.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R08");
            riskRulesHit.setValue(M70R08+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R09.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R09");
            riskRulesHit.setValue(M70R09+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R10.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R10");
            riskRulesHit.setValue(M70R10+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M70R11.equals("Y")){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M70R11");
            riskRulesHit.setValue(M70R11+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R01<15){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R01");
            riskRulesHit.setValue(M80R01+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R02<6){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R02");
            riskRulesHit.setValue(M80R02+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R03<4){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R03");
            riskRulesHit.setValue(M80R03+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R04<10){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R04");
            riskRulesHit.setValue(M80R04+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R05<10){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R05");
            riskRulesHit.setValue(M80R05+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if(M80R06>0.9){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("M80R06");
            riskRulesHit.setValue(M80R06+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        if( sa001<550){
            RiskRulesHit riskRulesHit = new RiskRulesHit();
            riskRulesHit.setCreditId(userCreditId);
            riskRulesHit.setUserId(userId);
            riskRulesHit.setIdentification(identification);
            riskRulesHit.setIndexCode("sa001");
            riskRulesHit.setValue(sa001+"");
            riskRulesHitMapper.save(riskRulesHit);
        }
        
    }
}
