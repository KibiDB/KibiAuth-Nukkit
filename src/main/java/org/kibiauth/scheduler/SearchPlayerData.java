package org.kibiauth.scheduler;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import com.kibi.driver.Client;
import org.kibiauth.Auth;
import org.kibiauth.player.AuthPlayer;
import org.kibiauth.player.PlayerData;

public class SearchPlayerData extends Thread {

    private final Player player;

    public SearchPlayerData(Player player) {
        this.player = player;
    }

    public void run() {
        Config kibi = Auth.getKibi();

        Client client = new Client(kibi.getString("address"), kibi.getInt("port"), kibi.getString("password"));

        String result = client.getConnection().get(player.getName());

        PlayerData source = ((AuthPlayer) player).getData();

        if (result == null) {
            source.setStatus(PlayerData.STATUS_NOT_REGISTERED);

            return;
        }

        source.setPassword(result);
        source.setStatus(PlayerData.STATUS_NOT_AUTHENTICATED);

    }
}
