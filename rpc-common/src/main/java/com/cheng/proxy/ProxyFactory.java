package com.cheng.proxy;

import com.cheng.serialization.ISerializable;

import java.lang.reflect.Proxy;

public class ProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RPCProxy<T>(interfaceClass)
        );
    }

}
