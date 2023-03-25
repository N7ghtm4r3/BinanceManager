package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import org.json.JSONObject;

public class Convert extends AcceptQuote {

    private final String fromAsset;
    private final double fromAmount;
    private final String toAsset;
    private final double toAmount;
    private final double ratio;
    private final double inverseRatio;

    public Convert(long orderId, long createTime, AcceptQuoteStatus orderStatus, String fromAsset,
                   double fromAmount, String toAsset, double toAmount, double ratio, double inverseRatio) {
        super(orderId, createTime, orderStatus);
        this.fromAsset = fromAsset;
        this.fromAmount = fromAmount;
        this.toAsset = toAsset;
        this.toAmount = toAmount;
        this.ratio = ratio;
        this.inverseRatio = inverseRatio;
    }

    public Convert(JSONObject jConvertStatus) {
        super(jConvertStatus);
        fromAsset = hItem.getString("fromAsset");
        fromAmount = hItem.getDouble("fromAmount", 0);
        toAsset = hItem.getString("toAsset");
        toAmount = hItem.getDouble("toAmount", 0);
        ratio = hItem.getDouble("ratio", 0);
        inverseRatio = hItem.getDouble("inverseRatio", 0);
    }

    public String getFromAsset() {
        return fromAsset;
    }

    public double getFromAmount() {
        return fromAmount;
    }

    public String getToAsset() {
        return toAsset;
    }

    public double getToAmount() {
        return toAmount;
    }

    public double getRatio() {
        return ratio;
    }

    public double getInverseRatio() {
        return inverseRatio;
    }

}
