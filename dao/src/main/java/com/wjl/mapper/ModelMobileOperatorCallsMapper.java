package com.wjl.mapper;

import com.wjl.model.ModelMobileOperatorCalls;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface ModelMobileOperatorCallsMapper {
    /**
     * 根据用户ID查找用户之前的记录
     * @param userId
     * @return
     */
    @Select("SELECT" +
            " a.query_time," +
            " a.call_type," +
            " a.cell_phone," +
            " a.create_time," +
            " a.id," +
            " a.identification," +
            " a.init_type," +
            " a.other_cell_phone," +
            " a.place," +
            " a.start_time," +
            " a.subtotal," +
            " a.update_time," +
            " a.user_id," +
            " a.use_time," +
            " a.verify_count" +
            " FROM" +
            " model_mobile_operator_calls a" +
            " WHERE" +
            " a.user_id = #{userId};")
    List<ModelMobileOperatorCalls> findByUserId(Long userId);

    /**
     * 删除上一次该userID的记录
     * @param userId
     */
    @Delete("DELETE" +
            " FROM" +
            " model_mobile_operator_calls" +
            " WHERE" +
            " user_id = #{userId};")
    void delete(Long userId);

    /**
     * 保存ModelMobileOperatorCalls
     * @param modelMobileOperatorCalls
     */
    @Insert("INSERT INTO model_mobile_operator_calls" +
            "(user_id,update_time,cell_phone,place,other_cell_phone,subtotal,start_time,init_type,call_type,use_time,verify_count,query_time,create_time,identification)"+
            " VALUES" +
            " (#{userId},#{updateTime},#{cellPhone},#{place},#{otherCellPhone},#{subtotal},#{startTime},#{initType},#{callType},#{useTime},#{verifyCount},#{queryTime},#{createTime},#{identification});")
    void save(ModelMobileOperatorCalls modelMobileOperatorCalls);

    /**
     * 运营商：最近6个月top3通话联系人
     * @param userId
     * @param identification
     * @return
     */
    @Select("select user_id,month,other_cell_phone,use_time,callout_time,callin_time,rank" +
            " from (" +
            " SELECT" +
            "     a.user_id," +
            "     a.month," +
            "     a.other_cell_phone," +
            "     a.use_time,a.callout_time,a.callin_time," +
            "     if(@pdept=a.month,@rank:=@rank+1,@rank:=1) as rank,  " +
            "     @pdept:=a.month" +
            " FROM" +
            "     (select user_id,substr(start_time,1,7) month,other_cell_phone,round(sum(use_time)/60,1) use_time," +
            " round(sum(if(init_type='主叫',use_time,0))/60,1) callout_time,round(sum(if(init_type='被叫',use_time,0))/60,1) callin_time" +
            " from model_mobile_operator_calls" +
            " where user_id = #{userId} and identification = #{identification}" +
            " group by user_id,month,other_cell_phone" +
            " ) a," +
            " (select @pdept:=null,@rank:=0) r" +
            " order by user_id,month desc," +
            " use_time desc)a" +
            " where rank <=3;")
    List<ModelMobileOperatorCalls> findModelMobileOperatorContact(@Param("userId")Long userId, @Param("identification")String identification);

    /**
     * 运营商：活跃区间
     * @param userId
     * @param identification
     * @return
     */
    @Select("select user_id,substr(start_time,1,7) month," +
            "       case when substr(start_time,12,2) in ('00','01','02','03','04','05') then '凌晨'" +
            "            when substr(start_time,12,2) in ('06','07','08','09','10','11') then '上午'" +
            "            when substr(start_time,12,2) in ('12','13','14','15','16','17') then '下午'" +
            "            when substr(start_time,12,2) in ('18','19','20','21','22','23') then '晚上' end period," +
            "       round(sum(use_time)/60) use_time" +
            " from model_mobile_operator_calls where user_id = #{userId} and identification = #{identification}" +
            " group by user_id,substr(start_time,1,7)," +
            "     case when substr(start_time,12,2) in ('00','01','02','03','04','05') then '凌晨'" +
            "          when substr(start_time,12,2) in ('06','07','08','09','10','11') then '上午'" +
            "          when substr(start_time,12,2) in ('12','13','14','15','16','17') then '下午'" +
            "          when substr(start_time,12,2) in ('18','19','20','21','22','23') then '晚上' end; ")
    List<ModelMobileOperatorCalls> findModelMobileOperatorLiveRange(@Param("userId")Long userId, @Param("identification")String identification);

    /**
     * 运营商：活跃时段
     * @param userId
     * @param identification
     * @return
     */
    @Select("select user_id,substr(start_time,1,7) month," +
            " substr(start_time,12,2) hour," +
            " round(sum(use_time)/60) use_time" +
            " from model_mobile_operator_calls where user_id = #{userId} and identification = #{identification}" +
            " group by substr(start_time,1,7),substr(start_time,12,2);")
    List<ModelMobileOperatorCalls> findModelMobileOperatorLiveTime(@Param("userId")Long userId, @Param("identification")String identification);

    /**
     * 运营商：活跃地区
     * @param userId
     * @param identification
     * @return
     */
    @Select("select user_id,place,left(start_time,7) month,round(sum(use_time)/60,1) use_time from model_mobile_operator_calls where user_id = #{userId} and identification = #{identification}" +
            " group by place,left(start_time,7)" +
            " having length(place)>0" +
            " order by month,use_time DESC;")
    List<ModelMobileOperatorCalls> findModelMobileOperatorLiveAreas(@Param("userId")Long userId, @Param("identification")String identification);

    /**
     * 运营商：主被叫分布
     * @param userId
     * @param identification
     * @return
     */
    @Select("select user_id,substr(start_time,1,7)  month," +
            "        init_type," +
            "        round(sum(use_time)/60) use_time,count(1) cnt" +
            " from  model_mobile_operator_calls where user_id = #{userId} and identification = #{identification}" +
            " group by substr(start_time,1,7),init_type;")
    List<ModelMobileOperatorCalls> findModelMobileOperatorCallsDistribution(@Param("userId")Long userId, @Param("identification")String identification);
}
