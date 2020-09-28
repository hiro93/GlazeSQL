package net.sk32.glaze.engine.network.codec;

/**
 * MySQL 握手包
 * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_connection_phase_packets_protocol_handshake_v10.html">Protocol::HandshakeV10</a>
 */
public class HandshakePacket {
    private int protocolVersion;
    private String serverVersion;
    private long threadId;
    private byte[] authPluginData;

}
