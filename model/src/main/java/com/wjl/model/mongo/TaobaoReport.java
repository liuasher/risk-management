package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.Id;

/**
 * @author hqh
 */
@Data
public class TaobaoReport {
    /**
     * MONGO主键ID
     */
    @Id
    private String	id;
    /**
     * 查询时间
     */
    private Long queryTime;
    /**
     * 项目标识
     */
    private String identification;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 淘宝报告
     */
    private JSONObject report;
}
