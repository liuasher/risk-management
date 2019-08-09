package com.wjl.service.thirdservice;

import com.wjl.commom.model.Response;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
public interface EbankReportService {
    /**
     * 通过报告id在MongoDB中查询报告，组成response返回
     * @param reportId
     * @return
     */
    Response getReport(String reportId);
}
