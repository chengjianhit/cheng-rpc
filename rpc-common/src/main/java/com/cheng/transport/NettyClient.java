package com.cheng.transport;

import com.cheng.codec.MsgDecoder;
import com.cheng.codec.MsgEncoder;
import com.cheng.entity.Request;
import com.cheng.entity.Response;
import com.cheng.handler.ClientHandler;
import com.cheng.serialization.JSONSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class NettyClient implements IClient{


    public String host;
    public int port;
    public InetSocketAddress address;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
        this.address = new InetSocketAddress(host, port);
    }

    private EventLoopGroup eventLoopGroup;
    private Channel channel;

    private ClientHandler clientHandler;

    @Override
    public void initConnect(InetSocketAddress address) {
        clientHandler = new ClientHandler();
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,4));
                        pipeline.addLast(new MsgEncoder(Request.class, new JSONSerialization()));
                        pipeline.addLast(new MsgDecoder(Response.class,new JSONSerialization()));
                        pipeline.addLast(clientHandler);
                    }
                });

        try {
            channel = bootstrap.connect(address).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response sendMsg(Request request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return clientHandler.getRpcResponse(request.getRequestId());
    }

    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }
}
