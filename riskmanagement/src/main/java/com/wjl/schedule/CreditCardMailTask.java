package com.wjl.schedule;

import com.wjl.model.MoxieCreditCardMail;
import com.wjl.model.mongo.CreditCardMailBillData;
import com.wjl.model.mongo.CreditCardMailReportData;
import com.wjl.mongo.CreditCardMailBillDataRepository;
import com.wjl.mongo.CreditCardMailReportDataRepository;
import com.wjl.service.MoXieCreditCardMailService;
import com.wjl.service.ModelCreditCardMailBillService;
import com.wjl.service.ModelCreditCardMailReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 保存信用卡邮箱到mongo定时器
 *
 * @author LINJX
 */
@Slf4j
@Component
public class CreditCardMailTask {
    @Autowired
    private MoXieCreditCardMailService moXieCreditCardMailService;

    @Autowired
    private CreditCardMailBillDataRepository creditCardMailBillDataRepository;

    @Autowired
    private CreditCardMailReportDataRepository creditCardMailReportDataRepository;

    @Autowired
    private ModelCreditCardMailReportService modelCreditCardMailReportService;


    @Autowired
    private ModelCreditCardMailBillService modelCreditCardMailBillService;


    /**
     * 系统启动后60秒后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void billToMysql() {
        List<MoxieCreditCardMail> billWait = moXieCreditCardMailService.findBillWait();
        if (null != billWait && billWait.size()>0) {
            for (MoxieCreditCardMail moxieCreditCardMail : billWait) {
                CreditCardMailBillData byId = creditCardMailBillDataRepository.findById(moxieCreditCardMail.getReportId());
                try {
                    modelCreditCardMailBillService.saveModelCreaditCardBill(byId, moxieCreditCardMail.getIdentification(), moxieCreditCardMail.getUserId());
                } catch (Exception e) {
                    moXieCreditCardMailService.updateMysqlStatus(moxieCreditCardMail.getId(), 1);
                    log.error(String.format("userId=%s,Mysql存入用卡邮箱bill失败", moxieCreditCardMail.getUserId()), e);
                    return;
                }
                moXieCreditCardMailService.updateMysqlStatus(moxieCreditCardMail.getId(), 2);
                log.error(String.format("userId=%s,Mysql存入信用卡邮箱bill成功", moxieCreditCardMail.getUserId()));
            }
        }
    }

    /**
     * 系统启动后60秒后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void reportToMysql() {
        List<MoxieCreditCardMail> reportWait = moXieCreditCardMailService.findReportWait();
        if (null != reportWait && reportWait.size()>0) {
            for (MoxieCreditCardMail moxieCreditCardMail : reportWait) {
                CreditCardMailReportData byId = creditCardMailReportDataRepository.findById(moxieCreditCardMail.getReportId());
                try {
                    modelCreditCardMailReportService.saveByJsonArray(byId, moxieCreditCardMail.getIdentification(), moxieCreditCardMail.getUserId());
                } catch (Exception e) {
                    moXieCreditCardMailService.updateMysqlStatus(moxieCreditCardMail.getId(), 1);
                    log.error(String.format("userId=%s,Mysql存入用卡邮箱report失败", moxieCreditCardMail.getUserId()), e);
                    return;
                }
                moXieCreditCardMailService.updateMysqlStatus(moxieCreditCardMail.getId(), 2);
                log.error(String.format("userId=%s,Mysql存入用卡邮箱report成功", moxieCreditCardMail.getUserId()));
            }
        }
    }
}
