package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.info;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTLimitInfo} class is useful to format a BLVT limit info
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-blvt-user-limit-info-user_data">
 * Get BLVT User Limit Info (USER_DATA)</a>
 * @see BinanceItem
 */
public class BLVTLimitInfo extends BinanceItem {

    /**
     * {@code tokenName} token name of the BLVT limit info
     */
    private final String tokenName;

    /**
     * {@code userDailyTotalPurchaseLimit} user daily total purchase limit of the BLVT limit info
     */
    private final double userDailyTotalPurchaseLimit;

    /**
     * {@code userDailyTotalRedeemLimit} user daily total redeem limit of the BLVT limit info
     */
    private final double userDailyTotalRedeemLimit;

    /**
     * Constructor to init {@link BLVTLimitInfo} object
     *
     * @param tokenName:                   token name of the BLVT limit info
     * @param userDailyTotalPurchaseLimit: user daily total purchase limit of the BLVT limit info
     * @param userDailyTotalRedeemLimit:   user daily total redeem limit of the BLVT limit info
     */
    public BLVTLimitInfo(String tokenName, double userDailyTotalPurchaseLimit, double userDailyTotalRedeemLimit) {
        super(null);
        this.tokenName = tokenName;
        this.userDailyTotalPurchaseLimit = userDailyTotalPurchaseLimit;
        this.userDailyTotalRedeemLimit = userDailyTotalRedeemLimit;
    }

    /**
     * Constructor to init {@link BLVTLimitInfo} object
     *
     * @param jBLVTLimitInfo: BLVT limit info details as {@link JSONObject}
     */
    public BLVTLimitInfo(JSONObject jBLVTLimitInfo) {
        super(jBLVTLimitInfo);
        tokenName = hItem.getString("tokenName");
        userDailyTotalPurchaseLimit = hItem.getDouble("userDailyTotalPurchaseLimit", 0);
        userDailyTotalRedeemLimit = hItem.getDouble("userDailyTotalRedeemLimit", 0);
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
     * Method to get {@link #userDailyTotalPurchaseLimit} instance <br>
     * No-any params required
     *
     * @return {@link #userDailyTotalPurchaseLimit} instance as double
     */
    public double getUserDailyTotalPurchaseLimit() {
        return userDailyTotalPurchaseLimit;
    }

    /**
     * Method to get {@link #userDailyTotalPurchaseLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #userDailyTotalPurchaseLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getUserDailyTotalPurchaseLimit(int decimals) {
        return roundValue(userDailyTotalPurchaseLimit, decimals);
    }

    /**
     * Method to get {@link #userDailyTotalRedeemLimit} instance <br>
     * No-any params required
     *
     * @return {@link #userDailyTotalRedeemLimit} instance as double
     */
    public double getUserDailyTotalRedeemLimit() {
        return userDailyTotalRedeemLimit;
    }

    /**
     * Method to get {@link #userDailyTotalRedeemLimit} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #userDailyTotalRedeemLimit} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getUserDailyTotalRedeemLimit(int decimals) {
        return roundValue(userDailyTotalRedeemLimit, decimals);
    }

}
