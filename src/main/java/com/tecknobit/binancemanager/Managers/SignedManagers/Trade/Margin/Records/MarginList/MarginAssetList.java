package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

public class MarginAssetList {

    private final String asset;
    private final long txId;
    private final long timestamp;
    private final String status;

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

    public String getStatus() {
        return status;
    }

}
