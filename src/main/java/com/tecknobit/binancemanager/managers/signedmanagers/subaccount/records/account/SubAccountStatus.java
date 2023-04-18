package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class SubAccountStatus extends BinanceItem {

    private final String email;
    private final boolean isSubUserEnabled;
    private final boolean isUserActive;
    private final long insertTime;
    private final boolean isMarginEnabled;
    private final boolean isFutureEnabled;
    private final long mobile;

    public SubAccountStatus(String email, boolean isSubUserEnabled, boolean isUserActive, long insertTime,
                            boolean isMarginEnabled, boolean isFutureEnabled, long mobile) {
        super(null);
        this.email = email;
        this.isSubUserEnabled = isSubUserEnabled;
        this.isUserActive = isUserActive;
        this.insertTime = insertTime;
        this.isMarginEnabled = isMarginEnabled;
        this.isFutureEnabled = isFutureEnabled;
        this.mobile = mobile;
    }

    public SubAccountStatus(JSONObject jSubAccountStatus) {
        super(jSubAccountStatus);
        email = hItem.getString("email");
        isSubUserEnabled = hItem.getBoolean("isSubUserEnabled");
        isUserActive = hItem.getBoolean("isUserActive");
        insertTime = hItem.getLong("insertTime", 0);
        isMarginEnabled = hItem.getBoolean("isMarginEnabled");
        isFutureEnabled = hItem.getBoolean("isFutureEnabled");
        mobile = hItem.getLong("mobile", 0);
    }

    public String getEmail() {
        return email;
    }

    public boolean isSubUserEnabled() {
        return isSubUserEnabled;
    }

    public boolean isUserActive() {
        return isUserActive;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public boolean isMarginEnabled() {
        return isMarginEnabled;
    }

    public boolean isFutureEnabled() {
        return isFutureEnabled;
    }

    public long getMobile() {
        return mobile;
    }

}
