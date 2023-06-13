package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.liability;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SmallLiabilityExchangeCoin} class is useful to format a {@code "Binance"}'s small liability exchange coin
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-small-liability-exchange-coin-list-user_data">
 * Get Small Liability Exchange Coin List (USER_DATA)</a>
 * @see BinanceItem
 */
public class SmallLiabilityExchangeCoin extends BinanceItem {

    /**
     * {@code asset} of the coin
     */
    private final String asset;

    /**
     * {@code interest} of the coin
     */
    private final double interest;

    /**
     * {@code principal} of the coin
     */
    private final double principal;

    /**
     * {@code liabilityOfBUSD} liability of BUSD of the coin
     */
    private final double liabilityOfBUSD;

    /**
     * Constructor to init {@link SmallLiabilityExchangeCoin}
     *
     * @param asset           : asset of the coin
     * @param interest        : interest of the coin
     * @param principal       : principal of the coin
     * @param liabilityOfBUSD : liability of BUSD of the coin
     */
    public SmallLiabilityExchangeCoin(String asset, double interest, double principal, double liabilityOfBUSD) {
        super(null);
        this.asset = asset;
        this.interest = interest;
        this.principal = principal;
        this.liabilityOfBUSD = liabilityOfBUSD;
    }

    /**
     * Constructor to init {@link SmallLiabilityExchangeCoin}
     *
     * @param jSmallLiabilityExchangeCoin : small liability exchange coin details as {@link JSONObject}
     */
    public SmallLiabilityExchangeCoin(JSONObject jSmallLiabilityExchangeCoin) {
        super(jSmallLiabilityExchangeCoin);
        asset = hItem.getString("asset");
        interest = hItem.getDouble("interest", 0);
        principal = hItem.getDouble("principal", 0);
        liabilityOfBUSD = hItem.getDouble("liabilityOfBUSD", 0);
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
     * Method to get {@link #interest} instance <br>
     * No-any params required
     *
     * @return {@link #interest} instance as double
     */
    public double getInterest() {
        return interest;
    }

    /**
     * Method to get {@link #interest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getInterest(int decimals) {
        return roundValue(interest, decimals);
    }

    /**
     * Method to get {@link #principal} instance <br>
     * No-any params required
     *
     * @return {@link #principal} instance as double
     */
    public double getPrincipal() {
        return principal;
    }

    /**
     * Method to get {@link #principal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #principal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPrincipal(int decimals) {
        return roundValue(principal, decimals);
    }

    /**
     * Method to get {@link #liabilityOfBUSD} instance <br>
     * No-any params required
     *
     * @return {@link #liabilityOfBUSD} instance as double
     */
    public double getLiabilityOfBUSD() {
        return liabilityOfBUSD;
    }

    /**
     * Method to get {@link #liabilityOfBUSD} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #liabilityOfBUSD} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getLiabilityOfBUSD(int decimals) {
        return roundValue(liabilityOfBUSD, decimals);
    }

}
