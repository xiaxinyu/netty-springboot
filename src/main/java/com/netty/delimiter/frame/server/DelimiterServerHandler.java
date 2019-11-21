package com.netty.delimiter.frame.server;

import com.netty.delimiter.frame.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DelimiterServerHandler extends ChannelInboundHandlerAdapter {
    int counter = 0;

    /**
     * 每次传入的消息都要调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        log.info("This is {} times receive client: [{}]", (++counter), body);
        String message = body + Constant.DELIMITER;
        ByteBuf echo = Unpooled.copiedBuffer(message.getBytes());
        ctx.writeAndFlush(echo);
    }

    /**
     * 异常捕获
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
