package org.kibiauth.command;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.player.AuthPlayer;
import org.kibiauth.player.PlayerData;
import org.kibiauth.scheduler.RegisterPlayerTask;

public class RegisterCommand extends VanillaCommand {

    public RegisterCommand() {
        super("register", "Register Command", "/register <password>", new String[]{});
    }

    public boolean execute(CommandSender sender, String s, String[] args) {
        if (sender instanceof ConsoleCommandSender) return false;

        if (!((args.length) >= 1)) {
            sender.sendMessage(TextFormat.YELLOW + getUsage());

            return false;
        }

        AuthPlayer authPlayer = ((AuthPlayer) sender);
        PlayerData source = authPlayer.getData();

        if (source.getStatus() == PlayerData.STATUS_SEARCH) {
            authPlayer.sendMessage(TextFormat.RED + "Your data has not been loaded yet... Please try again");

            return false;
        }

        if (source.getStatus() == PlayerData.STATUS_NOT_AUTHENTICATED) {
            authPlayer.sendMessage(TextFormat.RED + "You are already registered, use /login <password>");

            return false;
        }

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) {
            authPlayer.sendMessage(TextFormat.RED + "You have already logged into the server");

            return false;
        }

        if (args[0].length() <= 4) {
            authPlayer.sendMessage(TextFormat.RED + "The password is very short, it must contain at least 8 characters!");

            return false;
        }

        Thread register = new RegisterPlayerTask(authPlayer.getName(), args[0]);
        register.start();

        authPlayer.handleAuthAttributes(true);
        authPlayer.sendMessage(TextFormat.GREEN + "You have successfully registered!");

        return false;
    }
}
