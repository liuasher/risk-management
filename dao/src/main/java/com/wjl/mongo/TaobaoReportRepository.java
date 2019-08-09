package com.wjl.mongo;

import com.wjl.model.mongo.TaobaoReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZHAOJP
 */
public interface TaobaoReportRepository extends MongoRepository<TaobaoReport,String> {

}

