package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code MarginLoan} class is useful to format Binance Margin Query Loan repay request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginLoan {

    /**
     * {@code total} is instance that memorizes total size about {@link #marginLoanAssetsList}
     * **/
    private int total;

    /**
     * {@code marginLoanAssetsList} is instance that memorizes list of {@link MarginLoanAsset}
     * **/
    private ArrayList<MarginLoanAsset> marginLoanAssetsList;

    /** Constructor to init {@link MarginLoan} object
     * @param jsonLoan: loan details in JSON format
     * @throws IllegalArgumentException when loan details are not recoverable
     * **/
    public MarginLoan(JSONObject jsonLoan) {
        JSONArray jsonAssets = jsonLoan.getJSONArray("rows");
        if(jsonAssets != null) {
            total = jsonAssets.length();
            loadMarginLoanAssets(jsonAssets);
        }else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /** Method to load LoanAssets list
     * @param jsonAssets: obtained from Binance's request
     * any return
     * **/
    private void loadMarginLoanAssets(JSONArray jsonAssets) {
        marginLoanAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++){
            JSONObject jsonObject = jsonAssets.getJSONObject(j);
            marginLoanAssetsList.add(new MarginLoanAsset(jsonObject.getString("asset"),
                    jsonObject.getLong("txId"),
                    jsonObject.getLong("timestamp"),
                    jsonObject.getString("status"),
                    jsonObject.getString("isolatedSymbol"),
                    jsonObject.getDouble("principal")
            ));
        }
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginLoanAsset> getMarginLoanAssetsList() {
        return marginLoanAssetsList;
    }

    public void setMarginLoanAssetsList(ArrayList<MarginLoanAsset> marginLoanAssetsList) {
        this.marginLoanAssetsList = marginLoanAssetsList;
        total = marginLoanAssetsList.size();
    }

    public void insertMarginIsolatedTransfer(MarginLoanAsset marginLoanAsset){
        if(!marginLoanAssetsList.contains(marginLoanAsset)){
            marginLoanAssetsList.add(marginLoanAsset);
            total += 1;
        }
    }

    public boolean removeMarginIsolatedTransfer(MarginLoanAsset marginLoanAsset){
        boolean removed = marginLoanAssetsList.remove(marginLoanAsset);
        if(removed)
            total -= 1;
        return removed;
    }

    public MarginLoanAsset getMarginLoanAsset(int index) {
        return marginLoanAssetsList.get(index);
    }

    @Override
    public String toString() {
        return "MarginLoan{" +
                "total=" + total +
                ", marginLoanAssetsList=" + marginLoanAssetsList +
                '}';
    }

    /**
     * The {@code MarginLoanAsset} class is useful to obtain and format MarginLoanAsset object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * **/

    public static class MarginLoanAsset extends MarginAssetList {

        /**
         * {@code isolatedSymbol} is instance that memorizes isolated symbol of the asset
         * **/
        private final String isolatedSymbol;

        /**
         * {@code principal} is instance that memorizes principal value
         * **/
        private double principal;

        /** Constructor to init {@link MarginLoanAsset} object
         * @param asset: asset
         * @param txId: identifier of transaction
         * @param timestamp: timestamp of transaction
         * @param status: status of transaction
         * @param isolatedSymbol: symbol of the asset
         * @param principal: principal value
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public MarginLoanAsset(String asset, long txId, long timestamp, String status, String isolatedSymbol,
                               double principal) {
            super(asset, txId, timestamp, status);
            this.isolatedSymbol = isolatedSymbol;
            if(principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            else
                this.principal = principal;
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public double getPrincipal() {
            return principal;
        }

        /** Method to set {@link #principal}
         * @param principal: principal value
         * @throws IllegalArgumentException when principal value is less than 0
         * **/
        public void setPrincipal(double principal) {
            if(principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            this.principal = principal;
        }

        @Override
        public String toString() {
            return "MarginLoanAsset{" +
                    "isolatedSymbol='" + isolatedSymbol + '\'' +
                    ", principal=" + principal +
                    ", asset='" + asset + '\'' +
                    ", txId=" + txId +
                    ", timestamp=" + timestamp +
                    ", status='" + status + '\'' +
                    '}';
        }

    }

}
