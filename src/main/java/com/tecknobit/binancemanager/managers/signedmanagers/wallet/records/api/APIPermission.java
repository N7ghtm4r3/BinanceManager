package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.api;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.formatters.JsonHelper.getLong;

/**
 * The {@code APIPermission} class is useful to format a {@code "Binance"}'s API permission
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
 * Get API Key Permission (USER_DATA)</a>
 **/
public class APIPermission {

    /**
     * {@code ipRestrict} is instance that memorizes if api has ip restriction
     * **/
    private boolean ipRestrict;

    /**
     * {@code createTime} is instance that memorizes api creation time
     * **/
    private final long createTime;

    /**
     * {@code enableWithdrawals} is instance that memorizes api has withdrawals enabled
     * **/
    private boolean enableWithdrawals;

    /**
     * {@code enableInternalTransfer} is instance that memorizes api has internal transfer enabled
     * **/
    private boolean enableInternalTransfer;

    /**
     * {@code permitsUniversalTransfer} is instance that memorizes api permits universal transfer
     * **/
    private boolean permitsUniversalTransfer;

    /**
     * {@code enableVanillaOptions} is instance that memorizes api has vanilla options enabled
     * **/
    private boolean enableVanillaOptions;

    /**
     * {@code enableReading} is instance that memorizes api has reading option enabled
     * **/
    private boolean enableReading;

    /**
     * {@code enableFutures} is instance that memorizes api has futures option enabled
     * **/
    private boolean enableFutures;

    /**
     * {@code enableMargin} is instance that memorizes api has margin option enabled
     * **/
    private boolean enableMargin;

    /**
     * {@code enableSpotAndMarginTrading} is instance that memorizes api is allowed to spot and margin trading
     * **/
    private boolean enableSpotAndMarginTrading;

    /**
     * {@code tradingAuthorityExpirationTime} is instance that memorizes api trading authority expiration value
     * **/
    private long tradingAuthorityExpirationTime;

    /** Constructor to init {@link APIPermission} object
     * @param ipRestrict: api has ip restriction
     * @param createTime: api creation time
     * @param enableWithdrawals: api has withdrawals enabled
     * @param enableInternalTransfer: api has internal transfer enabled
     * @param permitsUniversalTransfer: api permits universal transfer
     * @param enableVanillaOptions: api has vanilla options enabled
     * @param enableReading: api has reading option enabled
     * @param enableFutures: api has futures option enabled
     * @param enableMargin: api has margin option enabled
     * @param enableSpotAndMarginTrading: api is allowed to spot and margin trading
     * @param tradingAuthorityExpirationTime: api trading authority expiration value
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public APIPermission(boolean ipRestrict, long createTime, boolean enableWithdrawals, boolean enableInternalTransfer,
                         boolean permitsUniversalTransfer, boolean enableVanillaOptions, boolean enableReading,
                         boolean enableFutures, boolean enableMargin, boolean enableSpotAndMarginTrading,
                         long tradingAuthorityExpirationTime) {
        this.ipRestrict = ipRestrict;
        this.createTime = createTime;
        this.enableWithdrawals = enableWithdrawals;
        this.enableInternalTransfer = enableInternalTransfer;
        this.permitsUniversalTransfer = permitsUniversalTransfer;
        this.enableVanillaOptions = enableVanillaOptions;
        this.enableReading = enableReading;
        this.enableFutures = enableFutures;
        this.enableMargin = enableMargin;
        this.enableSpotAndMarginTrading = enableSpotAndMarginTrading;
        this.tradingAuthorityExpirationTime = tradingAuthorityExpirationTime;
    }

    /**
     * Constructor to init {@link APIPermission} object
     *
     * @param apiPermission: api permission details as {@link JSONObject}
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     **/
    public APIPermission(JSONObject apiPermission) {
        ipRestrict = apiPermission.getBoolean("ipRestrict");
        createTime = apiPermission.getLong("createTime");
        enableWithdrawals = apiPermission.getBoolean("enableWithdrawals");
        enableInternalTransfer = apiPermission.getBoolean("enableInternalTransfer");
        permitsUniversalTransfer = apiPermission.getBoolean("permitsUniversalTransfer");
        enableVanillaOptions = apiPermission.getBoolean("enableVanillaOptions");
        enableReading = apiPermission.getBoolean("enableReading");
        enableFutures = apiPermission.getBoolean("enableFutures");
        enableMargin = apiPermission.getBoolean("enableMargin");
        enableSpotAndMarginTrading = apiPermission.getBoolean("enableSpotAndMarginTrading");
        tradingAuthorityExpirationTime = getLong(apiPermission, "tradingAuthorityExpirationTime", -1);
    }

    /**
     * Method to get {@link #ipRestrict} instance <br>
     * No-any params required
     *
     * @return {@link #ipRestrict} instance as boolean
     **/
    public boolean isIpRestrict() {
        return ipRestrict;
    }

    /**
     * Method to set {@link #ipRestrict}
     *
     * @param ipRestrict: whether the IP is restricted
     **/
    public void setIpRestrict(boolean ipRestrict) {
        this.ipRestrict = ipRestrict;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as long
     **/
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as {@link Date}
     **/
    public Date getCreationDate() {
        return TimeFormatter.getDate(createTime);
    }

    /**
     * Method to get {@link #enableWithdrawals} instance <br>
     * No-any params required
     *
     * @return {@link #enableWithdrawals} instance as boolean
     **/
    public boolean areEnabledWithdrawals() {
        return enableWithdrawals;
    }

    /**
     * Method to set {@link #enableWithdrawals}
     *
     * @param enableWithdrawals: whether the withdrawals are enabled
     **/
    public void setEnableWithdrawals(boolean enableWithdrawals) {
        this.enableWithdrawals = enableWithdrawals;
    }

    /**
     * Method to get {@link #enableInternalTransfer} instance <br>
     * No-any params required
     *
     * @return {@link #enableInternalTransfer} instance as boolean
     **/
    public boolean isEnabledInternalTransfer() {
        return enableInternalTransfer;
    }

    /**
     * Method to set {@link #enableInternalTransfer}
     *
     * @param enableInternalTransfer: whether the internal transfers are enabled
     **/
    public void setInternalTransfer(boolean enableInternalTransfer) {
        this.enableInternalTransfer = enableInternalTransfer;
    }

    /**
     * Method to get {@link #permitsUniversalTransfer} instance <br>
     * No-any params required
     *
     * @return {@link #permitsUniversalTransfer} instance as boolean
     **/
    public boolean permitsUniversalTransfer() {
        return permitsUniversalTransfer;
    }

    /**
     * Method to set {@link #permitsUniversalTransfer}
     *
     * @param permitsUniversalTransfer: whether the universal transfers are enabled
     **/
    public void setUniversalTransfer(boolean permitsUniversalTransfer) {
        this.permitsUniversalTransfer = permitsUniversalTransfer;
    }

    /**
     * Method to get {@link #enableVanillaOptions} instance <br>
     * No-any params required
     *
     * @return {@link #enableVanillaOptions} instance as boolean
     **/
    public boolean areEnabledVanillaOptions() {
        return enableVanillaOptions;
    }

    /**
     * Method to set {@link #enableVanillaOptions}
     *
     * @param enableVanillaOptions: whether the vanilla options are enabled
     **/
    public void setVanillaOptions(boolean enableVanillaOptions) {
        this.enableVanillaOptions = enableVanillaOptions;
    }

    /**
     * Method to get {@link #enableReading} instance <br>
     * No-any params required
     *
     * @return {@link #enableReading} instance as boolean
     **/
    public boolean isEnabledReading() {
        return enableReading;
    }

    /**
     * Method to set {@link #enableReading}
     *
     * @param enableReading: whether the reading is enabled
     **/
    public void setReading(boolean enableReading) {
        this.enableReading = enableReading;
    }

    /**
     * Method to get {@link #enableFutures} instance <br>
     * No-any params required
     *
     * @return {@link #enableFutures} instance as boolean
     **/
    public boolean isEnabledFutures() {
        return enableFutures;
    }

    /**
     * Method to set {@link #enableFutures}
     *
     * @param enableFutures: whether the futures options are enabled
     **/
    public void setFutures(boolean enableFutures) {
        this.enableFutures = enableFutures;
    }

    /**
     * Method to get {@link #enableMargin} instance <br>
     * No-any params required
     *
     * @return {@link #enableMargin} instance as boolean
     **/
    public boolean isEnabledMargin() {
        return enableMargin;
    }

    /**
     * Method to set {@link #enableMargin}
     *
     * @param enableMargin: whether the margin options are enabled
     **/
    public void setMargin(boolean enableMargin) {
        this.enableMargin = enableMargin;
    }

    /**
     * Method to get {@link #enableSpotAndMarginTrading} instance <br>
     * No-any params required
     *
     * @return {@link #enableSpotAndMarginTrading} instance as boolean
     **/
    public boolean isEnabledSpotAndMarginTrading() {
        return enableSpotAndMarginTrading;
    }

    /**
     * Method to set {@link #enableSpotAndMarginTrading}
     *
     * @param enableSpotAndMarginTrading: whether the spot and margin trading are enabled
     **/
    public void setSpotAndMarginTrading(boolean enableSpotAndMarginTrading) {
        this.enableSpotAndMarginTrading = enableSpotAndMarginTrading;
    }

    /**
     * Method to get {@link #tradingAuthorityExpirationTime} instance <br>
     * No-any params required
     *
     * @return {@link #tradingAuthorityExpirationTime} instance as long
     * @apiNote if {@code "tradingAuthorityExpirationTime"} = -1 means that is not set for this api key
     **/
    public long getTradingAuthorityExpirationTime() {
        return tradingAuthorityExpirationTime;
    }

    /**
     * Method to get {@link #tradingAuthorityExpirationTime} instance <br>
     * No-any params required
     *
     * @return {@link #tradingAuthorityExpirationTime} instance as {@link Date}
     * @apiNote if {@code "tradingAuthorityExpirationTime"} = -1 will be returned {@code "null"}
     **/
    public Date getTradingAuthorityExpirationDate() {
        if (tradingAuthorityExpirationTime == -1)
            return null;
        return TimeFormatter.getDate(tradingAuthorityExpirationTime);
    }

    /**
     * Method to set {@link #tradingAuthorityExpirationTime}
     *
     * @param tradingAuthorityExpirationTime: api trading authority expiration value
     * @throws IllegalArgumentException when api trading authority expiration value is less than 0
     **/
    public void setTradingAuthorityExpirationTime(long tradingAuthorityExpirationTime) {
        if (tradingAuthorityExpirationTime < 0)
            throw new IllegalArgumentException("Api trading authority expiration value cannot be less than 0");
        this.tradingAuthorityExpirationTime = tradingAuthorityExpirationTime;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
