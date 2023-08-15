package com.example.sstep;

import android.app.Application;

public class LoginData extends Application {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
