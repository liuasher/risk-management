package com.wjl.service.thirdservice;

import com.wjl.commom.model.Response;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
public interface TaobaoDataService {
    /**
     * 通过账单id查询淘宝账单，返回查询response
     * @param billId
     * @return
     */
    Response getBill(String billId);
}
