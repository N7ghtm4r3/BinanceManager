package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity.SavingActivityStructure.SavingActivityType;

/**
 * The {@code PurchaseRecord} class is useful to format a purchase record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-purchase-record-user_data">
 * Get Purchase Record (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingRecordStructure
 **/
public class PurchaseRecord extends SavingRecordStructure {

    /**
     * {@code lendingType} lending type of the purchase record
     **/
    private final SavingActivityType lendingType;

    /**
     * {@code lot} of the purchase record
     **/
    private final int lot;

    /**
     * {@code productName} product name of the purchase record
     **/
    private final String productName;

    /**
     * {@code purchaseId} purchase id of the purchase record
     **/
    private final long purchaseId;

    /**
     * Constructor to init {@link PurchaseRecord} object
     *
     * @param asset:       asset of the saving purchase record
     * @param amount:      amount of the saving purchase record
     * @param createTime:  create time of the saving purchase record
     * @param status:      status of the saving purchase record
     * @param lendingType: lending type of the purchase record
     * @param lot:         the lot of the purchase record
     * @param productName: product name of the purchase record
     * @param purchaseId:  purchase id of the purchase record
     **/
    public PurchaseRecord(String asset, double amount, long createTime, String status, SavingActivityType lendingType,
                          int lot, String productName, long purchaseId) {
        super(asset, amount, createTime, status);
        this.lendingType = lendingType;
        this.lot = lot;
        this.productName = productName;
        this.purchaseId = purchaseId;
    }

    /**
     * Constructor to init {@link PurchaseRecord} object
     *
     * @param jPurchaseRecord: purchase record details as {@link JSONObject}
     **/
    public PurchaseRecord(JSONObject jPurchaseRecord) {
        super(jPurchaseRecord);
        lendingType = SavingActivityType.valueOf(hItem.getString("lendingType"));
        lot = hItem.getInt("lot", 0);
        productName = hItem.getString("productName");
        purchaseId = hItem.getLong("purchaseId", 0);
    }

    /**
     * Method to get {@link #lendingType} instance <br>
     * No-any params required
     *
     * @return {@link #lendingType} instance as {@link SavingActivityType}
     **/
    public SavingActivityType getLendingType() {
        return lendingType;
    }

    /**
     * Method to get {@link #lot} instance <br>
     * No-any params required
     *
     * @return {@link #lot} instance as int
     **/
    public int getLot() {
        return lot;
    }

    /**
     * Method to get {@link #productName} instance <br>
     * No-any params required
     *
     * @return {@link #productName} instance as {@link SavingActivityType}
     **/
    public String getProductName() {
        return productName;
    }

    /**
     * Method to get {@link #purchaseId} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseId} instance as long
     **/
    public long getPurchaseId() {
        return purchaseId;
    }

}
