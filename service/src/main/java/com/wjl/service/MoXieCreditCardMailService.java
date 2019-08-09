package com.wjl.service;

import com.alibaba.fastjson.JSONObject;
import com.wjl.model.MoxieCreditCardMail;
import com.wjl.model.mongo.CreditCardMailBillData;
import com.wjl.model.mongo.CreditCardMailReportData;

import java.util.List;

/**
 * @author LINJX
 * @description
 * @date 2018/4/3 16:19
 */
public interface MoXieCreditCardMailService {

    /**
     * 根据用户id和类型查找
     * @param userId 用户ID
     * @param type 类型
     * @return MoxieCreditCardMail
     */
    MoxieCreditCardMail findByUserIdAndTypeAndTaskId(Long userId, Integer type,String taskId);

    /**
     * 根据授信id修改魔蝎信用卡认证数据为失败
     * @param userId 用户id
     */
    void updateMoXieBankFailed(Long userId);

    /**
     * 查询Bill
     * @return 查询Bill等待的列表
     */
    List<MoxieCreditCardMail> findBillWait();

    /**
     * 查询Report等待
     *@return 查询Report等待的列表
     */
    List<MoxieCreditCardMail> findReportWait();

    /**
     * 更新报告id和查询时间
     * @param id 用户id
     * @param reportId 报告id
     * @param queryTime 查询时间
     */
    void updateReportIdAndQueryTime(Long id, String reportId, Long queryTime);

    /**
     * 根据taskId查找reportId为空的报告
     * @param taskId 任务id
     * @return 结果列表
     */
    List<MoxieCreditCardMail> findReportIsEmptyByTaskId(String taskId);

    /**
     * 保存接口
     * @param moxieCreditCardMail 魔蝎信用卡邮箱实体
     */

    void save(MoxieCreditCardMail moxieCreditCardMail);

    /**
     * 查询手机运营商Bill
     * @param moxieCreditCardMail 魔蝎信用卡邮箱实体类
     * @return 报告主键
     */
    String billQuery(MoxieCreditCardMail moxieCreditCardMail);

    /**
     * 查询手机运营商Report
     * @param moxieCreditCardMail 魔蝎信用卡邮箱实体类
     * @return 报告主键
     */
     String reportQuery(MoxieCreditCardMail moxieCreditCardMail);

    /**
     * 查询bill
     * @param billId mongo中bill的id
     * @return bill对象
     */
    CreditCardMailBillData getBill(String billId);

    /**
     * 查询report
     * @param reportId mongo中report的id
     * @return report对象
     */
    CreditCardMailReportData getReport(String reportId);

    /**
     * 修改mysql导入状态
     * @param id 魔蝎信用卡邮箱表主键
     * @param status 状态码
     */
    void updateMysqlStatus(Long id,Integer status);


}
