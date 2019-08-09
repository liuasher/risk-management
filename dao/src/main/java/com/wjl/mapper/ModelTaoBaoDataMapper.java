package com.wjl.mapper;

import com.wjl.model.ModelTaobaoData;
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
public interface ModelTaoBaoDataMapper {

    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_taobao_data b"+
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
            " model_taobao_data" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelTaobaoData
     */
    @Insert("INSERT INTO model_taobao_data(user_id,real_name,phone_number,verify_count,query_time,create_time,update_time,identification)" +
            " VALUES" +
            "(#{userId},#{realName},#{phoneNumber},#{verifyCount},#{queryTime},#{createTime},#{updateTime},#{identification})")
    void save(ModelTaobaoData modelTaobaoData);
}
