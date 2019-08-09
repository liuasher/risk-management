package com.wjl.mapper;

import com.wjl.model.ApiToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/2
 */
@Mapper
@Repository
public interface ApiTokenMapper {

    /**
     * 通过token和type查找对象
     * @param token
     * @param type
     * @return
     */
    @Select("select id,ip,type,token,api,create_time as createTime,update_time as updateTime from api_token where token = #{token} and type = #{type}")
    ApiToken findByTokenAndType(@Param("token") String token, @Param("type") Integer type);
}
