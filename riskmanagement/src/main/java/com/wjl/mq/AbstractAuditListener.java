package com.wjl.mq;

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
import com.wjl.model.mq.AuditBean;
import com.wjl.model.mq.ResultBean;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * 处理规则抽象类
 * @author hqh
 */
@Log4j
public abstract class AbstractAuditListener implements AuditListener {
    @Autowired
    private ApiTokenMapper apiTokenMapper;

    @Override
    public AuditResult auditProcess(AuditBean auditBean){
        Map<String,Object> data=isReadyData(auditBean);
        TxnContext txnContext=TxnContext.getCurrentContext();
        Map<String, Object> fieldData = auditBean.getFieldData();
        if (data==null || data.isEmpty()){
            log.error(String.format("【%s 审核,授信审核 userCreditId=%s 数据未准备好】",getStepName(),auditBean.getCreditId()));
            callAuditRefuse(auditBean,"自动审核拒绝，"+txnContext.getRemark());
            return AuditResult.builder().auditResult(Constant.AutoAuditResult_Fail).build();
        }
        if (!isDataCompleted(auditBean,data)){
            log.error(String.format("【%s 审核,授信审核 userCreditId=%s 数据不完善】",getStepName(),auditBean.getCreditId()));
            callAuditRefuse(auditBean,"自动审核拒绝，"+txnContext.getRemark());
            return AuditResult.builder().auditResult(Constant.AutoAuditResult_Fail).build();
        }
        if (fieldData==null){
            fieldData= Maps.newHashMap();
        }
        return doAudit(auditBean,data,fieldData);
    }

    @Override
    public void callAuditRefuse(AuditBean auditBean,String remark){
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

    abstract String getStepName();
}
