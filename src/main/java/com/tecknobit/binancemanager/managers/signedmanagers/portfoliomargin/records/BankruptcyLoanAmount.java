package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class BankruptcyLoanAmount extends BinanceItem {

    private final String asset;
    private final double amount;

    public BankruptcyLoanAmount(String asset, double amount) {
        super(null);
        this.asset = asset;
        this.amount = amount;
    }

    public BankruptcyLoanAmount(JSONObject jBankruptcyLoanAmount) {
        super(jBankruptcyLoanAmount);
        asset = hItem.getString("asset");
        amount = hItem.getDouble("amount", 0);
    }

    public String getAsset() {
        return asset;
    }

    public double getAmount() {
        return amount;
    }

}
