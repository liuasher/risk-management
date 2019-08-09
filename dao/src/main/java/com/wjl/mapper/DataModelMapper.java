package com.wjl.mapper;

import com.wjl.model.DataModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface DataModelMapper {
    /**
     * 保存DataModel
     * @param dataModel
     */
    @Insert("INSERT INTO data_model (" +
            " user_id," +
            " credit_id," +
            " identification," +
            " add_time," +
            " yp_cnt," +
            " eatch_Call_Rate," +
            " phone_use_time," +
            " call_in_cnt_rsd," +
            " contact_num," +
            " dm_tel_nouse_basedays," +
            " call_in_time_std," +
            " phone2houseplace," +
            " score" +
            " )VALUES(#{userId},#{creditId},#{identification},#{addTime},#{ypCnt},#{eatchCallRate},#{phoneUseTime},#{callInCntRsd},#{contactNum},#{dmTelNouseBasedays},#{callInTimeStd},#{phone2houseplace},#{score});")
    void save(DataModel dataModel);
}
