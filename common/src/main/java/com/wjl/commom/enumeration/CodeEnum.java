package com.wjl.commom.enumeration;

import lombok.Getter;

/**
 * CodeEnum
 * @author  hqh
 */
@Getter
public enum CodeEnum {
    /**
     * 认证成功
     */
    IDENTIFY_SUCCESS("1001", "认证成功"),
    /**
     * 认证失败
     */
    IDENTIFY_FAIL("1002", "认证失败"),
    /**
     * 认证中
     */
    IDENTIFY_PROCEESS("1003","认证中"),
    /**
     * 认证参数为空
     */
    PARAMETER_MISTAKE("1004","认证参数为空"),
    /**
     * 查询成功
     */
    QUERY_SUCCESS("2001", "查询成功"),
    /**
     * 查询失败
     */
    QUERY_FAIL("2002", "查询失败"),
    /**
     * 机审通过
     */
    AUDIT_PASS("3001","机审通过"),
    /**
     * 待人工审核
     */
    AUDIT_WAIT("3002","待人工审核"),
    /**
     * 机审拒绝
     */
    AUDIT_FAIL("3003","机审拒绝");


    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCodeEnum(String code) {
        for (CodeEnum co : CodeEnum.values()) {
            if (co.code.equals(code)) {
                return co.message;
            }
        }
        return null;
    }
}
