package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.MoxieConfiguration;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.MoXieEbankMapper;
import com.wjl.model.MoXieEbank;
import com.wjl.model.mongo.EbankData;
import com.wjl.model.mongo.EbankReport;
import com.wjl.mongo.EbankDataRepository;
import com.wjl.mongo.EbankReportRepository;
import com.wjl.service.MoXieEbankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 魔蝎网银Service
 * @date 2018/4/2
 */
@Service
@Slf4j
public class MoXieEbankServiceImpl implements MoXieEbankService {

    @Autowired
    MoXieEbankMapper moXieEbankMapper;
    @Autowired
    private MoxieConfiguration moxieConfiguration;
    @Autowired
    private EbankDataRepository ebankDataRepository;
    @Autowired
    private EbankReportRepository ebankReportRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(MoXieEbank moXieEbank) {
        moXieEbankMapper.save(moXieEbank);
    }

    @Override
    public MoXieEbank findByUserIdAndType(Long userId, Integer type,String identification,String taskId) {
        return moXieEbankMapper.findByUserIdAndType(userId,type,identification,taskId);
    }

    @Override
    public List<MoXieEbank> findBillWait() {
        return moXieEbankMapper.findBillWait();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateReportIdAndQueryTime(Long id, String reportId, long queryTime) {
        moXieEbankMapper.updateReportIdAndQueryTime(id,reportId,queryTime);
    }

    @Override
    public List<MoXieEbank> findReportWait() {
        return moXieEbankMapper.findReportWait();
    }

    @Override
    public String billQuery(MoXieEbank moXieEbank) {

        Long id = moXieEbank.getId();
        Long userId = moXieEbank.getUserId();
        String taskId = moXieEbank.getTaskId();
        String billUrl = moxieConfiguration.getEbankBillUrl();
        String token = moxieConfiguration.getToken();
        String params = String.format("task_id=%s",taskId);
        String bill = HttpUtils.sendMoXieGet(billUrl, params, token).toString();
        JSONArray jsonBill = JSONObject.parseArray(bill);
        EbankData ebankData = new EbankData();
        ebankData.setQueryTime(System.currentTimeMillis());
        ebankData.setUserId(userId);
        ebankData.setIdentification(moXieEbank.getIdentification());
        ebankData.setBill(jsonBill);
        //保存至MongoDB
        ebankDataRepository.save(ebankData);
        //更新mysql中的账单id和查询时间
        updateReportIdAndQueryTime(id, ebankData.getId(), ebankData.getQueryTime());
        log.info(String.format("-----魔蝎网银账单成功存入Mongo,userId=%s-----", userId));
        return ebankData.getId();

    }

    @Override
    public String reportQuery(MoXieEbank moXieEbank) {

        Long id = moXieEbank.getId();
        Long userId = moXieEbank.getUserId();
        String taskId = moXieEbank.getTaskId();
        String reportUrl = moxieConfiguration.getEbankReportUrl();
        String token = moxieConfiguration.getToken();
        String params = String.format("task_id=%s",taskId);
        String report = HttpUtils.sendMoXieGet(reportUrl, params, token).toString();
        JSONObject jsonReport = JSONObject.parseObject(report);
        EbankReport ebankReport = new EbankReport();
        ebankReport.setQueryTime(System.currentTimeMillis());
        ebankReport.setUserId(userId);
        ebankReport.setIdentification(moXieEbank.getIdentification());
        ebankReport.setReport(jsonReport);
        //保存至MongoDB
        ebankReportRepository.save(ebankReport);
        //更新mysql中的报告id和查询时间
        updateReportIdAndQueryTime(id, ebankReport.getId(), ebankReport.getQueryTime());
        log.info(String.format("-----魔蝎网银报告成功存入Mongo,userId=%s-----", userId));
        return ebankReport.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMysqlStatusById(Long id, Integer status) {
        moXieEbankMapper.updateMysqlStatusById(id,status);
    }
}
