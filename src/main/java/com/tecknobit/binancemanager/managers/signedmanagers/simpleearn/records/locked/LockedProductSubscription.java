package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleProductSubscription;
import org.json.JSONObject;

/**
 * The {@code LockedProductSubscription} class is useful to format a {@code Binance}'s locked product subscription
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-locked-product-trade">
 * Subscribe Locked Product (TRADE)</a>
 * @see BinanceItem
 * @see FlexibleProductSubscription
 */
public class LockedProductSubscription extends FlexibleProductSubscription {

    /**
     * {@code positionId} position identifier
     */
    private final long positionId;

    /**
     * Constructor to init a {@link LockedProductSubscription} object
     *
     * @param purchaseId: purchase identifier
     * @param success:    whether the subscription has been successful
     * @param positionId: position identifier
     */
    public LockedProductSubscription(long purchaseId, boolean success, long positionId) {
        super(purchaseId, success);
        this.positionId = positionId;
    }

    /**
     * Constructor to init a {@link LockedProductSubscription} object
     *
     * @param jLockedProductSubscription: locked product subscription details as {@link JSONObject}
     */
    public LockedProductSubscription(JSONObject jLockedProductSubscription) {
        super(jLockedProductSubscription);
        this.positionId = hItem.getLong("positionId", 0);
    }

    /**
     * Method to get {@link #positionId} instance <br>
     * No-any params required
     *
     * @return {@link #positionId} instance as long
     */
    public long getPositionId() {
        return positionId;
    }

}

