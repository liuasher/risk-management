package com.wjl.service;

import com.wjl.model.mongo.MobileOperatorReport;

/**
 * @author hqh
 */
public interface ModelMobileOperatorReportService {
    /**
     * 保存ModelMobileOperatorReport
     * @param mobileOperatorReport
     * @param token
     */
     void save(MobileOperatorReport mobileOperatorReport, String token);
}
