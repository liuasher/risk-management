package com.wjl.mongo;

import com.wjl.model.mongo.MoXieAliPayReportData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoxieAliPayReportDataRepository extends MongoRepository<MoXieAliPayReportData, String> {
    /**
     * 根据ID查询支付宝报告
     *
     * @param verifyId
     * @return
     */
    //MoXieAliPayReportData findByVerifyId(Long verifyId);

    /**
     * 根据reportId
     *
     * @param reportId
     * @return
     */
    MoXieAliPayReportData findById(String reportId);
}
