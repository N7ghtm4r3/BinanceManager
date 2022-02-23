package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API;

/**
 *  The {@code APIPermission} class is useful to manage APIPermission Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-api-key-permission-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class APIPermission {

    private final boolean ipRestrict;
    private final long createTime;
    private final boolean enableWithdrawals;
    private final boolean enableInternalTransfer;
    private final boolean permitsUniversalTransfer;
    private final boolean enableVanillaOptions;
    private final boolean enableReading;
    private final boolean enableFutures;
    private final boolean enableMargin;
    private final boolean enableSpotAndMarginTrading;
    private final long tradingAuthorityExpirationTime;

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

    public boolean ipRestrict() {
        return ipRestrict;
    }

    public long createTime() {
        return createTime;
    }

    public boolean enableWithdrawals() {
        return enableWithdrawals;
    }

    public boolean enableInternalTransfer() {
        return enableInternalTransfer;
    }

    public boolean permitsUniversalTransfer() {
        return permitsUniversalTransfer;
    }

    public boolean enableVanillaOptions() {
        return enableVanillaOptions;
    }

    public boolean enableReading() {
        return enableReading;
    }

    public boolean enableFutures() {
        return enableFutures;
    }

    public boolean enableMargin() {
        return enableMargin;
    }

    public boolean enableSpotAndMarginTrading() {
        return enableSpotAndMarginTrading;
    }

    public long tradingAuthorityExpirationTime() {
        return tradingAuthorityExpirationTime;
    }

}
