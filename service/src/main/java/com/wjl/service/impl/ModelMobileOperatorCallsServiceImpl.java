package com.wjl.service.impl;

import com.wjl.mapper.ModelMobileOperatorCallsMapper;
import com.wjl.model.ModelMobileOperatorCalls;
import com.wjl.service.ModelMobileOperatorCallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hqh
 */
@Service
public class ModelMobileOperatorCallsServiceImpl implements ModelMobileOperatorCallsService {
    @Autowired
    private ModelMobileOperatorCallsMapper modelMobileOperatorCallsMapper;
    @Override
    public List<ModelMobileOperatorCalls> findModelMobileOperatorContact(Long userId, String identification) {
        return modelMobileOperatorCallsMapper.findModelMobileOperatorContact(userId,identification);
    }

    @Override
    public List<ModelMobileOperatorCalls> findmodelMobileOperatorLiveRange(Long userId, String identification) {
        return modelMobileOperatorCallsMapper.findModelMobileOperatorLiveRange(userId,identification);
    }

    @Override
    public List<ModelMobileOperatorCalls> findModelMobileOperatorLiveTime(Long userId, String identification) {
        return modelMobileOperatorCallsMapper.findModelMobileOperatorLiveTime(userId,identification);
    }

    @Override
    public List<ModelMobileOperatorCalls> findModelMobileOperatorLiveAreas(Long userId, String identification) {
        return modelMobileOperatorCallsMapper.findModelMobileOperatorLiveAreas(userId,identification);
    }

    @Override
    public List<ModelMobileOperatorCalls> findModelMobileOperatorCallsDistribution(Long userId, String identification) {
        return modelMobileOperatorCallsMapper.findModelMobileOperatorCallsDistribution(userId,identification);
    }
}
