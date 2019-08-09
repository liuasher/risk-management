package com.wjl.aop;


import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.service.LogRiskManagementServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 风控模块的所有非0的错误码记录下来
 *
 * @author hqh
 */
@Slf4j
@Aspect
@Component
public class LogRiskManagementServiceAop {

    @Autowired
    private LogRiskManagementServiceInfo logRiskManagementServiceInfo;

    /**
     * 监控controller包的接口
     *
     * @param response 返回参数
     * @throws Throwable Throwable
     */
    @AfterReturning(returning = "response", pointcut = "execution(* com.wjl.controller.*.*(..))")
    public void useTimeLog(Response response) throws Throwable {
        if (response != null) {
            String code = response.getCode();
            if (CodeEnum.IDENTIFY_SUCCESS.getCode().equals(code)||CodeEnum.QUERY_SUCCESS.getCode().equals(code)) {
                log.info(String.format("-----接口请求成功，code=%s,message=%s-----",response.getCode(),response.getMessage()));
            }else {
                //当返回码不为成功的返回码时，保存日志
                logRiskManagementServiceInfo.saveWrongCodeLog(response.getCode(), response.getMessage());
            }
        }

    }
}
