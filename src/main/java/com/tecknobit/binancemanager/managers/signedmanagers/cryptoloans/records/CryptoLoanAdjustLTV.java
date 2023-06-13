package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.loan.LoanBaseStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CryptoLoanAdjustLTV} class is useful to create a crypto loan adjust LTV
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#adjust-ltv-crypto-loan-adjust-ltv-trade">
 * Adjust LTV - Crypto Loan Adjust LTV (TRADE)</a>
 * @see BinanceItem
 * @see LoanBaseStructure
 */
public class CryptoLoanAdjustLTV extends LoanBaseStructure {

    /**
     * {@code LoanAdjustDirection} list of available adjust directions
     */
    public enum LoanAdjustDirection {

        /**
         * {@code ADDITIONAL} adjust direction
         */
        ADDITIONAL,

        /**
         * {@code REDUCED} adjust direction
         */
        REDUCED

    }

    /**
     * {@code direction} of the loan adjust LTV
     */
    private final LoanAdjustDirection direction;

    /**
     * {@code amount} of the loan adjust LTV
     */
    private final double amount;

    /**
     * {@code currentLTV} current LTV of the loan adjust LTV
     */
    private final double currentLTV;

    /**
     * Constructor to init {@link CryptoLoanAdjustLTV} object
     *
     * @param loanCoin       :       coin of the loan adjust LTV
     * @param collateralCoin : collateral coin of the loan adjust LTV
     * @param direction      : direction of the loan adjust LTV
     * @param amount         : amount of the loan adjust LTV
     * @param currentLTV     : current LTV of the loan adjust LTV
     */
    public CryptoLoanAdjustLTV(String loanCoin, String collateralCoin, LoanAdjustDirection direction, double amount,
                               double currentLTV) {
        super(loanCoin, collateralCoin);
        this.direction = direction;
        this.amount = amount;
        this.currentLTV = currentLTV;
    }

    /**
     * Constructor to init {@link CryptoLoanAdjustLTV} object
     *
     * @param jCryptoLoanAdjustLTV : crypto loan adjust LTV details as {@link JSONObject}
     */
    public CryptoLoanAdjustLTV(JSONObject jCryptoLoanAdjustLTV) {
        super(jCryptoLoanAdjustLTV);
        direction = LoanAdjustDirection.valueOf(hItem.getString("direction"));
        amount = hItem.getDouble("amount", 0);
        currentLTV = hItem.getDouble("currentLTV", 0);
    }

    /**
     * Method to get {@link #direction} instance <br>
     * No-any params required
     *
     * @return {@link #direction} instance as {@link LoanAdjustDirection}
     */
    public LoanAdjustDirection getDirection() {
        return direction;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
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

}
