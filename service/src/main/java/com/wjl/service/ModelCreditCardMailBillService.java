package com.wjl.service;


import com.wjl.model.mongo.CreditCardMailBillData;

public interface ModelCreditCardMailBillService {
    void impHistory();

    /**
     *
     * @param creditCardMailBillData 信用卡邮箱账单数据
     * @param identification 项目标识符
     */
    void saveModelCreaditCardBill(CreditCardMailBillData creditCardMailBillData,String identification,Long userId);

}
