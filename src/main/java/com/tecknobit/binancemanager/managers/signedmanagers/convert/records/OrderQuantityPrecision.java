package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code OrderQuantityPrecision} class is useful to format an order quantity precision
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-quantity-precision-per-asset-user_data">
 * Query order quantity precision per asset (USER_DATA)</a>
 * @see BinanceItem
 **/
public class OrderQuantityPrecision extends BinanceItem {

    /**
     * {@code asset} of the quantity precision
     **/
    private final String asset;

    /**
     * {@code fraction} of the quantity precision
     **/
    private final double fraction;

    /**
     * Constructor to init {@link OrderQuantityPrecision} object
     *
     * @param asset:    asset of the quantity precision
     * @param fraction: fraction of the quantity precision
     **/
    public OrderQuantityPrecision(String asset, double fraction) {
        super(null);
        this.asset = asset;
        this.fraction = fraction;
    }

    /**
     * Constructor to init {@link OrderQuantityPrecision} object
     *
     * @param jOrderQuantityPrecision: order quantity precision details as {@link JSONObject}
     **/
    public OrderQuantityPrecision(JSONObject jOrderQuantityPrecision) {
        super(jOrderQuantityPrecision);
        asset = hItem.getString("asset");
        fraction = hItem.getDouble("fraction", 0);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     **/
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #fraction} instance <br>
     * No-any params required
     *
     * @return {@link #fraction} instance as double
     **/
    public double getFraction() {
        return fraction;
    }

    /**
     * Method to get {@link #fraction} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fraction} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFraction(int decimals) {
        return roundValue(fraction, decimals);
    }

}
