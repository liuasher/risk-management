package com.wjl.mapper;

import com.wjl.model.Iphone;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by LINJX on 2018/2/27.
 */
@Mapper
@Repository
public interface IphoneVerifyMapper {

    /**
     * 保存接口
     * @param iphone 苹果手机类
     */
    @Insert("insert into iphone(user_id,icloud_name,icloud_password,serial_number,imei,create_time,status,identification) values(#{userId},#{icloudName},#{icloudPassword},#{serialNumber},#{imei},#{createTime},#{status},#{identification})")
    @Options(useGeneratedKeys=true)
    void saveIphoneInfo(Iphone iphone);

    /**
     * 查询接口
     * @param id 主键id
     * @return iphone 苹果手机类
     */
    @Select("select id,user_id as userId,icloud_name as icloudName,icloud_password as icloudPassword,serial_number as serialNumber,imei,update_time as updateTime,create_time as createTime,status,identification from iphone where id = #{id}")
    Iphone getIphoneInfo(Long id);


}
