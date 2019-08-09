package com.wjl.service.thirdservice;

import com.wjl.model.MoxieAliPay;

import java.util.Date;
import java.util.List;

/**
 * 支付宝操作
 *
 * @author mayue
 * @date 2018/4/3
 */
public interface AliPayService {

    /**
     * 保存支付宝请求信息
     *
     * @param moxieAliPay
     * @return 是否保存成功
     */
    void saveAliPayRequestInfo(MoxieAliPay moxieAliPay);

    /**
     * 查询已经保存在数据库中的支付宝请求信息
     *
     * @param userId 用户ID
     * @param type   类型
     * @param taskId taskId
     * @return MoxieAliPay
     */
    MoxieAliPay findByUserIdAndTypeAndTaskId(Long userId, Integer type, String taskId);

    /**
     * 查询未被处理过的支付宝信息
     *
     * @return List<MoxieAliPay>
     */
    List<MoxieAliPay> findBillWait();

    /**
     * 查询未被处理过的支付宝报告
     *
     * @return List<MoxieAliPay>
     */
    List<MoxieAliPay> findReportWait();

    /**
     * 更新魔蝎支付宝
     *
     * @param id        主键ID
     * @param reportId  报告id
     * @param queryTime 查询时间
     */
    void updateReportIdAndQueryTime(Long id, String reportId, Date queryTime);

    /**
     * 查询支付宝Data
     *
     * @param moxieAliPay 魔蝎支付宝请求信息
     * @return String 支付宝Data
     */
    String queryData(MoxieAliPay moxieAliPay);

    /**
     * 查询支付宝Report
     *
     * @param moxieAliPay 魔蝎支付宝请求信息
     * @return String 支付宝报告
     */
    String queryReport(MoxieAliPay moxieAliPay);

    /**
     * 修改支付宝model数据库状态
     *
     * @param id          id
     * @param mysqlStatus 数据库状态吗，1表示model导入mysql成功，2表示失败
     */
    void updateMysqlStatus(Long id, Integer mysqlStatus);

}
