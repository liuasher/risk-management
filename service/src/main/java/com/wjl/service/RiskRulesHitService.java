package com.wjl.service;

import com.wjl.model.mq.AuditBean;

import java.util.Map; /**
 * @author ZHAOJP
 * @version 1.0
 * @description 风控击中指标service
 * @date 2018/4/10
 */
public interface RiskRulesHitService {
    /**
     * 保存用户风控击中指标的数值
     * @param fieldValueMap
     * @param auditBean
     */
    void saveRiskHitRules(Map<String, Object> fieldValueMap, AuditBean auditBean);
}
