package com.wjl.service.impl;

import com.wjl.mapper.ModelEbankBillMapper;
import com.wjl.model.ModelEbankBill;
import com.wjl.service.ModelEbankBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
@Service
public class ModelEbankBillServiceImpl implements ModelEbankBillService {

    @Autowired
    private ModelEbankBillMapper modelEbankBillMapper;

    @Override
    public Integer findTopVerifyCountByUserId(Long userId) {
        return modelEbankBillMapper.findTopVerifyCountByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        modelEbankBillMapper.deleteByUserId(userId);
    }

    @Override
    public void saveList(List<ModelEbankBill> modelEbankBillList) {
        if(null!=modelEbankBillList && modelEbankBillList.size()>0){
            for(ModelEbankBill modelEbankBill:modelEbankBillList){
                modelEbankBillMapper.save(modelEbankBill);
            }
        }
    }
}
