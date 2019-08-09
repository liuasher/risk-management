package com.wjl.mq;

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
@RabbitListener(queues = QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1, containerFactory = "rabbitListenerContainerFactory")
public class RiskAuditListenerStep1 extends AbstractAuditListener {

    private static final String stepName = "审核步骤[step1新客规则]";

    @Autowired
    private MqSender mqSender;

    @Autowired
    private LocalService localService;



    private String nextQueue2Name = QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2;

    @Bean("step1Queue")
    public Queue step1Queue() {
        return new Queue(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1);
    }

    /**
     * 审核-step1新客规则（Step1审核）
     *
     * @param auditBean
     */
    @RabbitHandler
    public void process(@Payload AuditBean auditBean) {
        if (auditBean == null) {
            log.error(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1 + ", 队列数据为null ");
            return;
        }
        Long creditId = auditBean.getCreditId();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1 + " start, creditId=%s ", auditBean.getCreditId()));
        long start = System.currentTimeMillis();
        if (creditId == null) {
            log.error("【审核步骤[step1新客规则]审核，申请记录 creditId=null】");
            return;
        }

        try {
            AuditResult result = auditProcess(auditBean);
            if (Constant.AutoAuditResult_OK .equals( result.getAuditResult())) {
                auditBean.setFieldData(result.getFieldData());

                    mqSender.send(nextQueue2Name, auditBean);

            }
        } catch (Exception e) {
            log.error(String.format("【审核步骤[step1新客规则]审核,申请记录 creditId=%s 】执行异常:%s", creditId, e.getMessage()), e);
        } finally {
            TxnContext.getCurrentContext().unset();
        }
        long end = System.currentTimeMillis();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP1 + " end, creditId=%s, use=%s", creditId, (end - start)));
    }

    @Override
    public AuditResult doAudit(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        return localService.step1TxnProcess(auditBean, data, fieldData);
    }

    @Override
    public Map<String, Object> isReadyData(AuditBean auditBean) {
        return localService.step1IsReadyData(auditBean);
    }

    @Override
    public boolean isDataCompleted(AuditBean auditBean, Map<String, Object> data) {
        return localService.step1IsReadyDataComplete(auditBean, data);
    }

    @Override
    String getStepName() {
        return stepName;
    }

}
