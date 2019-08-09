package com.wjl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.mapper.ModelTaobaoReportMapper;
import com.wjl.model.mongo.TaobaoReport;
import com.wjl.model.ModelTaobaoReport;
import com.wjl.service.ModelTaobaoReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by LINJX on 2018/3/20.
 */
@Slf4j
@Service
public class ModelTaobaoReportServiceImpl implements ModelTaobaoReportService {
    @Autowired
    private ModelTaobaoReportMapper modelTaobaoReportMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taobaoMongoToMysql(TaobaoReport moXieTaobaoReport, String identification) {
        try {
            log.info("--------userId="+moXieTaobaoReport.getUserId()+",淘宝报告数据导入mysql开始---------");
            JSONObject report = moXieTaobaoReport.getReport();

            JSONObject userAndAccountBasicInfo = report.getJSONObject("basic_info").getJSONObject("user_and_account_basic_info");
            String taobaoName = userAndAccountBasicInfo.getString("taobao_name");
            String taobaoPhoneNumber = userAndAccountBasicInfo.getString("taobao_phone_number");
            JSONObject totalssets = report.getJSONObject("wealth_info").getJSONObject("totalssets");
            String huaiBeiCanUseLimit = totalssets.getString("huai_bei_can_use_limit");
            String huaiBeiLimit = totalssets.getString("huai_bei_limit");
            ModelTaobaoReport taobaoReport = new ModelTaobaoReport();
            taobaoReport.setTaobaoName(taobaoName);
            taobaoReport.setTaobaoPhoneNumber(taobaoPhoneNumber);
            taobaoReport.setHuaiBeiCanUseLimit(huaiBeiCanUseLimit);
            taobaoReport.setHuaiBeiLimit(huaiBeiLimit);
            Long applyTime1 = moXieTaobaoReport.getQueryTime();

            Date applyTime = new Date(applyTime1);
            Date createTime = new Date(System.currentTimeMillis());
            Long userId = moXieTaobaoReport.getUserId();

            Integer verifyCount = 1;
            Integer count = modelTaobaoReportMapper.findTopVerifyCountByUserId(userId);
            if(null != count){
                verifyCount = count + 1;
                modelTaobaoReportMapper.deleteByUserId(userId);
            }

            taobaoReport.setQueryTime(applyTime);
            taobaoReport.setCreateTime(createTime);
            taobaoReport.setUserId(userId);
            taobaoReport.setVerifyCount(verifyCount);
            taobaoReport.setIdentification(identification);
            modelTaobaoReportMapper.save(taobaoReport);
            log.info("--------userId="+moXieTaobaoReport.getUserId()+",淘宝报告数据导入mysql结束---------");
        } catch (Exception e) {
            log.error("userId："+moXieTaobaoReport.getUserId()+"淘宝报告数据导入失败"+e.getMessage(),e);
        }


    }


}
