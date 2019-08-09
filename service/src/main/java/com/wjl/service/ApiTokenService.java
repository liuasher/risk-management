package com.wjl.service;

import com.wjl.model.ApiToken;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description app端回调url和api查询
 * @date 2018/4/2
 */
public interface ApiTokenService {
    /**
     * 通过token和
     * @param token
     * @param type
     * @return
     */
    ApiToken findByTokenAndType(String token, Integer type);
}
