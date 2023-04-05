package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class SwapBaseStructure extends BinanceItem {

    protected final String quoteAsset;
    protected final String baseAsset;
    protected final double price;
    protected final double fee;

    public SwapBaseStructure(String quoteAsset, String baseAsset, double price, double fee) {
        super(null);
        this.quoteAsset = quoteAsset;
        this.baseAsset = baseAsset;
        this.price = price;
        this.fee = fee;
    }

    public SwapBaseStructure(JSONObject jSwapBaseStructure) {
        super(jSwapBaseStructure);
        quoteAsset = hItem.getString("quoteAsset");
        baseAsset = hItem.getString("baseAsset");
        price = hItem.getDouble("price", 0);
        fee = hItem.getDouble("fee", 0);
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public String getBaseAsset() {
        return baseAsset;
    }

    public double getPrice() {
        return price;
    }

    public double getFee() {
        return fee;
    }

}
