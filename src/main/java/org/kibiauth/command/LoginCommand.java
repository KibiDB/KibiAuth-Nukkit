package org.kibiauth.command;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.utils.TextFormat;
import org.kibiauth.player.AuthPlayer;
import org.kibiauth.player.PlayerData;

public class LoginCommand extends VanillaCommand {
    public LoginCommand() {
        super("login", "Login Command", "/login <password>", new String[]{});
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

        if (source.getStatus() == PlayerData.STATUS_NOT_REGISTERED) {
            authPlayer.sendMessage(TextFormat.RED + "You have not registered, use /register <password>");

            return false;
        }

        if (source.getStatus() == PlayerData.STATUS_AUTHENTICATED) {
            authPlayer.sendMessage(TextFormat.RED + "You have already logged into the server");

            return false;
        }

        if (args[0].equals(source.getPassword())) {
            authPlayer.handleAuthAttributes(true);
            authPlayer.sendMessage(TextFormat.GREEN + "You have successfully logged into the server");

            return false;
        }

        authPlayer.sendMessage(TextFormat.RED + "Invalid password, please try again!");

        return false;
    }
}
