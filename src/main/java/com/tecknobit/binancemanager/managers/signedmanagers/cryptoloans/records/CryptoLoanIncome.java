package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CryptoLoanIncome} class is useful to create a crypto loan income
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-crypto-loans-income-history-user_data">
 * Get Crypto Loans Income History (USER_DATA)</a>
 * @see BinanceItem
 */
public class CryptoLoanIncome extends BinanceItem {

    /**
     * {@code LoanType} list of available loan types
     */
    public enum LoanType {

        /**
         * {@code borrowIn} loan type
         */
        borrowIn,

        /**
         * {@code collateralSpent} loan type
         */
        collateralSpent,

        /**
         * {@code repayAmount} loan type
         */
        repayAmount,

        /**
         * {@code collateralReturn} loan type
         */
        collateralReturn,

        /**
         * {@code addCollateral} loan type
         */
        addCollateral,

        /**
         * {@code removeCollateral} loan type
         */
        removeCollateral,

        /**
         * {@code collateralReturnAfterLiquidation} loan type
         */
        collateralReturnAfterLiquidation

    }

    /**
     * {@code asset} of the loan income
     */
    private final String asset;

    /**
     * {@code type} of the loan income
     */
    private final LoanType type;

    /**
     * {@code amount} of the loan income
     */
    private final double amount;

    /**
     * {@code timestamp} of the loan income
     */
    private final long timestamp;

    /**
     * {@code tranId} tran id of the loan income
     */
    private final long tranId;

    /**
     * Constructor to init {@link CryptoLoanIncome} object
     *
     * @param asset     :asset of the loan income
     * @param type      :type of the loan income
     * @param amount    :amount of the loan income
     * @param timestamp :timestamp of the loan income
     * @param tranId    : tran id of the loan income
     */
    public CryptoLoanIncome(String asset, LoanType type, double amount, long timestamp, long tranId) {
        super(null);
        this.asset = asset;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.tranId = tranId;
    }

    /**
     * Constructor to init {@link CryptoLoanIncome} object
     *
     * @param jCryptoLoanIncome : crypto loan income details as {@link JSONObject}
     */
    public CryptoLoanIncome(JSONObject jCryptoLoanIncome) {
        super(jCryptoLoanIncome);
        asset = hItem.getString("asset");
        type = LoanType.valueOf(hItem.getString("type"));
        amount = hItem.getDouble("amount", 0);
        timestamp = hItem.getLong("timestamp", 0);
        tranId = hItem.getLong("tranId", 0);
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
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link LoanType}
     */
    public LoanType getType() {
        return type;
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
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as long
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(timestamp);
    }

    /**
     * Method to get {@link #tranId} instance <br>
     * No-any params required
     *
     * @return {@link #tranId} instance as long
     */
    public long getTranId() {
        return tranId;
    }

}
