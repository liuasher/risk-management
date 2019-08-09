package com.wjl.service.thirdservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.enumeration.TongDunEnum;
import com.wjl.commom.util.HttpUtils;
import com.wjl.commom.util.third.HttpUtil_Tondun;
import com.wjl.mapper.TongDunMapper;
import com.wjl.model.TongDunRequestInfo;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.TongDunBean;
import com.wjl.mongo.TongDunReportRepository;
import com.wjl.service.thirdservice.TongDunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用同盾接口，存取报告等
 *
 * @author mayue
 * @date 2018/4/2
 */
@Service
@Slf4j
public class TongDunServiceImpl implements TongDunService {

    @Autowired
    private TongDunMapper tongDunMapper;

    @Autowired
    private TongDunReportRepository tongDunReportRepository;

    @Override
    public Long audit(TongDunBean requestArgs, String submitUrl) {
        Map<String, String> param = new HashMap<>(8);
        param.put("userId", String.valueOf(requestArgs.getUserId()));
        param.put("black_box", requestArgs.getBlackBox());
        param.put("ip_address", requestArgs.getIpAddress());
        param.put("name", requestArgs.getName());
        param.put("id_number", requestArgs.getIdCard());
        param.put("mobile", requestArgs.getMobile());
        param.put("card_number", requestArgs.getCardNo());

        String retStr = null;

            retStr = HttpUtil_Tondun.doPost(submitUrl, param);

        JSONObject retObj = JSONObject.parseObject(retStr);
        if (retObj == null) {
            //失败
            log.error("同盾submit报告没有获取到");
            return null;
        }

        Boolean success = retObj.getBoolean("success");
        if (!success) {
            log.error("同盾submit失败，userId={}，report={}", requestArgs.getUserId(), retObj);
        }

        String reportId = retObj.getString("report_id");

        //请求数据入库
        TongDunRequestInfo tongDun = new TongDunRequestInfo();
        tongDun.setUserId(requestArgs.getUserId());
        tongDun.setReportId(reportId);
        tongDun.setBlackBox(requestArgs.getBlackBox());
        tongDun.setIp(requestArgs.getIpAddress());
        tongDun.setReasonCode(retObj.getString("reason_code"));
        tongDun.setReasonDesc(retObj.getString("reason_desc"));
        tongDun.setSuccess(success.toString());
        tongDun.setIdentification(requestArgs.getIdentification());
        tongDun.setCreateTime(new Date());
        tongDun.setSubmitStatus(Integer.valueOf(TongDunEnum.STATUS_SUBMIT.getCode()));
        tongDun.setSubmitTime(new Date());
        tongDunMapper.insertRequestInfo(tongDun);
        return tongDun.getId();
    }

    @Override
    public String query(TongDunBean requestArgs, String queryUrl, Long id) {
        String retStr = HttpUtil_Tondun.doGet(queryUrl, null);
        JSONObject retObj = JSONObject.parseObject(retStr);
        if (retObj == null) {
            //失败
            return null;
        }

        Boolean success = retObj.getBoolean("success");
        if (!success) {
            //失败
            log.error("reportId={}, code={}, desc={}", retObj.getString("report_id"), retObj.getString("reason_code"), retObj.getString("reason_desc"));
            return null;
        }

        //报表落库
        TongDunReport report = new TongDunReport();
        report.setUserId(requestArgs.getUserId());
        report.setQueryTime(System.currentTimeMillis());
        report.setBlackBox(requestArgs.getBlackBox());
        report.setReportId(retObj.getString("report_id"));
        report.setFinalScore(retObj.getLong("final_score"));
        report.setFinalDecision(retObj.getString("final_decision"));
        report.setSuccess("success");
        report.setReport(retObj);
        TongDunReport tongDunReport = tongDunReportRepository.save(report);

        String verifyId = tongDunReport.getId();
        TongDunRequestInfo tongDun = tongDunMapper.findById(id);
        tongDun.setVerifyId(verifyId);
        tongDun.setQueryTime(new Date());
        tongDun.setSubmitStatus(Integer.valueOf(TongDunEnum.STATUS_QUERY.getCode()));
        tongDun.setUpdateTime(new Date());
        if (tongDunMapper.updateRequestInfo(tongDun)) {
            return verifyId;
        }
        return null;
    }

    @Override
    public TongDunRequestInfo findById(Long id) {
        return tongDunMapper.findById(id);
    }

    @Override
    public TongDunReport get(String verifyId) {
        return tongDunReportRepository.findById(verifyId);
    }
}
