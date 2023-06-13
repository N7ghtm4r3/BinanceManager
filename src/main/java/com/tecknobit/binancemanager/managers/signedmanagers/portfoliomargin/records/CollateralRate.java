package com.tecknobit.binancemanager.managers.signedmanagers.portfoliomargin.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CollateralRate} class is useful to create a collateral rate
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#portfolio-margin-collateral-rate-market_data">
 * Portfolio Margin Collateral Rate (MARKET_DATA)</a>
 * @see BinanceItem
 */
public class CollateralRate extends BinanceItem {

    /**
     * {@code asset} of the collateral rate
     */
    private final String asset;

    /**
     * {@code collateralRate} value of the collateral rate
     */
    private final double collateralRate;

    /**
     * Constructor to init {@link CollateralRate}
     *
     * @param asset          : asset of the collateral rate
     * @param collateralRate : value of the collateral rate
     */
    public CollateralRate(String asset, double collateralRate) {
        super(null);
        this.asset = asset;
        this.collateralRate = collateralRate;
    }

    /**
     * Constructor to init {@link CollateralRate}
     *
     * @param jCollateralRate : collateral rate details as {@link JSONObject}
     */
    public CollateralRate(JSONObject jCollateralRate) {
        super(jCollateralRate);
        asset = hItem.getString("asset");
        collateralRate = hItem.getDouble("collateralRate", 0);
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
     * Method to get {@link #collateralRate} instance <br>
     * No-any params required
     *
     * @return {@link #collateralRate} instance as double
     */
    public double getCollateralRate() {
        return collateralRate;
    }

    /**
     * Method to get {@link #collateralRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #collateralRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getCollateralRate(int decimals) {
        return roundValue(collateralRate, decimals);
    }

}
