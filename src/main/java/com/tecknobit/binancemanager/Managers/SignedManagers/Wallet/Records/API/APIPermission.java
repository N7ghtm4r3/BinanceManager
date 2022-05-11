package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API;

/**
 *  The {@code APIPermission} class is useful to manage APIPermission Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data">https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class APIPermission {

    private boolean ipRestrict;
    private final long createTime;
    private boolean enableWithdrawals;
    private boolean enableInternalTransfer;
    private boolean permitsUniversalTransfer;
    private boolean enableVanillaOptions;
    private boolean enableReading;
    private boolean enableFutures;
    private boolean enableMargin;
    private boolean enableSpotAndMarginTrading;
    private long tradingAuthorityExpirationTime;

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

    public long getTradingAuthorityExpirationTime() {
        return tradingAuthorityExpirationTime;
    }

    public void setTradingAuthorityExpirationTime(long tradingAuthorityExpirationTime) {
        this.tradingAuthorityExpirationTime = tradingAuthorityExpirationTime;
    }

}
