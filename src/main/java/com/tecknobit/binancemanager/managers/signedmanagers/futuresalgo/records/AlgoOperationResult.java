package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class AlgoOperationResult extends BinanceItem implements BinanceManager.BinanceResponse {

    protected final boolean success;

    public AlgoOperationResult(boolean success) {
        super(null);
        this.success = success;
    }

    public AlgoOperationResult(JSONObject jAlgoOperationResult) {
        super(jAlgoOperationResult);
        success = hItem.getBoolean("success");
    }

    public boolean isSuccess() {
        return success;
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
