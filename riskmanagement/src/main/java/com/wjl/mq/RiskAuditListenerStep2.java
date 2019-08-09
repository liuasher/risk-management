package com.wjl.mq;

import com.wjl.commom.util.TxnContext;
import com.wjl.model.AuditResult;
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
@RabbitListener(queues = QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2, containerFactory = "rabbitListenerContainerFactory")
public class RiskAuditListenerStep2 extends AbstractAuditListener {

    private static final String stepName = "审核步骤[step2新客评分卡]";


    @Autowired
    private LocalService localService;




    @Bean("step2Queue")
    public Queue step2Queue() {
        return new Queue(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2);
    }

    /**
     * 审核-step2新客评分卡（Step2审核）
     *
     * @param auditBean
     */
    @RabbitHandler
    public void process(@Payload AuditBean auditBean) {
        if (auditBean == null) {
            log.error(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2 + ", 队列数据为null ");
            return;
        }
        Long creditId = auditBean.getCreditId();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2 + " start, creditId=%s ", auditBean.getCreditId()));
        long start = System.currentTimeMillis();
        if (creditId == null) {
            log.error("【审核步骤[step2新客评分卡]审核，申请记录 creditId=null】");
            return;
        }

        try {
             auditProcess(auditBean);

        } catch (Exception e) {
            log.error(String.format("【审核步骤[step2新客评分卡]审核,申请记录 creditId=%s 】执行异常:%s", creditId, e.getMessage()), e);
        } finally {
            TxnContext.getCurrentContext().unset();
        }
        long end = System.currentTimeMillis();
        log.info(String.format(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP2 + " end, creditId=%s, use=%s", creditId, (end - start)));
    }

    @Override
    public AuditResult doAudit(AuditBean auditBean, Map<String, Object> data, Map<String, Object> fieldData) {
        return localService.step2TxnProcess(auditBean, data, fieldData);
    }

    @Override
    public Map<String, Object> isReadyData(AuditBean auditBean) {
        return localService.step2IsReadyData(auditBean);
    }

    @Override
    public boolean isDataCompleted(AuditBean auditBean, Map<String, Object> data) {
        return localService.step2IsReadyDataComplete(auditBean, data);
    }

    @Override
    String getStepName() {
        return stepName;
    }

}
