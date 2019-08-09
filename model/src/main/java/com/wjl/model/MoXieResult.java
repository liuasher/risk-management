package com.wjl.model;

import lombok.Data;

/**
 * @author hqh
 */
@Data
public class MoXieResult {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 魔蝎结果码 1：成功 2：失败 3：查询中
     */
    private String code;
    /**
     * 回调信息
     */
    private String message;
    /**
     * MONGO账单Id
     */
    private String billId;
    /**
     * MONGO报告Id
     */
    private String reportId;
}
