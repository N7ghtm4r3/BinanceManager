package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginRepay} class is useful to format Binance Margin Query Repay record request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginRepay {

    /**
     * {@code total} is instance that memorizes total size about {@link #marginRepayAssetsList}
     * **/
    private int total;

    /**
     * {@code marginRepayAssetsList} is instance that memorizes list of {@link MarginRepayAsset}
     * **/
    private ArrayList<MarginRepayAsset> marginRepayAssetsList;

    /** Constructor to init {@link MarginRepay} object
     * @param jsonRepay: repay details in JSON format
     * @throws IllegalArgumentException when repay details are not recoverable
     * **/
    public MarginRepay(JSONObject jsonRepay) {
        JSONArray jsonAssets = new JsonHelper(jsonRepay).getJSONArray("rows");
        if(jsonAssets != null){
            total = jsonAssets.length();
            loadMarginRepayAssets(jsonAssets);
        }else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to load RepayAssets list
     * @param jsonAssets: obtained from Binance's request
     * **/
    private void loadMarginRepayAssets(JSONArray jsonAssets) {
        marginRepayAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++){
            JSONObject jsonObject = jsonAssets.getJSONObject(j);
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

    public ArrayList<MarginRepayAsset> getMarginRepayAssetsList() {
        return marginRepayAssetsList;
    }

    public void setMarginRepayAssetsList(ArrayList<MarginRepayAsset> marginRepayAssetsList) {
        this.marginRepayAssetsList = marginRepayAssetsList;
        total = marginRepayAssetsList.size();
    }

    public void insertMarginRepayAsset(MarginRepayAsset marginRepayAsset){
        if(!marginRepayAssetsList.contains(marginRepayAsset)){
            marginRepayAssetsList.add(marginRepayAsset);
            total += 1;
        }
    }

    public boolean removeMarginRepayAsset(MarginRepayAsset marginRepayAsset){
        boolean removed = marginRepayAssetsList.remove(marginRepayAsset);
        if(removed)
           total -= 1;
        return removed;
    }

    public MarginRepayAsset getMarginRepayAsset(int index) {
        return marginRepayAssetsList.get(index);
    }

    @Override
    public String toString() {
        return "MarginRepay{" +
                "total=" + total +
                ", marginRepayAssetsList=" + marginRepayAssetsList +
                '}';
    }

    /**
     * The {@code MarginRepayAsset} class is useful to obtain and format MarginRepayAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * **/

    public static class MarginRepayAsset extends MarginLoan.MarginLoanAsset {

        /**
         * {@code amount} is instance that memorizes amount of asset
         * **/
        private double amount;

        /**
         * {@code interest} is instance that memorizes interest on the asset
         * **/
        private double interest;

        /** Constructor to init {@link MarginLoan.MarginLoanAsset} object
         * @param asset: asset
         * @param txId: identifier of transaction
         * @param timestamp: timestamp of transaction
         * @param status: status of transaction
         * @param isolatedSymbol: symbol of the asset
         * @param principal: principal value
         * @param amount: amount of asset
         * @param interest: interest on the asset
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public MarginRepayAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                                double principal, double amount, double interest) {
            super(asset, txId, timestamp, status, isolatedSymbol, principal);
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            else
                this.amount = amount;
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
        }

        public double getAmount() {
            return amount;
        }

        /** Method to set {@link #amount}
         * @param amount: amount of asset
         * @throws IllegalArgumentException when amount value is less than 0
         * **/
        public void setAmount(double amount) {
            if(amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        public double getInterest() {
            return interest;
        }

        /** Method to set {@link #interest}
         * @param interest: interest on the asset
         * @throws IllegalArgumentException when interest value is less than 0
         * **/
        public void setInterest(double interest) {
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            this.interest = interest;
        }

        @Override
        public String toString() {
            return "MarginRepayAsset{" +
                    "amount=" + amount +
                    ", interest=" + interest +
                    ", asset='" + asset + '\'' +
                    ", txId=" + txId +
                    ", timestamp=" + timestamp +
                    ", status='" + status + '\'' +
                    '}';
        }

    }

}
