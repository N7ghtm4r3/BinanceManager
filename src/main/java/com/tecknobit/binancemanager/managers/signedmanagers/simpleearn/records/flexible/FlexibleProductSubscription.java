package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class FlexibleProductSubscription extends BinanceItem {

    protected final long purchaseId;

    protected final boolean success;

    public FlexibleProductSubscription(long purchaseId, boolean success) {
        super(null);
        this.purchaseId = purchaseId;
        this.success = success;
    }

    public FlexibleProductSubscription(JSONObject jFlexibleProductSubscription) {
        super(jFlexibleProductSubscription);
        purchaseId = hItem.getLong("purchaseId", 0);
        success = hItem.getBoolean("success");
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public boolean isSuccess() {
        return success;
    }

}
