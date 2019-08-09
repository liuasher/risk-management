package com.wjl.service.thirdservice.impl;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.model.mongo.TaobaoReport;
import com.wjl.mongo.TaobaoReportRepository;
import com.wjl.service.thirdservice.TaobaoReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description
 * @date 2018/4/9
 */
@Slf4j
@Service
public class TaobaoReportServiceImpl implements TaobaoReportService {

    @Autowired
    private TaobaoReportRepository taobaoReportRepository;

    @Override
    public Response getReport(String reportId) {
        TaobaoReport taobaoReport = taobaoReportRepository.findOne(reportId);
        return new Response(CodeEnum.QUERY_SUCCESS,taobaoReport.getReport());
    }
}
