package com.wjl.mq.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.wjl.model.constant.Constant;
import com.wjl.model.mq.AuditBean;
import com.wjl.model.mq.Txn;
import com.wjl.mq.service.ThirdCalc;
import com.wjl.properties.AuditModelType;
import com.wjl.service.RiskLogService;
import com.wjl.service.RiskRulesHitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 准备交易Service
 *
 * @author 秦瑞华
 */
@Slf4j
@Component
public class ReadyTxnService {
    //三方指标计算列表
    @Autowired
    private List<ThirdCalc> thirdCalcList;
    @Autowired
    private RiskLogService riskLogService;
    @Autowired
    private RiskRulesHitService riskRulesHitService;

    /**
     * 准备交易
     *
     * @param
     * @return
     * @throws
     */
    public Txn readyTxn(AuditBean auditBean, AuditModelType modelType, Map<String, Object> data, Map<String, Object> fieldData) {
        Txn txn = new Txn();
        txn.setTradeId(auditBean.getCreditId() + "");                //原始交易ID(必填)
        txn.setTradeTime(System.currentTimeMillis());    //原始交易时间(必填)
        txn.setSystemId(modelType.getSystemId());        //所属系统ID(必填)
        txn.setModelType(modelType.getModelType());        //1.规则模型；2.评分卡模型(必填)
        txn.setModelId(modelType.getModelId());            //模型ID(必填)
        txn.setSecret(modelType.getSecret());            //秘钥

        //准备字段
        Map<String, Object> fieldValueMap = readyFields(auditBean, data);

        //剔除值为空的字段
        Iterator<Entry<String, Object>> it = fieldValueMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> item = it.next();
            if (item.getValue() == null) {
                it.remove();
            }
        }
        //指标合并
        if (fieldData != null) {
            fieldValueMap.putAll(fieldData);
        }
        fieldData = fieldValueMap;

        //设置交易字段数据
        txn.setData(fieldData);

        if(data.containsKey(Constant.GONGGO)) {
            //保存用户公共规则结果
            log.info("项目名：{}，userCreditId：{},开始保存公共规则结果",auditBean.getIdentification(),auditBean.getCreditId());
            try {
                riskLogService.saveRiskLog(fieldValueMap, auditBean);
            } catch (Exception e) {
                log.error("项目名：{}，userCreditId：{},保存公共规则结果出错,{}",auditBean.getIdentification(),auditBean.getCreditId(),e);
            }
            log.info("项目名：{}，userCreditId：{},保存公共规则结果完成",auditBean.getIdentification(),auditBean.getCreditId());

            //保存用户公共规则击中
            log.info("项目名：{}，userCreditId：{},开始保存公共规则击中",auditBean.getIdentification(),auditBean.getCreditId());
            try{
                riskRulesHitService.saveRiskHitRules(fieldValueMap, auditBean);
            }catch (Exception e) {
                log.error("项目名：{}，userCreditId：{},保存公共规则击中出错,{}",auditBean.getIdentification(),auditBean.getCreditId(),e);
            }

            log.info("项目名：{}，userCreditId：{},保存公共规则击中完成",auditBean.getIdentification(),auditBean.getCreditId());
        }

        log.info("fieldValueMap:" + fieldValueMap);
        //数据是否缺失
        Boolean dataLost = (Boolean) fieldValueMap.get("dataLost");
        if (dataLost != null && dataLost) {
            txn.setDataLost(true);
        }
        return txn;
    }

    /**
     * 准备字段<br/><br/>
     * <p>
     * 字段解析策略：尝试获取，无法获取的字段不返回值、或者设置为null,不向【规则引擎】发送该字段<br/><br/>
     * <p>
     * 关于默认值：不能设默认值的初始化为null,不向【规则引擎】发送该字段<br/>
     * 1.有的字段是一定要设默认值的：如[td_0001="N";同盾-身份证命中法院失信名单],<br/>
     * 默认是不命中的N，只有满足条件才能设置为Y<br/><br/>
     * 2.有的字段是不可以设默认值的：如[Long td_0008=null;//同盾-同盾得分]，<br/>
     * 同盾得分不能设置默认值，因此设置为null,只有解析到该值才进行设置，若无法解析到，则不向【规则引擎】发送该字段
     *
     * @param auditBean 借款申请
     */
    private Map<String, Object> readyFields(AuditBean auditBean, Map<String, Object> data) {
        //字段计算结果 <字段名, 字段值>
        Map<String, Object> fieldValueMap = Collections.synchronizedMap(Maps.newHashMap());

        try {
            //====================基础数据准备-开始========================
            /*
			 * 1.基础数据准备
			 */
            long start = System.currentTimeMillis();
            Preconditions.checkNotNull(auditBean, "申请信息不存在");
            long end1 = System.currentTimeMillis();
            //====================基础数据准备-结束========================
			
			/*
			 * 2.三方指标并行计算
			 */
            thirdCalcList.parallelStream().forEach(item -> item.calc(fieldValueMap, auditBean, data));
            long end2 = System.currentTimeMillis();
            log.info(String.format("三方字段并行计算完毕, creditId=%s, readyUse=%s, calcUse=%s", auditBean.getCreditId(), (end1 - start), (end2 - end1)));

        } catch (Exception e) {
            //设置为"数据缺失"
            log.error("基础数据准备异常,creditId=" + auditBean.getCreditId() + ", " + e.getMessage(), e);
        }
        return fieldValueMap;
    }


}
