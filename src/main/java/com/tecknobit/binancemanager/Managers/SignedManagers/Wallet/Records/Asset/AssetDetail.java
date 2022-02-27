package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

/**
 * The {@code AssetDetail} class is useful to manage AssetDetail Binance request
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data
 * **/

public class AssetDetail {

    private final String assetName;
    private final double minWithdrawAmount;
    private final boolean depositStatus;
    private final double withdrawFee;
    private final boolean withdrawStatus;
    private final String depositTip;

    public AssetDetail(String assetName, double minWithdrawAmount, boolean depositStatus, double withdrawFee,
                       boolean withdrawStatus, String depositTip) {
        this.assetName = assetName;
        this.minWithdrawAmount = minWithdrawAmount;
        this.depositStatus = depositStatus;
        this.withdrawFee = withdrawFee;
        this.withdrawStatus = withdrawStatus;
        this.depositTip = depositTip;
    }

    public String getAssetName() {
        return assetName;
    }

    public double getMinWithdrawAmount() {
        return minWithdrawAmount;
    }

    public boolean isDepositStatus() {
        return depositStatus;
    }

    public double getWithdrawFee() {
        return withdrawFee;
    }

    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    public String getDepositTip() {
        return depositTip;
    }

}

