package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginFee} class is useful to format {@code "Binance"} Isolated Margin Fee request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
 **/

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
     **/
    private int leverage;

    /**
     * {@code isolatedDataList} is instance that memorizes isolated data list
     **/
    private ArrayList<IsolatedData> isolatedDataList;

    /**
     * Constructor to init {@link IsolatedMarginFee} object
     *
     * @param vipLevel:         vip level
     * @param symbol:           symbol of asset
     * @param leverage:         leverage value
     * @param isolatedDataList: list of fees as {@link ArrayList} of {@link IsolatedData}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginFee(int vipLevel, String symbol, int leverage, ArrayList<IsolatedData> isolatedDataList) {
        this.vipLevel = vipLevel;
        this.symbol = symbol;
        this.leverage = leverage;
        this.isolatedDataList = isolatedDataList;
    }

    /**
     * Constructor to init {@link IsolatedMarginFee} object
     *
     * @param isolatedMarginFee: isolated margin fee details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public IsolatedMarginFee(JSONObject isolatedMarginFee) {
        vipLevel = isolatedMarginFee.getInt("vipLevel");
        if (vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        symbol = isolatedMarginFee.getString("symbol");
        leverage = isolatedMarginFee.getInt("leverage");
        if (leverage < 0)
            throw new IllegalArgumentException("Leverage value cannot be less than 0");
        loadIsolatedData(isolatedMarginFee.getJSONArray("data"));
    }

    /**
     * Method to assemble a IsolatedData list
     *
     * @param jsonIsolatedData: obtained from {@code "Binance"}'s request
     **/
    private void loadIsolatedData(JSONArray jsonIsolatedData) {
        isolatedDataList = new ArrayList<>();
        for (int j = 0; j < jsonIsolatedData.length(); j++)
            isolatedDataList.add(new IsolatedData(jsonIsolatedData.getJSONObject(j)));
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

    @Override
    public String toString() {
        return "IsolatedMarginFee{" +
                "vipLevel=" + vipLevel +
                ", symbol='" + symbol + '\'' +
                ", leverage=" + leverage +
                ", isolatedDataList=" + isolatedDataList +
                '}';
    }

    /**
     * The {@code IsolatedData} class is useful to create an isolated data object
     * **/

    public static class IsolatedData {

        /**
         * {@code coin} is instance that memorizes coin
         * **/
        protected final String coin;

        /**
         * {@code dailyInterest} is instance that memorizes value of daily interest
         * **/
        protected double dailyInterest;

        /**
         * {@code borrowLimit} is instance that memorizes value of limit for borrow
         * **/
        protected double borrowLimit;

        /** Constructor to init {@link IsolatedData} object
         * @param coin: coin
         * @param dailyInterest: value of daily interest
         * @param borrowLimit: limit for borrow
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public IsolatedData(String coin, double dailyInterest, double borrowLimit) {
            this.coin = coin;
            if (dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            else
                this.dailyInterest = dailyInterest;
            if (borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
            else
                this.borrowLimit = borrowLimit;
        }

        /**
         * Constructor to init {@link IsolatedData} object
         *
         * @param isolatedData: isolated data details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public IsolatedData(JSONObject isolatedData) {
            coin = isolatedData.getString("coin");
            dailyInterest = isolatedData.getDouble("dailyInterest");
            if (dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            borrowLimit = isolatedData.getDouble("borrowLimit");
            if (borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        }

        public String getCoin() {
            return coin;
        }

        public double getDailyInterest() {
            return dailyInterest;
        }

        /**
         * Method to get {@link #dailyInterest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #dailyInterest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getDailyInterest(int decimals) {
            return roundValue(dailyInterest, decimals);
        }


        /**
         * Method to set {@link #dailyInterest}
         *
         * @param dailyInterest: value of daily interest
         * @throws IllegalArgumentException when value of daily interest is less than 0
         **/
        public void setDailyInterest(double dailyInterest) {
            if (dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            this.dailyInterest = dailyInterest;
        }

        public double getBorrowLimit() {
            return borrowLimit;
        }

        /**
         * Method to get {@link #borrowLimit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #borrowLimit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getBorrowLimit(int decimals) {
            return roundValue(borrowLimit, decimals);
        }

        /**
         * Method to set {@link #borrowLimit}
         *
         * @param borrowLimit: value of borrow limit
         * @throws IllegalArgumentException when value of borrow limit is less than 0
         **/
        public void setBorrowLimit(double borrowLimit) {
            if (borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
            this.borrowLimit = borrowLimit;
        }

        @Override
        public String toString() {
            return "IsolatedData{" +
                    "coin='" + coin + '\'' +
                    ", dailyInterest=" + dailyInterest +
                    ", borrowLimit=" + borrowLimit +
                    '}';
        }

    }

}
