package com.cheng;

import com.cheng.server.connection.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RpcServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(RpcServerApplication.class, args);
        NettyServer nettyServer = applicationContext.getBean(NettyServer.class);
        try {
            nettyServer.startServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
