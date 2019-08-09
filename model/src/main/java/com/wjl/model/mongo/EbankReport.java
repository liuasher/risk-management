package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

/**
 * @author hqh
 */
@Data
public class EbankReport {
    /**
     * 主键Id
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
     * 用户ID
     */
    private Long userId;
    /**
     * 网银Report
     */
    private JSONObject report;
}
