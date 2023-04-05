package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import org.json.JSONObject;

public class SwapPreview extends SwapBaseStructure {

    private final double quoteAmount;
    private final double baseAmount;
    private final double share;
    private final double slippage;

    public SwapPreview(String quoteAsset, String baseAsset, double price, double fee, double quoteAmount,
                       double baseAmount, double share, double slippage) {
        super(quoteAsset, baseAsset, price, fee);
        this.quoteAmount = quoteAmount;
        this.baseAmount = baseAmount;
        this.share = share;
        this.slippage = slippage;
    }

    public SwapPreview(JSONObject jSwapPreview) {
        super(jSwapPreview);
        quoteAmount = hItem.getDouble("quoteAmt", 0);
        baseAmount = hItem.getDouble("baseAmt", 0);
        share = hItem.getDouble("share", 0);
        slippage = hItem.getDouble("slippage", 0);
    }

    public double getQuoteAmount() {
        return quoteAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getShare() {
        return share;
    }

    public double getSlippage() {
        return slippage;
    }

}
