package com.wjl.service.thirdservice;

import com.wjl.commom.model.Response;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
public interface EbankDataService {
    /**
     * 通过账单id在MongoDB中查询网银账单，返回查询response
     * @param billId
     * @return
     */
    Response getBill(String billId);
}
