package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ETHRedeem extends BinanceItem {

    private final boolean success;

    private final long arrivalTime;

    public ETHRedeem(boolean success, long arrivalTime) {
        super(null);
        this.success = success;
        this.arrivalTime = arrivalTime;
    }

    public ETHRedeem(JSONObject jETHRedeem) {
        super(jETHRedeem);
        success = hItem.getBoolean("success");
        arrivalTime = hItem.getLong("arrivalTime", 0);
    }

    public boolean isSuccess() {
        return success;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

}
