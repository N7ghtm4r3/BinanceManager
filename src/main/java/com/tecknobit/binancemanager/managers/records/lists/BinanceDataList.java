package com.tecknobit.binancemanager.managers.records.lists;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class BinanceDataList<T> extends BinanceItem implements BinanceManager.BinanceResponse {

    protected final int total;
    protected final boolean success;
    protected final ArrayList<T> data;

    public BinanceDataList(int total, boolean success, ArrayList<T> data) {
        super(null);
        this.total = total;
        this.success = success;
        this.data = data;
    }

    public BinanceDataList(JSONObject jFiatItem) {
        super(jFiatItem);
        total = hItem.getInt("total", 0);
        success = hItem.getBoolean("success");
        data = new ArrayList<>();
    }

    public int getTotal() {
        return total;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<T> getData() {
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
