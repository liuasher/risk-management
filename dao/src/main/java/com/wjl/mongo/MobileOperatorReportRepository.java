package com.wjl.mongo;


import com.wjl.model.mongo.MobileOperatorReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hqh
 */
public interface MobileOperatorReportRepository extends MongoRepository<MobileOperatorReport,String> {
    /**
     * 根据verifyId查询报告
     * @param reportId
     * @return
     */
    MobileOperatorReport findById(String reportId);

}

