package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.util.EmojiFilter;
import com.wjl.mapper.ModelPayDataMapper;
import com.wjl.mapper.ModelPayReportMapper;
import com.wjl.model.mongo.MoXieAliPayBillData;
import com.wjl.model.mongo.MoXieAliPayReportData;
import com.wjl.model.ModelPayData;
import com.wjl.model.ModelPayReport;
import com.wjl.service.AliPayMongoToMysqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付宝model字段处理
 *
 * @author mayue
 * @date 2018/3/20
 */

@Slf4j
@Component
public class AliPayMongoToMysqlServiceImpl implements AliPayMongoToMysqlService {

    @Autowired
    private ModelPayReportMapper modelPayReportMapper;

    @Autowired
    private ModelPayDataMapper modelPayDataMapper;

    /**
     * 支付宝信息model导入
     *
     * @param payData        支付宝信息
     * @param identification 项目标识
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveModelPayData(MoXieAliPayBillData payData, String identification) {
        Long userId = payData.getUserId();
        Long queryTime = payData.getQueryTime();
        Integer verifyCount = 1;

        //获取mongo数据
        JSONObject report = payData.getBill();
        JSONObject userInfo = report.getJSONObject("userinfo");
        String phoneNumber = userInfo.getString("phone_number");
        Date registerTime = userInfo.getDate("register_time");
        JSONArray tradeInfos = report.getJSONArray("tradeinfo");
        if (tradeInfos == null || tradeInfos.size() == 0) {
            log.info("用户为userId={}的用户没有交易记录", userId);
            log.info("userId={},支付宝DATA导入MYSQL成功", userId);
            return;
        }

        //判断是否之前认证过
        List<ModelPayData> list = modelPayDataMapper.findByUserId(userId);
        if (null != list && list.size() > 0) {
            verifyCount = list.get(0).getVerifyCount() + 1;
            //已经认证过，删除之前做的，重新存入
            modelPayDataMapper.remove(userId);
        }

        for (int i = 0; i < tradeInfos.size(); i++) {
            JSONObject tradeInfo = tradeInfos.getJSONObject(i);
            String incomeorexpense = tradeInfo.getString("incomeorexpense");
            String tradeNumber = tradeInfo.getString("trade_number");
            Date tradeTime = tradeInfo.getDate("trade_time");
            BigDecimal tradeAmount = tradeInfo.getBigDecimal("trade_amount");
            String tradeStatus = tradeInfo.getString("trade_status");
            String counterparty = tradeInfo.getString("counterparty");
            String capitalStatus = tradeInfo.getString("capital_status");

            //存入数据库
            ModelPayData modelPayData = new ModelPayData();
            modelPayData.setTradeNumber(tradeNumber);
            modelPayData.setVerifyCount(verifyCount);
            modelPayData.setUserId(userId);
            modelPayData.setQueryTime(new Date(queryTime));
            modelPayData.setPhoneNumber(phoneNumber);
            modelPayData.setRegisterTime(registerTime);
            modelPayData.setCapitalStatus(capitalStatus);
            modelPayData.setCreateTime(new Date());
            modelPayData.setCounterparty(counterparty);
            modelPayData.setIncomeorexpense(incomeorexpense);
            modelPayData.setTradeAmount(new BigDecimal(tradeAmount.doubleValue() / 100));
            modelPayData.setTradeTime(tradeTime);
            modelPayData.setTradeStatus(tradeStatus);
            modelPayData.setIdentification(identification);
            modelPayData.setCounterparty(EmojiFilter.filterEmoji(modelPayData.getCounterparty()));
            modelPayDataMapper.save(modelPayData);
        }
    }

    /**
     * 支付宝报告model导入
     *
     * @param payReport      支付宝信息
     * @param identification 项目标识
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveModelPayReport(MoXieAliPayReportData payReport, String identification) {
        Long userId = payReport.getUserId();

        JSONObject report = payReport.getReport();
        Long queryTime = payReport.getQueryTime();
        JSONObject basicInfo = report.getJSONObject("basic_info");
        JSONObject userInfo = basicInfo.getJSONObject("user_and_account_basic_info");
        String phoneNumber = userInfo.getString("phone_number");
        JSONObject wealthInfo = report.getJSONObject("wealth_info");
        JSONObject totalAssets = wealthInfo.getJSONObject("total_assets");
        String balance = totalAssets.getString("balance");
        String yuEBao = totalAssets.getString("yu_e_bao");
        String zhaoCaiBao = totalAssets.getString("zhao_cai_bao");
        String fund = totalAssets.getString("fund");
        String cunJinBao = totalAssets.getString("cun_jin_bao");
        String taobaoFinance = totalAssets.getString("taobao_finance");
        String huabaiLimit = totalAssets.getString("huabai_limit");

        //判断是否之前认证过
        ModelPayReport modelPayReport = modelPayReportMapper.findByUserId(userId);
        if (null == modelPayReport) {
            modelPayReport = new ModelPayReport();
            modelPayReport.setVerifyCount(1);
            modelPayReport.setUserId(userId);
            modelPayReport.setQueryTime(new Date(queryTime));
            modelPayReport.setPhoneNumber(phoneNumber);
            modelPayReport.setBalance(new BigDecimal(balance));
            modelPayReport.setCunJinBao(new BigDecimal(cunJinBao));
            modelPayReport.setFund(new BigDecimal(fund));
            modelPayReport.setHuabaiLimit(new BigDecimal(huabaiLimit));
            modelPayReport.setTaobaoFinance(new BigDecimal(taobaoFinance));
            modelPayReport.setYuebao(new BigDecimal(yuEBao));
            modelPayReport.setZhaoCaiBao(new BigDecimal(zhaoCaiBao));
            modelPayReport.setCreateTime(new Date());
            modelPayReport.setIdentification(identification);
            modelPayReportMapper.save(modelPayReport);
        } else {
            modelPayReport.setVerifyCount(modelPayReport.getVerifyCount() + 1);
            modelPayReport.setUserId(userId);
            modelPayReport.setQueryTime(new Date(queryTime));
            modelPayReport.setPhoneNumber(phoneNumber);
            modelPayReport.setBalance(new BigDecimal(balance));
            modelPayReport.setCunJinBao(new BigDecimal(cunJinBao));
            modelPayReport.setFund(new BigDecimal(fund));
            modelPayReport.setHuabaiLimit(new BigDecimal(huabaiLimit));
            modelPayReport.setTaobaoFinance(new BigDecimal(taobaoFinance));
            modelPayReport.setYuebao(new BigDecimal(yuEBao));
            modelPayReport.setZhaoCaiBao(new BigDecimal(zhaoCaiBao));
            modelPayReport.setUpdateTime(new Date());
            modelPayReportMapper.update(modelPayReport);
        }

    }
}
