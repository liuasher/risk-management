package com.wjl.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.constant.MoXieConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.model.ApiToken;
import com.wjl.model.MoXieResult;
import com.wjl.model.MoxieAliPay;
import com.wjl.model.mongo.MoXieAliPayBillData;
import com.wjl.model.mongo.MoXieAliPayReportData;
import com.wjl.mongo.MoxieAliPayBillDataRepository;
import com.wjl.mongo.MoxieAliPayReportDataRepository;
import com.wjl.service.ApiTokenService;
import com.wjl.service.thirdservice.AliPayService;
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
import java.util.Date;
import java.util.Map;

/**
 * 魔蝎支付宝回调接口
 *
 * @author hqh
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/pay")
public class AliPayController {

    private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

    private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

    private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";


    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private MoxieAliPayBillDataRepository moxieAliPayBillDataRepository;

    @Autowired
    private MoxieAliPayReportDataRepository moxieAliPayReportDataRepository;

    @Autowired
    private ApiTokenService apiTokenService;

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

        String keyResult = "result";
        String keyTask = "task";
        String keyFalse = "false";
        String keyTrue = "true";
        String keyReport = "report";
        String keyMessage = "message";
        String taskFail = "task.fail";
        String bill = "bill";
        String allBill = "allbill";
        String taskSubmit = "task.submit";
        String keySns = "sns";
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
                            String moxie = map.get("user_id").toString();
                            String[] split = moxie.split("_");
                            Long userId = Long.valueOf(split[1]);
                            String token = split[0];

                            MoxieAliPay moxieAliPay = new MoxieAliPay();
                            moxieAliPay.setCreateTime(new Date());
                            moxieAliPay.setStatus(MoXieConstant.STATUS3);
                            moxieAliPay.setType(MoXieConstant.TYPE1);
                            moxieAliPay.setUserId(userId);
                            moxieAliPay.setIdentification(token);
                            aliPayService.saveAliPayRequestInfo(moxieAliPay);

                            String url = null;
                            ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.ALIPAY);
                            if (null != byTokenAnAndType) {
                                url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                            }
                            MoXieResult moXieResult = new MoXieResult();
                            moXieResult.setUserId(userId);
                            moXieResult.setCode("2");
                            moXieResult.setMessage("支付宝失败");
                            String reqJson = JSON.toJSONString(moXieResult);
                            HttpUtils.sendPost(url, reqJson);

                            log.info("魔蝎支付宝授权登入失败");
                            log.info("task event. result={}, message={}", result, message);
                        }
                    } else if (StringUtils.equals(result, keyTrue)) {
                        String moxie = map.get("user_id").toString();
                        String[] split = moxie.split("_");
                        Long userId = Long.valueOf(split[1]);
                        String token = split[0];
                        String taskId = map.get("task_id").toString();
                        MoxieAliPay moxieAliPay = new MoxieAliPay();
                        moxieAliPay.setCreateTime(new Date());
                        moxieAliPay.setStatus(MoXieConstant.STATUS2);
                        moxieAliPay.setType(MoXieConstant.TYPE1);
                        moxieAliPay.setTaskId(taskId);
                        moxieAliPay.setUserId(userId);
                        moxieAliPay.setIdentification(token);
                        aliPayService.saveAliPayRequestInfo(moxieAliPay);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.ALIPAY);
                        if (null != byTokenAnAndType) {
                            url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                        }
                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("3");
                        moXieResult.setMessage("支付宝认证中");
                        String reqJson = JSON.toJSONString(moXieResult);
                        HttpUtils.sendPost(url, reqJson);
                    }

                    log.info("魔蝎支付宝授权登陆成功");
                }
            } catch (Exception e) {
                log.error("魔蝎支付宝授权出错", e);
            }
        }


        if (StringUtils.equals(eventName.toLowerCase(), taskFail)) {
            try {
                Map map = objectMapper.readValue(body, Map.class);
                if (map.containsKey(keyResult) && map.containsKey(keyMessage)) {
                    String result = map.get("result").toString();
                    String moxie = map.get("user_id").toString();
                    String[] split = moxie.split("_");
                    Long userId = Long.valueOf(split[1]);
                    String token = split[0];
                    String message = map.get("message") == null ? "未知异常" : map.get("message").toString();
                    //通知状态变更为 '认证失败'
                    MoxieAliPay moxieAliPay = new MoxieAliPay();
                    moxieAliPay.setCreateTime(new Date());
                    moxieAliPay.setStatus(MoXieConstant.STATUS3);
                    moxieAliPay.setType(MoXieConstant.TYPE2);
                    moxieAliPay.setUserId(userId);
                    moxieAliPay.setIdentification(token);
                    aliPayService.saveAliPayRequestInfo(moxieAliPay);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.ALIPAY);
                    if (null != byTokenAnAndType) {
                        url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                    }
                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("支付宝认证失败");
                    String reqJson = JSON.toJSONString(moXieResult);
                    HttpUtils.sendPost(url, reqJson);

                    log.info("魔蝎支付宝账单获取失败");
                    log.info("task fail event. result={}, message={}", result, message);
                }
            } catch (Exception e) {
                log.error("魔蝎支付宝账单出错", e);
            }
        }

        log.info("event name:" + eventName.toLowerCase());
        //任务完成的通知处理，其中qq联系人的通知为sns，其它的都为bill
        if (StringUtils.equals(eventName.toLowerCase(), bill) || StringUtils.equals(eventName.toLowerCase(), allBill) || StringUtils.equals(eventName.toLowerCase(), keySns)) {

            //通知状态变更为 '认证完成'
            //noticeHttp..

            try {
                Map map = objectMapper.readValue(body, Map.class);
                String taskId = map.get("task_id").toString();
                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                Long userId = Long.valueOf(split[1]);
                String token = split[0];

                MoxieAliPay moxieAliPay = new MoxieAliPay();
                moxieAliPay.setCreateTime(new Date());
                moxieAliPay.setStatus(MoXieConstant.STATUS2);
                moxieAliPay.setType(MoXieConstant.TYPE2);
                moxieAliPay.setUserId(userId);
                moxieAliPay.setTaskId(taskId);
                moxieAliPay.setIdentification(token);
                aliPayService.saveAliPayRequestInfo(moxieAliPay);

                log.info("魔蝎支付宝账单获取成功");
            } catch (Exception e) {
                log.error("魔蝎支付宝账单出错", e);
            }

        }

        long userId = 0L;
        String token = null;
        String taskId = null;
        boolean isAuthFinish = false;
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

                if (StringUtils.equals(result, keyTrue)) {
                    MoxieAliPay moxieAliPay = new MoxieAliPay();
                    moxieAliPay.setCreateTime(new Date());
                    moxieAliPay.setStatus(MoXieConstant.STATUS2);
                    moxieAliPay.setType(MoXieConstant.TYPE3);
                    moxieAliPay.setMessage(message);
                    moxieAliPay.setUserId(userId);
                    moxieAliPay.setTaskId(taskId);
                    moxieAliPay.setIdentification(token);
                    aliPayService.saveAliPayRequestInfo(moxieAliPay);

                    isAuthFinish = true;
                    log.info("魔蝎支付宝报告获取成功");
                } else if (StringUtils.equals(result, keyFalse)) {
                    MoxieAliPay moxieAliPay = new MoxieAliPay();
                    moxieAliPay.setCreateTime(new Date());
                    moxieAliPay.setStatus(MoXieConstant.STATUS3);
                    moxieAliPay.setType(MoXieConstant.TYPE3);
                    moxieAliPay.setMessage(message);
                    moxieAliPay.setUserId(userId);
                    moxieAliPay.setTaskId(taskId);
                    moxieAliPay.setIdentification(token);
                    aliPayService.saveAliPayRequestInfo(moxieAliPay);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.ALIPAY);
                    if (null != byTokenAnAndType) {
                        url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                    }
                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("支付宝认证失败");
                    String reqJson = JSON.toJSONString(moXieResult);
                    HttpUtils.sendPost(url, reqJson);

                    log.info("魔蝎支付宝报告获取失败");
                }
            } catch (Exception e) {
                log.error("魔蝎支付宝报告出错", e);
            }
        }

        if (isAuthFinish) {
            MoxieAliPay m1 = aliPayService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE1, taskId);
            MoxieAliPay m2 = aliPayService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE2, taskId);
            MoxieAliPay m3 = aliPayService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE3, taskId);

            if (m1 != null && m2 != null && m3 != null
                    && MoXieConstant.STATUS2.equals(m1.getStatus())
                    && MoXieConstant.STATUS2.equals(m2.getStatus())
                    && MoXieConstant.STATUS2.equals(m3.getStatus())) {

                String url = null;
                ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.ALIPAY);
                if (null != byTokenAnAndType) {
                    url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                }

                //TODO:调取报告
                String billId = aliPayService.queryData(m2);
                String reportId = aliPayService.queryReport(m3);

                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("1");
                moXieResult.setMessage("支付宝认证成功");
                moXieResult.setBillId(billId);
                moXieResult.setReportId(reportId);
                String reqJson = JSON.toJSONString(moXieResult);
                try {
                    //回调给app端
                    HttpUtils.sendPost(url, reqJson);
                } catch (IOException e) {
                    log.error("支付宝回调给app端,userId={},url={}", userId, url);
                    log.error("Exception={}", e.getMessage());
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
     * 查询支付宝信息
     *
     * @param requestArgs 集合
     * @return Response
     */
    @RequestMapping("/getData")
    public Response getData(@RequestBody RequestArgs requestArgs) {
        String billId = requestArgs.getBillId();
        if (null == billId) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        MoXieAliPayBillData bill = moxieAliPayBillDataRepository.findById(billId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, bill);
    }

    /**
     * 查询支付宝报告
     *
     * @param requestArgs 集合
     * @return Response
     */
    @RequestMapping("/getReport")
    public Response getReport(@RequestBody RequestArgs requestArgs) {
        String reportId = requestArgs.getReportId();
        if (null == reportId) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        MoXieAliPayReportData report = moxieAliPayReportDataRepository.findById(reportId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);
    }
}
