package Protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

//信息转换字节吗
public class MessageToByte extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        int len = msg.getLen();
        byte[] data = msg.getData();

        byte[] messageType = msg.MessageType();

        //1 写入魔术
        out.writeBytes(ProTo.Magic);

        //2 写入版本
        out.writeBytes(ProTo.Version);

        //3 消息类型
        out.writeBytes(messageType);

        //4 写入内容长度，用以切割字节 int = 4 字节
        out.writeInt(len);

        //5 写入真实的数据
        out.writeBytes(data);

    }


}
