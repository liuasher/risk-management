package com.wjl.service;


import com.wjl.model.ModelMobileOperatorCellBehavior;
import com.wjl.model.mongo.MobileOperatorBill;

import java.util.List;

/**
 * @author hqh
 */
public interface ModelMobileOperatorCellBehaviorService {
    /**
     * 将运营商报告保存至mysql数据库
     * @param mobileOperatorBill
     * @param token
     * @param userId
     */
     void save(MobileOperatorBill mobileOperatorBill, String token, Long userId);

    /**
     * 运营商：近6个月语音、流量、话费
     * @param userId
     * @param identification
     * @return
     */
    List<ModelMobileOperatorCellBehavior> findModelMobileOperatorTotal(Long userId, String identification);
}
