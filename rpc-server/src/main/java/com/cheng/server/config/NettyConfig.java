package com.cheng.server.config;

import com.cheng.server.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Slf4j
@Configuration
@EnableConfigurationProperties(NettyProperties.class)
public class NettyConfig {

    @Autowired
    private NettyProperties nettyProperties;

    @Autowired
    private ServerInitializer serverInitializer;

    @Bean
    public ServerBootstrap serverBootstrap(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(),workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.TRACE))
                .option(ChannelOption.SO_BACKLOG, nettyProperties.getBacklog())
                .childHandler(serverInitializer);
        return bootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup();
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup();
    }
}
