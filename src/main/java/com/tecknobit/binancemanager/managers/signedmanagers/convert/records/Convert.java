package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;


/**
 * The {@code AcceptQuote} class is useful to format an accept quote
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#order-status-user_data">
 * Order status (USER_DATA)</a>
 * @see BinanceItem
 * @see AcceptQuote
 **/
public class Convert extends AcceptQuote {

    /**
     * {@code fromAsset} from asset of the convert
     **/
    private final String fromAsset;

    /**
     * {@code fromAmount} from amount of the convert
     **/
    private final double fromAmount;

    /**
     * {@code toAsset} to asset of the convert
     **/
    private final String toAsset;

    /**
     * {@code toAmount} to amount of the convert
     **/
    private final double toAmount;

    /**
     * {@code ratio} of the convert
     **/
    private final double ratio;

    /**
     * {@code inverseRatio} inverse ratio of the convert
     **/
    private final double inverseRatio;

    /**
     * Constructor to init {@link Convert} object
     *
     * @param orderId:      id of the quote
     * @param createTime:   creation time of the quote
     * @param orderStatus:  status of the quote
     * @param fromAsset:    from asset of the convert
     * @param fromAmount:   from amount of the convert
     * @param toAsset:      to asset of the convert
     * @param toAmount:     to amount of the convert
     * @param ratio:        ratio of the convert
     * @param inverseRatio: inverse ratio of the convert
     **/
    public Convert(long orderId, long createTime, AcceptQuoteStatus orderStatus, String fromAsset,
                   double fromAmount, String toAsset, double toAmount, double ratio, double inverseRatio) {
        super(orderId, createTime, orderStatus);
        this.fromAsset = fromAsset;
        this.fromAmount = fromAmount;
        this.toAsset = toAsset;
        this.toAmount = toAmount;
        this.ratio = ratio;
        this.inverseRatio = inverseRatio;
    }

    /**
     * Constructor to init {@link Convert} object
     *
     * @param jConvertStatus: convert details as {@link JSONObject}
     **/
    public Convert(JSONObject jConvertStatus) {
        super(jConvertStatus);
        fromAsset = hItem.getString("fromAsset");
        fromAmount = hItem.getDouble("fromAmount", 0);
        toAsset = hItem.getString("toAsset");
        toAmount = hItem.getDouble("toAmount", 0);
        ratio = hItem.getDouble("ratio", 0);
        inverseRatio = hItem.getDouble("inverseRatio", 0);
    }

    /**
     * Method to get {@link #fromAsset} instance <br>
     * No-any params required
     *
     * @return {@link #fromAsset} instance as {@link String}
     **/
    public String getFromAsset() {
        return fromAsset;
    }

    /**
     * Method to get {@link #fromAmount} instance <br>
     * No-any params required
     *
     * @return {@link #fromAmount} instance as double
     **/
    public double getFromAmount() {
        return fromAmount;
    }

    /**
     * Method to get {@link #fromAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fromAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFromAmount(int decimals) {
        return roundValue(fromAmount, decimals);
    }

    /**
     * Method to get {@link #toAsset} instance <br>
     * No-any params required
     *
     * @return {@link #toAsset} instance as {@link String}
     **/
    public String getToAsset() {
        return toAsset;
    }

    /**
     * Method to get {@link #toAmount} instance <br>
     * No-any params required
     *
     * @return {@link #toAmount} instance as double
     **/
    public double getToAmount() {
        return toAmount;
    }

    /**
     * Method to get {@link #toAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #toAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getToAmount(int decimals) {
        return roundValue(toAmount, decimals);
    }

    /**
     * Method to get {@link #ratio} instance <br>
     * No-any params required
     *
     * @return {@link #ratio} instance as double
     **/
    public double getRatio() {
        return ratio;
    }

    /**
     * Method to get {@link #ratio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #ratio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRatio(int decimals) {
        return roundValue(ratio, decimals);
    }

    /**
     * Method to get {@link #inverseRatio} instance <br>
     * No-any params required
     *
     * @return {@link #inverseRatio} instance as double
     **/
    public double getInverseRatio() {
        return inverseRatio;
    }

    /**
     * Method to get {@link #inverseRatio} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #inverseRatio} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInverseRatio(int decimals) {
        return roundValue(inverseRatio, decimals);
    }

}
