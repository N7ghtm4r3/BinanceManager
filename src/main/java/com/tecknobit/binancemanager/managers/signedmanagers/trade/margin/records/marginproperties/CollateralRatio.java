package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginproperties;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CollateralRatio} class is useful to format a collateral ratio
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-collateral-ratio-market_data">
 * Cross margin collateral ratio (MARKET_DATA)</a>
 * @see BinanceItem
 **/
public class CollateralRatio extends BinanceItem {

    /**
     * {@code collaterals} list of {@link Collateral}
     **/
    private final ArrayList<Collateral> collaterals;

    /**
     * {@code assetNames} list of asset names
     **/
    private final ArrayList<String> assetNames;

    /**
     * Constructor to init {@link CollateralRatio} object
     *
     * @param collaterals: list of {@link Collateral}
     * @param assetNames:  list of asset names
     **/
    public CollateralRatio(ArrayList<Collateral> collaterals, ArrayList<String> assetNames) {
        super(null);
        this.collaterals = collaterals;
        this.assetNames = assetNames;
    }

    /**
     * Constructor to init {@link CollateralRatio} object
     *
     * @param jCollateralRatio: collateral ratio details as {@link JSONObject}
     **/
    public CollateralRatio(JSONObject jCollateralRatio) {
        super(jCollateralRatio);
        collaterals = new ArrayList<>();
        JSONArray jCollaterals = hItem.getJSONArray("collaterals", new JSONArray());
        for (int j = 0; j < jCollaterals.length(); j++)
            collaterals.add(new Collateral(jCollaterals.getJSONObject(j)));
        assetNames = returnStringsList(hItem.getJSONArray("assetNames"));
    }

    /**
     * Method to get {@link #collaterals} instance <br>
     * No-any params required
     *
     * @return {@link #collaterals} instance as {@link ArrayList} of {@link Collateral}
     **/
    public ArrayList<Collateral> getCollaterals() {
        return collaterals;
    }

    /**
     * Method to get {@link #assetNames} instance <br>
     * No-any params required
     *
     * @return {@link #assetNames} instance as {@link ArrayList} of {@link String}
     **/
    public ArrayList<String> getAssetNames() {
        return assetNames;
    }

    /**
     * The {@code Collateral} class is useful to format a collateral
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class Collateral extends BinanceItem {

        /**
         * {@code minUsdValue} min usd value
         **/
        private final double minUsdValue;

        /**
         * {@code maxUsdValue} max usd value
         **/
        private final double maxUsdValue;

        /**
         * {@code discountRate} discount rate value
         **/
        private final double discountRate;

        /**
         * Constructor to init {@link Collateral} object
         *
         * @param minUsdValue:  min usd value
         * @param maxUsdValue:  max usd value
         * @param discountRate: discount rate value
         **/
        public Collateral(double minUsdValue, double maxUsdValue, double discountRate) {
            super(null);
            this.minUsdValue = minUsdValue;
            this.maxUsdValue = maxUsdValue;
            this.discountRate = discountRate;
        }

        /**
         * Constructor to init {@link Collateral} object
         *
         * @param jCollateral: collateral details as {@link JSONObject}
         **/
        public Collateral(JSONObject jCollateral) {
            super(jCollateral);
            minUsdValue = hItem.getDouble("minUsdValue", 0);
            maxUsdValue = hItem.getDouble("maxUsdValue", 0);
            discountRate = hItem.getDouble("discountRate", 0);
        }

        /**
         * Method to get {@link #minUsdValue} instance <br>
         * No-any params required
         *
         * @return {@link #minUsdValue} instance as double
         **/
        public double getMinUsdValue() {
            return minUsdValue;
        }

        /**
         * Method to get {@link #minUsdValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minUsdValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMinUsdValue(int decimals) {
            return roundValue(minUsdValue, decimals);
        }

        /**
         * Method to get {@link #maxUsdValue} instance <br>
         * No-any params required
         *
         * @return {@link #maxUsdValue} instance as double
         **/
        public double getMaxUsdValue() {
            return maxUsdValue;
        }

        /**
         * Method to get {@link #maxUsdValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxUsdValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMaxUsdValue(int decimals) {
            return roundValue(maxUsdValue, decimals);
        }

        /**
         * Method to get {@link #discountRate} instance <br>
         * No-any params required
         *
         * @return {@link #discountRate} instance as double
         **/
        public double getDiscountRate() {
            return discountRate;
        }

        /**
         * Method to get {@link #discountRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #discountRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getDiscountRate(int decimals) {
            return roundValue(discountRate, decimals);
        }

    }

}
