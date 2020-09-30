package net.sk32.glaze.engine.network.protocol;

import java.util.Set;

/**
 * MySQL 握手包
 *
 * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_connection_phase_packets_protocol_handshake_v10.html">Protocol::HandshakeV10</a>
 */
public class HandshakeV10 {

    /**
     * protocol version, Always 10
     */
    public int protocolVersion;

    /**
     * server version, human readable status information
     */
    public String serverVersion;

    /**
     * thread id, a.k.a. connection id
     */
    public int threadId;

    /**
     * auth-plugin-data
     */
    public byte[] authPluginData;

    /**
     * Capabilities Flags
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/group__group__cs__capabilities__flags.html">Capabilities Flags</a>
     */
    public int capabilityFlags;

    /**
     * default server a_protocol_character_set, only the lower 8-bits
     */
    public byte characterSet;

    /**
     * SERVER_STATUS_flags_enum
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/mysql__com_8h.html">SERVER_STATUS_flags_enum</a>
     */
    public int statusFlags;

    private Set<CapabilityFlags> capabilities;

    public Set<CapabilityFlags> getCapabilities() {
        return capabilities;
    }
}
