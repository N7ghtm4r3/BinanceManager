package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 * The {@code Order} class is useful to manage and format all Binance Order request
 * @implNote used by BinanceSpotManager, BinanceMarginManager
 * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * **/

public class Order {

    /**
     * {@code STATUS_CONFIRMED} is constant for confirmed status
     * **/
    public static final String STATUS_CONFIRMED = "CONFIRMED";

    /**
     * {@code STATUS_PENDING} is constant for pending status
     * **/
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
     * **/
    protected final double orderId;

    /**
     * {@code clientOrderId} is instance that memorizes client order identifier
     * **/
    protected final String clientOrderId;

    /** Constructor to init {@link Order} object
     * @param symbol: symbol used in the order
     * @param orderId: order identifier
     * @param clientOrderId: client order identifier
     * **/
    public Order(String symbol, double orderId, String clientOrderId) {
        this.symbol = symbol;
        this.orderId = orderId;
        this.clientOrderId = clientOrderId;
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

}
