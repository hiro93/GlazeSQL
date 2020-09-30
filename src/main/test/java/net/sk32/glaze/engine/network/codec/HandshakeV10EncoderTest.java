package net.sk32.glaze.engine.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import net.sk32.glaze.engine.network.protocol.CapabilityFlags;
import net.sk32.glaze.engine.network.protocol.HandshakeV10;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class HandshakeV10EncoderTest {

    private final EmbeddedChannel channel = new EmbeddedChannel(new HandshakeV10Encoder());

    /**
     * 抓包数据
     */
    private final static char[] sample = {
            0x0a, 0x38, 0x2e, 0x30, 0x2e, 0x31, 0x36, 0x00, 0x66, 0x00, 0x00, 0x00, 0x35, 0x50, 0x56, 0x57,
            0x31, 0x29, 0x52, 0x74, 0x00, 0xff, 0xff, 0x21, 0x02, 0x00, 0xff, 0xc3, 0x15, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0x63, 0x04, 0x25, 0x3d, 0x60, 0x70, 0x40, 0x1e,
            0x32, 0x35, 0x11, 0x00, 0x6d, 0x79, 0x73, 0x71, 0x6c, 0x5f, 0x6e, 0x61, 0x74, 0x69, 0x76, 0x65,
            0x5f, 0x70, 0x61, 0x73, 0x73, 0x77, 0x6f, 0x72, 0x64, 0x00,
    };

    @Test
    void encode() {
        HandshakeV10 packet = new HandshakeV10();
        packet.authPluginData = new byte[]{0x35, 0x50, 0x56, 0x57, 0x31, 0x29, 0x52, 0x74,
                0x07, 0x63, 0x04, 0x25, 0x3d, 0x60, 0x70, 0x40, 0x1e, 0x32, 0x35, 0x11, 0x00};
        Set<CapabilityFlags> set = packet.getCapabilities();
        set.add(CapabilityFlags.CLIENT_LONG_PASSWORD);
        set.add(CapabilityFlags.CLIENT_FOUND_ROWS);
        set.add(CapabilityFlags.CLIENT_LONG_FLAG);
        set.add(CapabilityFlags.CLIENT_CONNECT_WITH_DB);
        set.add(CapabilityFlags.CLIENT_NO_SCHEMA);
        set.add(CapabilityFlags.CLIENT_COMPRESS);
        set.add(CapabilityFlags.CLIENT_ODBC);
        set.add(CapabilityFlags.CLIENT_LOCAL_FILES);
        set.add(CapabilityFlags.CLIENT_IGNORE_SPACE);
        set.add(CapabilityFlags.CLIENT_PROTOCOL_41);
        set.add(CapabilityFlags.CLIENT_INTERACTIVE);
        set.add(CapabilityFlags.CLIENT_SSL);
        set.add(CapabilityFlags.CLIENT_IGNORE_SIGPIPE);
        set.add(CapabilityFlags.CLIENT_TRANSACTIONS);
        set.add(CapabilityFlags.CLIENT_RESERVED);
        set.add(CapabilityFlags.CLIENT_RESERVED2);
//        set.add(CapabilityFlags.CLIENT_MULTI_STATEMENTS);
//        set.add(CapabilityFlags.CLIENT_MULTI_RESULTS);
//        set.add(CapabilityFlags.CLIENT_PS_MULTI_RESULTS);
        set.add(CapabilityFlags.CLIENT_PLUGIN_AUTH);
        set.add(CapabilityFlags.CLIENT_CONNECT_ATTRS);
        set.add(CapabilityFlags.CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA);
        set.add(CapabilityFlags.CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS);
        set.add(CapabilityFlags.CLIENT_SESSION_TRACK);
        set.add(CapabilityFlags.CLIENT_DEPRECATE_EOF);
        set.add(CapabilityFlags.CLIENT_SSL_VERIFY_SERVER_CERT);
        set.add(CapabilityFlags.CLIENT_OPTIONAL_RESULTSET_METADATA);
        set.add(CapabilityFlags.CLIENT_ZSTD_COMPRESSION_ALGORITHM);
        set.add(CapabilityFlags.CLIENT_CAPABILITY_EXTENSION);
        set.add(CapabilityFlags.CLIENT_REMEMBER_OPTIONS);

        packet.characterSet = 0x21;
        packet.statusFlags = 0x0200;

        channel.writeOutbound(packet);
        channel.finish();
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        for (int i = 0; i < sample.length; i++) {
            int aByte = buf.readByte();
            log.info("[handshake:{}] {} -> {}", i, ((int) sample[i]), aByte);
        }
    }
}