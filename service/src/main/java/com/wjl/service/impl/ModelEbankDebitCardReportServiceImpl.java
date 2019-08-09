package com.wjl.service.impl;

import com.wjl.mapper.ModelEbankDebitCardReportMappper;
import com.wjl.model.ModelEbankDebitCardReport;
import com.wjl.service.ModelEbankDebitCardReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/4
 */
@Service
public class ModelEbankDebitCardReportServiceImpl implements ModelEbankDebitCardReportService {

    @Autowired
    private ModelEbankDebitCardReportMappper modelEbankDebitCardReportMappper;

    @Override
    public Integer findVerifyCountByUserId(Long userId) {
        return modelEbankDebitCardReportMappper.findVerifyCountByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        modelEbankDebitCardReportMappper.deleteByUserId(userId);
    }

    @Override
    public void save(ModelEbankDebitCardReport modelEbankDebitCardReport) {
        modelEbankDebitCardReportMappper.save(modelEbankDebitCardReport);
    }
}
