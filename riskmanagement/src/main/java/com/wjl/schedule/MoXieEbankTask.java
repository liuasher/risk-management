package com.wjl.schedule;

import com.wjl.model.MoXieEbank;
import com.wjl.model.mongo.EbankData;
import com.wjl.model.mongo.EbankReport;
import com.wjl.mongo.EbankDataRepository;
import com.wjl.mongo.EbankReportRepository;
import com.wjl.service.MoXieEbankService;
import com.wjl.service.thirdservice.MoxieBankBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时器网银存model字段
 *
 * @author zhaojinping
 */
@Slf4j
@Component
public class MoXieEbankTask {

    @Autowired
    private MoXieEbankService moXieEbankService;
    @Autowired
    private EbankDataRepository ebankDataRepository;
    @Autowired
    private EbankReportRepository ebankReportRepository;
    @Autowired
    private MoxieBankBillService moxieBankBillService;

    /**
     * 定时器将网银账单导入mysql中model
     * <p>
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void ebankBillQuery() {

        //查找未导入MongoDB的网银账单
        List<MoXieEbank> billWait = moXieEbankService.findBillWait();
        if (null != billWait) {
            for (MoXieEbank moXieEbank : billWait) {

                Long userId = moXieEbank.getUserId();
                EbankData ebankData = ebankDataRepository.findOne(moXieEbank.getReportId());

                try {
                    moxieBankBillService.saveModelEbankBill(ebankData, userId, moXieEbank.getIdentification());
                    moXieEbankService.updateMysqlStatusById(moXieEbank.getId(), 2);
                    log.info(String.format("-----魔蝎网银bill model字段成功存入mysql,userId=%s-----", userId));
                } catch (Exception e) {
                    moXieEbankService.updateMysqlStatusById(moXieEbank.getId(), 1);
                    log.error(String.format("-----魔蝎网银账单存入model失败,userId=%s-----", userId));
                }

            }
        }
    }

    /**
     * 定时器将网银报告导入mysql中model
     * <p>
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void ebankReportQuery() {
        //查找未导入MongoDB的网银报告
        List<MoXieEbank> reportWait = moXieEbankService.findReportWait();
        if (null != reportWait) {
            //通过访问魔蝎接口，拿到报告将报告存入MongoDB
            for (MoXieEbank moXieEbank : reportWait) {
                Long userId = moXieEbank.getUserId();
                EbankReport ebankReport = ebankReportRepository.findOne(moXieEbank.getReportId());
                try {
                    moxieBankBillService.saveModelEbankReport(ebankReport, userId, moXieEbank.getIdentification());
                    moXieEbankService.updateMysqlStatusById(moXieEbank.getId(), 2);
                    log.info(String.format("-----魔蝎网银report model字段成功存入mysql,userId=%s-----", userId));
                } catch (Exception e) {
                    moXieEbankService.updateMysqlStatusById(moXieEbank.getId(), 1);
                    log.error(String.format("-----魔蝎网银报告存入model失败,userId=%s-----", userId));
                }
            }
        }
    }
}
