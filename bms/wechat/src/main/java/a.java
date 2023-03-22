import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class a {


    public static void main(String[] args) throws Exception{

          Bootstrap bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel SocketChannel) throws Exception {
                        SocketChannel.pipeline().addLast(new StringDecoder());
                        // 编码
                        SocketChannel.pipeline().addLast(new StringEncoder());
                        SocketChannel.pipeline()

                                .addLast(new send());
                    }
                });
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(19999));

        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (connect.isDone()) {
                    System.out.println("客户端链接成功");
                }
            }
        });
    }
}

class send extends SimpleChannelInboundHandler<String> {
    // 与服务器建立链接成功 执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("kokoko".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到服务器的数据," + msg);
    }
}