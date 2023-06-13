package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.isolated.properties;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code IsolatedMarginFee} class is useful to format a {@code "Binance"}'s isolated margin fee
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
 * Query Isolated Margin Fee Data (USER_DATA)</a>
 */
public class IsolatedMarginFee {

    /**
     * {@code vipLevel} is instance that memorizes vip level
     */
    private final int vipLevel;

    /**
     * {@code symbol} is instance that memorizes symbol of asset
     */
    private final String symbol;

    /**
     * {@code leverage} is instance that memorizes leverage value
     */
    private final int leverage;

    /**
     * {@code isolatedDataList} is instance that memorizes isolated data list
     */
    private final ArrayList<IsolatedData> isolatedDataList;

    /**
     * Constructor to init {@link IsolatedMarginFee} object
     *
     * @param vipLevel:         vip level
     * @param symbol:           symbol of asset
     * @param leverage:         leverage value
     * @param isolatedDataList: list of fees as {@link ArrayList} of {@link IsolatedData}
     * @throws IllegalArgumentException if parameters range is not respected
     */
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
     */
    public IsolatedMarginFee(JSONObject isolatedMarginFee) {
        vipLevel = isolatedMarginFee.getInt("vipLevel");
        if (vipLevel < 0)
            throw new IllegalArgumentException("Vip level value cannot be less than 0");
        symbol = isolatedMarginFee.getString("symbol");
        leverage = isolatedMarginFee.getInt("leverage");
        if (leverage < 0)
            throw new IllegalArgumentException("Leverage value cannot be less than 0");
        JSONArray jIsolatedData = isolatedMarginFee.getJSONArray("data");
        isolatedDataList = new ArrayList<>();
        for (int j = 0; j < jIsolatedData.length(); j++)
            isolatedDataList.add(new IsolatedData(jIsolatedData.getJSONObject(j)));
    }

    /**
     * Method to get {@link #vipLevel} instance <br>
     * No-any params required
     *
     * @return {@link #vipLevel} instance as int
     */
    public int getVipLevel() {
        return vipLevel;
    }

    /**
     * Method to get {@link #symbol} instance <br>
     * No-any params required
     *
     * @return {@link #symbol} instance as {@link String}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Method to get {@link #leverage} instance <br>
     * No-any params required
     *
     * @return {@link #leverage} instance as int
     */
    public int getLeverage() {
        return leverage;
    }

    /**
     * Method to get {@link #isolatedDataList} instance <br>
     * No-any params required
     *
     * @return {@link #isolatedDataList} instance as {@link ArrayList} of {@link IsolatedData}
     */
    public ArrayList<IsolatedData> getIsolatedDataList() {
        return isolatedDataList;
    }

    /**
     * Method to add an {@link IsolatedData} to {@link #isolatedDataList}
     *
     * @param isolatedData: isolated data to add
     */
    public void insertIsolatedData(IsolatedData isolatedData) {
        if (!isolatedDataList.contains(isolatedData))
            isolatedDataList.add(isolatedData);
    }

    /**
     * Method to remove an {@link IsolatedData} from {@link #isolatedDataList}
     *
     * @param isolatedData: isolated data to remove
     * @return result of operation as boolean
     */
    public boolean removeIsolatedData(IsolatedData isolatedData) {
        return isolatedDataList.remove(isolatedData);
    }

    /**
     * Method to get an isolated data from {@link #isolatedDataList} list
     *
     * @param index: index to fetch the isolated data
     * @return isolated data as {@link Order}
     */
    public IsolatedData getIsolatedData(int index) {
        return isolatedDataList.get(index);
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

    /**
     * The {@code IsolatedData} class is useful to create an isolated data object
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public static class IsolatedData {

        /**
         * {@code coin} is instance that memorizes coin
         */
        protected final String coin;

        /**
         * {@code dailyInterest} is instance that memorizes value of daily interest
         */
        protected final double dailyInterest;

        /**
         * {@code borrowLimit} is instance that memorizes value of limit for borrow
         */
        protected final double borrowLimit;

        /** Constructor to init {@link IsolatedData} object
         * @param coin: coin
         * @param dailyInterest: value of daily interest
         * @param borrowLimit: limit for borrow
         * @throws IllegalArgumentException if parameters range is not respected
         */
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
         */
        public IsolatedData(JSONObject isolatedData) {
            coin = isolatedData.getString("coin");
            dailyInterest = isolatedData.getDouble("dailyInterest");
            if (dailyInterest < 0)
                throw new IllegalArgumentException("Daily interest value cannot be less than 0");
            borrowLimit = isolatedData.getDouble("borrowLimit");
            if (borrowLimit < 0)
                throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        }

        /**
         * Method to get {@link #coin} instance <br>
         * No-any params required
         *
         * @return {@link #coin} instance as {@link String}
         */
        public String getCoin() {
            return coin;
        }

        /**
         * Method to get {@link #dailyInterest} instance <br>
         * No-any params required
         *
         * @return {@link #dailyInterest} instance as double
         */
        public double getDailyInterest() {
            return dailyInterest;
        }

        /**
         * Method to get {@link #dailyInterest} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #dailyInterest} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getDailyInterest(int decimals) {
            return roundValue(dailyInterest, decimals);
        }

        /**
         * Method to get {@link #borrowLimit} instance <br>
         * No-any params required
         *
         * @return {@link #borrowLimit} instance as double
         */
        public double getBorrowLimit() {
            return borrowLimit;
        }

        /**
         * Method to get {@link #borrowLimit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #borrowLimit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBorrowLimit(int decimals) {
            return roundValue(borrowLimit, decimals);
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
