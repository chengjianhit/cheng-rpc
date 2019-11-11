package com.cheng;

import com.cheng.api.ITestService;
import com.cheng.proxy.ProxyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcClientApplicationTests {



    @Test
    public void testProxy() {
        ITestService testService = ProxyFactory.create(ITestService.class);
        System.out.println(testService);
    }

}
