package net.sk32.glaze.engine.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import net.sk32.glaze.engine.network.codec.HandshakePacket;

import java.util.Random;

/**
 * 前端通道处理器
 */
@Slf4j
public class GlazeFrontHandler extends ChannelInboundHandlerAdapter {
    private byte[] seed;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 握手
        log.info("[Glaze] channel {} active", ctx.channel());
        initialHandshake(ctx);
    }

    /**
     * 发送握手包
     *
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_connection_phase.html">Connection Phase</a>
     */
    private void initialHandshake(ChannelHandlerContext ctx) {
        // 生成认证数据
        byte[] seed = new byte[20];
        new Random().nextBytes(seed);
        this.seed = seed;

        HandshakePacket packet = new HandshakePacket();
        ctx.writeAndFlush(packet);
    }
}
