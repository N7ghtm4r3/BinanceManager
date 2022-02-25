package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ACKOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.FullOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ResultOrder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.SEND_NEW_ORDER_ENDPOINT;
import static com.tecknobit.binancemanager.Constants.EndpointsList.TEST_NEW_ORDER_ENDPOINT;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.POST_METHOD;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ACKOrder.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.ACKOrder.LIMIT;

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
        return newOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams));
    }

    public String testNewOrder(String symbol, String side, String type, String newOrderRespType,
                               HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return newOrderRequest(TEST_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams));
    }

    public String sendNewOrder(String symbol, String side, String type,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        return newOrderRequest(SEND_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams));
    }

    public String sendNewOrder(String symbol, String side, String type, String newOrderRespType,
                               HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        return newOrderRequest(SEND_NEW_ORDER_ENDPOINT,requestManager.assembleExtraParams(params,extraParams));
    }

    public JSONObject sendJSONNewOrder(String symbol, String side, String type,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(sendNewOrder(symbol,side,type,extraParams));
    }

    public JSONObject sendJSONNewOrder(String symbol, String side, String type, String newOrderRespType,
                                       HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(sendNewOrder(symbol,side,type,newOrderRespType,extraParams));
    }

    public ACKOrder sendObjectNewOrder(String symbol, String side, String type,HashMap<String,Object> extraParams) throws Exception {
        jsonObject = new JSONObject(sendNewOrder(symbol,side,type,extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return getFullOrderResponse(jsonObject);
        else
            return getACKResponse(jsonObject);
    }

    public ACKOrder sendObjectNewOrder(String symbol, String side, String type,String newOrderRespType,
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

    private String newOrderRequest(String endpoint,String params) throws Exception {
        return getRequestResponse(endpoint,params+getSignature(params),POST_METHOD,apiKey);
    }

}
