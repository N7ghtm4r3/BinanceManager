package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAssetIndexPrice} class is useful to format a {@code Binance}'s margin asset index price
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-portfolio-margin-asset-index-price-market_data">
 * Query Portfolio Margin Asset Index Price (MARKET_DATA)</a>
 * @see BinanceItem
 */
public class MarginAssetIndexPrice extends BinanceItem {

    /**
     * {@code asset} of the margin asset index price
     */
    private final String asset;

    /**
     * {@code assetIndexPrice} value of the margin asset index price
     */
    private final double assetIndexPrice;

    /**
     * {@code time} of the margin asset index price
     */
    private final long time;

    /**
     * Constructor to init a {@link MarginAssetIndexPrice} object
     *
     * @param asset:           asset of the margin asset index price
     * @param assetIndexPrice: value of the margin asset index price
     * @param time:            time of the margin asset index price
     */
    public MarginAssetIndexPrice(String asset, double assetIndexPrice, long time) {
        super(null);
        this.asset = asset;
        this.assetIndexPrice = assetIndexPrice;
        this.time = time;
    }

    /**
     * Constructor to init a {@link MarginAssetIndexPrice} object
     *
     * @param jMarginAssetIndexPrice: margin asset index price details as {@link JSONObject}
     */
    public MarginAssetIndexPrice(JSONObject jMarginAssetIndexPrice) {
        super(jMarginAssetIndexPrice);
        asset = hItem.getString("asset");
        assetIndexPrice = hItem.getDouble("assetIndexPrice", 0);
        time = hItem.getLong("time", 0);
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
     * Method to get {@link #assetIndexPrice} instance <br>
     * No-any params required
     *
     * @return {@link #assetIndexPrice} instance as {@link double}
     */
    public double getAssetIndexPrice() {
        return assetIndexPrice;
    }

    /**
     * Method to get {@link #assetIndexPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #assetIndexPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getAssetIndexPrice(int decimals) {
        return roundValue(assetIndexPrice, decimals);
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link long}
     */
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

}
