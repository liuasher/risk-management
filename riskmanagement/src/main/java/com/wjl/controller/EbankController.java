package com.wjl.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.constant.MoXieConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.model.ApiToken;
import com.wjl.model.MoXieEbank;
import com.wjl.model.MoXieResult;
import com.wjl.service.ApiTokenService;
import com.wjl.service.MoXieEbankService;
import com.wjl.service.thirdservice.EbankDataService;
import com.wjl.service.thirdservice.EbankReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
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
import java.util.Map;

/**
 * 网银回调
 * @author zhaojinping
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/ebank")
public class EbankController {

    private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

    private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

    private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MoXieEbankService moXieEbankService;

    @Autowired
    private ApiTokenService apiTokenService;

    @Autowired
    private EbankReportService ebankReportService;

    @Autowired
    private EbankDataService ebankDataService;

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
        String taskSubmit = "task.submit";

        //任务提交
        if (StringUtils.equals(eventName.toLowerCase(), taskSubmit)) {
            //通知状态变更为 '认证中'
            //noticeHttp..

        }


        //登录结果
        if (StringUtils.equals(eventName.toLowerCase(), keyTask)) {
            try {
                Map map = objectMapper.readValue(body, Map.class);
                if (map.containsKey(keyResult)) {
                    String result = map.get("result").toString();
                    if (StringUtils.equals(result, keyFalse)) {
                        if (map.containsKey(keyMessage)) {
                            String message = map.get("message") == null ? "未知异常" : map.get("message").toString();

                            //通知状态变更为 '认证失败'
                            String moxie = map.get("user_id").toString();
                            String[] split = moxie.split("_");
                            String token = split[0];
                            String user_id =  split[1].toString();
                            Long userId = Long.valueOf(user_id);
                            MoXieEbank moXieEbank = new MoXieEbank();
                            moXieEbank.setCreateTime(System.currentTimeMillis());
                            moXieEbank.setStatus(MoXieConstant.STATUS3);
                            moXieEbank.setType(MoXieConstant.TYPE1);
                            moXieEbank.setIdentification(token);
                            moXieEbank.setUserId(userId);
                            moXieEbankService.save(moXieEbank);

                            String url = null;
                            ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_BANK);
                            if (null!=byTokenAnAndType){
                                url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                            }
                            MoXieResult moXieResult = new MoXieResult();
                            moXieResult.setUserId(userId);
                            moXieResult.setCode("2");
                            moXieResult.setMessage("网银认证失败");
                            //认证结果回调给app端
                            HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));

                            log.info("-----魔蝎网银授权登入失败-----");
                            log.info("-----魔蝎网银授权登入 task event. result={}, message={}-----", result, message);
                        }
                    } else if (StringUtils.equals(result, keyTrue)) {

                        String taskId = map.get("task_id").toString();
                        String moxie = map.get("user_id").toString();
                        String[] split = moxie.split("_");
                        String token = split[0];
                        String user_id =  split[1].toString();
                        Long userId = Long.valueOf(user_id);

                        MoXieEbank moXieEbank = new MoXieEbank();
                        moXieEbank.setCreateTime(System.currentTimeMillis());
                        moXieEbank.setStatus(MoXieConstant.STATUS2);
                        moXieEbank.setType(MoXieConstant.TYPE1);
                        moXieEbank.setTaskId(taskId);
                        moXieEbank.setIdentification(token);
                        moXieEbank.setUserId(userId);
                        moXieEbankService.save(moXieEbank);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_BANK);
                        if (null!=byTokenAnAndType){
                            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                        }
                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("3");
                        moXieResult.setMessage("网银认证中");
                        //认证结果回调给app端
                        HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));

                        log.info("-----魔蝎网银授权登陆成功,userId={}-----",userId);
                    }
                }
            } catch (Exception e) {
                log.error("-----魔蝎网银授权出错-----", e);
            }
        }


        if (StringUtils.equals(eventName.toLowerCase(), taskFail)) {
            try {
                Map map = objectMapper.readValue(body, Map.class);
                if (map.containsKey(keyResult) && map.containsKey(keyMessage)) {
                    String result = map.get("result").toString();

                    String moxie = map.get("user_id").toString();
                    String[] split = moxie.split("_");
                    String token = split[0];
                    String userIdStr = split[1];
                    Long userId = Long.valueOf(userIdStr);

                    String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
                    //通知状态变更为 '认证失败'
                    MoXieEbank moXieEbank = new MoXieEbank();
                    moXieEbank.setCreateTime(System.currentTimeMillis());
                    moXieEbank.setStatus(MoXieConstant.STATUS3);
                    moXieEbank.setType(MoXieConstant.TYPE2);
                    moXieEbank.setIdentification(token);
                    moXieEbank.setUserId(userId);
                    moXieEbankService.save(moXieEbank);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_BANK);
                    if (null!=byTokenAnAndType){
                        url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                    }
                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("认证失败");
                    //认证结果回调给app端
                    HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));

                    log.info("-----魔蝎淘宝账单获取失败-----");
                    log.info("-----魔蝎淘宝账单 task fail event. result={}, message={}-----", result, message);
                }
            } catch (Exception e) {
                log.error("-----魔蝎淘宝账单出错-----", e);
            }
        }

        log.info("event name:" + eventName.toLowerCase());
        //任务完成的通知处理，其中qq联系人的通知为sns，其它的都为bill
        if (StringUtils.equals(eventName.toLowerCase(), keyBill) || StringUtils.equals(eventName.toLowerCase(), keyAllBill) || StringUtils.equals(eventName.toLowerCase(), keySns)) {

            //通知状态变更为 '认证完成'
            //noticeHttp..

            try {
                Map map = objectMapper.readValue(body, Map.class);
                String taskId = map.get("task_id").toString();

                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                String token = split[0];
                String userIdStr = split[1];
                Long userId = Long.valueOf(userIdStr);

                MoXieEbank moXieEbank = new MoXieEbank();
                moXieEbank.setCreateTime(System.currentTimeMillis());
                moXieEbank.setStatus(MoXieConstant.STATUS2);
                moXieEbank.setType(MoXieConstant.TYPE2);
                moXieEbank.setUserId(userId);
                moXieEbank.setIdentification(token);
                moXieEbank.setTaskId(taskId);
                moXieEbankService.save(moXieEbank);

                log.info("-----魔蝎网银账单获取成功,userId={}-----",userId);
            } catch (Exception e) {
                log.error("-----魔蝎淘宝账单出错-----", e);
            }

        }
        Long userId = 0L;
        String token = null;
        boolean isAuthFinish = false;
        String taskId = null;
        if (StringUtils.equals(eventName.toLowerCase(), keyReport)) {
            try {
                Map map = objectMapper.readValue(body, Map.class);
                String result = map.get("result").toString();
                String message = map.get("message").toString();
                taskId = map.get("task_id").toString();
                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                userId = Long.valueOf(split[1]);
                token = split[0];

                MoXieEbank moXieEbank = new MoXieEbank();
                if (StringUtils.equals(result, keyTrue)) {
                    moXieEbank.setCreateTime(System.currentTimeMillis());
                    moXieEbank.setStatus(MoXieConstant.STATUS2);
                    moXieEbank.setType(MoXieConstant.TYPE3);
                    moXieEbank.setMessage(message);
                    moXieEbank.setUserId(userId);
                    moXieEbank.setIdentification(token);
                    moXieEbank.setTaskId(taskId);
                    moXieEbankService.save(moXieEbank);

                    isAuthFinish = true;
                    log.info("-----魔蝎网银报告获取成功,userId={}-----",userId);
                } else if (StringUtils.equals(result, keyFalse)) {
                    moXieEbank.setCreateTime(System.currentTimeMillis());
                    moXieEbank.setStatus(MoXieConstant.STATUS3);
                    moXieEbank.setType(MoXieConstant.TYPE3);
                    moXieEbank.setMessage(message);
                    moXieEbank.setUserId(userId);
                    moXieEbank.setIdentification(token);
                    moXieEbank.setTaskId(taskId);
                    moXieEbankService.save(moXieEbank);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_BANK);
                    if (null!=byTokenAnAndType){
                        url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                    }
                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("淘宝认证失败");
                    //认证结果回调给app端
                    HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));

                    log.info("-----魔蝎淘宝报告获取失败-----");
                }
            } catch (Exception e) {
                log.error("-----魔蝎淘宝报告出错-----", e);
            }
        }

        if (isAuthFinish) {
            MoXieEbank m1 = moXieEbankService.findByUserIdAndType(userId, MoXieConstant.TYPE1,token,taskId);
            MoXieEbank m2 = moXieEbankService.findByUserIdAndType(userId, MoXieConstant.TYPE2,token,taskId);
            MoXieEbank m3 = moXieEbankService.findByUserIdAndType(userId, MoXieConstant.TYPE3,token,taskId);

            if (m1 != null && m2 != null && m3 != null
                    && MoXieConstant.STATUS2.equals(m1.getStatus())
                    && MoXieConstant.STATUS2.equals(m2.getStatus())
                    && MoXieConstant.STATUS2.equals(m3.getStatus())) {

                String billId = moXieEbankService.billQuery(m2);
                String reportId = moXieEbankService.reportQuery(m3);

                String url = null;
                ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_BANK);
                if (null!=byTokenAnAndType){
                    url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                }
                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("1");
                moXieResult.setMessage("网银认证成功");
                moXieResult.setBillId(billId);
                moXieResult.setReportId(reportId);

                try {
                    //认证结果回调给app端
                    HttpUtils.sendPost(url,JSON.toJSONString(moXieResult));
                } catch (IOException e) {
                    e.printStackTrace();
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
     * 查询网银Bill
     * @param params
     * @return
     */
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public Response getData(@RequestBody Map<String, String> params){
        String billId = params.get("billId");
        if(null == billId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        return ebankDataService.getBill(billId);

    }

    /**
     * 查询网银Report
     * @param params
     * @return
     */
    @RequestMapping(value = "/getReport",method = RequestMethod.POST)
    public Response getReport(@RequestBody Map<String, String> params){
        String reportId = params.get("reportId");
        if(null == reportId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }

        return ebankReportService.getReport(reportId);
    }

}
