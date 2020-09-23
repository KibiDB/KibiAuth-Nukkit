package org.kibiauth.player;

import cn.nukkit.Player;

import java.util.HashMap;
import java.util.Map;

public class SessionStorage {
    private Map<String, PlayerData> storage = new HashMap<>();

    public PlayerData getSession(Player player) {
        return storage.get(player.getName());
    }

    public void setSession(Player player) {
        storage.put(player.getName(), new PlayerData());
    }

    public void removeSession(Player player) {
        storage.remove(player.getName());
    }
}
