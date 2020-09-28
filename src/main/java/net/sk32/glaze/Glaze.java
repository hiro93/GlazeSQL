package net.sk32.glaze;

import net.sk32.glaze.engine.network.GlazeServer;

public class Glaze {
    public static void main(String[] args) {
        GlazeServer server = new GlazeServer();
        server.start();
    }
}
