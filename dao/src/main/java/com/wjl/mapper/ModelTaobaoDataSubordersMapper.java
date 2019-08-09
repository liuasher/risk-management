package com.wjl.mapper;

import com.wjl.model.ModelTaobaoDataSuborders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description  淘宝data model
 * @date 2018/4/8
 */
@Mapper
@Repository
public interface ModelTaobaoDataSubordersMapper {

    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_taobao_data_suborders b"+
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
            " model_taobao_data_suborders" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelTaobaoDataSuborders
     */
    @Insert("INSERT INTO model_taobao_data_suborders(user_id,trade_id,cid_level2,cid_level1,quantity,original,item_id,item_pic," +
            "item_name,mapping_id,item_url,cname_level2,real_total,cname_level1,verify_count,create_time,update_time,identification)" +
            " VALUES" +
            "(#{userId},#{tradeId},#{cidLevel2},#{cidLevel1},#{quantity},#{original},#{itemId},#{itemPic},#{itemName}," +
            "#{mappingId},#{itemUrl},#{cnameLevel2},#{realTotal},#{cnameLevel1},#{verifyCount},#{createTime},#{updateTime},#{identification})")
    void save(ModelTaobaoDataSuborders modelTaobaoDataSuborders);
}
