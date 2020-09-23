package org.kibiauth.player;

import cn.nukkit.Player;
import org.kibiauth.Auth;

public class PlayerAuthAttributes {

    public static void handleAuthAttributes(Player player, boolean login) {
        if (!login) {
            player.setImmobile(true);

            return;
        }

        player.setImmobile(false);
        Auth.getSessionStorage().getSession(player).setStatus(PlayerData.STATUS_AUTHENTICATED);
    }
}
