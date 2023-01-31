package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists.InterestHistoryList.Interest;

/**
 * The {@code InterestHistoryList} class is useful to format a {@code "Binance"}'s margin interest history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
 * Get Interest History (USER_DATA)</a>
 * @see BinanceRowsList
 **/
public class InterestHistoryList extends BinanceRowsList<Interest> {

    /**
     * Constructor to init {@link InterestHistoryList} object
     *
     * @param total:              total size of interest assets
     * @param interestAssetsList: list of {@link Interest}
     **/
    public InterestHistoryList(int total, ArrayList<Interest> interestAssetsList) {
        super(total, interestAssetsList);
    }

    /**
     * Constructor to init {@link InterestHistoryList} object
     *
     * @param jsonHistory: history details as {@link JSONObject}
     **/
    public InterestHistoryList(JSONObject jsonHistory) {
        super(jsonHistory);
        JSONArray jHistoryList = hItem.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jHistoryList.length(); j++)
            rows.add(new Interest(jHistoryList.getJSONObject(j)));
    }

    /**
     * {@code OptionType} list of available option types
     **/
    public enum OptionType {

        /**
         * {@code "ON_BORROW"} option type
         **/
        ON_BORROW,

        /**
         * {@code "PERIODIC"} option type
         **/
        PERIODIC,

        /**
         * {@code "PERIODIC_CONVERTED"} option type
         **/
        PERIODIC_CONVERTED,

        /**
         * {@code "ON_BORROW_CONVERTED"} option type
         **/
        ON_BORROW_CONVERTED

    }

    /**
     * The {@code Interest} class is useful to create an interest
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class Interest {

        /**
         * {@code isolatedSymbol} is instance that memorizes isolated symbol of the asset
         **/
        private final String isolatedSymbol;

        /**
         * {@code asset} is instance that memorizes asset
         **/
        private final String asset;
        /**
         * {@code type} is instance that memorizes type value
         **/
        private final OptionType type;
        /**
         * {@code interest} is instance that memorizes interest on the asset
         **/
        private double interest;
        /**
         * {@code interestAccuredTime} is instance that memorizes accurate time value
         **/
        private long interestAccuredTime;
        /**
         * {@code interestRate} is instance that memorizes interest rate value
         **/
        private double interestRate;
        /**
         * {@code principal} is instance that memorizes principal value
         **/
        private double principal;

        /**
         * Constructor to init {@link Interest} object
         *
         * @param isolatedSymbol:      isolated symbol of the asset
         * @param asset:               asset
         * @param interest:            interest on the asset
         * @param interestAccuredTime: accurate time value
         * @param interestRate:        interest rate value
         * @param principal:           principal value
         * @param type:                type value
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Interest(String isolatedSymbol, String asset, double interest, long interestAccuredTime,
                        double interestRate, double principal, OptionType type) {
            this.isolatedSymbol = isolatedSymbol;
            this.asset = asset;
            if (interest < 0)
                throw new IllegalArgumentException("Interest value cannot be less than 0");
            else
                this.interest = interest;
            if (interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            else
                this.interestAccuredTime = interestAccuredTime;
            if (interestRate < 0)
                throw new IllegalArgumentException("Interest rate value cannot be less than 0");
            else
                this.interestRate = interestRate;
            if (principal < 0)
                throw new IllegalArgumentException("Principal value cannot be less than 0");
            else
                this.principal = principal;
            this.type = type;
        }

        /**
         * Constructor to init {@link Interest} object
         *
         * @param interestAsset: interest asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Interest(JSONObject interestAsset) {
            this(interestAsset.getString("isolatedSymbol"), interestAsset.getString("asset"),
                    interestAsset.getDouble("interest"), interestAsset.getLong("interestAccuredTime"),
                    interestAsset.getDouble("interestRate"), interestAsset.getDouble("principal"),
                    OptionType.valueOf(interestAsset.getString("type")));
        }

        /**
         * Method to get {@link #isolatedSymbol} instance <br>
         * No-any params required
         *
         * @return {@link #isolatedSymbol} instance as {@link String}
         **/
        public String getIsolatedSymbol() {
            return isolatedSymbol;
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         **/
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #interest} instance <br>
         * No-any params required
         *
         * @return {@link #interest} instance as double
         **/
        public double getInterest() {
            return interest;
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
         * Method to get {@link #total} instance <br>
         * No-any params required
         *
         * @return {@link #total} instance as int
         **/
        public long getInterestAccuredTime() {
            return interestAccuredTime;
        }

        /**
         * Method to set {@link #interestAccuredTime}
         *
         * @param interestAccuredTime: accurate time value
         * @throws IllegalArgumentException when accurate time value is less than 0
         **/
        public void setInterestAccuredTime(long interestAccuredTime) {
            if (interestAccuredTime < 0)
                throw new IllegalArgumentException("Interest accured time value cannot be less than 0");
            this.interestAccuredTime = interestAccuredTime;
        }

        /**
         * Method to get {@link #interestAccuredTime} instance <br>
         * No-any params required
         *
         * @return {@link #interestAccuredTime} instance as {@link Date}
         **/
        public Date getInterestAccuredDate() {
            return TimeFormatter.getDate(interestAccuredTime);
        }

        /**
         * Method to get {@link #interestRate} instance <br>
         * No-any params required
         *
         * @return {@link #interestRate} instance as double
         **/
        public double getInterestRate() {
            return interestRate;
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
         * Method to get {@link #principal} instance <br>
         * No-any params required
         *
         * @return {@link #principal} instance as double
         **/
        public double getPrincipal() {
            return principal;
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
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link OptionType}
         **/
        public OptionType getType() {
            return type;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
