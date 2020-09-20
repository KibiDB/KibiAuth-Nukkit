package org.kibiauth.scheduler;

import cn.nukkit.utils.Config;
import com.kibi.driver.Client;
import org.kibiauth.Auth;

public class RegisterPlayerTask extends Thread {
    private final String name;
    private final String password;

    public RegisterPlayerTask(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void run() {
        Config kibi = Auth.getKibi();

        Client client = new Client(kibi.getString("address"), kibi.getInt("port"), kibi.getString("password"));

        client.getConnection().insert(name, password);
    }
}
