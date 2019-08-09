package com.wjl.service;


import com.wjl.model.mongo.CreditCardMailReportData;

public interface ModelCreditCardMailReportService {
    public void saveByJsonArray(CreditCardMailReportData creditCardMailReportData,String identification,Long userId);
}
