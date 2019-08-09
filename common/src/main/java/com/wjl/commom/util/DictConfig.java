package com.wjl.commom.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * 字典配置
 *
 * @author 秦瑞华
 *
 */
public class DictConfig {

    /**
     * 同盾失信名单条目列表
     */
    public static ImmutableSet<String> Tongdun_Broken_Items = ImmutableSet.of(
            "身份证命中法院失信名单",
            "身份证命中法院执行名单",
            "身份证命中法院结案名单",
            "身份证命中犯罪通缉名单",
            "身份证命中欠款公司法人代表名单",
            "身份证命中车辆租赁违约名单");
    /**
     * 同盾高风险关注名单条目列表
     */
    public static ImmutableSet<String> Tongdun_HighRisk_Items = ImmutableSet.of(
            "身份证命中高风险关注名单",
            "手机号命中高风险关注名单"
    );

    /**
     * 同盾 互为联系人条目列表
     */
    public static ImmutableSet<String> Tongdun_IsContact_Items = ImmutableSet.of(
            "3个月内申请人身份证作为联系人身份证出现的次数大于等于2",
            "3个月内申请人手机号作为联系人手机号出现的次数大于等于2"
    );

    /**
     * 同盾 虚拟设备条目列表
     */
    public static ImmutableSet<String> Tongdun_FackDevice_Items = ImmutableSet.of(
            "借款设备模拟器识别",
            "借款设备作弊工具识别"
    );

    /**
     * 同盾 限制地区
     */
    public static ImmutableSet<String> Tongdun_RestrictedArea = ImmutableSet.of(
            "新疆", "西藏", "香港", "澳门", "台湾","内蒙古"
            // "广西", "重庆"....
    );
    /**
     * 敏感行业
     */
    public static ImmutableSet<String> SensitiveIndustry = ImmutableSet.of(
            "公安", "派出所", "警", "检察院", "监狱", "赌场", "新闻",
            "传媒", "推广", "监察", "部队", "法院", "司法局", "检察院", "武装",
            "边防", "法制", "报社", "电视台", "律师", "城管", "执法",
            "治安", "防控"
    );

    /**
     * 3个月内身份证关联多个申请信息
     */
    public static ImmutableSet<String> Tongdun_IdCard_And_ApplyFor = ImmutableSet.of(
            "3个月内身份证关联多个申请信息", "3个月内申请信息关联多个身份证");

    public static ImmutableSet<String> Tongdun_IdCard_And_Equipment = ImmutableSet.of(
            "7天内身份证使用过多设备进行申请", "1天内身份证使用过多设备进行申请",
            "1个月内身份证使用过多设备进行申请");

    public static ImmutableSet<String> UnNormalContactManKey = ImmutableSet.of(
            "贷", "借", "钱", "黑户", "套现", "中介", "养卡", "债", "催", "财富", "宜信", "平安", "陆金所", "恒昌", "达飞",
            "用钱宝", "理财", "闪电周转", "金融", "专线电话", "买单侠", "审批", "现金", "法院", "公安", "派出所");

    public static ImmutableSet<String> FamilyContactManKey = ImmutableSet.of("爸", "父", "爹", "妈", "母", "婆", "夫",
            "妻", "爱", "亲", "爷", "奶", "公", "侄", "叔", "伯", "舅", "哥", "姐", "弟", "兄", "妹", "姨", "姑", "孙", "嫂");

    /**
     * 自由 身份证命中区域(县、区)
     */
    public static ImmutableMap<String, String> IdCardRestrictedArea = null;


    }

