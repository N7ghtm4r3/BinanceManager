package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.account.futures.coin;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubCoinStructure} class is useful to format a sub coin structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-summary-of-sub-account-39-s-futures-account-v2-for-master-account">
 * Get Summary of Sub-account's Futures Account V2 (For Master Account)</a>
 * @see BinanceItem
 */
@Structure
public abstract class SubCoinStructure extends BinanceItem {

    /**
     * {@code totalMarginBalanceOfBTC} total margin balance of BTC of the sub coin
     */
    protected final double totalMarginBalanceOfBTC;

    /**
     * {@code totalUnrealizedProfitOfBTC} total unrealized profit of BTC of the sub coin
     */
    protected final double totalUnrealizedProfitOfBTC;

    /**
     * {@code totalWalletBalanceOfBTC} total wallet balance of BTC of the sub coin
     */
    protected final double totalWalletBalanceOfBTC;

    /**
     * {@code asset} of the sub coin
     */
    protected final String asset;

    /**
     * Constructor to init {@link SubCoinStructure} object
     *
     * @param totalMarginBalanceOfBTC:    total margin balance of BTC of the sub coin
     * @param totalUnrealizedProfitOfBTC: total unrealized profit of BTC of the sub coin
     * @param totalWalletBalanceOfBTC:    total wallet balance of BTC of the sub coin
     * @param asset:                      asset of the sub coin
     */
    public SubCoinStructure(double totalMarginBalanceOfBTC, double totalUnrealizedProfitOfBTC,
                            double totalWalletBalanceOfBTC, String asset) {
        super(null);
        this.totalMarginBalanceOfBTC = totalMarginBalanceOfBTC;
        this.totalUnrealizedProfitOfBTC = totalUnrealizedProfitOfBTC;
        this.totalWalletBalanceOfBTC = totalWalletBalanceOfBTC;
        this.asset = asset;
    }

    /**
     * Constructor to init {@link SubCoinStructure} object
     *
     * @param jSubCoinStructure: sub coin structure details as {@link JSONObject}
     */
    public SubCoinStructure(JSONObject jSubCoinStructure) {
        super(jSubCoinStructure);
        totalMarginBalanceOfBTC = hItem.getDouble("totalMarginBalanceOfBTC", 0);
        totalUnrealizedProfitOfBTC = hItem.getDouble("totalUnrealizedProfitOfBTC", 0);
        totalWalletBalanceOfBTC = hItem.getDouble("totalWalletBalanceOfBTC", 0);
        asset = hItem.getString("asset");
    }

    /**
     * Method to get {@link #totalMarginBalanceOfBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalMarginBalanceOfBTC} instance as double
     */
    public double getTotalMarginBalanceOfBTC() {
        return totalMarginBalanceOfBTC;
    }

    /**
     * Method to get {@link #totalMarginBalanceOfBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalMarginBalanceOfBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalMarginBalanceOfBTC(int decimals) {
        return roundValue(totalMarginBalanceOfBTC, decimals);
    }

    /**
     * Method to get {@link #totalUnrealizedProfitOfBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalUnrealizedProfitOfBTC} instance as double
     */
    public double getTotalUnrealizedProfitOfBTC() {
        return totalUnrealizedProfitOfBTC;
    }

    /**
     * Method to get {@link #totalUnrealizedProfitOfBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalUnrealizedProfitOfBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalUnrealizedProfitOfBTC(int decimals) {
        return roundValue(totalUnrealizedProfitOfBTC, decimals);
    }

    /**
     * Method to get {@link #totalWalletBalanceOfBTC} instance <br>
     * No-any params required
     *
     * @return {@link #totalWalletBalanceOfBTC} instance as double
     */
    public double getTotalWalletBalanceOfBTC() {
        return totalWalletBalanceOfBTC;
    }

    /**
     * Method to get {@link #totalWalletBalanceOfBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalWalletBalanceOfBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalWalletBalanceOfBTC(int decimals) {
        return roundValue(totalWalletBalanceOfBTC, decimals);
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

}
