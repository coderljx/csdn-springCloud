package server;

import Protocol.ByteToMessage;
import Protocol.JsonMessage;
import Protocol.Message;
import Protocol.MessageToByte;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class client {


    public static void main(String[] args) throws Exception{

          Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel SocketChannel) throws Exception {
                        // 编码
                        SocketChannel.pipeline()

                                .addLast(new ByteToMessage())
                                .addLast(new MessageToByte())
                                .addLast(new LoggingHandler())
                                .addLast(new send());

                    }
                });
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(20000));

        connect.addListener((ChannelFutureListener) future -> {
            if (connect.isDone()) {

            }
        });
    }
}

/**
 * 指定类型
 * 只有自定义的协议类型才会进行这个处理器 其他的类型都不会进来
 */
class send extends SimpleChannelInboundHandler<Message> {
    // 与服务器建立链接成功 执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 3; i++) {
            byte[] bytes = "{ age : 25 }".getBytes(StandardCharsets.UTF_8);
            Message message = new JsonMessage(bytes.length,bytes);
            ctx.writeAndFlush(message);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        byte[] data = msg.getData();
        int len = msg.getLen();

        System.out.println("server 的数据 ： " + new String(data));
    }


}