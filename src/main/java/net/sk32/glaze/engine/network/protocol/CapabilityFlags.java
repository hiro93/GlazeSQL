package net.sk32.glaze.engine.network.protocol;

/**
 * Values for the capabilities flag bitmask used by the MySQL protocol.
 * Currently need to fit into 32 bits.
 * Each bit represents an optional feature of the protocol.
 * Both the client and the server are sending these.
 * The intersection of the two determines what optional parts of the protocol will be used.
 *
 * @see <a href="https://dev.mysql.com/doc/internals/en/capability-flags.html#packet-Protocol::CapabilityFlags">
 * Capability Flags Reference Documentation</a>
 */
public enum CapabilityFlags {
    // Use the improved version of Old Password Authentication.
    CLIENT_LONG_PASSWORD,
    // Send found rows instead of affected rows in EOF_Packet.
    CLIENT_FOUND_ROWS,
    // Get all column flags.
    CLIENT_LONG_FLAG,
    // Database (schema) name can be specified on connect in Handshake Response Packet.
    CLIENT_CONNECT_WITH_DB,
    // Don't allow database.table.column.
    CLIENT_NO_SCHEMA,
    // Compression protocol supported. More...
    CLIENT_COMPRESS,
    // Special handling of ODBC behavior.
    CLIENT_ODBC,
    // Can use LOAD DATA LOCAL.
    CLIENT_LOCAL_FILES,
    // Ignore spaces before '('.
    CLIENT_IGNORE_SPACE,
    // New 4.1 protocol.
    CLIENT_PROTOCOL_41,
    // This is an interactive client.
    CLIENT_INTERACTIVE,
    // Use SSL encryption for the session.
    CLIENT_SSL,
    // Client only flag.
    CLIENT_IGNORE_SIGPIPE,
    // Client knows about transactions.
    CLIENT_TRANSACTIONS,
    // DEPRECATED: Old flag for 4.1 protocol.
    CLIENT_RESERVED,
    // DEPRECATED: Old flag for 4.1 authentication \ CLIENT_SECURE_CONNECTION.
    CLIENT_RESERVED2,
    // Enable/disable multi-stmt support.
    CLIENT_MULTI_STATEMENTS,
    // Enable/disable multi-results.
    CLIENT_MULTI_RESULTS,
    // Multi-results and OUT parameters in PS-protocol.
    CLIENT_PS_MULTI_RESULTS,
    // Client supports plugin authentication.
    CLIENT_PLUGIN_AUTH,
    // Client supports connection attributes.
    CLIENT_CONNECT_ATTRS,
    // Enable authentication response packet to be larger than 255 bytes.
    CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA,
    // Don't close the connection for a user account with expired password.
    CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS,
    // Capable of handling server state change information.
    CLIENT_SESSION_TRACK,
    // Client no longer needs EOF_Packet and will use OK_Packet instead.
    CLIENT_DEPRECATE_EOF,
    // Verify server certificate. More...
    CLIENT_SSL_VERIFY_SERVER_CERT,
    // The client can handle optional metadata information in the resultset.
    CLIENT_OPTIONAL_RESULTSET_METADATA,
    // Compression protocol extended to support zstd compression method.
    CLIENT_ZSTD_COMPRESSION_ALGORITHM,
    // This flag will be reserved to extend the 32bit capabilities structure to 64bits.
    CLIENT_CAPABILITY_EXTENSION,
    // Don't reset the options after an unsuccessful connect.
    CLIENT_REMEMBER_OPTIONS
}
