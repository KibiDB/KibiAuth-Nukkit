package org.kibiauth;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.player.PlayerData;
import org.kibiauth.player.SessionStorage;
import org.kibiauth.scheduler.AuthTask;
import org.kibiauth.scheduler.SearchPlayerData;

public class Listener implements cn.nukkit.event.Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Auth.getSessionStorage().setSession(player);

        Thread searchPlayer = new SearchPlayerData(player);
        searchPlayer.start();

        PlayerData source = Auth.getSessionStorage().getSession(player);

        TaskHandler task = Auth.getInstance().getServer().getScheduler().scheduleRepeatingTask(new AuthTask(player), 20);
        source.setTask(task.getTaskId());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String[] command = message.split(" ");

        Player player = event.getPlayer();
        PlayerData source = Auth.getSessionStorage().getSession(player);

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) return;

        if (command[0].equals("/register") || command[0].equals("/login")) return;

        player.sendMessage(TextFormat.RED + "You must first login!");
        event.setCancelled();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData source = Auth.getSessionStorage().getSession(player);

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) return;

        player.sendMessage(TextFormat.RED + "You must first login!");
        event.setCancelled();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SessionStorage sessions = Auth.getSessionStorage();

        int id = sessions.getSession(player).getTask();

        if (!(id == -3)) {
            Auth.getInstance().getServer().getScheduler().cancelTask(id);
        }

        sessions.removeSession(player);
    }
}
