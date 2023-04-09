package com.tecknobit.binancemanager.managers.records.loan;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LoanRepayStructure} class is useful to create a loan repay structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 * @see LoanBaseStructure
 * @see LoanBaseRepayStructure
 **/
public abstract class LoanRepayStructure extends LoanBaseRepayStructure {

    /**
     * {@code remainingPrincipal} remaining principal of the loan repay
     **/
    protected final double remainingPrincipal;

    /**
     * {@code remainingInterest} remaining interest of the loan repay
     **/
    protected final double remainingInterest;

    /**
     * {@code currentLTV} current LTV of the loan repay
     **/
    protected final double currentLTV;

    /**
     * Constructor to init {@link LoanRepayStructure} object
     *
     * @param loanCoin:           coin of the vip loan
     * @param collateralCoin:     collateral coin of the vip loan
     * @param repayStatus:        repay status of the loan
     * @param remainingPrincipal: remaining principal of the loan repay
     * @param remainingInterest:  remaining interest of the loan repay
     * @param currentLTV:         current LTV of the loan repay
     **/
    public LoanRepayStructure(String loanCoin, String collateralCoin, RepayStatus repayStatus, double remainingPrincipal,
                              double remainingInterest, double currentLTV) {
        super(loanCoin, collateralCoin, repayStatus);
        this.remainingPrincipal = remainingPrincipal;
        this.remainingInterest = remainingInterest;
        this.currentLTV = currentLTV;
    }

    /**
     * Constructor to init {@link LoanRepayStructure} object
     *
     * @param jLoanRepayStructure: loan repay structure details as {@link JSONObject}
     **/
    public LoanRepayStructure(JSONObject jLoanRepayStructure) {
        super(jLoanRepayStructure);
        remainingPrincipal = hItem.getDouble("remainingPrincipal", 0);
        remainingInterest = hItem.getDouble("remainingInterest", 0);
        currentLTV = hItem.getDouble("currentLTV", 0);
    }

    /**
     * Method to get {@link #remainingPrincipal} instance <br>
     * No-any params required
     *
     * @return {@link #remainingPrincipal} instance as double
     **/
    public double getRemainingPrincipal() {
        return remainingPrincipal;
    }

    /**
     * Method to get {@link #remainingPrincipal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #remainingPrincipal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRemainingPrincipal(int decimals) {
        return roundValue(remainingPrincipal, decimals);
    }

    /**
     * Method to get {@link #remainingInterest} instance <br>
     * No-any params required
     *
     * @return {@link #remainingInterest} instance as double
     **/
    public double getRemainingInterest() {
        return remainingInterest;
    }

    /**
     * Method to get {@link #remainingInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #remainingInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRemainingInterest(int decimals) {
        return roundValue(remainingInterest, decimals);
    }

    /**
     * Method to get {@link #currentLTV} instance <br>
     * No-any params required
     *
     * @return {@link #currentLTV} instance as double
     **/
    public double getCurrentLTV() {
        return currentLTV;
    }

    /**
     * Method to get {@link #currentLTV} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #currentLTV} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCurrentLTV(int decimals) {
        return roundValue(currentLTV, decimals);
    }

}
