package com.wjl.controller;

import com.alibaba.fastjson.JSON;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.constant.MoXieConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.ApiTokenMapper;
import com.wjl.mapper.MobileOperatorMapper;
import com.wjl.model.*;
import com.wjl.service.ModelMobileOperatorCallsService;
import com.wjl.service.ModelMobileOperatorCellBehaviorService;
import com.wjl.service.thirdservice.MobileOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 魔蝎手机运营商回调接口
 * @author hqh
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/mobileoperator")
public class MobileOperatorController {

    private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

    private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

    private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";



    @Autowired
    private MobileOperatorMapper mobileOperatorMapper;
    @Autowired
    private MobileOperatorService mobileOperatorService;
    @Autowired
    private ApiTokenMapper apiTokenMapper;
    @Autowired
    private ModelMobileOperatorCellBehaviorService modelMobileOperatorCellBehaviorService;
    @Autowired
    private ModelMobileOperatorCallsService modelMobileOperatorCallsService;


    /**
     * 回调接口, moxie通过此endpoint通知账单更新和任务状态更新
     */
    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    public void notifyUpdateBill(@RequestBody String body, ServletRequest request, ServletResponse response) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //事件类型：task or bill
        String eventName = httpServletRequest.getHeader(HEADER_MOXIE_EVENT);

        //业务类型：email、bank、carrier 等
        String eventType = httpServletRequest.getHeader(HEADER_MOXIE_TYPE);

        //body签名
        String signature = httpServletRequest.getHeader(HEADER_MOXIE_SIGNATURE);

        String keyTask = "task";
        String keyResult = "result";
        String keyFalse = "false";
        String keyTrue = "true";
        String keyMessage = "message";
        String taskFail = "task.fail";
        String keyBill = "bill";
        String keyAllBill = "allbill";
        String keySns = "sns";
        String keyReport = "report";

        log.info("request body:" + body);

        if (Strings.isNullOrEmpty(eventName)) {
            writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:" + HEADER_MOXIE_EVENT);
            return;
        }

        if (Strings.isNullOrEmpty(eventType)) {
            writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:" + HEADER_MOXIE_TYPE);
            return;
        }

        if (Strings.isNullOrEmpty(signature)) {
            writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "header not found:" + HEADER_MOXIE_SIGNATURE);
            return;
        }


        if (Strings.isNullOrEmpty(body)) {
            writeMessage(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, "request body is empty");
            return;
        }



        //登录结果
        //{"mobile":"15368098198","timestamp":1476084445670,"result":false,"message":"[CALO-22001-10]-服务密码错误，请确认正确后输入。","user_id":"374791","task_id":"fdda6b30-8eba-11e6-b7e9-00163e10b2cd"}
        if (StringUtils.equals(eventName.toLowerCase(), keyTask)) {
            try {
                Map map = JSON.parseObject(body, Map.class);
                if (map.containsKey(keyResult)) {
                    String result = map.get("result").toString();
                    if (StringUtils.equals(result, keyFalse)) {
                        if (map.containsKey(keyMessage)) {
                            String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
                            //拿到userId
                            String moxie = map.get("user_id").toString();
                            String mobile = map.get("mobile").toString();
                            String[] split = moxie.split("_");
                            Long userId = Long.valueOf(split[1]);
                            String identification = split[0];

                            MobileOperator mobileOperator = new MobileOperator();
                            //mobile_operator认证状态改为失败
                            mobileOperator.setCreateTime(new Date());
                            mobileOperator.setType(MoXieConstant.TYPE1);
                            mobileOperator.setUserId(userId);
                            mobileOperator.setMobile(mobile);
                            mobileOperator.setStatus(3);
                            mobileOperator.setIdentification(identification);
                            mobileOperatorMapper.save(mobileOperator);

                            String url = null;
                            ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(identification, ApiTypeConstant.MOBILE_OPERATOR);
                            if (null!=byTokenAnAndType){
                                url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                            }

                            MoXieResult moXieResult = new MoXieResult();
                            moXieResult.setUserId(userId);
                            moXieResult.setCode("2");
                            moXieResult.setMessage("运营商认证失败");
                            try {
                                HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));
                            } catch (IOException e) {
                                log.error("回调失败",e);
                            }

                            log.info("魔蝎运营商授权登陆失败. result={}, message={}, userId={}", result, message, userId);
                        }
                    } else if (StringUtils.equals(result, keyTrue)) {
                        String taskId = map.get("task_id").toString();
                        String moxie = map.get("user_id").toString();
                        String mobile = map.get("mobile").toString();
                        String[] split = moxie.split("_");
                        Long userId = Long.valueOf(split[1]);
                        String identification = split[0];

                        MobileOperator mobileOperator = new MobileOperator();
                        mobileOperator.setUserId(userId);
                        mobileOperator.setType(MoXieConstant.TYPE1);
                        mobileOperator.setStatus(2);
                        mobileOperator.setIdentification(identification);
                        mobileOperator.setMobile(mobile);
                        mobileOperator.setTaskId(taskId);
                        mobileOperator.setCreateTime(new Date());
                        mobileOperatorMapper.save(mobileOperator);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(identification, ApiTypeConstant.MOBILE_OPERATOR);
                        if (null!=byTokenAnAndType){
                            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                        }
                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("3");
                        moXieResult.setMessage("运营商认证中");
                        try {
                            HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));
                        } catch (IOException e) {
                            log.error("回调失败",e);
                        }
                        log.info("魔蝎运营商授权登陆成功 result={}, userId={}", result, userId);
                    }
                }
            } catch (Exception e) {
                log.error("body convert to object error", e);
            }
        }

        //任务过程中的失败
        //运营商的格式{"mobile":"13429801680","timestamp":1474641874728,"result":false,"message":"系统繁忙，请稍后再试","user_id":"1111","task_id":"3e9ff350-819c-11e6-b7fe-00163e004a23"}
        if (StringUtils.equals(eventName.toLowerCase(), taskFail)) {
            try {
                Map map = JSON.parseObject(body, Map.class);
                if (map.containsKey(keyResult) && map.containsKey(keyMessage)) {
                    String result = map.get("result").toString();
                    String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
                    if (StringUtils.equals(result, keyFalse)) {

                        //拿到userId
                        String moxie = map.get("user_id").toString();
                        String mobile = map.get("mobile").toString();
                        String[] split = moxie.split("_");
                        Long userId = Long.valueOf(split[1]);
                        String identification = split[0];

                        MobileOperator mobileOperator = new MobileOperator();
                        //mobile_operator认证状态改为失败
                        mobileOperator.setCreateTime(new Date());
                        mobileOperator.setType(MoXieConstant.TYPE2);
                        mobileOperator.setUserId(userId);
                        mobileOperator.setMobile(mobile);
                        mobileOperator.setStatus(3);
                        mobileOperator.setIdentification(identification);
                        mobileOperatorMapper.save(mobileOperator);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(identification, ApiTypeConstant.MOBILE_OPERATOR);
                        if (null!=byTokenAnAndType){
                            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                        }

                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("2");
                        moXieResult.setMessage("运营商认证失败");
                        try {
                            HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));
                        } catch (IOException e) {
                            log.error("回调失败",e);
                        }
                        log.info("魔蝎运营商账单采集失败,message={} userId={}", message, userId);
                    }
                }
            } catch (Exception e) {
                log.error("body convert to object error", e);
            }
        }

        log.info("event name:" + eventName.toLowerCase());
        //任务完成的通知处理，其中qq联系人的通知为sns，其它的都为bill
        if (StringUtils.equals(eventName.toLowerCase(), keyBill) || StringUtils.equals(eventName.toLowerCase(), keyAllBill) || StringUtils.equals(eventName.toLowerCase(), keySns)) {

            //通知状态变更为 '认证完成'
            //noticeHttp..

            try {
                Map map = JSON.parseObject(body, Map.class);
                String taskId = map.get("task_id").toString();
                //拿到userId
                String moxie = map.get("user_id").toString();
                String mobile = map.get("mobile").toString();
                String[] split = moxie.split("_");
                Long userId = Long.valueOf(split[1]);
                String identification = split[0];

                MobileOperator mobileOperator = new MobileOperator();
                mobileOperator.setUserId(userId);
                mobileOperator.setType(MoXieConstant.TYPE2);
                mobileOperator.setStatus(2);
                mobileOperator.setIdentification(identification);
                mobileOperator.setMobile(mobile);
                mobileOperator.setTaskId(taskId);
                mobileOperator.setCreateTime(new Date());
                mobileOperatorMapper.save(mobileOperator);

                log.info("魔蝎运营商账单采集成功, userId={}", userId);
            } catch (Exception e) {
                log.error("body convert to object error", e);
            }
        }

        long userId = 0L;
        String identification = null;
        boolean isAuthFinish = false;
        String taskId = null;
        if (StringUtils.equals(eventName.toLowerCase(), keyReport)) {
            try {
                Map map = JSON.parseObject(body, Map.class);
                String result = map.get("result").toString();
                String message = map.get("message").toString();
                taskId = map.get("task_id").toString();
                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                 userId = Long.valueOf(split[1]);
                identification = split[0];
                String mobile = map.get("mobile").toString();

                if (StringUtils.equals(result, keyTrue)) {
                    MobileOperator mobileOperator = new MobileOperator();
                    mobileOperator.setUserId(userId);
                    mobileOperator.setType(MoXieConstant.TYPE3);
                    mobileOperator.setStatus(2);
                    mobileOperator.setIdentification(identification);
                    mobileOperator.setMobile(mobile);
                    mobileOperator.setTaskId(taskId);
                    mobileOperator.setMessage(message);
                    mobileOperator.setCreateTime(new Date());
                    mobileOperatorMapper.save(mobileOperator);

                    isAuthFinish = true;
                    log.info("魔蝎运营商报告采集成功,message={}, userId={}", message, userId);
                } else if (StringUtils.equals(result, keyFalse)) {
                    MobileOperator mobileOperator = new MobileOperator();
                    //mobile_operator认证状态改为失败
                    mobileOperator.setCreateTime(new Date());
                    mobileOperator.setType(MoXieConstant.TYPE3);
                    mobileOperator.setUserId(userId);
                    mobileOperator.setMobile(mobile);
                    mobileOperator.setStatus(3);
                    mobileOperator.setIdentification(identification);
                    mobileOperatorMapper.save(mobileOperator);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(identification, ApiTypeConstant.MOBILE_OPERATOR);
                    if (null!=byTokenAnAndType){
                        url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                    }

                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("运营商认证失败");
                    try {
                        HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));
                    } catch (IOException e) {
                        log.error("回调失败",e);
                    }
                    log.info("魔蝎运营商报告采集失败,message={}, userId={}", message, userId);
                }

            } catch (Exception e) {
                log.error("body convert to object error", e);
            }
        }

        if (isAuthFinish) {
            MobileOperator m1 = mobileOperatorMapper.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE1,identification,taskId);
            MobileOperator m2 = mobileOperatorMapper.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE2,identification,taskId);
            MobileOperator m3 = mobileOperatorMapper.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE3,identification,taskId);

            if (m1 != null && m2 != null && m3 != null
                    && MoXieConstant.STATUS2.equals(m1.getStatus())
                    && MoXieConstant.STATUS2.equals(m2.getStatus())
                    && MoXieConstant.STATUS2.equals(m3.getStatus())) {

                String url = null;
                ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(identification, ApiTypeConstant.MOBILE_OPERATOR);
                if (null!=byTokenAnAndType){
                    url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                }
                String billId = mobileOperatorService.billQuery(m2);
                String reportId = mobileOperatorService.reportQuery(m3);

                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("1");
                moXieResult.setMessage("运营商认证成功");
                moXieResult.setBillId(billId);
                moXieResult.setReportId(reportId);
                try {
                    HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));
                } catch (IOException e) {
                    log.error("回调失败",e);
                }
            }
        }
        writeMessage(httpServletResponse, HttpServletResponse.SC_CREATED, "default eventtype");
    }

    private void writeMessage(HttpServletResponse response, int status, String content) {
        response.setStatus(status);
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.write(content);
        } catch (IOException ignored) {
        }
    }

    /**
     *
     * @param requestArgs
     * @return 运营商bill
     * @throws Exception exception
     */
    @RequestMapping(value = "/getBill")
    public Response getBill(@RequestBody RequestArgs requestArgs)throws Exception{
        String billId = requestArgs.getBillId();
        if(null==billId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        return  mobileOperatorService.getBill(requestArgs);
    }

    /**
     *
     * @param requestArgs
     * @return 运营商report
     * @throws Exception
     */
    @RequestMapping(value = "/getReport")
    public Response getReport(@RequestBody RequestArgs requestArgs)throws Exception{
        String reportId = requestArgs.getReportId();
        if(null==reportId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        return mobileOperatorService.getReport(requestArgs);
    }

    @RequestMapping(value = "/getModelMobileOperator")
    public Response getModelMobileOperator(@RequestBody RequestArgs requestArgs)throws Exception{
        List<ModelMobileOperatorCellBehavior> modelMobileOperatorTotal = modelMobileOperatorCellBehaviorService.findModelMobileOperatorTotal(requestArgs.getUserId(), requestArgs.getIdentification());
        List<ModelMobileOperatorCalls> modelMobileOperatorContact = modelMobileOperatorCallsService.findModelMobileOperatorContact(requestArgs.getUserId(), requestArgs.getIdentification());
        List<ModelMobileOperatorCalls> modelMobileOperatorLiveRange = modelMobileOperatorCallsService.findmodelMobileOperatorLiveRange(requestArgs.getUserId(), requestArgs.getIdentification());
        List<ModelMobileOperatorCalls> modelMobileOperatorLiveTime = modelMobileOperatorCallsService.findModelMobileOperatorLiveTime(requestArgs.getUserId(), requestArgs.getIdentification());
        List<ModelMobileOperatorCalls> modelMobileOperatorLiveAreas = modelMobileOperatorCallsService.findModelMobileOperatorLiveAreas(requestArgs.getUserId(), requestArgs.getIdentification());
        List<ModelMobileOperatorCalls> modelMobileOperatorCallsDistribution = modelMobileOperatorCallsService.findModelMobileOperatorCallsDistribution(requestArgs.getUserId(), requestArgs.getIdentification());

        MobileOperatorDto mobileOperatorDto = new MobileOperatorDto();
        mobileOperatorDto.setModelMobileOperatorTotal(modelMobileOperatorTotal);
        mobileOperatorDto.setModelMobileOperatorContact(modelMobileOperatorContact);
        mobileOperatorDto.setModelMobileOperatorLiveRange(modelMobileOperatorLiveRange);
        mobileOperatorDto.setModelMobileOperatorLiveTime(modelMobileOperatorLiveTime);
        mobileOperatorDto.setModelMobileOperatorLiveAreas(modelMobileOperatorLiveAreas);
        mobileOperatorDto.setModelMobileOperatorCallsDistribution(modelMobileOperatorCallsDistribution);

        return new Response<>(CodeEnum.QUERY_SUCCESS, mobileOperatorDto);
    }
}
