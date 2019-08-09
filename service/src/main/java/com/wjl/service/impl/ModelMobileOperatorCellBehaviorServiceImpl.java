package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.mapper.ModelMobileOperatorCellBehaviorMapper;
import com.wjl.model.MobileOperatorDto;
import com.wjl.model.ModelMobileOperatorCellBehavior;
import com.wjl.model.mongo.MobileOperatorBill;
import com.wjl.service.ModelMobileOperatorCellBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * ModelMobileOperator保存
 * @author hqh
 */
@Service
public class ModelMobileOperatorCellBehaviorServiceImpl implements ModelMobileOperatorCellBehaviorService {

    @Autowired
    ModelMobileOperatorCellBehaviorMapper modelMobileOperatorCellBehaviorMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(MobileOperatorBill mobileOperatorBill, String identification, Long userId) {
        JSONObject report = mobileOperatorBill.getReport();
        Long queryTime = mobileOperatorBill.getQueryTime();
        JSONObject jsonReport = report.getJSONObject("report");
        String rptId = jsonReport.getString("rpt_id");

        List<ModelMobileOperatorCellBehavior> byUserId = modelMobileOperatorCellBehaviorMapper.findByUserId(userId);
        Integer verifyCount = 1;
        if (byUserId.size() > 0) {
            verifyCount = byUserId.get(0).getVerifyCount() + 1;
            modelMobileOperatorCellBehaviorMapper.delete(userId);
        }

        JSONArray cellBehavior = report.getJSONArray("cell_behavior");
        if (cellBehavior != null) {
            for (int a = 0; a < cellBehavior.size(); a++) {
                JSONObject jsoncellBehavior = cellBehavior.getJSONObject(a);
                JSONArray behavior = jsoncellBehavior.getJSONArray("behavior");
                for (int b = 0; b < behavior.size(); b++) {
                    JSONObject jsonBehavior = behavior.getJSONObject(b);
                    String cellOperatorZh = jsonBehavior.getString("cell_operator_zh");
                    Float netFlow = jsonBehavior.getFloatValue("net_flow");
                    Float callOutTime = jsonBehavior.getFloatValue("call_out_time");
                    String cellOperator = jsonBehavior.getString("cell_operator");
                    Integer callInCnt = jsonBehavior.getInteger("call_in_cnt");
                    String cellPhoneNum = jsonBehavior.getString("cell_phone_num");
                    Integer smsCnt = jsonBehavior.getInteger("sms_cnt");
                    String cellLoc = jsonBehavior.getString("cell_loc");
                    Integer callCnt = jsonBehavior.getInteger("call_cnt");
                    Float totalAmount = jsonBehavior.getFloatValue("total_amount");
                    String cellMth = jsonBehavior.getString("cell_mth");
                    Integer callOutCnt = jsonBehavior.getInteger("call_out_cnt");
                    Float callInTime = jsonBehavior.getFloatValue("call_in_time");

                    ModelMobileOperatorCellBehavior modelMobileOperatorCellBehavior = new ModelMobileOperatorCellBehavior();
                    modelMobileOperatorCellBehavior.setUserId(userId);
                    modelMobileOperatorCellBehavior.setCellOperatorZh(cellOperatorZh);
                    modelMobileOperatorCellBehavior.setCellOperator(cellOperator);
                    modelMobileOperatorCellBehavior.setNetFlow(netFlow);
                    modelMobileOperatorCellBehavior.setCallOutTime(callOutTime);
                    modelMobileOperatorCellBehavior.setCallInCnt(callInCnt);
                    modelMobileOperatorCellBehavior.setCellPhoneNum(cellPhoneNum);
                    modelMobileOperatorCellBehavior.setSmsCnt(smsCnt);
                    modelMobileOperatorCellBehavior.setCellLoc(cellLoc);
                    modelMobileOperatorCellBehavior.setCallCnt(callCnt);
                    modelMobileOperatorCellBehavior.setTotalAmount(totalAmount);
                    modelMobileOperatorCellBehavior.setCellMth(cellMth);
                    modelMobileOperatorCellBehavior.setCallOutCnt(callOutCnt);
                    modelMobileOperatorCellBehavior.setCallInTime(callInTime);
                    modelMobileOperatorCellBehavior.setRptId(rptId);
                    modelMobileOperatorCellBehavior.setIdentification(identification);
                    modelMobileOperatorCellBehavior.setVerifyCount(verifyCount);
                    modelMobileOperatorCellBehavior.setQueryTime(new Date(queryTime));
                    modelMobileOperatorCellBehavior.setCreateTime(new Date());
                    modelMobileOperatorCellBehaviorMapper.save(modelMobileOperatorCellBehavior);

                }
            }

        }

    }

    @Override
    public List<ModelMobileOperatorCellBehavior> findModelMobileOperatorTotal(Long userId,String identification) {
       return modelMobileOperatorCellBehaviorMapper.findModelMobileOperatorTotal(userId, identification);
    }
}


