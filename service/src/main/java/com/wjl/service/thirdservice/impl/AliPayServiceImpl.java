package com.wjl.service.thirdservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.MoxieConfiguration;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.MoXieAliPayMapper;
import com.wjl.model.MoxieAliPay;
import com.wjl.model.mongo.MoXieAliPayBillData;
import com.wjl.model.mongo.MoXieAliPayReportData;
import com.wjl.mongo.MoxieAliPayBillDataRepository;
import com.wjl.mongo.MoxieAliPayReportDataRepository;
import com.wjl.service.thirdservice.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 支付宝操作
 *
 * @author mayue
 * @date 2018/4/3
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private MoXieAliPayMapper moXieAliPayMapper;

    @Autowired
    private MoxieConfiguration moxieConfiguration;

    @Autowired
    private MoxieAliPayBillDataRepository moxieAliPayBillDataRepository;

    @Autowired
    private MoxieAliPayReportDataRepository moxieAliPayReportDataRepository;

    @Override
    public void saveAliPayRequestInfo(MoxieAliPay moxieAliPay) {
        moXieAliPayMapper.saveAliPayRequestInfo(moxieAliPay);
    }

    @Override
    public MoxieAliPay findByUserIdAndTypeAndTaskId(Long userId, Integer type,String taskId) {
        return moXieAliPayMapper.findByUserIdAndTypeAndTaskId(userId, type,taskId);
    }

    @Override
    public List<MoxieAliPay> findBillWait() {
        return moXieAliPayMapper.findBillWait();
    }

    @Override
    public List<MoxieAliPay> findReportWait() {
        return moXieAliPayMapper.findReportWait();
    }

    @Override
    public void updateReportIdAndQueryTime(Long id, String reportId, Date queryTime) {
        moXieAliPayMapper.updateReportIdAndQueryTime(id, reportId, queryTime);
    }

    @Override
    public String queryData(MoxieAliPay moxieAliPay) {
        Long id = moxieAliPay.getId();
        Long userId = moxieAliPay.getUserId();
        String taskId = moxieAliPay.getTaskId();
        String billUrl = moxieConfiguration.getPayBillUrl();
        String token = moxieConfiguration.getToken();


        String payBill = HttpUtils.restfulSendGet(billUrl, taskId, token);
        if(null == payBill){
            log.info("userId={}的账单为空,taskId={},token={},billUrl={}", userId, taskId, token, billUrl);
        }
        JSONObject billDate = JSONObject.parseObject(payBill);
        MoXieAliPayBillData moXieAliPayBillData = new MoXieAliPayBillData();
        moXieAliPayBillData.setQueryTime(System.currentTimeMillis());
        moXieAliPayBillData.setUserId(userId);
        moXieAliPayBillData.setBill(billDate);
        moXieAliPayBillData.setIdentification(moxieAliPay.getIdentification());
        moxieAliPayBillDataRepository.save(moXieAliPayBillData);
        log.info(String.format("-----魔蝎支付宝Bill成功存入Mongo,userId=%s-----", userId));
        updateReportIdAndQueryTime(id, moXieAliPayBillData.getId(), new Date());
        return moXieAliPayBillData.getId();
    }

    @Override
    public String queryReport(MoxieAliPay moxieAliPay) {
        MoXieAliPayReportData moXieAliPayReportData = new MoXieAliPayReportData();
        //开始尝试获取Report
        Long id = moxieAliPay.getId();
        String taskId = moxieAliPay.getTaskId();
        Long userId = moxieAliPay.getUserId();
        String reportUrl = moxieConfiguration.getPayReportUrl();
        String token = moxieConfiguration.getToken();

        String report = HttpUtils.restfulSendGet(reportUrl, taskId, token);
        if(null == report){
            log.info("userId={}的账单为空,taskId={},token={},reportUrl={}", userId, taskId, token, reportUrl);
        }
        JSONObject jsonReport = JSONObject.parseObject(report);
        moXieAliPayReportData.setQueryTime(System.currentTimeMillis());
        moXieAliPayReportData.setReport(jsonReport);
        moXieAliPayReportData.setUserId(userId);
        moXieAliPayReportData.setIdentification(moxieAliPay.getIdentification());
        moxieAliPayReportDataRepository.save(moXieAliPayReportData);
        log.info(String.format("-----魔蝎支付宝Report成功存入Mongo,userId=%s-----", userId));
        updateReportIdAndQueryTime(id, moXieAliPayReportData.getId(), new Date());
        return moXieAliPayReportData.getId();
    }

    @Override
    public void updateMysqlStatus(Long id, Integer mysqlStatus) {
        moXieAliPayMapper.updateMysqlStatus(id, mysqlStatus);
    }
}
