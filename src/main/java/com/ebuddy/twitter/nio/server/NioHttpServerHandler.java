package com.ebuddy.twitter.nio.server;


import com.ebuddy.twitter.TweetDataStore;
import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created with IntelliJ IDEA.
 * User: Ramkumar S
 * Date: 21/3/13
 * Time: 2:47 AM
 */
public class NioHttpServerHandler extends ChannelInboundMessageHandlerAdapter<Object> {

    private HttpRequest request;
    private final StringBuilder buf = new StringBuilder();

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
            Map<String, List<String>> params = queryStringDecoder.parameters();
            List<String> filter = params.get("filter");
            if(filter != null && filter.size() == 1){
                buf.setLength(0);
                buf.append(new Gson().toJson(TweetDataStore.getInstance().getLatestTweets(filter.get(0))));
            }
        }

        if (msg instanceof HttpContent) {
            if (msg instanceof LastHttpContent) {
                LastHttpContent trailer = (LastHttpContent) msg;
                writeResponse(ctx, trailer);
            }
        }
    }


    private void writeResponse(ChannelHandlerContext ctx, HttpObject currentObj) {
        boolean keepAlive = isKeepAlive(request);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, currentObj.getDecoderResult().isSuccess()? OK : BAD_REQUEST,
                Unpooled.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));

        response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");

        if (keepAlive) {
            response.headers().set(CONTENT_LENGTH, response.data().readableBytes());
            response.headers().set(CONNECTION, Values.KEEP_ALIVE);
        }

        ctx.nextOutboundMessageBuffer().add(response);

        if (!keepAlive) {
            ctx.flush().addListener(ChannelFutureListener.CLOSE);
        }
    }


    @Override
    public void endMessageReceived(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(
        ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}

