package com.cheng.server.serviceImpl;

import com.cheng.api.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService {
    @Override
    public String testService(String args) {

        return "Receive Message From Client is " + args;
    }
}
