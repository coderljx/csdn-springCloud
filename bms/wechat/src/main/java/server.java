import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

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
                // 加入http 解码器
                socketChannel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new HttpObjectAggregator(8192))
                        // 使用netty的ws 协议升级 http协议
                        .addLast(new WebSocketServerProtocolHandler("/hello"))
                        .addLast(new koko());
            }
        });

        ChannelFuture bind = serverBootstrap.bind(19999).sync();

        bind.addListener((ChannelFutureListener) channelFuture -> {
            if (bind.isDone()) {
                System.out.println("服务器启动成功");
            }
        });

    }

}

class koko extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static int a = 1;
    /**
     * 读取 ws发送来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        System.out.println("客户端消息 ： " + text);

        a++;
        /**
         * 不管是什么协议 都是通过管道传输出去
         * 不同过的协议只是 内容不同 本质都是通过 tcp/ip 协议之上构建的
         * 前后端约定协议的内容 然后去解析
         */
        ctx.channel().writeAndFlush(new TextWebSocketFrame("I am Server Message!"));

        while (a > 3) {
            Thread.sleep(1000);
            ctx.channel().writeAndFlush(new TextWebSocketFrame("我是服务器端主动推送的消息哦!"));
        }
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

