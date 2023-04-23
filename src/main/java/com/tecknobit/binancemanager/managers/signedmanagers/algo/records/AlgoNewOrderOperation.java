package com.tecknobit.binancemanager.managers.signedmanagers.algo.records;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat;

/**
 * The {@code AlgoNewOrderOperation} class is useful to create an algo new order operation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#volume-participation-vp-new-order-trade">
 *             Volume Participation(VP) New Order (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade">
 *             Time-Weighted Average Price(Twap) New Order (TRADE)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#time-weighted-average-price-twap-new-order-trade-2">
 *             Time-Weighted Average Price (Twap) New Order (TRADE)</a>
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
public class AlgoNewOrderOperation extends AlgoOperationResult {

    /**
     * {@code clientAlgoId} client algo identifier
     **/
    private final String clientAlgoId;

    /**
     * Constructor to init {@link AlgoNewOrderOperation} object
     *
     * @param success:      whether the operation has been successful
     * @param clientAlgoId: client algo identifier
     **/
    public AlgoNewOrderOperation(boolean success, String clientAlgoId) {
        super(success);
        this.clientAlgoId = clientAlgoId;
    }

    /**
     * Constructor to init {@link AlgoNewOrderOperation} object
     *
     * @param jAlgoNewOrderOperation: algo new order operation details as {@link JSONObject}
     **/
    public AlgoNewOrderOperation(JSONObject jAlgoNewOrderOperation) {
        super(jAlgoNewOrderOperation);
        clientAlgoId = hItem.getString("clientAlgoId");
    }

    /**
     * Method to get {@link #clientAlgoId} instance <br>
     * No-any params required
     *
     * @return {@link #clientAlgoId} instance as {@link String}
     **/
    public String getClientAlgoId() {
        return clientAlgoId;
    }

    /**
     * Method to create an algo order operation
     *
     * @param algoOrderResponse: obtained from Binance's response
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return algo order operation as {@code "format"} defines
     **/
    @Returner
    public static <T> T returnAlgoOrderNewOperation(String algoOrderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(algoOrderResponse);
            case LIBRARY_OBJECT:
                return (T) new AlgoNewOrderOperation(new JSONObject(algoOrderResponse));
            default:
                return (T) algoOrderResponse;
        }
    }

}
