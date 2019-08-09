package com.wjl.mapper;

import com.wjl.model.MoxieAliPay;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 支付宝mapper
 *
 * @author mayue
 * @date 2018/4/3
 */
@Repository
@Mapper
public interface MoXieAliPayMapper {

    /**
     * 保存支付宝请求信息
     *
     * @param moxieAliPay 魔蝎支付宝请求信息
     */
    @Insert("INSERT INTO moxie_alibaba_pay (\n" +
            "\tuser_id,\n" +
            "\treport_id,\n" +
            "\ttype,\n" +
            "\tstatus,\n" +
            "\ttask_id,\n" +
            "\tmessage,\n" +
            "\tcreate_time,\n" +
            "\tupdate_time,\n" +
            "\tquery_time,\n" +
            "\tmysql_status,\n" +
            "\tidentification\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{userId},\n" +
            "\t\t#{reportId},\n" +
            "\t\t#{type},\n" +
            "\t\t#{status},\n" +
            "\t\t#{taskId},\n" +
            "\t\t#{message},\n" +
            "\t\t#{createTime},\n" +
            "\t\t#{updateTime},\n" +
            "\t\t#{queryTime},\n" +
            "\t\t#{mysqlStatus},\n" +
            "\t\t#{identification})\n")
    void saveAliPayRequestInfo(MoxieAliPay moxieAliPay);

    /**
     * 查询已经保存在数据库中的支付宝请求信息
     *
     * @param userId 用户ID
     * @param type   类型
     * @param taskId taskId
     * @return MoxieAliPay 魔蝎支付宝请求信息
     */
    @Select("SELECT\n" +
            "\tb.id,\n" +
            "\tb.user_id as userId,\n" +
            "\tb.status,\n" +
            "\tb.type,\n" +
            "\tb.create_time as createTime,\n" +
            "\tb.task_id,\n" +
            "\tb.identification\n" +
            "FROM\n" +
            "\tmoxie_alibaba_pay b\n" +
            "WHERE\n" +
            "\tb.user_id = #{userId}\n" +
            "\tAND b.type = #{moxieType}\n" +
            "\tAND b.task_id = #{taskId}\n" +
            "ORDER BY\n" +
            "\tb.create_time DESC\n" +
            "LIMIT 1")
    MoxieAliPay findByUserIdAndTypeAndTaskId(@Param("userId") Long userId, @Param("moxieType") Integer type, @Param("taskId") String taskId);

    /**
     * 查询未被处理过的支付宝报告
     *
     * @return List<MoxieAliPay>
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.user_id,\n" +
            "\ta.report_id,\n" +
            "\ta.type,\n" +
            "\ta.status,\n" +
            "\ta.task_id,\n" +
            "\ta.message,\n" +
            "\ta.create_time,\n" +
            "\ta.update_time,\n" +
            "\ta.query_time,\n" +
            "\ta.mysql_status,\n" +
            "\ta.identification\n" +
            "FROM\n" +
            "\tmoxie_alibaba_pay a\n" +
            "WHERE\n" +
            "\ta.report_id IS NOT NULL\n" +
            "\tAND a.mysql_status IS NULL\n" +
            "\tAND a.type = 3\n" +
            "\tAND a.status = 2")
    List<MoxieAliPay> findReportWait();

    /**
     * 查询未被处理过的支付宝信息
     *
     * @return List<MoxieAliPay>
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.user_id,\n" +
            "\ta.report_id,\n" +
            "\ta.type,\n" +
            "\ta.status,\n" +
            "\ta.task_id,\n" +
            "\ta.message,\n" +
            "\ta.create_time,\n" +
            "\ta.update_time,\n" +
            "\ta.query_time,\n" +
            "\ta.mysql_status,\n" +
            "\ta.identification\n" +
            "FROM\n" +
            "\tmoxie_alibaba_pay a\n" +
            "WHERE\n" +
            "\ta.report_id IS NOT NULL\n" +
            "\tAND a.mysql_status IS NULL\n" +
            "\tAND a.type = 2\n" +
            "\tAND a.status = 2")
    List<MoxieAliPay> findBillWait();

    /**
     * 更新魔蝎支付宝
     *
     * @param id        主键ID
     * @param reportId  报告id
     * @param queryTime 查询时间
     */
    @Update("UPDATE moxie_alibaba_pay a\n" +
            "SET a.report_id = #{reportId},\n" +
            "  a.query_time = #{queryTime}\n" +
            "WHERE\n" +
            "\ta.id = #{id}")
    void updateReportIdAndQueryTime(@Param("id") Long id, @Param("reportId") String reportId, @Param("queryTime") Date queryTime);

    /**
     * 修改支付宝model数据库状态
     *
     * @param id          id
     * @param mysqlStatus 数据库状态吗，1表示model导入mysql成功，2表示失败
     */
    @Update("UPDATE moxie_alibaba_pay SET mysql_status = #{mysqlStatus} where id = #{id}")
    void updateMysqlStatus(@Param("id") Long id, @Param("mysqlStatus") Integer mysqlStatus);
}
