package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubFuturesAccountStructure} class is useful to format a sub futures account structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-detail-on-sub-account-39-s-futures-account-for-master-account">
 *             Get Detail on Sub-account's Futures Account (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-for-master-account">
 *             Get Summary of Sub-account's Futures Account (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public abstract class SubFuturesAccountStructure extends BinanceItem {

    /**
     * {@code totalInitialMargin} total initial margin of the sub futures account structure
     **/
    protected final double totalInitialMargin;

    /**
     * {@code totalMaintenanceMargin} total maintenance margin of the sub futures account structure
     **/
    protected final double totalMaintenanceMargin;

    /**
     * {@code totalMarginBalance} total margin balance of the sub futures account structure
     **/
    protected final double totalMarginBalance;

    /**
     * {@code totalOpenOrderInitialMargin} total open order initial margin of the sub futures account structure
     **/
    protected final double totalOpenOrderInitialMargin;

    /**
     * {@code totalPositionInitialMargin} total position initial margin of the sub futures account structure
     **/
    protected final double totalPositionInitialMargin;

    /**
     * {@code totalUnrealizedProfit} total unrealized profit of the sub futures account structure
     **/
    protected final double totalUnrealizedProfit;

    /**
     * {@code totalWalletBalance} total wallet balance of the sub futures account structure
     **/
    protected final double totalWalletBalance;

    /**
     * Constructor to init {@link SubFuturesAccountStructure} object
     *
     * @param totalInitialMargin:          total initial margin of the sub futures account structure
     * @param totalMaintenanceMargin:      total maintenance margin of the sub futures account structure
     * @param totalMarginBalance:          total margin balance of the sub futures account structure
     * @param totalOpenOrderInitialMargin: total open order initial margin of the sub futures account structure
     * @param totalPositionInitialMargin:  total position initial margin of the sub futures account structure
     * @param totalUnrealizedProfit:       total unrealized profit of the sub futures account structure
     * @param totalWalletBalance:          total wallet balance of the sub futures account structure
     **/
    public SubFuturesAccountStructure(double totalInitialMargin, double totalMaintenanceMargin, double totalMarginBalance,
                                      double totalOpenOrderInitialMargin, double totalPositionInitialMargin,
                                      double totalUnrealizedProfit, double totalWalletBalance) {
        super(null);
        this.totalInitialMargin = totalInitialMargin;
        this.totalMaintenanceMargin = totalMaintenanceMargin;
        this.totalMarginBalance = totalMarginBalance;
        this.totalOpenOrderInitialMargin = totalOpenOrderInitialMargin;
        this.totalPositionInitialMargin = totalPositionInitialMargin;
        this.totalUnrealizedProfit = totalUnrealizedProfit;
        this.totalWalletBalance = totalWalletBalance;
    }

    /**
     * Constructor to init {@link SubFuturesAccountStructure} object
     *
     * @param jSubFuturesAccountStructure: sub futures account structure details as {@link JSONObject}
     **/
    public SubFuturesAccountStructure(JSONObject jSubFuturesAccountStructure) {
        super(jSubFuturesAccountStructure);
        totalInitialMargin = hItem.getDouble("totalInitialMargin", 0);
        totalMaintenanceMargin = hItem.getDouble("totalMaintenanceMargin", 0);
        totalMarginBalance = hItem.getDouble("totalMarginBalance", 0);
        totalOpenOrderInitialMargin = hItem.getDouble("totalOpenOrderInitialMargin", 0);
        totalPositionInitialMargin = hItem.getDouble("totalPositionInitialMargin", 0);
        totalUnrealizedProfit = hItem.getDouble("totalUnrealizedProfit", 0);
        totalWalletBalance = hItem.getDouble("totalWalletBalance", 0);
    }

    /**
     * Method to get {@link #totalInitialMargin} instance <br>
     * No-any params required
     *
     * @return {@link #totalInitialMargin} instance as double
     **/
    public double getTotalInitialMargin() {
        return totalInitialMargin;
    }

    /**
     * Method to get {@link #totalInitialMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalInitialMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalInitialMargin(int decimals) {
        return roundValue(totalInitialMargin, decimals);
    }

    /**
     * Method to get {@link #totalMaintenanceMargin} instance <br>
     * No-any params required
     *
     * @return {@link #totalMaintenanceMargin} instance as double
     **/
    public double getTotalMaintenanceMargin() {
        return totalMaintenanceMargin;
    }

    /**
     * Method to get {@link #totalMaintenanceMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalMaintenanceMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalMaintenanceMargin(int decimals) {
        return roundValue(totalMaintenanceMargin, decimals);
    }

    /**
     * Method to get {@link #totalMarginBalance} instance <br>
     * No-any params required
     *
     * @return {@link #totalMarginBalance} instance as double
     **/
    public double getTotalMarginBalance() {
        return totalMarginBalance;
    }

    /**
     * Method to get {@link #totalMarginBalance} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalMarginBalance} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalMarginBalance(int decimals) {
        return roundValue(totalMarginBalance, decimals);
    }

    /**
     * Method to get {@link #totalOpenOrderInitialMargin} instance <br>
     * No-any params required
     *
     * @return {@link #totalOpenOrderInitialMargin} instance as double
     **/
    public double getTotalOpenOrderInitialMargin() {
        return totalOpenOrderInitialMargin;
    }

    /**
     * Method to get {@link #totalOpenOrderInitialMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalOpenOrderInitialMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalOpenOrderInitialMargin(int decimals) {
        return roundValue(totalOpenOrderInitialMargin, decimals);
    }

    /**
     * Method to get {@link #totalPositionInitialMargin} instance <br>
     * No-any params required
     *
     * @return {@link #totalPositionInitialMargin} instance as double
     **/
    public double getTotalPositionInitialMargin() {
        return totalPositionInitialMargin;
    }

    /**
     * Method to get {@link #totalPositionInitialMargin} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalPositionInitialMargin} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalPositionInitialMargin(int decimals) {
        return roundValue(totalPositionInitialMargin, decimals);
    }

    /**
     * Method to get {@link #totalUnrealizedProfit} instance <br>
     * No-any params required
     *
     * @return {@link #totalUnrealizedProfit} instance as double
     **/
    public double getTotalUnrealizedProfit() {
        return totalUnrealizedProfit;
    }

    /**
     * Method to get {@link #totalUnrealizedProfit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalUnrealizedProfit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalUnrealizedProfit(int decimals) {
        return roundValue(totalUnrealizedProfit, decimals);
    }

    /**
     * Method to get {@link #totalWalletBalance} instance <br>
     * No-any params required
     *
     * @return {@link #totalWalletBalance} instance as double
     **/
    public double getTotalWalletBalance() {
        return totalWalletBalance;
    }

    /**
     * Method to get {@link #totalWalletBalance} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalWalletBalance} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalWalletBalance(int decimals) {
        return roundValue(totalWalletBalance, decimals);
    }

}
