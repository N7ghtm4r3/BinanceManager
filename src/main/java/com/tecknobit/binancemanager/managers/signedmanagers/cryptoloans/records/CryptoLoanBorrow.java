package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

public class CryptoLoanBorrow extends LoanBaseStructure {

    private final double loanAmount;
    private final double collateralAmount;
    private final double hourlyInterestRate;
    private final long orderId;

    /**
     * Constructor to init {@link CryptoLoanBorrow} object
     *
     * @param loanCoin       :       coin of the vip loan
     * @param collateralCoin : collateral coin of the vip loan
     **/
    public CryptoLoanBorrow(String loanCoin, String collateralCoin, double loanAmount, double collateralAmount,
                            double hourlyInterestRate, long orderId) {
        super(loanCoin, collateralCoin);
        this.loanAmount = loanAmount;
        this.collateralAmount = collateralAmount;
        this.hourlyInterestRate = hourlyInterestRate;
        this.orderId = orderId;
    }

    /**
     * Constructor to init {@link CryptoLoanBorrow} object
     *
     * @param jCryptoLoanBorrow : VIP loan base structure details as {@link JSONObject}
     **/
    public CryptoLoanBorrow(JSONObject jCryptoLoanBorrow) {
        super(jCryptoLoanBorrow);
        loanAmount = hItem.getDouble("loanAmount", 0);
        collateralAmount = hItem.getDouble("collateralAmount", 0);
        hourlyInterestRate = hItem.getDouble("hourlyInterestRate", 0);
        orderId = hItem.getLong("orderId", 0);
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getCollateralAmount() {
        return collateralAmount;
    }

    public double getHourlyInterestRate() {
        return hourlyInterestRate;
    }

    public long getOrderId() {
        return orderId;
    }

}
