package com.wjl.model.constant;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * 常量
 *
 * @author 秦瑞华
 *
 */
public class Constant {


	/**
	 * 规则引擎审核结果<br/>
	 * -1：不可审核；0：待审核；1：通过；2：预警；3：拦截；
	 */
	public static final Integer AutoAuditResult_No 		= -1;
	public static final Integer AutoAuditResult_Wait 	= 0;
	public static final Integer AutoAuditResult_OK 		= 1;
	public static final Integer AutoAuditResult_Warn 	= 2;
	public static final Integer AutoAuditResult_Fail 	= 3;
	public static ImmutableMap<Integer, String> AutoAuditResultMap;
    /**
     * 高优先级渠道
     */
	public static ImmutableSet<String> HighWeightSourceSet;

    //逾期情况 状态值，自定义大小 比较大小
    public static ImmutableMap<String ,Integer> OverdueCodeMap=null;

	//常量初始化位置
	static {
		ImmutableMap.Builder<Integer, String> builder = ImmutableMap.builder();
		builder.put(AutoAuditResult_No, "不可自动审核");
		builder.put(AutoAuditResult_Wait, "待自动审核");
		builder.put(AutoAuditResult_OK, "自动审核通过");
		builder.put(AutoAuditResult_Warn, "自动审核预警");
		builder.put(AutoAuditResult_Fail, "自动审核拦截");
		AutoAuditResultMap = builder.build();


		ImmutableSet.Builder<String> highWeightSourceSetBuilder = ImmutableSet.builder();
		highWeightSourceSetBuilder.add("beikeqianbao");
		highWeightSourceSetBuilder.add("beikeqianbao02");
        HighWeightSourceSet = highWeightSourceSetBuilder.build();
        ImmutableMap.Builder<String,Integer> OverdueCodeBuilder=ImmutableMap.builder();
        OverdueCodeBuilder.put("M1",1);
        OverdueCodeBuilder.put("M2",2);
        OverdueCodeBuilder.put("M3",3);
        OverdueCodeBuilder.put("M3+",4);
        OverdueCodeBuilder.put("M4",5);
        OverdueCodeBuilder.put("M5",6);
        OverdueCodeBuilder.put("M6",7);
        OverdueCodeBuilder.put("M6+",8);
        OverdueCodeMap=OverdueCodeBuilder.build();
    }


    /**
     * 同盾
     */
    public static final String TONGDUN_DATA="tongdun";
    /**
     * 手机运营商Bill
     */
    public static final String MOBILE_OPERATOR_BILL="mobileOperatorBill";
    /**
     * 数据丢失
     */
    public static final String DATA_LOSE="dataLose";
    /**
     * 腾讯云
     */
    public static final String TENCENT_CLOUD_DATA="tencentData";
    /**
     * 公共规则计算
     */
    public static final String GONGGO="gongon";
    /**
     * 手机运营商Report
     */
    public static final String MOBILE_OPERATOR_REPORT="mobileOperatorReport";

}