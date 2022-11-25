package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code ACKOrder} class is useful to format a {@code "Binance"}'s spot order in {@code "ACK"} format
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * New Order (TRADE)</a>
 * @see Order
 * @see SpotOrder
 **/
// TODO: 22/11/2022 CHECK TO REMOVE
public class ACKSpotOrder extends SpotOrder {

    /**
     * Constructor to init {@link ACKSpotOrder} object
     *
     * @param ackOrder: ack order details as {@link JSONObject}
     **/
    public ACKSpotOrder(JSONObject ackOrder) {
        super(ackOrder);
        transactTime = hOrder.getLong("transactTime");
    }

    /**
     * {@code transactTime} is instance that memorizes transaction time
     **/
    protected long transactTime;

    /** Constructor to init {@link ACKSpotOrder} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * @param orderListId: list order identifier
     * @param transactTime: transaction time
     * **/
    public ACKSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime) {
        super(symbol, orderId, orderListId, clientOrderId);
        this.transactTime = transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * Any params required
     *
     * @return {@link #transactTime} instance as long
     **/
    public long getTransactTime() {
        return transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * Any params required
     *
     * @return {@link #transactTime} instance as {@link Date}
     **/
    public Date getTransactDate() {
        return TimeFormatter.getDate(transactTime);
    }

    /**
     * {@code ReplaceMode} list of available replace mode
     **/
    public enum ReplaceMode {

        /**
         * {@code STOP_ON_FAILURE} whether the cancel request fails, the new order placement will not be attempted
         **/
        STOP_ON_FAILURE,

        /**
         * {@code ALLOW_FAILURE_REPLACE_MODE} whether the new order placement will be attempted even if cancel request fails
         **/
        ALLOW_FAILURE

    }

}
