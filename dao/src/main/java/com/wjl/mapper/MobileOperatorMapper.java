package com.wjl.mapper;

import com.wjl.model.MobileOperator;
import com.wjl.model.MobileOperatorDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface MobileOperatorMapper {
    /**
     * 保存MobileOperator
     * @param mobileOperator
     */
    @Insert("INSERT INTO mobile_operator" +
            " (user_id,mobile,report_id,type,status,task_id,message,create_time,query_time,identification,mysql_status)"+
            " VALUES" +
            " (#{userId},#{mobile},#{reportId},#{type},#{status},#{taskId},#{message},#{createTime},#{queryTime},#{identification},#{mysqlStatus});")
    void save(MobileOperator mobileOperator);

    /**
     * 查找24小时内同一个用户id首次认证的魔蝎运营商记录
     * @param userId
     * @param type
     * @param status
     * @return
     */
    @Select("SELECT " +
            " * " +
            " FROM " +
            " mobile_operator b " +
            " WHERE " +
            " b.`status` = #{status} " +
            " AND b.type = #{type} " +
            " AND TO_DAYS(NOW()) - TO_DAYS(create_time) < 1 " +
            " AND b.user_id = #{userId} " +
            " ORDER BY " +
            " b.create_time DESC " +
            " LIMIT 1")
    MobileOperator findMobileOperator(@Param("userId") Long userId, @Param("type") Integer type, @Param("status") Integer status);

    /**
     * 根据userId以及项目标识码查找用户魔蝎task_id
     * @param userId
     * @param identification
     * @param type
     * @return
     */
    @Select("SELECT" +
            " id"+
            " mobile," +
            " task_id" +
            " FROM" +
            " mobile_operator" +
            " WHERE" +
            " user_id = #{userId}" +
            " AND identification = #{identification}" +
            " AND type = #{type}" +
            " ORDER BY" +
            " id DESC"+
            " LIMIT 1;")
    MobileOperator findByUserIdAndTokenAndTypeOrderByIdDesc(@Param("userId") Long userId,@Param("identification") String identification,@Param("type") Integer type);

    /**
     * 根据用户Id,状态码以及项目标识码查找MobileOperator
     * @param userId
     * @param type
     * @param identification
     * @param taskId
     * @return
     */
    @Select("SELECT" +
            " id," +
            " user_id,"+
            " task_id,"+
            " mobile,"+
            " identification,"+
            " STATUS" +
            " FROM" +
            " mobile_operator" +
            " WHERE" +
            " user_id = #{userId}" +
            " AND type = #{type}" +
            " AND identification = #{identification}" +
            " AND task_id = #{taskId}"+
            " ORDER BY create_time DESC LIMIT 1;")
    MobileOperator findByUserIdAndTypeAndTaskId(@Param("userId")Long userId,@Param("type")Integer type,@Param("identification") String identification,@Param("taskId")String taskId);

    /**
     * 将MONGO主键更新为mobile_operator的reportId
     * @param reportId
     * @param queryTime
     * @param id
     */
    @Update("UPDATE mobile_operator " +
            " SET report_id = #{reportId}," +
            " query_time = #{queryTime}"+
            " WHERE" +
            " id = #{id};")
    void updateReportId(@Param("reportId")String reportId, @Param("queryTime")Date queryTime,@Param("id")Long id);

    /**
     * 更新MYSQL状态码
     * @param id
     * @param mysqlStatus
     */
    @Update("UPDATE mobile_operator SET mysql_status = #{mysqlStatus} where id = #{id}")
    void updateMysqlStatus(@Param("id") Long id,@Param("mysqlStatus")Integer mysqlStatus);

    /**
     * 定时器查询Bill
     * @return
     */
    @Select(value = "select id,report_id,user_id,identification from mobile_operator a where a.report_id is not null and a.mysql_status is null and a.type = 2 and a.status = 2;")
    List<MobileOperator> findBillWait();

    /**
     * 定时器查询Report
     * @return
     */
    @Select(value = "select id,report_id,user_id,identification from mobile_operator a where a.report_id is not null and a.mysql_status is null and a.type = 3 and a.status = 2;")
    List<MobileOperator> findReportWait();


}
