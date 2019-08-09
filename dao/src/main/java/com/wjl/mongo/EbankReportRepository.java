package com.wjl.mongo;

import com.wjl.model.mongo.EbankReport;
import com.wjl.model.mongo.TaobaoReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZHAOJP.
 */
public interface EbankReportRepository extends MongoRepository<EbankReport,String> {

}

