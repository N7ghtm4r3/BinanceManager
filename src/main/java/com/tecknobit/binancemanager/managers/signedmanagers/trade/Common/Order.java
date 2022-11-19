package com.tecknobit.binancemanager.managers.signedmanagers.trade.common;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.BinanceMarginManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.BinanceSpotManager;
import org.json.JSONObject;

/**
 * The {@code Order} class is useful to manage and format all Binance Order request
 *
 * @implNote used by {@link BinanceSpotManager} and {@link BinanceMarginManager}
 * @apiNote see the official documentation at:
 * <ul>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
 * </li>
 * </ul>
 **/

public abstract class Order {

    /**
     * {@code STATUS_CONFIRMED} is constant for confirmed status
     **/
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    /**
     * {@code STATUS_PENDING} is constant for pending status
     **/
    public static final String STATUS_PENDING = "PENDING";

    /**
     * {@code STATUS_FAILED} is constant for failed status
     * **/
    public static final String STATUS_FAILED = "FAILED";

    /**
     * {@code STATUS_NEW} is constant for new status
     * **/
    public static final String STATUS_NEW = "NEW";

    /**
     * {@code STATUS_PARTIALLY_FILLED} is constant for status partially filled
     * **/
    public static final String STATUS_PARTIALLY_FILLED = "PARTIALLY_FILLED";

    /**
     * {@code STATUS_FILLED} is constant for filled status
     * **/
    public static final String STATUS_FILLED = "FILLED";

    /**
     * {@code STATUS_CANCELED} is constant for canceled status
     * **/
    public static final String STATUS_CANCELED = "CANCELED";

    /**
     * {@code STATUS_PENDING_CANCELED} is constant for pending canceled status
     * **/
    public static final String STATUS_PENDING_CANCELED = "PENDING_CANCELED";

    /**
     * {@code STATUS_REJECTED} is constant for rejected status
     * **/
    public static final String STATUS_REJECTED = "REJECTED";

    /**
     * {@code STATUS_EXPIRED} is constant for expired status
     * **/
    public static final String STATUS_EXPIRED = "EXPIRED";

    /**
     * {@code STATUS_EXECUTING} is constant for executing status
     * **/
    public static final String STATUS_EXECUTING = "EXECUTING";

    /**
     * {@code STATUS_ALL_DONE} is constant for all done status
     * **/
    public static final String STATUS_ALL_DONE = "ALL_DONE";

    /**
     * {@code REJECT} is constant for reject status
     * **/
    public static final String STATUS_REJECT = "REJECT";

    /**
     * {@code symbol} is instance that memorizes symbol used in the order
     * **/
    protected final String symbol;

    /**
     * {@code orderId} is instance that memorizes order identifier
     **/
    protected final double orderId;

    /**
     * {@code clientOrderId} is instance that memorizes client order identifier
     **/
    protected final String clientOrderId;

    /**
     * {@code jsonHelper} is instance that memorizes {@link JsonHelper} tool
     **/
    protected final JsonHelper hOrder;

    /**
     * Constructor to init {@link Order} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     **/
    public Order(String symbol, double orderId, String clientOrderId) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
        hOrder = null;
    }

    /**
     * Constructor to init {@link Order} object
     *
     * @param order: order details as {@link JSONObject}
     **/
    public Order(JSONObject order) {
        hOrder = new JsonHelper(order);
        symbol = hOrder.getString("symbol");
        orderId = hOrder.getLong("orderId");
        clientOrderId = hOrder.getString("clientOrderId");
    }

    public String getSymbol() {
        return symbol;
    }

    public double getOrderId() {
        return orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "symbol='" + symbol + '\'' +
                ", orderId=" + orderId +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }

}
