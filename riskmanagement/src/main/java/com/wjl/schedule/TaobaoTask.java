package com.wjl.schedule;

import com.wjl.model.MoXieTaobao;
import com.wjl.model.mongo.TaobaoData;
import com.wjl.model.mongo.TaobaoReport;
import com.wjl.mongo.TaobaoDataRepository;
import com.wjl.mongo.TaobaoReportRepository;
import com.wjl.service.MoXieTaobaoService;
import com.wjl.service.ModelTaobaoDataService;
import com.wjl.service.ModelTaobaoReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaojinping
 */
@Slf4j
@Component
public class TaobaoTask {


    @Autowired
    private MoXieTaobaoService moXieTaobaoService;

    @Autowired
    private TaobaoDataRepository taobaoDataRepository;

    @Autowired
    private TaobaoReportRepository taobaoReportRepository;

    @Autowired
    private ModelTaobaoDataService modelTaobaoDataService;

    @Autowired
    private ModelTaobaoReportService modelTaobaoReportService;

    /**
     * 定时器将淘宝账单导入mysql中model
     * <p>
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void taobaoBillQuery() {

        //查找未存入MongoDB的淘宝账单
        List<MoXieTaobao> billWait = moXieTaobaoService.findBillWait();
        if (null != billWait) {
            //调魔蝎接口将淘宝账单存入MongoDB，同时更新mysql表的状态
            for (MoXieTaobao moXieTaobao : billWait) {

                Long userId = moXieTaobao.getUserId();

                TaobaoData moXieTaobaoData = taobaoDataRepository.findOne(moXieTaobao.getReportId());

                try {
                    modelTaobaoDataService.taobaoDataToMySql(moXieTaobaoData, moXieTaobao.getIdentification());
                    moXieTaobaoService.updateMysqlStatusById(moXieTaobao.getId(), 2);
                    log.info(String.format("-----魔蝎淘宝账单成功存入Mysql,userId=%s-----", userId));
                } catch (Exception e) {
                    moXieTaobaoService.updateMysqlStatusById(moXieTaobao.getId(), 1);
                    log.error("-----userId=" + userId + ",淘宝DATA导入Mysql失败-----", e);
                }

            }
        }
    }

    /**
     * 定时器将淘宝报告导入mysql中model
     * <p>
     * 系统启动后60s后首次执行<br>
     * 当前任务执行完毕后5分钟后执行下一次任务<br>
     */
    @Scheduled(initialDelay = 60 * 1000, fixedDelay = 60 * 5000)
    public void taobaoReportQuery() {
        List<MoXieTaobao> reportWait = moXieTaobaoService.findReportWait();
        if (null != reportWait) {
            for (MoXieTaobao moXieTaobao : reportWait) {
                Long userId = moXieTaobao.getUserId();
                TaobaoReport moXieTaobaoReport = taobaoReportRepository.findOne(moXieTaobao.getReportId());
                try {
                    modelTaobaoReportService.taobaoMongoToMysql(moXieTaobaoReport, moXieTaobao.getIdentification());
                    moXieTaobaoService.updateMysqlStatusById(moXieTaobao.getId(), 2);
                    log.info(String.format("-----魔蝎淘宝报告关键字段成功存入mysql,userId=%s-----", userId));
                } catch (Exception e) {
                    moXieTaobaoService.updateMysqlStatusById(moXieTaobao.getId(), 1);
                    log.error("-----userId=" + userId + ",淘宝REPORT导入Mysql失败-----", e);
                }


            }
        }
    }
}
