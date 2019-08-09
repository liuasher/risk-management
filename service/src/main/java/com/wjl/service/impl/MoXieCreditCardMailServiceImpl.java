package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.MoxieConfiguration;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.MoXieCreditCardMailMapper;
import com.wjl.model.MoxieCreditCardMail;
import com.wjl.model.mongo.CreditCardMailBillData;
import com.wjl.model.mongo.CreditCardMailReportData;
import com.wjl.mongo.CreditCardMailBillDataRepository;
import com.wjl.mongo.CreditCardMailReportDataRepository;
import com.wjl.service.MoXieCreditCardMailService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LINJX
 * @description
 * @date 2018/4/3 16:20
 */
@Service
@Log4j
public class MoXieCreditCardMailServiceImpl implements MoXieCreditCardMailService {

    @Autowired
    private MoXieCreditCardMailMapper moXieCreditCardMailMapper;

    @Autowired
    private MoxieConfiguration moxieConfiguration;
    @Autowired
    private CreditCardMailBillDataRepository creditCardMailBillDataRepository;
    @Autowired
    private CreditCardMailReportDataRepository creditCardMailReportDataRepository;


    @Override
    public MoxieCreditCardMail findByUserIdAndTypeAndTaskId(Long userId, Integer type,String taskId) {
        return moXieCreditCardMailMapper.findByUserIdAndTypeAndTaskId(userId,type,taskId);
    }

    @Override
    public void updateMoXieBankFailed(Long userId) {
        moXieCreditCardMailMapper.updateMoXieBankFailed(userId);
    }

    @Override
    public List<MoxieCreditCardMail> findBillWait() {
        return moXieCreditCardMailMapper.findBillWait();
    }

    @Override
    public List<MoxieCreditCardMail> findReportWait() {
        return moXieCreditCardMailMapper.findReportWait();
    }

    @Override
    public void updateReportIdAndQueryTime(Long id, String reportId, Long queryTime) {
        moXieCreditCardMailMapper.updateReportIdAndQueryTime(id,reportId,queryTime);
    }

    @Override
    public List<MoxieCreditCardMail> findReportIsEmptyByTaskId(String taskId) {
        return moXieCreditCardMailMapper.findReportIsEmptyByTaskId(taskId);
    }

    @Override
    public void save(MoxieCreditCardMail moxieCreditCardMail) {
        moXieCreditCardMailMapper.save(moxieCreditCardMail);
    }

    @Override
    public String billQuery(MoxieCreditCardMail moxieCreditCardMail) {
        String token = moxieConfiguration.getToken();
        String creditCardBillUrl = moxieConfiguration.getCreditCardBillUrl();
        String params = "task_id=%s";
        params = String.format(params, moxieCreditCardMail.getTaskId());
        String bill = HttpUtils.sendMoXieGet(creditCardBillUrl, params, token);
        JSONObject jsonBill = JSONObject.parseObject(bill);
        if(null == jsonBill) {
            log.error(String.format("查询信用卡邮箱bill完毕-失败,userId=%s", moxieCreditCardMail.getUserId()));
            return null;
        }
        CreditCardMailBillData creditCardMailBillData = new CreditCardMailBillData();
        creditCardMailBillData.setQueryTime(System.currentTimeMillis());
        creditCardMailBillData.setBill(jsonBill);
        creditCardMailBillData.setUserId(moxieCreditCardMail.getUserId());
        creditCardMailBillData.setIdentification(moxieCreditCardMail.getIdentification());
        creditCardMailBillDataRepository.save(creditCardMailBillData);
        log.info(String.format("保存信用卡邮箱bill到mongo完毕-成功,userId=%s", moxieCreditCardMail.getUserId()));
        updateReportIdAndQueryTime(moxieCreditCardMail.getId(), creditCardMailBillData.getId(), System.currentTimeMillis());
        return creditCardMailBillData.getId();
    }

    @Override
    public String reportQuery(MoxieCreditCardMail moxieCreditCardMail) {
        String token = moxieConfiguration.getToken();
        String creditCardReportUrl = moxieConfiguration.getCreditCardReportUrl();
        String emailId = moxieCreditCardMail.getEmailId();
        String params = emailId+"/"+moxieCreditCardMail.getTaskId();
        String report = HttpUtils.restfulSendGet(creditCardReportUrl, params, token);
        JSONArray jsonReport = JSONArray.parseArray(report);
        if(null == jsonReport) {
            log.error(String.format("查询信用卡邮箱report完毕-失败,userId=%s", moxieCreditCardMail.getUserId()));
            return null;
        }
        CreditCardMailReportData creditCardMailReportData = new CreditCardMailReportData();
        creditCardMailReportData.setQueryTime(System.currentTimeMillis());
        creditCardMailReportData.setReport(jsonReport);
        creditCardMailReportData.setUserId(moxieCreditCardMail.getUserId());
        creditCardMailReportData.setIdentification(moxieCreditCardMail.getIdentification());
        creditCardMailReportDataRepository.save(creditCardMailReportData);
        log.info(String.format("保存信用卡邮箱Report到mongo完毕-成功,userId=%s", moxieCreditCardMail.getUserId()));
        updateReportIdAndQueryTime(moxieCreditCardMail.getId(), creditCardMailReportData.getId(), System.currentTimeMillis());
        return creditCardMailReportData.getId();
    }

    @Override
    public CreditCardMailBillData getBill(String billId) {
        return creditCardMailBillDataRepository.findById(billId);
    }

    @Override
    public CreditCardMailReportData getReport(String reportId) {
        return creditCardMailReportDataRepository.findById(reportId);
    }

    @Override
    public void updateMysqlStatus(Long id, Integer status) {
        moXieCreditCardMailMapper.updateMysqlStatus(id,status);
    }

}
