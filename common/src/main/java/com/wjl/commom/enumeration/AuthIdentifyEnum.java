package com.wjl.commom.enumeration;

import lombok.Getter;

/**
 * @Description :调用魔蝎实名认证返回状态码
 * @author : 赵聪
 * @date : 2018/2/27 17:58
 */
@Getter
public enum AuthIdentifyEnum {
    SUCCESS(0,"认证成功"),
    Fail(111,"请求失败"),
    WRONG_CREDITCARD_ID(1,"认证信息不一致"),
    NOMESSAGE(2,"认证信息不存在"),
    ERRO(9,"其他异常");


    private Integer code;
    private String message;

    AuthIdentifyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static String getAuthIdentifyEnumCode(Integer code) {
        for (AuthIdentifyEnum co : AuthIdentifyEnum.values()) {
            if (co.code.equals(code)) {
                return co.message;
            }
        }
        return null;
    }
}
