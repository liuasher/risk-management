package com.wjl.model.mq;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * 审核Bean
 * 
 * @author 秦瑞华
 *
 */
@Data
public class AuditBean {
	/**
	 * 授信ID
	 */
	private Long creditId;
	/**
	 * 客户ID
	 */
	private Long userId;
	/**
	 * 客户身份证号
	 */
	private String idCard;
	/**
	 * 客户手机号
	 */
	private String mobile;
	/**
	 * 工作单位
	 */
	private String companyName;
	/**
	 * 通讯录人数
	 */
	private Integer contactNum;
	/**
	 * 同盾报告Id
	 */
	private String tongDunId;
	/**
	 * 腾讯云报告Id
	 */
	private String tencentCloudId;
	/**
	 * 手机运营商Bill
	 */
	private String mobileOperatorBillId;
	/**
	 * 手机运营商Report
	 */
	private String mobileOperatorReportId;
	/**
	 * 交易字段数据
	 */
	private Map<String, Object> fieldData=Maps.newHashMap();

	/**
	 * 携带数据
	 */
	private Map<String, Object> data=Maps.newHashMap();
	/**
	 * 项目标识码
	 */
	private String identification;
	/**
	 * 用户登录次数
	 */
	private Integer ypCnt;
	/**
	 * 用户居住地：省
	 */
	private String province;
	/**
	 * 用户居住地：市
	 */
	private String city;
	/**
	 * 用户居住地：区
	 */
	private String district;
}