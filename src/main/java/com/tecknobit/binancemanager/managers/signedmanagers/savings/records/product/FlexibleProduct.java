package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FlexibleProduct} class is useful to format a flexible product
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-list-user_data">
 * Get Flexible Product List (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingProductStructure
 */
public class FlexibleProduct extends SavingProductStructure {

    /**
     * {@code FlexibleProductStatus} list of available flexible product statuses
     */
    public enum FlexibleProductStatus {

        /**
         * {@code ALL} flexible product status
         */
        ALL,

        /**
         * {@code SUBSCRIBABLE} flexible product status
         */
        SUBSCRIBABLE,

        /**
         * {@code UNSUBSCRIBABLE} flexible product status
         */
        UNSUBSCRIBABLE

    }

    /**
     * {@code Featured} list of available featured
     */
    public enum Featured {

        /**
         * {@code TRUE} featured
         */
        TRUE,

        /**
         * {@code ALL} featured
         */
        ALL

    }

    /**
     * {@code canPurchase} whether the flexible product can purchase
     */
    private final boolean canPurchase;

    /**
     * {@code featured} whether the flexible product is featured
     */
    private final boolean featured;

    /**
     * {@code minPurchaseAmount} min purchase amount of the flexible product
     */
    private final double minPurchaseAmount;

    /**
     * {@code purchaseAmount} purchase amount of the flexible product
     */
    private final double purchaseAmount;

    /**
     * {@code status} of the flexible product
     */
    private final SavingStatus status;

    /**
     * {@code upLimit} up limit of the flexible product
     */
    private final double upLimit;

    /**
     * {@code upLimitPerUser} up limit per user of the flexible product
     */
    private final double upLimitPerUser;

    /**
     * Constructor to init {@link FlexibleProduct} object
     *
     * @param asset:                     asset of the saving product
     * @param tierAnnualInterestRate:    tier annual interest rate of the saving product
     * @param canRedeem:                 whether the saving product can redeem
     * @param productId:                 product id of the saving product
     * @param averageAnnualInterestRate: average annual interest rate of the saving product
     * @param canPurchase:               whether the flexible product can purchase
     * @param featured:                  whether the flexible product is featured
     * @param minPurchaseAmount:         min purchase amount of the flexible product
     * @param purchaseAmount:            purchase amount of the flexible product
     * @param status:                    status of the flexible product
     * @param upLimit:                   up limit of the flexible product
     * @param upLimitPerUser:            up limit per user of the flexible product
     */
    public FlexibleProduct(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem, String productId,
                           double averageAnnualInterestRate, boolean canPurchase, boolean featured, double minPurchaseAmount,
                           double purchaseAmount, SavingStatus status, double upLimit, double upLimitPerUser) {
        super(asset, tierAnnualInterestRate, canRedeem, productId, averageAnnualInterestRate);
        this.canPurchase = canPurchase;
        this.featured = featured;
        this.minPurchaseAmount = minPurchaseAmount;
        this.purchaseAmount = purchaseAmount;
        this.status = status;
        this.upLimit = upLimit;
        this.upLimitPerUser = upLimitPerUser;
    }

    /**
     * Constructor to init {@link FlexibleProduct} object
     *
     * @param jFlexibleProduct: flexible product details as {@link JSONObject}
     */
    public FlexibleProduct(JSONObject jFlexibleProduct) {
        super(jFlexibleProduct);
        canPurchase = hItem.getBoolean("canPurchase");
        featured = hItem.getBoolean("featured");
        minPurchaseAmount = hItem.getDouble("minPurchaseAmount", 0);
        purchaseAmount = hItem.getDouble("purchasedAmount", 0);
        status = SavingStatus.valueOf(hItem.getString("status"));
        upLimit = hItem.getDouble("upLimit", 0);
        upLimitPerUser = hItem.getDouble("upLimitPerUser", 0);
    }

    /**
     * Method to get {@link #canPurchase} instance <br>
     * No-any params required
     *
     * @return {@link #canPurchase} instance as boolean
     */
    public boolean canPurchase() {
        return canPurchase;
    }

    /**
     * Method to get {@link #featured} instance <br>
     * No-any params required
     *
     * @return {@link #featured} instance as boolean
     */
    public boolean isFeatured() {
        return featured;
    }

    /**
     * Method to get {@link #minPurchaseAmount} instance <br>
     * No-any params required
     *
     * @return {@link #minPurchaseAmount} instance as double
     */
    public double getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    /**
     * Method to get {@link #minPurchaseAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #minPurchaseAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMinPurchaseAmount(int decimals) {
        return roundValue(minPurchaseAmount, decimals);
    }

    /**
     * Method to get {@link #purchaseAmount} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseAmount} instance as double
     */
    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    /**
     * Method to get {@link #purchaseAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #purchaseAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPurchaseAmount(int decimals) {
        return roundValue(purchaseAmount, decimals);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link SavingStatus}
     */
    public SavingStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #upLimit} instance <br>
     * No-any params required
     *
     * @return {@link #upLimit} instance as double
     */
    public double getUpLimit() {
        return upLimit;
    }

    /**
     * Method to get {@link #upLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #upLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getUpLimit(int decimals) {
        return roundValue(upLimit, decimals);
    }

    /**
     * Method to get {@link #upLimitPerUser} instance <br>
     * No-any params required
     *
     * @return {@link #upLimitPerUser} instance as double
     */
    public double getUpLimitPerUser() {
        return upLimitPerUser;
    }

    /**
     * Method to get {@link #upLimitPerUser} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #upLimitPerUser} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getUpLimitPerUser(int decimals) {
        return roundValue(upLimitPerUser, decimals);
    }

}
