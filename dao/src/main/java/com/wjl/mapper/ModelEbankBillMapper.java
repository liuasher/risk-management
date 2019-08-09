package com.wjl.mapper;

import com.wjl.model.ModelEbankBill;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 网银model
 * @date 2018/4/4
 */
@Mapper
@Repository
public interface ModelEbankBillMapper {

    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_ebank_bill b"+
            " WHERE"+
            " b.user_id = #{userId}"+
            " ORDER BY"+
            " b.create_time DESC"+
            " LIMIT 1")
    Integer findTopVerifyCountByUserId(Long userId);

    /**
     * 通过userId删除数据
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_ebank_bill" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelEbankBill
     */
    @Insert("INSERT INTO model_ebank_bill" +
            "(user_id,card_num,bill_id,trans_date,post_date,description,amount_money,currency_type,balance,category,trans_addr,trans_method,trans_channel,opposite_card_no,opposite_bank,remark,card_type,verify_count,query_time,create_time,update_time,identification)"+
            " VALUES" +
            "(#{userId},#{cardNum},#{billId},#{transDate},#{postDate},#{description},#{amountMoney},#{currencyType},#{balance},#{category},#{transAddr},#{transMethod},#{transChannel},#{oppositeCardNo},#{oppositeBank},#{remark},#{cardType},#{verifyCount},#{queryTime},#{createTime},#{updateTime},#{identification})")
    void save(ModelEbankBill modelEbankBill);
}
