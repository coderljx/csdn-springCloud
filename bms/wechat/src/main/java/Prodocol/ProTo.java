package Prodocol;

import java.nio.charset.StandardCharsets;

public class ProTo {

    /**
     * 协议魔术
     */
    public static byte[] Magic = "LJX".getBytes(StandardCharsets.UTF_8);

    /**
     * 版本
     */
    public static byte[] Version = "1.0".getBytes(StandardCharsets.UTF_8);


    /**
     * 告知服务器是文本消息
     * 0 文本消息
     */
    public static byte[] TextType = new byte[]{'0'};


}
