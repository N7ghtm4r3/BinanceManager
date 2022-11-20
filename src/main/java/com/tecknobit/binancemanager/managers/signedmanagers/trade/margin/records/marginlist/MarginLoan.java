package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginLoan} class is useful to format {@code "Binance"} Margin Query Loan repay request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
 **/

public class MarginLoan {

    /**
     * {@code total} is instance that memorizes total size about {@link #marginLoanAssetsList}
     * **/
    private int total;

    /**
     * {@code marginLoanAssetsList} is instance that memorizes list of {@link MarginLoanAsset}
     **/
    private ArrayList<MarginLoanAsset> marginLoanAssetsList;

    /**
     * Constructor to init {@link MarginLoan} object
     *
     * @param total:                total size of loans
     * @param marginLoanAssetsList: list of {@link MarginLoanAsset}
     **/
    public MarginLoan(int total, ArrayList<MarginLoanAsset> marginLoanAssetsList) {
        this.total = total;
        this.marginLoanAssetsList = marginLoanAssetsList;
    }

    /**
     * Constructor to init {@link MarginLoan} object
     *
     * @param jsonLoan: loan details as {@link JSONObject}
     * @throws IllegalArgumentException when loan details are not recoverable
     **/
    public MarginLoan(JSONObject jsonLoan) {
        JSONArray jsonAssets = getJSONArray(jsonLoan, "rows");
        if (jsonAssets != null) {
            total = jsonAssets.length();
            loadMarginLoanAssets(jsonAssets);
        } else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /**
     * Method to load LoanAssets list
     *
     * @param jsonAssets: obtained from {@code "Binance"}'s request
     *                    any return
     **/
    private void loadMarginLoanAssets(JSONArray jsonAssets) {
        marginLoanAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            marginLoanAssetsList.add(new MarginLoanAsset(jsonAssets.getJSONObject(j)));
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
     * The {@code MarginLoanAsset} class is useful to create a margin loan asset type
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
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            else
                this.principal = principal;
        }

        /**
         * Constructor to init {@link MarginLoanAsset} object
         *
         * @param loanAsset: loan asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginLoanAsset(JSONObject loanAsset) {
            super(loanAsset);
            isolatedSymbol = loanAsset.getString("isolatedSymbol");
            principal = loanAsset.getDouble("principal");
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public double getPrincipal() {
            return principal;
        }

        /**
         * Method to get {@link #principal} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #principal} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPrincipal(int decimals) {
            return roundValue(principal, decimals);
        }

        /**
         * Method to set {@link #principal}
         *
         * @param principal: principal value
         * @throws IllegalArgumentException when principal value is less than 0
         **/
        public void setPrincipal(double principal) {
            if (principal < 0)
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
