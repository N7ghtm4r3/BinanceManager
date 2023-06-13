package com.tecknobit.binancemanager.managers.records;

import com.tecknobit.apimanager.annotations.Structure;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;

/**
 * The {@code BinanceResponseStructure} class is useful to create a base response structure
 *
 * @param <T> data contained by the structure
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 * @see BinanceResponse
 */
@Structure
public class BinanceResponseStructure<T> extends BinanceItem implements BinanceResponse {

    /**
     * {@code success} whether the operation has been successful
     */
    protected final boolean success;

    /**
     * {@code data} of the giftcard
     */
    protected T data;

    /**
     * Constructor to init {@link BinanceResponseStructure} object
     *
     * @param success: whether the operation has been successful
     * @param data:    data of the giftcard
     */
    public BinanceResponseStructure(boolean success, T data) {
        super(null);
        this.success = success;
        this.data = data;
    }

    /**
     * Constructor to init {@link BinanceResponseStructure} object
     *
     * @param jGiftCardStructure: giftcard structure details as {@link JSONObject}
     */
    public BinanceResponseStructure(JSONObject jGiftCardStructure) {
        super(jGiftCardStructure);
        success = hItem.getBoolean("success");
        data = (T) hItem.get("data");
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

    /**
     * Method to get {@link #data} instance <br>
     * No-any params required
     *
     * @return {@link #data} instance as {@link T}
     */
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
     */
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
     */
    @Override
    public String getMessage() {
        if (hItem != null)
            return hItem.getString("message");
        return null;
    }

}
