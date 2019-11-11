package com.cheng;

import com.cheng.api.ITestService;
import com.cheng.entity.Request;
import com.cheng.entity.Response;
import com.cheng.transport.SendTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RpcClientApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RpcClientApplication.class, args);
        ITestService testService = applicationContext.getBean(ITestService.class);
        String result = testService.testService("Hello");
        System.out.println("***************** " + result);



    }

}
