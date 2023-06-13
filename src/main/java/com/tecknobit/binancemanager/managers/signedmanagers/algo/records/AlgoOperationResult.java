package com.tecknobit.binancemanager.managers.signedmanagers.algo.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code AlgoOperationResult} class is useful to create an algo operation result
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
 */
@Structure
public abstract class AlgoOperationResult extends BinanceItem implements BinanceResponse {

    /**
     * {@code success} whether the operation has been successful
     */
    protected final boolean success;

    /**
     * Constructor to init {@link AlgoOperationResult} object
     *
     * @param success: whether the operation has been successful
     */
    public AlgoOperationResult(boolean success) {
        super(null);
        this.success = success;
    }

    /**
     * Constructor to init {@link AlgoOperationResult} object
     *
     * @param jAlgoOperationResult: algo operation result details as {@link JSONObject}
     */
    public AlgoOperationResult(JSONObject jAlgoOperationResult) {
        super(jAlgoOperationResult);
        success = hItem.getBoolean("success");
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
            return hItem.getString("msg");
        return null;
    }

}
