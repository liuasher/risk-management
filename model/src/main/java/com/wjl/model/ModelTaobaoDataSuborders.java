package com.wjl.model;

import lombok.Data;

import java.util.Date;

@Data
public class ModelTaobaoDataSuborders {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private String tradeId;

    /**
     * 二级目录的id
     */
    private String cidLevel2;

    /**
     * 一级目录的id
     */
    private String cidLevel1;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品原价
     */
    private String original;

    /**
     * 商品id
     */
    private String itemId;

    /**
     * 商品图片
     */
    private String itemPic;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 淘宝账号在魔蝎科技中的映射ID
     */
    private String mappingId;

    /**
     * 商品链接
     */
    private String itemUrl;

    /**
     * 二级目录的名称
     */
    private String cnameLevel2;

    /**
     * 商品真实交易价格
     */
    private String realTotal;

    /**
     * 一级目录的名称
     */
    private String cnameLevel1;

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
