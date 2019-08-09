package com.wjl.commom.enumeration;

import lombok.Getter;

/**
 * 同盾字典
 *
 * @author mayue
 * @date 2018/3/7
 */
@Getter
public enum TongDunEnum {
    /**
     * 状态：0：初始；1：已提交；2：已查询
     */
    STATUS_INIT("0", "初始状态"),
    STATUS_SUBMIT("1", "已提交"),
    STATUS_QUERY("2", "已查询"),
    APP_NAME_ANDROID("android", "android"),
    APP_NAME_IOS("ios", "ios");

    private String code;
    private String message;

    TongDunEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
