package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code PurchaseStakingProductResult} class is useful to format a purchase staking product result
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#purchase-staking-product-user_data">
 * Purchase Staking Product(USER_DATA)</a>
 * @see BinanceItem
 */
public class PurchaseStakingProductResult extends BinanceItem {

    /**
     * {@code positionId} position id of the purchase result
     */
    private final long positionId;

    /**
     * {@code success} whether the purchase result has been successful
     */
    private final boolean success;

    /**
     * Constructor to init {@link PurchaseStakingProductResult} object
     *
     * @param positionId: position id of the purchase result
     * @param success:    whether the purchase result has been successful
     */
    public PurchaseStakingProductResult(long positionId, boolean success) {
        super(null);
        this.positionId = positionId;
        this.success = success;
    }

    /**
     * Constructor to init {@link PurchaseStakingProductResult} object
     *
     * @param jPurchaseStakingProductResult: staking product purchase result details as {@link JSONObject}
     */
    public PurchaseStakingProductResult(JSONObject jPurchaseStakingProductResult) {
        super(jPurchaseStakingProductResult);
        positionId = hItem.getLong("positionId", 0);
        success = hItem.getBoolean("success");
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
