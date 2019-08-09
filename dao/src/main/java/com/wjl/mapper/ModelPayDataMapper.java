package com.wjl.mapper;

import com.wjl.model.ModelPayData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付宝信息操作
 *
 * @author mayue
 * @date 2018/4/3
 */
@Mapper
@Repository
public interface ModelPayDataMapper {
    /**
     * 通过userId查询用户的支付宝信息
     * @param userId
     * @return
     */
    @Select("SELECT " +
            "   user_id, " +
            "   verify_count " +
            "FROM " +
            "   model_pay_data " +
            "WHERE " +
            "   user_id = #{userId}")
    List<ModelPayData> findByUserId(Long userId);

    /**
     * 删除认证过的数据
     * @param userId
     */
    @Delete("delete from model_pay_data where user_id = #{userId}")
    void remove(Long userId);

    /**
     * 添加支付信息
     * @param modelPayData
     */
    @Insert("INSERT INTO model_pay_data (\n" +
            "\tuser_id,\n" +
            "\tcredit_id,\n" +
            "\tphone_number,\n" +
            "\tregister_time,\n" +
            "\ttrade_number,\n" +
            "\tincomeorexpense,\n" +
            "\ttrade_time,\n" +
            "\ttrade_amount,\n" +
            "\ttrade_status,\n" +
            "\tcounterparty,\n" +
            "\tcapital_status,\n" +
            "\tidentification,\n" +
            "\tquery_time,\n" +
            "\tverify_count,\n" +
            "\tcreate_time,\n" +
            "\tupdate_time\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{userId},\n" +
            "\t\t#{creditId},\n" +
            "\t\t#{phoneNumber},\n" +
            "\t\t#{registerTime},\n" +
            "\t\t#{tradeNumber},\n" +
            "\t\t#{incomeorexpense},\n" +
            "\t\t#{tradeTime},\n" +
            "\t\t#{tradeAmount},\n" +
            "\t\t#{tradeStatus},\n" +
            "\t\t#{counterparty},\n" +
            "\t\t#{capitalStatus},\n" +
            "\t\t#{identification},\n" +
            "\t\t#{queryTime},\n" +
            "\t\t#{verifyCount},\n" +
            "\t\t#{createTime},\n" +
            "\t\t#{updateTime})\n")
    void save(ModelPayData modelPayData);
}
