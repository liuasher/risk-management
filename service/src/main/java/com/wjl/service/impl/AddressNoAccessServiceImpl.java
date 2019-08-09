package com.wjl.service.impl;

import com.wjl.mapper.AddressNoAccessMapper;
import com.wjl.model.AddressNoAccess;
import com.wjl.service.AddressNoAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/10
 */
@Service
@Slf4j
public class AddressNoAccessServiceImpl implements AddressNoAccessService {

    @Autowired
    private AddressNoAccessMapper addressNoAccessMapper;

    @Override
    public List<AddressNoAccess> findALL() {
        return addressNoAccessMapper.findAll();
    }
}
