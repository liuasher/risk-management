package com.wjl.mapper;

import com.wjl.model.ModelCreditCardMailReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LINJX
 * @description
 * @date 2018/4/3 21:07
 */
@Mapper
@Repository
public interface ModelCreditCardMailReportMapper {

    /**
     * 保存接口
     * @param modelCreditCardMailReport model信用卡报告
     */
    @Insert("INSERT INTO model_credit_card_mail_report (\n" +
            "\tuser_id,\n" +
            "\temail,\n" +
            "\tbank_nums,\n" +
            "\tactive_cards,\n" +
            "\tcustomer_group_tag,\n" +
            "\ttotal_credit_limit,\n" +
            "\tmax_total_credit_limit,\n" +
            "\ttotal_installment_fee_amount,\n" +
            "\taverage_consume3m,\n" +
            "\taverage_consume6m,\n" +
            "\taverage_consume12m,\n" +
            "\taverage_sell_count3m,\n" +
            "\taverage_sell_count6m,\n" +
            "\taverage_sell_count12m,\n" +
            "\tquery_time,\n" +
            "\tcreate_time,\n" +
            "\tidentification,\n" +
            "\tverify_count\n" +
            ")\n" +
            "VALUES\n" +
            "\t(#{userId},#{email},#{bankNums},#{activeCards},#{customerGroupTag},#{totalCreditLimit},#{maxTotalCreditLimit},#{totalInstallmentFeeAmount},#{averageConsume3m},#{averageConsume6m},#{averageConsume12m},#{averageSellCount3m},#{averageSellCount6m},#{averageSellCount12m},#{queryTime},#{createTime},#{identification},#{verifyCount})")
    void save(ModelCreditCardMailReport modelCreditCardMailReport);

    /**
     * 根据userId查找记录
     * @param userId 用户id
     * @return 符合条件的list集合
     */
    @Select("SELECT\n" +
            "\tuser_id,\n" +
            "\temail,\n" +
            "\tbank_nums,\n" +
            "\tactive_cards,\n" +
            "\tcustomer_group_tag,\n" +
            "\ttotal_credit_limit,\n" +
            "\tmax_total_credit_limit,\n" +
            "\ttotal_installment_fee_amount,\n" +
            "\taverage_consume3m,\n" +
            "\taverage_consume6m,\n" +
            "\taverage_consume12m,\n" +
            "\taverage_sell_count3m,\n" +
            "\taverage_sell_count6m,\n" +
            "\taverage_sell_count12m,\n" +
            "\tquery_time,\n" +
            "\tcreate_time,\n" +
            "\tidentification,\n" +
            "\tverify_count\n" +
            "FROM\n" +
            "\tmodel_credit_card_mail_report\n" +
            "WHERE\n" +
            "\tuser_id = #{userId}")
    List<ModelCreditCardMailReport> findByUserId(Long userId);

    /**
     * 根据userId删除记录
     * @param userId 用户id
     */
    @Delete("DELETE\n" +
            "FROM\n" +
            "\tmodel_credit_card_mail_report\n" +
            "WHERE\n" +
            "\tuser_id = #{userId}")
    void deleteByUserId(Long userId);
}
