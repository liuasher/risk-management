package com.wjl.service.thirdservice;

import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.model.MobileOperator;

import java.util.Date;

/**
 * @author hqh
 */
public interface MobileOperatorService {
    /**
     * 获取运营商Bill
     * @param requestArgs
     * @return
     */
    Response getBill(RequestArgs requestArgs);

    /**
     * 获取运营Report
     * @param requestArgs
     * @return
     */
    Response getReport(RequestArgs requestArgs);

    /**
     * 更新MobileOperator的reportId
     * @param reportId
     * @param queryTime
     * @param id
     */
    void updateReportId(String reportId, Date queryTime, Long id);

    /**
     * 查询手机运营商Bill
     * @param mobileOperator
     */
    String billQuery(MobileOperator mobileOperator);

    /**
     * 查询手机运营商Report
     * @param mobileOperator
     */
    String reportQuery(MobileOperator mobileOperator);

    /**
     * 更新MYSQL状态码
     * @param id
     * @param mysqlStatus
     */
    void updateMysqlStatus(Long id,Integer mysqlStatus);

}
