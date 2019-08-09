package com.wjl.properties;

import lombok.Data;

/**
 * 审核模型类型
 * @author hqh
 */
@Data
public class AuditModelType {
    /**
     * 所属系统ID
     */
    private Integer systemId;
    /**
     * 1.规则模型；2.评分卡模型(必填)
     */
    private Integer modelType;
    /**
     * 模型ID(必填)
     */
    private Integer modelId;
    /**
     * 秘钥
     */
    private String  secret;
}
