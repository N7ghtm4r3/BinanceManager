package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

/**
 * The {@code MarginMaxBorrow} class is useful to format Binance Margin Max Borrow request response
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginMaxBorrow {

    private double amount;
    private double borrowLimit;

    public MarginMaxBorrow(double amount, double borrowLimit) {
        this.amount = amount;
        this.borrowLimit = borrowLimit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount < 0)
            throw new IllegalArgumentException("Amount value cannot be less than 0");
        this.amount = amount;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(double borrowLimit) {
        if(borrowLimit < 0)
            throw new IllegalArgumentException("Borrow limit value cannot be less than 0");
        this.borrowLimit = borrowLimit;
    }

}
