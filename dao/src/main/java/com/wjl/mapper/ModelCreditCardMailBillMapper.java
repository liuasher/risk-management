package com.wjl.mapper;

import com.wjl.model.ModelCreditCardMailBill;
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
public interface ModelCreditCardMailBillMapper {

    /**
     * 保存接口
     * @param modelCreditCardMailBill 信用卡账单对象
     */
    @Insert("INSERT INTO model_credit_card_mail_bill (\n" +
            "\tuser_id,\n" +
            "\tname_on_card,\n" +
            "\tmail_sender,\n" +
            "\tbill_date,\n" +
            "\tpayment_due_date,\n" +
            "\tcard_number,\n" +
            "\tbank_id,\n" +
            "\tbank_name,\n" +
            "\tcredit_limit,\n" +
            "\tnew_balance,\n" +
            "\tlast_payment,\n" +
            "\tpoint,\n" +
            "\tpoint_lose_date,\n" +
            "\tquery_time,\n" +
            "\tcreate_time,\n" +
            "\tidentification,\n" +
            "\tverify_count\n" +
            ")\n" +
            "VALUES\n" +
            "\t(#{userId},#{nameOnCard},#{mailSender},#{billDate},#{paymentDueDate},#{cardNumber},#{bankId},#{bankName},#{creditLimit},#{newBalance},#{lastPayment},#{point},#{pointLoseDate},#{queryTime},#{createTime},#{identification},#{verifyCount})")
    void save(ModelCreditCardMailBill modelCreditCardMailBill);

    /**
     * 通过userId查找
     * @param userId 用户id
     * @return 查找到的list集合
     */
    @Select("SELECT\n" +
            "\tuser_id,\n" +
            "\tname_on_card,\n" +
            "\tmail_sender,\n" +
            "\tbill_date,\n" +
            "\tpayment_due_date,\n" +
            "\tcard_number,\n" +
            "\tbank_id,\n" +
            "\tbank_name,\n" +
            "\tcredit_limit,\n" +
            "\tnew_balance,\n" +
            "\tlast_payment,\n" +
            "\tpoint,\n" +
            "\tpoint_lose_date,\n" +
            "\tquery_time,\n" +
            "\tcreate_time,\n" +
            "\tidentification,\n" +
            "\tverify_count\n" +
            "FROM\n" +
            "\tmodel_credit_card_mail_bill\n" +
            "WHERE\n" +
            "\tuser_id = #{userId}")
    List<ModelCreditCardMailBill> findByUserId(Long userId);

    /**
     * 根据userId删除记录
     * @param userId 用户id
     */
    @Delete("DELETE\n" +
            "FROM\n" +
            "\tmodel_credit_card_mail_bill\n" +
            "WHERE\n" +
            "\tuser_id = #{userId}")
    void deleteByUserId(Long userId);
}
