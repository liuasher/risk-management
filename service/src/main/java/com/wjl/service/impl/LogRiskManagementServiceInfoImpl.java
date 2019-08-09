package com.wjl.service.impl;

import com.wjl.mapper.LogRiskManagementMapper;
import com.wjl.model.LogRiskManagementService;
import com.wjl.service.LogRiskManagementServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hqh
 */
@Slf4j
@Service
public class LogRiskManagementServiceInfoImpl implements LogRiskManagementServiceInfo {
    @Autowired
    private LogRiskManagementMapper logRiskManagementMapper;

    @Override
    public void saveWrongCodeLog(String code, String message) {
        LogRiskManagementService logRiskManagementService = new LogRiskManagementService();
        logRiskManagementService.setCode(code);
        logRiskManagementService.setCreateTime(new Date());
        logRiskManagementMapper.save(logRiskManagementService);
        log.info(String.format("保存app错误码日志记录成功，code=%s，message=%s", code, message));
    }
}
