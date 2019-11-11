package com.cheng.handler;

import com.cheng.entity.DefaultFuture;
import com.cheng.entity.Request;
import com.cheng.entity.Response;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler extends ChannelDuplexHandler {

   private Map<String, DefaultFuture> futureMap = new HashMap();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof Response){
            Response response = (Response) msg;
            DefaultFuture defaultFuture = futureMap.get(response.getRequestId());
            defaultFuture.setResponse(response);

        }
        super.channelRead(ctx, msg);
    }



    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof Request){

            futureMap.putIfAbsent(((Request) msg).getRequestId(),new DefaultFuture());
        }
        super.write(ctx, msg, promise);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public Response getRpcResponse(String requestId){

        try {
            DefaultFuture response = futureMap.get(requestId);
            return response.getResponse(0);
        }finally {
            futureMap.remove(requestId);
        }


    }
}
