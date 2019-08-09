package com.wjl.controller;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.service.thirdservice.AuthIdentifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : ZHAOJP
 * @Description :实名认证
 * @date : 2018/2/27 13:13
 */
@Slf4j
@RestController
public class AuthIdentifyController {
    @Autowired
    private AuthIdentifyService authIdentifyService;

    /**
     * 认证接口
     *
     * @return
     */
    @PostMapping(value = "/identifyAuth")
    public Response identifyAuth(@RequestBody RequestArgs requestArgs) {
        String identification = requestArgs.getIdentification();
        try {
            Response response = authIdentifyService.getAuthIdentify(requestArgs, identification);
            return response;
        } catch (Exception e) {
            log.error("-----实名认证出错------", e);
            return new Response<>(CodeEnum.IDENTIFY_FAIL, null);
        }
    }

    /**
     * 获取认证信息
     *
     * @return
     */
    @PostMapping(value = "/identifyInfo")
    public Response getIdentifyAuthInfo(@RequestBody Map param) {
        String taskId = (String) param.get("taskId");
        try {
            if (null == taskId) {
                return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
            }
            log.info("-----查询实名认证信息开始taskId:" + taskId + "-----");
            Response response = authIdentifyService.getAuthIdentifyInfo(taskId);
            return response;
        } catch (Exception e) {
            log.error("-----查询实名认证信息出错taskId=" + taskId + "-----", e);
            return new Response<>(CodeEnum.QUERY_FAIL, null);
        }

    }

}
