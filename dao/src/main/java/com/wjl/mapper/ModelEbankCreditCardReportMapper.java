package com.wjl.mapper;

import com.wjl.model.ModelEbankCreditCardReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
@Mapper
@Repository
public interface ModelEbankCreditCardReportMapper {

    /**
     * 通过userId查找verifyCount
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_ebank_credit_card_report b"+
            " WHERE"+
            " b.user_id = #{userId}"+
            " ORDER BY"+
            " b.create_time DESC"+
            " LIMIT 1")
    Integer findTopVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除对象
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_ebank_credit_card_report" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelEbankCreditCardReport
     */
    @Insert("insert into model_ebank_credit_card_report(user_id,creditcard_limit,total_can_use_consume_limit,repay_num," +
            "repay_ratio,delay_tag,delay_status,query_time,verify_count,create_time,update_time,identification)" +
            " values" +
            "(#{userId},#{creditcardLimit},#{totalCanUseConsumeLimit},#{repayNum},#{repayRatio}," +
            "#{delayTag},#{delayStatus},#{queryTime},#{verifyCount},#{createTime},#{updateTime},#{identification})")
    void save(ModelEbankCreditCardReport modelEbankCreditCardReport);
}
