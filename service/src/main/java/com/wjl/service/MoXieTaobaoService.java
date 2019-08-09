package com.wjl.service;

import com.wjl.model.MoXieTaobao;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 魔蝎淘宝Service
 * @date 2018/4/2
 */
public interface MoXieTaobaoService {

    /**
     * 保存操作
     * @param moxieTaobao
     */
    void save(MoXieTaobao moxieTaobao);

    /**
     * 通过userId和type查找对象
     * @param userId
     * @param type
     * @return
     */
    MoXieTaobao findByUserIdAndType(Long userId, Integer type,String identification,String taskId);

    /**
     * 查找未存入MongoDB的账单
     * @return
     */
    List<MoXieTaobao> findBillWait();

    /**
     * 通过id更新报告id和查找时间
     * @param id
     * @param reportId
     * @param queryTime
     */
    void updateReportIdAndQueryTime(Long id, String reportId, long queryTime);

    /**
     * 查找未存入MongoDB的报告
     * @return
     */
    List<MoXieTaobao> findReportWait();

    /**
     * 查找淘宝bill
     * @param moXieTaobao
     */
    String billQuery(MoXieTaobao moXieTaobao);

    /**
     * 查找淘宝report
     * @param moXieTaobao
     */
    String reportQuery(MoXieTaobao moXieTaobao);

    /**
     * 通过id更新mysqlStatus状态
     * @param id
     * @param i
     */
    void updateMysqlStatusById(Long id, Integer status);
}
