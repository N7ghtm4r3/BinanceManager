package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IsolatedMarginFee} class is useful to format Binance Isolated Margin Fee request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginFee {

    /**
     * {@code vipLevel} is instance that memorizes vip level
     * **/
    private int vipLevel;

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     * **/
    private final String symbol;

    /**
     * {@code leverage} is instance that memorizes leverage value
     * **/
    private int leverage;

    /**
     * {@code isolatedDataList} is instance that memorizes isolated data list
     * **/
    private ArrayList<IsolatedData> isolatedDataList;

    /** Constructor to init {@link IsolatedMarginFee} object
     * @param vipLevel: vip level
     * @param symbol: symbol of asset
     * @param leverage: leverage value
     * @param jsonFees: fees details in JSON format
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public IsolatedMarginFee(int vipLevel, String symbol, int leverage, JSONArray jsonFees) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        else
            this.vipLevel = vipLevel;
        this.symbol = symbol;
        if(leverage < 0)
            throw new IllegalArgumentException("Leverage value cannot be less than 0");
        else
            this.leverage = leverage;
        loadIsolatedData(jsonFees);
    }

    /** Method to assemble a IsolatedData list
     * @param jsonIsolatedData: obtained from Binance's request
     * **/
    private void loadIsolatedData(JSONArray jsonIsolatedData) {
        isolatedDataList = new ArrayList<>();
        for (int j = 0; j < jsonIsolatedData.length(); j++) {
            JSONObject data = jsonIsolatedData.getJSONObject(j);
            isolatedDataList.add(new IsolatedData(data.getString("coin"),
                    data.getDouble("dailyInterest"),
                    data.getDouble("borrowLimit")
            ));
        }
    }

    public int getVipLevel() {
        return vipLevel;
    }

    /** Method to set {@link #vipLevel}
     * @param vipLevel: vip level
     * @throws IllegalArgumentException when vip level value is less than 0
     * **/
    public void setVipLevel(int vipLevel) {
        if(vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        this.vipLevel = vipLevel;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLeverage() {
        return leverage;
    }

    /** Method to set {@link #leverage}
     * @param leverage: leverage value
     * @throws IllegalArgumentException when leverage value is less than 0
     * **/
    public void setLeverage(int leverage) {
        if(leverage < 0)
            throw new IllegalArgumentException("Leverage value cannot be less than 0");
        this.leverage = leverage;
    }

    public ArrayList<IsolatedData> getIsolatedDataList() {
        return isolatedDataList;
    }

    public void setIsolatedDataList(ArrayList<IsolatedData> isolatedDataList) {
        this.isolatedDataList = isolatedDataList;
    }

    public void insertIsolatedData(IsolatedData isolatedData){
        if(!isolatedDataList.contains(isolatedData))
            isolatedDataList.add(isolatedData);
    }

    public boolean removeIsolatedData(IsolatedData isolatedData){
        return isolatedDataList.remove(isolatedData);
    }

    public IsolatedData getIsolatedData(int index) {
        return isolatedDataList.get(index);
    }

    /**
     * The {@code IsolatedData} class is useful to obtain and format IsolatedData object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * **/

    public static class IsolatedData {

        /**
         * {@code coin} is instance that memorizes coin
         * **/
        private final String coin;

        /**
         * {@code dailyInterest} is instance that memorizes value of daily interest
         * **/
        private double dailyInterest;

        /**
         * {@code borrowLimit} is instance that memorizes value of limit for borrow
         * **/
        private double borrowLimit;

        /** Constructor to init {@link IsolatedData} object
         * @param coin: coin
         * @param dailyInterest: value of daily interest
         * @param borrowLimit: limit for borrow
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public IsolatedData(String coin, double dailyInterest, double borrowLimit) {
            this.coin = coin;
            if(dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            else
                this.dailyInterest = dailyInterest;
            if(borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
            else
                this.borrowLimit = borrowLimit;
        }

        public String getCoin() {
            return coin;
        }

        public double getDailyInterest() {
            return dailyInterest;
        }

        /** Method to set {@link #dailyInterest}
         * @param dailyInterest: value of daily interest
         * @throws IllegalArgumentException when value of daily interest is less than 0
         * **/
        public void setDailyInterest(double dailyInterest) {
            if(dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            this.dailyInterest = dailyInterest;
        }

        public double getBorrowLimit() {
            return borrowLimit;
        }

        /** Method to set {@link #borrowLimit}
         * @param borrowLimit: value of borrow limit
         * @throws IllegalArgumentException when value of borrow limit is less than 0
         * **/
        public void setBorrowLimit(double borrowLimit) {
            if(borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
            this.borrowLimit = borrowLimit;
        }

    }

}
