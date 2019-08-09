package com.wjl.mapper;

import com.wjl.model.ModelMobileOperatorCellBehavior;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface ModelMobileOperatorCellBehaviorMapper {
    /**
     * 根据用户Id查找集合列
     * @param userId
     * @return
     */
    @Select("SELECT" +
            " a.id," +
            " a.query_time," +
            " a.call_cnt," +
            " a.call_in_cnt," +
            " a.call_in_time," +
            " a.call_out_cnt," +
            " a.call_out_time," +
            " a.cell_loc," +
            " a.cell_mth," +
            " a.cell_operator," +
            " a.cell_operator_zh," +
            " a.cell_phone_num," +
            " a.create_time," +
            " a.net_flow," +
            " a.sms_cnt," +
            " a.identification," +
            " a.total_amount," +
            " a.verify_count," +
            " a.rpt_id," +
            " a.user_id" +
            " FROM" +
            " model_mobile_operator_cell_behavior a" +
            " WHERE" +
            " a.user_id = #{userId}")
    List<ModelMobileOperatorCellBehavior> findByUserId(Long userId);

    /**
     * 删除上一次该userID的记录
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_mobile_operator_cell_behavior" +
            " WHERE" +
            " user_id = #{userId}")
    void delete(Long userId);

    /**
     * 保存ModelMobileOperatorCellBehavior
     * @param modelMobileOperatorCellBehavior
     */
    @Insert("INSERT INTO model_mobile_operator_cell_behavior" +
            " (user_id,rpt_id,cell_operator,cell_operator_zh,cell_phone_num,cell_loc,cell_mth,call_cnt,call_out_cnt,call_out_time,call_in_cnt,call_in_time,net_flow,sms_cnt,total_amount,verify_count,query_time,create_time,identification)"+
            " VALUES" +
            " (#{userId},#{rptId},#{cellOperator},#{cellOperatorZh},#{cellPhoneNum},#{cellLoc},#{cellMth},#{callCnt},#{callOutCnt},#{callOutTime},#{callInCnt},#{callInTime},#{netFlow},#{smsCnt},#{totalAmount},#{verifyCount},#{queryTime},#{createTime},#{identification});")
    void save(ModelMobileOperatorCellBehavior modelMobileOperatorCellBehavior);

    /**
     * 查询运营商：近6个月语音、流量、话费
     * @param userId
     * @param identification
     * @return
     */
    @Select("SELECT" +
            " user_id," +
            " cell_mth," +
            " round(call_in_time + call_out_time) call_time," +
            " round(net_flow) net_flow," +
            " round(total_amount, 1) total_amount" +
            " FROM" +
            " model_mobile_operator_cell_behavior" +
            " WHERE" +
            " user_id = #{userId}" +
            " AND identification = #{identification}" +
            " ORDER BY" +
            " cell_mth;")
    List<ModelMobileOperatorCellBehavior> findModelMobileOperatorTotal(@Param("userId")Long userId, @Param("identification")String identification);
}
