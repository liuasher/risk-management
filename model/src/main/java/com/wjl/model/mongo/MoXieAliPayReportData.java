package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

/**
 * 支付宝Report
 * @author hqh
 */
@Data
public class MoXieAliPayReportData {
    /**
     * MONGO主键ID
     */
    @Id
    private String id;
    /**
     * 项目标识
     */
    private String identification;
    /**
     * 查询时间
     */
    private Long queryTime;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 支付宝Report
     */
    private JSONObject report;
}
