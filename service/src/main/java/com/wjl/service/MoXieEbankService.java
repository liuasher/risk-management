package com.wjl.service;

import com.wjl.model.MoXieEbank;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 魔蝎网银
 * @date 2018/4/2
 */
public interface MoXieEbankService {
    /**
     * 保存操作
     * @param moXieEbank
     */
    void save(MoXieEbank moXieEbank);

    /**
     * 通过userId和type查找对象
     * @param userId
     * @param type
     * @return
     */
    MoXieEbank findByUserIdAndType(Long userId, Integer type,String identification,String taskId);

    /**
     * 查找未导入MongoDB的账单
     * @return
     */
    List<MoXieEbank> findBillWait();

    /**
     * 通过id更新reportId和 queryTime
     * @param id
     * @param reportId
     * @param queryTime
     */
    void updateReportIdAndQueryTime(Long id, String reportId, long queryTime);

    /**
     * 查找未导入MongoDB的报告
     * @return
     */
    List<MoXieEbank> findReportWait();

    /**
     * 向魔蝎查询网银bill
     * @param moXieEbank
     */
    String billQuery(MoXieEbank moXieEbank);

    /**
     * 向魔蝎查询网银report
     * @param moXieEbank
     */
    String reportQuery(MoXieEbank moXieEbank);

    /**
     * 通过id更新msqlStatus状态
     * @param id
     * @param i
     */
    void updateMysqlStatusById(Long id, Integer status);
}
