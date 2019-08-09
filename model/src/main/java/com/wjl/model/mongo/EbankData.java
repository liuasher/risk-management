package com.wjl.model.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Id;

/**
 * 魔蝎淘宝Bill
 */
@Data
public class EbankData {
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
     * 网银Bill
     */
    private JSONArray bill;

}
