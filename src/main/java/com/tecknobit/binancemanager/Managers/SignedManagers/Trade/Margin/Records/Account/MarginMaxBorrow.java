package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

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
