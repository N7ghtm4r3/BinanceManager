package com.tecknobit.binancemanager.managers.signedmanagers.savings.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LendingAccount} class is useful to format a lending account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data">
 * Lending Account (USER_DATA) </a>
 * @see BinanceItem
 **/
public class LendingAccount extends BinanceItem {

    /**
     * {@code positionAmountVos} position amount vos of the lending account
     **/
    private final ArrayList<PositionAmount> positionAmountVos;

    /**
     * {@code totalAmountInBTC} total amount in BTC of the lending account
     **/
    private final double totalAmountInBTC;

    /**
     * {@code totalAmountInUSDT} total amount in USDT of the lending account
     **/
    private final double totalAmountInUSDT;

    /**
     * {@code totalFixedAmountInBTC} total fixed amount in BTC of the lending account
     **/
    private final double totalFixedAmountInBTC;

    /**
     * {@code totalFixedAmountInUSDT} total fixed amount in USDT of the lending account
     **/
    private final double totalFixedAmountInUSDT;

    /**
     * {@code totalFlexibleInBTC} total flexible in BTC of the lending account
     **/
    private final double totalFlexibleInBTC;

    /**
     * {@code totalFlexibleInUSDT} total flexible in USDT of the lending account
     **/
    private final double totalFlexibleInUSDT;

    /**
     * Constructor to init {@link LendingAccount} object
     *
     * @param positionAmountVos:      position amount vos of the lending account
     * @param totalAmountInBTC:       total amount in BTC of the lending account
     * @param totalAmountInUSDT:      total amount in USDT of the lending account
     * @param totalFixedAmountInBTC:  total fixed amount in BTC of the lending account
     * @param totalFixedAmountInUSDT: total fixed amount in USDT of the lending account
     * @param totalFlexibleInBTC:     total flexible in BTC of the lending account
     * @param totalFlexibleInUSDT:    total flexible in USDT of the lending account
     **/
    public LendingAccount(ArrayList<PositionAmount> positionAmountVos, double totalAmountInBTC, double totalAmountInUSDT,
                          double totalFixedAmountInBTC, double totalFixedAmountInUSDT, double totalFlexibleInBTC,
                          double totalFlexibleInUSDT) {
        super(null);
        this.positionAmountVos = positionAmountVos;
        this.totalAmountInBTC = totalAmountInBTC;
        this.totalAmountInUSDT = totalAmountInUSDT;
        this.totalFixedAmountInBTC = totalFixedAmountInBTC;
        this.totalFixedAmountInUSDT = totalFixedAmountInUSDT;
        this.totalFlexibleInBTC = totalFlexibleInBTC;
        this.totalFlexibleInUSDT = totalFlexibleInUSDT;
    }

    /**
     * Constructor to init {@link LendingAccount} object
     *
     * @param jLendingAccount: lending account details as {@link JSONObject}
     **/
    public LendingAccount(JSONObject jLendingAccount) {
        super(jLendingAccount);
        positionAmountVos = new ArrayList<>();
        for (Object position : hItem.fetchList("positionAmountVos"))
            positionAmountVos.add(new PositionAmount((JSONObject) position));
        totalAmountInBTC = hItem.getDouble("totalAmountInBTC", 0);
        totalAmountInUSDT = hItem.getDouble("totalAmountInUSDT", 0);
        totalFixedAmountInBTC = hItem.getDouble("totalFixedAmountInBTC", 0);
        totalFixedAmountInUSDT = hItem.getDouble("totalFixedAmountInUSDT", 0);
        totalFlexibleInBTC = hItem.getDouble("totalFlexibleInBTC", 0);
        totalFlexibleInUSDT = hItem.getDouble("totalFlexibleInUSDT", 0);
    }

    /**
     * Method to get {@link #positionAmountVos} instance <br>
     * No-any params required
     *
     * @return {@link #positionAmountVos} instance as {@link ArrayList} of {@link PositionAmount}
     **/
    public ArrayList<PositionAmount> getPositionAmountVos() {
        return positionAmountVos;
    }

    /**
     * Method to get {@link #totalAmountInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmountInBTC} instance as double
     **/
    public double getTotalAmountInBTC() {
        return totalAmountInBTC;
    }

    /**
     * Method to get {@link #totalAmountInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmountInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalAmountInBTC(int decimals) {
        return roundValue(totalAmountInBTC, decimals);
    }

    /**
     * Method to get {@link #totalAmountInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmountInUSDT} instance as double
     **/
    public double getTotalAmountInUSDT() {
        return totalAmountInUSDT;
    }

    /**
     * Method to get {@link #totalAmountInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmountInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalAmountInUSDT(int decimals) {
        return roundValue(totalAmountInUSDT, decimals);
    }

    /**
     * Method to get {@link #totalFixedAmountInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalFixedAmountInBTC} instance as double
     **/
    public double getTotalFixedAmountInBTC() {
        return totalFixedAmountInBTC;
    }

    /**
     * Method to get {@link #totalFixedAmountInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFixedAmountInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalFixedAmountInBTC(int decimals) {
        return roundValue(totalFixedAmountInBTC, decimals);
    }

    /**
     * Method to get {@link #totalFixedAmountInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalFixedAmountInUSDT} instance as double
     **/
    public double getTotalFixedAmountInUSDT() {
        return totalFixedAmountInUSDT;
    }

    /**
     * Method to get {@link #totalFixedAmountInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFixedAmountInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalFixedAmountInUSDT(int decimals) {
        return roundValue(totalFixedAmountInUSDT, decimals);
    }

    /**
     * Method to get {@link #totalFlexibleInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalFlexibleInBTC} instance as double
     **/
    public double getTotalFlexibleInBTC() {
        return totalFlexibleInBTC;
    }

    /**
     * Method to get {@link #totalFlexibleInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFlexibleInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalFlexibleInBTC(int decimals) {
        return roundValue(totalFlexibleInBTC, decimals);
    }

    /**
     * Method to get {@link #totalFlexibleInUSDT} instance <br>
     * No-any params required
     *
     * @return {@link #totalFlexibleInUSDT} instance as double
     **/
    public double getTotalFlexibleInUSDT() {
        return totalFlexibleInUSDT;
    }

    /**
     * Method to get {@link #totalFlexibleInUSDT} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFlexibleInUSDT} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalFlexibleInUSDT(int decimals) {
        return roundValue(totalFlexibleInUSDT, decimals);
    }

    /**
     * The {@code PositionAmount} class is useful to format a position amount
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class PositionAmount extends BinanceItem {

        /**
         * {@code amount} of the position
         **/
        private final double amount;

        /**
         * {@code amountInBTC} amount in BTC of the position amount
         **/
        private final double amountInBTC;

        /**
         * {@code amountInUSDT} amount in USDT of the position amount
         **/
        private final double amountInUSDT;

        /**
         * {@code asset} of the position amount
         **/
        private final String asset;

        /**
         * Constructor to init {@link PositionAmount} object
         *
         * @param amount:       amount of the position
         * @param amountInBTC:  amount in BTC of the position amount
         * @param amountInUSDT: amount in USDT of the position amount
         * @param asset:        asset of the position amount
         **/
        public PositionAmount(double amount, double amountInBTC, double amountInUSDT, String asset) {
            super(null);
            this.amount = amount;
            this.amountInBTC = amountInBTC;
            this.amountInUSDT = amountInUSDT;
            this.asset = asset;
        }

        /**
         * Constructor to init {@link PositionAmount} object
         *
         * @param jPositionAmount: position amount details as {@link JSONObject}
         **/
        public PositionAmount(JSONObject jPositionAmount) {
            super(jPositionAmount);
            amount = hItem.getDouble("amount", 0);
            amountInBTC = hItem.getDouble("amountInBTC", 0);
            amountInUSDT = hItem.getDouble("amountInUSDT", 0);
            asset = hItem.getString("asset");
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         **/
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
         * Method to get {@link #amountInBTC} instance <br>
         * No-any params required
         *
         * @return {@link #amountInBTC} instance as double
         **/
        public double getAmountInBTC() {
            return amountInBTC;
        }

        /**
         * Method to get {@link #amountInBTC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amountInBTC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmountInBTC(int decimals) {
            return roundValue(amountInBTC, decimals);
        }

        /**
         * Method to get {@link #amountInUSDT} instance <br>
         * No-any params required
         *
         * @return {@link #amountInUSDT} instance as double
         **/
        public double getAmountInUSDT() {
            return amountInUSDT;
        }

        /**
         * Method to get {@link #amountInUSDT} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amountInUSDT} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAmountInUSDT(int decimals) {
            return roundValue(amountInUSDT, decimals);
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

    }

}
