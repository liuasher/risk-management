package com.wjl.mongo;


import com.wjl.model.mongo.CreditCardMailBillData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 魔蝎邮箱信用卡账单
 * @author LINJX
 */

public interface CreditCardMailBillDataRepository extends MongoRepository<CreditCardMailBillData,String>{

    CreditCardMailBillData findById(String reportId);

}
