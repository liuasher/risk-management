package com.wjl.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.TongDunConfiguration;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.enumeration.TongDunEnum;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.ApiTokenMapper;
import com.wjl.model.ApiToken;
import com.wjl.model.constant.QueueDict;
import com.wjl.model.mq.TongDunBean;
import com.wjl.service.thirdservice.TongDunService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 同盾认证 监听者
 *
 * @author 赵进平
 */
@Slf4j
@RabbitListener(queues = QueueDict.QUEUENAME_TONGDUN, containerFactory = "rabbitListenerContainerFactory")
@Configuration
public class TongDunListenerStep {

    @Autowired
    private TongDunConfiguration tongDunCfg;

    @Autowired
    private TongDunService tongDunService;

    @Autowired
    private ApiTokenMapper apiTokenMapper;

    @Bean
    public Queue step10Queue() {
        return new Queue(QueueDict.QUEUENAME_TONGDUN);
    }

    /**
     * 同盾认证队列
     */
    @RabbitHandler
    public void process(@Payload String tongDun) throws Exception {
        Response response;
        Map<String, String> result = new HashMap<>(2);
        TongDunBean tongDunBean = JSONObject.parseObject(tongDun, TongDunBean.class);

        if (null == tongDunBean) {
            log.error(QueueDict.QUEUENAME_TONGDUN + ", 队列数据为null ");
            return;
        }

        String url = null;
        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(tongDunBean.getIdentification(), ApiTypeConstant.TONGDUN);
        if (null != byTokenAnAndType) {
            url = byTokenAnAndType.getIp() + byTokenAnAndType.getApi();
        }

        Long userId = tongDunBean.getUserId();
        result.put("userId", userId.toString());
        log.info("-----消息队列：" + QueueDict.QUEUENAME_TONGDUN + " start, userId={}----- ", userId);

        try {
            String blackBox = tongDunBean.getBlackBox();
            log.info("-----userId={}的用户同盾认证开始-----", userId);

            //同盾参数
            String submitUrl = tongDunCfg.getSubmitUrl();
            String partnerCode = tongDunCfg.getPartnerCode();
            String partnerKey = tongDunCfg.getPartnerKey();
            String queryUrl = tongDunCfg.getQueryUrl();
            //应用名称 hrzc_and/hrzc_ios
            String appName = "";


        /*
         * 解析black_box.os,设置app_name
    	 * {"os":"android","version":"3.0.1","packages":"com.hrzc.zgqb_1.0.8","profile_time":397,"token_id":"xxxxx"}
    	 */
            String os = JSONObject.parseObject(new String(Base64.decodeBase64(blackBox))).getString("os");
            if (!StringUtils.isEmpty(os) && TongDunEnum.APP_NAME_ANDROID.getMessage().equals(os.toLowerCase())) {
                //Android
                appName = tongDunCfg.getAppNameAndroid();
            } else if (!StringUtils.isEmpty(os) && TongDunEnum.APP_NAME_IOS.getMessage().equals(os.toLowerCase())) {
                //iOS
                appName = tongDunCfg.getAppNameIos();
            }

            if (StringUtils.isEmpty(appName)) {
                log.error("-----提交同盾错误，app_name为空，black_box无效,userId={},black_box={}-----", userId, blackBox);
                response = new Response<>(CodeEnum.PARAMETER_MISTAKE, result);
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
                return;
            }

            //同盾认证
            String submitUrlOk = submitUrl + "?partner_code=" + partnerCode + "&partner_key=" + partnerKey + "&app_name=" + appName;
            Long id = tongDunService.audit(tongDunBean, submitUrlOk);
            if (null == id) {
                log.error("-----同盾提交失败,userId={}，请求信息未存入数据库-----", userId);
                response = new Response<>(CodeEnum.IDENTIFY_FAIL, result);
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
                return;
            }

            //查询报告ID
            String reportId = tongDunService.findById(id).getReportId();
            if (null == reportId) {
                log.error("-----同盾提交失败,userId={}，未获取到报告-----", userId);
                response = new Response<>(CodeEnum.IDENTIFY_FAIL, result);
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
                return;
            }

            //同盾查询
            String queryUrlOk = queryUrl + "?partner_code=" + partnerCode + "&partner_key=" + partnerKey + "&report_id=" + reportId;
            String verifyId = tongDunService.query(tongDunBean, queryUrlOk, id);
            if (null == verifyId) {
                log.error("-----同盾获取报告失败,userId={}-----", userId);
                response = new Response<>(CodeEnum.QUERY_FAIL, result);
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
                return;
            }
            result.put("verifyId", verifyId);
            log.info("-----同盾获取报告成功,userId={}-----", userId);
            response = new Response<>(CodeEnum.IDENTIFY_SUCCESS, result);
            try {
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
            } catch (Exception e) {
                log.error("Exception is {}", e);
            }
        } catch (Exception e) {
            log.error("-----消息队列：" + QueueDict.QUEUENAME_TONGDUN + " 出错, userId={}----- ", userId);
            log.error("Exception is {}", e);
            //待返回回调参数
            response = new Response<>(CodeEnum.IDENTIFY_FAIL, result);
            try {
                HttpUtils.sendPost(url, (JSON.toJSONString(response)));
            } catch (Exception error) {
                log.error("Exception is {}", error);
            }
        }
    }
}