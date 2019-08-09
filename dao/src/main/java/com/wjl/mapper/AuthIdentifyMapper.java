package com.wjl.mapper;

import com.wjl.model.AuthIdentify;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @author ZHAOJP
 */
@Mapper
@Repository
public interface AuthIdentifyMapper {
    /**
     * 保存接口
     * @param authIdentify 实名认证实体类
     */
    @Insert("insert into auth_identify(name,mobile,id_card,identification,code,create_time,update_time) values(#{name},#{mobile},#{idCard},#{identification},#{code},#{createTime},#{updateTime})")
    void save(AuthIdentify authIdentify);

    /**
     * 通过id查找对象
     * @param id
     * @return
     */
    @Select("select id,name,mobile,id_card as idCard,identification,code,create_time as createTime,update_time as updateTime from auth_identify where id = #{id}")
    AuthIdentify findById(Long id);

    /**
     * 通过姓名、身份证、手机号查找对象
     * @param realname
     * @param idCard
     * @param mobile
     * @return
     */
    @Select("select id,name,mobile,id_card as idCard,identification,code,create_time as createTime,update_time as updateTime " +
            " from " +
            " auth_identify where name = #{realname} and id_card = #{idCard} and mobile = #{mobile}")
    AuthIdentify findByThree(@Param("realname") String realname, @Param("idCard") String idCard, @Param("mobile") String mobile);
}
