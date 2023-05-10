package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;

/**
 * The {@code GiftCardStructure} class is useful to create a gift card structure
 *
 * @param <T> data contained by the structure
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
 *             Create a single-token gift card (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
 *             Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
 *             Redeem a Binance Gift Card (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
 *             Verify Binance Gift Card by Gift Card Number (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
 *             Fetch RSA Public Key (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
 *             Fetch Token Limit (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceResponse
 **/
@Structure
public abstract class GiftCardStructure<T> extends BinanceItem implements BinanceResponse {

    /**
     * {@code success} whether the operation has been successful
     **/
    protected final boolean success;

    /**
     * {@code data} of the giftcard
     **/
    protected T data;

    /**
     * Constructor to init {@link GiftCardStructure} object
     *
     * @param success: whether the operation has been successful
     * @param data:    data of the giftcard
     **/
    public GiftCardStructure(boolean success, T data) {
        super(null);
        this.success = success;
        this.data = data;
    }

    /**
     * Constructor to init {@link GiftCardStructure} object
     *
     * @param jGiftCardStructure: giftcard structure details as {@link JSONObject}
     **/
    public GiftCardStructure(JSONObject jGiftCardStructure) {
        super(jGiftCardStructure);
        success = hItem.getBoolean("success");
    }

    /**
     * Method to get {@link #success} instance <br>
     * No-any params required
     *
     * @return {@link #success} instance as boolean
     **/
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method to get {@link #data} instance <br>
     * No-any params required
     *
     * @return {@link #data} instance as {@link T}
     **/
    public T getData() {
        return data;
    }

    /**
     * Method to get error code <br>
     * No-any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     **/
    @Override
    public int getCode() {
        if (hItem != null)
            return hItem.getInt("code", 0);
        return 0;
    }

    /**
     * Method to get error message <br>
     * No-any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     **/
    @Override
    public String getMessage() {
        if (hItem != null)
            return hItem.getString("message");
        return null;
    }

}
