package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class CryptoLoanIncome extends BinanceItem {

    public enum LoanType {

        borrowIn,
        collateralSpent,
        repayAmount,
        collateralReturn,
        addCollateral,
        removeCollateral,
        collateralReturnAfterLiquidation

    }

    private final String asset;
    private final LoanType type;
    private final double amount;
    private final long timestamp;
    private final long tranId;

    public CryptoLoanIncome(String asset, LoanType type, double amount, long timestamp, long tranId) {
        super(null);
        this.asset = asset;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.tranId = tranId;
    }

    public CryptoLoanIncome(JSONObject jCryptoLoanIncome) {
        super(jCryptoLoanIncome);
        asset = hItem.getString("asset");
        type = LoanType.valueOf(hItem.getString("type"));
        amount = hItem.getDouble("amount", 0);
        timestamp = hItem.getLong("timestamp", 0);
        tranId = hItem.getLong("tranId", 0);
    }

    public String getAsset() {
        return asset;
    }

    public LoanType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getTranId() {
        return tranId;
    }

}
