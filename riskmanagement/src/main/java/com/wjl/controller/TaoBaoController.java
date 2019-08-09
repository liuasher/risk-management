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
import com.wjl.model.MoXieResult;
import com.wjl.model.MoXieTaobao;
import com.wjl.service.ApiTokenService;
import com.wjl.service.MoXieTaobaoService;
import com.wjl.service.thirdservice.TaobaoDataService;
import com.wjl.service.thirdservice.TaobaoReportService;
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
 * 魔蝎淘宝回调接口
 *
 * @author zhaojinping
 * @date 2018-3-21
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/taobao")
public class TaoBaoController {

    private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

    private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

    private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";


    private static ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private MoXieTaobaoService moXieTaobaoService;

    @Autowired
    private ApiTokenService apiTokenService;

    @Autowired
    private TaobaoReportService taobaoReportService;

    @Autowired
    private TaobaoDataService taobaoDataService;


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
                            //通知状态变更为 '认证失败'
                            String moxie = map.get("user_id").toString();
                            String[] split = moxie.split("_");
                            Long userId = Long.valueOf(split[1]);
                            String token = split[0];

                            MoXieTaobao moxieTaobao = new MoXieTaobao();
                            moxieTaobao.setCreateTime(System.currentTimeMillis());
                            moxieTaobao.setStatus(MoXieConstant.STATUS3);
                            moxieTaobao.setType(MoXieConstant.TYPE1);
                            moxieTaobao.setIdentification(token);
                            moxieTaobao.setUserId(userId);
                            moXieTaobaoService.save(moxieTaobao);

                            String url = null;
                            ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.TAOBAO);
                            if (null != byTokenAnAndType) {
                                url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                            }

                            MoXieResult moXieResult = new MoXieResult();
                            moXieResult.setUserId(userId);
                            moXieResult.setCode("2");
                            moXieResult.setMessage("淘宝认证失败");
                            //将失败结果回调给app端
                            HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));

                            log.info("-----魔蝎淘宝授权登入失败,userId={}-----", userId);
                            log.info("-----魔蝎淘宝授权登入 task event. result={}, message={}-----", result, message);
                        }
                    } else if (StringUtils.equals(result, keyTrue)) {

                        String taskId = map.get("task_id").toString();

                        String moxie = map.get("user_id").toString();
                        String[] split = moxie.split("_");
                        Long userId = Long.valueOf(split[1]);
                        String token = split[0];

                        MoXieTaobao moxieTaobao = new MoXieTaobao();
                        moxieTaobao.setCreateTime(System.currentTimeMillis());
                        moxieTaobao.setStatus(MoXieConstant.STATUS2);
                        moxieTaobao.setType(MoXieConstant.TYPE1);
                        moxieTaobao.setTaskId(taskId);
                        moxieTaobao.setIdentification(token);
                        moxieTaobao.setUserId(userId);
                        moXieTaobaoService.save(moxieTaobao);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.TAOBAO);
                        if (null != byTokenAnAndType) {
                            url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                        }

                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("3");
                        moXieResult.setMessage("淘宝认证中");
                        //回调给app端
                        HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));

                        log.info("-----魔蝎淘宝授权登陆成功,userId={}-----", userId);
                    }
                }
            } catch (Exception e) {
                log.error("-----魔蝎淘宝授权出错-----", e);
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
                    MoXieTaobao moxieTaobao = new MoXieTaobao();
                    moxieTaobao.setCreateTime(System.currentTimeMillis());
                    moxieTaobao.setStatus(MoXieConstant.STATUS3);
                    moxieTaobao.setType(MoXieConstant.TYPE2);
                    moxieTaobao.setIdentification(token);
                    moxieTaobao.setUserId(userId);
                    moXieTaobaoService.save(moxieTaobao);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.TAOBAO);
                    if (null != byTokenAnAndType) {
                        url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                    }

                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("淘宝认证失败");
                    //回调给app端
                    HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));

                    log.info("-----魔蝎淘宝账单获取失败-----");
                    log.info("-----魔蝎淘宝账单task fail event. result={}, message={}-----", result, message);
                }
            } catch (Exception e) {
                log.error("-----魔蝎淘宝账单出错-----", e);
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

                MoXieTaobao moxieTaobao = new MoXieTaobao();
                moxieTaobao.setCreateTime(System.currentTimeMillis());
                moxieTaobao.setStatus(MoXieConstant.STATUS2);
                moxieTaobao.setType(MoXieConstant.TYPE2);
                moxieTaobao.setUserId(userId);
                moxieTaobao.setIdentification(token);
                moxieTaobao.setTaskId(taskId);
                moXieTaobaoService.save(moxieTaobao);

                log.info("-----魔蝎淘宝账单获取成功,userId={}-----", userId);
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

                MoXieTaobao moxieTaobao = new MoXieTaobao();
                if (StringUtils.equals(result, keyTrue)) {
                    moxieTaobao.setCreateTime(System.currentTimeMillis());
                    moxieTaobao.setStatus(MoXieConstant.STATUS2);
                    moxieTaobao.setType(MoXieConstant.TYPE3);
                    moxieTaobao.setMessage(message);
                    moxieTaobao.setUserId(userId);
                    moxieTaobao.setIdentification(token);
                    moxieTaobao.setTaskId(taskId);
                    moXieTaobaoService.save(moxieTaobao);

                    isAuthFinish = true;
                    log.info("-----魔蝎淘宝报告获取成功,userId={}-----", userId);
                } else if (StringUtils.equals(result, keyFalse)) {
                    moxieTaobao.setCreateTime(System.currentTimeMillis());
                    moxieTaobao.setStatus(MoXieConstant.STATUS3);
                    moxieTaobao.setType(MoXieConstant.TYPE3);
                    moxieTaobao.setMessage(message);
                    moxieTaobao.setUserId(userId);
                    moxieTaobao.setIdentification(token);
                    moxieTaobao.setTaskId(taskId);
                    moXieTaobaoService.save(moxieTaobao);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.TAOBAO);
                    if (null != byTokenAnAndType) {
                        url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                    }

                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("淘宝认证失败");

                    //回调给app端
                    HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));

                    log.info("-----魔蝎淘宝报告获取失败-----");
                }
            } catch (Exception e) {
                log.error("-----魔蝎淘宝报告出错-----", e);
            }
        }

        if (isAuthFinish) {
            MoXieTaobao m1 = moXieTaobaoService.findByUserIdAndType(userId, MoXieConstant.TYPE1, token, taskId);
            MoXieTaobao m2 = moXieTaobaoService.findByUserIdAndType(userId, MoXieConstant.TYPE2, token, taskId);
            MoXieTaobao m3 = moXieTaobaoService.findByUserIdAndType(userId, MoXieConstant.TYPE3, token, taskId);

            if (m1 != null && m2 != null && m3 != null
                    && MoXieConstant.STATUS2.equals(m1.getStatus())
                    && MoXieConstant.STATUS2.equals(m2.getStatus())
                    && MoXieConstant.STATUS2.equals(m3.getStatus())) {

                String billId = moXieTaobaoService.billQuery(m2);
                String reportId = moXieTaobaoService.reportQuery(m3);

                String url = null;
                ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.TAOBAO);
                if (null != byTokenAnAndType) {
                    url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
                }

                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("1");
                moXieResult.setMessage("淘宝认证成功");
                moXieResult.setBillId(billId);
                moXieResult.setReportId(reportId);

                try {
                    //将结果回调给app端
                    HttpUtils.sendPost(url, JSON.toJSONString(moXieResult));
                } catch (IOException e) {
                    log.error("-----魔蝎淘宝认证结果回调给app端出错，userId={}-----" + e, userId);
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
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Response getData(@RequestBody Map<String, String> params) {
        String billId = params.get("billId");
        if (null == billId) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        return taobaoDataService.getBill(billId);
    }

    /**
     * 查询网银Report
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/getReport", method = RequestMethod.POST)
    public Response getReport(@RequestBody Map<String, String> params) {
        String reportId = params.get("reportId");
        if (null == reportId) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        return taobaoReportService.getReport(reportId);
    }

}
