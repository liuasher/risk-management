package com.wjl.controller;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.service.thirdservice.TongDunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 同盾认证
 *
 * @author mayue
 * @date 2018/3/5
 */
@Slf4j
@RestController
@RequestMapping("/tongDun")
public class TongDunController {

    @Autowired
    private TongDunService tongDunService;

    /**
     * 获取同盾报告
     *
     * @param verifyId 认证ID
     * @param request  request
     * @return Response
     */
    @RequestMapping("/getReport")
    public Response getReport(String verifyId, HttpServletRequest request) {
        if (null == verifyId) {
            log.error("查询失败，verifyId参数不能为空");
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        TongDunReport report = tongDunService.get(verifyId);
        return new Response<>(CodeEnum.QUERY_SUCCESS, report);
    }
}