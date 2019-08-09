package com.wjl.mapper;

import com.wjl.model.ModelTaobaoReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 淘宝report model
 * @date 2018/4/8
 */
@Mapper
@Repository
public interface ModelTaobaoReportMapper {

    /**
     * 通过userId查找第一条数据
     * @param userId
     * @return
     */
    @Select("SELECT "+
            "b.verify_count as verifyCount"+
            " FROM"+
            " model_taobao_report b"+
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
            " model_taobao_report" +
            " WHERE" +
            " user_id = #{userId};")
    void deleteByUserId(Long userId);

    /**
     * 保存对象
     * @param modelTaobaoReport
     */
    @Insert("INSERT INTO model_taobao_report(user_id,taobao_name,taobao_phone_number,huai_bei_limit,huai_bei_can_use_limit,verify_count," +
            "create_time,update_time,query_time,identification)" +
            " VALUES" +
            "(#{userId},#{taobaoName},#{taobaoPhoneNumber},#{huaiBeiLimit},#{huaiBeiCanUseLimit},#{verifyCount},#{createTime}," +
            "#{updateTime},#{queryTime},#{identification})")
    void save(ModelTaobaoReport modelTaobaoReport);
}
