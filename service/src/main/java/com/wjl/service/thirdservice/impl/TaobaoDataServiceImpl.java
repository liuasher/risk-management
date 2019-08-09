package com.wjl.service.thirdservice.impl;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.model.mongo.TaobaoData;
import com.wjl.mongo.TaobaoDataRepository;
import com.wjl.service.thirdservice.TaobaoDataService;
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
public class TaobaoDataServiceImpl implements TaobaoDataService {

    @Autowired
    private TaobaoDataRepository taobaoDataRepository;

    @Override
    public Response getBill(String billId) {
        TaobaoData taobaoData = taobaoDataRepository.findOne(billId);
        return new Response(CodeEnum.QUERY_SUCCESS, taobaoData.getBill());
    }
}
