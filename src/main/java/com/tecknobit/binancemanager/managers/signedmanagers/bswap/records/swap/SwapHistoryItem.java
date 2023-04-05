package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure.BSwapStatus;
import org.json.JSONObject;

public class SwapHistoryItem extends SwapStructure {

    private final long swapId;
    private final long swapTime;
    private final BSwapStatus status;

    public SwapHistoryItem(String quoteAsset, String baseAsset, double price, double fee, double quoteQuantity,
                           double baseQuantity, long swapId, long swapTime, BSwapStatus status) {
        super(quoteAsset, baseAsset, price, fee, quoteQuantity, baseQuantity);
        this.swapId = swapId;
        this.swapTime = swapTime;
        this.status = status;
    }

    public SwapHistoryItem(JSONObject jSwapHistoryItem) {
        super(jSwapHistoryItem);
        swapId = hItem.getLong("swapId", 0);
        swapTime = hItem.getLong("swapTime", 0);
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
    }

    public long getSwapId() {
        return swapId;
    }

    public long getSwapTime() {
        return swapTime;
    }

    public BSwapStatus getStatus() {
        return status;
    }

}
