package com.wjl.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hqh
 */
@Data
public class MobileOperatorDto {
    /**
     * 运营商：近6个月语音、流量、话费
     */
    private List<ModelMobileOperatorCellBehavior> modelMobileOperatorTotal;
    /**
     * 运营商：最近6个月top3通话联系人
     */
    private List<ModelMobileOperatorCalls> modelMobileOperatorContact;
    /**
     * 运营商：活跃区间
     */
    private List<ModelMobileOperatorCalls> modelMobileOperatorLiveRange;
    /**
     * 运营商：活跃时段
     */
    private List<ModelMobileOperatorCalls> modelMobileOperatorLiveTime;
    /**
     * 运营商：活跃地区
     */
    private List<ModelMobileOperatorCalls> modelMobileOperatorLiveAreas;
    /**
     * 运营商：主被叫分布
     */
    private List<ModelMobileOperatorCalls> modelMobileOperatorCallsDistribution;
}
