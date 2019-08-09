package com.wjl.model.mq;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Map;

/**
 * 输入交易
 * 
 * @author 秦瑞华
 *
 */
@Data
public class Txn {

	/**
	 * 密钥(必填)
	 */
	private String	secret;

	/**
	 * 原始交易ID(必填)
	 */
	@Indexed
	private String	tradeId;

	/**
	 * 原始交易时间(必填)
	 */
	private Long	tradeTime;

	/**
	 * 数据缺失,审核所需的数据未准备好（如：规定时限内第三方同盾、运营商未查询到数据）
	 */
	private Boolean dataLost=false;

	/**
	 * 应用系统ID(必填)
	 */
	private Integer systemId;

	/**
	 * 模型类型：1.规则模型；2.评分卡模型(必填)
	 */
	private Integer modelType;

	/**
	 * 模型ID(必填)
	 */
	private Integer modelId;

	/**
	 * 模型名称(不填)
	 */
	private String	modelName;


	/**
	 * 交易字段数据(必填)
	 */
	private Map<String, Object> data= Maps.newHashMap();

	/**
	 * 交易接收时间
	 */
	private Long 	auditStart;

	/**
	 * 交易审核完毕时间
	 */
	private Long 	auditEnd;

	/**
	 * 交易审核用时(ms)
	 */
	private Long 	auditUse;

	/**
	 * 审核结果：0：待审核；1：预警；2：拦截；3：审核通过；
	 */
	private Integer auditResult;
	/**
	 * 风险得分,0 -> 100
	 */
	private Integer riskScore;
	/**
	 * 风险等级,0 -> 4
	 */
	private Integer riskLevel;

	private Double	score=0d;


}
