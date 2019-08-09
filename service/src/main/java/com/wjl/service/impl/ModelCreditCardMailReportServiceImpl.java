package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.mapper.ModelCreditCardMailReportMapper;
import com.wjl.model.ModelCreditCardMailReport;
import com.wjl.model.mongo.CreditCardMailReportData;
import com.wjl.service.ModelCreditCardMailReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author LINJX
 */

@Slf4j
@Service
public class ModelCreditCardMailReportServiceImpl implements ModelCreditCardMailReportService {

    @Autowired
    ModelCreditCardMailReportMapper modelCreditCardMailReportMapper;

    @Override
    public void saveByJsonArray(CreditCardMailReportData creditCardMailReportData,String identification,Long userId) {
        Long start = System.currentTimeMillis();
        JSONArray jsonArray = creditCardMailReportData.getReport();
        if(null == jsonArray || jsonArray.size()<1){
            log.info(String.format("-----userId为%s的信用卡邮箱报告为空-----",userId));
            return;
        }
        List<ModelCreditCardMailReport> byUserId = modelCreditCardMailReportMapper.findByUserId(userId);
        Integer verifyCount = 1;
        if (byUserId != null && byUserId.size() > 0) {
            verifyCount = byUserId.get(0).getVerifyCount() + 1;
            modelCreditCardMailReportMapper.deleteByUserId(userId);
        }

        Long applyTime = creditCardMailReportData.getQueryTime();
        Date date = new Date(applyTime);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonReport = jsonArray.getJSONObject(i);
            ModelCreditCardMailReport modelCreditCardMailReport = new ModelCreditCardMailReport();
            modelCreditCardMailReport.setUserId(userId);
            modelCreditCardMailReport.setQueryTime(date);
            //取数
            JSONObject userBasicInformation = jsonReport.getJSONObject("user_basic_information");
            String email = userBasicInformation.getString("email");
            String bankNums = userBasicInformation.getString("bank_nums");
            String activeCards = userBasicInformation.getString("active_cards");
            String customerGroupTag = userBasicInformation.getString("customer_group_tag");
            //存数
            modelCreditCardMailReport.setEmail(email);
            modelCreditCardMailReport.setBankNums(Integer.valueOf(bankNums));
            modelCreditCardMailReport.setActiveCards(Integer.valueOf(activeCards));
            modelCreditCardMailReport.setCustomerGroupTag(customerGroupTag);

            //取数
            JSONObject creditLimitInformatin = jsonReport.getJSONObject("credit_limit_informatin");
            String totalCreditLimit = creditLimitInformatin.getString("total_credit_limit");
            String maxTotalCreditLimit = creditLimitInformatin.getString("max_total_credit_limit");
            //存数
            if(null!=totalCreditLimit && !totalCreditLimit.contains("信用卡邮箱")){
                modelCreditCardMailReport.setTotalCreditLimit(BigDecimal.valueOf(Double.valueOf(totalCreditLimit)));
            }
            if(null!=maxTotalCreditLimit && !maxTotalCreditLimit.contains("信用卡邮箱")){
                modelCreditCardMailReport.setMaxTotalCreditLimit(BigDecimal.valueOf(Double.valueOf(maxTotalCreditLimit)));
            }

            //取数
            JSONObject interestFeeInformation = jsonReport.getJSONObject("interest_fee_information");
            String totalInstallmentFeeAmount = interestFeeInformation.getString("total_installment_fee_amount");
            //存数
            modelCreditCardMailReport.setTotalInstallmentFeeAmount(BigDecimal.valueOf(Double.valueOf(totalInstallmentFeeAmount)));

            //取数
            JSONObject newChargeInformation = jsonReport.getJSONObject("new_charge_information");
            String averageConsume3m = newChargeInformation.getString("average_consume_l3m");
            String averageConsume6m = newChargeInformation.getString("average_consume_l6m");
            String averageConsume12m = newChargeInformation.getString("average_consume_l12m");
            String averageSellCount3m = newChargeInformation.getString("average_sell_count_l3m");
            String averageSellCount6m = newChargeInformation.getString("average_sell_count_l6m");
            String averageSellCount12m = newChargeInformation.getString("average_sell_count_l12m");
            //存数
            modelCreditCardMailReport.setAverageConsume3m(BigDecimal.valueOf(Double.valueOf(averageConsume3m)));
            modelCreditCardMailReport.setAverageConsume6m(BigDecimal.valueOf(Double.valueOf(averageConsume6m)));
            modelCreditCardMailReport.setAverageConsume12m(BigDecimal.valueOf(Double.valueOf(averageConsume12m)));
            modelCreditCardMailReport.setAverageSellCount3m(Integer.valueOf(averageSellCount3m));
            modelCreditCardMailReport.setAverageSellCount6m(Integer.valueOf(averageSellCount6m));
            modelCreditCardMailReport.setAverageSellCount12m(Integer.valueOf(averageSellCount12m));
            modelCreditCardMailReport.setCreateTime(new Date());
            modelCreditCardMailReport.setIdentification(identification);
            modelCreditCardMailReport.setVerifyCount(verifyCount);
            modelCreditCardMailReportMapper.save(modelCreditCardMailReport);
        }
        log.info(String.format("-----model_credit_card_mail_report表存入数据完成，userId=%s，耗时=%sms -----",userId,System.currentTimeMillis()-start));

    }
}
