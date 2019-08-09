package com.wjl.mapper;

import com.wjl.model.RiskRulesHit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 用户风控击中指标
 * @date 2018/4/10
 */
@Mapper
@Repository
public interface RiskRulesHitMapper {
    /**
     * 保存用户风控击中指标
     * @param riskRulesHit
     */
    @Insert("insert into risk_rules_hit(" +
            "user_id," +
            "credit_id," +
            "identification," +
            "index_code," +
            "value" +
            ")" +
            "VALUES(" +
            "#{userId}," +
            "#{creditId}," +
            "#{identification}," +
            "#{indexCode}," +
            "#{value}" +
            ")")
    void save(RiskRulesHit riskRulesHit);
}
