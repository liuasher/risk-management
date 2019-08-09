package com.wjl.model;

import lombok.Data;

import java.util.Date;

@Data
public class ModelTaobaoDataTradedetail {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     *
     */
    private Long dataId;

    /**
     * 订单id
     */
    private String tradeId;

    /**
     * 订单时间
     */
    private Date tradeCreatetime;

    /**
     * 交易状态中文
     */
    private String tradeText;

    /**
     * 订单金额
     */
    private String actualFee;

    /**
     * 店铺名称
     */
    private String sellerShopname;

    /**
     * 淘宝账号在魔蝎科技中的映射Id
     */
    private String mappingId;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 卖家昵称
     */
    private String sellerNick;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 项目标识
     */
    private String identification;

    /**
     * 认证次数
     */
    private Integer verifyCount;
}
