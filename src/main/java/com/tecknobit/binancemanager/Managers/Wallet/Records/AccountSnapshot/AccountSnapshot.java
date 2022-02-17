package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshot;

import com.tecknobit.binancemanager.Managers.Wallet.BinanceWalletManager;
import org.json.JSONArray;

public class AccountSnapshot {

    private final int code;
    private final String msg;
    private final String type;
    private final JSONArray jsonArray;

    public AccountSnapshot(int code, String msg, String type, JSONArray jsonArray) {
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.jsonArray = jsonArray;
    }

    public AccountSnapshot getAccountSnapshot(){
        switch (type){
            case BinanceWalletManager.SPOT:
                return new AccountSnapshotSpot(code,msg,type,jsonArray).getAccountSnapshotSpot();
            case BinanceWalletManager.MARGIN: return null;
            default: return null;
        }
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

}
