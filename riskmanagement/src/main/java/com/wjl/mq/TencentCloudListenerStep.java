package com.wjl.mq;

import com.alibaba.fastjson.JSON;
import com.wjl.commom.configuration.TencentCloudConfiguration;
import com.wjl.commom.constant.ApiTypeConstant;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.commom.mq.MqSender;
import com.wjl.commom.util.HttpUtils;
import com.wjl.mapper.ApiTokenMapper;
import com.wjl.model.ApiToken;
import com.wjl.model.constant.QueueDict;
import com.wjl.model.mq.TencentCloudBean;
import com.wjl.service.TencentCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯云认证 监听者
 * 
 * @author 赵进平
 *
 */
@Slf4j
@RabbitListener(queues = QueueDict.QUEUENAME_TENCENTCLOUD, containerFactory="rabbitListenerContainerFactory")
@Configuration   
public class TencentCloudListenerStep {

    @Autowired
	private ApiTokenMapper apiTokenMapper;

    @Resource
    private TencentCloudService tencentCloudService;

    @Bean  
    public Queue step00Queue() {
        return new Queue(QueueDict.QUEUENAME_TENCENTCLOUD);
    }  
  
    /**
     * 腾讯云认证队列
     */
    @RabbitHandler  
    public void process(@Payload String tencent) throws Exception {
        if (tencent == null) {
            log.error(QueueDict.QUEUENAME_TENCENTCLOUD + ", 队列数据为null ");
            return;
        }
        TencentCloudBean tencentCloudBean = JSON.parseObject(tencent, TencentCloudBean.class);
        String url = null;
        ApiToken byTokenAnAndType = apiTokenMapper.findByTokenAndType(tencentCloudBean.getIdentification(), ApiTypeConstant.TENCENT_CLOUD);
        if (null!=byTokenAnAndType){
            url=  byTokenAnAndType.getIp()+byTokenAnAndType.getApi();
        }
        Long userId = null;
        try {
            userId = tencentCloudBean.getUserId();
            Response response = tencentCloudService.saveTxyInfo(tencentCloudBean);
            try {
                HttpUtils.sendPost(url,JSON.toJSONString(response));
            }catch (Exception e){
                log.error("错误原因 {}", e);
            }
        } catch (Exception e) {
            log.info(String.format("-----消息队列："+QueueDict.QUEUENAME_TENCENTCLOUD+" 出错, userId=%s----- ", userId));
            log.error("错误原因 {}", e);
            Map<String, Long> map = new HashMap<>(2);
            map.put("userId",userId);
            Response<Map<String, Long>> mapResponse = new Response<>(CodeEnum.IDENTIFY_FAIL, map);
            String response = JSON.toJSONString(mapResponse);
            HttpUtils.sendPost(url,response);
        }
    }
}