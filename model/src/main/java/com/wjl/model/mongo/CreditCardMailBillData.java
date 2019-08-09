package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

/**
 * 魔蝎邮箱信用卡账单
 * @author hqh
 */
@Data
public class CreditCardMailBillData {
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
     * 项目标识码
     */

    private String identification;
    /**
     * 用户id
     */
    private Long    userId;
    /**
     * 邮箱信用卡账单
     */
    private JSONObject bill;
}
