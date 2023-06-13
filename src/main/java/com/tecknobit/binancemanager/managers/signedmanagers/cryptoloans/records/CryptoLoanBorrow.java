package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CryptoLoanBorrow} class is useful to create a crypto loan borrow
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#borrow-crypto-loan-borrow-trade">
 * Borrow - Crypto Loan Borrow (TRADE)</a>
 * @see BinanceItem
 * @see LoanBaseStructure
 */
public class CryptoLoanBorrow extends LoanBaseStructure {

    /**
     * {@code loanAmount} loan amount of the loan borrow
     */
    private final double loanAmount;

    /**
     * {@code collateralAmount} collateral amount of the loan borrow
     */
    private final double collateralAmount;

    /**
     * {@code hourlyInterestRate} hourly interest rate of the loan borrow
     */
    private final double hourlyInterestRate;

    /**
     * {@code orderId} order id of the loan borrow
     */
    private final long orderId;

    /**
     * Constructor to init {@link CryptoLoanBorrow} object
     *
     * @param loanCoin           :       coin of the loan borrow
     * @param collateralCoin     : collateral coin of the loan borrow
     * @param loanAmount         :     loan amount of the loan borrow
     * @param collateralAmount   : collateral amount of the loan borrow
     * @param hourlyInterestRate : hourly interest rate of the loan borrow
     * @param orderId            : order id of the loan borrow
     */
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
     * @param jCryptoLoanBorrow : crypto loan borrow details as {@link JSONObject}
     */
    public CryptoLoanBorrow(JSONObject jCryptoLoanBorrow) {
        super(jCryptoLoanBorrow);
        loanAmount = hItem.getDouble("loanAmount", 0);
        collateralAmount = hItem.getDouble("collateralAmount", 0);
        hourlyInterestRate = hItem.getDouble("hourlyInterestRate", 0);
        orderId = hItem.getLong("orderId", 0);
    }

    /**
     * Method to get {@link #loanAmount} instance <br>
     * No-any params required
     *
     * @return {@link #loanAmount} instance as double
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Method to get {@link #loanAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #loanAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLoanAmount(int decimals) {
        return roundValue(loanAmount, decimals);
    }

    /**
     * Method to get {@link #collateralAmount} instance <br>
     * No-any params required
     *
     * @return {@link #collateralAmount} instance as double
     */
    public double getCollateralAmount() {
        return collateralAmount;
    }

    /**
     * Method to get {@link #collateralAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #collateralAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCollateralAmount(int decimals) {
        return roundValue(collateralAmount, decimals);
    }

    /**
     * Method to get {@link #hourlyInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #hourlyInterestRate} instance as double
     */
    public double getHourlyInterestRate() {
        return hourlyInterestRate;
    }

    /**
     * Method to get {@link #hourlyInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #hourlyInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getHourlyInterestRate(int decimals) {
        return roundValue(hourlyInterestRate, decimals);
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

}
