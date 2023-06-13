package com.tecknobit.binancemanager.managers.records.loan;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LoanOrder} class is useful to create a loan order structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 * @see LoanBaseStructure
 */
@Structure
public abstract class LoanOrder extends LoanBaseStructure {

    /**
     * {@code orderId} id of the loan order
     */
    protected final long orderId;

    /**
     * {@code totalDebt} total debit of the loan order
     */
    protected final double totalDebt;

    /**
     * {@code residualInterest} residual interest of the loan order
     */
    protected final double residualInterest;

    /**
     * {@code currentLTV} current LTV of the loan order
     */
    protected final double currentLTV;

    /**
     * {@code expirationTime} expiration time of the loan order
     */
    protected final long expirationTime;

    /**
     * Constructor to init {@link LoanOrder} object
     *
     * @param loanCoin:         coin of the loan order
     * @param collateralCoin:   collateral coin of the loan order
     * @param orderId:          id of the loan order
     * @param totalDebt:        total debit of the loan order
     * @param residualInterest: residual interest of the loan order
     * @param currentLTV:       current LTV of the loan order
     * @param expirationTime:   expiration time of the loan order
     */
    public LoanOrder(String loanCoin, String collateralCoin, long orderId, double totalDebt, double residualInterest,
                     double currentLTV, long expirationTime) {
        super(loanCoin, collateralCoin);
        this.orderId = orderId;
        this.totalDebt = totalDebt;
        this.residualInterest = residualInterest;
        this.currentLTV = currentLTV;
        this.expirationTime = expirationTime;
    }

    /**
     * Constructor to init {@link LoanOrder} object
     *
     * @param jLoanOrder: loan order details as {@link JSONObject}
     */
    public LoanOrder(JSONObject jLoanOrder) {
        super(jLoanOrder);
        orderId = hItem.getLong("orderId", 0);
        totalDebt = hItem.getDouble("totalDebt", 0);
        residualInterest = hItem.getDouble("residualInterest", 0);
        currentLTV = hItem.getDouble("currentLTV", 0);
        expirationTime = hItem.getLong("expirationTime", 0);
    }

    /**
     * Method to get {@link #orderId} instance <br>
     * No-any params required
     *
     * @return {@link #orderId} instance as long
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Method to get {@link #totalDebt} instance <br>
     * No-any params required
     *
     * @return {@link #totalDebt} instance as double
     */
    public double getTotalDebt() {
        return totalDebt;
    }

    /**
     * Method to get {@link #totalDebt} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalDebt} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalDebt(int decimals) {
        return roundValue(totalDebt, decimals);
    }

    /**
     * Method to get {@link #residualInterest} instance <br>
     * No-any params required
     *
     * @return {@link #residualInterest} instance as double
     */
    public double getResidualInterest() {
        return residualInterest;
    }

    /**
     * Method to get {@link #residualInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #residualInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getResidualInterest(int decimals) {
        return roundValue(residualInterest, decimals);
    }

    /**
     * Method to get {@link #currentLTV} instance <br>
     * No-any params required
     *
     * @return {@link #currentLTV} instance as double
     */
    public double getCurrentLTV() {
        return currentLTV;
    }

    /**
     * Method to get {@link #currentLTV} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #currentLTV} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCurrentLTV(int decimals) {
        return roundValue(currentLTV, decimals);
    }

    /**
     * Method to get {@link #expirationTime} instance <br>
     * No-any params required
     *
     * @return {@link #expirationTime} instance as long
     */
    public long getExpirationTime() {
        return expirationTime;
    }

    /**
     * Method to get {@link #expirationTime} instance <br>
     * No-any params required
     *
     * @return {@link #expirationTime} instance as {@link Date}
     */
    public Date getExpirationDate() {
        return TimeFormatter.getDate(expirationTime);
    }

}
