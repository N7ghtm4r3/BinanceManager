package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshot;

import org.json.JSONArray;

public class AccountSnapshot {

    private final int code;
    private final String msg;
    private final String type;
    private final long updateTime;
    private final JSONArray jsonArray;

    public AccountSnapshot(int code, String msg, String type, long updateTime, JSONArray jsonArray) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.updateTime = updateTime;
        this.jsonArray = jsonArray;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

    public long getUpdateTime() {
        return updateTime;
    }
    
}
