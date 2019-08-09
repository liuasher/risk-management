package com.wjl.controller;

import com.alibaba.fastjson.JSON;
import com.wjl.model.AuditResult;
import com.wjl.model.mq.Txn;
import com.wjl.service.microservice.RuleEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 规则引擎
 * @author hqh
 * @date 2018-4-10
 */
@Slf4j
@RestController
@RequestMapping(value = "/RuleEngine")
public class RuleEngineController {
    @Resource
    RuleEngineService ruleEngineService;

    @RequestMapping(value = "/getRuleResult")
    public String getRuleResult(@RequestBody String requestBody){
        Txn txn = JSON.parseObject(requestBody, Txn.class);
        AuditResult auditResult = ruleEngineService.ruleProcess(txn);
        String result = JSON.toJSONString(auditResult);
        return result;
    }

    @RequestMapping(value = "/getScoreResult")
    public String getScoreResult(@RequestBody String requestBody){
        Txn txn = JSON.parseObject(requestBody, Txn.class);
        AuditResult auditResult = ruleEngineService.scoreProcess(txn);
        String result = JSON.toJSONString(auditResult);
        return result;
    }
}
