package com.wjl.mapper;

import com.wjl.model.MoxieCreditCardMail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author LINJX
 * @description
 * @date 2018/4/2 19:52
 */
@Mapper
@Repository
public interface MoXieCreditCardMailMapper {

    /**
     * 根据用户id和类型查找
     * @param userId 用户ID
     * @param type 类型
     * @return MoxieCreditCardMail
     */
    @Select("SELECT "+
            " id,user_id,report_id,email_id,type,status,task_id,message,create_time,update_time,query_time,mysql_status,identification"+
            " FROM"+
            " moxie_creditcard_bill b"+
            " WHERE"+
            " b.user_id = #{userId}"+
            " AND b.type = #{type}"+
            " AND b.task_id = #{taskId}"+
            " ORDER BY"+
            " b.create_time DESC"+
            " LIMIT 1")
    MoxieCreditCardMail findByUserIdAndTypeAndTaskId(@Param("userId") Long userId, @Param("type") Integer type,@Param("taskId") String taskId);

    /**
     * 根据授信id修改魔蝎信用卡认证数据为失败
     * @param userId 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update moxie_creditcard_bill b set b.status = 3 where b.user_id = #{userId}")
    void updateMoXieBankFailed(@Param("userId") Long userId);

    /**
     * 查询Bill等待
     * @return 查询Bill等待的列表
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.user_id,\n" +
            "\ta.report_id,\n" +
            "\ta.email_id,\n" +
            "\ta.type,\n" +
            "\ta.`status`,\n" +
            "\ta.task_id,\n" +
            "\ta.message,\n" +
            "\ta.create_time,\n" +
            "\ta.update_time,\n" +
            "\ta.query_time,\n" +
            "\ta.mysql_status,\n" +
            "\ta.identification\n" +
            "FROM\n" +
            "\tmoxie_creditcard_bill a\n" +
            "WHERE\n" +
            "\ta.report_id IS not NULL\n" +
            "AND a.mysql_status is null\n" +
            "AND a.type = 2\n" +
            "AND a. STATUS = 2")
    List<MoxieCreditCardMail> findBillWait();

    /**
     * 查询Report等待
     *@return 查询Report等待的列表
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.user_id,\n" +
            "\ta.report_id,\n" +
            "\ta.email_id,\n" +
            "\ta.type,\n" +
            "\ta.`status`,\n" +
            "\ta.task_id,\n" +
            "\ta.message,\n" +
            "\ta.create_time,\n" +
            "\ta.update_time,\n" +
            "\ta.query_time,\n" +
            "\ta.mysql_status,\n" +
            "\ta.identification\n" +
            "FROM\n" +
            "\tmoxie_creditcard_bill a\n" +
            "WHERE\n" +
            "\ta.report_id IS not NULL\n" +
            "AND a.mysql_status is null\n" +
            "AND a.type = 3\n" +
            "AND a. STATUS = 2")
    List<MoxieCreditCardMail> findReportWait();


    /**
     * 更新报告id和查询时间
     * @param id 用户id
     * @param reportId 报告id
     * @param queryTime 查询时间
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update moxie_creditcard_bill a set a.report_id = #{reportId}, a.query_time=#{queryTime} where a.id = #{id} ")
    void updateReportIdAndQueryTime(@Param("id") Long id, @Param("reportId") String reportId, @Param("queryTime") Long queryTime);

    /**
     * 根据taskId查找reportId为空的报告
     * @param taskId 任务id
     * @return 结果列表
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.user_id,\n" +
            "\ta.report_id,\n" +
            "\ta.email_id,\n" +
            "\ta.type,\n" +
            "\ta.`status`,\n" +
            "\ta.task_id,\n" +
            "\ta.message,\n" +
            "\ta.create_time,\n" +
            "\ta.update_time,\n" +
            "\ta.query_time,\n" +
            "\ta.mysql_status,\n" +
            "\ta.identification\n" +
            "FROM\n" +
            "\tmoxie_creditcard_bill a\n" +
            "WHERE\n" +
            "\ta.report_id IS NULL\n" +
            "AND a.type = 3\n" +
            "AND a. STATUS = 3\n" +
            "AND a.task_id = #{taskId}\n")
    List<MoxieCreditCardMail> findReportIsEmptyByTaskId(@Param("taskId") String taskId);

    /**
     * 保存接口
     * @param moxieCreditCardMail 魔蝎信用卡邮箱实体
     */
    @Insert("INSERT INTO moxie_creditcard_bill ("+
            " create_time,"+
            " task_id,"+
            " STATUS,"+
            " type,"+
            " user_id,"+
            " identification,"+
            " email_id,"+
            " report_id,"+
            " message"+
            " )"+
            " VALUES"+
            " ("+
	        " #{createTime},#{taskId},#{status},#{type},#{userId},#{identification},#{emailId},#{reportId},#{message})")
    void save(MoxieCreditCardMail moxieCreditCardMail);

    /**
     * 修改mysql状态
     * @param id 主键id
     * @param mysqlStatus mysql状态码
     */
    @Update("UPDATE moxie_creditcard_bill\n" +
            "SET mysql_status = #{mysqlStatus}\n" +
            "WHERE\n" +
            "\tid = #{id}")
    void updateMysqlStatus(@Param("id") Long id,@Param("mysqlStatus")Integer mysqlStatus);
}
