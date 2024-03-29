package com.netty.springboot.server;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringNettyApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;

    @Value("${netty.host}")
    private String host;

    @Autowired
    private EchoServer echoServer;

    public static void main(String[] args) {
        SpringApplication.run(SpringNettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = echoServer.start(host, port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                echoServer.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}