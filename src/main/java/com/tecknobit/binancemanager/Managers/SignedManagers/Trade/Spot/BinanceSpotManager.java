package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account.OrderCountUsage;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account.SpotAccountInformation;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account.SpotAccountTradeList;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.BaseOrderDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel.CancelOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ComposedOrderDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel.OpenOrders;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.ACKOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.FullOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.OrderStatus;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response.ResultOrder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Order.*;

/**
 *  The {@code BinanceSpotManager} class is useful to manage all Binance Spot Endpoints
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#spot-account-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceSpotManager extends BinanceSignedManager {

    /** Constructor to init BinanceSpotManager
     * @param #baseEndpoint base endpoint to work on
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * **/
    public BinanceSpotManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /** Constructor to init BinanceSpotManager
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * automatically set a working endpoint
     * **/
    public BinanceSpotManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null, apiKey, secretKey);
    }

    /** Request to test a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade
     * return result of the order WITHOUT buy or sell nothing, if is correct return "{}" else error of the request
     * **/
    public String testNewOrder(String symbol, String side, String type,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return sendSignedRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    /** Request to test a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#test-new-order-trade
     * return result of the order WITHOUT buy or sell nothing, if is correct return "{}" else error of the request
     * **/
    public String testNewOrder(String symbol, String side, String type, String newOrderRespType,
                               HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return sendSignedRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as String
     * **/
    public String sendNewOrder(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return sendSignedRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as JsonObject
     * **/
    public JSONObject sendNewOrderJSON(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(sendNewOrder(symbol,side,type,extraParams));
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as AckOrder (next to cast in base at type used)
     * @implNote if type LIMIT or MARKET will be must cast in {@link FullOrder} object
     * @implNote with other types will be an {@link ACKOrder} object
     * **/
    public ACKOrder sendNewOrderObject(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(sendNewOrder(symbol,side,type,extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return getFullOrderResponse(jsonObject);
        else
            return getACKResponse(jsonObject);
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as String
     * **/
    public String sendNewOrder(String symbol, String side, String type, String newOrderRespType,
                               HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return sendSignedRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as JsonObject
     * **/
    public JSONObject sendNewOrderJSON(String symbol, String side, String type, String newOrderRespType,
                                       HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(sendNewOrder(symbol,side,type,newOrderRespType,extraParams));
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * return result of the order as AckOrder (next to cast in base at newOrderRespType used)
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_RESULT object will be {@link ResultOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_FULL object will be {@link FullOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_ACK object will be {@link ACKOrder}
     * **/
    public ACKOrder sendNewOrderObject(String symbol, String side, String type, String newOrderRespType,
                                       HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(sendNewOrder(symbol,side,type,newOrderRespType,extraParams));
        switch (newOrderRespType){
            case NEW_ORDER_RESP_TYPE_RESULT:
                return new ResultOrder(jsonObject.getString("symbol"),
                        jsonObject.getLong("orderId"),
                        jsonObject.getLong("orderListId"),
                        jsonObject.getString("clientOrderId"),
                        jsonObject.getLong("transactTime"),
                        jsonObject.getDouble("price"),
                        jsonObject.getDouble("origQty"),
                        jsonObject.getDouble("executedQty"),
                        jsonObject.getDouble("cummulativeQuoteQty"),
                        jsonObject.getString("status"),
                        jsonObject.getString("timeInForce"),
                        jsonObject.getString("type"),
                        jsonObject.getString("side")
                );
            case NEW_ORDER_RESP_TYPE_FULL:
                return getFullOrderResponse(jsonObject);
            default:
                return getACKResponse(jsonObject);
        }
    }

    /** Method to assemble an ACKOrder object
     * @param #response: obtained from Binance's request
     * return an ACKOrder object with response data
     * **/
    private ACKOrder getACKResponse(JSONObject response){
        return new ACKOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getLong("orderListId"),
                response.getString("clientOrderId"),
                response.getLong("transactTime")
        );
    }

    /** Method to assemble an FullOrder object
     * @param #response: obtained from Binance's request
     * return an FullOrder object with response data
     * **/
    private FullOrder getFullOrderResponse(JSONObject response){
        return new FullOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getLong("orderListId"),
                response.getString("clientOrderId"),
                response.getLong("transactTime"),
                response.getDouble("price"),
                response.getDouble("origQty"),
                response.getDouble("executedQty"),
                response.getDouble("cummulativeQuoteQty"),
                response.getString("status"),
                response.getString("timeInForce"),
                response.getString("type"),
                response.getString("side"),
                response.getJSONArray("fills")
        );
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as String
     * **/
    public String cancelOrder(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        return sendSignedRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as JsonObject
     * **/
    public JSONObject cancelOrderJSON(String symbol, long orderId) throws Exception {
       return new JSONObject(cancelOrder(symbol, orderId));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as {@link CancelOrder} object
     * **/
    public CancelOrder cancelOrderObject(String symbol, long orderId) throws Exception {
        return CancelOrder.assembleCancelOrderObject(new JSONObject(cancelOrderJSON(symbol, orderId)));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as String
     * **/
    public String cancelOrder(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId ;
        return sendSignedRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as JsonObject
     * **/
    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as {@link CancelOrder} object
     * **/
    public CancelOrder cancelOrderObject(String symbol, String origClientOrderId) throws Exception {
        return CancelOrder.assembleCancelOrderObject( new JSONObject(cancelOrderJSON(symbol, origClientOrderId)));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as String
     * **/
    public String cancelOrder(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        params = requestManager.assembleExtraParams(params, extraParams);
        return sendSignedRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as JsonObject
     * **/
    public JSONObject cancelOrderJSON(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, orderId, extraParams));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as {@link CancelOrder} object
     * **/
    public CancelOrder cancelOrderObject(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        return CancelOrder.assembleCancelOrderObject(new JSONObject(cancelOrderJSON(symbol, orderId, extraParams)));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as String
     * **/
    public String cancelOrder(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId ="+origClientOrderId;
        params = requestManager.assembleExtraParams(params, extraParams);
        return sendSignedRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as JsonObject
     * **/
    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId, extraParams));
    }

    /** Request to cancel an Order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,origClientOrderId, newClientOrderId, recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade
     * return result of CancelOrder operation as {@link CancelOrder} object
     * **/
    public CancelOrder cancelOrderObject(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        return CancelOrder.assembleCancelOrderObject(new JSONObject(cancelOrderJSON(symbol, origClientOrderId, extraParams)));
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as String
     * **/
    public String cancelAllOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as JsonArray
     * **/
    public JSONArray cancelAllOpenOrdersJSON(String symbol) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol));
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as {@link OpenOrders} object
     * **/
    public OpenOrders cancelAllOpenOrdersObject(String symbol) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol)));
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as String
     * **/
    public String cancelAllOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&recvWindow="+recvWindow;
        return sendSignedRequest(OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as JsonArray
     * **/
    public JSONArray cancelAllOpenOrdersJSON(String symbol, long recvWindow) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol, recvWindow));
    }

    /** Request to cancel all open orders on a symbol
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #recvWindow: time to keep alive response, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-all-open-orders-on-a-symbol-trade
     * return result of cancel all open orders on a symbol as {@link OpenOrders} object
     * **/
    public OpenOrders cancelAllOpenOrdersObject(String symbol, long recvWindow) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol, recvWindow)));
    }

    /** Method to assemble an OpenOrders object
     * @param #jsonArray: obtained from Binance's request
     * return an OpenOrders object with response data
     * **/
    private OpenOrders cancelAllOpenOrdersObject(JSONArray jsonArray){
        ArrayList<CancelOrder> cancelOrders = new ArrayList<>();
        ArrayList<ComposedOrderDetails> cancelOrderComposeds = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject order = jsonArray.getJSONObject(j);
            try {
                String contingencyType = order.getString("contingencyType");
                cancelOrderComposeds.add(assembleComposedOrderDetails(order));
            }catch (JSONException e){
                cancelOrders.add(CancelOrder.assembleCancelOrderObject(order));
            }
        }
        return new OpenOrders(cancelOrders,cancelOrderComposeds);
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as String
     * **/
    public String getOrderStatus(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        return sendSignedRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as JsonObject
     * **/
    public JSONObject getJSONOrderStatus(String symbol, long orderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as {@link OrderStatus} object
     * **/
    public OrderStatus getObjectOrderStatus(String symbol, long orderId) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol, orderId)));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as String
     * **/
    public String getOrderStatus(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId ;
        return sendSignedRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as JsonObject
     * **/
    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as {@link OrderStatus} object
     * **/
    public OrderStatus getObjectOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol,origClientOrderId)));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as String
     * **/
    public String getOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId+"&recvWindow="+recvWindow;
        return sendSignedRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as JsonObject
     * **/
    public JSONObject getJSONOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId, recvWindow));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #orderId: identifier of the order es. 1232065
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as {@link OrderStatus} object
     * **/
    public OrderStatus getObjectOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol, orderId, recvWindow)));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as String
     * **/
    public String getOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&recvWindow="+recvWindow;
        return sendSignedRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as JsonObject
     * **/
    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId,recvWindow));
    }

    /** Request to get status of an order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #origClientOrderId: identifier of the client order es. myOrder1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-order-user_data
     * return status of an order as {@link OrderStatus} object
     * **/
    public OrderStatus getObjectOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol,origClientOrderId,recvWindow)));
    }

    /** Request to get current open orders list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as String
     * **/
    public String getCurrentOpenOrders() throws Exception {
        return sendSignedRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get current open orders list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as JsonArray
     * **/
    public JSONArray getJSONCurrentOpenOrders() throws Exception {
        return new JSONArray(sendSignedRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD));
    }

    /** Request to get current open orders list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<OrderStatus> getCurrentOpenOrdersList() throws Exception {
        return assembleOrderStatusList(new JSONArray(sendSignedRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD)));
    }

    /** Request to get current open orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as String
     * **/
    public String getCurrentOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp();
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get current open orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as JsonArray
     * **/
    public JSONArray getJSONCurrentOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getCurrentOpenOrders(extraParams));
    }

    /** Request to get current open orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#current-open-orders-user_data
     * return current open orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<OrderStatus> getCurrentOpenOrdersList(HashMap<String, Object> extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getCurrentOpenOrders(extraParams)));
    }

    /** Request to get all orders list
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as String
     * **/
    public String getAllOrdersList(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(ALL_ORDERS_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all orders list
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as JsonArray
     * **/
    public JSONArray getJSONAllOrdersList(String symbol) throws Exception {
        return new JSONArray(getAllOrdersList(symbol));
    }

    /** Request to get all orders list
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<OrderStatus> getObjectAllOrdersList(String symbol) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol)));
    }

    /** Request to get all orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as String
     * **/
    public String getAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(ALL_ORDERS_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as JsonArray
     * **/
    public JSONArray getJSONAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOrdersList(symbol, extraParams));
    }

    /** Request to get all orders list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data
     * return all orders list as ArrayList<OrderStatus>
     * **/
    public ArrayList<OrderStatus> getObjectAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol, extraParams)));
    }

    /** Method to assemble an OrderStatus object
     * @param #response: obtained from Binance's request
     * return an OrderStatus object with response data
     * **/
    private OrderStatus getObjectOrderStatus(JSONObject response){
        return new OrderStatus(response.getString("symbol"),
                response.getLong("orderId"),
                response.getLong("orderListId"),
                response.getString("clientOrderId"),
                response.getDouble("price"),
                response.getDouble("origQty"),
                response.getDouble("executedQty"),
                response.getDouble("cummulativeQuoteQty"),
                response.getString("status"),
                response.getString("timeInForce"),
                response.getString("type"),
                response.getString("side"),
                response.getDouble("stopPrice"),
                response.getDouble("icebergQty"),
                response.getLong("time"),
                response.getLong("updateTime"),
                response.getBoolean("isWorking"),
                response.getDouble("origQuoteOrderQty")
        );
    }

    /** Method to assemble an OrderStatus object list
     * @param #jsonArray: obtained from Binance's request
     * return an ArrayList<OrderStatus> with response data
     * **/
    private ArrayList<OrderStatus> assembleOrderStatusList(JSONArray jsonArray){
        ArrayList<OrderStatus> orderStatuses = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            orderStatuses.add(getObjectOrderStatus(jsonArray.getJSONObject(j)));
        return orderStatuses;
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as String
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&price="+price+"&stopPrice="+stopPrice;
        return sendSignedRequest(OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as JsonObject
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice)));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount used to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as String
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                  String stopLimitTimeInForce) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&price="+price+"&stopPrice="+stopPrice
                +"&stopLimitPrice="+stopLimitPrice+"&stopLimitTimeInForce="+stopLimitTimeInForce;
        return sendSignedRequest(OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount used to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as JsonObject
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                          String stopLimitTimeInForce) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,stopLimitPrice,stopLimitTimeInForce));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount used to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                      double stopLimitPrice, String stopLimitTimeInForce) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,
                stopLimitPrice,stopLimitTimeInForce)));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as String
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice,
                                  HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&price="+price+"&stopPrice="+stopPrice;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as JsonObject
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice,
                                          HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,extraParams));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                      HashMap<String, Object> extraParams) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice, extraParams)));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as String
     * **/
    public String sendNewOcoOrder(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                  String stopLimitTimeInForce, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&price="+price+"&stopPrice="+stopPrice
                +"&stopLimitPrice="+stopLimitPrice+"&stopLimitTimeInForce="+stopLimitTimeInForce;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as JsonObject
     * **/
    public JSONObject sendNewOcoOrderJSON(String symbol, String side, double price, double stopPrice, double stopLimitPrice,
                                          String stopLimitTimeInForce, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,stopLimitPrice,stopLimitTimeInForce,extraParams));
    }

    /** Request to send new oco order
     * @param #symbol: symbol used in new oco order es. BTCBUSD
     * @param #side: side of the order BUY,SELL
     * @param #price: amount used in the order
     * @param #stopPrice: amount to stop order
     * @param #stopLimitPrice: amount used to stop in the limit order
     * @param #stopLimitTimeInForce: GTC, FOK, IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are listClientOrderId,side,quantity,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-oco-trade
     * return oco order response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails sendNewOcoOrderObject(String symbol, String side, double price, double stopPrice,
                                                      double stopLimitPrice, String stopLimitTimeInForce,
                                                      HashMap<String, Object> extraParams) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(sendNewOcoOrder(symbol, side, price, stopPrice,
                stopLimitPrice,stopLimitTimeInForce,extraParams)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as String
     * **/
    public String cancelAllOcoOrders(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as JsonObject
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, long orderListId) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, orderListId));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails cancelAllOcoOrdersObject(String symbol, long orderListId) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(cancelOrderJSON(symbol, orderListId)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as String
     * **/
    public String cancelAllOcoOrders(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as JsonObject
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, listClientOrderId));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails cancelAllOcoOrdersObject(String symbol, String listClientOrderId) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(cancelOrderJSON(symbol, listClientOrderId)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as String
     * **/
    public String cancelAllOcoOrders(String symbol, long orderListId, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as JsonObject
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, long orderListId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, orderListId, extraParams));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails cancelAllOcoOrdersObject(String symbol, long orderListId,
                                                         HashMap<String,Object> extraParams) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(cancelOrderJSON(symbol, orderListId, extraParams)));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as String
     * **/
    public String cancelAllOcoOrders(String symbol, String listClientOrderId,  HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as JsonObject
     * **/
    public JSONObject cancelAllOcoOrdersJSON(String symbol, String listClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrderJSON(symbol, listClientOrderId, extraParams));
    }

    /** Request to cancel all OcoOrders
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderListId,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cancel-oco-trade
     * return cancel all OcoOrders response as ComposedOrderDetails object
     * **/
    public ComposedOrderDetails cancelAllOcoOrdersObject(String symbol, String listClientOrderId,
                                                         HashMap<String,Object> extraParams) throws Exception {
        return assembleComposedOrderDetails(new JSONObject(cancelOrderJSON(symbol, listClientOrderId, extraParams)));
    }

    /** Method to assemble an ComposedOrderDetails object
     * @param #order: obtained from Binance's request
     * return a ComposedOrderDetails object with response data
     * **/
    private ComposedOrderDetails assembleComposedOrderDetails(JSONObject order){
        return new ComposedOrderDetails(order.getLong("orderListId"),
                order.getString("contingencyType"),
                order.getString("listStatusType"),
                order.getString("listOrderStatus"),
                order.getString("listClientOrderId"),
                order.getLong("transactionTime"),
                order.getString("symbol"),
                order
        );
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as ComposedOrderDetails object
     * **/
    public String getOcoOrderStatus(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as JsonObject
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, long orderListId) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, orderListId));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as BaseOrderDetails object
     * **/
    public BaseOrderDetails getOcoOrderStatusObject(String symbol, long orderListId) throws Exception {
        return assembleBaseOrderDetails(new JSONObject(getOcoOrderStatus(symbol, orderListId)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return cancel all OcoOrders response as String
     * **/
    public String getOcoOrderStatus(String symbol, long orderListId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&recvWindow="+recvWindow;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return cancel all OcoOrders response as JsonObject
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, long orderListId, long recvWindow) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, orderListId, recvWindow));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used to fecth OCO order status es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return cancel all OcoOrders response as BaseOrderDetails object
     * **/
    public BaseOrderDetails getOcoOrderStatusObject(String symbol, long orderListId, long recvWindow) throws Exception {
        return assembleBaseOrderDetails(new JSONObject(getOcoOrderStatus(symbol, orderListId, recvWindow)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as String
     * **/
    public String getOcoOrderStatus(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as JsonObject
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, listClientOrderId));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as BaseOrderDetails object
     * **/
    public BaseOrderDetails getOcoOrderStatusObject(String symbol, String listClientOrderId) throws Exception {
        return assembleBaseOrderDetails(new JSONObject(getOcoOrderStatus(symbol, listClientOrderId)));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as String
     * **/
    public String getOcoOrderStatus(String symbol, String listClientOrderId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId+"&recvWindow="+recvWindow;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as JsonObject
     * **/
    public JSONObject getOcoOrderStatusJSON(String symbol,  String listClientOrderId, long recvWindow) throws Exception {
        return new JSONObject(getOcoOrderStatus(symbol, listClientOrderId, recvWindow));
    }

    /** Request to get OCO order status
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-oco-user_data
     * return OCO order status response as BaseOrderDetails object
     * **/
    public BaseOrderDetails getOcoOrderStatusObject(String symbol, String listClientOrderId, long recvWindow) throws Exception {
        return assembleBaseOrderDetails(new JSONObject(getOcoOrderStatus(symbol, listClientOrderId, recvWindow)));
    }

    /** Request to get OCO order status list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as String
     * **/
    public String getOcoOrderStatusList() throws Exception {
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get OCO order status list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as JsonArray
     * **/
    public JSONArray getJSONOcoOrderStatusList() throws Exception {
        return new JSONArray(getOcoOrderStatusList());
    }

    /** Request to get OCO order status list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOcoOrderStatusList() throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOcoOrderStatusList()));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as String
     * **/
    public String getOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        String params = getParamTimestamp()+"&fromId="+fromId+"&"+timeParam+"="+timeParamValue;
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as JsonArray
     * **/
    public JSONArray getJSONOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        return new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOcoOrderStatusList(long fromId, String timeParam, long timeParamValue) throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue)));
    }

    /** Request to get OCO order status list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as String
     * **/
    public String getOcoOrderStatusList(HashMap<String, Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as JsonArray
     * **/
    public JSONArray getJSONOcoOrderStatusList(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getOcoOrderStatusList(extraParams));
    }

    /** Request to get OCO order status list
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOcoOrderStatusList(HashMap<String, Object> extraParams) throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOcoOrderStatusList(extraParams)));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as String
     * **/
    public String getOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                        HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&fromId="+fromId+"&"+timeParam+"="+timeParamValue;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(OCO_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as JsonArray
     * **/
    public JSONArray getJSONOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                               HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue, extraParams));
    }

    /** Request to get OCO order status list
     * @param #fromId: identifier of origin
     * @param #timeParam: startTime or endTime;
     * @param #timeParamValue: value of #timeParam
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-all-oco-user_data
     * return OCO order status list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOcoOrderStatusList(long fromId, String timeParam, long timeParamValue,
                                                                   HashMap<String, Object> extraParams) throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOcoOrderStatusList(fromId, timeParam, timeParamValue, extraParams)));
    }

    /** Request to get open OCO order list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as String
     * **/
    public String getOpenOcoOrderList() throws Exception {
        return sendSignedRequest(OCO_OPEN_ORDER_LIST_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get open OCO order list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as JsonArray
     * **/
    public JSONArray getJSONOpenOcoOrderList() throws Exception {
        return new JSONArray(getOpenOcoOrderList());
    }

    /** Request to get open OCO order list
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOpenOcoOrderList() throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOpenOcoOrderList()));
    }

    /** Request to get open OCO order list
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as String
     * **/
    public String getOpenOcoOrderList(long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(OCO_OPEN_ORDER_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get open OCO order list
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as JsonArray
     * **/
    public JSONArray getJSONOpenOcoOrderList(long recvWindow) throws Exception {
        return new JSONArray(getOpenOcoOrderList(recvWindow));
    }

    /** Request to get open OCO order list
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-open-oco-user_data
     * return open OCO order list response as ArrayList<BaseOrderDetails>
     * **/
    public ArrayList<BaseOrderDetails> getObjectOpenOcoOrderList(long recvWindow) throws Exception {
        return assembleBaseOrderDetails(new JSONArray(getOpenOcoOrderList(recvWindow)));
    }

    /** Method to assemble an BaseOrderDetails object list
     * @param #jsonArray: obtained from Binance's request
     * return an ArrayList<BaseOrderDetails> with response data
     * **/
    private ArrayList<BaseOrderDetails> assembleBaseOrderDetails(JSONArray jsonArray){
        ArrayList<BaseOrderDetails> baseOrderDetailsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            baseOrderDetailsList.add(assembleBaseOrderDetails(jsonArray.getJSONObject(j)));
        return baseOrderDetailsList;
    }

    /** Method to assemble an BaseOrderDetails object
     * @param #order: obtained from Binance's request
     * return a BaseOrderDetails object with response data
     * **/
    private BaseOrderDetails assembleBaseOrderDetails(JSONObject order){
        return new BaseOrderDetails(order.getLong("orderListId"),
                order.getString("contingencyType"),
                order.getString("listStatusType"),
                order.getString("listOrderStatus"),
                order.getString("listClientOrderId"),
                order.getLong("transactionTime"),
                order.getString("symbol"),
                order
        );
    }

    /** Request to get spot account information
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as String
     * **/
    public String getSpotAccountInformation() throws Exception {
        return sendSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get spot account information
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as JsonObject
     * **/
    public JSONObject getJSONSpotAccountInformation() throws Exception {
        return new JSONObject(getSpotAccountInformation());
    }

    /** Request to get spot account information
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as SpotAccountInformation object
     * **/
    public SpotAccountInformation getObjectSpotAccountInformation() throws Exception {
        return assembleSpotAccountInformationObject(new JSONObject(getSpotAccountInformation()));
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as String
     * **/
    public String getSpotAccountInformation(double recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(SPOT_ACCOUNT_INFORMATION_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as JsonObject
     * **/
    public JSONObject getJSONSpotAccountInformation(double recvWindow) throws Exception {
        return new JSONObject(getSpotAccountInformation(recvWindow));
    }

    /** Request to get spot account information
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
     * return spot account information response as SpotAccountInformation object
     * **/
    public SpotAccountInformation getObjectSpotAccountInformation(double recvWindow) throws Exception {
        return assembleSpotAccountInformationObject(new JSONObject(getSpotAccountInformation(recvWindow)));
    }

    /** Method to assemble an SpotAccountInformation object
     * @param #jsonObject: obtained from Binance's request
     * return a SpotAccountInformation object with response data
     * **/
    private SpotAccountInformation assembleSpotAccountInformationObject(JSONObject jsonObject){
        return new SpotAccountInformation(jsonObject.getDouble("makerCommission"),
                jsonObject.getDouble("takerCommission"),
                jsonObject.getDouble("buyerCommission"),
                jsonObject.getDouble("sellerCommission"),
                jsonObject.getBoolean("canTrade"),
                jsonObject.getBoolean("canWithdraw"),
                jsonObject.getBoolean("canDeposit"),
                jsonObject.getLong("updateTime"),
                jsonObject.getString("accountType"),
                jsonObject
        );
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as String
     * **/
    public String getAccountTradeList(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as JsonArray
     * **/
    public JSONArray getJSONAccountTradeList(String symbol) throws Exception {
        return new JSONArray(getAccountTradeList(symbol));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as ArrayList<SpotAccountTradeList>
     * **/
    public ArrayList<SpotAccountTradeList> getObjectAccountTradeList(String symbol) throws Exception {
        return assembleSpotAccountTradeList(new JSONArray(getAccountTradeList(symbol)));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as String
     * **/
    public String getAccountTradeList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(SPOT_ACCOUNT_TRADE_LIST_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as JsonArray
     * **/
    public JSONArray getJSONAccountTradeList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAccountTradeList(symbol, extraParams));
    }

    /** Request to get Account Trade List
     * @param #symbol: symbol used in cancel oco order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted orderId,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-trade-list-user_data
     * return Account Trade List response as ArrayList<SpotAccountTradeList>
     * **/
    public ArrayList<SpotAccountTradeList> getObjectAccountTradeList(String symbol, HashMap<String, Object> extraParams)
            throws Exception {
        return assembleSpotAccountTradeList(new JSONArray(getAccountTradeList(symbol,extraParams)));
    }

    /** Method to assemble an SpotAccountTradeList object
     * @param #jsonArray: obtained from Binance's request
     * return a SpotAccountTradeList object with response data
     * **/
    private ArrayList<SpotAccountTradeList> assembleSpotAccountTradeList(JSONArray jsonArray){
        ArrayList<SpotAccountTradeList> spotAccountTradeLists = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject trade = jsonArray.getJSONObject(j);
            spotAccountTradeLists.add(new SpotAccountTradeList(trade.getString("symbol"),
                    trade.getLong("id"),
                    trade.getLong("orderId"),
                    trade.getLong("orderListId"),
                    trade.getDouble("price"),
                    trade.getDouble("qty"),
                    trade.getDouble("quoteQty"),
                    trade.getDouble("commission"),
                    trade.getString("commissionAsset"),
                    trade.getLong("time"),
                    trade.getBoolean("isBuyer"),
                    trade.getBoolean("isMaker"),
                    trade.getBoolean("isBestMatch")
            ));
        }
        return spotAccountTradeLists;
    }

    /** Request to get current order count usage
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as String
     * **/
    public String getCurrentOrderCountUsage() throws Exception {
        return sendSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get current order count usage
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as JsonArray
     * **/
    public JSONArray getJSONCurrentOrderCountUsage() throws Exception {
        return new JSONArray(getCurrentOrderCountUsage());
    }

    /** Request to get current order count usage
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as ArrayList<OrderCountUsage>
     * **/
    public ArrayList<OrderCountUsage> getObjectCurrentOrderCountUsage() throws Exception {
        return assembleOrderCountUsageList(new JSONArray(getCurrentOrderCountUsage()));
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as String
     * **/
    public String getCurrentOrderCountUsage(long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(SPOT_ACCOUNT_CURRENT_ORDER_COUNT_USAGE,params,GET_METHOD);
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as JsonArray
     * **/
    public JSONArray getJSONCurrentOrderCountUsage(long recvWindow) throws Exception {
        return new JSONArray(getCurrentOrderCountUsage(recvWindow));
    }

    /** Request to get current order count usage
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-current-order-count-usage-trade
     * return current order count usage response as ArrayList<OrderCountUsage>
     * **/
    public ArrayList<OrderCountUsage> getObjectCurrentOrderCountUsage(long recvWindow) throws Exception {
        return assembleOrderCountUsageList(new JSONArray(getCurrentOrderCountUsage(recvWindow)));
    }

    /** Method to assemble an OrderCountUsage object
     * @param #jsonArray: obtained from Binance's request
     * return a OrderCountUsage object with response data
     * **/
    private ArrayList<OrderCountUsage> assembleOrderCountUsageList(JSONArray jsonArray){
        ArrayList<OrderCountUsage> orderCountUsages = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject order = jsonArray.getJSONObject(j);
            orderCountUsages.add(new OrderCountUsage(order.getString("rateLimitType"),
                    order.getString("interval"),
                    order.getDouble("intervalNum"),
                    order.getDouble("limit"),
                    order.getInt("count")
            ));
        }
        return orderCountUsages;
    }

}
