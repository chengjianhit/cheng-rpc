package com.cheng.test;

import com.cheng.NettyConstants;
import com.cheng.entity.Request;
import com.cheng.transport.NettyClient;
import com.cheng.transport.SendTool;

import java.lang.reflect.Method;
import java.util.UUID;

public class RPCProxyClient implements java.lang.reflect.InvocationHandler{


    /**
     * 得到被代理对象;
     */
//    public static Object getProxy(Object obj){
//        return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(),
//                obj.getClass().getInterfaces(), new RPCProxyClient(obj));
//    }

    /**
     * 调用此方法执行
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //结果参数;
        Object result = new Object();
        // ...执行通信相关逻辑
        // ...
        return "Cheng Echo";

    }
}
