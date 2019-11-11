package com.cheng.codec;

import com.cheng.serialization.ISerializable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {

    private Class<?> cls;

    private ISerializable serializable;

    public MsgDecoder(Class<?> cls, ISerializable serializable) {
        this.cls = cls;
        this.serializable = serializable;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4){
            return;
        }
        byte[] msgByte = new byte[in.readInt()];
        in.readBytes(msgByte);
        Object object = serializable.deSerializable(msgByte, cls);
        out.add(object);
    }
}
