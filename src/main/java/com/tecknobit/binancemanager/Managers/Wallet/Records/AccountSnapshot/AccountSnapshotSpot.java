package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshot;

import org.json.JSONArray;

public class AccountSnapshotSpot extends AccountSnapshot{

    public AccountSnapshotSpot(int code, String msg, String type, long updateTime, JSONArray jsonArray) {
        super(code, msg, type, updateTime, jsonArray);
    }

}
