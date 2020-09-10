package ai.deeproute.study.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //log.info("Server received {}B message:\n{}", byteBuf.readableBytes(), new String(NettyUtils.readAllBytes(byteBuf)));
        log.info("Server received {}B message", byteBuf.readableBytes());
        super.channelRead(ctx, msg);
    }
}
