package com.cheng.codec;

import com.cheng.serialization.ISerializable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder {

    private Class<?> cls;
    private ISerializable serializable;

    public MsgEncoder(Class<?> cls, ISerializable serializable) {
        this.cls = cls;
        this.serializable = serializable;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(cls != null){
            byte[] bytes = serializable.serializable(msg);
            //如果此处没有writeInt,会报错 io.netty.handler.codec.TooLongFrameException
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }
    }
}
