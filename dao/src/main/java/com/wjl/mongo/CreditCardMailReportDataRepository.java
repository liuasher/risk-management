package com.wjl.mongo;

import com.wjl.model.mongo.CreditCardMailReportData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 魔蝎邮箱信用卡报告
 * @author LINJX
 */
public interface CreditCardMailReportDataRepository extends MongoRepository<CreditCardMailReportData,String>{

    CreditCardMailReportData findById(String reportId);
}
