package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ACKOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.CancelOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.FullOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ResultOrder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.ORDER_ENDPOINT;
import static com.tecknobit.binancemanager.Constants.EndpointsList.TEST_NEW_ORDER_ENDPOINT;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.DELETE_METHOD;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.POST_METHOD;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ACKOrder.*;

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

    public String testNewOrder(String symbol, String side, String type,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return sendOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    public String testNewOrder(String symbol, String side, String type, String newOrderRespType,
                               HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return sendOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    public String newOrder(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return sendOrderRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    public String newOrder(String symbol, String side, String type, String newOrderRespType,
                           HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return sendOrderRequest(ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams),POST_METHOD);
    }

    public JSONObject newOrderJSON(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(newOrder(symbol,side,type,extraParams));
    }

    public JSONObject newOrderJSON(String symbol, String side, String type, String newOrderRespType,
                                   HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(newOrder(symbol,side,type,newOrderRespType,extraParams));
    }

    public ACKOrder newOrderObject(String symbol, String side, String type, HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(newOrder(symbol,side,type,extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return getFullOrderResponse(jsonObject);
        else
            return getACKResponse(jsonObject);
    }

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

    private ACKOrder getACKResponse(JSONObject response){
        return new ACKOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getLong("orderListId"),
                response.getString("clientOrderId"),
                response.getLong("transactTime")
        );
    }

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

    public String cancelOrder(String symbol, long orderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        return sendOrderRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONObject cancelOrderJSON(String symbol, long orderId) throws Exception {
       return new JSONObject(cancelOrder(symbol, orderId));
    }

    public CancelOrder cancelOrderObject(String symbol, long orderId) throws Exception {
        jsonObject = new JSONObject(cancelOrderJSON(symbol, orderId));
        return cancelOrderObject(jsonObject);
    }

    public String cancelOrder(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId ;
        return sendOrderRequest(ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId));
    }

    public CancelOrder cancelOrderObject(String symbol, String origClientOrderId) throws Exception {
        jsonObject = new JSONObject(cancelOrderJSON(symbol, origClientOrderId));
        return cancelOrderObject(jsonObject);
    }

    public String cancelOrder(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderId="+orderId;
        params = requestManager.assembleExtraParams(params, extraParams);
        return sendOrderRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    public JSONObject cancelOrderJSON(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, orderId, extraParams));
    }

    public CancelOrder cancelOrderObject(String symbol, long orderId, HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(cancelOrderJSON(symbol, orderId, extraParams));
        return cancelOrderObject(jsonObject);
    }

    public String cancelOrder(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId ="+origClientOrderId;
        params = requestManager.assembleExtraParams(params, extraParams);
        return sendOrderRequest(ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    public JSONObject cancelOrderJSON(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(cancelOrder(symbol, origClientOrderId, extraParams));
    }

    public CancelOrder cancelOrderObject(String symbol, String origClientOrderId, HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(cancelOrderJSON(symbol, origClientOrderId, extraParams));
        return cancelOrderObject(jsonObject);
    }

    private CancelOrder cancelOrderObject(JSONObject response){
        return new CancelOrder(response.getString("symbol"),
                response.getString("origClientOrderId"),
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
                response.getString("side")
        );
    }

    private String sendOrderRequest(String endpoint, String params, String method) throws Exception {
        return getRequestResponse(endpoint,params+getSignature(params),method,apiKey);
    }

}
