package com.tecknobit.binancemanager.Managers.Wallet.Records.Asset;

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

    @Override
    public String toString() {
        return "AssetDetail{" +
                "assetName='" + assetName + '\'' +
                ", minWithdrawAmount=" + minWithdrawAmount +
                ", depositStatus=" + depositStatus +
                ", withdrawFee=" + withdrawFee +
                ", withdrawStatus=" + withdrawStatus +
                ", depositTip='" + depositTip + '\'' +
                '}';
    }

}

