package com.wjl.service;

import com.wjl.model.mq.AuditBean;

import java.util.Map; /**
 * @author ZHAOJP
 * @version 1.0
 * @description 风控各项指标
 * @date 2018/4/10
 */
public interface RiskLogService {
    /**
     * 保存用户的风控指标数值
     * @param fieldValueMap
     * @param auditBean
     */
    void saveRiskLog(Map<String, Object> fieldValueMap, AuditBean auditBean);
}
