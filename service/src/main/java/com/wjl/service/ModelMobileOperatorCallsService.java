package com.wjl.service;


import com.wjl.model.ModelMobileOperatorCalls;
import com.wjl.model.ModelMobileOperatorCellBehavior;
import com.wjl.model.mongo.MobileOperatorBill;

import java.util.List;

/**
 * @author hqh
 */
public interface ModelMobileOperatorCallsService {
    /**
     * 运营商：最近6个月top3通话联系人
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCalls> findModelMobileOperatorContact(Long userId, String identification);

    /**
     * 运营商：活跃区间
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCalls> findmodelMobileOperatorLiveRange(Long userId, String identification);

    /**
     * 运营商：活跃时段
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCalls> findModelMobileOperatorLiveTime(Long userId, String identification);

    /**
     * 运营商：活跃地区
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCalls> findModelMobileOperatorLiveAreas(Long userId, String identification);

    /**
     * 运营商：主被叫分布
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCalls> findModelMobileOperatorCallsDistribution(Long userId, String identification);
}
