package ai.deeproute.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPNettyClient {

    private int serverPort;
    private String hostname;
    private SocketChannel socketChannel;

    public TCPNettyClient(int serverPort, String hostname) {
        this.serverPort = serverPort;
        this.hostname = hostname;
        initChannel();
    }

    public void initChannel() {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new ClientHandler());

                            log.info("New TCP channel pipeline initialized. Remote address={}. Channel type={}.",
                                    ch.remoteAddress(), ch.getClass().getName());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(hostname, serverPort).sync();
            this.socketChannel = (SocketChannel) f.channel();

            log.info("TCP Client Channel started to listen to {}:{}", this.hostname, this.serverPort);
        } catch (Exception e) {
            log.error("Failed to initialize TCP channel after several tries. Cause: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        TCPNettyClient client = new TCPNettyClient(20000, "localhost");
    }
}
