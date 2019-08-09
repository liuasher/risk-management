package com.wjl.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wjl.commom.mq.MqSender;
import com.wjl.model.constant.QueueDict;
import com.wjl.model.mq.AuditBean;
import com.wjl.model.mq.TencentCloudBean;
import com.wjl.model.mq.TongDunBean;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * mq测试
 */
@RestController
@RequestMapping("/mq")
@Log4j
public class MqController {

    @Autowired
    private MqSender mqSender;



    /**
     * test
     * @param 
     * @return
     */
    @RequestMapping(value="/test/{applyId}", method= RequestMethod.GET)
    public String baiqishiReportJson(@PathVariable Long applyId) {
		TencentCloudBean t = new TencentCloudBean();

		t.setUserId(13579L);
		t.setCardNo("123");
		t.setIdCard("gggh");
		t.setIdentification("QJQ");
		t.setIp("192.168.1.1");
		t.setRealname("koukou");
		t.setMobile("13100672569");
        String s = JSON.toJSONString(t);
        mqSender.send(QueueDict.QUEUENAME_TENCENTCLOUD, s);
    	
        return "成功";
    }

    @RequestMapping("/tongDun/test")
    public String tongDunMqTest(){
        TongDunBean bean = new TongDunBean();
        bean.setUserId(11481L);
        bean.setName("丁鹏辉");
        bean.setMobile("15267147947");
        bean.setBlackBox("eyJ0b2tlbklkIjoiTjI3TXNcLytuMGRaZU5FamVrRW00ZUwwQmUzd01LN0Ywa2RiTjdKaFI1S2RLViszSDZCXC9wZHJTV3I4TTF5NWdidGl0dlpxaE1TOWxKcW1kc3NKNVZzUT09Iiwib3MiOiJpT1MiLCJwcm9maWxlVGltZSI6MjEyLCJ2ZXJzaW9uIjoiMy4wLjUifQ==");
        bean.setIdentification("QJQ");
        bean.setIdCard("330122199206282519");
        bean.setIpAddress("192.168.201.143");
        String json = JSON.toJSON(bean).toString();
        mqSender.send(QueueDict.QUEUENAME_TONGDUN, json);
        return "成功";
    }

    @RequestMapping(value = "/audit")
    public String audit(){
        AuditBean auditBean = new AuditBean();
        auditBean.setCreditId(100L);
        auditBean.setCity("杭州市");
        auditBean.setDistrict("西湖区");
        auditBean.setIdCard("330381199508290136");
        auditBean.setIdentification("EHUIZU");
        auditBean.setMobile("18758248004");
        auditBean.setMobileOperatorBillId("5ac9df40177a5264ec732dbe");
        auditBean.setMobileOperatorReportId("5ac9df4a177a5264ec732dbf");
        auditBean.setProvince("浙江省");
        auditBean.setContactNum(50);
        auditBean.setYpCnt(1);
        auditBean.setTencentCloudId("5acc28ad5ccc4202261827e4");
        auditBean.setTongDunId("5ac47b1fe2b63433e4ce7413");
        auditBean.setUserId(200L);
        auditBean.setCompanyName("haha");
        Map<String, Object> fieldData= Maps.newHashMap();
        fieldData.put("M10R06","N");
        fieldData.put("M10R07","N");
        fieldData.put("M20R02",2);
        fieldData.put("M20R03",2);
        fieldData.put("M20R04",2);
        fieldData.put("M20R05",2);
        fieldData.put("M20R06",1);
        fieldData.put("M30R01","N");
        fieldData.put("M30R03","N");
        fieldData.put("M30R06",100);
        fieldData.put("M30R07",0.2F);
        fieldData.put("M30R08",2);
        auditBean.setFieldData(fieldData);
        mqSender.send(QueueDict.QUEUENAME_RISK_AUTOAUDIT_STEP0, JSON.toJSONString(auditBean));
        return "成功";
    }
}
