package com.wjl.service.impl;

import com.wjl.mapper.DataModelMapper;
import com.wjl.model.DataModel;
import com.wjl.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hqh
 */
@Service
public class DataModelServiceImpl implements DataModelService {
    @Autowired
    DataModelMapper dataModelMapper;
    @Override
    public void save(DataModel dataModel) {
        dataModelMapper.save(dataModel);
    }
}
