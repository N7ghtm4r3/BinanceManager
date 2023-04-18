package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class AssetTransfer extends BinanceItem {

    protected final String from;
    protected final String to;
    protected final String asset;
    protected final double qty;
    protected final long tranId;
    protected final long time;

    public AssetTransfer(String from, String to, String asset, double qty, long tranId, long time) {
        super(null);
        this.from = from;
        this.to = to;
        this.asset = asset;
        this.qty = qty;
        this.tranId = tranId;
        this.time = time;
    }

    public AssetTransfer(JSONObject jSpotAssetTransfer) {
        super(jSpotAssetTransfer);
        from = hItem.getString("from");
        to = hItem.getString("to");
        asset = hItem.getString("asset");
        qty = hItem.getDouble("qty", 0);
        tranId = hItem.getLong("tranId", 0);
        time = hItem.getLong("time", 0);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getAsset() {
        return asset;
    }

    public double getQty() {
        return qty;
    }

    public long getTranId() {
        return tranId;
    }

    public long getTime() {
        return time;
    }

}
