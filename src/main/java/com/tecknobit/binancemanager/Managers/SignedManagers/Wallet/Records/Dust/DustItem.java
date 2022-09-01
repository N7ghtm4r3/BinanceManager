package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code DustItem} class is useful to obtain and format dust item object
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
 **/

public class DustItem {

    /**
     * {@code transId} is instance that memorizes transaction identifier value
     * **/
    private final long transId;

    /**
     * {@code serviceChargeAmount} is instance that memorizes service charge amount value
     * **/
    private final double serviceChargeAmount;

    /**
     * {@code amount} is instance that memorizes amount value
     * **/
    private final double amount;

    /**
     * {@code operateTime} is instance that memorizes operate time value
     * **/
    private final long operateTime;

    /**
     * {@code transferedAmount} is instance that memorizes transfered amount value
     * **/
    private final double transferedAmount;

    /**
     * {@code fromAsset} is instance that memorizes from asset value
     * **/
    private final String fromAsset;

    /**
     * Constructor to init {@link DustItem} object
     *
     * @param transId:             transaction identifier value
     * @param serviceChargeAmount: service charge amount value
     * @param amount:              amount value
     * @param operateTime:         operate time value
     * @param transferedAmount:    transfered amount value
     * @param fromAsset:           from asset value
     **/
    public DustItem(long transId, double serviceChargeAmount, double amount, long operateTime,
                    double transferedAmount, String fromAsset) {
        this.transId = transId;
        this.serviceChargeAmount = serviceChargeAmount;
        this.amount = amount;
        this.operateTime = operateTime;
        this.transferedAmount = transferedAmount;
        this.fromAsset = fromAsset;
    }

    /**
     * Constructor to init {@link DustItem} object
     *
     * @param dustItem: dust item details as {@link JSONObject}
     **/
    public DustItem(JSONObject dustItem) {
        transId = dustItem.getLong("transId");
        serviceChargeAmount = dustItem.getDouble("serviceChargeAmount");
        amount = dustItem.getDouble("amount");
        operateTime = dustItem.getLong("operateTime");
        transferedAmount = dustItem.getDouble("transferedAmount");
        fromAsset = dustItem.getString("fromAsset");
    }

    /**
     * Method to assemble an {@link DustItem} list
     *
     * @param userAssetDribbletDetails: list of items
     * @return list as {@link ArrayList} of {@link DustItem}
     **/
    public static ArrayList<DustItem> getListDribbletsDetails(JSONArray userAssetDribbletDetails) {
        ArrayList<DustItem> dustItems = new ArrayList<>();
        for (int j = 0; j < userAssetDribbletDetails.length(); j++)
            dustItems.add(new DustItem(userAssetDribbletDetails.getJSONObject(j)));
        return dustItems;
    }

    public long getTransId() {
        return transId;
    }

    public double getServiceChargeAmount() {
        return serviceChargeAmount;
    }

    /**
     * Method to get {@link #serviceChargeAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #serviceChargeAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getServiceChargeAmount(int decimals) {
        return roundValue(serviceChargeAmount, decimals);
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    public long getOperateTime() {
        return operateTime;
    }

    public double getTransferedAmount() {
        return transferedAmount;
    }

    /**
     * Method to get {@link #transferedAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #transferedAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTransferedAmount(int decimals) {
        return roundValue(transferedAmount, decimals);
    }

    public String getFromAsset() {
        return fromAsset;
    }

    @Override
    public String toString() {
        return "DustItem{" +
                "transId=" + transId +
                ", serviceChargeAmount=" + serviceChargeAmount +
                ", amount=" + amount +
                ", operateTime=" + operateTime +
                ", transferedAmount=" + transferedAmount +
                ", fromAsset='" + fromAsset + '\'' +
                '}';
    }

}
