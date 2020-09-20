package org.kibiauth.player;

import org.kibiauth.scheduler.AuthTask;

public class PlayerData {
    public final static int STATUS_SEARCH = 0;
    public final static int STATUS_NOT_AUTHENTICATED = 1;
    public final static int STATUS_NOT_REGISTERED = 2;
    public final static int STATUS_AUTHENTICATED = 3;

    private int auth_status = STATUS_SEARCH;
    private String password;
    private int task = -3;


    public int getStatus() {
        return auth_status;
    }

    public void setStatus(int type) {
        auth_status = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }
}
