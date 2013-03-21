package com.ebuddy.twitter.nio.server;

import com.ebuddy.twitter.TweetDataStore;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 2:47 AM
 */
public class NioHttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast("decoder", new HttpRequestDecoder());
        p.addLast("encoder", new HttpResponseEncoder());
        p.addLast("handler", new NioHttpServerHandler());
    }

}
