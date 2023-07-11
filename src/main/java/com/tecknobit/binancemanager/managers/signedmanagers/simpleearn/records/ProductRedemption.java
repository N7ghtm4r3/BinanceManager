package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code ProductRedemption} class is useful to format a {@code Binance}'s product redemption
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-flexible-product-trade">
 *             Redeem Flexible Product (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-locked-product-trade">
 *             Redeem Locked Product (TRADE)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class ProductRedemption extends BinanceItem {

    /**
     * {@code redeemId} redemption identifier
     */
    private final long redeemId;

    /**
     * {@code success} whether the redemption has been successful
     */
    private final boolean success;

    /**
     * Constructor to init a {@link ProductRedemption} object
     *
     * @param redeemId: redemption identifier
     * @param success:  whether the redemption has been successful
     */
    public ProductRedemption(long redeemId, boolean success) {
        super(null);
        this.redeemId = redeemId;
        this.success = success;
    }

    /**
     * Constructor to init a {@link ProductRedemption} object
     *
     * @param jProductRedemption: product redemption details as {@link JSONObject}
     */
    public ProductRedemption(JSONObject jProductRedemption) {
        super(jProductRedemption);
        redeemId = hItem.getLong("redeemId", 0);
        success = hItem.getBoolean("success");
    }

    /**
     * Method to get {@link #redeemId} instance <br>
     * No-any params required
     *
     * @return {@link #redeemId} instance as long
     */
    public long getRedeemId() {
        return redeemId;
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
