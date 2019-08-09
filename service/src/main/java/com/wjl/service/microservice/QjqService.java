package com.wjl.service.microservice;


import com.wjl.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="Qjq-Service",fallback=QjqServiceFallback.class)
public interface QjqService {
    @RequestMapping(value = "/qjqService/getCount",method = RequestMethod.POST)
    public int getCount();

    @RequestMapping(value = "/qjqService/getUser",method = RequestMethod.POST)
    public User getUser();

}
