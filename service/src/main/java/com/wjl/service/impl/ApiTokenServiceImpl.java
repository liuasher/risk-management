package com.wjl.service.impl;

import com.wjl.mapper.ApiTokenMapper;
import com.wjl.model.ApiToken;
import com.wjl.service.ApiTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/2
 */
@Service
public class ApiTokenServiceImpl implements ApiTokenService {

    @Autowired
    ApiTokenMapper apiTokenMapper;

    @Override
    public ApiToken findByTokenAndType(String token, Integer type) {
        return apiTokenMapper.findByTokenAndType(token,type);
    }
}
