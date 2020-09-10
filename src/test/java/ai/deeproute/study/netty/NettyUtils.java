package ai.deeproute.study.netty;

import io.netty.buffer.ByteBuf;

public class NettyUtils {
    public static byte[] readByteArray(int length, ByteBuf byteBuf) {
        byte[] data = new byte[length];
        byteBuf.readBytes(data);
        return data;
    }

    public static byte[] readAllBytes(ByteBuf byteBuf) {
        byte[] data = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(data);
        return data;
    }
}
