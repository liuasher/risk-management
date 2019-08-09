package com.wjl.mapper;

import com.wjl.model.TencentCloudInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author LINJX
 * @description
 * @date 2018/4/2 14:07
 */
@Mapper
@Repository
public interface TencentCloudMapper {

    /**
     * @param tencentCloudInfo 请求参数
     */
    @Select("INSERT INTO txy_request_info ("+
            "user_id,id_card,mobile,realname,ip,bank_card_number,"+
            "create_time, identification, STATUS, verify_id)"+
            "VALUES ( #{userId}, #{idCard}, #{mobile}, #{realname}, #{ip}, #{bankCardNumber}, #{createTime}, #{identification}, #{status}, #{verifyId})")
    void saveTxyInfo(TencentCloudInfo tencentCloudInfo);
}
