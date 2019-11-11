package com.cheng.proxy;

import com.cheng.entity.Request;
import com.cheng.transport.SendTool;

import java.lang.reflect.Method;
import java.util.UUID;

public class RPCProxy<T>  implements java.lang.reflect.InvocationHandler {

    private Class<T> clz;

    public RPCProxy() {
    }

    public RPCProxy(Class<T> clz){
        this.clz = clz;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(clz.getName());
        request.setMethodName(method.getName());
        request.setArgs(args);
        request.setParameterTypes(method.getParameterTypes());
        return SendTool.sendMsg(request).getResult();
    }
}
