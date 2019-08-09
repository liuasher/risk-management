package com.wjl.controller;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.service.IphoneVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 苹果手机认证
 * @author LINJX
 */
@Slf4j
@RestController
@RequestMapping("/verify")
public class IphoneVerifyController {

    @Autowired
    private IphoneVerifyService iphoneVerifyService;


    /**
     * 保存苹果手机信息接口
     *
     * @param requestArgs 请求参数
     * @param request     请求参数
     * @return 包装的taskId
     */
    @RequestMapping(value = "/saveIphoneInfo", method = RequestMethod.POST)
    public Response saveIphoneInfo(@RequestBody RequestArgs requestArgs) {
        final String identification = requestArgs.getIdentification();
        try {
            return iphoneVerifyService.saveIphoneInfo(requestArgs, identification);
        } catch (Exception e) {
            log.error("保存苹果手机接口出错", e);
            return new Response<>(CodeEnum.IDENTIFY_FAIL, null);
        }
    }

    /**
     * 获取苹果手机信息接口
     *
     * @param requestArgs 请求参数
     * @return Response
     */
    @RequestMapping(value = "/getIphoneInfo", method = RequestMethod.POST)
    public Response getIphoneInfo(@RequestBody RequestArgs requestArgs) {
        String taskId = requestArgs.getTaskId();
        try {
            return iphoneVerifyService.getIphoneInfo(taskId);
        } catch (Exception e) {
            log.error(String.format("-----taskId为%s 的手机认证获取出错-----", taskId));
            return new Response<>(CodeEnum.QUERY_FAIL, null);
        }
    }
}
