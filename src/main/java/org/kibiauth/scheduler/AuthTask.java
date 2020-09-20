package org.kibiauth.scheduler;

import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.Auth;
import org.kibiauth.player.AuthPlayer;
import org.kibiauth.player.PlayerData;

public class AuthTask extends Task {
    private final AuthPlayer player;

    public AuthTask(AuthPlayer player) {
        this.player = player;
    }

    public void onRun(int i) {
        PlayerData source = player.getData();

        if (source.getStatus() == PlayerData.STATUS_SEARCH) {
            player.sendPopup(TextFormat.RED + "Searching for information in the database...");
            player.handleAuthAttributes(false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_NOT_REGISTERED) {
            player.sendPopup(TextFormat.RED + "You are not registered use /register <password>");
            player.handleAuthAttributes(false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_NOT_AUTHENTICATED) {
            player.sendPopup(TextFormat.RED + "Login using /login <password>");
            player.handleAuthAttributes(false);

            return;
        }

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) {
            player.handleAuthAttributes(true);
            source.setTask(-3);

            Auth.getInstance().getServer().getScheduler().cancelTask(getTaskId());
        }
    }
}
