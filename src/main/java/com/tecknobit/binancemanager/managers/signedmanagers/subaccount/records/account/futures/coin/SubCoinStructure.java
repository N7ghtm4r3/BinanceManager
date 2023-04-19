package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class SubCoinStructure extends BinanceItem {

    protected final double totalMarginBalanceOfBTC;
    protected final double totalUnrealizedProfitOfBTC;
    protected final double totalWalletBalanceOfBTC;
    protected final String asset;

    public SubCoinStructure(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                            double totalWalletBalanceOfBTC, String asset) {
        super(null);
        this.totalMarginBalanceOfBTC = totalMarginBalanceOfBTC;
        this.totalUnrealizedProfitOfBTC = totalUnrealizedProfitOfBTC;
        this.totalWalletBalanceOfBTC = totalWalletBalanceOfBTC;
        this.asset = asset;
    }

    public SubCoinStructure(JSONObject jSubCoinStructure) {
        super(jSubCoinStructure);
        totalMarginBalanceOfBTC = hItem.getDouble("totalMarginBalanceOfBTC", 0);
        totalUnrealizedProfitOfBTC = hItem.getDouble("totalUnrealizedProfitOfBTC", 0);
        totalWalletBalanceOfBTC = hItem.getDouble("totalWalletBalanceOfBTC", 0);
        asset = hItem.getString("asset");
    }

    public double getTotalMarginBalanceOfBTC() {
        return totalMarginBalanceOfBTC;
    }

    public double getTotalUnrealizedProfitOfBTC() {
        return totalUnrealizedProfitOfBTC;
    }

    public double getTotalWalletBalanceOfBTC() {
        return totalWalletBalanceOfBTC;
    }

    public String getAsset() {
        return asset;
    }

}
