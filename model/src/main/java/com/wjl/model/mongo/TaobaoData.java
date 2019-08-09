package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

/**
 * 魔蝎淘宝账单和报告
 * @author hqh
 */
@Data
public class TaobaoData {
    /**
     * Mongo主键Id
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
     * 淘宝Bill
     */
    private JSONObject bill;

}
