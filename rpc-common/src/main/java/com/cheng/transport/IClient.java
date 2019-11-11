package com.cheng.transport;

import com.cheng.entity.Request;
import com.cheng.entity.Response;

import java.net.InetSocketAddress;

public interface IClient {

    void initConnect(InetSocketAddress address);

    Response sendMsg(Request request);

    void close();
}
