package com.wjl.service.microservice;

import com.wjl.model.AuditResult;
import com.wjl.model.constant.Constant;
import com.wjl.model.mq.Txn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Component
public class RuleEngineServiceFallback implements RuleEngineService {
    @Override
    public AuditResult ruleProcess(@RequestBody Txn txn) {
        log.error(String.format("【调用规则模型失败，applyId=%s】",txn.getTradeId()));
        return AuditResult.builder().auditResult(Constant.AutoAuditResult_Wait).build();
    }

    @Override
    public AuditResult scoreProcess(@RequestBody Txn txn) {
        log.error(String.format("【调用评分模型失败，applyId=%s】",txn.getTradeId()));
        return AuditResult.builder().auditResult(Constant.AutoAuditResult_Wait).build();
    }
}
