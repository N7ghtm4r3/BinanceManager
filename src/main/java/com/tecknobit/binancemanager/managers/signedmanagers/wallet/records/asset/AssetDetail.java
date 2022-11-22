package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code AssetDetail} class is useful to format a {@code "Binance"} asset details
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#asset-detail-user_data">
 * Asset Detail (USER_DATA)</a>
 **/
public class AssetDetail {

    /**
     * {@code assetName} is instance that memorizes asset name
     * **/
    private final String assetName;

    /**
     * {@code minWithdrawAmount} is instance that memorizes minimum withdraw amount value
     **/
    private final double minWithdrawAmount;

    /**
     * {@code depositStatus} is instance that memorizes status of deposits
     * **/
    private final boolean depositStatus;

    /**
     * {@code withdrawFee} is instance that memorizes withdraw fee amount value
     * **/
    private final double withdrawFee;

    /**
     * {@code withdrawStatus} is instance that memorizes withdraw status
     * **/
    private final boolean withdrawStatus;

    /**
     * {@code depositTip} is instance that memorizes deposit tip
     * **/
    private final String depositTip;

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
        if (withdrawFee < 0)
            throw new IllegalArgumentException("Withdraw fee value cannot be less than 0");
        else
            this.withdrawFee = withdrawFee;
        this.withdrawStatus = withdrawStatus;
        this.depositTip = depositTip;
    }

    /**
     * Constructor to init {@link AssetDetail} object
     *
     * @param assetDetail: asset details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    // TODO: 22/11/2022 REMOVE ASSET NAME
    public AssetDetail(String assetName, JSONObject assetDetail) {
        this.assetName = assetName;
        minWithdrawAmount = assetDetail.getDouble("minWithdrawAmount");
        if (minWithdrawAmount < 0)
            throw new IllegalArgumentException("Min withdraw amount value cannot be less than 0");
        depositStatus = assetDetail.getBoolean("depositStatus");
        withdrawFee = assetDetail.getDouble("withdrawFee");
        if (withdrawFee < 0)
            throw new IllegalArgumentException("Withdraw fee value cannot be less than 0");
        withdrawStatus = assetDetail.getBoolean("withdrawStatus");
        depositTip = JsonHelper.getString(assetDetail, "depositTip", "No tip");
    }

    /**
     * Method to get {@link #assetName} instance <br>
     * Any params required
     *
     * @return {@link #assetName} instance as {@link String}
     **/
    public String getAssetName() {
        return assetName;
    }

    /**
     * Method to get {@link #minWithdrawAmount} instance <br>
     * Any params required
     *
     * @return {@link #minWithdrawAmount} instance as double
     **/
    public double getMinWithdrawAmount() {
        return minWithdrawAmount;
    }

    /**
     * Method to get {@link #minWithdrawAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #minWithdrawAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMinWithdrawAmount(int decimals) {
        return roundValue(minWithdrawAmount, decimals);
    }

    /**
     * Method to get {@link #depositStatus} instance <br>
     * Any params required
     *
     * @return {@link #depositStatus} instance as boolean
     **/
    public boolean isDepositStatus() {
        return depositStatus;
    }

    /**
     * Method to get {@link #withdrawFee} instance <br>
     * Any params required
     *
     * @return {@link #withdrawFee} instance as double
     **/
    public double getWithdrawFee() {
        return withdrawFee;
    }

    /**
     * Method to get {@link #withdrawFee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #withdrawFee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getWithdrawFee(int decimals) {
        return roundValue(withdrawFee, decimals);
    }

    /**
     * Method to get {@link #withdrawStatus} instance <br>
     * Any params required
     *
     * @return {@link #withdrawStatus} instance as boolean
     **/
    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    /**
     * Method to get {@link #depositTip} instance <br>
     * Any params required
     *
     * @return {@link #depositTip} instance as {@link String}
     **/
    public String getDepositTip() {
        return depositTip;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}

