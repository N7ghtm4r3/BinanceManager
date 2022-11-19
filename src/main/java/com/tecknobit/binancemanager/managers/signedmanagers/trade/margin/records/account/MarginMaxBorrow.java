package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginMaxBorrow} class is useful to format Binance Margin Max Borrow request response
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
 **/

public class MarginMaxBorrow {

    /**
     * {@code amount} is instance that memorizes amount of borrow
     * **/
    private double amount;

    /**
     * {@code borrowLimit} is instance that memorizes limit of borrow
     * **/
    private double borrowLimit;

    /** Constructor to init {@link MarginMaxBorrow} object
     * @param amount: amount of borrow
     * @param borrowLimit: limit of borrow
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginMaxBorrow(double amount, double borrowLimit) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        else
            this.amount = amount;
        if (borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        else
            this.borrowLimit = borrowLimit;
    }

    /**
     * Constructor to init {@link MarginMaxBorrow} object
     *
     * @param maxBorrow: margin max borrow details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginMaxBorrow(JSONObject maxBorrow) {
        amount = maxBorrow.getDouble("amount");
        if (amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        borrowLimit = maxBorrow.getDouble("borrowLimit");
        if (borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
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
     * @param amount: amount of borrow
     * @throws IllegalArgumentException when amount value is less than 0
     **/
    public void setAmount(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        this.amount = amount;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

    /**
     * Method to get {@link #borrowLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #borrowLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBorrowLimit(int decimals) {
        return roundValue(borrowLimit, decimals);
    }

    /**
     * Method to set {@link #borrowLimit}
     *
     * @param borrowLimit: limit of borrow
     * @throws IllegalArgumentException when limit of borrow value is less than 0
     **/
    public void setBorrowLimit(double borrowLimit) {
        if (borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        this.borrowLimit = borrowLimit;
    }

    @Override
    public String toString() {
        return "MarginMaxBorrow{" +
                "amount=" + amount +
                ", borrowLimit=" + borrowLimit +
                '}';
    }

}
