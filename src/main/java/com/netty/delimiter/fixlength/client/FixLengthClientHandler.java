package com.netty.delimiter.fixlength.client;

import com.netty.delimiter.fixlength.Constant;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixLengthClientHandler extends ChannelInboundHandlerAdapter {
    private int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("Send Message: {}", Constant.ECHO_REQ);
            ctx.writeAndFlush(Unpooled.copiedBuffer(Constant.ECHO_REQ.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("This is {} times received server:	[{}]", (++counter), msg);
        System.out.println();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}