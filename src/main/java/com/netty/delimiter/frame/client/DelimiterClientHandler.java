package com.netty.delimiter.frame.client;

import com.netty.delimiter.frame.Constant;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DelimiterClientHandler extends ChannelInboundHandlerAdapter {
    private int counter = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String longText = Constant.getLongText();
        String shortText = Constant.getShortText();

        String message = longText;
        log.info("Charter length：{}", message.length());
        for (int i = 0; i < 10; i++) {
            log.info("Send Message: {}", message);
            ctx.writeAndFlush(Unpooled.copiedBuffer(message.getBytes()));
        }
        log.info("*********************** Finish all tasks *************************");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("This is {} times received server:	[{}]", (++counter), msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("*********************** Client finishes reading task *************************");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}