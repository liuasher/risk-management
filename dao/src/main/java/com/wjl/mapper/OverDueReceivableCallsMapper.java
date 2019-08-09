package com.wjl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author hqh
 */
@Mapper
@Repository
public interface OverDueReceivableCallsMapper {
    @Select(value = "select count(*) from overdue_receivable_calls as a where a.number = #{number} ")
    Integer getCount(String number);
}
