package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * 运营商Reprot
 * @author hqh
 */
@Data
public class MobileOperatorReport {
    /**
     * 主键Id
     */
    @Id
    private String	id;
    /**
     * 手机号
     */
    private String	phone;
    /**
     * 认证Id
     */
    private String identification;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 查询时间
     */
    private Long queryTime;
    /**
     * 运营商Report
     */
    private JSONObject report;
}
