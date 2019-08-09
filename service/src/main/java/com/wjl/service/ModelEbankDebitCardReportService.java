package com.wjl.service;

import com.wjl.model.ModelEbankDebitCardReport; /**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
public interface ModelEbankDebitCardReportService {

    /**
     * 通过userId查找verifyCount
     * @param userId
     * @return
     */
    Integer findVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除对象
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelEbankDebitCardReport
     */
    void save(ModelEbankDebitCardReport modelEbankDebitCardReport);
}
