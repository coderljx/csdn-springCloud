package WeChat.Ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 实现ws的处理器
 * 真正进行交互的
 */
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

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

        /**
         * 不管是什么协议 都是通过管道传输出去
         * 不同过的协议只是 内容不同 本质都是通过 tcp/ip 协议之上构建的
         * 前后端约定协议的内容 然后去解析
         */
        ctx.channel().writeAndFlush(new TextWebSocketFrame("I am Server Message!"));

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
