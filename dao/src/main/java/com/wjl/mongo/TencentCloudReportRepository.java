package com.wjl.mongo;


import com.wjl.model.mongo.TencentCloudReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LINJX
 */

public interface TencentCloudReportRepository extends MongoRepository<TencentCloudReport,String> {

    //TencentCloudReport findTop1ByCreditId(Long creditId);

    /**
     * 查询腾讯云报告
     * @param verifyId 认证id
     * @return 腾讯云报告
     */
    TencentCloudReport findById(String verifyId);
}