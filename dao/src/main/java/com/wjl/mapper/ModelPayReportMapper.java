package com.wjl.mapper;

import com.wjl.model.ModelPayReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Demo class
 *
 * @author mayue
 * @date 2018/4/3
 */
@Mapper
@Repository
public interface ModelPayReportMapper {
    /**
     * 通过userId查询用户的支付宝报告
     *
     * @param userId 用户ID
     * @return ModelPayReport 支付宝报告model
     */
    @Select("SELECT\n" +
            "\tuser_id,\n" +
            "\tcredit_id,\n" +
            "\tphone_number,\n" +
            "\thuabai_limit,\n" +
            "\tfund,\n" +
            "\tzhao_cai_bao,\n" +
            "\tcun_jin_bao,\n" +
            "\ttaobao_finance,\n" +
            "\tbalance,\n" +
            "\tyuebao,\n" +
            "\tidentification,\n" +
            "\tquery_time,\n" +
            "\tverify_count,\n" +
            "\tcreate_time,\n" +
            "\tupdate_time\n" +
            "FROM\n" +
            "\tmodel_pay_report\n" +
            "WHERE\n" +
            "\tuser_id = #{userId}")
    ModelPayReport findByUserId(Long userId);

    /**
     * 添加支付报告
     *
     * @param modelPayReport 支付宝报告model
     */
    @Insert("INSERT INTO model_pay_report (\n" +
            "\tuser_id,\n" +
            "\tcredit_id,\n" +
            "\tphone_number,\n" +
            "\thuabai_limit,\n" +
            "\tfund,\n" +
            "\tzhao_cai_bao,\n" +
            "\tcun_jin_bao,\n" +
            "\ttaobao_finance,\n" +
            "\tbalance,\n" +
            "\tyuebao,\n" +
            "\tidentification,\n" +
            "\tquery_time,\n" +
            "\tverify_count,\n" +
            "\tcreate_time,\n" +
            "\tupdate_time\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{userId},#{creditId},#{phoneNumber},#{huabaiLimit},#{fund},#{zhaoCaiBao},#{cunJinBao},#{taobaoFinance},#{balance},#{yuebao},#{identification},#{queryTime},#{verifyCount},#{createTime},#{updateTime})\n")
    void save(ModelPayReport modelPayReport);

    /**
     * 更新支付报告
     *
     * @param modelPayReport 支付宝报告model
     */
    @Update("UPDATE model_pay_report\n" +
            "SET user_id = #{userId},\n" +
            "\tcredit_id = #{creditId},\n" +
            "\tphone_number = #{phoneNumber},\n" +
            "\thuabai_limit = #{huabaiLimit},\n" +
            "\tfund = #{fund},\n" +
            "\tzhao_cai_bao = #{zhaoCaiBao},\n" +
            "\tcun_jin_bao = #{cunJinBao},\n" +
            "\ttaobao_finance = #{taobaoFinance},\n" +
            "\tbalance = #{balance},\n" +
            "\tyuebao = #{yuebao},\n" +
            "\tquery_time = #{queryTime},\n" +
            "\tverify_count = #{verifyCount},\n" +
            "\tcreate_time = #{createTime},\n" +
            "\tupdate_time = #{updateTime}\n" +
            "WHERE\n" +
            "\tid = #{id}")
    void update(ModelPayReport modelPayReport);
}
