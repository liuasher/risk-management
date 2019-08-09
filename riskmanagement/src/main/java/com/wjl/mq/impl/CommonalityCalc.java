package com.wjl.mq.impl;

import com.wjl.commom.util.IndicatorConfig;
import com.wjl.model.AddressNoAccess;
import com.wjl.model.constant.Constant;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.ThirdCalc;
import com.wjl.service.AddressNoAccessService;
import com.wjl.service.RiskBlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/10
 */
@Service
@Slf4j
public class CommonalityCalc implements ThirdCalc {

    @Autowired
    private AddressNoAccessService addressNoAccessService;
    @Autowired
    private RiskBlackService riskBlackService;

    @Override
    public void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data) {
        //公共规则计算
        Object gonggo = data.get(Constant.GONGGO);
        if (gonggo == null) {
            return;
        }

        //年龄
        Integer M10R01 = 0;
        //身份证号命中负面地区清单
        String M10R03 = "N";
        //是否击中内部黑名单
        String M40R01 = "N";
        //工作单位击中非准入行业关键字
        String M10R08 = "N";

        try {
            //获取用户身份证
            String idcard = auditBean.getIdCard();
            //获取出生年份
            String year = idcard.substring(6, 10);
            // 得到当前的系统时间
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            // 当前年份
            String fyear = format.format(date).substring(0, 4);
            M10R01 = Integer.valueOf(fyear) - Integer.valueOf(year);
        } catch (Exception e) {
            log.error("用户年龄计算出错", e);
        }

        try {
            String idcard = auditBean.getIdCard();
            List<AddressNoAccess> all = addressNoAccessService.findALL();
            for (AddressNoAccess addressNoAccess: all) {
                String substring = idcard.substring(0, addressNoAccess.getIdMatch());
                if (substring.equals(addressNoAccess.getCertNo())){
                    M10R03="Y";
                    log.info("-----身份证命中负面区域，idCard={}-----",idcard);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("-----身份证命中负面区域出错-----",e);
        }

        try {
            //判断手机号码是否击中黑名单
            Integer countByMobile = riskBlackService.getCountByMobile(auditBean.getMobile());
            if (countByMobile > 0) {
                M40R01 = "Y";
            }
            //判断用户身份证是否击中黑名单
            Integer countByIdCard = riskBlackService.getCountByIdCard(auditBean.getIdCard());
            if (countByIdCard > 0) {
                M40R01 = "Y";
            }
        } catch (Exception e) {
            log.error("-----黑名单计算出错-----", e);
        }

        try {
            String companyName = auditBean.getCompanyName();

            //非准入行业
            String industry = "公安,派出所,警,检察院,监狱,赌场,新闻,传媒,推广,监察,部队,法院,司法局,检察,武装,边防,法制,报社,电视台,律师,城管,执法";

            String[] split = industry.split(",");
            for (int i = 0; i < split.length; i++) {
                if (companyName.contains(split[i])) {
                    M10R08 = "Y";
                    break;
                }
            }
        } catch (Exception e) {
            log.error("-----计算工作单位击中非准入行业关键字出错-----",e);
        }

        fieldValueMap.put(IndicatorConfig.M10R01, M10R01);
        fieldValueMap.put(IndicatorConfig.M10R03, M10R03);
        fieldValueMap.put(IndicatorConfig.M40R01, M40R01);
        fieldValueMap.put(IndicatorConfig.M10R08, M10R08);

    }

}
