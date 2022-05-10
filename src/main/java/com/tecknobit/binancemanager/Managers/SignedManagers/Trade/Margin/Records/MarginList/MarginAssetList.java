package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Order.*;

/**
 *  The {@code MarginAssetList} class is useful to format Binance Margin List response request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginAssetList {

    private final String asset;
    private final long txId;
    private long timestamp;
    private String status;

    public MarginAssetList(String asset, long txId, long timestamp, String status) {
        this.asset = asset;
        this.txId = txId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getAsset() {
        return asset;
    }

    public long getTxId() {
        return txId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        if(timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equals(STATUS_CONFIRMED) || status.equals(STATUS_PENDING) || status.equals(STATUS_FAILED))
            this.status = status;
        else
            throw new IllegalArgumentException("Status can be only CONFIRMED, PENDING or FAILED");
    }

}
