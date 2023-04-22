package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.account;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code MarginAccount} class is useful to format a {@code "Binance"} margin account
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
 *             Query Isolated Margin Account Info (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 *             Query Cross Margin Account Details (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 *             Daily Account Snapshot (USER_DATA)</a>
 *     </li>
 * </ul>
 **/
public class MarginAccount extends BinanceItem {

    /**
     * {@code totalAssetOfBtc} is instance that memorizes total asset of Bitcoin
     **/
    protected final double totalAssetOfBtc;

    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total liability of Bitcoin
     **/
    protected final double totalLiabilityOfBtc;

    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total net asset of Bitcoin
     **/
    protected final double totalNetAssetOfBtc;

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param totalAssetOfBtc:     total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc:  total net asset of Bitcoin
     **/
    public MarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc) {
        super(null);
        this.totalAssetOfBtc = totalAssetOfBtc;
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    }

    /**
     * Constructor to init {@link MarginAccount} object
     *
     * @param marginAccount: margin account details as {@link JSONObject}
     **/
    public MarginAccount(JSONObject marginAccount) {
        super(marginAccount);
        totalAssetOfBtc = hItem.getDouble("totalAssetOfBtc", 0);
        totalLiabilityOfBtc = hItem.getDouble("totalLiabilityOfBtc", 0);
        totalNetAssetOfBtc = hItem.getDouble("totalNetAssetOfBtc", 0);
    }

    /**
     * Method to get {@link #totalAssetOfBtc} instance <br>
     * No-any params required
     *
     * @return {@link #totalAssetOfBtc} instance as double
     **/
    public double getTotalAssetOfBtc() {
        return totalAssetOfBtc;
    }

    /**
     * Method to get {@link #totalAssetOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalAssetOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalAssetOfBtc(int decimals) {
        return roundValue(totalAssetOfBtc, decimals);
    }

    /**
     * Method to get {@link #totalLiabilityOfBtc} instance <br>
     * No-any params required
     *
     * @return {@link #totalLiabilityOfBtc} instance as double
     **/
    public double getTotalLiabilityOfBtc() {
        return totalLiabilityOfBtc;
    }

    /**
     * Method to get {@link #totalLiabilityOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalLiabilityOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalLiabilityOfBtc(int decimals) {
        return roundValue(totalLiabilityOfBtc, decimals);
    }

    /**
     * Method to get {@link #totalNetAssetOfBtc} instance <br>
     * No-any params required
     *
     * @return {@link #totalNetAssetOfBtc} instance as double
     **/
    public double getTotalNetAssetOfBtc() {
        return totalNetAssetOfBtc;
    }

    /**
     * Method to get {@link #totalNetAssetOfBtc} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalNetAssetOfBtc} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalNetAssetOfBtc(int decimals) {
        return roundValue(totalNetAssetOfBtc, decimals);
    }

}
