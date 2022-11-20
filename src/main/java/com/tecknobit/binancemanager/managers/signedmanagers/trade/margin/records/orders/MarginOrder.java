package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order;
import org.json.JSONObject;

/**
 * The {@code MarginOrder} class is useful to format all {@code "Binance"}'s margin order responses
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 **/

public abstract class MarginOrder extends Order {

    /**
     * {@code transactTime} is instance that memorizes transaction time
     **/
    protected final long transactTime;

    /**
     * Constructor to init {@link MarginOrder} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime:  transaction time
     **/
    public MarginOrder(String symbol, double orderId, String clientOrderId, long transactTime) {
        super(symbol, orderId, clientOrderId);
        this.transactTime = transactTime;
    }

    /**
     * Constructor to init {@link MarginOrder} object
     *
     * @param marginOrder: margin order details as {@link JSONObject}
     **/
    public MarginOrder(JSONObject marginOrder) {
        super(marginOrder);
        transactTime = marginOrder.getLong("transactTime");
    }

    public long getTransactTime() {
        return transactTime;
    }

    @Override
    public String toString() {
        return "MarginOrder{" +
                "transactTime=" + transactTime +
                ", symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
