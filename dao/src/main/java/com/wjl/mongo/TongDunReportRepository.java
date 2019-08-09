package com.wjl.mongo;

import com.wjl.model.mongo.TongDunReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Demo class
 *
 * @author mayue
 * @date 2018/3/5
 */
public interface TongDunReportRepository extends MongoRepository<TongDunReport, String> {

    /**
     * 通过ID找到报告
     * @param verifyId
     * @return
     */
    TongDunReport findById(String verifyId);

}
