package com.wjl.controller;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.model.constant.QueueDict;
import com.wjl.model.mongo.TencentCloudReport;
import com.wjl.model.mq.TencentCloudBean;
import com.wjl.mongo.TencentCloudReportRepository;
import com.wjl.service.TencentCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 腾讯云 数据处理
 * @author LINJX
 */
@Slf4j
@RestController
@RequestMapping(value = "/txy")
public class TencentCloudController {

    @Autowired
    private TencentCloudReportRepository tencentCloudReportRepository;

    /**
     * 查询报告接口
     * @param requestArgs 请求参数
     * @return mongo中腾讯云报告
     */
    @PostMapping("/getReport")
    public Response getTxyInfo(@RequestBody RequestArgs requestArgs) {
        String verifyId = requestArgs.getVerifyId();
        if (null == verifyId) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        TencentCloudReport report = tencentCloudReportRepository.findById(verifyId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);
    }

}
