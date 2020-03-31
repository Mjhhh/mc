package com.edu.message.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {

    private static class SingletionWSServer {
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletionWSServer.instance;
    }

    private ServerBootstrap serverBootstrap;

    private WSServer(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)         //设置主从线程组
                .channel(NioServerSocketChannel.class)      //设置NIO双向通道
                .childHandler(new WSServerInitializer());   //设置子处理器，用于处理workGroup中的channel
    }

    public void start() {
        serverBootstrap.bind(8088);
        System.err.println("netty websocket service 启动完毕。");
    }
}
