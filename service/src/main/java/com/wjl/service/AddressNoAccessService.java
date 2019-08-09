package com.wjl.service;

import com.wjl.model.AddressNoAccess;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 身份证非准入地区
 * @date 2018/4/10
 */
public interface AddressNoAccessService {
    /**
     * 查找所有的非准入地区列表
     * @return
     */
    List<AddressNoAccess> findALL();
}
