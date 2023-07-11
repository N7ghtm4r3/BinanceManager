package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TierAnnualPercentageRate} class is useful to format a tier annual percentage rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
public class TierAnnualPercentageRate extends BinanceItem {

    /**
     * {@code _0_5BTC} 0-5 BTC
     */
    protected final double _0_5BTC;

    /**
     * {@code _5_10BTC} 5-10 BTC
     */
    protected final double _5_10BTC;

    /**
     * Constructor to init {@link TierAnnualPercentageRate} object
     *
     * @param _0_5BTC:  0-5 BTC
     * @param _5_10BTC: 5-10 BTC
     */
    public TierAnnualPercentageRate(double _0_5BTC, double _5_10BTC) {
        super(null);
        this._0_5BTC = _0_5BTC;
        this._5_10BTC = _5_10BTC;
    }

    /**
     * Constructor to init {@link TierAnnualPercentageRate} object
     *
     * @param jTierAnnualPercentageRate: tier annual percentage rate details as {@link JSONObject}
     */
    public TierAnnualPercentageRate(JSONObject jTierAnnualPercentageRate) {
        super(jTierAnnualPercentageRate);
        _0_5BTC = hItem.getDouble("0-5BTC", 0);
        _5_10BTC = hItem.getDouble("5-10BTC", 0);
    }

    /**
     * Method to get {@link #_0_5BTC} instance <br>
     * No-any params required
     *
     * @return {@link #_0_5BTC} instance as double
     */
    public double get0_5BTC() {
        return _0_5BTC;
    }

    /**
     * Method to get {@link #_0_5BTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #_0_5BTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double get0_5BTC(int decimals) {
        return roundValue(_0_5BTC, decimals);
    }

    /**
     * Method to get {@link #_5_10BTC} instance <br>
     * No-any params required
     *
     * @return {@link #_5_10BTC} instance as double
     */
    public double get5_10BTC() {
        return _5_10BTC;
    }

    /**
     * Method to get {@link #_5_10BTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #_5_10BTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double get5_10BTC(int decimals) {
        return roundValue(_5_10BTC, decimals);
    }

    /**
     * Method to get an instance of this Telegram's type
     *
     * @param jItem: item details as {@link JSONObject}
     * @return instance as {@link TierAnnualPercentageRate}
     */
    public static TierAnnualPercentageRate getInstance(JSONObject jItem) {
        if (jItem == null)
            return null;
        else
            return new TierAnnualPercentageRate(jItem);
    }

}
