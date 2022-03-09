package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

/**
 * The {@code MarginMaxBorrow} class is useful to format Binance Margin Max Borrow request response
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data
 * @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginMaxBorrow {

    private final double amount;
    private final double borrowLimit;

    public MarginMaxBorrow(double amount, double borrowLimit) {
        this.amount = amount;
        this.borrowLimit = borrowLimit;
    }

    public double getAmount() {
        return amount;
    }

    public double getBorrowLimit() {
        return borrowLimit;
    }

}
