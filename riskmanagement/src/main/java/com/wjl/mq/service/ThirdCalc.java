package com.wjl.mq.service;


import com.wjl.model.mq.AuditBean;

import java.util.Map;


/**
 * 三方指标计算
 * 
 * @author 秦瑞华
 *
 */
public interface ThirdCalc {

	/**
	 * * 指标计算
	 *
	 * @param fieldValueMap 字段计算结果
	 * @param auditBean 交易记录
	 * @param data 第三方数据
	 */
	void calc(Map<String, Object> fieldValueMap, AuditBean auditBean, Map<String, Object> data);

}
