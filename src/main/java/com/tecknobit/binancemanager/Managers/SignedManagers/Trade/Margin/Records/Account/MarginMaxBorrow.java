package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

/**
 * The {@code MarginMaxBorrow} class is useful to format Binance Margin Max Borrow request response
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

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
        if(amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        else
            this.amount = amount;
        if(borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        else
            this.borrowLimit = borrowLimit;
    }

    public double getAmount() {
        return amount;
    }

    /** Method to set {@link #amount}
     * @param amount: amount of borrow
     * @throws IllegalArgumentException when amount value is less than 0
     * **/
    public void setAmount(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        this.amount = amount;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

    /** Method to set {@link #borrowLimit}
     * @param borrowLimit: limit of borrow
     * @throws IllegalArgumentException when limit of borrow value is less than 0
     * **/
    public void setBorrowLimit(double borrowLimit) {
        if(borrowLimit < 0)
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
