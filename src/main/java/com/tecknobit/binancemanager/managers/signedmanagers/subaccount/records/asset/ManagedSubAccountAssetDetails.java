package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ManagedSubAccountAssetDetails extends BinanceItem {

    private final String coin;
    private final String name;
    private final double totalBalance;
    private final double availableBalance;
    private final double inOrder;
    private final double btcValue;

    public ManagedSubAccountAssetDetails(String coin, String name, double totalBalance, double availableBalance,
                                         double inOrder, double btcValue) {
        super(null);
        this.coin = coin;
        this.name = name;
        this.totalBalance = totalBalance;
        this.availableBalance = availableBalance;
        this.inOrder = inOrder;
        this.btcValue = btcValue;
    }

    public ManagedSubAccountAssetDetails(JSONObject jManagedSubAccountAssetDetails) {
        super(jManagedSubAccountAssetDetails);
        coin = hItem.getString("coin");
        name = hItem.getString("name");
        totalBalance = hItem.getDouble("totalBalance", 0);
        availableBalance = hItem.getDouble("availableBalance", 0);
        inOrder = hItem.getDouble("inOrder", 0);
        btcValue = hItem.getDouble("btcValue", 0);
    }

    public String getCoin() {
        return coin;
    }

    public String getName() {
        return name;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getInOrder() {
        return inOrder;
    }

    public double getBtcValue() {
        return btcValue;
    }

}
