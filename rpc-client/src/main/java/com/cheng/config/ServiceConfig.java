package com.cheng.config;

import com.cheng.api.ITestService;
import com.cheng.api.RPCConsumer;
import com.cheng.proxy.ProxyFactory;
import com.cheng.proxy.RPCProxy;
import com.cheng.test.HelloWorldServiceV;
import com.cheng.test.RPCProxyClient;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.Set;

@Slf4j
@Component
public class ServiceConfig implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        Reflections reflections = new Reflections("com.cheng");
        Set<Class<?>> typesAnnotated = reflections.getTypesAnnotatedWith(RPCConsumer.class);
        log.info("afterPropertiesSet typesAnnotated is {}",typesAnnotated);
        for (Class<?> type:typesAnnotated) {

            beanFactory.registerSingleton(type.getSimpleName(), ProxyFactory.create(type));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
