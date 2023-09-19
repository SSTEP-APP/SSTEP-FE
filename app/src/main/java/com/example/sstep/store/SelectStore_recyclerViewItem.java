package com.example.sstep.store;

public class SelectStore_recyclerViewItem {
    private String selectStoreName;
    private String selectStoreAddress;
    private String selectStorePerson;
    private long selectStoreId;
    private long selectStoreCode;
    private long selectStaffId;
    private boolean selectIsOwner;

    public String getSelectStoreName() {
        return selectStoreName;
    }

    public void setSelectStoreName(String selectStoreName) {
        this.selectStoreName = selectStoreName;
    }

    public String getSelectStoreAddress() {
        return selectStoreAddress;
    }

    public void setSelectStoreAddress(String selectStoreAddress) {
        this.selectStoreAddress = selectStoreAddress;
    }

    public String getSelectStorePerson() {
        return selectStorePerson;
    }

    public void setSelectStorePerson(String selectStorePerson) {
        this.selectStorePerson = selectStorePerson;
    }

    public long getSelectStoreId() {
        return selectStoreId;
    }

    public void setSelectStoreId(long selectStoreId) {
        this.selectStoreId = selectStoreId;
    }

    public long getSelectStoreCode() {
        return selectStoreCode;
    }

    public void setSelectStoreCode(long selectStoreCode) {
        this.selectStoreCode = selectStoreCode;
    }

    public long getSelectStaffId() {
        return selectStaffId;
    }

    public void setSelectStaffId(long selectStaffId) {
        this.selectStaffId = selectStaffId;
    }

    public boolean isSelectIsOwner() {
        return selectIsOwner;
    }

    public void setSelectIsOwner(boolean selectIsOwner) {
        this.selectIsOwner = selectIsOwner;
    }
}