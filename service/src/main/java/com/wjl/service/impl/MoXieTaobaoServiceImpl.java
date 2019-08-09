package com.wjl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.MoxieConfiguration;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.MoXieTaobaoMapper;
import com.wjl.model.MoXieTaobao;
import com.wjl.model.mongo.TaobaoData;
import com.wjl.model.mongo.TaobaoReport;
import com.wjl.mongo.TaobaoDataRepository;
import com.wjl.mongo.TaobaoReportRepository;
import com.wjl.service.MoXieTaobaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/2
 */
@Service
@Slf4j
public class MoXieTaobaoServiceImpl implements MoXieTaobaoService {

    @Autowired
    private MoXieTaobaoMapper moXieTaobaoMapper;
    @Autowired
    private MoxieConfiguration moxieConfiguration;
    @Autowired
    private TaobaoDataRepository taobaoDataRepository;
    @Autowired
    private TaobaoReportRepository taobaoReportRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(MoXieTaobao moxieTaobao) {
        moXieTaobaoMapper.save(moxieTaobao);
    }

    @Override
    public MoXieTaobao findByUserIdAndType(Long userId, Integer type,String identification,String taskId) {
        return moXieTaobaoMapper.findByUserIdAndType(userId,type,identification,taskId);
    }

    @Override
    public List<MoXieTaobao> findBillWait() {
        return moXieTaobaoMapper.findBillWait();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateReportIdAndQueryTime(Long id, String reportId, long queryTime) {
        moXieTaobaoMapper.updateReportIdAndQueryTime(id,reportId,queryTime);
    }

    @Override
    public List<MoXieTaobao> findReportWait() {
        return moXieTaobaoMapper.findReportWait();
    }

    @Override
    public String billQuery(MoXieTaobao moXieTaobao) {

        Long id = moXieTaobao.getId();
        Long userId = moXieTaobao.getUserId();
        String taskId = moXieTaobao.getTaskId();
        String billUrl = moxieConfiguration.getTaobaoBillUrl();
        String token = moxieConfiguration.getToken();
        String bill = HttpUtils.restfulSendGet(billUrl, taskId,token);
        JSONObject jsonBill = JSONObject.parseObject(bill);
        TaobaoData taobaoData = new TaobaoData();
        taobaoData.setQueryTime(System.currentTimeMillis());
        taobaoData.setUserId(userId);
        taobaoData.setIdentification(moXieTaobao.getIdentification());
        taobaoData.setBill(jsonBill);
        taobaoDataRepository.save(taobaoData);
        updateReportIdAndQueryTime(id,taobaoData.getId(),taobaoData.getQueryTime());
        log.info(String.format("-----魔蝎淘宝账单成功存入Mongo,userId=%s-----", userId));
        return taobaoData.getId();
    }

    @Override
    public String reportQuery(MoXieTaobao moXieTaobao) {
        Long id = moXieTaobao.getId();
        Long userId = moXieTaobao.getUserId();
        String taskId = moXieTaobao.getTaskId();
        String reportUrl = moxieConfiguration.getTaobaoReportUrl();
        String token = moxieConfiguration.getToken();
        String report = HttpUtils.restfulSendGet(reportUrl, taskId,token);
        JSONObject jsonReport = JSONObject.parseObject(report);
        TaobaoReport moXieTaobaoReport = new TaobaoReport();
        moXieTaobaoReport.setQueryTime(System.currentTimeMillis());
        moXieTaobaoReport.setUserId(userId);
        moXieTaobaoReport.setIdentification(moXieTaobao.getIdentification());
        moXieTaobaoReport.setReport(jsonReport);
        taobaoReportRepository.save(moXieTaobaoReport);
        updateReportIdAndQueryTime(id,moXieTaobaoReport.getId(),moXieTaobaoReport.getQueryTime());
        log.info(String.format("-----魔蝎淘宝报告成功存入Mongo,userId=%s-----", userId));
        return moXieTaobaoReport.getId();
    }

    @Override
    public void updateMysqlStatusById(Long id, Integer status) {
        moXieTaobaoMapper.updateMysqlStatusById(id,status);
    }
}
