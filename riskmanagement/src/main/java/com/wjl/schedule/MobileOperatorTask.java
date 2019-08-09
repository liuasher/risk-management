package com.wjl.schedule;

import com.wjl.mapper.MobileOperatorMapper;
import com.wjl.model.MobileOperator;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.mongo.MobileOperatorBillRepository;
import com.wjl.mongo.MobileOperatorReportRepository;
import com.wjl.service.ModelMobileOperatorCellBehaviorService;
import com.wjl.service.ModelMobileOperatorReportService;
import com.wjl.service.thirdservice.MobileOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hqh
 */
@Slf4j
@Component
public class MobileOperatorTask {
    @Autowired
    private MobileOperatorMapper mobileOperatorMapper;

    @Autowired
    private MobileOperatorBillRepository mobileOperatorBillRepository;

    @Autowired
    private MobileOperatorReportRepository mobileOperatorReportRepository;

    @Autowired
    private ModelMobileOperatorCellBehaviorService modelMobileOperatorCellBehaviorService;

    @Autowired
    private ModelMobileOperatorReportService modelMobileOperatorReportService;

    @Autowired
    private MobileOperatorService mobileOperatorService;

    /**
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void billToMysql() {

        List<MobileOperator> billWait = mobileOperatorMapper.findBillWait();
        if (billWait.size() == 0) {
            return;
        }
        for (MobileOperator mobileOperator : billWait) {
            MobileOperatorBill mobileOperatorBill = mobileOperatorBillRepository.findById(mobileOperator.getReportId());

            try {
                modelMobileOperatorCellBehaviorService.save(mobileOperatorBill, mobileOperator.getIdentification(), mobileOperator.getUserId());
            } catch (Exception e) {
                mobileOperatorService.updateMysqlStatus(mobileOperator.getId(),1);
                log.error(String.format("userId=%s,Mysql存入运营商bill失败", mobileOperator.getUserId()), e);
            }
            mobileOperatorService.updateMysqlStatus(mobileOperator.getId(),2);
            log.info(String.format("Mysql存入运营商bill完毕-成功,userId=%s", mobileOperator.getUserId()));
        }


    }

    /**
     * 系统启动后60m后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void reportToMysql() {

        List<MobileOperator> reportWait = mobileOperatorMapper.findReportWait();
        if (reportWait.size() == 0) {
            return;
        }
        for (MobileOperator mobileOperator : reportWait) {
            MobileOperatorReport mobileOperatorReport = mobileOperatorReportRepository.findById(mobileOperator.getReportId());

            try {
                modelMobileOperatorReportService.save(mobileOperatorReport, mobileOperator.getIdentification());
            } catch (Exception e) {
                mobileOperatorService.updateMysqlStatus(mobileOperator.getId(),1);
                log.error(String.format("userId=%s,Mysql存入运营商report失败", mobileOperator.getUserId()), e);
            }
            mobileOperatorService.updateMysqlStatus(mobileOperator.getId(),2);
            log.info(String.format("Mysql存入运营商report完毕-成功,userId=%s", mobileOperator.getUserId()));
        }

   }
}
