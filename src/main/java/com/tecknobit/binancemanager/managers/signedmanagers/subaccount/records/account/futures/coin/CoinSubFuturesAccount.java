package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.SubFuturesAccount.SubFuturesAsset;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoinSubFuturesAccount extends BinanceItem {

    private final String email;
    private final ArrayList<SubFuturesAsset> assets;
    private final boolean canDeposit;
    private final boolean canTrade;
    private final boolean canWithdraw;
    private final int feeTier;
    private final long updateTime;

    public CoinSubFuturesAccount(String email, ArrayList<SubFuturesAsset> assets, boolean canDeposit, boolean canTrade,
                                 boolean canWithdraw, int feeTier, long updateTime) {
        super(null);
        this.email = email;
        this.assets = assets;
        this.canDeposit = canDeposit;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.feeTier = feeTier;
        this.updateTime = updateTime;
    }

    public CoinSubFuturesAccount(JSONObject jCoinSubFuturesAccount) {
        super(jCoinSubFuturesAccount);
        email = hItem.getString("email");
        assets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("assets");
        if (jList != null)
            for (JSONObject item : jList)
                assets.add(new SubFuturesAsset(item));
        canDeposit = hItem.getBoolean("canDeposit");
        canTrade = hItem.getBoolean("canTrade");
        canWithdraw = hItem.getBoolean("canWithdraw");
        feeTier = hItem.getInt("feeTier", 0);
        updateTime = hItem.getLong("updateTime", 0);
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<SubFuturesAsset> getAssets() {
        return assets;
    }

    public boolean canDeposit() {
        return canDeposit;
    }

    public boolean canTrade() {
        return canTrade;
    }

    public boolean canWithdraw() {
        return canWithdraw;
    }

    public int getFeeTier() {
        return feeTier;
    }

    public long getUpdateTime() {
        return updateTime;
    }

}
