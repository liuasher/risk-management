package com.wjl.service;

import com.wjl.model.mongo.MoXieAliPayBillData;
import com.wjl.model.mongo.MoXieAliPayReportData;

/**
 * 支付宝model字段处理
 *
 * @author mayue
 * @date 2018/3/30
 */
public interface AliPayMongoToMysqlService {
    /**
     * 存储支付宝model信息
     *
     * @param payData        支付宝信息
     * @param identification 项目标识
     */
    void saveModelPayData(MoXieAliPayBillData payData, String identification);

    /**
     * 存储支付宝model报告
     *
     * @param payReport      支付宝报告
     * @param identification 项目标识
     */
    void saveModelPayReport(MoXieAliPayReportData payReport, String identification);
}
