package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PurchaseQuota} class is useful to format a purchase quota
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-left-daily-purchase-quota-of-flexible-product-user_data">
 * Get Left Daily Purchase Quota of Flexible Product (USER_DATA)</a>
 * @see BinanceItem
 **/
public class PurchaseQuota extends BinanceItem {

    /**
     * {@code asset} of the purchase quota
     **/
    protected final String asset;

    /**
     * {@code leftQuota} left quota of the purchase quota
     **/
    protected final double leftQuota;

    /**
     * Constructor to init {@link PurchaseQuota} object
     *
     * @param asset:     asset of the purchase quota
     * @param leftQuota: left quota of the purchase quota
     **/
    public PurchaseQuota(String asset, double leftQuota) {
        super(null);
        this.asset = asset;
        this.leftQuota = leftQuota;
    }

    /**
     * Constructor to init {@link PurchaseQuota} object
     *
     * @param jPurchaseQuota: purchase quota details as {@link JSONObject}
     **/
    public PurchaseQuota(JSONObject jPurchaseQuota) {
        super(jPurchaseQuota);
        asset = hItem.getString("asset");
        leftQuota = hItem.getDouble("leftQuota", 0);
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
     * Method to get {@link #leftQuota} instance <br>
     * No-any params required
     *
     * @return {@link #leftQuota} instance as double
     **/
    public double getLeftQuota() {
        return leftQuota;
    }

    /**
     * Method to get {@link #leftQuota} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #leftQuota} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLeftQuota(int decimals) {
        return roundValue(leftQuota, decimals);
    }

}
