package com.wjl.mongo;


import com.wjl.model.mongo.MobileOperatorBill;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author hqh
 */
public interface MobileOperatorBillRepository extends MongoRepository<MobileOperatorBill, String> {

	/**
	 * 根据verifyId查询MONGO中的报告
	 * @param reportId
	 * @return
	 */
	MobileOperatorBill findById(String reportId);


}
