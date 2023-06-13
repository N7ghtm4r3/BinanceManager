package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code QuoteRequest} class is useful to format a quote request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#send-quote-request-user_data">
 * Send quote request (USER_DATA)</a>
 * @see BinanceItem
 */
public class QuoteRequest extends BinanceItem {

    /**
     * {@code WalletType} list of available wallet types
     */
    public enum WalletType {

        /**
         * {@code SPOT} wallet type
         */
        SPOT,

        /**
         * {@code FUNDING} wallet type
         */
        FUNDING

    }

    /**
     * {@code ValidTime} list of available valid times
     */
    public enum ValidTime {

        /**
         * {@code 10s} valid time
         */
        _10s("10s"),

        /**
         * {@code 30s} valid time
         */
        _30s("30s"),

        /**
         * {@code 1m} valid time
         */
        _1m("1m"),

        /**
         * {@code 2m} valid time
         */
        _2m("2m");

        /**
         * {@code validTime} valid time value
         */
        private final String validTime;

        /**
         * Constructor to init {@link ValidTime} object
         *
         * @param validTime: valid time value
         */
        ValidTime(String validTime) {
            this.validTime = validTime;
        }

        /**
         * Method to get {@link #validTime} instance <br>
         * No-any params required
         *
         * @return {@link #validTime} instance as {@link String}
         */
        @Override
        public String toString() {
            return validTime;
        }

    }

    /**
     * {@code quoteId} quote id of the quote request
     */
    private final long quoteId;

    /**
     * {@code ratio} of the quote request
     */
    private final double ratio;

    /**
     * {@code inverseRatio} inverse ratio of the quote request
     */
    private final double inverseRatio;

    /**
     * {@code validTimestamp} valid timestamp of the quote request
     */
    private final long validTimestamp;

    /**
     * {@code toAmount} to amount the quote request
     */
    private final double toAmount;

    /**
     * {@code fromAmount} from amount of the quote request
     */
    private final double fromAmount;

    /**
     * Constructor to init {@link QuoteRequest} object
     *
     * @param quoteId:        quote id of the quote request
     * @param ratio:          ratio of the quote request
     * @param inverseRatio:   inverse ratio of the quote request
     * @param validTimestamp: valid timestamp of the quote request
     * @param toAmount:       to amount the quote request
     * @param fromAmount:     from amount of the quote request
     */
    public QuoteRequest(long quoteId, double ratio, double inverseRatio, long validTimestamp, double toAmount,
                        double fromAmount) {
        super(null);
        this.quoteId = quoteId;
        this.ratio = ratio;
        this.inverseRatio = inverseRatio;
        this.validTimestamp = validTimestamp;
        this.toAmount = toAmount;
        this.fromAmount = fromAmount;
    }

    /**
     * Constructor to init {@link QuoteRequest} object
     *
     * @param jQuoteRequest: quote request details as {@link JSONObject}
     */
    public QuoteRequest(JSONObject jQuoteRequest) {
        super(jQuoteRequest);
        quoteId = hItem.getLong("quoteId", 0);
        ratio = hItem.getDouble("ratio", 0);
        inverseRatio = hItem.getDouble("inverseRatio", 0);
        validTimestamp = hItem.getLong("validTimestamp", 0);
        toAmount = hItem.getDouble("toAmount", 0);
        fromAmount = hItem.getDouble("fromAmount", 0);
    }

    /**
     * Method to get {@link #quoteId} instance <br>
     * No-any params required
     *
     * @return {@link #quoteId} instance as long
     */
    public long getQuoteId() {
        return quoteId;
    }

    /**
     * Method to get {@link #ratio} instance <br>
     * No-any params required
     *
     * @return {@link #ratio} instance as double
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * Method to get {@link #ratio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #ratio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRatio(int decimals) {
        return roundValue(ratio, decimals);
    }

    /**
     * Method to get {@link #inverseRatio} instance <br>
     * No-any params required
     *
     * @return {@link #inverseRatio} instance as double
     */
    public double getInverseRatio() {
        return inverseRatio;
    }

    /**
     * Method to get {@link #inverseRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #inverseRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getInverseRatio(int decimals) {
        return roundValue(inverseRatio, decimals);
    }

    /**
     * Method to get {@link #validTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #validTimestamp} instance as long
     */
    public long getValidTimestamp() {
        return validTimestamp;
    }

    /**
     * Method to get {@link #validTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #validTimestamp} instance as {@link Date}
     */
    public Date getValidTimestampDate() {
        return TimeFormatter.getDate(validTimestamp);
    }

    /**
     * Method to get {@link #toAmount} instance <br>
     * No-any params required
     *
     * @return {@link #toAmount} instance as double
     */
    public double getToAmount() {
        return toAmount;
    }

    /**
     * Method to get {@link #toAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #toAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getToAmount(int decimals) {
        return roundValue(toAmount, decimals);
    }

    /**
     * Method to get {@link #fromAmount} instance <br>
     * No-any params required
     *
     * @return {@link #fromAmount} instance as double
     */
    public double getFromAmount() {
        return fromAmount;
    }

    /**
     * Method to get {@link #fromAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fromAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getFromAmount(int decimals) {
        return roundValue(fromAmount, decimals);
    }

}
