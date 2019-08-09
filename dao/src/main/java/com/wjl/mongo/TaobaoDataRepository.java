package com.wjl.mongo;

import com.wjl.model.mongo.TaobaoData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ZHAOJP
 */
public interface TaobaoDataRepository extends MongoRepository<TaobaoData,String> {
    /**
     * 通过userId查找对象
     * @param userId
     * @return
     */
    TaobaoData findByUserId(Long userId);

}

