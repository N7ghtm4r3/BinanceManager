package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IsolatedMarginFee} class is useful to format Binance Isolated Margin Fee request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginFee {

    private int vipLevel;
    private final String symbol;
    private int leverage;
    private ArrayList<IsolatedData> isolatedDataList;

    public IsolatedMarginFee(int vipLevel, String symbol, int leverage, JSONArray jsonArray) {
        this.vipLevel = vipLevel;
        this.symbol = symbol;
        this.leverage = leverage;
        loadIsolatedData(jsonArray);
    }

    /** Method to assemble a IsolatedData list
     * @param jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadIsolatedData(JSONArray jsonArray) {
        isolatedDataList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++) {
            JSONObject data = jsonArray.getJSONObject(j);
            isolatedDataList.add(new IsolatedData(data.getString("coin"),
                    data.getDouble("dailyInterest"),
                    data.getDouble("borrowLimit")
            ));
        }
    }

    public int getVipLevel() {
        return vipLevel;
    }

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
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * **/

    public static class IsolatedData {

        private final String coin;
        private double dailyInterest;
        private double borrowLimit;

        public IsolatedData(String coin, double dailyInterest, double borrowLimit) {
            this.coin = coin;
            this.dailyInterest = dailyInterest;
            this.borrowLimit = borrowLimit;
        }

        public String getCoin() {
            return coin;
        }

        public double getDailyInterest() {
            return dailyInterest;
        }

        public void setDailyInterest(double dailyInterest) {
            if(dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            this.dailyInterest = dailyInterest;
        }

        public double getBorrowLimit() {
            return borrowLimit;
        }

        public void setBorrowLimit(double borrowLimit) {
            if(borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
            this.borrowLimit = borrowLimit;
        }

    }

}
