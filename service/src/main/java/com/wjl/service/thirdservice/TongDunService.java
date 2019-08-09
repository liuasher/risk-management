package com.wjl.service.thirdservice;

import com.wjl.model.TongDunRequestInfo;
import com.wjl.model.mongo.TongDunReport;
import com.wjl.model.mq.TongDunBean;

/**
 * 调用同盾接口，存取报告等
 *
 * @author mayue
 * @date 2018/4/2
 */
public interface TongDunService {

    /**
     * 同盾认证
     * @param requestArgs 同盾需要参数
     * @param submitUrl 提交url
     * @return 主键
     */
    Long audit(TongDunBean requestArgs, String submitUrl);

    /**
     * 查询报告
     * @param requestArgs 同盾需要参数
     * @param queryUrl 查询url
     * @param id mongo主键id
     * @return verifyId
     */
    String query(TongDunBean requestArgs, String queryUrl, Long id);

    /**
     * 通过主键id查询报告id
     * @param id 主键
     * @return reportId
     */
    TongDunRequestInfo findById(Long id);

    /**
     * 通过认证ID获取报告
     * @param verifyId 认证ID
     * @return 同盾报告
     */
    TongDunReport get(String verifyId);
}
