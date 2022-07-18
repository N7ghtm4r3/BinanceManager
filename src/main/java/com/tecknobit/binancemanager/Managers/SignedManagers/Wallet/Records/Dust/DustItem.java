package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code DustItem} class is useful to obtain and format dust item object
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
 * **/

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

    /** Constructor to init {@link DustItem} object
     * @param transId: transaction identifier value
     * @param serviceChargeAmount: service charge amount value
     * @param amount: amount value
     * @param operateTime: operate time value
     * @param transferedAmount: transfered amount value
     * @param fromAsset: from asset value
     * **/
    public DustItem(long transId, double serviceChargeAmount, double amount, long operateTime,
                                 double transferedAmount, String fromAsset) {
        this.transId = transId;
        this.serviceChargeAmount = serviceChargeAmount;
        this.amount = amount;
        this.operateTime = operateTime;
        this.transferedAmount = transferedAmount;
        this.fromAsset = fromAsset;
    }

    public long transId() {
        return transId;
    }

    public double serviceChargeAmount() {
        return serviceChargeAmount;
    }

    public double amount() {
        return amount;
    }

    public long operateTime() {
        return operateTime;
    }

    public double transferedAmount() {
        return transferedAmount;
    }

    public String fromAsset() {
        return fromAsset;
    }

    /** Method to assemble an AssetDribbletsDetails list
     * @param userAssetDribbletDetails: accountDetails obtain by DustLog Binance request
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data</a>
     * @return assetDribbletsDetailsList list as ArrayList<AssetDribbletsDetails>
     * **/
    public static ArrayList<DustItem> getListDribbletsDetails(JSONArray userAssetDribbletDetails) {
        ArrayList<DustItem> dustItems = new ArrayList<>();
        for (int j = 0; j < userAssetDribbletDetails.length(); j++) {
            JSONObject assetDribblet = userAssetDribbletDetails.getJSONObject(j);
            dustItems.add(new DustItem(assetDribblet.getLong("transId"),
                    assetDribblet.getDouble("serviceChargeAmount"),
                    assetDribblet.getDouble("amount"),
                    assetDribblet.getLong("operateTime"),
                    assetDribblet.getDouble("transferedAmount"),
                    assetDribblet.getString("fromAsset")
            ));
        }
        return dustItems;
    }

}
