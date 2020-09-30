package net.sk32.glaze.engine.network.protocol;

import java.util.Set;
import java.util.TreeSet;

/**
 * MySQL 握手包
 *
 * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/page_protocol_connection_phase_packets_protocol_handshake_v10.html">Protocol::HandshakeV10</a>
 */
public class HandshakeV10 {

    /**
     * protocol version, Always 10
     */
    private final int protocolVersion = 0x0A;

    /**
     * server version, human readable status information
     */
    private final String serverVersion = "8.0.16";

    /**
     * thread id, a.k.a. connection id
     */
    private int threadId;

    /**
     * auth-plugin-data
     */
    public byte[] authPluginData;

    /**
     * auth_plugin_name
     */
    public final String authPluginName = "mysql_native_password";

    /**
     * Capabilities Flags
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/group__group__cs__capabilities__flags.html">Capabilities Flags</a>
     */
    public long capabilityFlags() {
        long flag = 0;
        for (CapabilityFlags capability : capabilities) {
            flag |= 1L << capability.ordinal();
        }
        return flag;
    };

    /**
     * default server a_protocol_character_set, only the lower 8-bits
     */
    public byte characterSet;

    /**
     * SERVER_STATUS_flags_enum
     * @see <a href="https://dev.mysql.com/doc/dev/mysql-server/latest/mysql__com_8h.html">SERVER_STATUS_flags_enum</a>
     */
    public int statusFlags;

    private final Set<CapabilityFlags> capabilities = new TreeSet<>();

    public Set<CapabilityFlags> getCapabilities() {
        return capabilities;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getThreadId() {
        return threadId;
    }
}
