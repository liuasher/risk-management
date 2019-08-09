package com.wjl.service.thirdservice;

import com.wjl.commom.model.Response;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
public interface TaobaoReportService {

    /**
     * 通过报告id查询淘宝报告，返回response查询结果
     * @param reportId
     * @return
     */
    Response getReport(String reportId);
}
