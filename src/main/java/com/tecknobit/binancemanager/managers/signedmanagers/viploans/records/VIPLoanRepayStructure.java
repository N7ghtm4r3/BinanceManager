package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code VIPLoanRepayStructure} class is useful to create a VIP loan repay structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#vip-loan-repay-trade">
 *             VIP Loan Repay (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-vip-loan-repayment-history-user_data">
 *             Get VIP Loan Repayment History (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see VIPLoanBaseStructure
 **/
public abstract class VIPLoanRepayStructure extends VIPLoanBaseStructure {

    /**
     * {@code RepayStatus} list of available repay statuses
     **/
    public enum RepayStatus {

        /**
         * {@code Repaid} repay status
         **/
        Repaid,

        /**
         * {@code Repaying} repay status
         **/
        Repaying,

        /**
         * {@code Failed} repay status
         **/
        Failed

    }

    /**
     * {@code repayAmount} repay amount of the loan
     **/
    protected final double repayAmount;

    /**
     * {@code repayStatus} repay status of the loan
     **/
    protected final RepayStatus repayStatus;

    /**
     * Constructor to init {@link VIPLoanBaseStructure} object
     *
     * @param loanCoin:       coin of the vip loan
     * @param collateralCoin: collateral coin of the vip loan
     * @param repayAmount:    repay amount of the loan
     * @param repayStatus:    repay status of the loan
     **/
    public VIPLoanRepayStructure(String loanCoin, String collateralCoin, double repayAmount, RepayStatus repayStatus) {
        super(loanCoin, collateralCoin);
        this.repayAmount = repayAmount;
        this.repayStatus = repayStatus;
    }

    /**
     * Constructor to init {@link VIPLoanRepayStructure} object
     *
     * @param jVIPLoanRepayStructure: VIP loan repay structure details as {@link JSONObject}
     **/
    public VIPLoanRepayStructure(JSONObject jVIPLoanRepayStructure) {
        super(jVIPLoanRepayStructure);
        repayAmount = hItem.getDouble("repayAmount", 0);
        repayStatus = RepayStatus.valueOf(hItem.getString("repayStatus"));
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

    /**
     * Method to get {@link #repayStatus} instance <br>
     * No-any params required
     *
     * @return {@link #repayStatus} instance as {@link String}
     **/
    public RepayStatus getRepayStatus() {
        return repayStatus;
    }

}
