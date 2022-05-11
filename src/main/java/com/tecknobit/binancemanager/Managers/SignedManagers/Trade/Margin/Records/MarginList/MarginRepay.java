package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginRepay} class is useful to format Binance Margin Query Repay record request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginRepay {

    private int total;
    private ArrayList<MarginRepayAsset> marginRepayAssetsList;

    public MarginRepay(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("rows");
        total = jsonArray.length();
        loadMarginRepayAssets(jsonArray);
    }

    /** Method to load RepayAssets list
     * @param #jsonArray: obtained from Binance's request
     * any return
     * **/
    private void loadMarginRepayAssets(JSONArray jsonArray) {
        marginRepayAssetsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            marginRepayAssetsList.add(new MarginRepayAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("status"),
                    jsonObject.getString("isolatedSymbol"),
                    jsonObject.getDouble("principal"),
                    jsonObject.getDouble("amount"),
                    jsonObject.getDouble("interest")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if(total < 0)
            throw new IllegalArgumentException("Total value cannot be less than 0");
        this.total = total;
    }

    public ArrayList<MarginRepayAsset> getMarginRepayAssetsList() {
        return marginRepayAssetsList;
    }

    public void setMarginRepayAssetsList(ArrayList<MarginRepayAsset> marginRepayAssetsList) {
        this.marginRepayAssetsList = marginRepayAssetsList;
    }

    public void insertMarginRepayAsset(MarginRepayAsset marginRepayAsset){
        if(!marginRepayAssetsList.contains(marginRepayAsset)){
            marginRepayAssetsList.add(marginRepayAsset);
            setTotal(total + 1);
        }
    }

    public boolean removeMarginRepayAsset(MarginRepayAsset marginRepayAsset){
        boolean removed = marginRepayAssetsList.remove(marginRepayAsset);
        if(removed)
            setTotal(total - 1);
        return removed;
    }

    public MarginRepayAsset getMarginRepayAsset(int index) {
        return marginRepayAssetsList.get(index);
    }

    /**
     * The {@code MarginRepayAsset} class is useful to obtain and format MarginRepayAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * **/

    public static class MarginRepayAsset extends MarginLoan.MarginLoanAsset {

        private double amount;
        private double interest;

        public MarginRepayAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                                double principal, double amount, double interest) {
            super(asset, txId, timestamp, status, isolatedSymbol, principal);
            this.amount = amount;
            this.interest = interest;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

    }

}
