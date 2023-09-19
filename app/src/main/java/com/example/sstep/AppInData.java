package com.example.sstep;

import android.app.Application;

public class AppInData extends Application {
    private String userId;
    private long storeId;
    private long storeCode;
    private long staffId;
    private boolean owner;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(long storeCode) {
        this.storeCode = storeCode;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
