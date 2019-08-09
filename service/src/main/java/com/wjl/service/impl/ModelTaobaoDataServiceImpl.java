package com.wjl.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wjl.mapper.ModelTaoBaoDataMapper;
import com.wjl.mapper.ModelTaobaoDataSubordersMapper;
import com.wjl.mapper.ModelTaobaoDataTradedetailMapper;
import com.wjl.model.mongo.TaobaoData;
import com.wjl.model.ModelTaobaoData;
import com.wjl.model.ModelTaobaoDataSuborders;
import com.wjl.model.ModelTaobaoDataTradedetail;
import com.wjl.service.ModelTaobaoDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class ModelTaobaoDataServiceImpl implements ModelTaobaoDataService {
    @Autowired
    private ModelTaoBaoDataMapper modelTaoBaoDataMapper;
    @Autowired
    private ModelTaobaoDataTradedetailMapper modelTaobaoDataTradedetailMapper;
    @Autowired
    private ModelTaobaoDataSubordersMapper modelTaobaoDataSubordersMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void taobaoDataToMySql(TaobaoData taobaoData, String identification){
        log.info("--------userId="+taobaoData.getUserId()+",淘宝账单数据导入mysql开始---------");
        if(taobaoData.getBill()==null){
            return;
        }
        Long userId = taobaoData.getUserId();
        Long applyTime = taobaoData.getQueryTime();
        //如果model_taobao_data之前有此userId的记录，将verifyCount加1，同时删除原数据
        Integer verifyCount = 1;
        Integer count = modelTaoBaoDataMapper.findTopVerifyCountByUserId(userId);
        if(null != count){
            verifyCount = count + 1;
            modelTaoBaoDataMapper.deleteByUserId(userId);
        }

        JSONObject bill = taobaoData.getBill();
        JSONObject userinfo = bill.getJSONObject("userinfo");
        String real_name = userinfo.getString("real_name");
        String phone_number = userinfo.getString("phone_number");
        ModelTaobaoData modelTaobaoData = new ModelTaobaoData();
        modelTaobaoData.setUserId(userId);
        modelTaobaoData.setIdentification(identification);
        modelTaobaoData.setRealName(real_name);
        modelTaobaoData.setPhoneNumber(phone_number);
        modelTaobaoData.setVerifyCount(verifyCount);
        modelTaobaoData.setQueryTime(new Date(applyTime));
        modelTaobaoData.setCreateTime(new Date());
        modelTaoBaoDataMapper.save(modelTaobaoData);

        JSONObject tradedetails1 = bill.getJSONObject("tradedetails");
        JSONArray tradedetails = tradedetails1.getJSONArray("tradedetails");

        //如果model_taobao_data_tradedetail之前有此userId的记录，将verifyCount加1，同时删除原数据
        Integer detailCount = 1;
        Integer count1 = modelTaobaoDataTradedetailMapper.findTopVerifyCountByUserId(userId);
        if(null != count1){
            detailCount = count1 + 1;
            modelTaobaoDataTradedetailMapper.deleteByUserId(userId);
        }

        //如果model_taobao_data_suborders之前有此userId的记录，将verifyCount加1，同时删除原数据
        Integer subordersCount = 1;
        Integer count2 = modelTaobaoDataSubordersMapper.findTopVerifyCountByUserId(userId);
        if(null != count2){
            subordersCount = count2 + 1;
            modelTaobaoDataSubordersMapper.deleteByUserId(userId);
        }

        for(int i = 0 ;i<tradedetails.size();i++){
            JSONObject tradedetail = tradedetails.getJSONObject(i);
            String trade_id = tradedetail.getString("trade_id");
            Date trade_createtime = tradedetail.getDate("trade_createtime");
            String trade_text = tradedetail.getString("trade_text");
            String actual_fee = tradedetail.getString("actual_fee");
            Double fee = Double.valueOf(actual_fee)/100;
            String seller_shopname = tradedetail.getString("seller_shopname");
            String mapping_id = tradedetail.getString("mapping_id");
            String trade_status = tradedetail.getString("trade_status");
            String seller_nick = tradedetail.getString("seller_nick");
            Long seller_id = tradedetail.getLong("seller_id");
            ModelTaobaoDataTradedetail modelTaobaoDataTradedetail = new ModelTaobaoDataTradedetail();
            modelTaobaoDataTradedetail.setActualFee(fee.toString());
            modelTaobaoDataTradedetail.setCreateTime(new Date());
            modelTaobaoDataTradedetail.setMappingId(mapping_id);
            modelTaobaoDataTradedetail.setSellerId(seller_id);
            modelTaobaoDataTradedetail.setSellerNick(seller_nick);
            modelTaobaoDataTradedetail.setSellerShopname(seller_shopname);
            modelTaobaoDataTradedetail.setTradeId(trade_id);
            modelTaobaoDataTradedetail.setTradeCreatetime(trade_createtime);
            modelTaobaoDataTradedetail.setTradeText(trade_text);
            modelTaobaoDataTradedetail.setTradeStatus(trade_status);
            modelTaobaoDataTradedetail.setUserId(userId);
            modelTaobaoDataTradedetail.setVerifyCount(detailCount);
            modelTaobaoDataTradedetail.setIdentification(identification);
            modelTaobaoDataTradedetail.setDataId(modelTaobaoData.getId());
            modelTaobaoDataTradedetailMapper.save(modelTaobaoDataTradedetail);

            JSONArray sub_orders = tradedetail.getJSONArray("sub_orders");
            for(int a = 0;a<sub_orders.size();a++){
                JSONObject suborders = sub_orders.getJSONObject(a);
                String cid_level1 = suborders.getString("cid_level1");
                String cid_level2 = suborders.getString("cid_level2");
                String item_id = suborders.getString("item_id");
                Integer quantity = suborders.getInteger("quantity");
                String original = suborders.getString("original");
                String item_pic = suborders.getString("item_pic");
                String item_name = suborders.getString("item_name");
                String item_url = suborders.getString("item_url");
                String cname_level2 = suborders.getString("cname_level2");
                String real_total = suborders.getString("real_total");
                Double o = Double.valueOf(original)/100;
                Double r = Double.valueOf(real_total) / 100;
                String cname_level1 = suborders.getString("cname_level1");
                ModelTaobaoDataSuborders modelTaobaoDataSuborders = new ModelTaobaoDataSuborders();
                modelTaobaoDataSuborders.setCidLevel1(cid_level1);
                modelTaobaoDataSuborders.setCidLevel2(cid_level2);
                modelTaobaoDataSuborders.setCnameLevel1(cname_level1);
                modelTaobaoDataSuborders.setCnameLevel2(cname_level2);
                modelTaobaoDataSuborders.setItemId(item_id);
                modelTaobaoDataSuborders.setQuantity(quantity);
                modelTaobaoDataSuborders.setOriginal(o.toString());
                modelTaobaoDataSuborders.setItemPic(item_pic);
                modelTaobaoDataSuborders.setItemName(item_name);
                modelTaobaoDataSuborders.setItemUrl(item_url);
                modelTaobaoDataSuborders.setRealTotal(r.toString());
                modelTaobaoDataSuborders.setUserId(userId);
                modelTaobaoDataSuborders.setVerifyCount(subordersCount);
                modelTaobaoDataSuborders.setIdentification(identification);
                modelTaobaoDataSuborders.setTradeId(trade_id);
                modelTaobaoDataSuborders.setCreateTime(new Date());
                modelTaobaoDataSubordersMapper.save(modelTaobaoDataSuborders);
            }
        }
        log.info("--------userId="+taobaoData.getUserId()+",淘宝账单数据导入mysql结束---------");
    }
}
