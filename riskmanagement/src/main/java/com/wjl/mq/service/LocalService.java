package com.wjl.mq.service;


import com.wjl.model.AuditResult;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.model.mongo.TencentCloudReport;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.AuditBean;

import java.util.Map;

/**
 * @author hqh
 */
public interface LocalService {


    /**
     * 获取手机运营商Report
     * @param reportId
     * @return
     */
    MobileOperatorReport getMobileOperatorReport(String reportId);

    /**
     * 获取手机运营商Bill
     * @param reportId
     * @return
     */
    MobileOperatorBill getMobileOperatorBill(String reportId);

    /**
     * 获取腾讯云报告
     * @param reportId
     * @return
     */
    TencentCloudReport getTencentCloudReport(String reportId);
    /**
     * 获取同盾报告
     */
    TongDunReport getTongDunReport(String reportId);
    /**
     * 检查 step0公共规则 数据是否准备好
     * @param auditBean
     * @return
     */
    Map<String,Object> step0IsReadyData(AuditBean auditBean);

    /**
     * 检查 step1新客规则1 数据是否准备好
     * @param auditBean
     * @return
     */
    Map<String,Object> step1IsReadyData(AuditBean auditBean);

    /**
     * 检查 step2新客评分卡1 数据是否准备好
     * @param auditBean
     * @return
     */
    Map<String,Object> step2IsReadyData(AuditBean auditBean);

    /**
     * 检查step0公共规则 数据是否完整
     * @param auditBean
     * @param data
     * @return
     */
    boolean step0IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data);

    /**
     * 检查step1新客规则1 数据是否完整
     * @param auditBean
     * @param data
     * @return
     */
    boolean step1IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data);

    /**
     * 检查step2新客评分卡1数据是否完整
     * @param auditBean
     * @param data
     * @return
     */
    boolean step2IsReadyDataComplete(AuditBean auditBean, Map<String, Object> data);

    /**
     * step0公共规则调用规则引擎服务自动审核
     * @param auditBean
     * @param data
     * @param fieldData
     * @return
     */
    AuditResult step0TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData);

    /**
     * step1新客规则1调用规则引擎服务自动审核
     * 审核结果：拦截时 ，自动拒绝；通过时 下一个流程 step2新客评分卡1
     * @param auditBean  申请记录
     * @param data  第三方数据
     * @param fieldData 指标数据
     * @return
     */
    AuditResult step1TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData);

    /**
     * step2新客评分卡调用规则引擎服务自动审核
     * 审核结果：拦截时 ，自动拒绝；通过时 自动放款；预警时 ，下一个流程 step3新客规则2
     * @param auditBean   申请记录
     * @param data  第三方数据
     * @param fieldData  指标数据
     * @return
     */
    AuditResult step2TxnProcess(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData);

    /**
     * 机审拒绝
     * @param auditBean
     * @param remark
     */
    void callAuditRefuse(AuditBean auditBean,String remark);

    /**
     * 机审通过
     * @param auditBean
     * @param remark
     */
    void callAuditPass(AuditBean auditBean, String remark);

    /**
     * 待人工审核
     * @param auditBean
     */
    void manVerify(AuditBean auditBean, String remark);


}