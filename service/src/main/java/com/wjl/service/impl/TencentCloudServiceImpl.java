package com.wjl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.TencentCloudConfiguration;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.third.ApiRequest;
import com.wjl.commom.util.third.ApiResponse;
import com.wjl.commom.util.third.TxyApi;
import com.wjl.mapper.TencentCloudMapper;
import com.wjl.model.TencentCloudInfo;
import com.wjl.model.mongo.TencentCloudReport;
import com.wjl.model.mq.TencentCloudBean;
import com.wjl.mongo.TencentCloudReportRepository;
import com.wjl.service.TencentCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author LINJX
 * @description
 * @date 2018/4/2 14:05
 */
@Service
@Slf4j
public class TencentCloudServiceImpl implements TencentCloudService {
    @Autowired
    private TencentCloudMapper tencentCloudMapper;

    @Autowired
    private TencentCloudReportRepository tencentCloudReportRepository;

    @Resource
    private TencentCloudConfiguration tencentCloudConfiguration;

    @Override
    public Response saveTxyInfo(TencentCloudBean requestArgs) throws Exception{
        String identification = requestArgs.getIdentification();
        Long userId = requestArgs.getUserId();
        String idNumber = requestArgs.getIdCard();
        String phoneNumber = requestArgs.getMobile();
        String name = requestArgs.getRealname();
        String bankCardNumber = requestArgs.getCardNo();
        String userIp = requestArgs.getIp();

        TencentCloudReport tencentCloudReport = txySubmit(requestArgs);
        //腾讯云状态码
        final String tencentStatus = "Success";
        final String codeDesc = "codeDesc";
        if (tencentCloudReport == null || !tencentCloudReport.getReport().getString(codeDesc).equals(tencentStatus)) {
            log.info(String.format("查询腾讯云数据完毕-失败, userId=%s ", userId));
            TencentCloudReport failed = new TencentCloudReport();
            failed.setUserId(userId);
            failed.setQueryTime(System.currentTimeMillis());
            failed.setSuccess("failed");
            tencentCloudReportRepository.save(failed);
            Map<String, Long> map = new HashMap<>(2);
            map.put("userId",userId);
            return new Response<>(CodeEnum.IDENTIFY_FAIL, map);
        }
        tencentCloudReport.setUserId(userId);
        tencentCloudReport.setQueryTime(System.currentTimeMillis());
        tencentCloudReport.setSuccess("success");
        tencentCloudReportRepository.save(tencentCloudReport);
        //保存腾讯云参数
        TencentCloudInfo tencentCloudInfo = new TencentCloudInfo();
        tencentCloudInfo.setUserId(userId);
        tencentCloudInfo.setIdCard(idNumber);
        tencentCloudInfo.setMobile(phoneNumber);
        tencentCloudInfo.setRealname(name);
        tencentCloudInfo.setIp(userIp);
        tencentCloudInfo.setBankCardNumber(bankCardNumber);
        tencentCloudInfo.setCreateTime(System.currentTimeMillis());
        tencentCloudInfo.setIdentification(identification);
        tencentCloudInfo.setStatus(Integer.parseInt(tencentCloudReport.getReport().getString("code")));
        tencentCloudInfo.setVerifyId(tencentCloudReport.getId());
        tencentCloudMapper.saveTxyInfo(tencentCloudInfo);
        Map<String, String> map1 = new HashMap<>(2);
        map1.put("verifyId", tencentCloudReport.getId());
        map1.put("userId",tencentCloudReport.getUserId().toString());
        log.info(String.format("userId=%s,腾讯云数据服务结束-成功", userId));
        return new Response<>(CodeEnum.IDENTIFY_SUCCESS, map1);
    }


    private TencentCloudReport txySubmit(TencentCloudBean requestArgs) throws Exception {
        if (requestArgs == null) {
            log.error("贷前准备数据为空");
            return null;
        }
        Long userId = requestArgs.getUserId();
        log.info(String.format("userId=%s,腾讯云请求服务-开始", userId));
        String secretId = tencentCloudConfiguration.getSecretId();
        String secretKey = tencentCloudConfiguration.getSecretKey();
        Map<String, String> args = new TreeMap<>();
        args.put("idNumber", requestArgs.getIdCard());
        args.put("phoneNumber", "0086-" + requestArgs.getMobile());
        args.put("name", requestArgs.getRealname());
        if(requestArgs.getCardNo()!=null) {
            args.put("bankCardNumber", requestArgs.getCardNo());
        }
        args.put("userIp", requestArgs.getIp());

        ApiResponse res;
        try {
            String url = TxyApi.makeURL("GET", "AntiFraud", "gz", secretId, secretKey, args, "utf-8");
            res = ApiRequest.sendGet(url, "");
        } catch (Exception e) {
            log.error(String.format("userId=%s,腾讯云参数准备错误", userId));
            return null;
        }
        String result = (String) res.getBody();
        JSONObject report = JSONObject.parseObject(result);
        TencentCloudReport tencentCloudReport = new TencentCloudReport();
        tencentCloudReport.setReport(report);
        return tencentCloudReport;
    }


}
