package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

/**
 * The {@code AssetDetail} class is useful to manage AssetDetail Binance request
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
 *     https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data</a>
 * **/

public class AssetDetail {

    /**
     * {@code assetName} is instance that memorizes asset name
     * **/
    private final String assetName;

    /**
     * {@code minWithdrawAmount} is instance that memorizes minimum withdraw amount value
     * **/
    private double minWithdrawAmount;

    /**
     * {@code depositStatus} is instance that memorizes status of deposits
     * **/
    private boolean depositStatus;

    /**
     * {@code withdrawFee} is instance that memorizes withdraw fee amount value
     * **/
    private double withdrawFee;

    /**
     * {@code withdrawStatus} is instance that memorizes withdraw status
     * **/
    private boolean withdrawStatus;

    /**
     * {@code depositTip} is instance that memorizes deposit tip
     * **/
    private String depositTip;

    /** Constructor to init {@link AssetDetail} object
     * @param assetName: asset name
     * @param minWithdrawAmount: minimum withdraw amount value
     * @param depositStatus: status of deposits
     * @param withdrawFee: withdraw fee amount value
     * @param withdrawStatus: withdraw status
     * @param depositTip: deposit tip
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public AssetDetail(String assetName, double minWithdrawAmount, boolean depositStatus, double withdrawFee,
                       boolean withdrawStatus, String depositTip) {
        this.assetName = assetName;
        if(minWithdrawAmount < 0)
            throw new IllegalArgumentException("Min withdraw amount value cannot be less than 0");
        else
            this.minWithdrawAmount = minWithdrawAmount;
        this.depositStatus = depositStatus;
        if(withdrawFee < 0)
            throw new IllegalArgumentException("Withdraw fee value cannot be less than 0");
        else
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

    /** Method to set {@link #minWithdrawAmount}
     * @param minWithdrawAmount: minimum withdraw amount value
     * @throws IllegalArgumentException when minimum withdraw amount value is less than 0
     * **/
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

    /** Method to set {@link #withdrawFee}
     * @param withdrawFee: withdraw fee amount value
     * @throws IllegalArgumentException when withdraw fee amount value is less than 0
     * **/
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

