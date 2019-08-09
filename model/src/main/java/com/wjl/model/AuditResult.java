package com.wjl.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;


/**
 * 审核结论
 * 
 * @author 秦瑞华
 *
 */
@Data
@Builder
public class AuditResult {
	/**
	 * 原始交易ID
	 */
	private String 	tradeId;
	/**
	 * 所属系统ID
	 */
	private Integer systemId;
	/**
	 * 交易审核用时(ms)
	 */
	private Long 	auditUse;
	/**
	 * 审核结果：0：待审核；1：通过；2：预警；3：拦截；
	 */
	private Integer auditResult;
	/**
	 * 评分卡得分
	 */
	private Double	score=0d;
	/**
	 * 存放指标字段
	 */
	private Map<String,Object> fieldData;
}
