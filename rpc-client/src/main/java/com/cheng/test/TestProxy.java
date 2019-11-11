package com.cheng.test;

import com.cheng.proxy.ProxyFactory;
import com.cheng.serialization.ISerializable;

import java.lang.reflect.Proxy;

public class TestProxy {

    public static void main(String[] args) {
//        ISerializable serializable =(ISerializable) ProxyFactory.create(ISerializable.class);
//        System.out.println(serializable);

        HelloWorldServiceV service =(HelloWorldServiceV) Proxy.newProxyInstance(HelloWorldServiceV.class.getClassLoader(),
                new Class[]{HelloWorldServiceV.class}, new RPCProxyClient());
        String cheng = service.sayHello("CHENG");
        System.out.println("*******************"  + cheng);
    }

}
