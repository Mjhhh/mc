package com.edu.message.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        //http的编解码器
        pipeline.addLast(new HttpServerCodec());

        //用于对httpMessage进行聚合，聚合为FullHttpRequest或者FullHttpResponse
        //几乎所有的netty编程都会用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //====================以上是用于支持http的handler======================


        // ====================== 增加心跳支持 start    ======================
        // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
        // 如果是读空闲或者写空闲，不处理
//        pipeline.addLast(new IdleStateHandler(8, 10, 60));
        // 自定义的空闲状态检测
//        pipeline.addLast(new HeartBeatHandler());
        // ====================== 增加心跳支持 end    ======================


        // ====================== 以下是支持httpWebsocket ======================

        /**
         * websocket服务器处理的协议，用来指定给客户端连接访问的路由：/ws
         * 本handler会帮你处理一些繁重复杂的工作
         * 它会帮你处理握手动作：handshaking(close, ping, pong) ping + pong = 心跳
         * websocket都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 1048576*100));

        //自定义handler
        pipeline.addLast(new ChatHandler());

    }
}
