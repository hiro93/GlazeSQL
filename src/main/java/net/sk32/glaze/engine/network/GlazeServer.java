package net.sk32.glaze.engine.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import net.sk32.glaze.engine.network.codec.MysqlPacketDecoder;

/**
 * 服务线程
 */
@Slf4j
public class GlazeServer extends Thread {
    private static final int PORT = 9527;
    public static final int IDLE_TIME_SECONDS = 3600;

    @Override
    public void run() {
        serve();
    }

    /**
     * 平平无奇的 Netty Server
     */
    private void serve() {
        // 构造两个线程池，parentGroup 用于接收客户端连接，将请求发送给 childGroup 处理
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        // 服务端启动类
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 心跳检测处理器
                        ch.pipeline().addLast(new IdleStateHandler(IDLE_TIME_SECONDS, IDLE_TIME_SECONDS, IDLE_TIME_SECONDS));
                        // MySQL Packet 解码器
                        ch.pipeline().addLast(new MysqlPacketDecoder());
                        // 前端处理器
                        ch.pipeline().addLast(new GlazeFrontHandler());
                    }
                });

        // 监听端口
        try {
            ChannelFuture future = bootstrap.bind(PORT).sync();
            log.info("[Glaze] waiting connection at channel {}...", future.channel().localAddress());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("[Glaze] 服务监听失败", e);
        } finally {
            parentGroup.shutdownGracefully();
            log.info("[Glaze] 服务正常关闭");
        }
    }
}
