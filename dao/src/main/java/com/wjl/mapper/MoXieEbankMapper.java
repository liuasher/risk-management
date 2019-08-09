package com.wjl.mapper;

import com.wjl.model.MoXieEbank;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/2
 */
@Mapper
@Repository
public interface MoXieEbankMapper {

    /**
     * 保存接口
     * @param moXieEbank
     */
    @Insert("insert into moxie_ebank(user_id,report_id,type,status,task_id,message," +
            "create_time,update_time,apply_time,query_time,mysql_status,identification) " +
            "values(#{userId},#{reportId},#{type},#{status},#{taskId},#{message},#{createTime},#{updateTime},#{applyTime},#{queryTime},#{mysqlStatus},#{identification})")
    void save(MoXieEbank moXieEbank);

    /**
     * 通过userId和type查找最新的一条数据
     * @param userId 用户ID
     * @param type 类型
     * @return
     */
    @Select("select id,user_id as userId,report_id as reportId,type,status,task_id as taskId,message,create_time as createTime,update_time as updateTime," +
            "apply_time as applyTime,query_time as queryTime,mysql_status as mysqlStatus,identification from moxie_ebank where user_id = #{userId} and type = #{moxieType} " +
            "and identification = #{identification} and task_id = #{taskId} order by create_time desc limit 1")
    MoXieEbank findByUserIdAndType(@Param("userId") Long userId, @Param("moxieType") Integer type, @Param("identification") String identification,@Param("taskId") String taskId);


    /**
     * 查询Bill等待
     */
    @Select("select id,user_id as userId,report_id as reportId,type,status,task_id as taskId,message,create_time as createTime,update_time as updateTime," +
            "apply_time as applyTime,query_time as queryTime,mysql_status as mysqlStatus,identification from moxie_ebank where report_id is not null and type = 2 and status = 2 and mysql_status is null")
    List<MoXieEbank> findBillWait();

    /**
     * 查询Report等待
     *
     */
    @Select("select id,user_id as userId,report_id as reportId,type,status,task_id as taskId,message,create_time as createTime,update_time as updateTime," +
            "apply_time as applyTime,query_time as queryTime,mysql_status as mysqlStatus,identification from moxie_ebank where report_id is not null and type = 3 and status = 2 and mysql_status is null")
    List<MoXieEbank> findReportWait();

    /**
     * 通过id更新reportId和queryTime
     * @param id
     * @param reportId
     * @param queryTime
     */
    @Update("update moxie_ebank m set m.report_id = #{reportId},m.query_time = #{queryTime} where m.id = #{id}")
    void updateReportIdAndQueryTime(@Param("id") Long id, @Param("reportId") String reportId, @Param("queryTime") Long queryTime);

    /**
     * 通过id更新mysqlStatus
     * @param id
     * @param status
     */
    @Update("update moxie_ebank m set m.mysql_status = #{status} where m.id = #{id}")
    void updateMysqlStatusById(@Param("id")Long id, @Param("status") Integer status);
}
