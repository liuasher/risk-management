package com.wjl.service.thirdservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjl.commom.configuration.FirmConfiguration;
import com.wjl.commom.configuration.ThirdConfiguration;
import com.wjl.commom.enumeration.AuthIdentifyEnum;
import com.wjl.commom.enumeration.CodeEnum;
import com.wjl.commom.model.RequestArgs;
import com.wjl.commom.model.Response;
import com.wjl.commom.util.AesUtils;
import com.wjl.commom.util.third.HttpUtil_MoXie;
import com.wjl.mapper.AuthIdentifyMapper;
import com.wjl.model.AuthIdentify;
import com.wjl.model.AuthIdentifyVo;
import com.wjl.service.thirdservice.AuthIdentifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * @author ZHAOJP
 */
@Service
@Slf4j
public class AuthIdentifyServiceImpl implements AuthIdentifyService {
    @Autowired
    ThirdConfiguration thirdConfiguration;
    @Autowired
    FirmConfiguration firmConfiguration;
    @Autowired
    AuthIdentifyMapper authIdentifyMapper;

    @Override
    public Response getAuthIdentify(RequestArgs requestArgs, String identification) throws Exception {
        log.info("-----实名认证开始-----");
        //姓名
        String realname = requestArgs.getName();
        //身份证号
        String idcard = requestArgs.getIdCard();
        //手机号
        String mobile = requestArgs.getMobile();
        //对身份证信息完整性做出校验
        if (StringUtils.isBlank(realname) || StringUtils.isBlank(idcard) ||StringUtils.isBlank(mobile)){
            return new Response(CodeEnum.PARAMETER_MISTAKE, null);
        }

        //如果风控数据库已存在3要素校验的结果，则不访问魔蝎接口，直接从风控数据库获取结果
        AuthIdentify verify = authIdentifyMapper.findByThree(realname,idcard,mobile);
        if(null!=verify && 0==verify.getCode()){
            String tokens = AesUtils.aesEncryptHexString(verify.getId().toString(), firmConfiguration.getWjl());
            Map<String, String> map = new HashMap<>(1);
            map.put("taskId", tokens);
            return new Response<>(CodeEnum.IDENTIFY_SUCCESS,map);
        }

        Map map = new HashMap(4);
        map.put("name", realname);
        map.put("mobile", mobile);
        map.put("idCard", idcard);
        map.put("element", "3");
        //魔蝎接口
        String resp = HttpUtil_MoXie.sendGet(thirdConfiguration.getAuthIdentifyServiceUrl(), paramsMapToStr(map));
        log.info(String.format("-----魔蝎二要素认证返回:%s-----", resp));
        JSONObject respJson = JSON.parseObject(resp);
        Boolean success = respJson.getBoolean("success");

        long createTime = System.currentTimeMillis();
        AuthIdentify authIdentify=new AuthIdentify();
        authIdentify.setIdCard(idcard);
        authIdentify.setName(realname);
        authIdentify.setMobile(mobile);
        authIdentify.setCreateTime(createTime);
        authIdentify.setIdentification(identification);
        log.info("-----魔蝎回调success:"+success+"-----");
        if (success){
            //请求成功
            String respDataStr = respJson.getString("data");
            JSONObject respData = JSON.parseObject(respDataStr);
            Integer code = Integer.valueOf(respData.getString("code")) ;
            if (AuthIdentifyEnum.SUCCESS.getCode().equals(code)){
                authIdentify.setCode(AuthIdentifyEnum.SUCCESS.getCode());
                authIdentifyMapper.save(authIdentify);
                String tokens = AesUtils.aesEncryptHexString(authIdentify.getId().toString(), firmConfiguration.getWjl());
                Map<String, String> map1 = new HashMap<>(1);
                map1.put("taskId", tokens);
                return new Response<>(CodeEnum.IDENTIFY_SUCCESS,map1);
            }else {
                authIdentify.setCode(Integer.valueOf(code));
                authIdentifyMapper.save(authIdentify);
                return new Response<>(CodeEnum.IDENTIFY_FAIL,null);
            }
        }else{
            authIdentify.setCode(AuthIdentifyEnum.Fail.getCode());
            authIdentifyMapper.save(authIdentify);
            return new Response<>(CodeEnum.IDENTIFY_FAIL,null);
        }
    }

    @Override
    public Response getAuthIdentifyInfo(String taskId)throws Exception {

        String tid = AesUtils.aesDecryptHexString(taskId, firmConfiguration.getWjl());
        Long id = Long.valueOf(tid);
        log.info("-----查询实名认证信息Id="+id+"-----");
        AuthIdentify authIdentify=authIdentifyMapper.findById(id);
        if (null != authIdentify){
            //如果不为空，将认证信息返回去
            AuthIdentifyVo authIdentifyVo=new AuthIdentifyVo();
            authIdentifyVo.setIdcard(authIdentify.getIdCard());
            authIdentifyVo.setCreateTime(authIdentify.getCreateTime());
            authIdentifyVo.setName(authIdentify.getName());
            authIdentifyVo.setMobile(authIdentify.getMobile());
            authIdentifyVo.setQueryTime(System.currentTimeMillis());
            if(AuthIdentifyEnum.SUCCESS.getCode().equals(authIdentify.getCode())){
                authIdentifyVo.setCode(Integer.valueOf(CodeEnum.QUERY_SUCCESS.getCode()));
            }else{
                authIdentifyVo.setCode(Integer.valueOf(CodeEnum.QUERY_FAIL.getCode()));
            }
            return new Response(CodeEnum.QUERY_SUCCESS,authIdentifyVo);
        }else{
            return new Response(CodeEnum.QUERY_FAIL,null);
        }
    }

    /**
     * url map拼接
     * @param params
     * @return
     */
    private String paramsMapToStr(Map<String, Object> params) {
        StringBuilder paramBuilder = new StringBuilder();
        if (params != null && params.size() > 0) {
            params.forEach((k, v) -> paramBuilder.append("&").append(k).append("=").append(v));
        }
        return paramBuilder.toString();
    }


}
