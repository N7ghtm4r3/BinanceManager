package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code VIPLoanRepay} class is useful to create a VIP loan repay
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
 * VIP Loan Repay (TRADE)</a>
 * @see BinanceItem
 * @see VIPLoanBaseStructure
 **/
public class VIPLoanRepay extends VIPLoanRepayStructure {

    /**
     * {@code remainingPrincipal} remaining principal of the VIP loan repay
     **/
    private final double remainingPrincipal;

    /**
     * {@code remainingInterest} remaining interest of the VIP loan repay
     **/
    private final double remainingInterest;

    /**
     * {@code currentLTV} current LTV of the VIP loan repay
     **/
    private final double currentLTV;

    /**
     * Constructor to init {@link VIPLoanRepay} object
     *
     * @param loanCoin:           coin of the vip loan
     * @param collateralCoin:     collateral coin of the vip loan
     * @param repayAmount:        repay amount of the loan
     * @param repayStatus:        repay status of the loan
     * @param remainingPrincipal: remaining principal of the VIP loan repay
     * @param remainingInterest:  remaining interest of the VIP loan repay
     * @param currentLTV:         current LTV of the VIP loan repay
     **/
    public VIPLoanRepay(String loanCoin, String collateralCoin, double repayAmount, RepayStatus repayStatus,
                        double remainingPrincipal, double remainingInterest, double currentLTV) {
        super(loanCoin, collateralCoin, repayAmount, repayStatus);
        this.remainingPrincipal = remainingPrincipal;
        this.remainingInterest = remainingInterest;
        this.currentLTV = currentLTV;
    }

    /**
     * Constructor to init {@link VIPLoanRepay} object
     *
     * @param jVIPLoanRepay: VIP loan repay details as {@link JSONObject}
     **/
    public VIPLoanRepay(JSONObject jVIPLoanRepay) {
        super(jVIPLoanRepay);
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
