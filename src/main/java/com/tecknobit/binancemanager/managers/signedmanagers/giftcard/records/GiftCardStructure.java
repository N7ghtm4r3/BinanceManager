package com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;

public abstract class GiftCardStructure<T> extends BinanceItem implements BinanceResponse {

    protected final boolean success;
    protected T data;

    public GiftCardStructure(boolean success, T data) {
        super(null);
        this.success = success;
        this.data = data;
    }

    public GiftCardStructure(JSONObject jGiftCardResponse) {
        super(jGiftCardResponse);
        success = hItem.getBoolean("success");
    }

    public boolean isSuccess() {
        return success;
    }

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
