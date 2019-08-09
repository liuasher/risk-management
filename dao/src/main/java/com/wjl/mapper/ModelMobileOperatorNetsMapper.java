package com.wjl.mapper;

import com.wjl.model.ModelMobileOperatorNets;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface ModelMobileOperatorNetsMapper {
    /**
     * 根据用户Id查找之前的记录
     * @param userId
     * @return
     */
    @Select("SELECT" +
            " a.cell_phone," +
            " a.create_time," +
            " a.id," +
            " a.net_type," +
            " a.place," +
            " a.query_time," +
            " a.start_time," +
            " a.subflow," +
            " a.subtotal," +
            " a.identification," +
            " a.update_time," +
            " a.user_id," +
            " a.use_time," +
            " a.verify_count" +
            " FROM" +
            " model_mobile_operator_nets a" +
            " WHERE" +
            " a.user_id = #{userId};")
    List<ModelMobileOperatorNets> findByUserId(Long userId);

    /**
     * 删除上一次该userID的记录
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_mobile_operator_nets" +
            " WHERE" +
            " user_id = #{userId};")
    void delete(Long userId);

    @Insert("INSERT INTO model_mobile_operator_nets" +
            " (user_id,update_time,cell_phone,place,subtotal,start_time,net_type,subflow,use_time,verify_count,query_time,create_time,identification)"+
            " VALUES" +
            " (#{userId},#{updateTime},#{cellPhone},#{place},#{subtotal},#{startTime},#{netType},#{subflow},#{useTime},#{verifyCount},#{queryTime},#{createTime},#{identification});")
    void save(ModelMobileOperatorNets modelMobileOperatorNets);
}
