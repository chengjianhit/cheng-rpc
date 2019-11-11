package com.cheng.transport;

import com.cheng.NettyConstants;
import com.cheng.entity.Request;
import com.cheng.entity.Response;

public class SendTool {

    public static Response sendMsg(Request request){
        NettyClient client = new NettyClient(NettyConstants.SERVER_IP, NettyConstants.SERVER_PORT);
        client.initConnect(client.address);
        return client.sendMsg(request);
    }
}
