package com.tecknobit.binancemanager.managers.signedmanagers.mining.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class MiningResponse<T> extends BinanceItem implements BinanceResponse {

    public enum HashRateType {

        H_hashrate,
        D_hashrate

    }

    protected T data;

    public MiningResponse(T data) {
        super(null);
        this.data = data;
    }

    public MiningResponse(JSONObject jMiningResponse) {
        super(jMiningResponse);
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
            return hItem.getString("msg");
        return null;
    }

}
