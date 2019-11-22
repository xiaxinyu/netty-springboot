package com.netty.delimiter.fixlength.server;

import com.netty.delimiter.fixlength.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixLengthServer {
    private final int port;

    public FixLengthServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(Constant.INCISION_LENGTH));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new FixLengthServerHandler());
                        }
                    });
            // 绑定端口，同步等待成功
            ChannelFuture f = bootstrap.bind(this.port).sync();
            log.info("{} started and listening for connections on {}", FixLengthServer.class.getName(), f.channel().localAddress());
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new FixLengthServer(9999).start();
    }
}