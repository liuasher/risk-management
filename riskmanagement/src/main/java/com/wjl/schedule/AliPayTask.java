package com.wjl.schedule;

import com.wjl.model.MoxieAliPay;
import com.wjl.model.mongo.MoXieAliPayBillData;
import com.wjl.model.mongo.MoXieAliPayReportData;
import com.wjl.mongo.MoxieAliPayBillDataRepository;
import com.wjl.mongo.MoxieAliPayReportDataRepository;
import com.wjl.service.AliPayMongoToMysqlService;
import com.wjl.service.thirdservice.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
/**
 * 定时器取支付宝报告
 *
 * @author mayue
 * @date 2018/4/2
 */
public class AliPayTask {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private MoxieAliPayBillDataRepository moxieAliPayBillDataRepository;
    @Autowired
    private MoxieAliPayReportDataRepository moxieAliPayReportDataRepository;
    @Autowired
    private AliPayMongoToMysqlService aliPayMongoToMysqlService;

    /**
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     * 支付宝data导入mysql
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void queryData() throws Exception {
        List<MoxieAliPay> payBill = aliPayService.findBillWait();
        if (payBill != null && payBill.size() > 0) {
            for (MoxieAliPay moxieAliPay : payBill) {
                MoXieAliPayBillData moXieAliPayBillData = moxieAliPayBillDataRepository.findById(moxieAliPay.getReportId());

                try {
                    aliPayMongoToMysqlService.saveModelPayData(moXieAliPayBillData, moxieAliPay.getIdentification());
                } catch (Exception e) {
                    aliPayService.updateMysqlStatus(moxieAliPay.getId(), 1);
                    log.error("-----支付宝Bill导入Mysql失败,Exception={}-----", e);
                }
                aliPayService.updateMysqlStatus(moxieAliPay.getId(), 2);
                log.info("-----支付宝Bill导入Mysql成功,moXieAliPayId={},userId={}-----", moxieAliPay.getId(), moxieAliPay.getUserId());
            }
        }
    }

    /**
     * 支付宝report导入mysql
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void queryReport() throws Exception {
        List<MoxieAliPay> payReport = aliPayService.findReportWait();
        if (payReport != null && payReport.size() > 0) {
            for (MoxieAliPay moxieAlibabaPay : payReport) {
                MoXieAliPayReportData moXieAliPayReportData = moxieAliPayReportDataRepository.findById(moxieAlibabaPay.getReportId());
                try {
                    aliPayMongoToMysqlService.saveModelPayReport(moXieAliPayReportData, moxieAlibabaPay.getIdentification());
                } catch (Exception e) {
                    aliPayService.updateMysqlStatus(moxieAlibabaPay.getId(), 1);
                    log.error("-----支付宝Report导入Mysql失败,Exception={}-----", e.getMessage());
                }
                aliPayService.updateMysqlStatus(moxieAlibabaPay.getId(), 2);
                log.info("-----支付宝Report导入Mysql成功,moxie_alibaba_pay.id={},userId={}-----", moxieAlibabaPay.getId(), moxieAlibabaPay.getUserId());
            }
        }
    }
}
