package com.ebuddy.twitter.nio.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 2:16 AM
 */
public class NioHttpServer {

    private int port;

    public void run() throws Exception {
        ServerBootstrap b = new ServerBootstrap();

        try {
            b.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NioHttpServerInitializer());

            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } finally {
            b.shutdown();
        }
    }

    public void startServer(){
        System.out.println("starting http server.....");
        port = 10000;
        try {
            this.run();
        } catch (Exception e) {
            System.out.println("Error Starting netty server");
            throw new RuntimeException();
        }
    }

    public void stopServer(){
        System.out.println("Stopping http server.....");
    }
}
