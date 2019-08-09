package com.wjl.service;

import com.wjl.commom.model.Response;
import com.wjl.model.mq.TencentCloudBean;

/**
 * @author LINJX
 * @description
 * @date 2018/4/2 14:03
 */
public interface TencentCloudService {

    /**
     * 保存腾讯云
     * @param requestArgs 请求参数
     * @param requestArgs 标识码
     * @return 保存verifyId
     * @throws Exception exception
     */
    Response saveTxyInfo(TencentCloudBean requestArgs) throws Exception;

}
