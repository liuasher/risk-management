package com.wjl.mongo;

import com.wjl.model.mongo.EbankData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZHAOJP
 */
public interface EbankDataRepository extends MongoRepository<EbankData,String> {

    /**
     * 通过userId查找对象
     * @param userId
     * @return
     */
    EbankData findByUserId(Long userId);

}

