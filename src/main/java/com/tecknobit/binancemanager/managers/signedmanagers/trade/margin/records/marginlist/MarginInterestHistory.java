package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.formatters.JsonHelper.getJSONArray;
import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginInterestHistory} class is useful to format {@code "Binance"} Margin Interest History request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
 **/

public class MarginInterestHistory {

    /**
     * {@code ON_BORROW} is constant for on borrow option
     * **/
    public static final String ON_BORROW = "ON_BORROW";

    /**
     * {@code PERIODIC} is constant for periodic option
     * **/
    public static final String PERIODIC = "PERIODIC";

    /**
     * {@code PERIODIC_CONVERTED} is constant for periodic converted option
     * **/
    public static final String PERIODIC_CONVERTED = "PERIODIC_CONVERTED";

    /**
     * {@code ON_BORROW_CONVERTED} is constant for on borrow converted option
     * **/
    public static final String ON_BORROW_CONVERTED = "ON_BORROW_CONVERTED";

    /**
     * {@code total} is instance that memorizes total size about {@link #marginInterestAssetsList}
     * **/
    private int total;

    /**
     * {@code marginInterestAssetsList} is instance that memorizes list of {@link MarginInterestAsset}
     **/
    private ArrayList<MarginInterestAsset> marginInterestAssetsList;

    /**
     * Constructor to init {@link MarginInterestHistory} object
     *
     * @param total:                    total size of interest assets
     * @param marginInterestAssetsList: list of {@link MarginInterestAsset}
     **/
    public MarginInterestHistory(int total, ArrayList<MarginInterestAsset> marginInterestAssetsList) {
        this.total = total;
        this.marginInterestAssetsList = marginInterestAssetsList;
    }

    /**
     * Constructor to init {@link MarginInterestHistory} object
     *
     * @param jsonHistory: history details as {@link JSONObject}
     * @throws IllegalArgumentException when history details are not recoverable
     **/
    public MarginInterestHistory(JSONObject jsonHistory) {
        JSONArray jsonHistoryList = getJSONArray(jsonHistory, "rows");
        if (jsonHistoryList != null) {
            total = jsonHistoryList.length();
            loadMarginInterestAssets(jsonHistoryList);
        } else
            throw new IllegalArgumentException("Details are not recoverable");
    }

    /**
     * Method to load InterestAssets list
     *
     * @param interestAssets: obtained from {@code "Binance"}'s request
     *                        any return
     **/
    private void loadMarginInterestAssets(JSONArray interestAssets) {
        marginInterestAssetsList = new ArrayList<>();
        for (int j = 0; j < interestAssets.length(); j++)
            marginInterestAssetsList.add(new MarginInterestAsset(interestAssets.getJSONObject(j)));
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<MarginInterestAsset> getMarginInterestAssetsList() {
        return marginInterestAssetsList;
    }

    public void setMarginInterestAssetsList(ArrayList<MarginInterestAsset> marginInterestAssetsList) {
        this.marginInterestAssetsList = marginInterestAssetsList;
        total = marginInterestAssetsList.size();
    }

    public void insertMarginInterestAsset(MarginInterestAsset marginInterestAsset){
        if(!marginInterestAssetsList.contains(marginInterestAsset)) {
            marginInterestAssetsList.add(marginInterestAsset);
            total += 1;
        }
    }

    public boolean removeMarginInterestAsset(MarginInterestAsset marginInterestAsset){
        boolean removed = marginInterestAssetsList.remove(marginInterestAsset);
        if(removed)
            total -= 1;
        return removed;
    }

    public MarginInterestAsset getMarginInterestAsset(int index) {
        return marginInterestAssetsList.get(index);
    }

    @Override
    public String toString() {
        return "MarginInterestHistory{" +
                "total=" + total +
                ", marginInterestAssetsList=" + marginInterestAssetsList +
                '}';
    }

    /**
     * The {@code MarginInterestAsset} class is useful to create a margin interest asset type
     * **/

    public static class MarginInterestAsset {

        /**
         * {@code isolatedSymbol} is instance that memorizes isolated symbol of the asset
         * **/
        private final String isolatedSymbol;

        /**
         * {@code asset} is instance that memorizes asset
         * **/
        private final String asset;

        /**
         * {@code interest} is instance that memorizes interest on the asset
         * **/
        private double interest;

        /**
         * {@code interestAccuredTime} is instance that memorizes accurate time value
         * **/
        private long interestAccuredTime;

        /**
         * {@code interestRate} is instance that memorizes interest rate value
         * **/
        private double interestRate;

        /**
         * {@code principal} is instance that memorizes principal value
         * **/
        private double principal;

        /**
         * {@code type} is instance that memorizes type value
         * **/
        private String type;

        /** Constructor to init {@link MarginInterestAsset} object
         * @param isolatedSymbol: isolated symbol of the asset
         * @param asset: asset
         * @param interest: interest on the asset
         * @param interestAccuredTime: accurate time value
         * @param interestRate: interest rate value
         * @param principal: principal value
         * @param type: type value
         * @throws IllegalArgumentException if parameters range is not respected
         * **/
        public MarginInterestAsset(String isolatedSymbol, String asset, double interest, long interestAccuredTime,
                                   double interestRate, double principal, String type) {
            this.isolatedSymbol = isolatedSymbol;
            this.asset = asset;
            if(interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
            if(interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            else
                this.interestAccuredTime = interestAccuredTime;
            if(interestRate < 0)
                throw new IllegalArgumentException("Interest rate value cannot be less than 0");
            else
                this.interestRate = interestRate;
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            else
                this.principal = principal;
            if (typeIsValid(type))
                this.type = type;
            else {
                throw new IllegalArgumentException("Type value can only be ON_BORROW,ON_BORROW_CONVERTED,PERIODIC " +
                        "or PERIODIC_CONVERTED");
            }
        }

        /**
         * Constructor to init {@link MarginInterestAsset} object
         *
         * @param interestAsset: interest asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public MarginInterestAsset(JSONObject interestAsset) {
            isolatedSymbol = interestAsset.getString("isolatedSymbol");
            asset = interestAsset.getString("asset");
            interest = interestAsset.getDouble("interest");
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            interestAccuredTime = interestAsset.getLong("interestAccusedTime");
            if (interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            interestRate = interestAsset.getDouble("interestRate");
            if (interestRate < 0)
                throw new IllegalArgumentException("Interest rate value cannot be less than 0");
            principal = interestAsset.getDouble("principal");
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            type = interestAsset.getString("type");
            if (!typeIsValid(type)) {
                throw new IllegalArgumentException("Type value can only be ON_BORROW,ON_BORROW_CONVERTED,PERIODIC " +
                        "or PERIODIC_CONVERTED");
            }
        }

        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        public String getAsset() {
            return asset;
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

        public long getInterestAccuredTime() {
            return interestAccuredTime;
        }

        /** Method to set {@link #interestAccuredTime}
         * @param interestAccuredTime: accurate time value
         * @throws IllegalArgumentException when accurate time value is less than 0
         * **/
        public void setInterestAccuredTime(long interestAccuredTime) {
            if (interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            this.interestAccuredTime = interestAccuredTime;
        }

        public double getInterestRate() {
            return interestRate;
        }

        /**
         * Method to get {@link #interestRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #interestRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getInterestRate(int decimals) {
            return roundValue(interestRate, decimals);
        }

        /**
         * Method to set {@link #interestRate}
         *
         * @param interestRate: interest rate value
         * @throws IllegalArgumentException when interest rate value is less than 0
         **/
        public void setInterestRate(double interestRate) {
            if (interestRate < 0)
                throw new IllegalArgumentException("Interest rate value cannot be less than 0");
            this.interestRate = interestRate;
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

        public String getType() {
            return type;
        }

        /** Method to set {@link #type}
         * @param type:type value
         * @throws IllegalArgumentException when type value is not valid
         * **/
        public void setType(String type) {
            if(typeIsValid(type))
                this.type = type;
            else {
                throw new IllegalArgumentException("Type value can only be ON_BORROW,ON_BORROW_CONVERTED,PERIODIC " +
                        "or PERIODIC_CONVERTED");
            }
        }

        /** Method to check {@link #type} validity
         * @param type: type value
         * @return validity of type as boolean
         * **/
        private boolean typeIsValid(String type){
            return type.equals(ON_BORROW) || type.equals(ON_BORROW_CONVERTED) || type.equals(PERIODIC)
                    || type.equals(PERIODIC_CONVERTED);
        }

        @Override
        public String toString() {
            return "MarginInterestAsset{" +
                    "isolatedSymbol='" + isolatedSymbol + '\'' +
                    ", asset='" + asset + '\'' +
                    ", interest=" + interest +
                    ", interestAccuredTime=" + interestAccuredTime +
                    ", interestRate=" + interestRate +
                    ", principal=" + principal +
                    ", type='" + type + '\'' +
                    '}';
        }

    }

}
