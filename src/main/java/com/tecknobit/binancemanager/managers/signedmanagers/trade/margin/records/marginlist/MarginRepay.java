package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginRepay} class is useful to format {@code "Binance"} Margin Query Repay record request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
 **/

public class MarginRepay {

    /**
     * {@code total} is instance that memorizes total size about {@link #marginRepayAssetsList}
     * **/
    private int total;

    /**
     * {@code marginRepayAssetsList} is instance that memorizes list of {@link MarginRepayAsset}
     **/
    private ArrayList<MarginRepayAsset> marginRepayAssetsList;

    /**
     * Constructor to init {@link MarginRepay} object
     *
     * @param total:                 total size of repays
     * @param marginRepayAssetsList: list of {@link MarginRepayAsset}
     **/
    public MarginRepay(int total, ArrayList<MarginRepayAsset> marginRepayAssetsList) {
        this.total = total;
        this.marginRepayAssetsList = marginRepayAssetsList;
    }

    /**
     * Constructor to init {@link MarginRepay} object
     *
     * @param jsonRepay: repay details as {@link JSONObject}
     * @throws IllegalArgumentException when repay details are not recoverable
     **/
    public MarginRepay(JSONObject jsonRepay) {
        JSONArray jsonAssets = getJSONArray(jsonRepay, "rows");
        if (jsonAssets != null) {
            total = jsonAssets.length();
            loadMarginRepayAssets(jsonAssets);
        } else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /**
     * Method to load RepayAssets list
     *
     * @param jsonAssets: obtained from {@code "Binance"}'s request
     **/
    private void loadMarginRepayAssets(JSONArray jsonAssets) {
        marginRepayAssetsList = new ArrayList<>();
        for (int j = 0; j < jsonAssets.length(); j++)
            marginRepayAssetsList.add(new MarginRepayAsset(jsonAssets.getJSONObject(j)));
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
     * The {@code MarginRepayAsset} class is useful to create a margin repay asset type
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

        /** Constructor to init {@link MarginRepayAsset} object
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
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            else
                this.amount = amount;
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
        }

        /**
         * Constructor to init {@link MarginRepayAsset} object
         *
         * @param repayAsset: margin repay asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginRepayAsset(JSONObject repayAsset) {
            super(repayAsset);
            amount = repayAsset.getDouble("amount");
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            interest = repayAsset.getDouble("interest");
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
        }

        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to set {@link #amount}
         *
         * @param amount: amount of asset
         * @throws IllegalArgumentException when amount value is less than 0
         **/
        public void setAmount(double amount) {
            if (amount < 0)
                throw new IllegalArgumentException("Amount value cannot be less than 0");
            this.amount = amount;
        }

        public double getInterest() {
            return interest;
        }

        /**
         * Method to get {@link #interest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getInterest(int decimals) {
            return roundValue(interest, decimals);
        }

        /**
         * Method to set {@link #interest}
         *
         * @param interest: interest on the asset
         * @throws IllegalArgumentException when interest value is less than 0
         **/
        public void setInterest(double interest) {
            if (interest < 0)
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
