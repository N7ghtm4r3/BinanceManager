package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.api;

import org.json.JSONObject;

import static com.tecknobit.apimanager.formatters.JsonHelper.getLong;

/**
 * The {@code APIPermission} class is useful to manage APIPermission Binance request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
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
        tradingAuthorityExpirationTime = getLong(apiPermission, "tradingAuthorityExpirationTime");
    }

    public boolean isIpRestrict() {
        return ipRestrict;
    }

    public void setIpRestrict(boolean ipRestrict) {
        this.ipRestrict = ipRestrict;
    }

    public long getCreateTime() {
        return createTime;
    }

    public boolean isEnableWithdrawals() {
        return enableWithdrawals;
    }

    public void setEnableWithdrawals(boolean enableWithdrawals) {
        this.enableWithdrawals = enableWithdrawals;
    }

    public boolean isEnableInternalTransfer() {
        return enableInternalTransfer;
    }

    public void setEnableInternalTransfer(boolean enableInternalTransfer) {
        this.enableInternalTransfer = enableInternalTransfer;
    }

    public boolean isPermitsUniversalTransfer() {
        return permitsUniversalTransfer;
    }

    public void setPermitsUniversalTransfer(boolean permitsUniversalTransfer) {
        this.permitsUniversalTransfer = permitsUniversalTransfer;
    }

    public boolean isEnableVanillaOptions() {
        return enableVanillaOptions;
    }

    public void setEnableVanillaOptions(boolean enableVanillaOptions) {
        this.enableVanillaOptions = enableVanillaOptions;
    }

    public boolean isEnableReading() {
        return enableReading;
    }

    public void setEnableReading(boolean enableReading) {
        this.enableReading = enableReading;
    }

    public boolean isEnableFutures() {
        return enableFutures;
    }

    public void setEnableFutures(boolean enableFutures) {
        this.enableFutures = enableFutures;
    }

    public boolean isEnableMargin() {
        return enableMargin;
    }

    public void setEnableMargin(boolean enableMargin) {
        this.enableMargin = enableMargin;
    }

    public boolean isEnableSpotAndMarginTrading() {
        return enableSpotAndMarginTrading;
    }

    public void setEnableSpotAndMarginTrading(boolean enableSpotAndMarginTrading) {
        this.enableSpotAndMarginTrading = enableSpotAndMarginTrading;
    }


    /**
     * @implSpec if tradingAuthorityExpirationTime = -1 means that is not set for this api key
     **/
    public long getTradingAuthorityExpirationTime() {
        return tradingAuthorityExpirationTime;
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

    @Override
    public String toString() {
        return "APIPermission{" +
                "ipRestrict=" + ipRestrict +
                ", createTime=" + createTime +
                ", enableWithdrawals=" + enableWithdrawals +
                ", enableInternalTransfer=" + enableInternalTransfer +
                ", permitsUniversalTransfer=" + permitsUniversalTransfer +
                ", enableVanillaOptions=" + enableVanillaOptions +
                ", enableReading=" + enableReading +
                ", enableFutures=" + enableFutures +
                ", enableMargin=" + enableMargin +
                ", enableSpotAndMarginTrading=" + enableSpotAndMarginTrading +
                ", tradingAuthorityExpirationTime=" + tradingAuthorityExpirationTime +
                '}';
    }

}
