package com.wjl.mq.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.commom.util.TxnContext;
import com.wjl.mapper.ApiTokenMapper;
import com.wjl.model.ApiToken;
import com.wjl.model.AuditResult;
import com.wjl.model.constant.Constant;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.model.mongo.TencentCloudReport;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.AuditBean;
import com.wjl.model.mq.ResultBean;
import com.wjl.model.mq.Txn;
import com.wjl.mongo.MobileOperatorBillRepository;
import com.wjl.mongo.MobileOperatorReportRepository;
import com.wjl.mongo.TencentCloudReportRepository;
import com.wjl.mongo.TongDunReportRepository;
import com.wjl.mq.service.ReadyTxnService;
import com.wjl.mq.service.LocalService;
import com.wjl.properties.AppProperties;
import com.wjl.properties.AuditModelType;
import com.wjl.service.microservice.RuleEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 *@author hqh
 */
@Service("localService")
@Slf4j
public class LocalServiceImpl implements LocalService {


    @Autowired
    private MobileOperatorReportRepository mobileOperatorReportRepository;

    @Autowired
    private MobileOperatorBillRepository mobileOperatorBillRepository;

    @Autowired
    private TencentCloudReportRepository tencentCloudReportRepository;

    @Autowired
    private TongDunReportRepository tongDunReportRepository;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private ReadyTxnService readyTxnService;

    @Autowired
    private RuleEngineService ruleEngineService;

    @Autowired
    private ApiTokenMapper apiTokenMapper;

    @Override
    public MobileOperatorReport getMobileOperatorReport(String reportId) {
        return mobileOperatorReportRepository.findById(reportId);
    }

    @Override
    public MobileOperatorBill getMobileOperatorBill(String reportId) {
        return mobileOperatorBillRepository.findById(reportId);
    }

    @Override
    public TencentCloudReport getTencentCloudReport(String reportId) {
        return tencentCloudReportRepository.findById(reportId);
    }

    @Override
    public TongDunReport getTongDunReport(String reportId) {
        return tongDunReportRepository.findById(reportId);
    }


    @Override
    public Map<String, Object> step0IsReadyData(AuditBean auditBean) {
        /**
         *  公共规则
         */
        TxnContext txnContext = TxnContext.getCurrentContext();
        String remark ;
        if (auditBean == null) {
            txnContext.setRemark("【审核步骤[step0公共规则] 认证记录不存在】");
            return null;
        }
        try {

            MobileOperatorReport mobileOperatorReport = getMobileOperatorReport(auditBean.getMobileOperatorReportId());
            if (mobileOperatorReport == null) {
                remark = String.format("【审核步骤[step0公共规则]，未获取到运营商原始数据(userCredit=%s)报表数据：null】", auditBean.getCreditId());
                log.error(remark);
                txnContext.setRemark(remark);
                return null;
            }
            MobileOperatorBill mobileOperatorBill = getMobileOperatorBill(auditBean.getMobileOperatorBillId());
            if (mobileOperatorBill == null) {
                remark = String.format("【审核步骤[step0公共规则]，未获取到运营商(creditId=%s)报表数据：null】", auditBean.getCreditId());
                log.error(remark);
                txnContext.setRemark(remark);
                return null;
            }
            TencentCloudReport tencentCloudReport = getTencentCloudReport(auditBean.getTencentCloudId());
            if (tencentCloudReport == null) {
                remark = String.format("【审核步骤[step0公共规则]，未获取到腾讯云反欺诈(creditId=%s)报表数据：null】", auditBean.getCreditId());
                log.error(remark);
                txnContext.setRemark(remark);
                return null;
            }
            TongDunReport tongDunReport = getTongDunReport(auditBean.getTongDunId());
            if (tongDunReport == null) {
                remark = String.format("【审核步骤[step1新客规则1]，未获取到同盾(creditId=%s)报表数据：null】", auditBean.getCreditId());
                log.error(remark);
                txnContext.setRemark(remark);
                return null;
            }

            Map<String, Object> dataMap = Maps.newHashMap();
            //手机运营商Bill
            dataMap.put(Constant.MOBILE_OPERATOR_BILL, mobileOperatorBill);
            //腾讯云
            dataMap.put(Constant.TENCENT_CLOUD_DATA, tencentCloudReport);
            //同盾
            dataMap.put(Constant.TONGDUN_DATA, tongDunReport);
            //手机运营商Report
            dataMap.put(Constant.MOBILE_OPERATOR_REPORT, mobileOperatorReport);
            dataMap.put(Constant.GONGGO, true);
            dataMap.put(Constant.DATA_LOSE, false);
            return dataMap;
        } catch (Exception e) {
            remark = String.format("【审核步骤[step0公共规则]（creditId=%s）准备数据异常】：%s", auditBean.getCreditId(), e.getMessage());
            log.error(remark, e);
            txnContext.setRemark(remark);
            return null;
        }
    }

    @Override
    public Map<String, Object> step1IsReadyData(AuditBean auditBean) {
        /*
         *  新客规则1计算
         */
        TxnContext txnContext = TxnContext.getCurrentContext();
        String remark ;
        if (auditBean == null) {
            txnContext.setRemark("【审核步骤[step1新客规则1] 交易记录不存在】");
            return null;
        }
        try {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put(Constant.DATA_LOSE, false);
            return dataMap;
        } catch (Exception e) {
            remark = String.format("【审核步骤[step1新客规则1]（userCreditId=%s）准备数据异常】：%s", auditBean.getCreditId(), e.getMessage());
            log.error(remark, e);
            txnContext.setRemark(remark);
            return null;
        }
    }

    @Override
    public Map<String, Object> step2IsReadyData(AuditBean auditBean) {

        Map<String, Object> data = Maps.newHashMap();
        data.put(Constant.DATA_LOSE, false);
        return data;
    }




    @Override
    public boolean step0IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data) {

        TxnContext txnContext = TxnContext.getCurrentContext();
        if (auditBean == null || data == null) {
            txnContext.setRemark("审核步骤[step0公共规则] 交易记录不存在或者第三方数据不存在");
            return false;
        }

        return true;
    }

    @Override
    public boolean step1IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data) {

        return true;
    }

    @Override
    public boolean step2IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data) {

        return true;
    }



    @Override
    public AuditResult step0TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        try {
            long readyTxnStart = System.currentTimeMillis();
            AuditModelType step0ModelType = appProperties.getStep0ModelType();
            step0ModelType.setSecret(appProperties.getRuleEngineSecret());
            Txn txn = readyTxnService.readyTxn(auditBean, step0ModelType, data, fieldData);
            AuditResult result = ruleEngineService.ruleProcess(txn);
            log.info(String.format("【审核步骤[step0公共规则] , userCreditId=%s】 AuditResult=%s, readyTxnUse=%s, txn=%s", auditBean.getCreditId(), result, (System.currentTimeMillis() - readyTxnStart), txn));
            if (Constant.AutoAuditResult_Fail.equals(result.getAuditResult())) {
                //当step0公共规则 自动审核拦截 拒绝
                log.info(String.format("【审核步骤[step0公共规则] ,userCreditId=%s，自动审核拦截】", auditBean.getCreditId()));
                callAuditRefuse(auditBean, "自动审核拒绝，审核步骤[step0公共规则]" + Constant.AutoAuditResultMap.get(result.getAuditResult()));
            }
            log.info(String.format("【审核步骤[step0公共规则]，自动审核-完成 ,userCreditId=%s】", auditBean.getCreditId()));
            result.setFieldData(txn.getData());
            return result;
        } catch (Exception e) {
            log.error(String.format("【审核步骤[step0公共规则],userCredit=%s】 异常:%s",
                    auditBean.getCreditId(), e.getMessage()), e);
        }
        return AuditResult.builder().auditResult(Constant.AutoAuditResult_Wait).build();
    }

    @Override
    public AuditResult step1TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        try {
            long readyTxnStart = System.currentTimeMillis();
            AuditModelType step1ModelType = appProperties.getStep1ModelType();
            step1ModelType.setSecret(appProperties.getRuleEngineSecret());
            Txn txn = readyTxnService.readyTxn(auditBean, step1ModelType, data, fieldData);
            AuditResult result = ruleEngineService.ruleProcess(txn);
            log.info(String.format("【审核步骤[step1新客规则1],userCreditId=%s】 AuditResult=%s, readyTxnUse=%s, txn=%s", auditBean.getCreditId(), result, (System.currentTimeMillis() - readyTxnStart), txn));
            if (Constant.AutoAuditResult_Fail.equals(result.getAuditResult())) {
                //step1新客规则1 自动审核拦截 拒绝
                log.info(String.format("【审核步骤[step1新客规则1] ,userCreditId=%s，自动审核拦截，拒绝放款】", auditBean.getCreditId()));
                callAuditRefuse(auditBean, "自动审核拒绝，审核步骤[step1新客规则1]" + Constant.AutoAuditResultMap.get(result.getAuditResult()));
            }
            log.info(String.format("【审核步骤[step1新客规则1]，自动审核-完成,userCreditId=%s】", auditBean.getCreditId()));result.setFieldData(txn.getData());
            return result;
        } catch (Exception e) {
            log.error(String.format("【审核步骤[step1新客规则1] ,creditId=%s】 异常：%s", auditBean.getCreditId(), e.getMessage()), e);
        }
        return AuditResult.builder().auditResult(Constant.AutoAuditResult_Wait).build();
    }

    @Override
    public AuditResult step2TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        try {
            long readyTxnStart = System.currentTimeMillis();
            AuditModelType step2ModelType = appProperties.getStep2ModelType();
            step2ModelType.setSecret(appProperties.getRuleEngineSecret());
            Txn txn = readyTxnService.readyTxn(auditBean, step2ModelType, data, fieldData);
            AuditResult result = ruleEngineService.scoreProcess(txn);
            log.info(String.format("【审核步骤[step2新客评分卡1],userCreditId=%s】 AuditResult=%s, readyTxnUse=%s, txn=%s", auditBean.getCreditId(), result, (System.currentTimeMillis() - readyTxnStart), txn));
            if (Constant.AutoAuditResult_Fail.equals(result.getAuditResult())) {
                //step2新客评分卡1 自动审核拦截 拒绝
                log.info(String.format("【审核步骤[step2新客评分卡1] ,userCreditId=%s ，自动审核拦截，拒绝放款】", auditBean.getCreditId()));
                callAuditRefuse(auditBean, "自动审核拒绝，审核步骤[step2新客评分卡1]" + Constant.AutoAuditResultMap.get(result.getAuditResult()));
            } else if (Constant.AutoAuditResult_OK.equals(result.getAuditResult())) {
                //step2新客评分卡1 自动审核通过 放款 放款次数达到限制转人工
                log.info(String.format("【审核步骤[step2新客评分卡1],userCreditId=%s，自动审核通过，同意放款】", auditBean.getCreditId()));
                callAuditPass(auditBean, "自动审核通过，审核步骤[step2新客评分卡1]" + Constant.AutoAuditResultMap.get(result.getAuditResult()));
            } else if (Constant.AutoAuditResult_Warn.equals(result.getAuditResult())) {
                log.info(String.format("【审核步骤[step2新客评分卡1], userCreditId=%s，自动审核预警，转人工】", auditBean.getCreditId()));
                manVerify(auditBean,"自动审核预警，转人工" );
            }
            log.info(String.format("【审核步骤[step2新客评分卡1]，自动审核-完成 ,userCreditId=%s】", auditBean.getCreditId()));
            result.setFieldData(txn.getData());
            return result;
        } catch (Exception e) {
            log.error(String.format("【审核步骤[step2新客评分卡1] ,userCreditId=%s】 异常：%s",
                    auditBean.getCreditId(), e.getMessage()), e);
        }
        return AuditResult.builder().auditResult(Constant.AutoAuditResult_Wait).build();
    }

    @Override
    public void callAuditRefuse(AuditBean auditBean, String remark) {
        ResultBean resultBean = new ResultBean();
        resultBean.setRemark(remark);
        resultBean.setVerifyTime(System.currentTimeMillis());
        resultBean.setUserId(auditBean.getUserId());
        resultBean.setCreditId(auditBean.getCreditId());
        String url = null;
        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(auditBean.getIdentification(), ApiTypeConstant.RISK_MANAGE);
        if (null!=byTokenAnAndType){
            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
        }
        Response response = new Response<>(CodeEnum.AUDIT_FAIL,resultBean);
        try {
            HttpUtils.sendPost(url, JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("错误原因 {}", e);
        }
    }

    @Override
    public void callAuditPass(AuditBean auditBean, String remark) {
        ResultBean resultBean = new ResultBean();
        resultBean.setRemark(remark);
        resultBean.setVerifyTime(System.currentTimeMillis());
        resultBean.setUserId(auditBean.getUserId());
        resultBean.setCreditId(auditBean.getCreditId());
        String url = null;
        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(auditBean.getIdentification(), ApiTypeConstant.RISK_MANAGE);
        if (null!=byTokenAnAndType){
            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
        }
        Response response = new Response<>(CodeEnum.AUDIT_PASS,resultBean);
        try {
            HttpUtils.sendPost(url, JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("错误原因 {}", e);
        }
    }

    @Override
    public void manVerify(AuditBean auditBean,String remark) {
        ResultBean resultBean = new ResultBean();
        resultBean.setVerifyTime(System.currentTimeMillis());
        resultBean.setRemark(remark);
        resultBean.setUserId(auditBean.getUserId());
        resultBean.setCreditId(auditBean.getCreditId());
        String url = null;
        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(auditBean.getIdentification(), ApiTypeConstant.RISK_MANAGE);
        if (null!=byTokenAnAndType){
            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
        }
        Response response = new Response<>(CodeEnum.AUDIT_WAIT,resultBean);
        try {
            HttpUtils.sendPost(url, JSON.toJSONString(response));
        } catch (IOException e) {
            log.error("错误原因 {}", e);
        }
    }


}
