package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BankruptcyLoanAmount} class is useful to create a bankruptcy loan amount
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-bankruptcy-loan-amount-user_data">
 * Query Portfolio Margin Bankruptcy Loan Amount (USER_DATA)</a>
 * @see BinanceItem
 */
public class BankruptcyLoanAmount extends BinanceItem {

    /**
     * {@code asset} of the loan amount
     */
    private final String asset;

    /**
     * {@code amount} of the loan
     */
    private final double amount;

    /**
     * Constructor to init {@link BankruptcyLoanAmount}
     *
     * @param asset  : asset of loan amount
     * @param amount : amount of the loan
     */
    public BankruptcyLoanAmount(String asset, double amount) {
        super(null);
        this.asset = asset;
        this.amount = amount;
    }

    /**
     * Constructor to init {@link BankruptcyLoanAmount}
     *
     * @param jBankruptcyLoanAmount : bankruptcy loan amount details as {@link JSONObject}
     */
    public BankruptcyLoanAmount(JSONObject jBankruptcyLoanAmount) {
        super(jBankruptcyLoanAmount);
        asset = hItem.getString("asset");
        amount = hItem.getDouble("amount", 0);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
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

}
