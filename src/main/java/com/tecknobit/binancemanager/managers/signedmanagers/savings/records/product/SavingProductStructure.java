package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SavingProductStructure} class is useful to format a saving product structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
 *             Get Flexible Product List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
 *             Get Flexible Product Position (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see SavingStructure
 **/
public abstract class SavingProductStructure extends SavingStructure {

    /**
     * {@code tierAnnualInterestRate} tier annual interest rate of the saving product
     **/
    protected final TierAnnualInterestRate tierAnnualInterestRate;

    /**
     * {@code canRedeem} whether the saving product can redeem
     **/
    protected final boolean canRedeem;

    /**
     * {@code productId} product id of the saving product
     **/
    protected final String productId;

    /**
     * {@code averageAnnualInterestRate} average annual interest rate of the saving product
     **/
    protected final double averageAnnualInterestRate;

    /**
     * Constructor to init {@link SavingProductStructure} object
     *
     * @param asset:                     asset of the saving product
     * @param tierAnnualInterestRate:    tier annual interest rate of the saving product
     * @param canRedeem:                 whether the saving product can redeem
     * @param productId:                 product id of the saving product
     * @param averageAnnualInterestRate: average annual interest rate of the saving product
     **/
    public SavingProductStructure(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem,
                                  String productId, double averageAnnualInterestRate) {
        super(asset);
        this.tierAnnualInterestRate = tierAnnualInterestRate;
        this.canRedeem = canRedeem;
        this.productId = productId;
        this.averageAnnualInterestRate = averageAnnualInterestRate;
    }

    /**
     * Constructor to init {@link SavingProductStructure} object
     *
     * @param jSavingProductStructure: saving product details as {@link JSONObject}
     **/
    public SavingProductStructure(JSONObject jSavingProductStructure) {
        super(jSavingProductStructure);
        JSONObject jTier = hItem.getJSONObject("tierAnnualInterestRate");
        if (jTier != null)
            tierAnnualInterestRate = new TierAnnualInterestRate(jTier);
        else
            tierAnnualInterestRate = null;
        canRedeem = hItem.getBoolean("canRedeem");
        productId = hItem.getString("productId");
        averageAnnualInterestRate = hItem.getDouble("avgAnnualInterestRate", 0);
    }

    /**
     * Method to get {@link #tierAnnualInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #tierAnnualInterestRate} instance as {@link TierAnnualInterestRate}
     **/
    public TierAnnualInterestRate getTierAnnualInterestRate() {
        return tierAnnualInterestRate;
    }

    /**
     * Method to get {@link #canRedeem} instance <br>
     * No-any params required
     *
     * @return {@link #canRedeem} instance as boolean
     **/
    public boolean canRedeem() {
        return canRedeem;
    }

    /**
     * Method to get {@link #productId} instance <br>
     * No-any params required
     *
     * @return {@link #productId} instance as {@link String}
     **/
    public String getProductId() {
        return productId;
    }

    /**
     * Method to get {@link #averageAnnualInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #averageAnnualInterestRate} instance as double
     **/
    public double getAverageAnnualInterestRate() {
        return averageAnnualInterestRate;
    }

    /**
     * Method to get {@link #averageAnnualInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #averageAnnualInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAverageAnnualInterestRate(int decimals) {
        return roundValue(averageAnnualInterestRate, decimals);
    }

    /**
     * The {@code TierAnnualInterestRate} class is useful to format a tier annual interest rate
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class TierAnnualInterestRate extends BinanceItem {

        /**
         * {@code _0_5BTC} 0-5 BTC
         **/
        private final double _0_5BTC;

        /**
         * {@code _5_10BTC} 5-10 BTC
         **/
        private final double _5_10BTC;

        /**
         * {@code _m10BTC} >10 BTC
         **/
        private final double _m10BTC;

        /**
         * Constructor to init {@link TierAnnualInterestRate} object
         *
         * @param _0_5BTC:  0-5 BTC
         * @param _5_10BTC: 5-10 BTC
         * @param _m10BTC:  >10 BTC
         **/
        public TierAnnualInterestRate(double _0_5BTC, double _5_10BTC, double _m10BTC) {
            super(null);
            this._0_5BTC = _0_5BTC;
            this._5_10BTC = _5_10BTC;
            this._m10BTC = _m10BTC;
        }

        /**
         * Constructor to init {@link TierAnnualInterestRate} object
         *
         * @param jTierAnnualInterestRate: tier annual interest rate details as {@link JSONObject}
         **/
        public TierAnnualInterestRate(JSONObject jTierAnnualInterestRate) {
            super(jTierAnnualInterestRate);
            _0_5BTC = hItem.getDouble("0-5BTC", 0);
            _5_10BTC = hItem.getDouble("5-10BTC", 0);
            _m10BTC = hItem.getDouble(">10BTC", 0);
        }

        /**
         * Method to get {@link #_0_5BTC} instance <br>
         * No-any params required
         *
         * @return {@link #_0_5BTC} instance as double
         **/
        public double get_0_5BTC() {
            return _0_5BTC;
        }

        /**
         * Method to get {@link #_0_5BTC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_0_5BTC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get_0_5BTC(int decimals) {
            return roundValue(_0_5BTC, decimals);
        }

        /**
         * Method to get {@link #_5_10BTC} instance <br>
         * No-any params required
         *
         * @return {@link #_5_10BTC} instance as double
         **/
        public double get_5_10BTC() {
            return _5_10BTC;
        }

        /**
         * Method to get {@link #_5_10BTC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_5_10BTC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get_5_10BTC(int decimals) {
            return roundValue(_5_10BTC, decimals);
        }

        /**
         * Method to get {@link #_m10BTC} instance <br>
         * No-any params required
         *
         * @return {@link #_m10BTC} instance as double
         **/
        public double get_m10BTC() {
            return _m10BTC;
        }

        /**
         * Method to get {@link #_m10BTC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #_m10BTC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double get_m10BTC(int decimals) {
            return roundValue(_m10BTC, decimals);
        }

    }

}
