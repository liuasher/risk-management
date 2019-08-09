package com.wjl.model.mq;

import lombok.Data;

/**
 * 机审结果
 * @author hqh
 */
@Data
public class ResultBean {
    /**
     * 授信ID
     */
    private Long creditId;
    /**
     * 客户ID
     */
    private Long userId;
    /**
     * 机审时间
     */
    private Long verifyTime;
    /**
     * remark
     */
    private String remark;
}
