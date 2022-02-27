package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel.CancelOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Cancel.CancelOrderComposed;
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

    public static final String BUY = "BUY";
    public static final String SELL = "SELL";

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
        return sendOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
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
        return sendOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
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
    public String newOrder(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return sendOrderRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
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
    public JSONObject newOrderJSON(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(newOrder(symbol,side,type,extraParams));
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
    public ACKOrder newOrderObject(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(newOrder(symbol,side,type,extraParams));
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
    public String newOrder(String symbol, String side, String type, String newOrderRespType,
                           HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return sendOrderRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
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
    public JSONObject newOrderJSON(String symbol, String side, String type, String newOrderRespType,
                                   HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(newOrder(symbol,side,type,newOrderRespType,extraParams));
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
    public ACKOrder newOrderObject(String symbol, String side, String type, String newOrderRespType,
                                   HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(newOrder(symbol,side,type,newOrderRespType,extraParams));
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
        return sendOrderRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
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
        return sendOrderRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
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
        return sendOrderRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
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
        return sendOrderRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
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

    public String cancelAllOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendOrderRequest(OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONArray cancelAllOpenOrdersJSON(String symbol) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol));
    }

    public OpenOrders cancelAllOpenOrdersObject(String symbol) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol)));
    }

    public String cancelAllOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&recvWindow="+recvWindow;
        return sendOrderRequest(OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONArray cancelAllOpenOrdersJSON(String symbol, long recvWindow) throws Exception {
        return new JSONArray(cancelAllOpenOrders(symbol, recvWindow));
    }

    public OpenOrders cancelAllOpenOrdersObject(String symbol, long recvWindow) throws Exception {
        return cancelAllOpenOrdersObject(new JSONArray(cancelAllOpenOrders(symbol, recvWindow)));
    }

    private OpenOrders cancelAllOpenOrdersObject(JSONArray jsonArray){
        ArrayList<CancelOrder> cancelOrders = new ArrayList<>();
        ArrayList<CancelOrderComposed> cancelOrderComposeds = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject order = jsonArray.getJSONObject(j);
            try {
                String contingencyType = order.getString("contingencyType");
                cancelOrderComposeds.add(new CancelOrderComposed(order.getLong("orderListId"),
                        contingencyType,
                        order.getString("listStatusType"),
                        order.getString("listOrderStatus"),
                        order.getString("listClientOrderId"),
                        order.getLong("transactionTime"),
                        order.getString("symbol"),
                        order
                ));
            }catch (JSONException e){
                cancelOrders.add(CancelOrder.assembleCancelOrderObject(order));
            }
        }
        return new OpenOrders(cancelOrders,cancelOrderComposeds);
    }

    public String getOrderStatus(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        return sendOrderRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONOrderStatus(String symbol, long orderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId));
    }

    public OrderStatus getObjectOrderStatus(String symbol, long orderId) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol, orderId)));
    }

    public String getOrderStatus(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId ;
        return sendOrderRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId));
    }

    public OrderStatus getObjectOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol,origClientOrderId)));
    }

    public String getOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId+"&recvWindow="+recvWindow;
        return sendOrderRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, orderId, recvWindow));
    }

    public OrderStatus getObjectOrderStatus(String symbol, long orderId, long recvWindow) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol, orderId, recvWindow)));
    }

    public String getOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&recvWindow="+recvWindow;
        return sendOrderRequest(ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return new JSONObject(getOrderStatus(symbol, origClientOrderId,recvWindow));
    }

    public OrderStatus getObjectOrderStatus(String symbol, String origClientOrderId, long recvWindow) throws Exception {
        return getObjectOrderStatus(new JSONObject(getOrderStatus(symbol,origClientOrderId,recvWindow)));
    }

    public String getCurrentOpenOrders() throws Exception {
        return sendOrderRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONArray getJSONCurrentOpenOrders() throws Exception {
        return new JSONArray(sendOrderRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD));
    }

    public ArrayList<OrderStatus> getCurrentOpenOrdersList() throws Exception {
        return assembleOrderStatusList(new JSONArray(sendOrderRequest(OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD)));
    }

    public String getCurrentOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp();
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendOrderRequest(OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONCurrentOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getCurrentOpenOrders(extraParams));
    }

    public ArrayList<OrderStatus> getCurrentOpenOrdersList(HashMap<String, Object> extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getCurrentOpenOrders(extraParams)));
    }

    public String getAllOrdersList(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendOrderRequest(ALL_ORDERS_LIST_ENPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllOrdersList(String symbol) throws Exception {
        return new JSONArray(getAllOrdersList(symbol));
    }

    public ArrayList<OrderStatus> getObjectAllOrdersList(String symbol) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol)));
    }

    public String getAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendOrderRequest(ALL_ORDERS_LIST_ENPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOrdersList(symbol, extraParams));
    }

    public ArrayList<OrderStatus> getObjectAllOrdersList(String symbol,HashMap<String, Object> extraParams) throws Exception {
        return assembleOrderStatusList(new JSONArray(getAllOrdersList(symbol, extraParams)));
    }

    /** Method to get timestamp for request
     * any params required
     * return "?timestamp=" + getTimestamp() return value
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

    private ArrayList<OrderStatus> assembleOrderStatusList(JSONArray jsonArray){
        ArrayList<OrderStatus> orderStatuses = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            orderStatuses.add(getObjectOrderStatus(jsonArray.getJSONObject(j)));
        return orderStatuses;
    }

    private String sendOrderRequest(String endpoint, String params, String method) throws Exception {
        return getRequestResponse(endpoint,params+getSignature(params),method,apiKey);
    }

}
