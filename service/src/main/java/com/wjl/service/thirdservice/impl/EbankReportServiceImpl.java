package com.wjl.service.thirdservice.impl;

import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.Response;
import com.wjl.model.mongo.EbankReport;
import com.wjl.mongo.EbankReportRepository;
import com.wjl.service.thirdservice.EbankReportService;
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
public class EbankReportServiceImpl implements EbankReportService {

    @Autowired
    private EbankReportRepository ebankReportRepository;

    @Override
    public Response getReport(String reportId) {
        EbankReport ebankReport = ebankReportRepository.findOne(reportId);
        return new Response(CodeEnum.QUERY_SUCCESS, ebankReport.getReport());
    }
}
