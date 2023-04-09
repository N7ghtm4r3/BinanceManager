package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import com.tecknobit.binancemanager.managers.records.loan.LoanRepayStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code VIPLoanRepay} class is useful to create a VIP loan repay
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
 * VIP Loan Repay (TRADE)</a>
 * @see BinanceItem
 * @see LoanBaseStructure
 * @see LoanRepayStructure
 **/
public class VIPLoanRepay extends LoanRepayStructure {

    /**
     * {@code repayAmount} repay amount of the loan
     **/
    private final double repayAmount;

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
    public VIPLoanRepay(String loanCoin, String collateralCoin, RepayStatus repayStatus, double remainingPrincipal,
                        double remainingInterest, double currentLTV, double repayAmount) {
        super(loanCoin, collateralCoin, repayStatus, remainingPrincipal, remainingInterest, currentLTV);
        this.repayAmount = repayAmount;
    }

    /**
     * Constructor to init {@link VIPLoanRepay} object
     *
     * @param jVIPLoanRepay: VIP loan repay details as {@link JSONObject}
     **/
    public VIPLoanRepay(JSONObject jVIPLoanRepay) {
        super(jVIPLoanRepay);
        repayAmount = hItem.getDouble("repayAmount", 0);
    }

    /**
     * Method to get {@link #repayAmount} instance <br>
     * No-any params required
     *
     * @return {@link #repayAmount} instance as double
     **/
    public double getRepayAmount() {
        return repayAmount;
    }

    /**
     * Method to get {@link #repayAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #repayAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRepayAmount(int decimals) {
        return roundValue(repayAmount, decimals);
    }

}
