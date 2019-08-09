package com.wjl.service;

import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;

/**
 * Created by LINJX on 2018/2/27.
 */
public interface IphoneVerifyService {

    Response saveIphoneInfo(RequestArgs requestArgs,String identification);

    Response getIphoneInfo(String taskId);

}
