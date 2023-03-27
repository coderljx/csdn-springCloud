package server;

import Prodocol.ByteToMessage;
import Prodocol.JsonMessage;
import Prodocol.Message;
import Prodocol.MessageToByte;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class server {


    public static void main(String[] args) throws Exception{

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 设置工作的事件组是长连接
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
        // 工作的那个组 会调用这个handler 处理器处理数据
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline
                        /**
                         *    接受到的字节码进行解码
                         *    必须进行解码 不然收到的都是字节码
                         *    根据对应的协议进行解码后 才能拿到数据
                          */
                        .addLast(new MessageToByte())
                        .addLast(new ByteToMessage())
                        .addLast(new LoggingHandler())
                        .addLast(new koko());
            }
        });

        ChannelFuture bind = serverBootstrap.bind(19999).sync();

        bind.addListener((ChannelFutureListener) channelFuture -> {
            if (bind.isDone()) {

            }
        });

    }

}

class koko extends SimpleChannelInboundHandler<Message> {

    /**
     * 读取 ws发送来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        byte[] data = msg.getData();
        int len = msg.getLen();

        System.out.println("client 的数据 ： " + new String(data));

        byte[] bytes = "{ name : 'ljx' }".getBytes(StandardCharsets.UTF_8);
        Message message = new JsonMessage(bytes.length,bytes);
        ctx.writeAndFlush(message);
    }


    /**
     * 链接建立会第一个执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    }


    /**
     * 保持链接状态 跟客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 处于非活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    }
}

