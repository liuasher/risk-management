package com.wjl.service;



import com.wjl.model.ModelEbankBill;

import java.util.List; /**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
public interface ModelEbankBillService {
    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    Integer findTopVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除数据
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 保存对象集合
     * @param modelEbankBillList
     */
    void saveList(List<ModelEbankBill> modelEbankBillList);
}
