package org.kibiauth.scheduler;

import cn.nukkit.Player;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.Auth;
import org.kibiauth.player.PlayerAuthAttributes;
import org.kibiauth.player.PlayerData;

public class AuthTask extends Task {
    private final Player player;

    public AuthTask(Player player) {
        this.player = player;
    }

    public void onRun(int i) {
        PlayerData source = Auth.getSessionStorage().getSession(player);

        if (source.getStatus() == PlayerData.STATUS_SEARCH) {
            player.sendPopup(TextFormat.RED + "Searching for information in the database...");
            PlayerAuthAttributes.handleAuthAttributes(player, false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_NOT_REGISTERED) {
            player.sendPopup(TextFormat.RED + "You are not registered use /register <password>");
            PlayerAuthAttributes.handleAuthAttributes(player, false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_NOT_AUTHENTICATED) {
            player.sendPopup(TextFormat.RED + "Login using /login <password>");
            PlayerAuthAttributes.handleAuthAttributes(player, false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) {
            PlayerAuthAttributes.handleAuthAttributes(player, true);
            source.setTask(-3);

            Auth.getInstance().getServer().getScheduler().cancelTask(getTaskId());
        }
    }
}
