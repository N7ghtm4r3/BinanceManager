package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

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

}
