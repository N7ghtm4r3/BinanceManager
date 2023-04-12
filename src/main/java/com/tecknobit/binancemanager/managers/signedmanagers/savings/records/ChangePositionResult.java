package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ChangePositionResult extends BinanceItem {

    private final long dailyPurchaseId;
    private final boolean success;
    private final long time;

    public ChangePositionResult(long dailyPurchaseId, boolean success, long time) {
        super(null);
        this.dailyPurchaseId = dailyPurchaseId;
        this.success = success;
        this.time = time;
    }

    public ChangePositionResult(JSONObject jChangePositionResult) {
        super(jChangePositionResult);
        dailyPurchaseId = hItem.getLong("dailyPurchaseId", 0);
        success = hItem.getBoolean("success");
        time = hItem.getLong("time", 0);
    }

    public long getDailyPurchaseId() {
        return dailyPurchaseId;
    }

    public boolean isSuccess() {
        return success;
    }

    public long getTime() {
        return time;
    }

}
