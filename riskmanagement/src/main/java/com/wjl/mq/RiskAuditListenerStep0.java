package com.wjl.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.mq.MqSender;
import com.wjl.commom.util.TxnContext;
import com.wjl.model.AuditResult;
import com.wjl.model.constant.Constant;
import com.wjl.model.constant.QueueDict;
import com.wjl.model.mq.AuditBean;
import com.wjl.mq.service.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * 审核 step0公共规则
 * @author hqh
 */
@Slf4j
@Configuration
@RabbitListener(queues = QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0, containerFactory = "rabbitListenerContainerFactory")
public class RiskAuditListenerStep0 extends AbstractAuditListener {

    private static final String stepName = "审核步骤[step0公共规则]";

    @Autowired
    private MqSender mqSender;

    @Autowired
    private LocalService localService;



    private String nextQueue1Name = QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1;

    @Bean("step0Queue")
    public Queue step0Queue() {
        return new Queue(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0);
    }

    /**
     * 审核-step0公共规则（Step0审核）
     *
     * @param audit
     */
    @RabbitHandler
    public void process(@Payload String audit) {
        if (audit == null) {
            log.error(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0 + ", 队列数据为null ");
            return;
        }
        AuditBean auditBean = JSON.parseObject(audit, AuditBean.class);
        Long creditId = auditBean.getCreditId();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0 + " start, creditId=%s ", auditBean.getCreditId()));
        long start = System.currentTimeMillis();
        if (creditId == null) {
            log.error("【审核步骤[step0公共规则]审核，申请记录 creditId=null】");
            return;
        }

        try {
            AuditResult result = auditProcess(auditBean);
            if (Constant.AutoAuditResult_OK .equals(result.getAuditResult())) {
                auditBean.setFieldData(result.getFieldData());

                    mqSender.send(nextQueue1Name, auditBean);

            }
        } catch (Exception e) {
            log.error(String.format("【审核步骤[step0公共规则]审核,申请记录 creditId=%s 】执行异常:%s", creditId, e.getMessage()), e);
        } finally {
            TxnContext.getCurrentContext().unset();
        }
        long end = System.currentTimeMillis();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0 + " end, creditId=%s, use=%s", creditId, (end - start)));
    }

    @Override
    public AuditResult doAudit(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        return localService.step0TxnProcess(auditBean, data, fieldData);
    }

    @Override
    public Map<String, Object> isReadyData(AuditBean auditBean) {
        return localService.step0IsReadyData(auditBean);
    }

    @Override
    public boolean isDataCompleted(AuditBean auditBean, Map<String, Object> data) {
        return localService.step0IsReadyDataComplete(auditBean, data);
    }

    @Override
    String getStepName() {
        return stepName;
    }

}
