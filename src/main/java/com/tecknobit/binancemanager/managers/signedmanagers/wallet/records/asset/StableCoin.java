package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code StableCoin} class is useful to format a stable coin object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-auto-converting-stable-coins-user_data">
 *            Query auto-converting stable coins (USER_DATA)</a>
 *     </li>
 *      <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#switch-on-off-busd-and-stable-coins-conversion-user_data">
 *           Switch on/off BUSD and stable coins conversion (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public class StableCoin extends BinanceItem {

    /**
     * {@code convertedEnabled} whether conversion is enabled
     **/
    private final boolean convertedEnabled;

    /**
     * {@code coins} list of coins
     **/
    private final ArrayList<String> coins;

    /**
     * {@code exchangeRates} exchange rates
     **/
    private final ExchangeRates exchangeRates;

    /**
     * Constructor to init {@link StableCoin} object
     *
     * @param convertedEnabled: whether conversion is enabled
     * @param coins:            list of coins
     * @param exchangeRates:    exchange rates
     **/
    public StableCoin(boolean convertedEnabled, ArrayList<String> coins, ExchangeRates exchangeRates) {
        super(null);
        this.convertedEnabled = convertedEnabled;
        this.coins = coins;
        this.exchangeRates = exchangeRates;
    }

    /**
     * Constructor to init {@link StableCoin} object
     *
     * @param jStableCoin: stable coin details as {@link JSONObject}
     **/
    public StableCoin(JSONObject jStableCoin) {
        super(jStableCoin);
        convertedEnabled = hItem.getBoolean("convertEnabled");
        coins = returnStringsList(hItem.getJSONArray("coins"));
        exchangeRates = new ExchangeRates(hItem.getJSONObject("exchangeRates", new JSONObject()));
    }

    /**
     * Method to get {@link #convertedEnabled} instance <br>
     * No-any params required
     *
     * @return {@link #convertedEnabled} instance as boolean
     **/
    public boolean isConvertedEnabled() {
        return convertedEnabled;
    }

    /**
     * Method to get {@link #coins} instance <br>
     * No-any params required
     *
     * @return {@link #coins} instance as {@link ArrayList} of {@link String}
     **/
    public ArrayList<String> getCoins() {
        return coins;
    }

    /**
     * Method to get {@link #exchangeRates} instance <br>
     * No-any params required
     *
     * @return {@link #exchangeRates} instance as {@link ExchangeRates}
     **/
    public ExchangeRates getExchangeRates() {
        return exchangeRates;
    }

    /**
     * The {@code ExchangeRates} class is useful to format a exchange rates
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class ExchangeRates extends BinanceItem {

        /**
         * {@code USDC} USDC value
         **/
        private final double USDC;

        /**
         * {@code TUSD} TUSD value
         **/
        private final double TUSD;

        /**
         * {@code USDP} USDP value
         **/
        private final double USDP;

        /**
         * Constructor to init {@link ExchangeRates} object
         *
         * @param USDC: USDC value
         * @param TUSD: TUSD value
         * @param USDP: USDP value
         **/
        public ExchangeRates(double USDC, double TUSD, double USDP) {
            super(null);
            this.USDC = USDC;
            this.TUSD = TUSD;
            this.USDP = USDP;
        }

        /**
         * Constructor to init {@link ExchangeRates} object
         *
         * @param jExchangeRates: exchange rates details as {@link JSONObject}
         **/
        public ExchangeRates(JSONObject jExchangeRates) {
            super(jExchangeRates);
            USDC = hItem.getDouble("USDC", 0);
            TUSD = hItem.getDouble("TUSD", 0);
            USDP = hItem.getDouble("USDP", 0);
        }

        /**
         * Method to get {@link #USDC} instance <br>
         * No-any params required
         *
         * @return {@link #USDC} instance as double
         **/
        public double getUSDC() {
            return USDC;
        }

        /**
         * Method to get {@link #USDC} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #USDC} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getUSDC(int decimals) {
            return roundValue(USDC, decimals);
        }

        /**
         * Method to get {@link #TUSD} instance <br>
         * No-any params required
         *
         * @return {@link #TUSD} instance as double
         **/
        public double getTUSD() {
            return TUSD;
        }

        /**
         * Method to get {@link #TUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #TUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTUSD(int decimals) {
            return roundValue(TUSD, decimals);
        }

        /**
         * Method to get {@link #USDP} instance <br>
         * No-any params required
         *
         * @return {@link #USDP} instance as double
         **/
        public double getUSDP() {
            return USDP;
        }

        /**
         * Method to get {@link #USDP} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #USDP} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getUSDP(int decimals) {
            return roundValue(USDP, decimals);
        }

    }

}
