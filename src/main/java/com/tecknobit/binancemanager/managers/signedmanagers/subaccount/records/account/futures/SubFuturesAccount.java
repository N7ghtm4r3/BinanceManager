package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubFuturesAccount extends SubFuturesAccountStructure {

    private final String email;
    private final String asset;
    private final ArrayList<SubFuturesAsset> assets;
    private final boolean canDeposit;
    private final boolean canTrade;
    private final boolean canWithdraw;
    private final int feeTier;
    private final long updateTime;
    private final double maxWithdrawAmount;

    public SubFuturesAccount(double totalMaintenanceMargin, double totalMarginBalance, double totalOpenOrderInitialMargin,
                             double totalPositionInitialMargin, double totalUnrealizedProfit, double totalWalletBalance,
                             String email, String asset, ArrayList<SubFuturesAsset> assets, boolean canDeposit,
                             boolean canTrade, boolean canWithdraw, int feeTier, double totalInitialMargin,
                             long updateTime, double maxWithdrawAmount) {
        super(totalInitialMargin, totalMaintenanceMargin, totalMarginBalance, totalOpenOrderInitialMargin,
                totalPositionInitialMargin, totalUnrealizedProfit, totalWalletBalance);
        this.email = email;
        this.asset = asset;
        this.assets = assets;
        this.canDeposit = canDeposit;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.feeTier = feeTier;
        this.maxWithdrawAmount = maxWithdrawAmount;
        this.updateTime = updateTime;
    }

    public SubFuturesAccount(JSONObject jSubFuturesAccount) {
        super(jSubFuturesAccount);
        email = hItem.getString("email");
        asset = hItem.getString("asset");
        assets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("assets");
        if (jList != null)
            for (JSONObject item : jList)
                assets.add(new SubFuturesAsset(item));
        canDeposit = hItem.getBoolean("canDeposit");
        canTrade = hItem.getBoolean("canTrade");
        canWithdraw = hItem.getBoolean("canWithdraw");
        feeTier = hItem.getInt("feeTier", 0);
        maxWithdrawAmount = hItem.getDouble("maxWithdrawAmount", 0);
        updateTime = hItem.getLong("updateTime", 0);
    }

    public String getEmail() {
        return email;
    }

    public String getAsset() {
        return asset;
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

    public double getMaxWithdrawAmount() {
        return maxWithdrawAmount;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public static class SubFuturesAsset extends BinanceItem {

        private final String asset;
        private final double initialMargin;
        private final double maintenanceMargin;
        private final double marginBalance;
        private final double maxWithdrawAmount;
        private final double openOrderInitialMargin;
        private final double positionInitialMargin;
        private final double unrealizedProfit;
        private final double walletBalance;

        public SubFuturesAsset(String asset, double initialMargin, double maintenanceMargin, double marginBalance,
                               double maxWithdrawAmount, double openOrderInitialMargin, double positionInitialMargin,
                               double unrealizedProfit, double walletBalance) {
            super(null);
            this.asset = asset;
            this.initialMargin = initialMargin;
            this.maintenanceMargin = maintenanceMargin;
            this.marginBalance = marginBalance;
            this.maxWithdrawAmount = maxWithdrawAmount;
            this.openOrderInitialMargin = openOrderInitialMargin;
            this.positionInitialMargin = positionInitialMargin;
            this.unrealizedProfit = unrealizedProfit;
            this.walletBalance = walletBalance;
        }

        public SubFuturesAsset(JSONObject jSubFuturesAsset) {
            super(jSubFuturesAsset);
            asset = hItem.getString("asset");
            initialMargin = hItem.getDouble("initialMargin", 0);
            maintenanceMargin = hItem.getDouble("maintenanceMargin", 0);
            marginBalance = hItem.getDouble("marginBalance", 0);
            maxWithdrawAmount = hItem.getDouble("maxWithdrawAmount", 0);
            openOrderInitialMargin = hItem.getDouble("openOrderInitialMargin", 0);
            positionInitialMargin = hItem.getDouble("positionInitialMargin", 0);
            unrealizedProfit = hItem.getDouble("unrealizedProfit", 0);
            walletBalance = hItem.getDouble("walletBalance", 0);
        }

        public String getAsset() {
            return asset;
        }

        public double getInitialMargin() {
            return initialMargin;
        }

        public double getMaintenanceMargin() {
            return maintenanceMargin;
        }

        public double getMarginBalance() {
            return marginBalance;
        }

        public double getMaxWithdrawAmount() {
            return maxWithdrawAmount;
        }

        public double getOpenOrderInitialMargin() {
            return openOrderInitialMargin;
        }

        public double getPositionInitialMargin() {
            return positionInitialMargin;
        }

        public double getUnrealizedProfit() {
            return unrealizedProfit;
        }

        public double getWalletBalance() {
            return walletBalance;
        }

    }

}
