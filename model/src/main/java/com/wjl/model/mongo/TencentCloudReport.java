package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author hqh
 */
@Data
public class TencentCloudReport {
    /**
     * Mongo主键Id
     */
    @Id
    private String	id;
    /**
     * 查询时间
     */
    private Long  queryTime;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 是否SUBMMIT腾讯云接口成功
     */
    private String success;
    /**
     * 报告
     */
    private JSONObject report;

}
