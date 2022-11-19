package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot;

import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.OrderDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.common.TradeConstants;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account.OrderCountUsage;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account.SpotAccountInformation;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account.SpotAccountTradeList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.ComposedSpotOrderDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.DetailSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.OpenSpotOrders;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.SpotOrderCAS;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.ACKSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.FullSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.ResultSpotOrder;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.response.SpotOrderStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.*;
import static com.tecknobit.binancemanager.constants.EndpointsList.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.common.TradeConstants.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.SpotOrder.*;

/**
 *  The {@code BinanceSpotManager} class is useful to manage all Binance Spot Endpoints
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceSpotManager extends BinanceSignedManager {

    /** Constructor to init BinanceSpotManager
     * @param baseEndpoint base endpoint to work on
     * @param apiKey your api key
     * @param secretKey your secret key
     * **/
    public BinanceSpotManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /** Constructor to init BinanceSpotManager
     * @param apiKey your api key
     * @param secretKey your secret key
     * automatically set a working endpoint
     * **/
    public BinanceSpotManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null, apiKey, secretKey);
    }

    /**
     * Request to test a spot order
     *
     * @param symbol:      symbol used in the request es. BTCBUSD
     * @param side:        side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type:        LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request
     * @return result of the order WITHOUT buy or sell nothing, if is correct return "{}" else error of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     **/
    public String testNewOrder(String symbol, String side, String type, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type;
        return sendSignedRequest(SPOT_TEST_NEW_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(params, extraParams),
                POST_METHOD);
    }

    /** Request to test a spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade</a>
     * @return result of the order WITHOUT buy or sell nothing, if is correct return "{}" else error of the request
     * **/
    public String testNewOrder(String symbol, String side, String type, String newOrderRespType,
                               Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type
                 + "&newOrderRespType=" + newOrderRespType;
        return sendSignedRequest(SPOT_TEST_NEW_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(params, extraParams),
                POST_METHOD);
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendLimitOrder(String symbol, String side, String timeInForce, double quantity,
                                 double price, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, LIMIT, getLimitPayload(timeInForce, quantity, price, extraParams));
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendLimitOrderJSON(String symbol, String side, String timeInForce, double quantity,
                                         double price, Params extraParams) throws Exception {
        return new JSONObject(sendLimitOrder(symbol, side, timeInForce, quantity, price, extraParams));
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendLimitOrderObject(String symbol, String side, String timeInForce, double quantity,
                                                           double price, Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, LIMIT, getLimitPayload(timeInForce, quantity, price, extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendMarketOrderQty(String symbol, String side, double quantity,
                                     Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, MARKET, getMarketPayload("quantity", quantity, extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendMarketOrderQtyJSON(String symbol, String side, double quantity,
                                             Params extraParams) throws Exception {
        return new JSONObject(sendMarketOrderQty(symbol, side, quantity, extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendMarketOrderQtyObject(String symbol, String side, double quantity,
                                                               Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, MARKET, getMarketPayload("quantity", quantity, extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendMarketOrderQuoteQty(String symbol, String side, double quoteQuantity,
                                          Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, MARKET, getMarketPayload("quoteOrderQty", quoteQuantity, extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendMarketOrderQuoteQtyJSON(String symbol, String side, double quoteQuantity,
                                                  Params extraParams) throws Exception {
        return new JSONObject(sendMarketOrderQuoteQty(symbol, side, quoteQuantity, extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendMarketOrderQuoteQtyObject(String symbol, String side, double quoteQuantity,
                                                                    Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, MARKET, getMarketPayload("quoteOrderQty", quoteQuantity,
                extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossOrderPrice(String symbol, String side, double quantity, double stopPrice,
                                         Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, STOP_LOSS, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossOrderPriceJSON(String symbol, String side, double quantity, double stopPrice,
                                                 Params extraParams) throws Exception {
        return new JSONObject(sendStopLossOrderPrice(symbol, side, quantity, stopPrice, extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossOrderPriceObject(String symbol, String side, double quantity, double stopPrice,
                                                                   Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, STOP_LOSS, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossOrderDelta(String symbol, String side, double quantity, double trailingDelta,
                                         Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, STOP_LOSS, getLevelPayload(quantity, "trailingDelta", trailingDelta,
                extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossOrderDeltaJSON(String symbol, String side, double quantity, double trailingDelta,
                                                 Params extraParams) throws Exception {
        return new JSONObject(sendStopLossOrderDelta(symbol, side, quantity, trailingDelta, extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossOrderDeltaObject(String symbol, String side, double quantity,
                                                                   double trailingDelta,
                                                                   Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, STOP_LOSS, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossLimitOrderPrice(String symbol, String side, String timeInForce, double quantity,
                                              double price, double stopPrice, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossLimitOrderPriceJSON(String symbol, String side, String timeInForce, double quantity,
                                                      double price, double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderPrice(symbol, side, timeInForce, quantity, price, stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossLimitOrderPriceObject(String symbol, String side, String timeInForce,
                                                                        double quantity, double price, double stopPrice,
                                                                        Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossLimitOrderDelta(String symbol, String side, String timeInForce, double quantity,
                                              double price, double trailingDelta, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity, trailingDelta,
                "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossLimitOrderDeltaJSON(String symbol, String side, String timeInForce, double quantity,
                                                      double price, double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderDelta(symbol, side, timeInForce, quantity, price, trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossLimitOrderDeltaObject(String symbol, String side, String timeInForce,
                                                                        double quantity, double price, double trailingDelta,
                                                                        Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitOrderPrice(String symbol, String side, double quantity, double stopPrice,
                                           Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitOrderPriceJSON(String symbol, String side, double quantity, double stopPrice,
                                                   Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitOrderPrice(symbol, side, quantity, stopPrice, extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitOrderPriceObject(String symbol, String side, double quantity,
                                                                     double stopPrice, Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitOrderDelta(String symbol, String side, double quantity, double trailingDelta,
                                           Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, TAKE_PROFIT, getLevelPayload(quantity, "trailingDelta", trailingDelta,
                extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitOrderDeltaJSON(String symbol, String side, double quantity, double trailingDelta,
                                                   Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitOrderDelta(symbol, side, quantity, trailingDelta, extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitOrderDeltaObject(String symbol, String side, double quantity,
                                                                     double trailingDelta,
                                                                     Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, TAKE_PROFIT, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitLimitOrderPrice(String symbol, String side, String timeInForce, double quantity,
                                                double price, double stopPrice, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitLimitOrderPriceJSON(String symbol, String side, String timeInForce, double quantity,
                                                        double price, double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderPrice(symbol, side, timeInForce, quantity, price, stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitLimitOrderPriceObject(String symbol, String side, String timeInForce,
                                                                          double quantity, double price, double stopPrice,
                                                                          Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitLimitOrderDelta(String symbol, String side, String timeInForce, double quantity,
                                                double price, double trailingDelta, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity, trailingDelta,
                "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitLimitOrderDeltaJSON(String symbol, String side, String timeInForce, double quantity,
                                                        double price, double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitLimitOrderDelta(symbol, side, timeInForce, quantity, price, trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitLimitOrderDeltaObject(String symbol, String side, String timeInForce,
                                                                          double quantity, double price, double trailingDelta,
                                                                          Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity, price,
                "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendLimitMakerOrder(String symbol, String side, double quantity, double price,
                                      Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, LIMIT_MAKER, getLimitMakerPayload(quantity, price, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendLimitMakerOrderJSON(String symbol, String side, double quantity, double price,
                                              Params extraParams) throws Exception {
        return new JSONObject(sendLimitMakerOrder(symbol, side, quantity, price, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendLimitMakerOrderObject(String symbol, String side, double quantity, double price,
                                                                Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, LIMIT_MAKER, getLimitMakerPayload(quantity, price, extraParams));
    }

    /** Request to send a spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    private String sendNewOrder(String symbol, String side, String type, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(params, extraParams),
                POST_METHOD);
    }

    /** Request to send a spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as AckOrder
     * @implNote if type LIMIT or MARKET will be must cast in {@link FullSpotOrder} object
     * @implNote with other types will be an {@link ACKSpotOrder} object
     * **/
    private <T extends ACKSpotOrder> T sendNewOrderObject(String symbol, String side, String type, Params extraParams) throws Exception {
        JSONObject spotOrder = new JSONObject(sendNewOrder(symbol, side, type, extraParams));
        if (type.equals(LIMIT) || type.equals(MARKET))
            return (T) new FullSpotOrder(spotOrder);
        else
            return (T) new ACKSpotOrder(spotOrder);
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendLimitOrder(String symbol, String side, String newOrderRespType, String timeInForce, double quantity,
                                 double price, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, LIMIT, getLimitPayload(timeInForce, quantity, price, extraParams));
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendLimitOrderJSON(String symbol, String side, String newOrderRespType, String timeInForce, double quantity,
                                         double price, Params extraParams) throws Exception {
        return new JSONObject(sendLimitOrder(symbol, side, newOrderRespType, timeInForce, quantity, price, extraParams));
    }

    /** Request to send a limit spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendLimitOrderObject(String symbol, String side, String newOrderRespType,
                                                           String timeInForce, double quantity, double price,
                                                           Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, LIMIT, getLimitPayload(timeInForce, quantity, price,
                extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendMarketOrderQty(String symbol, String side, String newOrderRespType, double quantity,
                                     Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quantity", quantity,
                extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendMarketOrderQtyJSON(String symbol, String side, String newOrderRespType, double quantity,
                                             Params extraParams) throws Exception {
        return new JSONObject(sendMarketOrderQty(symbol, side, newOrderRespType, quantity, extraParams));
    }

    /** Request to send a market spot order with quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendMarketOrderQtyObject(String symbol, String side, String newOrderRespType,
                                                               double quantity, Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, MARKET, getMarketPayload("quantity", quantity,
                extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendMarketOrderQuoteQty(String symbol, String side, String newOrderRespType, double quoteQuantity,
                                          Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, MARKET, getMarketPayload("quoteOrderQty", quoteQuantity,
                extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendMarketOrderQuoteQtyJSON(String symbol, String side, String newOrderRespType, double quoteQuantity,
                                                  Params extraParams) throws Exception {
        return new JSONObject(sendMarketOrderQuoteQty(symbol, side, newOrderRespType, quoteQuantity, extraParams));
    }

    /** Request to send a market spot order with quote quantity
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quoteQuantity: quote quantity value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendMarketOrderQuoteQtyObject(String symbol, String side, String newOrderRespType,
                                                                    double quoteQuantity, Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, MARKET, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossOrderPrice(String symbol, String side, String newOrderRespType, double quantity, double stopPrice,
                                         Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossOrderPriceJSON(String symbol, String side, String newOrderRespType, double quantity,
                                                 double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(sendStopLossOrderPrice(symbol, side, newOrderRespType, quantity, stopPrice, extraParams));
    }

    /** Request to send a stop loss spot order with stopPrice
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossOrderPriceObject(String symbol, String side, String newOrderRespType,
                                                                   double quantity, double stopPrice,
                                                                   Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossOrderDelta(String symbol, String side, String newOrderRespType, double quantity,
                                         double trailingDelta, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossOrderDeltaJSON(String symbol, String side, String newOrderRespType, double quantity,
                                                 double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(sendStopLossOrderDelta(symbol, side, newOrderRespType, quantity, trailingDelta, extraParams));
    }

    /** Request to send a stop loss spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossOrderDeltaObject(String symbol, String side, String newOrderRespType,
                                                                   double quantity, double trailingDelta,
                                                                   Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, STOP_LOSS, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossLimitOrderPrice(String symbol, String side, String newOrderRespType, String timeInForce,
                                              double quantity, double price, double stopPrice,
                                              Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity,
                price, "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossLimitOrderPriceJSON(String symbol, String side, String newOrderRespType, String timeInForce,
                                                      double quantity, double price, double stopPrice,
                                                      Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderPrice(symbol, side, newOrderRespType, timeInForce, quantity, price,
                stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value for the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossLimitOrderPriceObject(String symbol, String side, String newOrderRespType,
                                                                        String timeInForce, double quantity, double price,
                                                                        double stopPrice, Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendStopLossLimitOrderDelta(String symbol, String side, String newOrderRespType, String timeInForce,
                                              double quantity, double price, double trailingDelta,
                                              Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce, quantity,
                trailingDelta, "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendStopLossLimitOrderDeltaJSON(String symbol, String side, String newOrderRespType, String timeInForce,
                                                      double quantity, double price, double trailingDelta,
                                                      Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderDelta(symbol, side, newOrderRespType, timeInForce, quantity, price,
                trailingDelta, extraParams));
    }

    /** Request to send a stop loss limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendStopLossLimitOrderDeltaObject(String symbol, String side, String newOrderRespType,
                                                                        String timeInForce, double quantity, double price,
                                                                        double trailingDelta,
                                                                        Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, STOP_LOSS_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitOrderPrice(String symbol, String side, String newOrderRespType, double quantity,
                                           double stopPrice, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice", stopPrice,
                extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitOrderPriceJSON(String symbol, String side, String newOrderRespType, double quantity,
                                                   double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitOrderPrice(symbol, side, newOrderRespType, quantity, stopPrice, extraParams));
    }

    /** Request to send a take profit spot order with stop price
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitOrderPriceObject(String symbol, String side, String newOrderRespType,
                                                                     double quantity, double stopPrice,
                                                                     Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitOrderDelta(String symbol, String side, String newOrderRespType, double quantity,
                                           double trailingDelta, Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitOrderDeltaJSON(String symbol, String side, String newOrderRespType, double quantity,
                                                   double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitOrderDelta(symbol, side, newOrderRespType, quantity, trailingDelta, extraParams));
    }

    /** Request to send a take profit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitOrderDeltaObject(String symbol, String side, String newOrderRespType,
                                                                     double quantity, double trailingDelta,
                                                                     Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, TAKE_PROFIT, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitLimitOrderPrice(String symbol, String side, String newOrderRespType, String timeInForce,
                                                double quantity, double price, double stopPrice,
                                                Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity,
                price, "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitLimitOrderPriceJSON(String symbol, String side, String newOrderRespType,
                                                        String timeInForce, double quantity, double price, double stopPrice,
                                                        Params extraParams) throws Exception {
        return new JSONObject(sendStopLossLimitOrderPrice(symbol, side, newOrderRespType, timeInForce, quantity, price,
                stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with stop price value
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param stopPrice: stop price value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitLimitOrderPriceObject(String symbol, String side, String newOrderRespType,
                                                                          String timeInForce, double quantity, double price,
                                                                          double stopPrice,
                                                                          Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendTakeProfitLimitOrderDelta(String symbol, String side, String newOrderRespType, String timeInForce,
                                                double quantity, double price, double trailingDelta,
                                                Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce, quantity,
                trailingDelta, "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendTakeProfitLimitOrderDeltaJSON(String symbol, String side, String newOrderRespType,
                                                        String timeInForce, double quantity, double price, double trailingDelta,
                                                        Params extraParams) throws Exception {
        return new JSONObject(sendTakeProfitLimitOrderDelta(symbol, side, newOrderRespType, timeInForce, quantity, price,
                trailingDelta, extraParams));
    }

    /** Request to send a take profit limit spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param timeInForce: time in force for the order
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param trailingDelta: trailing delta value
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendTakeProfitLimitOrderDeltaObject(String symbol, String side, String newOrderRespType,
                                                                          String timeInForce, double quantity, double price,
                                                                          double trailingDelta,
                                                                          Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, newOrderRespType, TAKE_PROFIT_LIMIT, getLevelLimitPayload(timeInForce,
                quantity, price, "trailingDelta", trailingDelta, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendLimitMakerOrder(String symbol, String side, String newOrderRespType, double quantity, double price,
                                      Params extraParams) throws Exception {
        return sendNewOrder(symbol, side, newOrderRespType, LIMIT_MAKER, getLimitMakerPayload(quantity, price, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendLimitMakerOrderJSON(String symbol, String side, String newOrderRespType, double quantity,
                                              double price, Params extraParams) throws Exception {
        return new JSONObject(sendLimitMakerOrder(symbol, side, quantity, price, extraParams));
    }

    /** Request to send a limit maker spot order with trailing delta
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param quantity: quantity value in the order
     * @param price: price value in the order
     * @param extraParams: additional params of the request, insert null if there are no extra params
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @return result of the order as {@link ACKSpotOrder}
     * **/
    public <T extends ACKSpotOrder> T sendLimitMakerOrderObject(String symbol, String side, String newOrderRespType,
                                                                double quantity, double price,
                                                                Params extraParams) throws Exception {
        return sendNewOrderObject(symbol, side, LIMIT_MAKER, newOrderRespType, getLimitMakerPayload(quantity, price,
                extraParams));
    }

    /**
     * Request to send a spot order
     *
     * @param symbol:           symbol used in the request es. BTCBUSD
     * @param side:             side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type:             LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams:      additional params of the request
     * @return result of the order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     **/
    private String sendNewOrder(String symbol, String side, String type, String newOrderRespType,
                                Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type
                + "&newOrderRespType=" + newOrderRespType;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(params, extraParams),
                POST_METHOD);
    }

    /** Request to send a spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *newOrderRespType, recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-order-trade</a>
     * @implNote in base of newOrderRespType response will be formatted as:
     * <ul>
     *      <li>if newOrderRespType = NEW_ORDER_RESP_TYPE_RESULT object will be {@link ResultSpotOrder}</li>
     *      <li>if newOrderRespType = NEW_ORDER_RESP_TYPE_FULL object will be {@link FullSpotOrder}</li>
     *      <li>if newOrderRespType = NEW_ORDER_RESP_TYPE_ACK object will be {@link ACKSpotOrder}</li>
     * </ul>
     * @return result of the order
     * **/
    private <T extends ACKSpotOrder> T sendNewOrderObject(String symbol, String side, String type, String newOrderRespType, 
                                                          Params extraParams) throws Exception {
        JSONObject order = new JSONObject(sendNewOrder(symbol, side, type, newOrderRespType, extraParams));
        switch (newOrderRespType) {
            case NEW_ORDER_RESP_TYPE_RESULT:
                return (T) new ResultSpotOrder(order);
            case NEW_ORDER_RESP_TYPE_FULL:
                return (T) new FullSpotOrder(order);
            default:
                return (T) new ACKSpotOrder(order);
        }
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link String}
     * **/
    public String cancelOrder(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderId=" + orderId;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link JSONObject}
     * **/
    public JSONObject cancelOrderJSON(String symbol, long orderId) throws Exception {
       return new JSONObject(cancelOrder(symbol, orderId));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link DetailSpotOrder} object
     * **/
    public DetailSpotOrder cancelOrderObject(String symbol, long orderId) throws Exception {
        return new DetailSpotOrder(new JSONObject(cancelOrderJSON(symbol, orderId)));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link String}
     * **/
    public String cancelOrder(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId ;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link JSONObject}
     * **/
    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link DetailSpotOrder} object
     * **/
    public DetailSpotOrder cancelOrderObject(String symbol, String origClientOrderId) throws Exception {
        return new DetailSpotOrder(new JSONObject(cancelOrderJSON(symbol, origClientOrderId)));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link String}
     * **/
    public String cancelOrder(String symbol, long orderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderId=" + orderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link JSONObject}
     * **/
    public JSONObject cancelOrderJSON(String symbol, long orderId, Params extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, orderId, extraParams));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link DetailSpotOrder} object
     * **/
    public DetailSpotOrder cancelOrderObject(String symbol, long orderId, Params extraParams) throws Exception {
        return new DetailSpotOrder(new JSONObject(cancelOrderJSON(symbol, orderId, extraParams)));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link String}
     * **/
    public String cancelOrder(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link JSONObject}
     * **/
    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId, extraParams));
    }

    /** Request to cancel an SpotOrder
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
     * @return result of DetailSpotOrder operation as {@link DetailSpotOrder} object
     * **/
    public DetailSpotOrder cancelOrderObject(String symbol, String origClientOrderId,
                                             Params extraParams) throws Exception {
        return new DetailSpotOrder(new JSONObject(cancelOrderJSON(symbol, origClientOrderId, extraParams)));
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link String}
     * **/
    public String cancelAllOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link JSONArray}
     * **/
    public JSONArray cancelAllOpenOrdersJSON(String symbol) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol));
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link OpenSpotOrders} object
     * **/
    public OpenSpotOrders cancelAllOpenOrdersObject(String symbol) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol)));
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link String}
     * **/
    public String cancelAllOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link JSONArray}
     * **/
    public JSONArray cancelAllOpenOrdersJSON(String symbol, long recvWindow) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol, recvWindow));
    }

    /** Request to cancel all open orders on a symbol
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade</a>
     * @return result of cancel all open orders on a symbol as {@link OpenSpotOrders} object
     * **/
    public OpenSpotOrders cancelAllOpenOrdersObject(String symbol, long recvWindow) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol, recvWindow)));
    }

    /** Method to assemble an OpenSpotOrders object
     * @param jsonOrders: obtained from Binance's request
     * @return an OpenSpotOrders object with response data
     * **/
    private OpenSpotOrders cancelAllOpenOrdersObject(JSONArray jsonOrders){
        ArrayList<ComposedSpotOrderDetails> cancelOrderComposed = new ArrayList<>();
        ArrayList<DetailSpotOrder> cancelOrders = new ArrayList<>();
        for (int j = 0; j < jsonOrders.length(); j++){
            JSONObject order = jsonOrders.getJSONObject(j);
            try {
                String contingencyType = order.getString("contingencyType");
                cancelOrderComposed.add(new ComposedSpotOrderDetails(order));
            }catch (JSONException e){
                cancelOrders.add(new DetailSpotOrder(order));
            }
        }
        return new OpenSpotOrders(cancelOrders, cancelOrderComposed);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link String}
     * **/
    public String getOrderStatus(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderId=" + orderId;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderStatus(String symbol, long orderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link SpotOrderStatus} object
     * **/
    public SpotOrderStatus getObjectOrderStatus(String symbol, long orderId) throws Exception {
        return new SpotOrderStatus(new JSONObject(getOrderStatus(symbol, orderId)));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link String}
     * **/
    public String getOrderStatus(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link SpotOrderStatus} object
     * **/
    public SpotOrderStatus getObjectOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new SpotOrderStatus(new JSONObject(getOrderStatus(symbol, origClientOrderId)));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link String}
     * **/
    public String getOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderId=" + orderId + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId, recvWindow));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param orderId: identifier of the order es. 1232065
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link SpotOrderStatus} object
     * **/
    public SpotOrderStatus getObjectOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return new SpotOrderStatus(new JSONObject(getOrderStatus(symbol, orderId, recvWindow)));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link String}
     * **/
    public String getOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId
                 + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link JSONObject}
     * **/
    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId, recvWindow));
    }

    /** Request to get status of an order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param origClientOrderId: identifier of the client order es. myOrder1
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data</a>
     * @return status of an order as {@link SpotOrderStatus} object
     * **/
    public SpotOrderStatus getObjectOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return new SpotOrderStatus(new JSONObject(getOrderStatus(symbol, origClientOrderId, recvWindow)));
    }

    /** Request to get current open orders list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as {@link String}
     * **/
    public String getCurrentOpenOrders() throws Exception {
        return sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get current open orders list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as {@link JSONArray}
     * **/
    public JSONArray getJSONCurrentOpenOrders() throws Exception {
        return new JSONArray(sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD));
    }

    /** Request to get current open orders list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<SpotOrderStatus> getCurrentOpenOrdersList() throws Exception {
        return assembleOrderStatusList(new JSONArray(sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT,
                getParamTimestamp(), GET_METHOD)));
    }

    /** Request to get current open orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as {@link String}
     * **/
    public String getCurrentOpenOrders(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(SPOT_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get current open orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as {@link JSONArray}
     * **/
    public JSONArray getJSONCurrentOpenOrders(Params extraParams) throws Exception {
        return new JSONArray(getCurrentOpenOrders(extraParams));
    }

    /** Request to get current open orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data</a>
     * @return current open orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<SpotOrderStatus> getCurrentOpenOrdersList(Params extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getCurrentOpenOrders(extraParams)));
    }

    /** Request to get all orders list
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as {@link String}
     * **/
    public String getAllOrdersList(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(SPOT_ALL_ORDERS_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all orders list
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOrdersList(String symbol) throws Exception {
        return new JSONArray(getAllOrdersList(symbol));
    }

    /** Request to get all orders list
     * @param symbol: symbol used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<SpotOrderStatus> getObjectAllOrdersList(String symbol) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol)));
    }

    /** Request to get all orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as {@link String}
     * **/
    public String getAllOrdersList(String symbol,Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_ALL_ORDERS_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOrdersList(String symbol,Params extraParams) throws Exception {
        return new JSONArray(getAllOrdersList(symbol, extraParams));
    }

    /** Request to get all orders list
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data</a>
     * @return all orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<SpotOrderStatus> getObjectAllOrdersList(String symbol, Params extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol, extraParams)));
    }

    /**
     * Method to assemble an OrderStatus object list
     *
     * @param jsonOrders: obtained from Binance's request
     * @return an ArrayList<OrderStatus> with response data
     **/
    private ArrayList<SpotOrderStatus> assembleOrderStatusList(JSONArray jsonOrders) {
        ArrayList<SpotOrderStatus> orderStatuses = new ArrayList<>();
        for (int j = 0; j < jsonOrders.length(); j++)
            orderStatuses.add(new SpotOrderStatus(jsonOrders.getJSONObject(j)));
        return orderStatuses;
    }

    /**
     * Request to cancel an existing limit order and send a new limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implNote CAS means CANCEL AND SEND
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casLimitOrder(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                double quantity, double price, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, LIMIT, cancelReplaceMode, getLimitPayload(timeInForce, quantity, price,
                extraParams));
    }

    /**
     * Request to cancel an existing limit order and send a new limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implNote CAS means CANCEL AND SEND
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casLimitOrderJSON(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                        double quantity, double price, Params extraParams) throws Exception {
        return new JSONObject(casLimitOrder(symbol, side, cancelReplaceMode, timeInForce, quantity, price, extraParams));
    }

    /**
     * Request to cancel an existing limit order and send a new limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implNote CAS means CANCEL AND SEND
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casLimitOrderObject(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                            double quantity, double price, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casLimitOrder(symbol, side, cancelReplaceMode, timeInForce, quantity,
                price, extraParams)));
    }

    /**
     * Request to cancel an existing market order with quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     * @implNote CAS means CANCEL AND SEND
     **/
    public String casMarketOrderQty(String symbol, String side, String cancelReplaceMode, double quantity,
                                    Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, MARKET, cancelReplaceMode, getMarketPayload("quantity", quantity,
                extraParams));
    }

    /**
     * Request to cancel an existing market order with quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     * @implNote CAS means CANCEL AND SEND
     **/
    public JSONObject casMarketOrderQtyJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                            Params extraParams) throws Exception {
        return new JSONObject(casMarketOrderQty(symbol, side, cancelReplaceMode, quantity, extraParams));
    }

    /**
     * Request to cancel an existing market order with quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     * @implNote CAS means CANCEL AND SEND
     **/
    public SpotOrderCAS casMarketOrderQtyObject(String symbol, String side, String cancelReplaceMode, double quantity,
                                                Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casMarketOrderQty(symbol, side, cancelReplaceMode, quantity, extraParams)));
    }

    /**
     * Request to cancel an existing market order with quote quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quoteQuantity:     quote quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     * @implNote CAS means CANCEL AND SEND
     **/
    public String casMarketOrderQuoteQty(String symbol, String side, String cancelReplaceMode, double quoteQuantity,
                                         Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, MARKET, cancelReplaceMode, getMarketPayload("quoteOrderQty",
                quoteQuantity, extraParams));
    }

    /**
     * Request to cancel an existing market order with quote quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quoteQuantity:     quote quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casMarketOrderQuoteQtyJSON(String symbol, String side, String cancelReplaceMode, double quoteQuantity,
                                                 Params extraParams) throws Exception {
        return new JSONObject(casMarketOrderQuoteQty(symbol, side, cancelReplaceMode, quoteQuantity, extraParams));
    }

    /**
     * Request to cancel an existing market order with quote quantity and send a new market spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quoteQuantity:     quote quantity value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casMarketOrderQuoteQtyObject(String symbol, String side, String cancelReplaceMode,
                                                     double quoteQuantity, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casMarketOrderQuoteQty(symbol, side, cancelReplaceMode, quoteQuantity,
                extraParams)));
    }

    /**
     * Request to cancel an existing stop loss order with price and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casStopLossOrderPrice(String symbol, String side, String cancelReplaceMode, double quantity,
                                        double stopPrice, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS, cancelReplaceMode, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing stop loss order with price and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casStopLossOrderPriceJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                                double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(casStopLossOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing stop loss order with price and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casStopLossOrderPriceObject(String symbol, String side, String cancelReplaceMode, double quantity,
                                                    double stopPrice, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casStopLossOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice,
                extraParams)));
    }

    /**
     * Request to cancel an existing stop loss order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casStopLossOrderDelta(String symbol, String side, String cancelReplaceMode, double quantity,
                                        double trailingDelta, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS, cancelReplaceMode, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing stop loss order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casStopLossOrderDeltaJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                                double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(casStopLossOrderDelta(symbol, side, cancelReplaceMode, quantity, trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing stop loss order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casStopLossOrderDeltaObject(String symbol, String side, String cancelReplaceMode, double quantity,
                                                    double trailingDelta, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casStopLossOrderDelta(symbol, side, cancelReplaceMode, quantity, trailingDelta,
                extraParams)));
    }

    /**
     * Request to cancel an existing stop loss limit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casStopLossLimitOrderPrice(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                             double quantity, double price, double stopPrice, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce, quantity, price,
                "stopPrice", stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing stop loss limit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casStopLossLimitOrderPriceJSON(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                                     double quantity, double price, double stopPrice,
                                                     Params extraParams) throws Exception {
        return new JSONObject(casStopLossLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce, quantity, price,
                stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing stop loss limit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value for the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casStopLossLimitOrderPriceObject(String symbol, String side, String cancelReplaceMode,
                                                         String timeInForce, double quantity, double price,
                                                         double stopPrice, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casStopLossLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce,
                quantity, price, stopPrice, extraParams)));
    }

    /**
     * Request to cancel an existing stop loss limit order with delta and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casStopLossLimitOrderDelta(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                             double quantity, double price, double trailingDelta,
                                             Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, STOP_LOSS_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, trailingDelta, "trailingDelta", trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing stop loss limit order with delta and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casStopLossLimitOrderDeltaJSON(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                                     double quantity, double price, double trailingDelta,
                                                     Params extraParams) throws Exception {
        return new JSONObject(casStopLossLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce, quantity, price,
                trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing stop loss limit order with delta and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casStopLossLimitOrderDeltaObject(String symbol, String side, String cancelReplaceMode,
                                                         String timeInForce, double quantity, double price,
                                                         double trailingDelta, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casStopLossLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce,
                quantity, price, trailingDelta, extraParams)));
    }

    /**
     * Request to cancel an existing take profit order with price and send a new take profit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casTakeProfitOrderPrice(String symbol, String side, String cancelReplaceMode, double quantity,
                                          double stopPrice, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT, cancelReplaceMode, getLevelPayload(quantity, "stopPrice",
                stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing take profit order with price and send a new take profit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casTakeProfitOrderPriceJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                                  double stopPrice, Params extraParams) throws Exception {
        return new JSONObject(casTakeProfitOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing take profit order with price and send a new stop loss limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casTakeProfitOrderPriceObject(String symbol, String side, String cancelReplaceMode, double quantity,
                                                      double stopPrice, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casTakeProfitOrderPrice(symbol, side, cancelReplaceMode, quantity, stopPrice,
                extraParams)));
    }

    /**
     * Request to cancel an existing take profit order with delta and send a new take profit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casTakeProfitOrderDelta(String symbol, String side, String cancelReplaceMode, double quantity,
                                          double trailingDelta, Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT, cancelReplaceMode, getLevelPayload(quantity, "trailingDelta",
                trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing take profit order with delta and send a new take profit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casTakeProfitOrderDeltaJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                                  double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(casTakeProfitOrderDelta(symbol, side, cancelReplaceMode, quantity, trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new stop loss spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casTakeProfitOrderDeltaObject(String symbol, String side, String cancelReplaceMode,
                                                      double quantity, double trailingDelta,
                                                      Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casTakeProfitOrderDelta(symbol, side, cancelReplaceMode, quantity,
                trailingDelta, extraParams)));
    }

    /**
     * Request to cancel an existing take profit limit order with price and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casTakeProfitLimitOrderPrice(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                               double quantity, double price, double stopPrice,
                                               Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce,
                quantity, price, "stopPrice", stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing take profit limit order with price and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casTakeProfitLimitOrderPriceJSON(String symbol, String side, String cancelReplaceMode,
                                                       String timeInForce, double quantity, double price, double stopPrice,
                                                       Params extraParams) throws Exception {
        return new JSONObject(casTakeProfitLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce, quantity, price,
                stopPrice, extraParams));
    }

    /**
     * Request to cancel an existing take profit limit order with price and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param stopPrice:         stop price value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casTakeProfitLimitOrderPriceObject(String symbol, String side, String cancelReplaceMode,
                                                           String timeInForce, double quantity, double price,
                                                           double stopPrice, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casTakeProfitLimitOrderPrice(symbol, side, cancelReplaceMode, timeInForce,
                quantity, price, stopPrice, extraParams)));
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casTakeProfitLimitOrderDelta(String symbol, String side, String cancelReplaceMode, String timeInForce,
                                               double quantity, double price, double trailingDelta,
                                               Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, TAKE_PROFIT_LIMIT, cancelReplaceMode, getLevelLimitPayload(timeInForce, quantity,
                trailingDelta, "trailingDelta", trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casTakeProfitLimitOrderDeltaJSON(String symbol, String side, String cancelReplaceMode,
                                                       String timeInForce, double quantity, double price,
                                                       double trailingDelta, Params extraParams) throws Exception {
        return new JSONObject(casTakeProfitLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce, quantity, price,
                trailingDelta, extraParams));
    }

    /**
     * Request to cancel an existing take profit limit order with delta and send a new take profit limit spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param timeInForce:       time in force for the order
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param trailingDelta:     trailing delta value
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casTakeProfitLimitOrderDeltaObject(String symbol, String side, String cancelReplaceMode,
                                                           String timeInForce, double quantity, double price,
                                                           double trailingDelta, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casTakeProfitLimitOrderDelta(symbol, side, cancelReplaceMode, timeInForce,
                quantity, price, trailingDelta, extraParams)));
    }

    /**
     * Request to cancel an existing limit maker order with delta and send a new limit maker spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public String casLimitMakerOrder(String symbol, String side, String cancelReplaceMode, double quantity, double price,
                                     Params extraParams) throws Exception {
        return cancelAndSendOrder(symbol, side, LIMIT_MAKER, cancelReplaceMode, getLimitMakerPayload(quantity, price, extraParams));
    }

    /**
     * Request to cancel an existing limit maker order with delta and send a new limit maker spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link JSONObject}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public JSONObject casLimitMakerOrderJSON(String symbol, String side, String cancelReplaceMode, double quantity,
                                             double price, Params extraParams) throws Exception {
        return new JSONObject(casLimitMakerOrder(symbol, side, cancelReplaceMode, quantity, price, extraParams));
    }

    /**
     * Request to cancel an existing limit maker order with delta and send a new limit maker spot order on the same symbol
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param quantity:          quantity value in the order
     * @param price:             price value in the order
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link SpotOrderCAS} custom object
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    public SpotOrderCAS casLimitMakerOrderObject(String symbol, String side, String cancelReplaceMode, double quantity,
                                                 double price, Params extraParams) throws Exception {
        return new SpotOrderCAS(new JSONObject(casLimitMakerOrder(symbol, side, cancelReplaceMode, quantity, price,
                extraParams)));
    }

    /**
     * Request to cancel and resend a spot order
     *
     * @param symbol:            symbol used in the request es. BTCBUSD
     * @param side:              side of the order -> {@link TradeConstants#BUY} or {@link TradeConstants#SELL}
     * @param type:              LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param cancelReplaceMode: mode to replace order -> {@link ACKSpotOrder#STOP_ON_FAILURE_REPLACE_MODE}
     *                           or {@link ACKSpotOrder#ALLOW_FAILURE_REPLACE_MODE}
     * @param extraParams:       additional params of the request, insert null if there are no extra params
     * @return result of cancellation of an order and creation of a new order as {@link String}
     * @implSpec (keys accepted are timeInForce, quantity, quoteOrderQty, price, newClientOrderId, stopPrice, icebergQty,
     *recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade">
     * https://binance-docs.github.io/apidocs/spot/en/#cancel-an-existing-order-and-send-a-new-order-trade</a>
     **/
    private String cancelAndSendOrder(String symbol, String side, String type, String cancelReplaceMode,
                                      Params extraParams) throws Exception {
        String query = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type + "&cancelReplaceMode="
                + cancelReplaceMode;
        return sendSignedRequest(CANCEL_AND_SEND_ORDER_ENDPOINT, apiRequest.encodeAdditionalParams(query, extraParams),
                POST_METHOD);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link String}
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&price=" + price + "&stopPrice="
                 + stopPrice;
        return sendSignedRequest(SPOT_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link JSONObject}
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice)));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount used to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link String}
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                  String stopLimitTimeInForce) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&price=" + price + "&stopPrice="
                 + stopPrice + "&stopLimitPrice=" + stopLimitPrice + "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        return sendSignedRequest(SPOT_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount used to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link JSONObject}
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                          String stopLimitTimeInForce) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice, stopLimitPrice, stopLimitTimeInForce));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount used to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                          double stopLimitPrice, String stopLimitTimeInForce) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,
                stopLimitPrice, stopLimitTimeInForce)));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link String}
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice,
                                  Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&price=" + price + "&stopPrice="
                + stopPrice;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link JSONObject}
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice,
                                          Params extraParams) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice, extraParams));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                          Params extraParams) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice, extraParams)));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link String}
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                  String stopLimitTimeInForce, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&price=" + price + "&stopPrice="
                + stopPrice + "&stopLimitPrice=" + stopLimitPrice + "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as {@link JSONObject}
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                          String stopLimitTimeInForce, Params extraParams) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice, stopLimitPrice, stopLimitTimeInForce,
                extraParams));
    }

    /** Request to send new oco order
     * @param symbol: symbol used in new oco order es. BTCBUSD
     * @param side: side of the order BUY,SELL
     * @param price: amount used in the order
     * @param stopPrice: amount to stop order
     * @param stopLimitPrice: amount used to stop in the limit order
     * @param stopLimitTimeInForce: GTC, FOK, IOC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade</a>
     * @return oco order response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                          double stopLimitPrice, String stopLimitTimeInForce,
                                                          Params extraParams) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,
                stopLimitPrice, stopLimitTimeInForce, extraParams)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link String}
     * **/
    public String cancelAllOcoOrders(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link JSONObject}
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, long orderListId) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, orderListId));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails cancelAllOcoOrdersObject(String symbol, long orderListId) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(cancelOrderJSON(symbol, orderListId)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link String}
     * **/
    public String cancelAllOcoOrders(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link JSONObject}
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, listClientOrderId));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails cancelAllOcoOrdersObject(String symbol, String listClientOrderId) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(cancelOrderJSON(symbol, listClientOrderId)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link String}
     * **/
    public String cancelAllOcoOrders(String symbol, long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link JSONObject}
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, long orderListId, Params extraParams) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, orderListId, extraParams));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails cancelAllOcoOrdersObject(String symbol, long orderListId,
                                                             Params extraParams) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(cancelOrderJSON(symbol, orderListId, extraParams)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link String}
     * **/
    public String cancelAllOcoOrders(String symbol, String listClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as {@link JSONObject}
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, String listClientOrderId, Params extraParams) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, listClientOrderId, extraParams));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade</a>
     * @return cancel all OcoOrders response as ComposedSpotOrderDetails object
     * **/
    public ComposedSpotOrderDetails cancelAllOcoOrdersObject(String symbol, String listClientOrderId,
                                                             Params extraParams) throws Exception {
        return new ComposedSpotOrderDetails(new JSONObject(cancelOrderJSON(symbol, listClientOrderId, extraParams)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as ComposedSpotOrderDetails object
     * **/
    public String getOcoOrderStatus(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as {@link JSONObject}
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, long orderListId) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, orderListId));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as OrderValues object
     * **/
    public OrderDetails getOcoOrderStatusObject(String symbol, long orderListId) throws Exception {
        return new OrderDetails(new JSONObject(getOcoOrderStatus(symbol, orderListId)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return cancel all OcoOrders response as {@link String}
     * **/
    public String getOcoOrderStatus(String symbol, long orderListId, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId +
                "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return cancel all OcoOrders response as {@link JSONObject}
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, long orderListId, long recvWindow) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, orderListId, recvWindow));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fetch OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return cancel all OcoOrders response as OrderValues object
     * **/
    public OrderDetails getOcoOrderStatusObject(String symbol, long orderListId, long recvWindow) throws Exception {
        return new OrderDetails(new JSONObject(getOcoOrderStatus(symbol, orderListId, recvWindow)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as {@link String}
     * **/
    public String getOcoOrderStatus(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as {@link JSONObject}
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, listClientOrderId));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as OrderValues object
     * **/
    public OrderDetails getOcoOrderStatusObject(String symbol, String listClientOrderId) throws Exception {
        return new OrderDetails(new JSONObject(getOcoOrderStatus(symbol, listClientOrderId)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as {@link String}
     * **/
    public String getOcoOrderStatus(String symbol, String listClientOrderId, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId +
                "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as {@link JSONObject}
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol,  String listClientOrderId, long recvWindow) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, listClientOrderId, recvWindow));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data</a>
     * @return OCO order status response as OrderValues object
     * **/
    public OrderDetails getOcoOrderStatusObject(String symbol, String listClientOrderId, long recvWindow) throws Exception {
        return new OrderDetails(new JSONObject(getOcoOrderStatus(symbol, listClientOrderId, recvWindow)));
    }

    /** Request to get OCO order status list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link String}
     * **/
    public String getOcoOrderStatusList() throws Exception {
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get OCO order status list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOcoOrderStatusList() throws Exception {
        return new JSONArray(getOcoOrderStatusList());
    }

    /** Request to get OCO order status list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as ArrayList<OrderValues>
     * **/
    public ArrayList<OrderDetails> getObjectOcoOrderStatusList() throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOcoOrderStatusList()));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link String}
     * **/
    public String getOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        String params = getParamTimestamp() + "&fromId=" + fromId + "&" + timeParam + "=" + timeParamValue;
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        return new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as ArrayList<OrderValues>
     * **/
    public ArrayList<OrderDetails> getObjectOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue)));
    }

    /** Request to get OCO order status list
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link String}
     * **/
    public String getOcoOrderStatusList(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOcoOrderStatusList(Params extraParams) throws Exception {
        return new JSONArray(getOcoOrderStatusList(extraParams));
    }

    /** Request to get OCO order status list
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as ArrayList<OrderValues>
     * **/
    public ArrayList<OrderDetails> getObjectOcoOrderStatusList(Params extraParams) throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOcoOrderStatusList(extraParams)));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link String}
     * **/
    public String getOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                        Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&fromId=" + fromId + "&" + timeParam + "=" + timeParamValue;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_OCO_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                               Params extraParams) throws Exception {
        return new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue, extraParams));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data</a>
     * @return OCO order status list response as ArrayList<OrderValues>
     * **/
    public ArrayList<OrderDetails> getObjectOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                                               Params extraParams) throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue, extraParams)));
    }

    /** Request to get open OCO order list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     * @return open OCO order list response as {@link String}
     * **/
    public String getOpenOcoOrderList() throws Exception {
        return sendSignedRequest(SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get open OCO order list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     * @return open OCO order list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOpenOcoOrderList() throws Exception {
        return new JSONArray(getOpenOcoOrderList());
    }

    /** Request to get open OCO order list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     * @return open OCO order list response as ArrayList<OrderValues>
     * **/
    public ArrayList<OrderDetails> getObjectOpenOcoOrderList() throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOpenOcoOrderList()));
    }

    /** Request to get open OCO order list
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     * @return open OCO order list response as {@link String}
     * **/
    public String getOpenOcoOrderList(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_OCO_OPEN_ORDER_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get open OCO order list
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     * @return open OCO order list response as {@link JSONArray}
     * **/
    public JSONArray getJSONOpenOcoOrderList(long recvWindow) throws Exception {
        return new JSONArray(getOpenOcoOrderList(recvWindow));
    }

    /**
     * Request to get open OCO order list
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return open OCO order list response as ArrayList<OrderValues>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data</a>
     **/
    public ArrayList<OrderDetails> getObjectOpenOcoOrderList(long recvWindow) throws Exception {
        return assembleBaseOrderDetailsList(new JSONArray(getOpenOcoOrderList(recvWindow)));
    }

    /**
     * Method to assemble an OrderValues object list
     *
     * @param #jsonOrders: obtained from Binance's request
     * @return an ArrayList<OrderValues> with response data
     **/
    private ArrayList<OrderDetails> assembleBaseOrderDetailsList(JSONArray jsonOrders) {
        ArrayList<OrderDetails> baseOrderDetailsList = new ArrayList<>();
        for (int j = 0; j < jsonOrders.length(); j++)
            baseOrderDetailsList.add(new OrderDetails(jsonOrders.getJSONObject(j)));
        return baseOrderDetailsList;
    }

    /** Request to get spot account information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as {@link String}
     * **/
    public String getSpotAccountInformation() throws Exception {
        return sendSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get spot account information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as {@link JSONObject}
     * **/
    public JSONObject getJSONSpotAccountInformation() throws Exception {
        return new JSONObject(getSpotAccountInformation());
    }

    /** Request to get spot account information <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as SpotAccountInformation object
     * **/
    public SpotAccountInformation getObjectSpotAccountInformation() throws Exception {
        return new SpotAccountInformation(new JSONObject(getSpotAccountInformation()));
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as {@link String}
     * **/
    public String getSpotAccountInformation(double recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as {@link JSONObject}
     * **/
    public JSONObject getJSONSpotAccountInformation(double recvWindow) throws Exception {
        return new JSONObject(getSpotAccountInformation(recvWindow));
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * @return spot account information response as SpotAccountInformation object
     * **/
    public SpotAccountInformation getObjectSpotAccountInformation(double recvWindow) throws Exception {
        return new SpotAccountInformation(new JSONObject(getSpotAccountInformation(recvWindow)));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as {@link String}
     * **/
    public String getAccountTradeList(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as {@link JSONArray}
     * **/
    public JSONArray getJSONAccountTradeList(String symbol) throws Exception {
        return new JSONArray(getAccountTradeList(symbol));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as ArrayList<SpotAccountTradeList>
     * **/
    public ArrayList<SpotAccountTradeList> getObjectAccountTradeList(String symbol) throws Exception {
        return assembleSpotAccountTradeList(new JSONArray(getAccountTradeList(symbol)));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as {@link String}
     * **/
    public String getAccountTradeList(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as {@link JSONArray}
     * **/
    public JSONArray getJSONAccountTradeList(String symbol, Params extraParams) throws Exception {
        return new JSONArray(getAccountTradeList(symbol, extraParams));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in AccountTradeList es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data</a>
     * @return Account Trade List response as ArrayList<SpotAccountTradeList>
     * **/
    public ArrayList<SpotAccountTradeList> getObjectAccountTradeList(String symbol, Params extraParams)
            throws Exception {
        return assembleSpotAccountTradeList(new JSONArray(getAccountTradeList(symbol,extraParams)));
    }

    /**
     * Method to assemble an SpotAccountTradeList object
     *
     * @param #jsonTrades: obtained from Binance's request
     * @return a SpotAccountTradeList object with response data
     **/
    private ArrayList<SpotAccountTradeList> assembleSpotAccountTradeList(JSONArray jsonTrades) {
        ArrayList<SpotAccountTradeList> spotAccountTradeLists = new ArrayList<>();
        for (int j = 0; j < jsonTrades.length(); j++)
            spotAccountTradeLists.add(new SpotAccountTradeList(jsonTrades.getJSONObject(j)));
        return spotAccountTradeLists;
    }

    /** Request to get current order count usage <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as {@link String}
     * **/
    public String getCurrentOrderCountUsage() throws Exception {
        return sendSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get current order count usage <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as {@link JSONArray}
     * **/
    public JSONArray getJSONCurrentOrderCountUsage() throws Exception {
        return new JSONArray(getCurrentOrderCountUsage());
    }

    /** Request to get current order count usage <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as ArrayList<OrderCountUsage>
     * **/
    public ArrayList<OrderCountUsage> getObjectCurrentOrderCountUsage() throws Exception {
        return assembleOrderCountUsageList(new JSONArray(getCurrentOrderCountUsage()));
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as {@link String}
     * **/
    public String getCurrentOrderCountUsage(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE, params, GET_METHOD);
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as {@link JSONArray}
     * **/
    public JSONArray getJSONCurrentOrderCountUsage(long recvWindow) throws Exception {
        return new JSONArray(getCurrentOrderCountUsage(recvWindow));
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade</a>
     * @return current order count usage response as ArrayList<OrderCountUsage>
     * **/
    public ArrayList<OrderCountUsage> getObjectCurrentOrderCountUsage(long recvWindow) throws Exception {
        return assembleOrderCountUsageList(new JSONArray(getCurrentOrderCountUsage(recvWindow)));
    }

    /**
     * Method to assemble an OrderCountUsage object
     *
     * @param #jsonCount: obtained from Binance's request
     * @return a OrderCountUsage object with response data
     **/
    private ArrayList<OrderCountUsage> assembleOrderCountUsageList(JSONArray jsonCount) {
        ArrayList<OrderCountUsage> orderCountUsages = new ArrayList<>();
        for (int j = 0; j < jsonCount.length(); j++)
            orderCountUsages.add(new OrderCountUsage(jsonCount.getJSONObject(j)));
        return orderCountUsages;
    }

}
