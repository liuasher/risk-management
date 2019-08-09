package com.wjl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/10
 */
@Mapper
@Repository
public interface RiskBlackMapper {

    @Select(value = "SELECT COUNT(id) FROM risk_black WHERE mobile=#{mobile} AND `status`=1")
    Integer getCountByMobile(@Param("mobile") String mobile);

    @Select(value = "SELECT COUNT(id) FROM risk_black WHERE cert_no=#{certNo} AND `status`=1")
    Integer getCountByIdCard(String certNo);

}
