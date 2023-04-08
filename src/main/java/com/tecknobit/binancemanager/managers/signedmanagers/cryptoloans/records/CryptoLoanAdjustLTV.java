package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

public class CryptoLoanAdjustLTV extends LoanBaseStructure {

    public enum LoanAdjustDirection {

        ADDITIONAL,
        REDUCED

    }

    private final LoanAdjustDirection direction;
    private final double amount;
    private final double currentLTV;

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param loanCoin       :       coin of the loan
     * @param collateralCoin : collateral coin of the loan
     **/
    public CryptoLoanAdjustLTV(String loanCoin, String collateralCoin, LoanAdjustDirection direction, double amount,
                               double currentLTV) {
        super(loanCoin, collateralCoin);
        this.direction = direction;
        this.amount = amount;
        this.currentLTV = currentLTV;
    }

    /**
     * Constructor to init {@link LoanBaseStructure} object
     *
     * @param jCryptoLoanAdjustLTV :  loan base structure details as {@link JSONObject}
     **/
    public CryptoLoanAdjustLTV(JSONObject jCryptoLoanAdjustLTV) {
        super(jCryptoLoanAdjustLTV);
        direction = LoanAdjustDirection.valueOf(hItem.getString("direction"));
        amount = hItem.getDouble("amount", 0);
        currentLTV = hItem.getDouble("currentLTV", 0);
    }

    public LoanAdjustDirection getDirection() {
        return direction;
    }

    public double getAmount() {
        return amount;
    }

    public double getCurrentLTV() {
        return currentLTV;
    }

}
