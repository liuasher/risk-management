package com.wjl.service;

import com.wjl.model.ModelEbankCreditCardReport; /**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
public interface ModelEbankCreditCardReportService {

    /**
     * 通过userId查找verifyCount
     * @param userId
     * @return
     */
    Integer findTopVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除对象
     * @param userId
     */
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelEbankCreditCardReport
     */
    void save(ModelEbankCreditCardReport modelEbankCreditCardReport);
}
