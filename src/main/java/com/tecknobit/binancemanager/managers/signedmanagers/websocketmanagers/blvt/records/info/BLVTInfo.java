package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.info;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTInfo} class is useful to format a BLVT info
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-info-market_data">
 * Get BLVT Info (MARKET_DATA)</a>
 * @see BinanceItem
 */
public class BLVTInfo extends BinanceItem {

    /**
     * {@code tokenName} token name of the BLVT info
     */
    private final String tokenName;

    /**
     * {@code description} of the BLVT info
     */
    private final String description;

    /**
     * {@code underlying} of the BLVT info
     */
    private final String underlying;

    /**
     * {@code tokenIssued} token issued of the BLVT info
     */
    private final double tokenIssued;

    /**
     * {@code basket} of the BLVT info
     */
    private final String basket;

    /**
     * {@code currentBaskets} current baskets of the BLVT info
     */
    private final ArrayList<Basket> currentBaskets;

    /**
     * {@code nav} of the BLVT info
     */
    private final double nav;

    /**
     * {@code realLeverage} real leverage of the BLVT info
     */
    private final double realLeverage;

    /**
     * {@code fundingRate} funding rate of the BLVT info
     */
    private final double fundingRate;

    /**
     * {@code dailyManagementFee} daily management fee of the BLVT info
     */
    private final double dailyManagementFee;

    /**
     * {@code purchaseFeePct} purchase fee pct of the BLVT info
     */
    private final double purchaseFeePct;

    /**
     * {@code dailyPurchaseLimit} daily purchase limit of the BLVT info
     */
    private final double dailyPurchaseLimit;

    /**
     * {@code redeemFeePct} redeem fee pct of the BLVT info
     */
    private final double redeemFeePct;

    /**
     * {@code dailyRedeemLimit} daily redeem limit of the BLVT info
     */
    private final double dailyRedeemLimit;

    /**
     * {@code timestamp} of the BLVT info
     */
    private final long timestamp;

    /**
     * Constructor to init {@link BLVTInfo} object
     *
     * @param tokenName: token name of the BLVT info
     * @param description: description of the BLVT info
     * @param underlying: underlying of the BLVT info
     * @param tokenIssued: token issued name of the BLVT limit info
     * @param basket: basket of the BLVT info
     * @param currentBaskets: current baskets of the BLVT info
     * @param nav: nav of the BLVT info
     * @param realLeverage: real leverage of the BLVT info
     * @param fundingRate: funding rate of the BLVT info
     * @param dailyManagementFee: daily management fee of the BLVT info
     * @param purchaseFeePct: purchase fee pct of the BLVT info
     * @param dailyPurchaseLimit: daily purchase limit of the BLVT info
     * @param redeemFeePct: redeem fee pct of the BLVT info
     * @param dailyRedeemLimit: daily redeem limit of the BLVT info
     * @param timestamp: timestamp of the BLVT info
     */
    public BLVTInfo(String tokenName, String description, String underlying, double tokenIssued, String basket,
                    ArrayList<Basket> currentBaskets, double nav, double realLeverage, double fundingRate,
                    double dailyManagementFee, double purchaseFeePct, double dailyPurchaseLimit, double redeemFeePct,
                    double dailyRedeemLimit, long timestamp) {
        super(null);
        this.tokenName = tokenName;
        this.description = description;
        this.underlying = underlying;
        this.tokenIssued = tokenIssued;
        this.basket = basket;
        this.currentBaskets = currentBaskets;
        this.nav = nav;
        this.realLeverage = realLeverage;
        this.fundingRate = fundingRate;
        this.dailyManagementFee = dailyManagementFee;
        this.purchaseFeePct = purchaseFeePct;
        this.dailyPurchaseLimit = dailyPurchaseLimit;
        this.redeemFeePct = redeemFeePct;
        this.dailyRedeemLimit = dailyRedeemLimit;
        this.timestamp = timestamp;
    }

    /**
     * Constructor to init {@link BLVTInfo} object
     *
     * @param jBLVTInfo: BVLT info details as {@link JSONObject}
     */
    public BLVTInfo(JSONObject jBLVTInfo) {
        super(jBLVTInfo);
        tokenName = hItem.getString("tokenName");
        description = hItem.getString("description");
        underlying = hItem.getString("underlying");
        tokenIssued = hItem.getDouble("tokenIssued", 0);
        basket = hItem.getString("basket");
        currentBaskets = new ArrayList<>();
        for (Object basket : hItem.fetchList("currentBaskets"))
            currentBaskets.add(new Basket((JSONObject) basket));
        nav = hItem.getDouble("nav", 0);
        realLeverage = hItem.getDouble("realLeverage", 0);
        fundingRate = hItem.getDouble("fundingRate", 0);
        dailyManagementFee = hItem.getDouble("dailyManagementFee", 0);
        purchaseFeePct = hItem.getDouble("purchaseFeePct", 0);
        dailyPurchaseLimit = hItem.getDouble("dailyPurchaseLimit", 0);
        redeemFeePct = hItem.getDouble("redeemFeePct", 0);
        dailyRedeemLimit = hItem.getDouble("dailyRedeemLimit", 0);
        timestamp = hItem.getLong("timestamp", 0);
    }

    /**
     * Method to get {@link #tokenName} instance <br>
     * No-any params required
     *
     * @return {@link #tokenName} instance as {@link String}
     */
    public String getTokenName() {
        return tokenName;
    }

    /**
     * Method to get {@link #description} instance <br>
     * No-any params required
     *
     * @return {@link #description} instance as {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get {@link #underlying} instance <br>
     * No-any params required
     *
     * @return {@link #underlying} instance as {@link String}
     */
    public String getUnderlying() {
        return underlying;
    }

    /**
     * Method to get {@link #tokenIssued} instance <br>
     * No-any params required
     *
     * @return {@link #tokenIssued} instance as double
     */
    public double getTokenIssued() {
        return tokenIssued;
    }

    /**
     * Method to get {@link #tokenIssued} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #tokenIssued} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTokenIssued(int decimals) {
        return roundValue(tokenIssued, decimals);
    }

    /**
     * Method to get {@link #basket} instance <br>
     * No-any params required
     *
     * @return {@link #basket} instance as {@link String}
     */
    public String getBasket() {
        return basket;
    }

    /**
     * Method to get {@link #currentBaskets} instance <br>
     * No-any params required
     *
     * @return {@link #currentBaskets} instance as {@link ArrayList} of {@link Basket}
     */
    public ArrayList<Basket> getCurrentBaskets() {
        return currentBaskets;
    }

    /**
     * Method to get {@link #nav} instance <br>
     * No-any params required
     *
     * @return {@link #nav} instance as double
     */
    public double getNav() {
        return nav;
    }

    /**
     * Method to get {@link #nav} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #nav} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getNav(int decimals) {
        return roundValue(nav, decimals);
    }

    /**
     * Method to get {@link #realLeverage} instance <br>
     * No-any params required
     *
     * @return {@link #realLeverage} instance as double
     */
    public double getRealLeverage() {
        return realLeverage;
    }

    /**
     * Method to get {@link #realLeverage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #realLeverage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRealLeverage(int decimals) {
        return roundValue(realLeverage, decimals);
    }

    /**
     * Method to get {@link #fundingRate} instance <br>
     * No-any params required
     *
     * @return {@link #fundingRate} instance as double
     */
    public double getFundingRate() {
        return fundingRate;
    }

    /**
     * Method to get {@link #fundingRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fundingRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getFundingRate(int decimals) {
        return roundValue(fundingRate, decimals);
    }

    /**
     * Method to get {@link #dailyManagementFee} instance <br>
     * No-any params required
     *
     * @return {@link #dailyManagementFee} instance as double
     */
    public double getDailyManagementFee() {
        return dailyManagementFee;
    }

    /**
     * Method to get {@link #dailyManagementFee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyManagementFee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyManagementFee(int decimals) {
        return roundValue(dailyManagementFee, decimals);
    }

    /**
     * Method to get {@link #purchaseFeePct} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseFeePct instance as double
     */
    public double getPurchaseFeePct() {
        return purchaseFeePct;
    }

    /**
     * Method to get {@link #purchaseFeePct} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #purchaseFeePct} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPurchaseFeePct(int decimals) {
        return roundValue(purchaseFeePct, decimals);
    }

    /**
     * Method to get {@link #dailyPurchaseLimit} instance <br>
     * No-any params required
     *
     * @return {@link #dailyPurchaseLimit} instance as double
     */
    public double getDailyPurchaseLimit() {
        return dailyPurchaseLimit;
    }

    /**
     * Method to get {@link #dailyPurchaseLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyPurchaseLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyPurchaseLimit(int decimals) {
        return roundValue(dailyPurchaseLimit, decimals);
    }

    /**
     * Method to get {@link #redeemFeePct} instance <br>
     * No-any params required
     *
     * @return {@link #redeemFeePct} instance as double
     */
    public double getRedeemFeePct() {
        return redeemFeePct;
    }

    /**
     * Method to get {@link #redeemFeePct} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #redeemFeePct} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRedeemFeePct(int decimals) {
        return roundValue(redeemFeePct, decimals);
    }

    /**
     * Method to get {@link #dailyRedeemLimit} instance <br>
     * No-any params required
     *
     * @return {@link #dailyRedeemLimit} instance as double
     */
    public double getDailyRedeemLimit() {
        return dailyRedeemLimit;
    }

    /**
     * Method to get {@link #dailyRedeemLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyRedeemLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getDailyRedeemLimit(int decimals) {
        return roundValue(dailyRedeemLimit, decimals);
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as long
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Method to get {@link #timestamp} instance <br>
     * No-any params required
     *
     * @return {@link #timestamp} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(timestamp);
    }

    /**
     * The {@code Basket} class is useful to format a BLVT basket
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class Basket extends BinanceItem {

        /**
         * {@code symbol} of the basket
         */
        private final String symbol;

        /**
         * {@code amount} of the basket
         */
        private final double amount;

        /**
         * {@code notionalValue} notional value of the basket
         */
        private final double notionalValue;

        /**
         * Constructor to init {@link Basket} object
         *
         * @param symbol:        symbol of the basket
         * @param amount:        amount of the basket
         * @param notionalValue: notional value of the basket
         */
        public Basket(String symbol, double amount, double notionalValue) {
            super(null);
            this.symbol = symbol;
            this.amount = amount;
            this.notionalValue = notionalValue;
        }

        /**
         * Constructor to init {@link Basket} object
         *
         * @param jBasket: basket details as {@link JSONObject}
         */
        public Basket(JSONObject jBasket) {
            super(jBasket);
            symbol = hItem.getString("symbol");
            amount = hItem.getDouble("amount", 0);
            notionalValue = hItem.getDouble("notionalValue", 0);
        }

        /**
         * Method to get {@link #symbol} instance <br>
         * No-any params required
         *
         * @return {@link #symbol} instance as {@link String}
         */
        public String getSymbol() {
            return symbol;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #notionalValue} instance <br>
         * No-any params required
         *
         * @return {@link #notionalValue} instance as double
         */
        public double getNotionalValue() {
            return notionalValue;
        }

        /**
         * Method to get {@link #notionalValue} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #notionalValue} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getNotionalValue(int decimals) {
            return roundValue(notionalValue, decimals);
        }

    }

}
