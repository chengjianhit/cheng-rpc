package com.cheng.server.handler;

import com.cheng.codec.MsgDecoder;
import com.cheng.codec.MsgEncoder;
import com.cheng.entity.Request;
import com.cheng.entity.Response;
import com.cheng.serialization.JSONSerialization;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
        pipeline.addLast(new MsgEncoder(Response.class,new JSONSerialization()));
        pipeline.addLast(new MsgDecoder(Request.class,new JSONSerialization()));
        pipeline.addLast(serverHandler);
    }
}
