package edu.temple.langexchange;

import android.app.Application;

public class MyAccount extends Application {

    private static int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
