package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import org.json.JSONObject;

public abstract class SwapStructure extends SwapBaseStructure {

    protected final double quoteQuantity;
    protected final double baseQuantity;

    public SwapStructure(String quoteAsset, String baseAsset, double price, double fee, double quoteQuantity,
                         double baseQuantity) {
        super(quoteAsset, baseAsset, price, fee);
        this.quoteQuantity = quoteQuantity;
        this.baseQuantity = baseQuantity;
    }

    public SwapStructure(JSONObject jSwapStructure) {
        super(jSwapStructure);
        quoteQuantity = hItem.getDouble("quoteQuantity", 0);
        baseQuantity = hItem.getDouble("baseQuantity", 0);
    }

    public double getQuoteQuantity() {
        return quoteQuantity;
    }

    public double getBaseQuantity() {
        return baseQuantity;
    }

}
