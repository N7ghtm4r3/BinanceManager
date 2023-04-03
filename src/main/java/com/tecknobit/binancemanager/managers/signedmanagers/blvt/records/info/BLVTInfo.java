package com.tecknobit.binancemanager.managers.signedmanagers.blvt.records.info;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class BLVTInfo extends BinanceItem {

    private final String tokenName;
    private final String description;
    private final String underlying;
    private final double tokenIssued;
    private final String basket;
    private final ArrayList<Basket> currentBaskets;
    private final double nav;
    private final double realLeverage;
    private final double fundingRate;
    private final double dailyManagementFee;
    private final double purchaseFeePct;
    private final double dailyPurchaseLimit;
    private final double redeemFeePct;
    private final double dailyRedeemLimit;
    private final long timestamp;

    public BLVTInfo(String tokenName, String description, String underlying, double tokenIssued, String basket,
                    ArrayList<Basket> currentBaskets, double nav, double realLeverage, double fundingRate,
                    double dailyManagementFee, double purchaseFeePct, double dailyPurchaseLimit, double redeemFeePct,
                    double dailyRedeemLimit, long timestamp) {
        super(null);
        this.tokenName = tokenName;
        this.description = description;
        this.underlying = underlying;
        this.tokenIssued = tokenIssued;
        this.basket = basket;
        this.currentBaskets = currentBaskets;
        this.nav = nav;
        this.realLeverage = realLeverage;
        this.fundingRate = fundingRate;
        this.dailyManagementFee = dailyManagementFee;
        this.purchaseFeePct = purchaseFeePct;
        this.dailyPurchaseLimit = dailyPurchaseLimit;
        this.redeemFeePct = redeemFeePct;
        this.dailyRedeemLimit = dailyRedeemLimit;
        this.timestamp = timestamp;
    }

    public BLVTInfo(JSONObject jBLVTInfo) {
        super(jBLVTInfo);
        tokenName = hItem.getString("tokenName");
        description = hItem.getString("description");
        underlying = hItem.getString("underlying");
        tokenIssued = hItem.getDouble("tokenIssued", 0);
        basket = hItem.getString("basket");
        currentBaskets = new ArrayList<>();
        for (Object basket : hItem.fetchList("currentBaskets"))
            currentBaskets.add(new Basket((JSONObject) basket));
        nav = hItem.getDouble("nav", 0);
        realLeverage = hItem.getDouble("realLeverage", 0);
        fundingRate = hItem.getDouble("fundingRate", 0);
        dailyManagementFee = hItem.getDouble("dailyManagementFee", 0);
        purchaseFeePct = hItem.getDouble("purchaseFeePct", 0);
        dailyPurchaseLimit = hItem.getDouble("dailyPurchaseLimit", 0);
        redeemFeePct = hItem.getDouble("redeemFeePct", 0);
        dailyRedeemLimit = hItem.getDouble("dailyRedeemLimit", 0);
        timestamp = hItem.getLong("timestamp", 0);
    }

    public String getTokenName() {
        return tokenName;
    }

    public String getDescription() {
        return description;
    }

    public String getUnderlying() {
        return underlying;
    }

    public double getTokenIssued() {
        return tokenIssued;
    }

    public String getBasket() {
        return basket;
    }

    public ArrayList<Basket> getCurrentBaskets() {
        return currentBaskets;
    }

    public double getNav() {
        return nav;
    }

    public double getRealLeverage() {
        return realLeverage;
    }

    public double getFundingRate() {
        return fundingRate;
    }

    public double getDailyManagementFee() {
        return dailyManagementFee;
    }

    public double getPurchaseFeePct() {
        return purchaseFeePct;
    }

    public double getDailyPurchaseLimit() {
        return dailyPurchaseLimit;
    }

    public double getRedeemFeePct() {
        return redeemFeePct;
    }

    public double getDailyRedeemLimit() {
        return dailyRedeemLimit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static class Basket extends BinanceItem {

        private final String symbol;
        private final double amount;
        private final double notionalValue;

        public Basket(String symbol, double amount, double notionalValue) {
            super(null);
            this.symbol = symbol;
            this.amount = amount;
            this.notionalValue = notionalValue;
        }

        public Basket(JSONObject jBasket) {
            super(jBasket);
            symbol = hItem.getString("symbol");
            amount = hItem.getDouble("amount", 0);
            notionalValue = hItem.getDouble("notionalValue", 0);
        }

        public String getSymbol() {
            return symbol;
        }

        public double getAmount() {
            return amount;
        }

        public double getNotionalValue() {
            return notionalValue;
        }

    }

}
