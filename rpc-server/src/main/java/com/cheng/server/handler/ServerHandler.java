package com.cheng.server.handler;

import com.cheng.entity.Request;
import com.cheng.entity.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Request> implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
        Response response = new Response();
        try {
            response.setRequestId(msg.getRequestId());
            Object result = dealRequest(msg);
            response.setResult(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setThrowable(new Throwable("Server Error"));
        }
        ctx.writeAndFlush(response);
    }

    /**
     * 业务处理
     * @param request
     * @return
     */
    private Object dealRequest(Request request) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        try {
            String className = request.getClassName();
            Class<?> clz = Class.forName(className);
            Object bean = applicationContext.getBean(clz);
            Class<?> service = bean.getClass();
            Method method = service.getMethod(request.getMethodName(), request.getParameterTypes());
            return method.invoke(bean, request.getArgs());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return new RuntimeException();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
