package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

/**
 * The {@code AssetDetail} class is useful to manage AssetDetail Binance request
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
 * **/

public class AssetDetail {

    private final String assetName;
    private double minWithdrawAmount;
    private boolean depositStatus;
    private double withdrawFee;
    private boolean withdrawStatus;
    private String depositTip;

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

    public void setMinWithdrawAmount(double minWithdrawAmount) {
        if(minWithdrawAmount < 0)
            throw new IllegalArgumentException("Min withdraw amount value cannot be less than 0");
        this.minWithdrawAmount = minWithdrawAmount;
    }

    public boolean isDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(boolean depositStatus) {
        this.depositStatus = depositStatus;
    }

    public double getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(double withdrawFee) {
        if(withdrawFee < 0)
            throw new IllegalArgumentException("Withdraw fee value cannot be less than 0");
        this.withdrawFee = withdrawFee;
    }

    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(boolean withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getDepositTip() {
        return depositTip;
    }

    public void setDepositTip(String depositTip) {
        this.depositTip = depositTip;
    }

}

