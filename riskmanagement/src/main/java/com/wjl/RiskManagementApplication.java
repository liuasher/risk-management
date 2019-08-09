package com.wjl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 风控项目启动类
 * @author hqh
 */
@EnableHystrix
@EnableHystrixDashboard
@EnableScheduling
@EnableDiscoveryClient    //服务注册与发现
@EnableFeignClients
@SpringBootApplication
@ComponentScan("com.wjl")
public class RiskManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskManagementApplication.class, args);
	}
}
