package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ProductRedemption extends BinanceItem {

    private final long redeemId;

    private final boolean success;

    public ProductRedemption(long redeemId, boolean success) {
        super(null);
        this.redeemId = redeemId;
        this.success = success;
    }

    public ProductRedemption(JSONObject jProductRedemption) {
        super(jProductRedemption);
        redeemId = hItem.getLong("redeemId", 0);
        success = hItem.getBoolean("success");
    }

    public long getRedeemId() {
        return redeemId;
    }

    public boolean isSuccess() {
        return success;
    }

}
