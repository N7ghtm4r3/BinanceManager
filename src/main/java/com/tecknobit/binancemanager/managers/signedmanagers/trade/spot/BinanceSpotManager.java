package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.market.records.stats.ExchangeInformation.SelfTradePreventionMode;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderCountUsage;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account.SpotAccountInformation;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account.SpotAccountTradesList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.ComposedSpotOrderDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.OpenSpotOrders;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.SpotOrderCAS;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.SpotOrderDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.*;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.ACKSpotOrder.ReplaceMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.*;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.OrderType.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderCountUsage.returnCountUsageList;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.SpotOrder.*;

/**
 * The {@code BinanceSpotManager} class is useful to manage all {@code "Binance"}'s Spot Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 * Spot Account/Trade</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 */
public class BinanceSpotManager extends BinanceSignedManager {

    /**
     * {@code SPOT_TEST_NEW_ORDER_ENDPOINT} is constant for SPOT_TEST_NEW_ORDER_ENDPOINT's endpoint
     */
    public static final String SPOT_TEST_NEW_ORDER_ENDPOINT = "/api/v3/order/test";

    /**
     * {@code SPOT_ORDER_ENDPOINT} is constant for SPOT_ORDER_ENDPOINT's endpoint
     */
    public static final String SPOT_ORDER_ENDPOINT = "/api/v3/order";

    /**
     * {@code SPOT_OPEN_ORDERS_ENDPOINT} is constant for SPOT_OPEN_ORDERS_ENDPOINT's endpoint
     */
    public static final String SPOT_OPEN_ORDERS_ENDPOINT = "/api/v3/openOrders";

    /**
     * {@code SPOT_ALL_ORDERS_LIST_ENDPOINT} is constant for SPOT_ALL_ORDERS_LIST_ENDPOINT's endpoint
     */
    public static final String SPOT_ALL_ORDERS_LIST_ENDPOINT = "/api/v3/allOrders";

    /**
     * {@code CANCEL_AND_SEND_ORDER_ENDPOINT} is constant for CANCEL_AND_SEND_ORDER_ENDPOINT's endpoint
     */
    public static final String CANCEL_AND_SEND_ORDER_ENDPOINT = "/api/v3/order/cancelReplace";

    /**
     * {@code SPOT_OCO_ORDER_ENDPOINT} is constant for SPOT_OCO_ORDER_ENDPOINT's endpoint
     */
    public static final String SPOT_OCO_ORDER_ENDPOINT = "/api/v3/order/oco";

    /**
     * {@code SPOT_OCO_ORDER_LIST_ENDPOINT} is constant for SPOT_OCO_ORDER_LIST_ENDPOINT's endpoint
     */
    public static final String SPOT_OCO_ORDER_LIST_ENDPOINT = "/api/v3/orderList";

    /**
     * {@code SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT} is constant for SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT's endpoint
     */
    public static final String SPOT_OCO_ORDER_LIST_STATUS_ENDPOINT = "/api/v3/allOrderList";

    /**
     * {@code SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT} is constant for SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT's endpoint
     */
    public static final String SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT = "/api/v3/openOrderList";

    /**
     * {@code SPOT_ACCOUNT_INFORMATION_ENDPOINT} is constant for SPOT_ACCOUNT_INFORMATION_ENDPOINT's endpoint
     */
    public static final String SPOT_ACCOUNT_INFORMATION_ENDPOINT = "/api/v3/account";

    /**
     * {@code SPOT_ACCOUNT_TRADE_LIST_ENDPOINT} is constant for SPOT_ACCOUNT_TRADE_LIST_ENDPOINT's endpoint
     */
    public static final String SPOT_ACCOUNT_TRADE_LIST_ENDPOINT = "/api/v3/myTrades";

    /**
     * {@code SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE} is constant for SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE's endpoint
     */
    public static final String SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE = "/api/v3/rateLimit/order";

    /**
     * {@code MY_PREVENTED_MATCHES} is constant for MY_PREVENTED_MATCHES's endpoint
     */
    public static final String MY_PREVENTED_MATCHES = "/api/v3/myPreventedMatches";

    /**
     * Constructor to init a {@link BinanceSpotManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSpotManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceSpotManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSpotManager(String baseEndpoint, int timeout, String apiKey,
                              String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init {@link BinanceSpotManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceSpotManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceSpotManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceSpotManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceSignedManager firstManager = new BinanceSignedManager("apiKey", "apiSecret");
     *        //You don't need to insert all credentials to make manager work
     *        BinanceSignedManager secondManager = new BinanceSignedManager(); //same credentials used
     *     }
     * </pre>
     */
    public BinanceSpotManager() {
        super();
    }

    /**
     * Request to test a spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param type:        LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quantity"} -> order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "price"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return result of the operation <b>WITHOUT</b> buy or sell -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1")
    @RequestPath(method = POST, path = "/api/v3/order/test")
    public boolean testNewOrder(String symbol, Side side, OrderType type, OrderResponseType newOrderRespType,
                                Params extraParams) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("newOrderRespType", newOrderRespType);
        return testNewOrder(symbol, side, type, extraParams);
    }

    /**
     * Request to test a spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param type:        LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quantity"} -> order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "price"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return result of the operation <b>WITHOUT</b> buy or sell -> {@code "true"} is successful, {@code "false"} if not successful
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * Test New Order (TRADE)</a>
     */
    @RequestWeight(weight = "1")
    @RequestPath(method = POST, path = "/api/v3/order/test")
    public boolean testNewOrder(String symbol, Side side, OrderType type, Params extraParams) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("timestamp", getServerTime());
        extraParams.addParam("side", side);
        extraParams.addParam("type", type);
        return sendPostSignedRequest(SPOT_TEST_NEW_ORDER_ENDPOINT, extraParams).equals("{}");
    }

    /**
     * Request to send a limit spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link FullSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendLimitOrder(String symbol, Side side, double quantity, double price,
                                                  TimeInForce timeInForce, Params extraParams) throws Exception {
        return sendLimitOrder(symbol, side, quantity, price, timeInForce, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send a limit spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendLimitOrder(String symbol, Side side, double quantity, double price, TimeInForce timeInForce,
                                Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, LIMIT, null, getLimitPayload(timeInForce, quantity, price,
                extraParams), format);
    }

    /**
     * Request to send a market spot order with quantity
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "price"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link FullSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendMarketOrderQty(String symbol, Side side, double quantity,
                                                      Params extraParams) throws Exception {
        return sendMarketOrderQty(symbol, side, quantity, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send a market spot order with quantity
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param quantity:    quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "price"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "stopPrice"} -> stop price - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendMarketOrderQty(String symbol, Side side, double quantity, Params extraParams,
                                    ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, MARKET, null, getMarketPayload("quantity", quantity,
                extraParams), format);
    }

    /**
     * Request to send a market spot order with quote quantity
     *
     * @param symbol:        symbol used in the request es. BTCBUSD
     * @param side:          side of the order
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams:   additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                       <ul>
     *                           <li>
     *                               {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                           </li>
     *                           <li>
     *                               {@code "quantity"} -> order quantity - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "price"} -> quote order quantity - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                               if not sent - [STRING]
     *                             </li>
     *                             <li>
     *                                  {@code "stopPrice"} -> stop price - [DECIMAL]
     *                             </li>
     *                             <li>
     *                                 {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                             </li>
     *                             <li>
     *                                 {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                             </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                       </ul>
     * @return result of the order as {@link FullSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendMarketOrderQuoteQty(String symbol, Side side, double quoteQuantity,
                                                           Params extraParams) throws Exception {
        return sendMarketOrderQuoteQty(symbol, side, quoteQuantity, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to send a market spot order with quote quantity
     *
     * @param symbol:        symbol used in the request es. BTCBUSD
     * @param side:          side of the order
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams:   additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                       <ul>
     *                           <li>
     *                               {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                           </li>
     *                           <li>
     *                               {@code "quantity"} -> order quantity - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "price"} -> quote order quantity - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                               if not sent - [STRING]
     *                             </li>
     *                             <li>
     *                                  {@code "stopPrice"} -> stop price - [DECIMAL]
     *                             </li>
     *                             <li>
     *                                 {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                             </li>
     *                             <li>
     *                                 {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                             </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                       </ul>
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendMarketOrderQuoteQty(String symbol, Side side, double quoteQuantity, Params extraParams,
                                         ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, MARKET, null, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), format);
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "price"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                                          Params extraParams) throws Exception {
        return sendStopLossOrderPrice(symbol, side, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "price"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossOrderPrice(String symbol, Side side, double quantity, double stopPrice, Params extraParams,
                                        ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS, null, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "price"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossOrderDelta(String symbol, Side side, double quantity, double trailingDelta,
                                                          Params extraParams) throws Exception {
        return sendStopLossOrderDelta(symbol, side, quantity, trailingDelta, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "price"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossOrderDelta(String symbol, Side side, double quantity, double trailingDelta,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS, null, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /**
     * Request to send a stop loss limit spot order with stop price
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order
     * @param timeInForce: time in force for the order
     * @param quantity:    quantity value in the order
     * @param price:       price value in the order
     * @param stopPrice:   stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                           </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce,
                                                               double quantity, double price, double stopPrice,
                                                               Params extraParams) throws Exception {
        return sendStopLossLimitOrderPrice(symbol, side, timeInForce, quantity, price, stopPrice, extraParams,
                LIBRARY_OBJECT);
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                             double price, double stopPrice, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS_LIMIT, null, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossLimitOrderDelta(String symbol, Side side, TimeInForce timeInForce,
                                                               double quantity, double price, double trailingDelta,
                                                               Params extraParams) throws Exception {
        return sendStopLossLimitOrderDelta(symbol, side, timeInForce, quantity, price, trailingDelta, extraParams,
                LIBRARY_OBJECT);
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossLimitOrderDelta(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                             double price, double trailingDelta, Params extraParams,
                                             ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS_LIMIT, null, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams), format);
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                                            Params extraParams) throws Exception {
        return sendTakeProfitOrderPrice(symbol, side, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitOrderPrice(String symbol, Side side, double quantity, double stopPrice,
                                          Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT, null, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitOrderDelta(String symbol, Side side, double quantity, double trailingDelta,
                                                            Params extraParams) throws Exception {
        return sendTakeProfitOrderDelta(symbol, side, quantity, trailingDelta, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitOrderDelta(String symbol, Side side, double quantity, double trailingDelta,
                                          Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT, null, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce,
                                                                 double quantity, double price, double stopPrice,
                                                                 Params extraParams) throws Exception {
        return sendTakeProfitLimitOrderPrice(symbol, side, timeInForce, quantity, price, stopPrice, extraParams,
                LIBRARY_OBJECT);
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitLimitOrderPrice(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                               double price, double stopPrice, Params extraParams,
                                               ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT_LIMIT, null, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitLimitOrderDelta(String symbol, Side side, TimeInForce timeInForce,
                                                                 double quantity, double price, double trailingDelta,
                                                                 Params extraParams) throws Exception {
        return sendTakeProfitLimitOrderDelta(symbol, side, timeInForce, quantity, price, trailingDelta, extraParams,
                LIBRARY_OBJECT);
    }

    /**
     * Request to send a take profit limit spot order with trailing delta
     *
     * @param symbol:        symbol used in the request es. BTCBUSD
     * @param side:          side of the order
     * @param timeInForce:   time in force for the order
     * @param quantity:      quantity value in the order
     * @param price:         price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams:   additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                       <ul>
     *                           <li>
     *                               {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                           </li>
     *                           <li>
     *                               {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                           </li>
     *                           <li>
     *                               {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                               if not sent - [STRING]
     *                             </li>
     *                             <li>
     *                                 {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                             </li>
     *                             <li>
     *                                 {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                             </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                           </li>
     *                       </ul>
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitLimitOrderDelta(String symbol, Side side, TimeInForce timeInForce, double quantity,
                                               double price, double trailingDelta, Params extraParams,
                                               ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT_LIMIT, null, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams), format);
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendLimitMakerOrder(String symbol, Side side, double quantity, double price,
                                                       Params extraParams) throws Exception {
        return sendLimitMakerOrder(symbol, side, quantity, price, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                           <li>
     *                               {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                               on the symbol. The possible supported values are, constants available
     *                               {@link SelfTradePreventionMode} - [STRING]
     *                           </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendLimitMakerOrder(String symbol, Side side, double quantity, double price,
                                     Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, LIMIT_MAKER, null, getLimitMakerPayload(quantity, price,
                extraParams), format);
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendLimitOrder(String symbol, Side side, OrderResponseType newOrderRespType,
                                                  TimeInForce timeInForce, double quantity, double price,
                                                  Params extraParams) throws Exception {
        return sendLimitOrder(symbol, side, newOrderRespType, timeInForce, quantity, price, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendLimitOrder(String symbol, Side side, OrderResponseType newOrderRespType, TimeInForce timeInForce,
                                double quantity, double price, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, LIMIT, newOrderRespType, getLimitPayload(timeInForce, quantity, price,
                extraParams), format);
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendMarketOrderQty(String symbol, Side side, OrderResponseType newOrderRespType,
                                                      double quantity, Params extraParams) throws Exception {
        return sendMarketOrderQty(symbol, side, newOrderRespType, quantity, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendMarketOrderQty(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                    Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, MARKET, newOrderRespType, getMarketPayload("quantity", quantity,
                extraParams), format);
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendMarketOrderQuoteQty(String symbol, Side side, OrderResponseType newOrderRespType,
                                                           double quoteQuantity, Params extraParams) throws Exception {
        return sendMarketOrderQuoteQty(symbol, side, newOrderRespType, quoteQuantity, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendMarketOrderQuoteQty(String symbol, Side side, OrderResponseType newOrderRespType, double quoteQuantity,
                                         Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, MARKET, newOrderRespType, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), format);
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                          double quantity, double stopPrice,
                                                          Params extraParams) throws Exception {
        return sendStopLossOrderPrice(symbol, side, newOrderRespType, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                        double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS, newOrderRespType, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                                          double quantity, double trailingDelta,
                                                          Params extraParams) throws Exception {
        return sendStopLossOrderDelta(symbol, side, newOrderRespType, quantity, trailingDelta, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                        double trailingDelta, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS, newOrderRespType, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                               TimeInForce timeInForce, double quantity, double price,
                                                               double stopPrice, Params extraParams) throws Exception {
        return sendStopLossLimitOrderPrice(symbol, side, newOrderRespType, timeInForce, quantity, price, stopPrice,
                extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                             TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                             Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS_LIMIT, newOrderRespType, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendStopLossLimitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                                               TimeInForce timeInForce, double quantity, double price,
                                                               double trailingDelta, Params extraParams) throws Exception {
        return sendStopLossLimitOrderDelta(symbol, side, newOrderRespType, timeInForce, quantity, price, trailingDelta,
                extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendStopLossLimitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                             TimeInForce timeInForce, double quantity, double price, double trailingDelta,
                                             Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, STOP_LOSS_LIMIT, newOrderRespType, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams), format);
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                            double quantity, double stopPrice,
                                                            Params extraParams) throws Exception {
        return sendTakeProfitOrderPrice(symbol, side, newOrderRespType, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                          double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT, newOrderRespType, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                                            double quantity, double trailingDelta,
                                                            Params extraParams) throws Exception {
        return sendTakeProfitOrderDelta(symbol, side, newOrderRespType, quantity, trailingDelta, extraParams,
                LIBRARY_OBJECT);
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                          double trailingDelta, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT, newOrderRespType, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendTakeProfitLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                                                 TimeInForce timeInForce, double quantity, double price,
                                                                 double stopPrice, Params extraParams) throws Exception {
        return sendTakeProfitLimitOrderPrice(symbol, side, newOrderRespType, timeInForce, quantity, price, stopPrice,
                extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitLimitOrderPrice(String symbol, Side side, OrderResponseType newOrderRespType,
                                               TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                               Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT_LIMIT, newOrderRespType, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitLimitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                               TimeInForce timeInForce, double quantity, double price,
                                               double trailingDelta, Params extraParams) throws Exception {
        return sendTakeProfitLimitOrderDelta(symbol, side, newOrderRespType, timeInForce, quantity, price, trailingDelta,
                extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendTakeProfitLimitOrderDelta(String symbol, Side side, OrderResponseType newOrderRespType,
                                               TimeInForce timeInForce, double quantity, double price,
                                               double trailingDelta, Params extraParams,
                                               ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, TAKE_PROFIT_LIMIT, newOrderRespType, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams), format);
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@link ACKSpotOrder} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T extends SpotOrder> T sendLimitMakerOrder(String symbol, Side side, OrderResponseType newOrderRespType,
                                                       double quantity, double price, Params extraParams) throws Exception {
        return sendLimitMakerOrder(symbol, side, newOrderRespType, quantity, price, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     New Order (TRADE)</a>
     * @return result of the order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP/UID)")
    @RequestPath(method = POST, path = "/api/v3/order")
    public <T> T sendLimitMakerOrder(String symbol, Side side, OrderResponseType newOrderRespType, double quantity,
                                     double price, Params extraParams, ReturnFormat format) throws Exception {
        return returnNewOrder(symbol, side, LIMIT_MAKER, newOrderRespType, getLimitMakerPayload(quantity, price,
                extraParams), format);
    }

    /**
     * Request to send a new spot order
     *
     * @param symbol:           symbol used to the order es. BTCBUSD
     * @param side:             BUY or SELL order
     * @param type:             LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams:      additional params of the request
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of the order as {@code "format"} defines
     */
    private <T> T returnNewOrder(String symbol, Side side, OrderType type, OrderResponseType newOrderRespType,
                                 Params extraParams, ReturnFormat format) throws Exception {
        Params payload = new Params();
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("type", type);
        extraParams.addParam("newOrderRespType", newOrderRespType);
        String params = getTimestampParam() + "&symbol=" + symbol + "&side=" + side + "&type=" + type
                + "&newOrderRespType=" + newOrderRespType;
        String orderResponse = sendPostSignedRequest(SPOT_ORDER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderResponse);
            case LIBRARY_OBJECT:
                JSONObject orderResult = new JSONObject(orderResponse);
                if (newOrderRespType != null) {
                    switch (newOrderRespType) {
                        case RESULT:
                            return (T) new ResultSpotOrder(orderResult);
                        case FULL:
                            return (T) new FullSpotOrder(orderResult);
                        default:
                            return (T) new ACKSpotOrder(orderResult);
                    }
                } else {
                    if (type.equals(LIMIT) || type.equals(MARKET))
                        return (T) new FullSpotOrder(orderResult);
                    else
                        return (T) new ACKSpotOrder(orderResult);
                }
            default:
                return (T) orderResponse;
        }
    }

    /**
     * Request to cancel a spot order
     *
     * @param order: order to cancel
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(Order order) throws Exception {
        return cancelOrder(order.getSymbol(), order.getOrderId(), LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param order:  order to cancel
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(Order order, ReturnFormat format) throws Exception {
        return cancelOrder(order.getSymbol(), order.getOrderId(), format);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:  symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(String symbol, long orderId) throws Exception {
        return cancelOrder(symbol, orderId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:  symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(String symbol, long orderId, ReturnFormat format) throws Exception {
        return returnSpotOrderDetails(sendDeleteSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&orderId=" + orderId), format);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(String symbol, String origClientOrderId) throws Exception {
        return cancelOrder(symbol, origClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnSpotOrderDetails(sendDeleteSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&origClientOrderId=" + origClientOrderId), format);
    }

    /**
     * Request to cancel a spot order
     *
     * @param order:       order to cancel
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                              {@link CancelRestriction} - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(Order order, Params extraParams) throws Exception {
        return cancelOrder(order.getSymbol(), order.getOrderId(), extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param order:       order to cancel
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                              {@link CancelRestriction} - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(Order order, Params extraParams, ReturnFormat format) throws Exception {
        return cancelOrder(order.getSymbol(), order.getOrderId(), extraParams, format);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param orderId:     identifier of the order es. 1232065
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                             if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "orderId"} -> order identifier - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                              {@link CancelRestriction} - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(String symbol, long orderId, Params extraParams) throws Exception {
        return cancelOrder(symbol, orderId, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param orderId:     identifier of the order es. 1232065
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                       </li>
     *                       <li>
     *                           {@code "orderId"} -> order identifier - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "origClientOrderId"} -> orig client order identifier - [STRING]
     *                       </li>
     *                         <li>
     *                              {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                              {@link CancelRestriction} - [STRING]
     *                         </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:           return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(String symbol, long orderId, Params extraParams, ReturnFormat format) throws Exception {
        return returnSpotOrderDetails(sendDeleteSignedRequest(SPOT_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol + "&orderId=" + orderId, extraParams)), format);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param extraParams:       additional params of the request, keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                               </li>
     *                               <li>
     *                                   {@code "orderId"} -> order identifier - [LONG]
     *                               </li>
     *                               <li>
     *                                   {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                   {@link CancelRestriction} - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                               </li>
     *                           </ul>
     * @return result of cancel operation as {@link SpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public SpotOrderDetails cancelOrder(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        return cancelOrder(symbol, origClientOrderId, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param extraParams:       additional params of the request, keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                               </li>
     *                               <li>
     *                                   {@code "orderId"} -> order identifier - [LONG]
     *                               </li>
     *                               <li>
     *                                   {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                   {@link CancelRestriction} - [STRING]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                               </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancel operation as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     * Cancel Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/order")
    public <T> T cancelOrder(String symbol, String origClientOrderId, Params extraParams,
                             ReturnFormat format) throws Exception {
        return returnSpotOrderDetails(sendDeleteSignedRequest(SPOT_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId,
                extraParams)), format);
    }

    /**
     * Method to create a spot order details
     *
     * @param spotDetailsResponse: obtained from Binance's response
     * @param format:              return type formatter -> {@link ReturnFormat}
     * @return spot order details as {@code "format"} defines
     */
    @Returner
    private <T> T returnSpotOrderDetails(String spotDetailsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(spotDetailsResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotOrderDetails(new JSONObject(spotDetailsResponse));
            default:
                return (T) spotDetailsResponse;
        }
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     Cancel all Open Orders on a Symbol (TRADE)</a>
     * @return result of cancel all open orders on a symbol as {@link OpenSpotOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/openOrders")
    public OpenSpotOrders cancelAllOpenOrders(String symbol) throws Exception {
        return cancelAllOpenOrders(symbol, LIBRARY_OBJECT);
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     Cancel all Open Orders on a Symbol (TRADE)</a>
     * @return result of cancel all open orders on a symbol as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/openOrders")
    public <T> T cancelAllOpenOrders(String symbol, ReturnFormat format) throws Exception {
        return returnOpenSpotOrders(sendDeleteSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, getTimestampParam()
                + "&symbol=" + symbol), format);
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     Cancel all Open Orders on a Symbol (TRADE)</a>
     * @return result of cancel all open orders on a symbol as {@link OpenSpotOrders} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/openOrders")
    public OpenSpotOrders cancelAllOpenOrders(String symbol, long recvWindow) throws Exception {
        return cancelAllOpenOrders(symbol, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel all open orders on a symbol
     *
     * @param symbol:     symbol used in the request es. BTCBUSD
     * @param recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return result of cancel all open orders on a symbol as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     * Cancel all Open Orders on a Symbol (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/openOrders")
    public <T> T cancelAllOpenOrders(String symbol, long recvWindow, ReturnFormat format) throws Exception {
        return returnOpenSpotOrders(sendDeleteSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create an open spot orders list
     *
     * @param ordersResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return open spot orders as {@code "format"} defines
     */
    @Returner
    private <T> T returnOpenSpotOrders(String ordersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(ordersResponse);
            case LIBRARY_OBJECT:
                return (T) new OpenSpotOrders(new JSONArray(ordersResponse));
            default:
                return (T) ordersResponse;
        }
    }

    /**
     * Request to get status of an order
     *
     * @param order: order from fetch the status
     * @return status of an order as {@link SpotOrderStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(Order order) throws Exception {
        return getOrderStatus(order.getSymbol(), order.getOrderId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get status of an order
     *
     * @param order:  order from fetch the status
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(Order order, ReturnFormat format) throws Exception {
        return getOrderStatus(order.getSymbol(), order.getOrderId(), format);
    }

    /**
     * Request to get status of an order
     *
     * @param symbol:  symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @return status of an order as {@link SpotOrderStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(String symbol, long orderId) throws Exception {
        return getOrderStatus(symbol, orderId, LIBRARY_OBJECT);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     Query Order (USER_DATA)</a>
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(String symbol, long orderId, ReturnFormat format) throws Exception {
        return returnOrderStatus(sendGetSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol=" + symbol
                + "&orderId=" + orderId), format);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     Query Order (USER_DATA)</a>
     * @return status of an order as {@link SpotOrderStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return getOrderStatus(symbol, origClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to get status of an order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(String symbol, String origClientOrderId, ReturnFormat format) throws Exception {
        return returnOrderStatus(sendGetSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol=" + symbol
                + "&origClientOrderId=" + origClientOrderId), format);
    }

    /**
     * Request to get status of an order
     *
     * @param order:      order from fetch the status
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return status of an order as {@link SpotOrderStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(Order order, long recvWindow) throws Exception {
        return getOrderStatus(order.getSymbol(), order.getOrderId(), recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get status of an order
     *
     * @param order:      order from fetch the status
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(Order order, long recvWindow, ReturnFormat format) throws Exception {
        return getOrderStatus(order.getSymbol(), order.getOrderId(), recvWindow, format);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     Query Order (USER_DATA)</a>
     * @return status of an order as {@link SpotOrderStatus} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return getOrderStatus(symbol, orderId, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     Query Order (USER_DATA)</a>
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(String symbol, long orderId, long recvWindow, ReturnFormat format) throws Exception {
        return returnOrderStatus(sendGetSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol=" + symbol
                + "&orderId=" + orderId + "&recvWindow=" + recvWindow), format);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     Query Order (USER_DATA)</a>
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     */
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public SpotOrderStatus getOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return getOrderStatus(symbol, origClientOrderId, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get status of an order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param recvWindow:        time to keep alive request, then rejected. Max value is 60000
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return status of an order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     * Query Order (USER_DATA)</a>
     */
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/order")
    public <T> T getOrderStatus(String symbol, String origClientOrderId, long recvWindow,
                                ReturnFormat format) throws Exception {
        return returnOrderStatus(sendGetSignedRequest(SPOT_ORDER_ENDPOINT, getTimestampParam() + "&symbol=" + symbol +
                "&origClientOrderId=" + origClientOrderId + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create a spot order status
     *
     * @param orderResponse: obtained from Binance's response
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return spot order status as {@code "format"} defines
     */
    @Returner
    private <T> T returnOrderStatus(String orderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotOrderStatus(new JSONObject(orderResponse));
            default:
                return (T) orderResponse;
        }
    }

    /**
     * Request to cancel an existing limit order and send a new limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implNote {@code "CAS"} means {@code "CANCEL AND SEND"}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casLimitOrder(String symbol, Side side, ReplaceMode cancelReplaceMode, TimeInForce timeInForce,
                                      double quantity, double price, Params extraParams) throws Exception {
        return casLimitOrder(symbol, side, cancelReplaceMode, timeInForce, quantity, price, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing limit order and send a new limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @implNote {@code "CAS"} means {@code "CANCEL AND SEND"}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casLimitOrder(String symbol, Side side, ReplaceMode cancelReplaceMode, TimeInForce timeInForce,
                               double quantity, double price, Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, LIMIT, cancelReplaceMode, getLimitPayload(timeInForce, quantity, price,
                extraParams), format);
    }

    /**
     * Request to cancel an existing market order with quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     * @implNote {@code "CAS"} means {@code "CANCEL AND SEND"}
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casMarketOrderQty(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                          Params extraParams) throws Exception {
        return casMarketOrderQty(symbol, side, cancelReplaceMode, quantity, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing market order with quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     * @implNote {@code "CAS"} means {@code "CANCEL AND SEND"}
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casMarketOrderQty(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                          Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, MARKET, cancelReplaceMode, getMarketPayload("quantity", quantity,
                extraParams), format);
    }

    /**
     * Request to cancel an existing market order with quote quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quoteQuantity:     quote quantity value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casMarketOrderQuoteQty(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                               double quoteQuantity, Params extraParams) throws Exception {
        return casMarketOrderQuoteQty(symbol, side, cancelReplaceMode, quoteQuantity, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing market order with quote quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quoteQuantity:     quote quantity value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casMarketOrderQuoteQty(String symbol, Side side, ReplaceMode cancelReplaceMode, double quoteQuantity,
                                        Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, MARKET, cancelReplaceMode, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams), format);
    }

    /**
     * Request to cancel an existing stop loss order with price and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casStopLossOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                              double stopPrice, Params extraParams) throws Exception {
        return casStopLossOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing stop loss order with price and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casStopLossOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                       double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS, cancelReplaceMode, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /**
     * Request to cancel an existing stop loss order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casStopLossOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                              double trailingDelta, Params extraParams) throws Exception {
        return casStopLossOrderDelta(symbol, side, cancelReplaceMode, quantity, trailingDelta, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing stop loss order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casStopLossOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                       double trailingDelta, Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS, cancelReplaceMode, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /**
     * Request to cancel an existing stop loss limit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casStopLossLimitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                                   TimeInForce timeInForce, double quantity, double price,
                                                   double stopPrice, Params extraParams) throws Exception {
        return casStopLossLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce, quantity, price, stopPrice,
                extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing stop loss limit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casStopLossLimitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                            TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                            Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to cancel an existing stop loss limit order with delta and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casStopLossLimitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                                   TimeInForce timeInForce, double quantity, double price,
                                                   double trailingDelta, Params extraParams) throws Exception {
        return casStopLossLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce, quantity, price, trailingDelta,
                extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing stop loss limit order with delta and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casStopLossLimitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                            TimeInForce timeInForce, double quantity, double price, double trailingDelta,
                                            Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, trailingDelta, "trailingDelta", trailingDelta, extraParams), format);
    }

    /**
     * Request to cancel an existing take profit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casTakeProfitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                                double stopPrice, Params extraParams) throws Exception {
        return casTakeProfitOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing take profit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casTakeProfitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                         double stopPrice, Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT, cancelReplaceMode, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams), format);
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casTakeProfitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                                double trailingDelta, Params extraParams) throws Exception {
        return casTakeProfitOrderDelta(symbol, side, cancelReplaceMode, quantity, trailingDelta, extraParams,
                LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casTakeProfitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                         double trailingDelta, Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT, cancelReplaceMode, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams), format);
    }

    /**
     * Request to cancel an existing take profit limit order with price and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casTakeProfitLimitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                                     TimeInForce timeInForce, double quantity, double price,
                                                     double stopPrice, Params extraParams) throws Exception {
        return casTakeProfitLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce, quantity, price, stopPrice,
                extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing take profit limit order with price and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casTakeProfitLimitOrderPrice(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                              TimeInForce timeInForce, double quantity, double price, double stopPrice,
                                              Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams), format);
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casTakeProfitLimitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                                     TimeInForce timeInForce, double quantity, double price,
                                                     double trailingDelta, Params extraParams) throws Exception {
        return casTakeProfitLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce, quantity, price, trailingDelta,
                extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casTakeProfitLimitOrderDelta(String symbol, Side side, ReplaceMode cancelReplaceMode,
                                              TimeInForce timeInForce, double quantity, double price, double trailingDelta,
                                              Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, trailingDelta, "trailingDelta", trailingDelta, extraParams), format);
    }

    /**
     * Request to cancel an existing limit maker order with delta and send a new limit maker spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public SpotOrderCAS casLimitMakerOrder(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity,
                                           double price, Params extraParams) throws Exception {
        return casLimitMakerOrder(symbol, side, cancelReplaceMode, quantity, price, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel an existing limit maker order with delta and send a new limit maker spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                           <ul>
     *                               <li>
     *                                   {@code "timeInForce"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                               </li>
     *                               <li>
     *                                   {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                               </li>
     *                               <li>
     *                                   {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                   if not sent - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "icebergQty"} -> to create an iceberg order - [DECIMAL]
     *                                 </li>
     *                                 <li>
     *                                     {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                                     on the symbol. The possible supported values are, constants available
     *                                     {@link SelfTradePreventionMode} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "cancelRestrictions"} -> cancel restriction value, constants available
     *                                     {@link CancelRestriction} - [STRING]
     *                                 </li>
     *                                 <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                 </li>
     *                           </ul>
     * @param format:            return type formatter -> {@link ReturnFormat}
     * @return result of cancellation of an order and creation of a new order as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * Cancel an Existing Order and Send a New Order (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/api/v3/order/cancelReplace")
    public <T> T casLimitMakerOrder(String symbol, Side side, ReplaceMode cancelReplaceMode, double quantity, double price,
                                    Params extraParams, ReturnFormat format) throws Exception {
        return cancelAndSendOrder(symbol, side, LIMIT_MAKER, cancelReplaceMode, getLimitMakerPayload(quantity, price,
                extraParams), format);
    }

    /**
     * Request to cancel and resend a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order
     * @param type:              LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param cancelReplaceMode: mode to replace order -> {@link ReplaceMode#STOP_ON_FAILURE}
     *                           or {@link ReplaceMode#ALLOW_FAILURE}
     * @param extraParams:       additional params of the request
     * @param format:            return type formatter -> {@link ReturnFormat}
     **/
    @Returner
    private <T> T cancelAndSendOrder(String symbol, Side side, OrderType type, ReplaceMode cancelReplaceMode,
                                     Params extraParams, ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("type", type);
        extraParams.addParam("cancelReplaceMode", cancelReplaceMode);
        String opeResponse = sendPostSignedRequest(CANCEL_AND_SEND_ORDER_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(opeResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotOrderCAS(new JSONObject(opeResponse));
            default:
                return (T) opeResponse;
        }
    }

    /**
     * Request to get current open orders list <br>
     * No-any params required
     *
     * @return current open orders list as {@link ArrayList} of {@link SpotOrderStatus}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     * Current Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrders")
    public ArrayList<SpotOrderStatus> getCurrentOpenOrders() throws Exception {
        return getCurrentOpenOrders(LIBRARY_OBJECT);
    }

    /**
     * Request to get current open orders list
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return current open orders list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     * Current Open Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "3/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrders")
    public <T> T getCurrentOpenOrders(ReturnFormat format) throws Exception {
        return returnOrdersList(sendGetSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get current open orders list
     *
     * @param extraParams: extra params of the request, keys accepted are:
     *                     <ul>
     *                         <li>
     *                             {@code "symbol"} -> symbol value - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                     </ul>
     * @return current open orders list as {@link ArrayList} of {@link SpotOrderStatus}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     * Current Open Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrders")
    public ArrayList<SpotOrderStatus> getCurrentOpenOrders(Params extraParams) throws Exception {
        return getCurrentOpenOrders(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get current open orders list
     * @param extraParams: extra params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "symbol"} -> symbol value - [STRING]
     *                       </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *    Current Open Orders (USER_DATA)</a>
     * @return current open orders list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "3/40(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrders")
    public <T> T getCurrentOpenOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnOrdersList(sendGetSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /** Request to get all orders list
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     All Orders (USER_DATA)</a>
     * @return all orders list as {@link ArrayList} of {@link SpotOrderStatus}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrders")
    public ArrayList<SpotOrderStatus> getAllOrders(String symbol) throws Exception {
        return getAllOrders(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get all orders list
     *
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all orders list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     * All Orders (USER_DATA)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrders")
    public <T> T getAllOrders(String symbol, ReturnFormat format) throws Exception {
        return returnOrdersList(sendGetSignedRequest(SPOT_ALL_ORDERS_LIST_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol), format);
    }

    /**
     * Request to get all orders list
     *
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @return all orders list as {@link ArrayList} of {@link SpotOrderStatus}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     * All Orders (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrders")
    public ArrayList<SpotOrderStatus> getAllOrders(String symbol, Params extraParams) throws Exception {
        return getAllOrders(symbol, extraParams, LIBRARY_OBJECT);
    }

    /** Request to get all orders list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> order identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     All Orders (USER_DATA)</a>
     * @return all orders list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrders")
    public <T> T getAllOrders(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnOrdersList(sendGetSignedRequest(SPOT_ALL_ORDERS_LIST_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol, extraParams)), format);
    }

    /**
     * Method to create a spot order status
     *
     * @param ordersResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return spot order status as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOrdersList(String ordersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(ordersResponse);
            case LIBRARY_OBJECT:
                ArrayList<SpotOrderStatus> orders = new ArrayList<>();
                JSONArray jOrders = new JSONArray(ordersResponse);
                for (int j = 0; j < jOrders.length(); j++)
                    orders.add(new SpotOrderStatus(jOrders.getJSONObject(j)));
                return (T) orders;
            default:
                return (T) ordersResponse;
        }
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     New OCO (TRADE)</a>
     * @return oco order response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public ComposedSpotOrderDetails sendNewOCOOrder(String symbol, Side side, double price, double stopPrice) throws Exception {
        return sendNewOCOOrder(symbol, side, price, stopPrice, LIBRARY_OBJECT);
    }

    /**
     * Request to send new oco order
     *
     * @param symbol:    symbol used in new oco order es. BTCBUSD
     * @param side:      side of the order BUY,SELL
     * @param price:     amount used in the order
     * @param stopPrice: amount to stop order
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return oco order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     * New OCO (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public <T> T sendNewOCOOrder(String symbol, Side side, double price, double stopPrice,
                                 ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("symbol", symbol);
        payload.addParam("side", side);
        payload.addParam("price", price);
        payload.addParam("stopPrice", stopPrice);
        return returnComposedOrderDetails(sendPostSignedRequest(SPOT_OCO_ORDER_ENDPOINT, payload), format);
    }

    /**
     * Request to send new oco order
     *
     * @param symbol:               symbol used in new oco order es. BTCBUSD
     * @param side:                 side of the order BUY,SELL
     * @param price:                amount used in the order
     * @param stopPrice:            amount used to stop order
     * @param stopLimitPrice:       amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @return oco order response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     * New OCO (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public ComposedSpotOrderDetails sendNewOCOOrder(String symbol, Side side, double price, double stopPrice,
                                                    double stopLimitPrice, TimeInForce stopLimitTimeInForce) throws Exception {
        return sendNewOCOOrder(symbol, side, price, stopPrice, stopLimitPrice, stopLimitTimeInForce, LIBRARY_OBJECT);
    }

    /**
     * Request to send new oco order
     *
     * @param symbol:               symbol used in new oco order es. BTCBUSD
     * @param side:                 side of the order BUY,SELL
     * @param price:                amount used in the order
     * @param stopPrice:            amount used to stop order
     * @param stopLimitPrice:       amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param format:               return type formatter -> {@link ReturnFormat}
     * @return oco order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     * New OCO (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public <T> T sendNewOCOOrder(String symbol, Side side, double price, double stopPrice, double stopLimitPrice,
                                 TimeInForce stopLimitTimeInForce, ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("symbol", symbol);
        payload.addParam("side", side);
        payload.addParam("price", price);
        payload.addParam("stopPrice", stopPrice);
        payload.addParam("stopLimitPrice", stopLimitPrice);
        payload.addParam("stopLimitTimeInForce", stopLimitTimeInForce);
        return returnComposedOrderDetails(sendPostSignedRequest(SPOT_OCO_ORDER_ENDPOINT, payload), format);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quantity"} -> order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "limitClientOrderId"} -> unique Id for the limit order - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "limitIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> stop limit time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitPrice"} -> If provided, stopLimitTimeInForce is required - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> unique Id for the stop loss/stop loss limit leg - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                             on the symbol. The possible supported values are, constants available
     *                             {@link SelfTradePreventionMode} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     New OCO (TRADE)</a>
     * @return oco order response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public ComposedSpotOrderDetails sendNewOCOOrder(String symbol, Side side, double price, double stopPrice,
                                                    Params extraParams) throws Exception {
        return sendNewOCOOrder(symbol, side, price, stopPrice, extraParams, LIBRARY_OBJECT);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quantity"} -> order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "limitClientOrderId"} -> unique Id for the limit order - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "limitIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> stop limit time in force, constants available {@link TimeInForce} - [ENUM]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitPrice"} -> If provided, stopLimitTimeInForce is required - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> unique Id for the stop loss/stop loss limit leg - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                             on the symbol. The possible supported values are, constants available
     *                             {@link SelfTradePreventionMode} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                   </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     New OCO (TRADE)</a>
     * @return oco order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public <T> T sendNewOCOOrder(String symbol, Side side, double price, double stopPrice, Params extraParams,
                                 ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("price", price);
        extraParams.addParam("stopPrice", stopPrice);
        return returnComposedOrderDetails(sendPostSignedRequest(SPOT_OCO_ORDER_ENDPOINT, extraParams), format);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quantity"} -> order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "limitClientOrderId"} -> unique Id for the limit order - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "limitIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> unique Id for the stop loss/stop loss limit leg - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                             on the symbol. The possible supported values are, constants available
     *                             {@link SelfTradePreventionMode} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     New OCO (TRADE)</a>
     * @return oco order response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public ComposedSpotOrderDetails sendNewOCOOrder(String symbol, Side side, double price, double stopPrice,
                                                    double stopLimitPrice, TimeInForce stopLimitTimeInForce,
                                                    Params extraParams) throws Exception {
        return sendNewOCOOrder(symbol, side, price, stopPrice, stopLimitPrice, stopLimitTimeInForce, extraParams,
                LIBRARY_OBJECT);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param extraParams: additional params of the request, keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "listClientOrderId"} -> time in force, constants available {@link TimeInForce} - [ENUM]
     *                       </li>
     *                       <li>
     *                           {@code "quantity"} -> order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "quoteOrderQty"} -> quote order quantity - [DECIMAL]
     *                       </li>
     *                       <li>
     *                           {@code "limitClientOrderId"} -> unique Id for the limit order - [LONG]
     *                       </li>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                         </li>
     *                         <li>
     *                              {@code "stopPrice"} -> stop price - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "limitIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopIcebergQty"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                             {@code "stopClientOrderId"} -> unique Id for the stop loss/stop loss limit leg - [LONG]
     *                         </li>
     *                         <li>
     *                             {@code "stopLimitTimeInForce"} -> to create an iceberg order - [DECIMAL]
     *                         </li>
     *                         <li>
     *                         <li>
     *                             {@code "newOrderRespType"} -> response type, constants {@link OrderResponseType} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "selfTradePreventionMode"} -> the allowed enums is dependent on what is configured
     *                             on the symbol. The possible supported values are, constants available
     *                             {@link SelfTradePreventionMode} - [STRING]
     *                         </li>
     *                         <li>
     *                             {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                         </li>
     *                   </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     New OCO (TRADE)</a>
     * @return oco order response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP) / 2(UID)")
    @RequestPath(method = POST, path = "/api/v3/order/oco")
    public <T> T sendNewOCOOrder(String symbol, Side side, double price, double stopPrice, double stopLimitPrice,
                                 TimeInForce stopLimitTimeInForce, Params extraParams,
                                 ReturnFormat format) throws Exception {
        extraParams.addParam("symbol", symbol);
        extraParams.addParam("side", side);
        extraParams.addParam("price", price);
        extraParams.addParam("stopPrice", stopPrice);
        extraParams.addParam("stopLimitPrice", stopLimitPrice);
        extraParams.addParam("stopLimitTimeInForce", stopLimitTimeInForce);
        return returnComposedOrderDetails(sendPostSignedRequest(SPOT_OCO_ORDER_ENDPOINT, extraParams), format);
    }

    /** Request to cancel all OCO orders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     Cancel OCO (TRADE)</a>
     * @return cancel all OCO orders response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public ComposedSpotOrderDetails cancelAllOCOOrders(String symbol, long orderListId) throws Exception {
        return cancelAllOCOOrders(symbol, orderListId, LIBRARY_OBJECT);
    }

    /** Request to cancel all OCO orders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     Cancel OCO (TRADE)</a>
     * @return cancel all OCO orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public <T> T cancelAllOCOOrders(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnComposedOrderDetails(sendDeleteSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol + "&orderListId=" + orderListId), format);
    }

    /** Request to cancel all OCO orders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     Cancel OCO (TRADE)</a>
     * @return cancel all OCO orders response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public ComposedSpotOrderDetails cancelAllOCOOrders(String symbol, String listClientOrderId) throws Exception {
        return cancelAllOCOOrders(symbol, listClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel all OCO orders
     *
     * @param #symbol:            symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return cancel all OCO orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     * Cancel OCO (TRADE)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public <T> T cancelAllOCOOrders(String symbol, String listClientOrderId, ReturnFormat format) throws Exception {
        return returnComposedOrderDetails(sendDeleteSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId), format);
    }

    /** Request to cancel all OCO orders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     Cancel OCO (TRADE)</a>
     * @return cancel all OCO orders response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public ComposedSpotOrderDetails cancelAllOCOOrders(String symbol, long orderListId, Params extraParams) throws Exception {
        return cancelAllOCOOrders(symbol, orderListId, extraParams, LIBRARY_OBJECT);
    }

    /** Request to cancel all OCO orders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                   <ul>
     *                       <li>
     *                           {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                           if not sent - [STRING]
     *                        </li>
     *                       <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                       </li>
     *                   </ul>
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     Cancel OCO (TRADE)</a>
     * @return cancel all OCO orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public <T> T cancelAllOCOOrders(String symbol, long orderListId, Params extraParams,
                                    ReturnFormat format) throws Exception {
        return returnComposedOrderDetails(sendDeleteSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&orderListId=" + orderListId, extraParams)), format);
    }

    /**
     * Request to cancel all OCO orders
     *
     * @param #symbol:            symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams:        additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                            <ul>
     *                                <li>
     *                                    {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                    if not sent - [STRING]
     *                                 </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                </li>
     *                            </ul>
     * @return cancel all OCO orders response as {@link ComposedSpotOrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     * Cancel OCO (TRADE)</a>
     **/
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public ComposedSpotOrderDetails cancelAllOCOOrders(String symbol, String listClientOrderId,
                                                       Params extraParams) throws Exception {
        return cancelAllOCOOrders(symbol, listClientOrderId, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to cancel all OCO orders
     *
     * @param #symbol:            symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams:        additional params of the request (insert {@code "null"} if there are no extra params), keys accepted are:
     *                            <ul>
     *                                <li>
     *                                    {@code "newClientOrderId"} -> a unique id among open orders. Automatically generated
     *                                    if not sent - [STRING]
     *                                 </li>
     *                                <li>
     *                                     {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                                </li>
     *                            </ul>
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return cancel all OCO orders response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     * Cancel OCO (TRADE)</a>
     **/
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = DELETE, path = "/api/v3/orderList")
    public <T> T cancelAllOCOOrders(String symbol, String listClientOrderId, Params extraParams,
                                    ReturnFormat format) throws Exception {
        return returnComposedOrderDetails(sendDeleteSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam() + "&symbol=" + symbol +
                        "&listClientOrderId=" + listClientOrderId, extraParams)), format);
    }

    /**
     * Method to create a composed spot order details
     *
     * @param composedDetailsResponse: obtained from Binance's response
     * @param format:                  return type formatter -> {@link ReturnFormat}
     * @return composed spot order details as {@code "format"} defines
     **/
    @Returner
    private <T> T returnComposedOrderDetails(String composedDetailsResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(composedDetailsResponse);
            case LIBRARY_OBJECT:
                return (T) new ComposedSpotOrderDetails(new JSONObject(composedDetailsResponse));
            default:
                return (T) composedDetailsResponse;
        }
    }

    /**
     * Request to get OCO order status
     *
     * @param #symbol:      symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @return OCO order status response as {@link OrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     * Query OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public OrderDetails getOCOOrderStatus(String symbol, long orderListId) throws Exception {
        return getOCOOrderStatus(symbol, orderListId, LIBRARY_OBJECT);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param format:                return type formatter -> {@link ReturnFormat}

     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *    Query OCO (USER_DATA)</a>
     * @return OCO order status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public <T> T getOCOOrderStatus(String symbol, long orderListId, ReturnFormat format) throws Exception {
        return returnOrderStatus(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&orderListId=" + orderListId), format);
    }

    /**
     * Request to get OCO order status
     *
     * @param #symbol:      symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow:  time to keep alive request, then rejected. Max value is 60000
     * @return OCO order status response as {@link OrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     **/
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public OrderDetails getOCOOrderStatus(String symbol, long orderListId, long recvWindow) throws Exception {
        return getOCOOrderStatus(symbol, orderListId, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return OCO order status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public <T> T getOCOOrderStatus(String symbol, long orderListId, long recvWindow, ReturnFormat format) throws Exception {
        return returnOCOOrderStatus(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&orderListId=" + orderListId + "&recvWindow=" + recvWindow), format);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN

     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *    Query OCO (USER_DATA)</a>
     * @return OCO order status response as {@link OrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public OrderDetails getOCOOrderStatus(String symbol, String listClientOrderId) throws Exception {
        return getOCOOrderStatus(symbol, listClientOrderId, LIBRARY_OBJECT);
    }

    /**
     * Request to get OCO order status
     *
     * @param #symbol:            symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param format:             return type formatter -> {@link ReturnFormat}
     * @return OCO order status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     * Query OCO (USER_DATA)</a>
     **/
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public <T> T getOCOOrderStatus(String symbol, String listClientOrderId, ReturnFormat format) throws Exception {
        return returnOCOOrderStatus(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() + "&symbol="
                + symbol + "&listClientOrderId=" + listClientOrderId), format);
    }

    /**
     * Request to get OCO order status
     *
     * @param #symbol:            symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow:        time to keep alive request, then rejected. Max value is 60000
     * @return OCO order status response as {@link OrderDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     * Query OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public OrderDetails getOCOOrderStatus(String symbol, String listClientOrderId, long recvWindow) throws Exception {
        return getOCOOrderStatus(symbol, listClientOrderId, recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *    Query OCO (USER_DATA)</a>
     * @return OCO order status response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "2(IP)")
    @RequestPath(method = GET, path = "/api/v3/orderList")
    public <T> T getOCOOrderStatus(String symbol, String listClientOrderId, long recvWindow,
                                   ReturnFormat format) throws Exception {
        return returnOCOOrderStatus(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() +
                        "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId + "&recvWindow=" + recvWindow),
                format);
    }

    /**
     * Method to create an OCO spot order status
     *
     * @param orderResponse: obtained from Binance's response
     * @param format:        return type formatter -> {@link ReturnFormat}
     * @return oco spot order status as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOCOOrderStatus(String orderResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(orderResponse);
            case LIBRARY_OBJECT:
                return (T) new OrderDetails(new JSONObject(orderResponse));
            default:
                return (T) orderResponse;
        }
    }

    /**
     * Request to get OCO order status list <br>
     * No-any params required
     *
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     * Query all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public ArrayList<OrderDetails> getAllOCOOrders() throws Exception {
        return getAllOCOOrders(LIBRARY_OBJECT);
    }

    /** Request to get OCO order status list 
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     Query all OCO (USER_DATA)</a>
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public <T> T getAllOCOOrders(ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam()), format);
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     Query all OCO (USER_DATA)</a>
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public ArrayList<OrderDetails> getAllOCOOrders(long fromId, String timeParam, long timeParamValue) throws Exception {
        return getAllOCOOrders(fromId, timeParam, timeParamValue, LIBRARY_OBJECT);
    }

    /**
     * Request to get OCO order status list
     *
     * @param #fromId:         identifier of origin
     * @param #timeParam:      startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     * Query all OCO (USER_DATA)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public <T> T getAllOCOOrders(long fromId, String timeParam, long timeParamValue, ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getTimestampParam() +
                "&fromId=" + fromId + "&" + timeParam + "=" + timeParamValue), format);
    }

    /** Request to get OCO order status list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     Query all OCO (USER_DATA)</a>
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public ArrayList<OrderDetails> getAllOCOOrders(Params extraParams) throws Exception {
        return getAllOCOOrders(extraParams, LIBRARY_OBJECT);
    }

    /** Request to get OCO order status list
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                     </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     Query all OCO (USER_DATA)</a>
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public <T> T getAllOCOOrders(Params extraParams, ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT,
                apiRequest.encodeAdditionalParams(getTimestampParam(), extraParams)), format);
    }

    /**
     * Request to get OCO order status list
     *
     * @param #fromId:         identifier of origin
     * @param #timeParam:      startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                              <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                               <li>
     *                                    {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                               </li>
     *                               <li>
     *                                    {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                               </li>
     *                               <li>
     *                                    {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                               </li>
     *                         </ul>
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     * Query all OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public ArrayList<OrderDetails> getAllOCOOrders(long fromId, String timeParam, long timeParamValue,
                                                   Params extraParams) throws Exception {
        return getAllOCOOrders(fromId, timeParam, timeParamValue, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get OCO order status list
     *
     * @param #fromId:         identifier of origin
     * @param #timeParam:      startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                               <li>
     *                                    {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                               </li>
     *                               <li>
     *                                    {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                               </li>
     *                               <li>
     *                                    {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                               </li>
     *                              <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                              </li>
     *                         </ul>
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     * Query all OCO (USER_DATA)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/allOrderList")
    public <T> T getAllOCOOrders(long fromId, String timeParam, long timeParamValue, Params extraParams,
                                 ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&fromId=" + fromId + "&" + timeParam + "=" + timeParamValue,
                extraParams)), format);
    }

    /**
     * Request to get open OCO order list <br>
     * No-any params required
     *
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     * Query Open OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrderList")
    public ArrayList<OrderDetails> getOpenOCOOrders() throws Exception {
        return getOpenOCOOrders(LIBRARY_OBJECT);
    }

    /** Request to get open OCO order list
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     Query all OCO (USER_DATA)</a>
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "3(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrderList")
    public <T> T getOpenOCOOrders(ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT, getTimestampParam()), format);
    }

    /**
     * Request to get open OCO order list
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return OCO order status list response as {@link ArrayList} of {@link OrderDetails}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     * Query Open OCO (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "3(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrderList")
    public ArrayList<OrderDetails> getOpenOCOOrders(long recvWindow) throws Exception {
        return getOpenOCOOrders(recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get open OCO order list 
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     Query Open OCO (USER_DATA)</a>
     * @return OCO order status list response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "3(IP)")
    @RequestPath(method = GET, path = "/api/v3/openOrderList")
    public <T> T getOpenOCOOrders(long recvWindow, ReturnFormat format) throws Exception {
        return returnOCOOrdersList(sendGetSignedRequest(SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT, getTimestampParam()
                + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create a spot order status
     *
     * @param ordersResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return spot order status as {@code "format"} defines
     **/
    @Returner
    private <T> T returnOCOOrdersList(String ordersResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(ordersResponse);
            case LIBRARY_OBJECT:
                ArrayList<OrderDetails> ocoOrdersList = new ArrayList<>();
                JSONArray jOrders = new JSONArray(ordersResponse);
                for (int j = 0; j < jOrders.length(); j++)
                    ocoOrdersList.add(new OrderDetails(jOrders.getJSONObject(j)));
                return (T) ocoOrdersList;
            default:
                return (T) ordersResponse;
        }
    }

    /**
     * Request to get spot account information <br>
     * No-any params required
     *
     * @return spot account information response as  {@link SpotAccountInformation} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     * Account Information (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/account")
    public SpotAccountInformation getSpotAccountInformation() throws Exception {
        return getSpotAccountInformation(LIBRARY_OBJECT);
    }

    /**
     * Request to get spot account information
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return spot account information response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     * Account Information (USER_DATA)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/account")
    public <T> T getSpotAccountInformation(ReturnFormat format) throws Exception {
        return returnAccountInformation(sendGetSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT, getTimestampParam()), format);
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     Account Information (USER_DATA)</a>
     * @return spot account information response as  {@link SpotAccountInformation} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/account")
    public SpotAccountInformation getSpotAccountInformation(double recvWindow) throws Exception {
        return getSpotAccountInformation(recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     Account Information (USER_DATA)</a>
     * @return spot account information response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/account")
    public <T> T getSpotAccountInformation(double recvWindow, ReturnFormat format) throws Exception {
        return returnAccountInformation(sendGetSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT, getTimestampParam()
                + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Method to create an account information
     *
     * @param accountResponse: obtained from Binance's response
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return account information status as {@code "format"} defines
     **/
    @Returner
    private <T> T returnAccountInformation(String accountResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(accountResponse);
            case LIBRARY_OBJECT:
                return (T) new SpotAccountInformation(new JSONObject(accountResponse));
            default:
                return (T) accountResponse;
        }
    }

    /**
     * Request to get Account Trade List
     *
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @return Account Trade List response as {@link ArrayList} of {@link SpotAccountTradesList}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     * Account Trade List (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myTrades")
    public ArrayList<SpotAccountTradesList> getAccountTradesList(String symbol) throws Exception {
        return getAccountTradesList(symbol, LIBRARY_OBJECT);
    }

    /**
     * Request to get Account Trade List
     *
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @param format:  return type formatter -> {@link ReturnFormat}
     * @return Account Trade List response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     * Account Trade List (USER_DATA)</a>
     **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myTrades")
    public <T> T getAccountTradesList(String symbol, ReturnFormat format) throws Exception {
        return returnTradesList(sendGetSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT, getTimestampParam() +
                "&symbol=" + symbol), format);
    }

    /**
     * Request to get Account Trade List
     *
     * @param #symbol:     symbol used in AccountTradeList es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> this can only be used in combination with symbol - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> tradeId to fetch from. Default gets most recent trades - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return Account Trade List response as {@link ArrayList} of {@link SpotAccountTradesList}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     * Account Trade List (USER_DATA)</a>
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myTrades")
    public ArrayList<SpotAccountTradesList> getAccountTradesList(String symbol, Params extraParams) throws Exception {
        return getAccountTradesList(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get Account Trade List
     *
     * @param #symbol:     symbol used in AccountTradeList es. BTCBUSD
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "orderId"} -> this can only be used in combination with symbol - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get aggregate trades from INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get aggregate trades until INCLUSIVE - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromId"} -> tradeId to fetch from. Default gets most recent trades - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return Account Trade List response as {@code "format"} defines
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     * Account Trade List (USER_DATA)</a>
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myTrades")
    public <T> T getAccountTradesList(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        return returnTradesList(sendGetSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol, extraParams)), format);
    }

    /**
     * Method to create a trades list
     *
     * @param tradesResponse: obtained from Binance's response
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @return trades list as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTradesList(String tradesResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONArray(tradesResponse);
            case LIBRARY_OBJECT:
                ArrayList<SpotAccountTradesList> tradesList = new ArrayList<>();
                JSONArray jTrades = new JSONArray(tradesResponse);
                for (int j = 0; j < jTrades.length(); j++)
                    tradesList.add(new SpotAccountTradesList(jTrades.getJSONObject(j)));
                return (T) tradesList;
            default:
                return (T) tradesResponse;
        }
    }

    /**
     * Request to get current order count usage <br>
     * No-any params required
     *
     * @return current order count usage response as {@link ArrayList} of {@link OrderCountUsage}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     * Query Current Order Count Usage (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "20(IP)")
    @RequestPath(method = GET, path = "/api/v3/rateLimit/order")
    public ArrayList<OrderCountUsage> getCurrentOrderCountUsage() throws Exception {
        return getCurrentOrderCountUsage(LIBRARY_OBJECT);
    }

    /** Request to get current order count usage
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     Query Current Order Count Usage (TRADE)</a>
     * @return current order count usage response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "20(IP)")
    @RequestPath(method = GET, path = "/api/v3/rateLimit/order")
    public <T> T getCurrentOrderCountUsage(ReturnFormat format) throws Exception {
        return returnCountUsageList(sendGetSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE, getTimestampParam()), format);
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     Query Current Order Count Usage (TRADE)</a>
     * @return current order count usage response as {@link ArrayList} of {@link OrderCountUsage}
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @Wrapper
    @RequestWeight(weight = "20(IP)")
    @RequestPath(method = GET, path = "/api/v3/rateLimit/order")
    public ArrayList<OrderCountUsage> getCurrentOrderCountUsage(long recvWindow) throws Exception {
        return getCurrentOrderCountUsage(recvWindow, LIBRARY_OBJECT);
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @param format:         return type formatter -> {@link ReturnFormat}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     Query Current Order Count Usage (TRADE)</a>
     * @return current order count usage response as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                     <ul>
     *                         <li>
     *                             {@link #getErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #getJSONErrorResponse()}
     *                         </li>
     *                         <li>
     *                             {@link #printErrorResponse()}
     *                         </li>
     *                     </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * **/
    @RequestWeight(weight = "20(IP)")
    @RequestPath(method = GET, path = "/api/v3/rateLimit/order")
    public <T> T getCurrentOrderCountUsage(long recvWindow, ReturnFormat format) throws Exception {
        return returnCountUsageList(sendGetSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE,
                getTimestampParam() + "&recvWindow=" + recvWindow), format);
    }

    /**
     * Request to get prevented matches
     *
     * @param symbol:      symbol of the prevented matches to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "preventedMatchId"} -> prevented match identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "orderId"} -> this can only be used in combination with symbol - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromPreventedMatchId"} -> from prevented match identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @return prevented matches as {@link ArrayList} of {@link PreventedMatch} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     * Query Prevented Matches (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1/10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myPreventedMatches")
    public ArrayList<PreventedMatch> getPreventedMatches(String symbol, Params extraParams) throws Exception {
        return getPreventedMatches(symbol, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get prevented matches
     *
     * @param symbol:      symbol of the prevented matches to fetch
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "preventedMatchId"} -> prevented match identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "orderId"} -> this can only be used in combination with symbol - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "fromPreventedMatchId"} -> from prevented match identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "limit"} -> limit results, max 1000 - [INT, default 500]
     *                           </li>
     *                          <li>
     *                            {@code "recvWindow"} -> request is valid for in ms, must be less than 60000 - [LONG, default 5000]
     *                          </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return prevented matches as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     * Query Prevented Matches (USER_DATA)</a>
     **/
    @Returner
    @RequestWeight(weight = "1/10(IP)")
    @RequestPath(method = GET, path = "/api/v3/myPreventedMatches")
    public <T> T getPreventedMatches(String symbol, Params extraParams, ReturnFormat format) throws Exception {
        String preventedMatchesResponse = sendGetSignedRequest(MY_PREVENTED_MATCHES, apiRequest.encodeAdditionalParams(
                getTimestampParam() + "&symbol=" + symbol, extraParams));
        switch (format) {
            case JSON:
                return (T) new JSONArray(preventedMatchesResponse);
            case LIBRARY_OBJECT:
                ArrayList<PreventedMatch> preventedMatches = new ArrayList<>();
                JSONArray jPreventedMatches = new JSONArray(preventedMatchesResponse);
                for (int j = 0; j < jPreventedMatches.length(); j++)
                    preventedMatches.add(new PreventedMatch(jPreventedMatches.getJSONObject(j)));
                return (T) preventedMatches;
            default:
                return (T) preventedMatchesResponse;
        }
    }

}
