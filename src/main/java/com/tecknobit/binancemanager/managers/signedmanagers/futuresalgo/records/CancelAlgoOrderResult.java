package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code AlgoNewOrderOperation} class is useful to create an algo new order operation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
 * Cancel Algo Order (TRADE)</a>
 * @see BinanceItem
 * @see BinanceManager.BinanceResponse
 * @see AlgoOperationResult
 **/
public class CancelAlgoOrderResult extends AlgoOperationResult {

    /**
     * {@code algoId} algo identifier
     **/
    private final long algoId;

    /**
     * Constructor to init {@link CancelAlgoOrderResult} object
     *
     * @param success: whether the operation has been successful
     * @param algoId:  algo identifier
     **/
    public CancelAlgoOrderResult(boolean success, long algoId) {
        super(success);
        this.algoId = algoId;
    }

    /**
     * Constructor to init {@link CancelAlgoOrderResult} object
     *
     * @param jCancelAlgoOrderResult: cancel algo result details as {@link JSONObject}
     **/
    public CancelAlgoOrderResult(JSONObject jCancelAlgoOrderResult) {
        super(jCancelAlgoOrderResult);
        algoId = hItem.getLong("algoId", 0);
    }

    /**
     * Method to get {@link #algoId} instance <br>
     * No-any params required
     *
     * @return {@link #algoId} instance as long
     **/
    public long getAlgoId() {
        return algoId;
    }

}
