package com.wjl.mongo;

import com.wjl.model.mongo.MoXieAliPayBillData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MoxieAliPayBillDataRepository extends MongoRepository<MoXieAliPayBillData,String> {

    /**
     * 根据ID查询支付宝信息
     * @param verifyId
     * @return
     */
    //MoXieAliPayBillData findByVerifyId(Long verifyId);

    /**
     * 根据reportId
     *
     * @param reportId
     * @return
     */
    MoXieAliPayBillData findById(String reportId);
}
