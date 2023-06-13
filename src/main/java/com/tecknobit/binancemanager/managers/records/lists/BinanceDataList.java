package com.tecknobit.binancemanager.managers.records.lists;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code BinanceDataList} class is useful to create a {@code "Binance"}'s data list
 *
 * @param <T> type of the item to insert in the list
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 * @see BinanceResponse
 */
@Structure
public abstract class BinanceDataList<T> extends BinanceItem implements BinanceResponse {

    /**
     * {@code total} number of items
     */
    protected final int total;

    /**
     * {@code success} whether the list is created with success
     */
    protected final boolean success;

    /**
     * {@code data} items of the list
     */
    protected final ArrayList<T> data;

    /**
     * Constructor to init {@link BinanceDataList} object
     *
     * @param total:   number of items
     * @param success: whether the list is created with success
     * @param data:    items of the list
     */
    public BinanceDataList(int total, boolean success, ArrayList<T> data) {
        super(null);
        this.total = total;
        this.success = success;
        this.data = data;
    }

    /**
     * Constructor to init {@link BinanceDataList} object
     *
     * @param jBinanceDataList: data list details as {@link JSONObject}
     */
    public BinanceDataList(JSONObject jBinanceDataList) {
        super(jBinanceDataList);
        total = hItem.getInt("total", 0);
        success = hItem.getBoolean("success");
        data = new ArrayList<>();
    }

    /**
     * Method to get {@link #total} instance <br>
     * No-any params required
     *
     * @return {@link #total} instance as int
     */
    public int getTotal() {
        return total;
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
     * @return {@link #data} instance as {@link ArrayList} of {@link T}
     */
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
