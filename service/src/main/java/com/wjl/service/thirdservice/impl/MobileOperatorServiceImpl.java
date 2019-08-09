package com.wjl.service.thirdservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.MoxieConfiguration;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.MobileOperatorMapper;
import com.wjl.model.MobileOperator;
import com.wjl.model.MobileOperatorDto;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.mongo.MobileOperatorBillRepository;
import com.wjl.mongo.MobileOperatorReportRepository;
import com.wjl.service.thirdservice.MobileOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @author hqh
 */
@Slf4j
@Service
public class MobileOperatorServiceImpl implements MobileOperatorService {
    @Autowired
    private MobileOperatorBillRepository mobileOperatorBillRepository;

    @Autowired
    private MobileOperatorReportRepository mobileOperatorReportRepository;

    @Autowired
    private MobileOperatorMapper mobileOperatorMapper;

    @Autowired
    private MoxieConfiguration moxieConfiguration;



    @Override
    public Response getBill(@RequestBody RequestArgs requestArgs){

        MobileOperatorBill mobileOperatorBill = mobileOperatorBillRepository.findById(requestArgs.getBillId());
        JSONObject report = mobileOperatorBill.getReport();
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);

    }
    @Override
    public Response getReport(@RequestBody RequestArgs requestArgs){

        MobileOperatorReport mobileOperatorReport = mobileOperatorReportRepository.findById(requestArgs.getReportId());
        JSONObject report = mobileOperatorReport.getReport();
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);

    }

    @Override
    public void updateReportId(String reportId, Date queryTime, Long id) {
        mobileOperatorMapper.updateReportId(reportId,queryTime,id);
    }

    @Override
    public String billQuery(MobileOperator mobileOperator) {

            String yysUrl = moxieConfiguration.getYysUrl();
            String token = moxieConfiguration.getToken();
            yysUrl = yysUrl + mobileOperator.getMobile() + "/jxreport";
            String params = "task_id=%s";
            params = String.format(params,mobileOperator.getTaskId());
            String report = HttpUtils.sendMoXieGet(yysUrl, params, token);
            JSONObject bill = JSONObject.parseObject(report);
            if (bill == null) {
                log.error(String.format("查询运营商bill完毕-失败,phone=%s", mobileOperator.getMobile()));
                return null;
            }
            MobileOperatorBill mobileOperatorBill = new MobileOperatorBill();
            mobileOperatorBill.setUserId(mobileOperator.getUserId());
            mobileOperatorBill.setPhone(mobileOperator.getMobile());
            mobileOperatorBill.setReport(bill);
            mobileOperatorBill.setQueryTime(System.currentTimeMillis());
            mobileOperatorBill.setIdentification(mobileOperator.getIdentification());

            mobileOperatorBillRepository.save(mobileOperatorBill);


            updateReportId(mobileOperatorBill.getId(), new Date(mobileOperatorBill.getQueryTime()),mobileOperator.getId());
            log.info(String.format("查询运营商bill完毕-成功,phone=%s", mobileOperator.getMobile()));
            return mobileOperatorBill.getId();

    }

    @Override
    public String reportQuery(MobileOperator mobileOperator) {
            String token = moxieConfiguration.getToken();
            String yysUrl = moxieConfiguration.getYysUrl();
            yysUrl = yysUrl + mobileOperator.getMobile() + "/jxdata";
            String params = "task_id=%s";
            params = String.format(params, mobileOperator.getTaskId());
            String report = HttpUtils.sendMoXieGet(yysUrl, params, token);
            JSONObject jsonReport = JSONObject.parseObject(report);
            if (jsonReport == null) {
                log.error(String.format("查询运营商report完毕-失败,phone=%s", mobileOperator.getMobile()));
                return null;
            }
            MobileOperatorReport mobileOperatorReport = new MobileOperatorReport();
            mobileOperatorReport.setUserId(mobileOperator.getUserId());
            mobileOperatorReport.setReport(jsonReport);
            mobileOperatorReport.setQueryTime(System.currentTimeMillis());
            mobileOperatorReport.setIdentification(mobileOperator.getIdentification());
            mobileOperatorReportRepository.save(mobileOperatorReport);


            updateReportId(mobileOperatorReport.getId(), new Date(mobileOperatorReport.getQueryTime()), mobileOperator.getId());
            log.info(String.format("查询运营商report完毕-成功,phone=%s", mobileOperator.getMobile()));
            return mobileOperatorReport.getId();
        }

    @Override
    public void updateMysqlStatus(Long id, Integer mysqlStatus) {
        mobileOperatorMapper.updateMysqlStatus(id,mysqlStatus);
    }


}
