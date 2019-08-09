package com.wjl.service.impl;

import com.wjl.mapper.ModelEbankCreditCardReportMapper;
import com.wjl.model.ModelEbankCreditCardReport;
import com.wjl.service.ModelEbankCreditCardReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/4
 */
@Service
public class ModelEbankCreditCardReportServiceImpl implements ModelEbankCreditCardReportService {
    @Autowired
    private ModelEbankCreditCardReportMapper modelEbankCreditCardReportMapper;

    @Override
    public Integer findTopVerifyCountByUserId(Long userId) {
        return modelEbankCreditCardReportMapper.findTopVerifyCountByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        modelEbankCreditCardReportMapper.deleteByUserId(userId);
    }

    @Override
    public void save(ModelEbankCreditCardReport modelEbankCreditCardReport) {
        modelEbankCreditCardReportMapper.save(modelEbankCreditCardReport);
    }
}
