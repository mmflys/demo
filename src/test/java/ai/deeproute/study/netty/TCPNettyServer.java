package ai.deeproute.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPNettyServer {
    private int serverPort;
    private ServerSocketChannel socketChannel;

    public TCPNettyServer(int serverPort) {
        this.serverPort = serverPort;
        initChannel();
    }

    public void initChannel() {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new ServerHandler());

                            log.info("New TCP channel pipeline initialized. Remote address={}. Channel type={}.",
                                    ch.remoteAddress(), ch.getClass().getName());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.bind(serverPort).sync();
            this.socketChannel = (ServerSocketChannel) f.channel();

            log.info("TCP server Channel started to bind to {}", this.serverPort);
        } catch (Exception e) {
            log.error("Failed to initialize TCP channel after several tries. Cause: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        TCPNettyServer server = new TCPNettyServer(20000);
    }
}
