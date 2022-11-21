package com.tecknobit.binancemanager.managers.signedmanagers.trade.common;

/**
 * The {@code TradeConstants} class contains all {@code "Binance"}'s Trade Constants to do trade's request
 *
 * @deprecated this class will be removed in the next update, use enums of constants in {@link Order} instead
 **/
@Deprecated
public class TradeConstants {

    /**
     * {@code BUY} is constant for buy operation
     **/
    public static final String BUY = "BUY";

    /**
     * {@code SELL} is constant for sell operation
     **/
    public static final String SELL = "SELL";

    /**
     * {@code LIMIT} is constant for limit order type
     * **/
    public static final String LIMIT = "LIMIT";

    /**
     * {@code MARKET} is constant for market order type
     * **/
    public static final String MARKET = "MARKET";

    /**
     * {@code STOP_LOSS} is constant for stop loss order type
     * **/
    public static final String STOP_LOSS = "STOP_LOSS";

    /**
     * {@code STOP_LOSS} is constant for stop loss limit order type
     * **/
    public static final String STOP_LOSS_LIMIT = "STOP_LOSS_LIMIT";

    /**
     * {@code STOP_LOSS} is constant for take profit order type
     * **/
    public static final String TAKE_PROFIT = "TAKE_PROFIT";

    /**
     * {@code STOP_LOSS} is constant for take profit limit order type
     * **/
    public static final String TAKE_PROFIT_LIMIT = "TAKE_PROFIT_LIMIT";

    /**
     * {@code LIMIT_MAKER} is constant for take profit limit order type
     * **/
    public static final String LIMIT_MAKER = "LIMIT_MAKER";

    /**
     * {@code TIME_IN_FORCE_GTC} is constant for GTC order type
     * **/
    public static final String TIME_IN_FORCE_GTC = "GTC";

    /**
     * {@code TIME_IN_FORCE_IOC} is constant for IOC order type
     * **/
    public static final String TIME_IN_FORCE_IOC = "IOC";

    /**
     * {@code TIME_IN_FORCE_FOK} is constant for FOK order type
     * **/
    public static final String TIME_IN_FORCE_FOK = "FOK";

    /**
     * {@code NEW_ORDER_RESP_TYPE_ACK} is constant for ACK order response type
     * **/
    public static final String NEW_ORDER_RESP_TYPE_ACK = "ACK";

    /**
     * {@code NEW_ORDER_RESP_TYPE_RESULT} is constant for RESULT order response type
     * **/
    public static final String NEW_ORDER_RESP_TYPE_RESULT = "RESULT";

    /**
     * {@code NEW_ORDER_RESP_TYPE_FULL} is constant for FULL order response type
     * **/
    public static final String NEW_ORDER_RESP_TYPE_FULL = "FULL";

}
