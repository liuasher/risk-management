package com.wjl.service;

import com.wjl.model.mongo.TaobaoData;


public interface ModelTaobaoDataService {

    public void taobaoDataToMySql(TaobaoData taobaoData, String identification);

}
