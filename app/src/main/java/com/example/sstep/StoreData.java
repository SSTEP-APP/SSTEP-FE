package com.example.sstep;

import android.app.Application;

public class StoreData extends Application {
    private long storeId;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
