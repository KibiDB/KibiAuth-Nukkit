package org.kibiauth.player;

import cn.nukkit.Player;
import cn.nukkit.network.SourceInterface;

public class AuthPlayer extends Player {
    private final PlayerData data;

    public AuthPlayer(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);

        data = new PlayerData();
    }

    public PlayerData getData() {
        return data;
    }

    public void handleAuthAttributes(boolean login) {
        if (!login) {
            this.setImmobile(true);

            return;
        }

        this.setImmobile(false);
        getData().setStatus(PlayerData.STATUS_AUTHENTICATED);
    }
}
