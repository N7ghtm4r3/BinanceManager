package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class PurchaseStakingProductResult extends BinanceItem {

    private final long positionId;
    private final boolean success;

    public PurchaseStakingProductResult(long positionId, boolean success) {
        super(null);
        this.positionId = positionId;
        this.success = success;
    }

    public PurchaseStakingProductResult(JSONObject jPurchaseStakingProductResult) {
        super(jPurchaseStakingProductResult);
        positionId = hItem.getLong("positionId", 0);
        success = hItem.getBoolean("success");
    }

    public long getPositionId() {
        return positionId;
    }

    public boolean isSuccess() {
        return success;
    }

}
