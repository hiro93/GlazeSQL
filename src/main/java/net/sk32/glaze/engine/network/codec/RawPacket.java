package net.sk32.glaze.engine.network.codec;

import lombok.Data;

/**
 *
 */
@Data
public class RawPacket {

    private final int payloadLength;
    private final byte sequenceId;
    private final byte[] payload;

    public RawPacket(byte sequenceId, int payloadLength, byte[] payload) {
        this.sequenceId = sequenceId;
        this.payloadLength = payloadLength;
        this.payload = payload;
    }
}
