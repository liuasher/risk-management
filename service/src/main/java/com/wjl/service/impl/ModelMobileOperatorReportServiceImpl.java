package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.mapper.ModelMobileOperatorCallsMapper;
import com.wjl.mapper.ModelMobileOperatorNetsMapper;
import com.wjl.model.ModelMobileOperatorCalls;
import com.wjl.model.ModelMobileOperatorNets;
import com.wjl.model.mongo.MobileOperatorReport;
import com.wjl.service.ModelMobileOperatorReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author  hqh
 */
@Service
public class ModelMobileOperatorReportServiceImpl implements ModelMobileOperatorReportService {

    @Autowired
    private ModelMobileOperatorCallsMapper modelMobileOperatorCallsMapper;

    @Autowired
    private ModelMobileOperatorNetsMapper modelMobileOperatorNetsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(MobileOperatorReport mobileOperatorReport, String identification){

        JSONObject rawData = mobileOperatorReport.getReport();

        Long userId = mobileOperatorReport.getUserId();
        Long queryTime = mobileOperatorReport.getQueryTime();
        List<ModelMobileOperatorCalls> byUserId = modelMobileOperatorCallsMapper.findByUserId(userId);
        List<ModelMobileOperatorNets> byUserId1 = modelMobileOperatorNetsMapper.findByUserId(userId);
        Integer verifyCount = 1;
        Integer verifyCount1 = 1;
        if(byUserId.size()>0){
            verifyCount = byUserId.get(0).getVerifyCount()+1;
            modelMobileOperatorCallsMapper.delete(userId);
        }
        if (byUserId1.size()>0){
            verifyCount1 = byUserId1.get(0).getVerifyCount()+1;
            modelMobileOperatorNetsMapper.delete(userId);
        }
        JSONArray transactions = rawData.getJSONArray("transactions");
        for(int i = 0;i<transactions.size();i++){
            JSONObject transaction = transactions.getJSONObject(i);
            //保存聚信立原始数据calls
            JSONArray calls = transaction.getJSONArray("calls");
            for (int c = 0; c < calls.size(); c++) {
                JSONObject call = calls.getJSONObject(c);
                Date startTime = call.getDate("start_time");
                Date updateTime = call.getDate("update_time");
                Integer useTime = call.getInteger("use_time");
                Double subtotal = call.getDouble("subtotal");
                String place = call.getString("place");
                String initType = call.getString("init_type");
                String callType = call.getString("call_type");
                String otherCellPhone = call.getString("other_cell_phone");
                String cellPhone = call.getString("cell_phone");
                ModelMobileOperatorCalls modelMobileOperatorCalls = new ModelMobileOperatorCalls();
                modelMobileOperatorCalls.setIdentification(identification);
                modelMobileOperatorCalls.setCallType(callType);
                modelMobileOperatorCalls.setStartTime(startTime);
                modelMobileOperatorCalls.setUpdateTime(updateTime);
                modelMobileOperatorCalls.setUseTime(useTime);
                modelMobileOperatorCalls.setSubtotal(subtotal);
                modelMobileOperatorCalls.setPlace(place);
                modelMobileOperatorCalls.setInitType(initType);
                modelMobileOperatorCalls.setOtherCellPhone(otherCellPhone);
                modelMobileOperatorCalls.setCellPhone(cellPhone);
                modelMobileOperatorCalls.setUserId(userId);
                modelMobileOperatorCalls.setQueryTime(new Date(queryTime));
                modelMobileOperatorCalls.setVerifyCount(verifyCount);
                modelMobileOperatorCalls.setCreateTime(new Date());
                modelMobileOperatorCallsMapper.save(modelMobileOperatorCalls);
            }
            JSONArray nets = transaction.getJSONArray("nets");
            for (int d = 0; d < nets.size(); d++) {
                JSONObject net = nets.getJSONObject(d);
                Date startTime = net.getDate("start_time");
                Date updateTime = net.getDate("update_time");
                Integer useTime = net.getInteger("use_time");
                Integer subflow = net.getInteger("subflow");
                String netType = net.getString("net_type");
                String place = net.getString("place");
                String cellPhone = net.getString("cell_phone");
                Double subtotal = net.getDouble("subtotal");
                ModelMobileOperatorNets modelMobileOperatorNets = new ModelMobileOperatorNets();
                modelMobileOperatorNets.setIdentification(identification);
                modelMobileOperatorNets.setStartTime(startTime);
                modelMobileOperatorNets.setUpdateTime(updateTime);
                modelMobileOperatorNets.setUseTime(useTime);
                modelMobileOperatorNets.setSubflow(subflow);
                modelMobileOperatorNets.setNetType(netType);
                modelMobileOperatorNets.setPlace(place);
                modelMobileOperatorNets.setCellPhone(cellPhone);
                modelMobileOperatorNets.setSubtotal(subtotal);
                modelMobileOperatorNets.setUserId(userId);
                modelMobileOperatorNets.setVerifyCount(verifyCount1);
                modelMobileOperatorNets.setQueryTime(new Date(queryTime));
                modelMobileOperatorNets.setCreateTime(new Date());
                modelMobileOperatorNetsMapper.save(modelMobileOperatorNets);
            }
        }
    }
}
