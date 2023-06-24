package test;

import Protocol.ByteToMessage;
import Protocol.MessageToByte;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class client {
    public static void main(String[] args) throws Exception {

        Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                // 让底层的tcp 保持长连接， 系统会在空闲时发送数据包 来检查连接是否正常
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel SocketChannel) throws Exception {
                        // 编码
                        SocketChannel
                                .pipeline()
                                .addLast(new ByteToMessage())
                                .addLast(new MessageToByte())
                                .addLast(new LoggingHandler())
                                .addLast((ChannelHandler) null);

                    }
                });
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(20000));

        connect.addListener((ChannelFutureListener) future -> {
            if (connect.isDone()) {

            }
        });
    }


    class koko extends ChannelInboundHandlerAdapter {

    }


}
