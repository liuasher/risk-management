package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.wjl.mapper.MoXieCreditCardMailMapper;
import com.wjl.mapper.ModelCreditCardMailBillMapper;
import com.wjl.model.ModelCreditCardMailBill;
import com.wjl.model.mongo.CreditCardMailBillData;
import com.wjl.mongo.CreditCardMailBillDataRepository;
import com.wjl.service.ModelCreditCardMailBillService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author LINJX
 */
@Service
@Log4j
public class ModelCreditCardMailMailBillServiceImpl implements ModelCreditCardMailBillService {
    @Autowired
    private CreditCardMailBillDataRepository creditCardMailBillDataRepository;
    @Autowired
    private ModelCreditCardMailBillMapper modelCreditCardMailBillMapper;

    @Override
    public void impHistory() {
        List<CreditCardMailBillData> creditCardMailBillData = creditCardMailBillDataRepository.findAll();
        for (CreditCardMailBillData creditCardMailBillData1 : creditCardMailBillData) {
           // saveModelCreaditCardBill(creditCardMailBillData1,"FCITY");
            }
        }

    @Override
    public void saveModelCreaditCardBill(CreditCardMailBillData creditCardMailBillData,String identification,Long userId) {
        Long start = System.currentTimeMillis();
        JSONObject bill = creditCardMailBillData.getBill();
        JSONArray bills = bill.getJSONArray("bills");
        List<ModelCreditCardMailBill> byUserId = modelCreditCardMailBillMapper.findByUserId(userId);
        Integer verifyCount = 1;
        if (byUserId != null && byUserId.size() > 0 ) {
            verifyCount = byUserId.get(0).getVerifyCount() + 1;
            modelCreditCardMailBillMapper.deleteByUserId(userId);
        }

        for (int i = 0; i < bills.size(); i++) {
            ModelCreditCardMailBill modelCreditCardMailBill2 = new ModelCreditCardMailBill();
            Long applyTime = creditCardMailBillData.getQueryTime();
            Date dateApplyTime = new Date(applyTime);
            modelCreditCardMailBill2.setQueryTime(dateApplyTime);
            modelCreditCardMailBill2.setUserId(creditCardMailBillData.getUserId());
            JSONObject ibill = bills.getJSONObject(i);
            //持卡人姓名
            String nameOnCard = ibill.getString("name_on_card");
            modelCreditCardMailBill2.setNameOnCard(nameOnCard);
            //邮箱是用户的邮箱地址，不是发件的邮箱地址
            String mailSender = ibill.getString("mail_sender");
            modelCreditCardMailBill2.setMailSender(mailSender);
            //账单日
            Date billDate = ibill.getDate("bill_date");
            modelCreditCardMailBill2.setBillDate(billDate);
            //还款日
            Date paymentDueDate = ibill.getDate("payment_due_date");
            modelCreditCardMailBill2.setPaymentDueDate(paymentDueDate);
            //卡号
            String cardNumber = ibill.getString("card_number");
            modelCreditCardMailBill2.setCardNumber(cardNumber);
            //银行ID
            Integer bankId = ibill.getInteger("bank_id");
            modelCreditCardMailBill2.setBankId(bankId);
            String bankName = getBankName(bankId);
            modelCreditCardMailBill2.setBankName(bankName);
            //信用卡额度
            String creditLimit = ibill.getString("credit_limit");
            modelCreditCardMailBill2.setCreditLimit(creditLimit);
            //本期账单金额等于本期应还金额
            String newBalance = ibill.getString("new_balance");
            modelCreditCardMailBill2.setNewBalance(newBalance);
            //上期还款金额
            String lastPayment = ibill.getString("last_payment");
            modelCreditCardMailBill2.setLastPayment(lastPayment);
            //可用积分
            Integer point = ibill.getInteger("point");
            modelCreditCardMailBill2.setPoint(point);
            //最近积分失效日期
            Date pointLoseDate = ibill.getDate("point_lose_date");
            modelCreditCardMailBill2.setPointLoseDate(pointLoseDate);
            modelCreditCardMailBill2.setCreateTime(new Date());
            modelCreditCardMailBill2.setIdentification(identification);
            modelCreditCardMailBill2.setVerifyCount(verifyCount);
            modelCreditCardMailBillMapper.save(modelCreditCardMailBill2);
        }
        log.info(String.format("-----model_credit_card_mail_bill表存入数据完成，userId=%s，耗时=%sms -----",userId,System.currentTimeMillis()-start));
    }

    private String  getBankName(Integer bankId){
            switch (bankId){
                case 1:
                    return "招商银行";
                case 2:
                    return "广发银行";
                case 3:
                    return "光大银行";
                case 4:
                    return "华夏银行";
                case 5:
                    return "建设银行";
                case 6:
                    return "民生银行";
                case 7:
                    return "农业银行";
                case 8:
                    return "浦发银行";
                case 9:
                    return "兴业银行";
                case 10:
                    return "中国银行";
                case 11:
                    return "中信银行";
                case 12:
                    return "工商银行";
                case 13:
                    return "交通银行";
                case 14:
                    return "邮储银行";
                case 15:
                    return "平安银行";
                case 16:
                    return "深发银行";
                case 17:
                    return "宁波银行";
                case 20:
                    return "河北银行";
                case 21:
                    return "大连银行";
                case 22:
                    return "徽商银行";
                case 23:
                    return "温州银行";
                case 24:
                    return "重庆农商";
                case 25:
                    return "重庆银行";
                case 26:
                    return "广州银行";
                case 27:
                    return "兰州银行";
                case 31:
                    return "鄞州银行";
                case 33:
                    return "上海银行";
                case 44:
                    return "包商银行";
                case 35:
                    return "长沙银行";
                case 36:
                    return "江苏农信";
                case 37:
                    return "福建农信";
                case 38:
                    return "成都农商";
                case 39:
                    return "富滇银行";
                case 40:
                    return "杭州银行";
                case 41:
                    return "江苏银行";
                case 42:
                    return "尧都农商";
                case 53:
                    return "广州农商";
                case 45:
                    return "台州银行";
                case 46:
                    return "华润银行";
                case 47:
                    return "吉林银行";
                case 48:
                    return "锦州银行";
                case 49:
                    return "上饶银行";
                case 50:
                    return "龙江银行";
                case 51:
                    return "青海银行";
                case 52:
                    return "汉口银行";
                case 18:
                    return "北京银行";
                case 34:
                    return "宁夏银行";
                case 88:
                    return "浙商银行";
                case 57:
                    return "浙江农信 ";
               default:
                    return "最新的账单不支持";


            }
    }
}
