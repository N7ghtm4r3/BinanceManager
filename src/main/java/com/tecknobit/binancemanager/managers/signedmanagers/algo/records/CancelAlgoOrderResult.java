package com.tecknobit.binancemanager.managers.signedmanagers.algo.records;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;

/**
 * The {@code AlgoNewOrderOperation} class is useful to create an algo new order operation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade">
 *             Cancel Algo Order (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-algo-order-trade-2">
 *             Cancel Algo Order (TRADE)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceResponse
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

    /**
     * Method to create a cancel algo order
     *
     * @param algoOrderResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return cancel algo order as {@code "format"} defines
     **/
    @Returner
    public static <T> T returnCancelAlgoOrder(String algoOrderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrderResponse);
            case LIBRARY_OBJECT:
                return (T) new CancelAlgoOrderResult(new JSONObject(algoOrderResponse));
            default:
                return (T) algoOrderResponse;
        }
    }

}
