package com.wjl.service.impl;

import com.wjl.mapper.RiskBlackMapper;
import com.wjl.service.RiskBlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/10
 */
@Service
@Slf4j
public class RiskBlackServiceImpl implements RiskBlackService {

    @Autowired
    private RiskBlackMapper riskBlackMapper;

    @Override
    public Integer getCountByMobile(String mobile) {
        return riskBlackMapper.getCountByMobile(mobile);
    }

    @Override
    public Integer getCountByIdCard(String idCard) {
        return riskBlackMapper.getCountByIdCard(idCard);
    }
}
