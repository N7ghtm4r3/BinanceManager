package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import org.json.JSONObject;

public class SwapQuote extends SwapStructure {

    private final double slippage;

    public SwapQuote(String quoteAsset, String baseAsset, double quoteQuantity, double baseQuantity, double price,
                     double fee, double slippage) {
        super(quoteAsset, baseAsset, quoteQuantity, baseQuantity, price, fee);
        this.slippage = slippage;
    }

    public SwapQuote(JSONObject jSwapQuote) {
        super(jSwapQuote);
        slippage = hItem.getDouble("slippage", 0);
    }

    public double getSlippage() {
        return slippage;
    }

}
