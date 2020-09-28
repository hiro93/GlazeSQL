package net.sk32.glaze.engine.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.AsciiString;
import net.sk32.glaze.engine.network.protocol.HandshakeV10;

public class HandshakeV10Encoder extends MessageToByteEncoder<HandshakeV10> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HandshakeV10 msg, ByteBuf out) throws Exception {
        out.writeByte(msg.protocolVersion);
        out.writeBytes(AsciiString.of(msg.serverVersion).array());
        out.writeByte(0x00);
        out.writeIntLE(msg.threadId);
        out.writeBytes(msg.authPluginData, 0, 8);
        out.writeByte(0x00);
        out.writeShortLE(msg.capabilityFlags);
        out.writeByte(msg.characterSet);
        out.writeShortLE(msg.statusFlags);
        out.writeShortLE(msg.capabilityFlags >> Short.SIZE);
    }
}
