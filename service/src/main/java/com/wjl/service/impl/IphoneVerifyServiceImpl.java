package com.wjl.service.impl;

import com.wjl.commom.configuration.FirmConfiguration;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.AesUtils;
import com.wjl.mapper.IphoneVerifyMapper;
import com.wjl.model.Iphone;
import com.wjl.service.IphoneVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by LINJX on 2018/2/27.
 */
@Service
@Slf4j
public class IphoneVerifyServiceImpl implements IphoneVerifyService {

    @Autowired
    private IphoneVerifyMapper iphoneVerifyMapper;

    @Autowired
    private FirmConfiguration firmConfiguration;


    @Override
    public Response saveIphoneInfo(RequestArgs requestArgs, String identification) {
        Long userId = requestArgs.getUserId();
        try {
            if (userId == null) {
                log.info("-----userId为空-----");
                return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
            }
            String icloudName = requestArgs.getIcloudName();
            String icloudPassword = requestArgs.getIcloudPassword();
            String serialNumber = requestArgs.getSerialNumber();
            String imei = requestArgs.getImei();
            if (StringUtils.isEmpty(icloudName) || StringUtils.isEmpty(icloudPassword)
                    || StringUtils.isEmpty(serialNumber) || StringUtils.isEmpty(imei)) {
                log.info("-----icloudName || icloudPassword|| serialNumber|| imei 为空-----");
                return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
            }
            log.info(String.format("-----用户%d 保存苹果手机信息开始-----", userId));
            Iphone iphone = new Iphone();
            iphone.setUserId(userId);
            iphone.setIcloudName(icloudName);
            iphone.setIcloudPassword(icloudPassword);
            iphone.setSerialNumber(serialNumber);
            iphone.setImei(imei);
            iphone.setCreateTime(System.currentTimeMillis());
            iphone.setIdentification(identification);
            iphoneVerifyMapper.saveIphoneInfo(iphone);
            Long id = iphone.getId();
            String wjl = firmConfiguration.getWjl();
            String tokens = AesUtils.aesEncryptHexString(id.toString(), wjl);
            Map<String, String> map = new HashMap<>(2);
            map.put("taskId", tokens);
            log.info(String.format("-----用户%d 保存苹果手机信息结束,成功-----", userId));
            return new Response<>(CodeEnum.IDENTIFY_SUCCESS, map);
        } catch (Exception e) {
            log.error(String.format("-----用户%d 手机认证出错-----", userId));
            return new Response<>(CodeEnum.IDENTIFY_FAIL, null);
        }
    }

    @Override
    public Response getIphoneInfo(String taskId) {
        try {
            if (StringUtils.isEmpty(taskId)) {
                return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
            }
            Long id = Long.valueOf(AesUtils.aesDecryptHexString(taskId, firmConfiguration.getWjl()));
            Iphone iphoneInfo = iphoneVerifyMapper.getIphoneInfo(id);
            if (iphoneInfo != null) {
                HashMap<String, Object> map = new HashMap<>(16);
                map.put("icloudName", iphoneInfo.getIcloudName());
                map.put("icloudPassword", iphoneInfo.getIcloudPassword());
                map.put("serialNumber", iphoneInfo.getSerialNumber());
                map.put("imei", iphoneInfo.getImei());
                map.put("createTime", iphoneInfo.getCreateTime());
                return new Response<>(CodeEnum.QUERY_SUCCESS, map);
            }
            return new Response<>(CodeEnum.QUERY_FAIL, null);
        } catch (Exception e) {
            log.error(String.format("-----taskId为%s 的手机信息获取出错-----", taskId));
            return new Response<>(CodeEnum.QUERY_FAIL, null);
        }
    }

}
