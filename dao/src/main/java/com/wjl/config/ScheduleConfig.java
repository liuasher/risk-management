package com.wjl.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 定时任务配置
 * 
 * 	配置线程池：10个线程
 * 
 *
 *
 */
@Configuration
@MapperScan("com.wjl.mapper")
public class ScheduleConfig implements SchedulingConfigurer {
 
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
 
    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    }
}
