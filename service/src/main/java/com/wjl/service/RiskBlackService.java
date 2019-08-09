package com.wjl.service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 内部黑名单
 * @date 2018/4/10
 */
public interface RiskBlackService {
    /**
     * 在黑名单中查找手机号匹配的黑名单数量
     * @param mobile
     * @return
     */
    Integer getCountByMobile(String mobile);

    /**
     * 在黑名单中查找身份证匹配的黑名单数量
     * @param idCard
     * @return
     */
    Integer getCountByIdCard(String idCard);

}
