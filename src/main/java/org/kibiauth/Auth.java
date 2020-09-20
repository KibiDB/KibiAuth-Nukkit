package org.kibiauth;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import org.kibiauth.command.LoginCommand;
import org.kibiauth.command.RegisterCommand;

import java.io.File;

public class Auth extends PluginBase {
    private static Auth instance;
    private static Config kibi;

    @Override
    public void onEnable() {
        instance = this;

        getDataFolder().mkdirs();

        if (!new File(getDataFolder() + "/kibidb.yml").exists()) {
            Config config = new Config(getDataFolder() + "/kibidb.yml", Config.YAML);
            config.set("address", "127.0.0.1");
            config.set("port", 3306);
            config.set("password", "123456");
            config.save();
        }

        kibi = new Config(getDataFolder() + "/kibidb.yml", Config.YAML);

        getServer().getPluginManager().registerEvents(new Listener(), this);
        getServer().getCommandMap().register("/register", new RegisterCommand());
        getServer().getCommandMap().register("/login", new LoginCommand());
    }

    public static Auth getInstance() {
        return instance;
    }

    public static Config getKibi() {
        return kibi;
    }
}
