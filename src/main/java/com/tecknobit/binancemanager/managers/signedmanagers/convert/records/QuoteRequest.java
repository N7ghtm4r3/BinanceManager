package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class QuoteRequest extends BinanceItem {

    private final long quoteId;
    private final double ratio;
    private final double inverseRatio;
    private final long validTimestamp;
    private final double toAmount;
    private final double fromAmount;

    public QuoteRequest(long quoteId, double ratio, double inverseRatio, long validTimestamp, double toAmount,
                        double fromAmount) {
        super(null);
        this.quoteId = quoteId;
        this.ratio = ratio;
        this.inverseRatio = inverseRatio;
        this.validTimestamp = validTimestamp;
        this.toAmount = toAmount;
        this.fromAmount = fromAmount;
    }

    public QuoteRequest(JSONObject jQuoteRequest) {
        super(jQuoteRequest);
        quoteId = hItem.getLong("quoteId", 0);
        ratio = hItem.getDouble("ratio", 0);
        inverseRatio = hItem.getDouble("inverseRatio", 0);
        validTimestamp = hItem.getLong("validTimestamp", 0);
        toAmount = hItem.getDouble("toAmount", 0);
        fromAmount = hItem.getDouble("fromAmount", 0);
    }

    public long getQuoteId() {
        return quoteId;
    }

    public double getRatio() {
        return ratio;
    }

    public double getInverseRatio() {
        return inverseRatio;
    }

    public long getValidTimestamp() {
        return validTimestamp;
    }

    public double getToAmount() {
        return toAmount;
    }

    public double getFromAmount() {
        return fromAmount;
    }

}
