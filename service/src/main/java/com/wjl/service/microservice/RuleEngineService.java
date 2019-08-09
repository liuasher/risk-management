package com.wjl.service.microservice;

import com.wjl.model.AuditResult;
import com.wjl.model.mq.Txn;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "Rule-Engine",fallback = RuleEngineServiceFallback.class)
public interface RuleEngineService {
    /**
     * 交易规则审核入口
     * @param txn
     * @return
     */
    @RequestMapping(value="/ruleEngine/ruleProcess", method= RequestMethod.POST)
     AuditResult ruleProcess(@RequestBody Txn txn);

    /**
     * 交易评分卡 审核入口
     * @param txn
     * @return
     */
    @RequestMapping(value="/ruleEngine/scoreProcess", method=RequestMethod.POST)
     AuditResult scoreProcess(@RequestBody Txn txn);
}
