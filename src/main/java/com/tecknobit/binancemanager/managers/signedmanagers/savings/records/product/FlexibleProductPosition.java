package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.product;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FlexibleProductPosition} class is useful to format a flexible product position
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-flexible-product-position-user_data">
 * Get Flexible Product Position (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingProductStructure
 **/
public class FlexibleProductPosition extends SavingProductStructure {

    /**
     * {@code annualInterestRate} annual interest rate of the flexible product position
     **/
    private final double annualInterestRate;

    /**
     * {@code dailyInterestRate} daily interest rate of the flexible product position
     **/
    private final double dailyInterestRate;

    /**
     * {@code freeAmount} free amount of the flexible product position
     **/
    private final double freeAmount;

    /**
     * {@code productName} product name of the flexible product position
     **/
    private final String productName;

    /**
     * {@code redeemingAmount} redeeming amount of the flexible product position
     **/
    private final double redeemingAmount;

    /**
     * {@code todayPurchaseAmount} today purchase amount of the flexible product position
     **/
    private final double todayPurchaseAmount;

    /**
     * {@code totalAmount} total amount of the flexible product position
     **/
    private final double totalAmount;

    /**
     * {@code totalInterest} total interest of the flexible product position
     **/
    private final double totalInterest;

    /**
     * Constructor to init {@link FlexibleProductPosition} object
     *
     * @param asset:                     asset of the saving product
     * @param tierAnnualInterestRate:    tier annual interest rate of the saving product
     * @param canRedeem:                 whether the saving product can redeem
     * @param productId:                 product id of the saving product
     * @param averageAnnualInterestRate: average annual interest rate of the saving product
     * @param annualInterestRate:        annual interest rate of the flexible product position
     * @param dailyInterestRate:         daily interest rate of the flexible product position
     * @param freeAmount:                free amount of the flexible product position
     * @param productName:               product name of the flexible product position
     * @param redeemingAmount:           redeeming amount of the flexible product position
     * @param todayPurchaseAmount:       today purchase amount of the flexible product position
     * @param totalAmount:               total amount of the flexible product position
     * @param totalInterest:             total interest of the flexible product position
     **/
    public FlexibleProductPosition(String asset, TierAnnualInterestRate tierAnnualInterestRate, boolean canRedeem,
                                   String productId, double averageAnnualInterestRate, double annualInterestRate,
                                   double dailyInterestRate, double freeAmount, String productName, double redeemingAmount,
                                   double todayPurchaseAmount, double totalAmount, double totalInterest) {
        super(asset, tierAnnualInterestRate, canRedeem, productId, averageAnnualInterestRate);
        this.annualInterestRate = annualInterestRate;
        this.dailyInterestRate = dailyInterestRate;
        this.freeAmount = freeAmount;
        this.productName = productName;
        this.redeemingAmount = redeemingAmount;
        this.todayPurchaseAmount = todayPurchaseAmount;
        this.totalAmount = totalAmount;
        this.totalInterest = totalInterest;
    }

    /**
     * Constructor to init {@link FlexibleProductPosition} object
     *
     * @param jFlexibleProductPosition: flexible product position details as {@link JSONObject}
     **/
    public FlexibleProductPosition(JSONObject jFlexibleProductPosition) {
        super(jFlexibleProductPosition);
        annualInterestRate = hItem.getDouble("annualInterestRate", 0);
        dailyInterestRate = hItem.getDouble("dailyInterestRate", 0);
        freeAmount = hItem.getDouble("freeAmount", 0);
        productName = hItem.getString("productName");
        redeemingAmount = hItem.getDouble("redeemingAmount", 0);
        todayPurchaseAmount = hItem.getDouble("todayPurchasedAmount", 0);
        totalAmount = hItem.getDouble("totalAmount", 0);
        totalInterest = hItem.getDouble("totalInterest", 0);
    }

    /**
     * Method to get {@link #annualInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #annualInterestRate} instance as double
     **/
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * Method to get {@link #annualInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #annualInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAnnualInterestRate(int decimals) {
        return roundValue(annualInterestRate, decimals);
    }

    /**
     * Method to get {@link #dailyInterestRate} instance <br>
     * No-any params required
     *
     * @return {@link #dailyInterestRate} instance as double
     **/
    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    /**
     * Method to get {@link #dailyInterestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #dailyInterestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getDailyInterestRate(int decimals) {
        return roundValue(dailyInterestRate, decimals);
    }

    /**
     * Method to get {@link #freeAmount} instance <br>
     * No-any params required
     *
     * @return {@link #freeAmount} instance as double
     **/
    public double getFreeAmount() {
        return freeAmount;
    }

    /**
     * Method to get {@link #freeAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #freeAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFreeAmount(int decimals) {
        return roundValue(freeAmount, decimals);
    }

    /**
     * Method to get {@link #productName} instance <br>
     * No-any params required
     *
     * @return {@link #productName} instance as {@link String}
     **/
    public String getProductName() {
        return productName;
    }

    /**
     * Method to get {@link #redeemingAmount} instance <br>
     * No-any params required
     *
     * @return {@link #redeemingAmount} instance as double
     **/
    public double getRedeemingAmount() {
        return redeemingAmount;
    }

    /**
     * Method to get {@link #redeemingAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #redeemingAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRedeemingAmount(int decimals) {
        return roundValue(redeemingAmount, decimals);
    }

    /**
     * Method to get {@link #todayPurchaseAmount} instance <br>
     * No-any params required
     *
     * @return {@link #todayPurchaseAmount} instance as double
     **/
    public double getTodayPurchaseAmount() {
        return todayPurchaseAmount;
    }

    /**
     * Method to get {@link #todayPurchaseAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #todayPurchaseAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTodayPurchaseAmount(int decimals) {
        return roundValue(todayPurchaseAmount, decimals);
    }

    /**
     * Method to get {@link #totalAmount} instance <br>
     * No-any params required
     *
     * @return {@link #totalAmount} instance as double
     **/
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Method to get {@link #totalAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalAmount(int decimals) {
        return roundValue(totalAmount, decimals);
    }

    /**
     * Method to get {@link #totalInterest} instance <br>
     * No-any params required
     *
     * @return {@link #totalInterest} instance as double
     **/
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * Method to get {@link #totalInterest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalInterest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalInterest(int decimals) {
        return roundValue(totalInterest, decimals);
    }

}
