package com.wjl.mapper;

import com.wjl.model.TongDunRequestInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 同盾dao层
 *
 * @author mayue
 * @date 2018/4/2
 */
@Mapper
@Repository
public interface TongDunMapper {

    /**
     * 添加同盾请求记录
     *
     * @param tongDun 同盾请求信息
     * @return 是否成功
     */
    @Insert("INSERT INTO tongdun_request_info (\n" +
            "\tuser_id,\n" +
            "\tblack_box,\n" +
            "\tip,\n" +
            "\tsubmit_status,\n" +
            "\treport_id,\n" +
            "\tsubmit_time,\n" +
            "\tquery_time,\n" +
            "\tsuccess,\n" +
            "\treason_code,\n" +
            "\treason_desc,\n" +
            "\tidentification,\n" +
            "\tcreate_time\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{userId},    \n" +
            "\t\t#{blackBox},    \n" +
            "\t\t#{ip},    \n" +
            "\t\t#{submitStatus}, \n" +
            "\t\t#{reportId}, \n" +
            "\t\t#{submitTime},    \n" +
            "\t\t#{queryTime},    \n" +
            "\t\t#{success},    \n" +
            "\t\t#{reasonCode},    \n" +
            "\t\t#{reasonDesc},    \n" +
            "\t\t#{identification},    \n" +
            "\t\t#{createTime}) ")
    @Options(useGeneratedKeys = true)
    Long insertRequestInfo(TongDunRequestInfo tongDun);

    /**
     * 更新请求信息
     * @param tongDun
     * @return
     */
    @Update("update " +
            "   tongdun_request_info " +
            "set " +
            "   verify_id = #{verifyId}, " +
            "   query_time = #{queryTime}, " +
            "   submit_status = #{submitStatus}, " +
            "   update_time = #{updateTime} " +
            "where " +
            "   id = #{id}")
    Boolean updateRequestInfo(TongDunRequestInfo tongDun);

    /**
     * 通过主键id查询请求信息
     * @param id 主键
     * @return 同盾请求信息
     */
    @Select("select " +
            "   id, " +
            "   report_id " +
            "from " +
            "   tongdun_request_info " +
            "where " +
            "   id = #{id}")
    TongDunRequestInfo findById(Long id);
}
