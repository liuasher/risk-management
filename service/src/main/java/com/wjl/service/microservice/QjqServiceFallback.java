package com.wjl.service.microservice;

import com.wjl.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QjqServiceFallback implements QjqService {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public User getUser() {
        return null;
    }
}
