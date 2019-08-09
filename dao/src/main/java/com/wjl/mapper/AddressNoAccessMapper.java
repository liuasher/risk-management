package com.wjl.mapper;

import com.wjl.model.AddressNoAccess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 身份证非准入地区mapper
 * @date 2018/4/10
 */
@Mapper
@Repository
public interface AddressNoAccessMapper {

    /**
     * 查找所有的非准入地区列表
     * @return
     */
    @Select("select " +
            " id,cert_no as certNo,district_name as districtName,id_match as idMatch,effect_index as effectIndex,effect_date as effectDate " +
            " from " +
            " address_no_access where effect_index = 1")
    List<AddressNoAccess> findAll();
}
