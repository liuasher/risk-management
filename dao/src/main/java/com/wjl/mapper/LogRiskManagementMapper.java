package com.wjl.mapper;

import com.wjl.model.LogRiskManagementService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author hqh
 */
@Mapper
@Repository
public interface LogRiskManagementMapper {
    /**
     * log_riskmanagement_service更新错误码日志记录
     * @param logRiskManagementService
     */
    @Insert("INSERT INTO"+
            " log_risk_management_service"+
            " VALUES"+
            "(#{id},#{code},#{createTime});")
    void save(LogRiskManagementService logRiskManagementService);
}
