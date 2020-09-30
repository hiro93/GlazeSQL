package net.sk32.glaze.engine.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AsciiString;
import net.sk32.glaze.engine.network.protocol.CapabilityFlags;
import net.sk32.glaze.engine.network.protocol.HandshakeV10;

public class HandshakeV10Encoder extends MessageToByteEncoder<HandshakeV10> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HandshakeV10 msg, ByteBuf out) throws Exception {
        out.writeByte(msg.getProtocolVersion());
        out.writeBytes(AsciiString.of(msg.getServerVersion()).array());
        out.writeByte(0x00);
        out.writeIntLE(msg.getThreadId());
        // first 8 bytes of the plugin provided data (scramble)
        out.writeBytes(msg.authPluginData, 0, 8);
        out.writeByte(0x00);
        // The lower 2 bytes of the Capabilities Flags
        out.writeShortLE((int) msg.capabilityFlags());
        // default server a_protocol_character_set, only the lower 8-bits
        out.writeByte(msg.characterSet);
        out.writeShortLE(msg.statusFlags);
        // The upper 2 bytes of the Capabilities Flags
        out.writeShortLE((int) (msg.capabilityFlags() >> Short.SIZE));
        if (msg.getCapabilities().contains(CapabilityFlags.CLIENT_PLUGIN_AUTH)) {
            out.writeByte(msg.authPluginData.length);
        } else {
            out.writeByte(0x00);
        }
        // reserved. All 0s.
        out.writeZero(10);
        // Rest of the plugin provided data (scramble), $len=MAX(13, length of auth-plugin-data - 8)
        out.writeBytes(msg.authPluginData, 8, msg.authPluginData.length - 8);
        if (msg.getCapabilities().contains(CapabilityFlags.CLIENT_PLUGIN_AUTH)) {
            // name of the auth_method that the auth_plugin_data belongs to
            out.writeBytes(AsciiString.of(msg.authPluginName).array());
            out.writeByte(0x00);
        }
    }
}
