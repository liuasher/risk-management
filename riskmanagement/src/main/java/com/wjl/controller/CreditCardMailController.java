package com.wjl.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.constant.MoXieConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.model.ApiToken;
import com.wjl.model.MoXieResult;
import com.wjl.model.MoxieCreditCardMail;
import com.wjl.model.mongo.CreditCardMailBillData;
import com.wjl.model.mongo.CreditCardMailReportData;
import com.wjl.service.ApiTokenService;
import com.wjl.service.MoXieCreditCardMailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 魔蝎信用卡邮箱接口
 * @author LINJX
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", method = RequestMethod.POST)
public class CreditCardMailController {

    private static final String HEADER_MOXIE_EVENT = "X-Moxie-Event";

    private static final String HEADER_MOXIE_TYPE = "X-Moxie-Type";

    private static final String HEADER_MOXIE_SIGNATURE = "X-Moxie-Signature";

    @Resource
    private MoXieCreditCardMailService moXieCreditCardMailService;

    @Resource
    private ApiTokenService apiTokenService;




    /**
     * 回调接口, moxie通过此endpoint通知账单更新和任务状态更新
     */
    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    public void notifyUpdateBill(@RequestBody String body, ServletRequest request, ServletResponse response) {

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

        MoxieCreditCardMail moxieCreditCardMail;

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
                            String[] split = moxie.split("_");
                            Long userId = Long.valueOf(split[1]);
                            String identification = split[0];

                            moxieCreditCardMail = new MoxieCreditCardMail();

                            //moxie_creditcard_bill状态变更为 '认证失败'
                            moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                            moxieCreditCardMail.setUserId(userId);
                            moxieCreditCardMail.setStatus(MoXieConstant.STATUS3);
                            moxieCreditCardMail.setType(MoXieConstant.TYPE1);
                            moxieCreditCardMail.setIdentification(identification);
                            moXieCreditCardMailService.save(moxieCreditCardMail);

                            //魔蝎信用卡邮箱认证失败，记录用户认证失败信息
                            String url = null;
                            ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(identification, ApiTypeConstant.E_MAILE);
                            if (null!=byTokenAnAndType){
                                url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                            }
                            MoXieResult moXieResult = new MoXieResult();
                            moXieResult.setUserId(userId);
                            moXieResult.setCode("2");
                            moXieResult.setMessage("魔蝎邮箱信用卡认证失败");
                            String reqJson = JSON.toJSONString(moXieResult);

                            HttpUtils.sendPost(url,reqJson);

                            log.info("魔蝎邮箱信用卡登陆失败. result={}, message={}，userId={}", result, message, userId);
                        }
                    } else if (StringUtils.equals(result, keyTrue)) {
                        String taskId = map.get("task_id").toString();

                        //拿到userId
                        String moxie = map.get("user_id").toString();
                        String[] split = moxie.split("_");
                        Long userId = Long.valueOf(split[1]);
                        String token = split[0];

                        moxieCreditCardMail = new MoxieCreditCardMail();

                        //保存borrow_apply_moxie_bank
                        moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                        moxieCreditCardMail.setTaskId(taskId);
                        moxieCreditCardMail.setStatus(MoXieConstant.STATUS2);
                        moxieCreditCardMail.setType(MoXieConstant.TYPE1);
                        moxieCreditCardMail.setUserId(userId);
                        moxieCreditCardMail.setIdentification(token);
                        moXieCreditCardMailService.save(moxieCreditCardMail);

                        String url = null;
                        ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_MAILE);
                        if (null!=byTokenAnAndType){
                            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                        }
                        MoXieResult moXieResult = new MoXieResult();
                        moXieResult.setUserId(userId);
                        moXieResult.setCode("3");
                        moXieResult.setMessage("魔蝎邮箱信用卡认证中");
                        String reqJson = JSON.toJSONString(moXieResult);
                        HttpUtils.sendPost(url,reqJson);

                        log.info("魔蝎邮箱信用卡授权登陆成功 result={}, userId={}", result, userId);
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

                //拿到userId
                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                Long userId = Long.valueOf(split[1]);
                String token = split[0];

                String message = map.get("message") == null ? "未知异常" : map.get("message").toString();

                moxieCreditCardMail = new MoxieCreditCardMail();
                moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                moxieCreditCardMail.setStatus(MoXieConstant.STATUS3);
                moxieCreditCardMail.setType(MoXieConstant.TYPE2);
                moxieCreditCardMail.setUserId(userId);
                moxieCreditCardMail.setIdentification(token);
                moXieCreditCardMailService.save(moxieCreditCardMail);

                //魔蝎信用卡邮箱认证失败，记录用户认证失败信息

                String url = null;
                ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_MAILE);
                if (null!=byTokenAnAndType){
                    url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                }
                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("2");
                moXieResult.setMessage("魔蝎网银信用卡认证失败");
                String reqJson = JSON.toJSONString(moXieResult);

                HttpUtils.sendPost(url,reqJson);
                log.info("魔蝎网银信用卡采集失败,message={} userId={}", message, userId);
            } catch (Exception e) {
                log.error("魔蝎邮箱信用卡账单采集失败", e);
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
                String[] split = moxie.split("_");
                Long userId = Long.valueOf(split[1]);
                String token = split[0];

                moxieCreditCardMail = new MoxieCreditCardMail();
                moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                moxieCreditCardMail.setTaskId(taskId);
                moxieCreditCardMail.setStatus(MoXieConstant.STATUS2);
                moxieCreditCardMail.setType(MoXieConstant.TYPE2);
                moxieCreditCardMail.setUserId(userId);
                moxieCreditCardMail.setIdentification(token);
                moXieCreditCardMailService.save(moxieCreditCardMail);

                log.info("魔蝎邮箱信用卡账单采集成功, userId={}", userId);
            } catch (Exception e) {
                log.error("魔蝎邮箱信用卡账单采集失败", e);
            }

        }

        Long userId = 0L;
        String token = null;
        String taskId = null;
        boolean isAuthFinish = false;
        if (StringUtils.equals(eventName.toLowerCase(), keyReport)) {
            try {
                Map map = JSON.parseObject(body, Map.class);
                String result = map.get("result").toString();
                String message = map.get("message").toString();
                taskId = map.get("task_id").toString();
                String emailId = map.get("email_id").toString();
                String moxie = map.get("user_id").toString();
                String[] split = moxie.split("_");
                userId = Long.valueOf(split[1]);
                token = split[0];


                moxieCreditCardMail = new MoxieCreditCardMail();
                if (StringUtils.equals(result, keyTrue)) {
                    moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                    moxieCreditCardMail.setTaskId(taskId);
                    moxieCreditCardMail.setMessage(message);
                    moxieCreditCardMail.setStatus(MoXieConstant.STATUS2);
                    moxieCreditCardMail.setType(MoXieConstant.TYPE3);
                    moxieCreditCardMail.setUserId(userId);
                    moxieCreditCardMail.setEmailId(emailId);
                    moxieCreditCardMail.setIdentification(token);
                    moXieCreditCardMailService.save(moxieCreditCardMail);

                    isAuthFinish = true;
                    log.info("魔蝎邮箱信用卡报告采集成功,userId={},message={}", userId, message);
                } else if (StringUtils.equals(result, keyFalse)) {

                    moxieCreditCardMail.setCreateTime(System.currentTimeMillis());
                    moxieCreditCardMail.setTaskId(taskId);
                    moxieCreditCardMail.setStatus(MoXieConstant.STATUS3);
                    moxieCreditCardMail.setType(MoXieConstant.TYPE3);
                    moxieCreditCardMail.setUserId(userId);
                    moxieCreditCardMail.setIdentification(token);
                    moXieCreditCardMailService.save(moxieCreditCardMail);

                    String url = null;
                    ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_MAILE);
                    if (null!=byTokenAnAndType){
                        url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                    }
                    MoXieResult moXieResult = new MoXieResult();
                    moXieResult.setUserId(userId);
                    moXieResult.setCode("2");
                    moXieResult.setMessage("魔蝎邮箱信用卡认证失败");
                    String reqJson = JSON.toJSONString(moXieResult);

                    HttpUtils.sendPost(url,reqJson);

                    log.info("魔蝎邮箱信用卡报告采集失败, userId={},message={}", userId, message);
                }
            } catch (Exception e) {
                log.error("魔蝎邮箱信用卡账单采集失败", e);
            }
        }

        if (isAuthFinish) {
            MoxieCreditCardMail m1 = moXieCreditCardMailService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE1,taskId);
            MoxieCreditCardMail m2 = moXieCreditCardMailService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE2,taskId);
            MoxieCreditCardMail m3 = moXieCreditCardMailService.findByUserIdAndTypeAndTaskId(userId, MoXieConstant.TYPE3,taskId);

            if (m1 != null && m2 != null && m3 != null
                    && MoXieConstant.STATUS2.equals(m1.getStatus())
                    && MoXieConstant.STATUS2.equals(m2.getStatus())
                    && MoXieConstant.STATUS2.equals(m3.getStatus())) {

                String url = null;
                ApiToken byTokenAnAndType = apiTokenService.findByTokenAndType(token, ApiTypeConstant.E_MAILE);
                if (null!=byTokenAnAndType){
                    url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
                }
                String billId = moXieCreditCardMailService.billQuery(m2);
                String reportId = moXieCreditCardMailService.reportQuery(m3);
                MoXieResult moXieResult = new MoXieResult();
                moXieResult.setUserId(userId);
                moXieResult.setCode("1");
                moXieResult.setMessage("魔蝎信用卡邮箱认证成功");
                moXieResult.setBillId(billId);
                moXieResult.setReportId(reportId);
                String reqJson = JSON.toJSONString(moXieResult);
                try {
                    HttpUtils.sendPost(url,reqJson);
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
     * 查询信用卡邮箱Bill
     * @param requestArgs 认证id
     * @return 用卡邮箱Bill
     */
    @RequestMapping("/getData")
    public Response getData(@RequestBody RequestArgs requestArgs){
        String billId = requestArgs.getBillId();
        if(null == billId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        CreditCardMailBillData bill = moXieCreditCardMailService.getBill(billId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, bill);
    }

    /**
     * 查询信用卡邮箱Report
     * @param requestArgs 认证id
     * @return 信用卡邮箱report
     */
    @RequestMapping("/getReport")
    public Response getReport(@RequestBody RequestArgs requestArgs){
        String reportId = requestArgs.getReportId();
        if(null == reportId){
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        CreditCardMailReportData report = moXieCreditCardMailService.getReport(reportId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);
    }
}
