package org.kibiauth;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerCreationEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.player.AuthPlayer;
import org.kibiauth.player.PlayerData;
import org.kibiauth.scheduler.AuthTask;
import org.kibiauth.scheduler.SearchPlayerData;

public class Listener implements cn.nukkit.event.Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCreation(PlayerCreationEvent event) {
        event.setPlayerClass(AuthPlayer.class);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Thread searchPlayer = new SearchPlayerData(player);
        searchPlayer.start();

        AuthPlayer source = ((AuthPlayer) player);

        TaskHandler task = Auth.getInstance().getServer().getScheduler().scheduleRepeatingTask(new AuthTask(((AuthPlayer) player)), 20);
        source.getData().setTask(task.getTaskId());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String[] command = message.split(" ");

        AuthPlayer player = ((AuthPlayer) event.getPlayer());
        PlayerData source = player.getData();

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) return;

        if (command[0].equals("/register") || command[0].equals("/login")) return;

        player.sendMessage(TextFormat.RED + "You must log in first to be able to execute commands");
        event.setCancelled();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {
        AuthPlayer player = ((AuthPlayer) event.getPlayer());

        int id = player.getData().getTask();

        if (!(id == -3)) {
            Auth.getInstance().getServer().getScheduler().cancelTask(id);
        }
    }
}
