package com.tecknobit.binancemanager.Managers.Wallet.Records.API;

public record APIPermission (boolean ipRestrict, long createTime, boolean enableWithdrawals, boolean enableInternalTransfer,
                             boolean permitsUniversalTransfer, boolean enableVanillaOptions, boolean enableReading,
                             boolean enableFutures, boolean enableMargin, boolean enableSpotAndMarginTrading,
                             long tradingAuthorityExpirationTime) {

    @Override
    public boolean ipRestrict() {
        return ipRestrict;
    }

    @Override
    public long createTime() {
        return createTime;
    }

    @Override
    public boolean enableWithdrawals() {
        return enableWithdrawals;
    }

    @Override
    public boolean enableInternalTransfer() {
        return enableInternalTransfer;
    }

    @Override
    public boolean permitsUniversalTransfer() {
        return permitsUniversalTransfer;
    }

    @Override
    public boolean enableVanillaOptions() {
        return enableVanillaOptions;
    }

    @Override
    public boolean enableReading() {
        return enableReading;
    }

    @Override
    public boolean enableFutures() {
        return enableFutures;
    }

    @Override
    public boolean enableMargin() {
        return enableMargin;
    }

    @Override
    public boolean enableSpotAndMarginTrading() {
        return enableSpotAndMarginTrading;
    }

    @Override
    public long tradingAuthorityExpirationTime() {
        return tradingAuthorityExpirationTime;
    }

}
