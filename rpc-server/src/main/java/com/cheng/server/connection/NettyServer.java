package com.cheng.server.connection;

import com.cheng.NettyConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Data
@Component
public class NettyServer {

    @Autowired
    private final ServerBootstrap bootstrap;

    private final InetSocketAddress address = new InetSocketAddress(NettyConstants.SERVER_PORT);

    private  Channel serverChannel;


    public void startServer() throws InterruptedException {
        serverChannel = bootstrap.bind(address).sync().channel().closeFuture().channel();
    }
}
