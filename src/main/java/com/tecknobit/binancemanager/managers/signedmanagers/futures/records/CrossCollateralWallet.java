package com.tecknobit.binancemanager.managers.signedmanagers.futures.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class CrossCollateralWallet extends BinanceItem {

    private final double totalCrossCollateral;
    private final double totalBorrowed;
    private final double totalInterest;
    private final double interestFreeLimit;
    private final String asset;
    private final ArrayList<CrossCollateral> crossCollaterals;

    public CrossCollateralWallet(double totalCrossCollateral, double totalBorrowed, double totalInterest,
                                 double interestFreeLimit, String asset, ArrayList<CrossCollateral> crossCollaterals) {
        super(null);
        this.totalCrossCollateral = totalCrossCollateral;
        this.totalBorrowed = totalBorrowed;
        this.totalInterest = totalInterest;
        this.interestFreeLimit = interestFreeLimit;
        this.asset = asset;
        this.crossCollaterals = crossCollaterals;
    }

    public CrossCollateralWallet(JSONObject jCrossCollateralWallet) {
        super(jCrossCollateralWallet);
        totalCrossCollateral = hItem.getDouble("totalCrossCollateral", 0);
        totalBorrowed = hItem.getDouble("totalBorrowed", 0);
        totalInterest = hItem.getDouble("totalInterest", 0);
        interestFreeLimit = hItem.getDouble("interestFreeLimit", 0);
        asset = hItem.getString("asset");
        crossCollaterals = new ArrayList<>();
        for (Object collateral : hItem.fetchList("crossCollaterals"))
            crossCollaterals.add(new CrossCollateral((JSONObject) collateral));
    }

    public double getTotalCrossCollateral() {
        return totalCrossCollateral;
    }

    public double getTotalBorrowed() {
        return totalBorrowed;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public double getInterestFreeLimit() {
        return interestFreeLimit;
    }

    public String getAsset() {
        return asset;
    }

    public ArrayList<CrossCollateral> getCrossCollaterals() {
        return crossCollaterals;
    }

    public static class CrossCollateral extends BinanceItem {

        private final String loanCoin;
        private final String collateralCoin;
        private final double locked;
        private final double loanAmount;
        private final double currentCollateralRate;
        private final double interestFreeLimitUsed;
        private final double principalForInterest;
        private final double interest;

        public CrossCollateral(String loanCoin, String collateralCoin, double locked, double loanAmount,
                               double currentCollateralRate, double interestFreeLimitUsed, double principalForInterest,
                               double interest) {
            super(null);
            this.loanCoin = loanCoin;
            this.collateralCoin = collateralCoin;
            this.locked = locked;
            this.loanAmount = loanAmount;
            this.currentCollateralRate = currentCollateralRate;
            this.interestFreeLimitUsed = interestFreeLimitUsed;
            this.principalForInterest = principalForInterest;
            this.interest = interest;
        }

        public CrossCollateral(JSONObject jCrossCollateral) {
            super(jCrossCollateral);
            loanCoin = hItem.getString("loanCoin");
            collateralCoin = hItem.getString("collateralCoin");
            locked = hItem.getDouble("locked", 0);
            loanAmount = hItem.getDouble("loanAmount", 0);
            currentCollateralRate = hItem.getDouble("currentCollateralRate", 0);
            interestFreeLimitUsed = hItem.getDouble("interestFreeLimitUsed", 0);
            principalForInterest = hItem.getDouble("principalForInterest", 0);
            interest = hItem.getDouble("interest", 0);
        }

        public String getLoanCoin() {
            return loanCoin;
        }

        public String getCollateralCoin() {
            return collateralCoin;
        }

        public double getLocked() {
            return locked;
        }

        public double getLoanAmount() {
            return loanAmount;
        }

        public double getCurrentCollateralRate() {
            return currentCollateralRate;
        }

        public double getInterestFreeLimitUsed() {
            return interestFreeLimitUsed;
        }

        public double getPrincipalForInterest() {
            return principalForInterest;
        }

        public double getInterest() {
            return interest;
        }

    }

}
