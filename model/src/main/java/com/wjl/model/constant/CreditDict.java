package com.wjl.model.constant;

/**
 * Created with IntelliJ IDEA.
 * Description:授权认证状态常量
 * User: zc
 * Date: 2017-12-14
 * Time: 14:32
 */

public class CreditDict {
    /**
     * 对应User表认证状态（status）
     */

    /**
     * 已认证-待审核
     */
    public static final Integer AUTO_AUDIT_RESULT_WAIT = 1;
    /**
     * 认证-未通过
     */
    public static final Integer AUTO_AUDIT_RESULT_FAIL = 2;
    /**
     * 认证-通过
     */
    public static final Integer AUTO_AUDIT_RESULT_OK = 3;
    /**
     * 对应User表借款状态
     */
    //借款状态:0:不可借款;1:可借款

    /**
     * 不可借款
     */
    public static final Integer AUTO_BORROW_RESULT_FAIL = 0;

    /**
     * 可借款
     */
    public static final Integer AUTO_BORROW_RESULT_OK = 1;

}
