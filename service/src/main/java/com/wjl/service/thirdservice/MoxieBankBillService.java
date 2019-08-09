package com.wjl.service.thirdservice;


import com.wjl.model.mongo.EbankData;
import com.wjl.model.mongo.EbankReport;

/**
 * @author mayue
 * @version 1.0
 * @description 魔蝎入库
 * @date 2017/12/15
 */
public interface MoxieBankBillService {



    /**
     * 网银账单导入model
     * @param userId
     */
    void saveModelEbankBill(EbankData ebankData, Long userId,String identification);

    /**
     * 网银报告导入model
     * @param ebankReport
     * @param userId
     */
    void saveModelEbankReport(EbankReport ebankReport, Long userId, String identification);


}
