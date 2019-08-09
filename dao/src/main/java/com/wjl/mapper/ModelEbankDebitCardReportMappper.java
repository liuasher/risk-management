package com.wjl.mapper;

import com.wjl.model.ModelEbankDebitCardReport;
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
public interface ModelEbankDebitCardReportMappper {

    /**
     * 通过userId查找verifyCount
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_ebank_debit_card_report b"+
            " WHERE"+
            " b.user_id = #{userId}"+
            " ORDER BY"+
            " b.create_time DESC"+
            " LIMIT 1")
    Integer findVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除对象
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_ebank_debit_card_report" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelEbankDebitCardReport
     */
    @Insert("insert into model_ebank_debit_card_report(user_id,income_amty,salary_incomey,loan_iny,expensey,consumption_expensey," +
            "loan_outy,query_time,verify_count,create_time,update_time,identification)" +
            " values" +
            "(#{userId},#{incomeAmty},#{salaryIncomey},#{loanIny},#{expensey},#{consumptionExpensey},#{loanOuty}," +
            "#{queryTime},#{verifyCount},#{createTime},#{updateTime},#{identification})")
    void save(ModelEbankDebitCardReport modelEbankDebitCardReport);
}
