package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code IsolatedMarginFee} class is useful to format Binance Isolated Margin Fee request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class IsolatedMarginFee {

    private final int vipLevel;
    private final String symbol;
    private final int leverage;
    private ArrayList<IsolatedData> isolatedData;

    public IsolatedMarginFee(int vipLevel, String symbol, int leverage, JSONArray jsonArray) {
        this.vipLevel = vipLevel;
        this.symbol = symbol;
        this.leverage = leverage;
        loadIsolatedData(jsonArray);
    }

    /** Method to assemble a IsolatedData list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadIsolatedData(JSONArray jsonArray) {
        isolatedData = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++) {
            JSONObject data = jsonArray.getJSONObject(j);
            isolatedData.add(new IsolatedData(data.getString("coin"),
                    data.getDouble("dailyInterest"),
                    data.getDouble("borrowLimit")
            ));
        }
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLeverage() {
        return leverage;
    }

    public ArrayList<IsolatedData> getIsolatedDataList() {
        return isolatedData;
    }

    public IsolatedData getIsolatedData(int index) {
        return isolatedData.get(index);
    }

    /**
     * The {@code IsolatedData} class is useful to obtain and format IsolatedData object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data
     * **/

    public static class IsolatedData {

        private final String coin;
        private final double dailyInterest;
        private final double borrowLimit;

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

        public double getBorrowLimit() {
            return borrowLimit;
        }

    }

}
