package com.wjl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * app错误码记录表
 * @author hqh
 */
@Data
public class LogRiskManagementService {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 状态码
     */
    private String code;

    /**
     * 创建时间
     */
    private Date createTime;
}
