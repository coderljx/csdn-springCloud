package Prodocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Arrays;
import java.util.List;

public class ByteToMessage extends ByteToMessageDecoder  {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            // 本次请求的小于4 字节
            return;
        }
        /**
         * 根据自定义的协议 解析请求的内容
         * 就是将字节码数据进行解码 编码
         */
        byte[] Magic = new byte[ProTo.Magic.length];
        in.readBytes(Magic);
        // 请求的魔术不匹配 直接关闭链接
        if (!Arrays.equals(Magic, ProTo.Magic)) {
            ctx.close();
        }
        System.out.println(new String(Magic));

        /**
         * 读取字节码的版本号
         */
        byte[] Version = new byte[ProTo.Version.length];
        in.readBytes(Version);
        System.out.println(new String(Version));

        /**
         * 读取传递过来的文本格式
         * 固定为 : 一个字节
         */
        byte[] MessageType = new byte[1];
        in.readBytes(MessageType);
        System.out.println(new String(MessageType));

        int len = in.readInt();
        System.out.println("发送数据的长度 ： " + len);

        byte[] data = new byte[len];
        in.readBytes(data);

        Message message = new JsonMessage(len,data);
        out.add(message);
    }
}
