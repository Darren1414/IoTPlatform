package com.starfz.connector.server;

import com.starfz.connector.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


@Component("connector-server")
public class Server {

    @Value("${server.port}")
    private Integer port;

    public Server() {

    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();                        // 创建 EventLoopGroup

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();                   // 创建 ServerBootstrap
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)                       // 指定使用 NIO 的传输 Channel
                    .localAddress(new InetSocketAddress(port))                   // 设置 socket 地址使用所选的端口
                    .childHandler(new ChannelInitializer<SocketChannel>() {      // 添加 ServerHandler 到 Channel 的 ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync();

            System.out.println(Server.class.getName() + " started and listen on " + future.channel().localAddress());

            future.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully().sync();
        }
    }
}
