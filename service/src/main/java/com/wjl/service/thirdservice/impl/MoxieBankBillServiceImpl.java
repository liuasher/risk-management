package com.wjl.service.thirdservice.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.wjl.model.ModelEbankBill;
import com.wjl.model.ModelEbankCreditCardReport;
import com.wjl.model.ModelEbankDebitCardReport;
import com.wjl.model.mongo.EbankData;
import com.wjl.model.mongo.EbankReport;

import com.wjl.service.ModelEbankBillService;
import com.wjl.service.ModelEbankCreditCardReportService;
import com.wjl.service.ModelEbankDebitCardReportService;
import com.wjl.service.thirdservice.MoxieBankBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ZHAOJP
 * @version 1.0
 * @description 魔蝎网银账单入库
 * @date 2017/12/15
 */
@Slf4j
@Service
public class MoxieBankBillServiceImpl implements MoxieBankBillService {

    @Autowired
    private ModelEbankBillService modelEbankBillService;
    @Autowired
    private ModelEbankCreditCardReportService modelEbankCreditCardReportService;
    @Autowired
    private ModelEbankDebitCardReportService modelEbankDebitCardReportService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveModelEbankBill(EbankData ebankData, Long userId, String identification) {
        Integer count = modelEbankBillService.findTopVerifyCountByUserId(userId);
        Integer verifyCount = 0;
        if (null != count) {
            modelEbankBillService.deleteByUserId(userId);
            verifyCount = count + 1;
        } else {
            verifyCount = 1;
        }
        JSONArray bills = ebankData.getBill();
        List<ModelEbankBill> modelEbankBillList = new LinkedList<>();
        for (int i = 0; i < bills.size(); i++) {
            JSONObject jsonObject = bills.getJSONObject(i);
            JSONArray bills1 = jsonObject.getJSONArray("bills");
            for (int j = 0; j < bills1.size(); j++) {
                JSONObject jsonObject1 = bills1.getJSONObject(j);

                JSONArray shopping_sheets = jsonObject1.getJSONArray("shopping_sheets");
                for (int k = 0; k < shopping_sheets.size(); k++) {
                    JSONObject jsonObject2 = shopping_sheets.getJSONObject(k);
                    ModelEbankBill modelEbankBill = new ModelEbankBill();
                    modelEbankBill.setUserId(userId);
                    //存入项目标识
                    modelEbankBill.setIdentification(identification);

                    modelEbankBill.setCardNum(jsonObject.getString("card_num"));//卡号
                    String bill_id = jsonObject1.getString("bill_id");//消费ID
                    modelEbankBill.setBillId(bill_id);
                    BigDecimal balance = jsonObject2.getBigDecimal("balance");//余额
                    modelEbankBill.setBalance(balance);
                    String card_type = jsonObject.getString("card_type");
                    modelEbankBill.setCardType(card_type);
                    Date trans_date = jsonObject2.getDate("trans_date");//交易时间
                    modelEbankBill.setTransDate(trans_date);
                    Date post_date = jsonObject2.getDate("post_date");//记账日期
                    modelEbankBill.setPostDate(post_date);
                    String description = jsonObject2.getString("description");//描述
                    modelEbankBill.setDescription(description);
                    BigDecimal amount_money = jsonObject2.getBigDecimal("amount_money");//交易金额
                    modelEbankBill.setAmountMoney(amount_money);
                    String currency_type = jsonObject2.getString("currency_type");//币种
                    modelEbankBill.setCurrencyType(currency_type);
                    String category = jsonObject2.getString("category");//消费类型
                    String category1 = getCategory(card_type, category);
                    modelEbankBill.setCategory(category1);
                    String trans_addr = jsonObject2.getString("trans_addr");//交易地点
                    modelEbankBill.setTransAddr(trans_addr);
                    String trans_method = jsonObject2.getString("trans_method");//交易方式
                    modelEbankBill.setTransMethod(trans_method);
                    String trans_channel = jsonObject2.getString("trans_channel");//交易通道
                    modelEbankBill.setTransChannel(trans_channel);
                    String opposite_card_no = jsonObject2.getString("opposite_card_no");//对方账号
                    modelEbankBill.setOppositeCardNo(opposite_card_no);
                    String opposite_bank = jsonObject2.getString("opposite_bank");//对方银行
                    modelEbankBill.setOppositeBank(opposite_bank);
                    String remark = jsonObject2.getString("remark");//备注
                    modelEbankBill.setRemark(remark);
                    modelEbankBill.setVerifyCount(verifyCount);
                    Long applyTime = ebankData.getQueryTime();
                    Date appdate = new Date(applyTime);
                    modelEbankBill.setQueryTime(appdate);
                    modelEbankBill.setCreateTime(new Date());
                    modelEbankBillList.add(modelEbankBill);
                }
            }
        }
        modelEbankBillService.saveList(modelEbankBillList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveModelEbankReport(EbankReport ebankReport, Long userId, String identification) {
        if (null != ebankReport) {
            JSONObject report = ebankReport.getReport();
            if (null != report) {
                JSONObject creditcard = report.getJSONObject("creditcard");
                JSONObject debitcard = report.getJSONObject("debitcard");
                //保存信用卡报告
                if (null != creditcard) {
                    Integer verifyCount = 1;
                    Integer count = modelEbankCreditCardReportService.findTopVerifyCountByUserId(userId);
                    if (null != count) {
                        modelEbankCreditCardReportService.deleteByUserId(userId);
                        verifyCount = count + 1;
                    }
                    ModelEbankCreditCardReport modelEbankCreditCardReport = new ModelEbankCreditCardReport();
                    JSONObject accountSummary = creditcard.getJSONObject("account_summary");
                    if (null != accountSummary) {
                        //总信用额（元）
                        BigDecimal creditcardLimit = accountSummary.getBigDecimal("creditcard_limit");
                        //总可用信用额（元）
                        BigDecimal total_can_use_consume_limit_1 = accountSummary.getBigDecimal("total_can_use_consume_limit_1");
                        modelEbankCreditCardReport.setCreditcardLimit(creditcardLimit);
                        modelEbankCreditCardReport.setTotalCanUseConsumeLimit(total_can_use_consume_limit_1);
                    }
                    JSONObject repayment_summary = creditcard.getJSONObject("repayment_summary");
                    if (null != repayment_summary) {
                        //还款笔数
                        Integer repay_num_1 = repayment_summary.getInteger("repay_num_1");
                        //还款率
                        BigDecimal repay_ratio_1 = repayment_summary.getBigDecimal("repay_ratio_1");
                        modelEbankCreditCardReport.setRepayNum(repay_num_1);
                        modelEbankCreditCardReport.setRepayRatio(repay_ratio_1);
                    }
                    JSONObject overdue_information = creditcard.getJSONObject("overdue_information");
                    if (null != overdue_information) {
                        //逾期标志
                        Integer delay_tag_1 = overdue_information.getInteger("delay_tag_1");
                        //逾期状态
                        Integer delay_status_1 = overdue_information.getInteger("delay_status_1");
                        modelEbankCreditCardReport.setDelayTag(delay_tag_1);
                        modelEbankCreditCardReport.setDelayStatus(delay_status_1);
                    }
                    modelEbankCreditCardReport.setUserId(userId);
                    modelEbankCreditCardReport.setIdentification(identification);
                    modelEbankCreditCardReport.setCreateTime(new Date());
                    Date applyTime = new Date(ebankReport.getQueryTime());
                    modelEbankCreditCardReport.setQueryTime(applyTime);
                    modelEbankCreditCardReport.setVerifyCount(verifyCount);
                    modelEbankCreditCardReportService.save(modelEbankCreditCardReport);

                }
                //保存借记卡报告
                if (null != debitcard) {
                    Integer count = modelEbankDebitCardReportService.findVerifyCountByUserId(userId);
                    Integer verifyCounts = 1;
                    if (null != count) {
                        modelEbankDebitCardReportService.deleteByUserId(userId);
                        verifyCounts = count + 1;
                    }
                    ModelEbankDebitCardReport modelEbankDebitCardReport = new ModelEbankDebitCardReport();
                    JSONObject user_basic_info = debitcard.getJSONObject("user_basic_info");
                    if (null != user_basic_info) {
                        //近1年收入（元）
                        BigDecimal income_amt_1y = user_basic_info.getBigDecimal("income_amt_1y");
                        //近一年工资收入（元）
                        BigDecimal salary_income_1y = user_basic_info.getBigDecimal("salary_income_1y");
                        //近一年贷款收入（元）
                        BigDecimal loan_in_1y = user_basic_info.getBigDecimal("loan_in_1y");
                        //近一年支出（元）
                        BigDecimal expense_1y = user_basic_info.getBigDecimal("expense_1y");
                        //近一年消费支出（元）
                        BigDecimal consumption_expense_1y = user_basic_info.getBigDecimal("consumption_expense_1y");
                        //近一年还贷支出（元）
                        BigDecimal loan_out_1y = user_basic_info.getBigDecimal("loan_out_1y");
                        modelEbankDebitCardReport.setIncomeAmty(income_amt_1y);
                        modelEbankDebitCardReport.setSalaryIncomey(salary_income_1y);
                        modelEbankDebitCardReport.setLoanIny(loan_in_1y);
                        modelEbankDebitCardReport.setLoanOuty(loan_out_1y);
                        modelEbankDebitCardReport.setExpensey(expense_1y);
                        modelEbankDebitCardReport.setConsumptionExpensey(consumption_expense_1y);
                    }
                    modelEbankDebitCardReport.setUserId(userId);
                    modelEbankDebitCardReport.setIdentification(identification);
                    modelEbankDebitCardReport.setCreateTime(new Date());
                    Date applyTime = new Date(ebankReport.getQueryTime());
                    modelEbankDebitCardReport.setQueryTime(applyTime);
                    modelEbankDebitCardReport.setVerifyCount(verifyCounts);

                    modelEbankDebitCardReportService.save(modelEbankDebitCardReport);
                }
            }
        }
    }


    String getCategory(String cardType, String category) {
        if ("信用卡".equals(cardType)) {
            switch (category) {
                case "WITHDRAW":
                    return "取现";
                case "PAYMENTS":
                    return "还款";
                case "INTEREST":
                    return "循环利息";
                case "OVERDUEPAYMENT":
                    return "延滞金";
                case "OVERDUEFEE":
                    return "超额费";
                case "INSTALLMENT":
                    return "分期";
                case "SHOPPING":
                    return "消费";
                default:
                    return "其他费用";
            }


        } else {
            switch (category) {
                case "SALARY":
                    return "工资";
                case "BONUS":
                    return "奖金";
                case "ACCRUAL":
                    return "利息";
                case "DEPOSIT":
                    return "存现";
                case "TRANSFER":
                    return "转账";
                case "LOANS":
                    return "借贷";
                case "REVERSE":
                    return "冲账";
                case "REFUND":
                    return "退款";
                case "EARNINGS":
                    return "收益";
                case "CLAIM":
                    return "报销";
                case "OTHERREVENUE":
                    return "其它收入";
                case "WITHDRAW":
                    return "取现";
                case "HANDLINGFEE":
                    return "手续费";

                case "REPAYMENT":
                    return "还贷";
                case "FINANCE":
                    return "理财";
                case "CONSUMPTION":
                    return "消费";
                case "RENT":
                    return "房租";
                case "FUND":
                    return "公积金";
                case "SOCIALSECURITY":
                    return "社保";
                case "COMMUNICATIONFEE":
                    return "通讯费";
                case "LIVINGEXPENSE":
                    return "生活费";
                case "OTHERFEE":
                    return "其它费用";
                case "OTHEREXPENSE":
                    return "其它支出";
                default:
                    return "其他费用";
            }
        }
    }
}
