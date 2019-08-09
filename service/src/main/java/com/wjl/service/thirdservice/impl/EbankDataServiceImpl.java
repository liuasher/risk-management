package com.wjl.service.thirdservice.impl;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.model.mongo.EbankData;
import com.wjl.mongo.EbankDataRepository;
import com.wjl.service.thirdservice.EbankDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
@Slf4j
@Service
public class EbankDataServiceImpl implements EbankDataService {

    @Autowired
    private EbankDataRepository ebankDataRepository;

    @Override
    public Response getBill(String billId) {
        EbankData ebankData = ebankDataRepository.findOne(billId);
        return new Response(CodeEnum.QUERY_SUCCESS, ebankData.getBill());
    }
}
