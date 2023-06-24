package WeChat.Ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class webServer {

    @Value("${ws.port}")
    private int port;


    public void stack() throws Exception {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 设置工作的事件组是长连接
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 工作的那个组 会调用这个handler 处理器处理数据
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                // 加入http 解码器
                socketChannel
                        .pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new HttpObjectAggregator(8192))
                        // 使用netty的ws 协议升级 http协议
                        .addLast(new WebSocketServerProtocolHandler("/hello"))
                        .addLast(new WebSocketServerProtocolHandler("/hello1"))
                        .addLast(new WsHandler());
            }
        });

        ChannelFuture bind = serverBootstrap.bind(port).sync();

        bind.addListener((ChannelFutureListener) channelFuture -> {
            if (bind.isDone()) {
                System.out.println("netty 服务器启动成功,端口 : " + port);
            }
        });

    }


    public static void main(String[] args) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 设置工作的事件组是长连接
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 工作的那个组 会调用这个handler 处理器处理数据
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                // 加入http 解码器
                socketChannel
                        .pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new HttpObjectAggregator(8192))
                        // 使用netty的ws 协议升级 http协议
                        .addLast(new WebSocketServerProtocolHandler("/hello"))

                        .addLast(new WsHandler());
            }
        });

        ChannelFuture bind = serverBootstrap.bind(19999).sync();

        bind.addListener((ChannelFutureListener) channelFuture -> {
            if (bind.isDone()) {
                System.out.println("netty 服务器启动成功,端口 : " + 19999);
            }
        });
    }


}



