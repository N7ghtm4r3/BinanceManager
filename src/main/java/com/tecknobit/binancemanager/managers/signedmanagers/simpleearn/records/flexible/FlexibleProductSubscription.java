package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code FlexibleProductSubscription} class is useful to format a {@code Binance}'s flexible product subscription
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-flexible-product-trade">
 * Subscribe Flexible Product (TRADE)</a>
 * @see BinanceItem
 */
public class FlexibleProductSubscription extends BinanceItem {

    /**
     * {@code purchaseId} purchase identifier
     */
    protected final long purchaseId;

    /**
     * {@code success} whether the subscription has been successful
     */
    protected final boolean success;

    /**
     * Constructor to init a {@link FlexibleProductSubscription} object
     *
     * @param purchaseId: purchase identifier
     * @param success:    whether the subscription has been successful
     */
    public FlexibleProductSubscription(long purchaseId, boolean success) {
        super(null);
        this.purchaseId = purchaseId;
        this.success = success;
    }

    /**
     * Constructor to init a {@link FlexibleProductSubscription} object
     *
     * @param jFlexibleProductSubscription: flexible product subscription details as {@link JSONObject}
     */
    public FlexibleProductSubscription(JSONObject jFlexibleProductSubscription) {
        super(jFlexibleProductSubscription);
        purchaseId = hItem.getLong("purchaseId", 0);
        success = hItem.getBoolean("success");
    }

    /**
     * Method to get {@link #purchaseId} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseId} instance as long
     */
    public long getPurchaseId() {
        return purchaseId;
    }

    /**
     * Method to get {@link #success} instance <br>
     * No-any params required
     *
     * @return {@link #success} instance as boolean
     */
    public boolean isSuccess() {
        return success;
    }

}
