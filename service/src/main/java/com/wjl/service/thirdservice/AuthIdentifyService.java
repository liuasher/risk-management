package com.wjl.service.thirdservice;

import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;

import java.util.Map;
/**
 * @Description :实名认证
 * @author : ZHAOJP
 * @date : 2018/2/28
 */
public interface AuthIdentifyService {
    /**
     * 三要素认证（调用魔蝎接口）
     * @param requestArgs
     * @return
     */
    Response getAuthIdentify(RequestArgs requestArgs, String identification) throws Exception;

    /**
     * 查询用户信息
     * @param taskId
     * @return
     */
    Response getAuthIdentifyInfo(String taskId) throws Exception;
}
