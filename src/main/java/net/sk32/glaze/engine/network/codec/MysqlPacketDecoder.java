package net.sk32.glaze.engine.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * MySQL Packet Decoder
 *
 * https://dev.mysql.com/doc/dev/mysql-server/latest/PAGE_PROTOCOL.html
 */
@Slf4j
public class MysqlPacketDecoder extends ByteToMessageDecoder {

    public static final int MAX_PACKET_SIZE = 16 * 1024 * 1024;

    /**
     * MySQL Packets 被切分为 16M 大小的包，包括 payload_length，sequence_id，payload 三部分
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_basic_packets.html">MySQL Packets</a>
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // int<3>:payload_length + int<1>:sequence_id
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int payload_length = in.readByte() & 0xff;
        payload_length |= (in.readByte() & 0xff) << 8;
        payload_length |= (in.readByte() & 0xff) << 16;

        // TODO: 包的最大长度是 2^24 byte
        // Sending More Than 16Mb

        byte sequence_id = in.readByte();

        // 可读数据长度不够，继续等待
        if (in.readableBytes() < payload_length) {
            in.resetReaderIndex();
            return;
        }

        RawPacket packet = new RawPacket(sequence_id, payload_length, in.readBytes(payload_length).array());
        log.info("received Packet: sequence_id={}, payload_length={}", sequence_id, payload_length);
        out.add(packet);
    }
}
