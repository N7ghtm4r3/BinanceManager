package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.CrossMarginAccountDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.*;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginAsset;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPair;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPriceIndex;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.CancelMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.ComposedMarginOrderDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.OpenMarginOrders;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.ACKMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.FullMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.MarginOrderStatus;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.ResultMarginOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.TradeConstants.*;

/**
 *  The {@code BinanceMarginManager} class is useful to manage all Binance Margin Endpoints
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceMarginManager extends BinanceSignedManager {

    public static final int MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT = 1;
    public static final int CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT = 2;

    /** Constructor to init BinanceMarginManager
     * @param #baseEndpoint base endpoint to work on
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * **/
    public BinanceMarginManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /** Constructor to init BinanceMarginManager
     * @param #apiKey your api key
     * @param #secretKey your secret key
     * automatically set a working endpoint
     * **/
    public BinanceMarginManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null, apiKey, secretKey);
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer as String
     * **/
    public String executeCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+asset+"&type="+type;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer as JsonObject
     * **/
    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type));
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer's tranId as long
     * **/
    public long getExecuteCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset,amount,type));
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer as String
     * **/
    public String executeCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+asset+"&type="+type+"&recvWindow="+recvWindow;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer as JsonObject
     * **/
    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    /** Request to execute a transfer
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount to be transferred
     * @param #type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin
     * return result transfer's tranId as long
     * **/
    public long getExecuteCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result of margin account borrow request as String
     * **/
    public String applyMarginAccountBorrow(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result of account borrow request as JsonObject
     * **/
    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result account borrow's tranId as long
     * **/
    public long getApplyMarginAccountBorrow(String asset, double amount) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result of margin account borrow request as String
     * **/
    public String applyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result of margin account borrow request as JsonObject
     * **/
    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * return result account borrow's tranId as long
     * **/
    public long getApplyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result of repay margin account request as String
     * **/
    public String repeayMarginAccount(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result of repay margin account request as JsonObject
     * **/
    public JSONObject repeayJSONMarginAccount(String asset, double amount) throws Exception {
        return new JSONObject(repeayMarginAccount(asset, amount));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result margin repeay's tranId as long
     * **/
    public long getRepeayMarginAccount(String asset, double amount) throws Exception {
        return getTransactionId(repeayMarginAccount(asset, amount));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result of repay margin account request as String
     * **/
    public String repeayMarginAccount(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result of repay margin account request as JsonObject
     * **/
    public JSONObject repeayJSONMarginAccount(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(repeayMarginAccount(asset, amount, extraParams));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * return result margin repeay's tranId as long
     * **/
    public long getRepeayMarginAccount(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        return getTransactionId(repeayMarginAccount(asset, amount, extraParams));
    }

    /** Method to get tranId value
     * @param #stringSource: obtained from Binance's request
     * return tranId value as long
     * **/
    private long getTransactionId(String stringSource){
        jsonObject = new JSONObject(stringSource);
        return jsonObject.getLong("tranId");
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * return detail's asset as String
     * **/
    public String queryMarginAsset(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * return detail's asset as JsonObject
     * **/
    public JSONObject queryJSONMarginAsset(String asset) throws Exception {
        return new JSONObject(queryMarginAsset(asset));
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * return detail's asset as {@link MarginAsset} object
     * **/
    public MarginAsset queryObjectMarginAsset(String asset) throws Exception {
        return assembleMarginAssetObject(new JSONObject(queryMarginAsset(asset)));
    }

    /** Request to get all margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * return all asset details as String
     * **/
    public String queryAllMarginAssets() throws Exception {
        return sendSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get all margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * return all asset details as JsonArray
     * **/
    public JSONArray queryJSONAllMarginAssets() throws Exception {
        return new JSONArray(queryAllMarginAssets());
    }

    /** Request to get margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * return detail's asset as ArrayList<{@link MarginAsset}>
     * **/
    public ArrayList<MarginAsset> queryAllMarginAssetsList() throws Exception {
        ArrayList<MarginAsset> marginAssets = new ArrayList<>();
        jsonArray = new JSONArray(queryAllMarginAssets());
        for (int j=0; j < jsonArray.length(); j++)
            marginAssets.add(assembleMarginAssetObject(jsonArray.getJSONObject(j)));
        return marginAssets;
    }

    /** Method to assemble an MarginAsset object
     * @param #jsonObject: obtained from Binance's request
     * return a MarginAsset object
     * **/
    private MarginAsset assembleMarginAssetObject(JSONObject jsonObject){
        return new MarginAsset(jsonObject.getString("assetFullName"),
                jsonObject.getString("assetName"),
                jsonObject.getBoolean("isBorrowable"),
                jsonObject.getBoolean("isMortgageable"),
                jsonObject.getDouble("userMinBorrow"),
                jsonObject.getDouble("userMinRepay")
        );
    }

    /** Request to get cross margin pair details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data
     * return cross margin pair as String
     * **/
    public String queryCrossMarginPair(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get cross margin pair details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data
     * return cross margin pair as JsonObject
     * **/
    public JSONObject queryJSONCrossMarginPair(String asset) throws Exception {
        return new JSONObject(queryCrossMarginPair(asset));
    }

    /** Request to get cross margin pair details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data
     * return cross margin pair as {@link MarginPair} object
     * **/
    public MarginPair queryObjectCrossMarginPair(String asset) throws Exception {
        return assembleMarginPairObject(new JSONObject(queryCrossMarginPair(asset)));
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * return all cross margin pair as String
     * **/
    public String queryAllMarginPairs() throws Exception {
        return sendSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * return all cross margin pair as JsonArray
     * **/
    public JSONArray queryJSONAllMarginPairs() throws Exception {
        return new JSONArray(queryAllMarginPairs());
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * return all cross margin pair as ArrayList<{@link MarginPair}>
     * **/
    public ArrayList<MarginPair> queryAllMarginPairsList() throws Exception {
        ArrayList<MarginPair> marginAssets = new ArrayList<>();
        jsonArray = new JSONArray(queryAllMarginPairs());
        for (int j=0; j < jsonArray.length(); j++)
            marginAssets.add(assembleMarginPairObject(jsonArray.getJSONObject(j)));
        return marginAssets;
    }

    /** Method to assemble an MarginPair object
     * @param #jsonObject: obtained from Binance's request
     * return a MarginPair object
     * **/
    private MarginPair assembleMarginPairObject(JSONObject jsonObject){
        return new MarginPair(jsonObject.getLong("id"),
                jsonObject.getString("symbol"),
                jsonObject.getString("base"),
                jsonObject.getString("quote"),
                jsonObject.getBoolean("isMarginTrade"),
                jsonObject.getBoolean("isBuyAllowed"),
                jsonObject.getBoolean("isSellAllowed")
        );
    }

    /** Request to get priceIndex of a symbol
     * @param #symbol: symbol used to get price index
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
     * return priceIndex of a symbol as String
     * **/
    public String getMarginPriceIndex(String symbol) throws Exception {
        return sendSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get priceIndex of a symbol
     * @param #symbol: symbol used to get price index
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
     * return priceIndex of a symbol as JsonObject
     * **/
    public JSONObject getJSONMarginPriceIndex(String symbol) throws Exception {
        return new JSONObject(getMarginPriceIndex(symbol));
    }

    /** Request to get priceIndex of a symbol
     * @param #symbol: symbol used to get price index
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
     * return priceIndex of a symbol as {@link MarginPriceIndex} object
     * **/
    public MarginPriceIndex getObjectMarginPriceIndex(String symbol) throws Exception {
        jsonObject = new JSONObject(getMarginPriceIndex(symbol));
        return new MarginPriceIndex(jsonObject.getLong("calcTime"),
                jsonObject.getDouble("price"),
                jsonObject.getString("symbol")
        );
    }

    /** Request to send a new margin order
     * @param #symbol: symbol used to the order es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as String
     * **/
    public String sendNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send a new margin order
     * @param #symbol: symbol used to the order es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as JsonObject
     * **/
    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, extraParams));
    }

    /** Request to send a spot order
     * @param #symbol: symbol used in the request es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as AckOrder (next to cast in base at type used)
     * @implNote if type LIMIT or MARKET will be must cast in {@link FullMarginOrder} object
     * @implNote with other types will be an {@link ACKMarginOrder} object
     * **/
    public ACKMarginOrder sendObjectNewMarginOrder(String symbol, String side, String type,
                                                   HashMap<String, Object> extraParams) throws Exception {
        jsonObject = new JSONObject(sendJSONNewMarginOrder(symbol, side, type, extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return getFullOrderResponse(jsonObject);
        else
            return getACKResponse(jsonObject);
    }

    /** Request to send a new margin order
     * @param #symbol: symbol used to the order es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as String
     * **/
    public String sendNewMarginOrder(String symbol, String side, String type,String newOrderRespType,
                                     HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send a new margin order
     * @param #symbol: symbol used to the order es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as JsonObject
     * **/
    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams,
                                             String newOrderRespType) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, newOrderRespType, extraParams));
    }

    /** Request to send a new margin order
     * @param #symbol: symbol used to the order es. BTCBUSD
     * @param #side: BUY or SELL order
     * @param #type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param #newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param #extraParams: extraParams of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade
     * return result of the order as AckOrder (next to cast in base at newOrderRespType used)
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_RESULT object will be {@link ResultMarginOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_FULL object will be {@link FullMarginOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_ACK object will be {@link ACKMarginOrder}
     * **/
    public ACKMarginOrder sendObjectNewMarginOrder(String symbol, String side, String type,HashMap<String, Object> extraParams,
                                                   String newOrderRespType) throws Exception {
        jsonObject = new JSONObject(sendJSONNewMarginOrder(symbol, side, type, extraParams, newOrderRespType));
        switch (newOrderRespType){
            case NEW_ORDER_RESP_TYPE_RESULT:
                return new ResultMarginOrder(jsonObject.getString("symbol"),
                        jsonObject.getLong("orderId"),
                        jsonObject.getString("clientOrderId"),
                        jsonObject.getLong("transactTime"),
                        jsonObject.getBoolean("isIsolated"),
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

    /** Method to assemble an ACKMarginOrder object
     * @param #response: obtained from Binance's request
     * return an ACKMarginOrder object with response data
     * **/
    private ACKMarginOrder getACKResponse(JSONObject response){
        return new ACKMarginOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getString("clientOrderId"),
                response.getLong("transactTime"),
                response.getBoolean("isIsolated")
        );
    }

    /** Method to assemble an FullMarginOrder object
     * @param #response: obtained from Binance's request
     * return a FullMarginOrder object with response data
     * **/
    private FullMarginOrder getFullOrderResponse(JSONObject response){
        return new FullMarginOrder(response.getString("symbol"),
                response.getLong("orderId"),
                response.getString("clientOrderId"),
                response.getLong("transactTime"),
                response.getBoolean("isIsolated"),
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

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as String
     * **/
    public String cancelMarginOrder(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as JsonObject
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as {@link CancelMarginOrder} object
     * **/
    public CancelMarginOrder cancelObjectMarginOrder(String symbol) throws Exception {
        return CancelMarginOrder.assembleCancelMarginOrderObject(new JSONObject(cancelMarginOrder(symbol)));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as String
     * **/
    public String cancelMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as JsonObject
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol, extraParams));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * return cancel a margin order response as {@link CancelMarginOrder} object
     * **/
    public CancelMarginOrder cancelObjectMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return CancelMarginOrder.assembleCancelMarginOrderObject(new JSONObject(cancelMarginOrder(symbol,extraParams)));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as String
     * **/
    public String cancelAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as JsonArray
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as {@link OpenMarginOrders} object
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol)));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as String
     * **/
    public String cancelAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as JsonObject
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol,extraParams));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * return cancel all margin order response as ArrayList<{@link OpenMarginOrders}>
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol,extraParams)));
    }

    /** Method to assemble an {@link OpenMarginOrders} object
     * @param #jsonArray: obtained from Binance's request
     * return a {@link OpenMarginOrders} list as ArrayList
     * **/
    private OpenMarginOrders assembleOpenMarginOrdersObject(JSONArray jsonArray){
        ArrayList<CancelMarginOrder> cancelMarginOrders = new ArrayList<>();
        ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject openMarginOrder = jsonArray.getJSONObject(j);
            if(!openMarginOrder.getString("contingencyType").isEmpty()) {
                composedMarginOrderDetails.add(new ComposedMarginOrderDetails(openMarginOrder.getLong("orderListId"),
                        openMarginOrder.getString("contingencyType"),
                        openMarginOrder.getString("listStatusType"),
                        openMarginOrder.getString("listOrderStatus"),
                        openMarginOrder.getString("listClientOrderId"),
                        openMarginOrder.getLong("transactionTime"),
                        openMarginOrder.getString("symbol"),
                        openMarginOrder.getBoolean("isIsolated"),
                        openMarginOrder
                ));
            }else
                CancelMarginOrder.assembleCancelMarginOrderObject(openMarginOrder);

        }
        return new OpenMarginOrders(cancelMarginOrders,composedMarginOrderDetails);
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory() throws Exception {
        return new JSONObject(getCrossMarginTransferHistory());
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory() throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory()));
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(extraParams));
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(extraParams)));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset)));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset,extraParams));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset,extraParams)));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as String
     * **/
    public String getQueryLoanRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as JsonObject
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset)));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as String
     * **/
    public String getQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as JsonObject
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset, extraParams));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset, extraParams)));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as String
     * **/
    public String getQueryRepayRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as JsonObject
     * **/
    public JSONObject getJSONRepayRecord(String asset) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset)));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as String
     * **/
    public String getQueryRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as JsonObject
     * **/
    public JSONObject getJSONRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset, extraParams));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset, extraParams)));
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as String
     * **/
    public String getInterestHistory() throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory() throws Exception {
        return new JSONObject(getInterestHistory());
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory() throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory()));
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as String
     * **/
    public String getInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(extraParams));
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(extraParams)));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as String
     * **/
    public String getInterestHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory(String asset) throws Exception {
        return new JSONObject(getInterestHistory(asset));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset)));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as String
     * **/
    public String getInterestHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as String
     * **/
    public JSONObject getJSONInterestHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(asset,extraParams));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset,extraParams)));
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as String
     * **/
    public String getMarginForceLiquidation() throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as JsonObject
     * **/
    public JSONObject getJSONMarginForceLiquidation() throws Exception {
        return new JSONObject(getMarginForceLiquidation());
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation() throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation()));
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as String
     * **/
    public String getMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as JsonObject
     * **/
    public JSONObject getJSONMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getMarginForceLiquidation(extraParams));
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation(extraParams)));
    }

    public String getCrossMarginAccountDetails() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONCrossMarginAccountDetails() throws Exception {
        return new JSONObject(getCrossMarginAccountDetails());
    }

    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails() throws Exception {
        return assembleCrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails()));
    }

    public String getCrossMarginAccountDetails(long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONCrossMarginAccountDetails(long recvWindow) throws Exception {
        return new JSONObject(getCrossMarginAccountDetails(recvWindow));
    }

    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails(long recvWindow) throws Exception {
        return assembleCrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails(recvWindow)));
    }

    private CrossMarginAccountDetails assembleCrossMarginAccountDetails(JSONObject jsonObject){
        return new CrossMarginAccountDetails(jsonObject.getBoolean("borrowEnabled"),
                jsonObject.getDouble("marginLevel"),
                jsonObject.getDouble("totalAssetOfBtc"),
                jsonObject.getDouble("totalLiabilityOfBtc"),
                jsonObject.getDouble("totalNetAssetOfBtc"),
                jsonObject.getBoolean("tradeEnabled"),
                jsonObject.getBoolean("transferEnabled"),
                jsonObject.getJSONArray("userAssets")
        );
    }

    public String getMarginOrderStatus(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginOrderStatus(String symbol) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol));
    }

    public MarginOrderStatus getObjectMarginOrderStatus(String symbol) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol)));
    }

    public String getMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol,extraParams));
    }

    public MarginOrderStatus getObjectMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol,extraParams)));
    }

    public String getAllMarginOpenOrders() throws Exception {
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONArray getJSONAllMarginOpenOrders() throws Exception {
        return new JSONArray(getAllMarginOpenOrders());
    }

    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList() throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders()));
    }

    public String getAllMarginOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated=true";
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllMarginOpenOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol));
    }

    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol)));
    }

    public String getAllMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(extraParams));
    }

    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(extraParams)));
    }

    public String getAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated=true"+"&recvWindow";
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol, recvWindow));
    }

    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol, long recvWindow) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol, recvWindow)));
    }

    public String getAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol));
    }

    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol)));
    }

    public String getAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol, extraParams));
    }

    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol, extraParams)));
    }

    private ArrayList<MarginOrderStatus> assembleMarginOrdersList(JSONArray jsonArray) {
        ArrayList<MarginOrderStatus> marginOrderStatus = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            marginOrderStatus.add(assembleMarginOrderStatus(jsonArray.getJSONObject(j)));
        return marginOrderStatus;
    }

    private MarginOrderStatus assembleMarginOrderStatus(JSONObject jsonObject){
        return new MarginOrderStatus(jsonObject.getString("symbol"),
                jsonObject.getLong("orderId"),
                jsonObject.getString("clientOrderId"),
                jsonObject.getLong("updateTime"),
                jsonObject.getBoolean("isIsolated"),
                jsonObject.getDouble("price"),
                jsonObject.getDouble("origQty"),
                jsonObject.getDouble("executedQty"),
                jsonObject.getDouble("cummulativeQuoteQty"),
                jsonObject.getString("status"),
                jsonObject.getString("timeInForce"),
                jsonObject.getString("type"),
                jsonObject.getString("side"),
                jsonObject.getDouble("icebergQty"),
                jsonObject.getBoolean("isWorking"),
                jsonObject.getDouble("stopPrice"),
                jsonObject.getLong("time")
        );
    }

}

