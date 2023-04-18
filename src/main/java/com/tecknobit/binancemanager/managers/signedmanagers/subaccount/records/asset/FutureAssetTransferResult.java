package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class FutureAssetTransferResult extends BinanceItem {

    private final boolean success;
    private final long txnId;

    public FutureAssetTransferResult(boolean success, long txnId) {
        super(null);
        this.success = success;
        this.txnId = txnId;
    }

    public FutureAssetTransferResult(JSONObject jFutureAssetTransferResult) {
        super(jFutureAssetTransferResult);
        success = hItem.getBoolean("success");
        txnId = hItem.getLong("txnId", 0);
    }

    public boolean isSuccess() {
        return success;
    }

    public long getTxnId() {
        return txnId;
    }

}
