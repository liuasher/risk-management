package com.wjl.mq;


import com.wjl.model.AuditResult;
import com.wjl.model.mq.AuditBean;

import java.util.Map;

/**
 * @author  肖斌 on 2017/9/13.
 */
public interface AuditListener {
    /**
     * 风控审核
     * @param auditBean
     * @return
     */
    AuditResult auditProcess(AuditBean auditBean);

    /**
     * 规则、评分卡审核
     * @param auditBean
     * @param data
     * @param fieldData
     * @return
     */
    AuditResult doAudit(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData);

    /**
     * 第三方数据是否准备完全
     * @param auditBean
     * @return
     */
    Map<String,Object> isReadyData(AuditBean auditBean);

    /**
     * 数据是否完整
     * @param auditBean
     * @param data
     * @return
     */
    boolean isDataCompleted(AuditBean auditBean, Map<String, Object> data);

    /**
     * 机审拒绝
     * @param auditBean
     * @param remark
     */
    void callAuditRefuse(AuditBean auditBean, String remark);


}
