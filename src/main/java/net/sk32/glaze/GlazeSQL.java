package net.sk32.glaze;

import net.sk32.glaze.engine.network.GlazeServer;

/**
 * GlazeSQL 启动器
 */
public class GlazeSQL {
    public static void main(String[] args) {
        GlazeServer server = new GlazeServer();
        server.start();
    }
}
