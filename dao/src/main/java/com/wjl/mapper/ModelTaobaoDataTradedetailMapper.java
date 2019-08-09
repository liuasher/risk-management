package com.wjl.mapper;

import com.wjl.model.ModelTaobaoDataTradedetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 淘宝data model
 * @date 2018/4/8
 */
@Mapper
@Repository
public interface ModelTaobaoDataTradedetailMapper {

    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_taobao_data_tradedetail b"+
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
            " model_taobao_data_tradedetail" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelTaobaoDataTradedetail
     */
    @Insert("INSERT INTO model_taobao_data_tradedetail(user_id,data_id,trade_id,trade_createtime,trade_text,actual_fee," +
            "seller_shopname,mapping_id,trade_status,seller_nick,seller_id,verify_count,create_time,update_time,identification)" +
            " VALUES" +
            "(#{userId},#{dataId},#{tradeId},#{tradeCreatetime},#{tradeText},#{actualFee},#{sellerShopname}," +
            "#{mappingId},#{tradeStatus},#{sellerNick},#{sellerId},#{verifyCount},#{createTime},#{updateTime},#{identification})")
    void save(ModelTaobaoDataTradedetail modelTaobaoDataTradedetail);
}
