package com.wjl.service;


import com.wjl.model.mongo.TaobaoReport;



public interface ModelTaobaoReportService {

    public void taobaoMongoToMysql(TaobaoReport moXieTaobaoReport, String identification);

}
