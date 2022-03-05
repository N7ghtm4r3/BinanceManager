package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

/**
 *  The {@code MarginAssetList} class is useful to format Binance Margin List response request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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
