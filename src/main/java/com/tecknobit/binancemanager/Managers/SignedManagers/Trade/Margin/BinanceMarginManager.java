package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.CrossMarginAccountDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.IsolatedDetails.ComposedIMarginAccountInfo;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.IsolatedDetails.IsolatedMarginAccountInfo;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccountTrade;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginMaxBorrow;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.*;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginAsset;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPair;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPriceIndex;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details.*;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.ACKMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.FullMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.MarginOrderStatus;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.ResultMarginOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.TradeConstants.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.IsolatedDetails.IsolatedMarginAccountInfo.assembleIsolatedMarginAccountInfoList;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details.ComposedMarginOrderDetails.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details.OCOMarginOrder.assembleOCOMarginOrder;

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
     * @return result transfer as String
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
     * @return result transfer as JsonObject
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
     * @return result transfer's tranId as long
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
     * @return result transfer as String
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
     * @return result transfer as JsonObject
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
     * @return result transfer's tranId as long
     * **/
    public long getExecuteCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * @return result of margin account borrow request as String
     * **/
    public String applyMarginAccountBorrow(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * @return result of account borrow request as JsonObject
     * **/
    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount));
    }

    /** Request to execute a margin account borrow
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount borrowed
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin
     * @return result account borrow's tranId as long
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
     * @return result of margin account borrow request as String
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
     * @return result of margin account borrow request as JsonObject
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
     * @return result account borrow's tranId as long
     * **/
    public long getApplyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * @return result of repay margin account request as String
     * **/
    public String repeayMarginAccount(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,POST_METHOD);
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * @return result of repay margin account request as JsonObject
     * **/
    public JSONObject repeayJSONMarginAccount(String asset, double amount) throws Exception {
        return new JSONObject(repeayMarginAccount(asset, amount));
    }

    /** Request to execute an repay margin account request
     * @param #asset: asset used in the request es. BTC
     * @param #amount: the amount repaied
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin
     * @return result margin repeay's tranId as long
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
     * @return result of repay margin account request as String
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
     * @return result of repay margin account request as JsonObject
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
     * @return result margin repeay's tranId as long
     * **/
    public long getRepeayMarginAccount(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        return getTransactionId(repeayMarginAccount(asset, amount, extraParams));
    }

    /** Method to get tranId value
     * @param #stringSource: obtained from Binance's request
     * @return tranId value as long
     * **/
    private long getTransactionId(String stringSource){
        jsonObject = new JSONObject(stringSource);
        return jsonObject.getLong("tranId");
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * @return detail's asset as String
     * **/
    public String queryMarginAsset(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * @return detail's asset as JsonObject
     * **/
    public JSONObject queryJSONMarginAsset(String asset) throws Exception {
        return new JSONObject(queryMarginAsset(asset));
    }

    /** Request to get margin asset details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data
     * @return detail's asset as {@link MarginAsset} object
     * **/
    public MarginAsset queryObjectMarginAsset(String asset) throws Exception {
        return assembleMarginAssetObject(new JSONObject(queryMarginAsset(asset)));
    }

    /** Request to get all margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * @return all asset details as String
     * **/
    public String queryAllMarginAssets() throws Exception {
        return sendSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get all margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * @return all asset details as JsonArray
     * **/
    public JSONArray queryJSONAllMarginAssets() throws Exception {
        return new JSONArray(queryAllMarginAssets());
    }

    /** Request to get margin asset details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data
     * @return detail's asset as ArrayList<{@link MarginAsset}>
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
     * @return a MarginAsset object
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
     * @return cross margin pair as String
     * **/
    public String queryCrossMarginPair(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get cross margin pair details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data
     * @return cross margin pair as JsonObject
     * **/
    public JSONObject queryJSONCrossMarginPair(String asset) throws Exception {
        return new JSONObject(queryCrossMarginPair(asset));
    }

    /** Request to get cross margin pair details
     * @param #asset: asset used in the request es. BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data
     * @return cross margin pair as {@link MarginPair} object
     * **/
    public MarginPair queryObjectCrossMarginPair(String asset) throws Exception {
        return assembleMarginPairObject(new JSONObject(queryCrossMarginPair(asset)));
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * @return all cross margin pair as String
     * **/
    public String queryAllMarginPairs() throws Exception {
        return sendSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT,"",GET_METHOD);
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * @return all cross margin pair as JsonArray
     * **/
    public JSONArray queryJSONAllMarginPairs() throws Exception {
        return new JSONArray(queryAllMarginPairs());
    }

    /** Request to get all cross margin pair details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data
     * @return all cross margin pair as ArrayList<{@link MarginPair}>
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
     * @return a MarginPair object
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
     * @return priceIndex of a symbol as String
     * **/
    public String getMarginPriceIndex(String symbol) throws Exception {
        return sendSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    /** Request to get priceIndex of a symbol
     * @param #symbol: symbol used to get price index
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
     * @return priceIndex of a symbol as JsonObject
     * **/
    public JSONObject getJSONMarginPriceIndex(String symbol) throws Exception {
        return new JSONObject(getMarginPriceIndex(symbol));
    }

    /** Request to get priceIndex of a symbol
     * @param #symbol: symbol used to get price index
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data
     * @return priceIndex of a symbol as {@link MarginPriceIndex} object
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
     * @return result of the order as String
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
     * @return result of the order as JsonObject
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
     * @return result of the order as AckOrder (next to cast in base at type used)
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
     * @return result of the order as String
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
     * @return result of the order as JsonObject
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
     * @return result of the order as AckOrder (next to cast in base at newOrderRespType used)
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
     * @return an ACKMarginOrder object with response data
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
     * @return a FullMarginOrder object with response data
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
     * @return cancel a margin order response as String
     * **/
    public String cancelMarginOrder(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * @return cancel a margin order response as JsonObject
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * @return cancel a margin order response as {@link DetailMarginOrder} object
     * **/
    public DetailMarginOrder cancelObjectMarginOrder(String symbol) throws Exception {
        return DetailMarginOrder.assembleDetailMarginOrderObject(new JSONObject(cancelMarginOrder(symbol)));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * @return cancel a margin order response as String
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
     * @return cancel a margin order response as JsonObject
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol, extraParams));
    }

    /** Request to get cancel a margin order
     * @param #symbol: symbol used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
     * @return cancel a margin order response as {@link DetailMarginOrder} object
     * **/
    public DetailMarginOrder cancelObjectMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return DetailMarginOrder.assembleDetailMarginOrderObject(new JSONObject(cancelMarginOrder(symbol,extraParams)));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * @return cancel all margin order response as String
     * **/
    public String cancelAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * @return cancel all margin order response as JsonArray
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * @return cancel all margin order response as {@link OpenMarginOrders} object
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol)));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * @return cancel all margin order response as String
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
     * @return cancel all margin order response as JsonObject
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol,extraParams));
    }

    /** Request to get cancel all a margin orders
     * @param #symbol: symbol used in the orders es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade
     * @return cancel all margin order response as ArrayList<{@link OpenMarginOrders}>
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol,extraParams)));
    }

    /** Method to assemble an {@link OpenMarginOrders} object
     * @param #jsonArray: obtained from Binance's request
     * @return a {@link OpenMarginOrders} list as ArrayList
     * **/
    private OpenMarginOrders assembleOpenMarginOrdersObject(JSONArray jsonArray){
        ArrayList<DetailMarginOrder> detailMarginOrders = new ArrayList<>();
        ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject openMarginOrder = jsonArray.getJSONObject(j);
            if(!openMarginOrder.getString("contingencyType").isEmpty())
                composedMarginOrderDetails.add(assembleComposedMarginOrderDetails(openMarginOrder));
            else
                detailMarginOrders.add(DetailMarginOrder.assembleDetailMarginOrderObject(openMarginOrder));
        }
        return new OpenMarginOrders(detailMarginOrders,composedMarginOrderDetails);
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory() throws Exception {
        return new JSONObject(getCrossMarginTransferHistory());
    }

    /** Request to get cross margin transfer history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory() throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory()));
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(extraParams));
    }

    /** Request to get cross margin transfer history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(extraParams)));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as String
     * **/
    public String getCrossMarginTransferHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset)));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as String
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
     * @return cross margin transfer history response as JsonObject
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset,extraParams));
    }

    /** Request to get cross margin transfer history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset,extraParams)));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * @return query loan record as String
     * **/
    public String getQueryLoanRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * @return query loan record as JsonObject
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * @return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset)));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * @return query loan record as String
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
     * @return query loan record as JsonObject
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset, extraParams));
    }

    /** Request to get query loan record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data
     * @return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset, extraParams)));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * @return query repay record as String
     * **/
    public String getQueryRepayRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * @return query repay record as JsonObject
     * **/
    public JSONObject getJSONRepayRecord(String asset) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * @return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset)));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * @return query repay record as String
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
     * @return query repay record as JsonObject
     * **/
    public JSONObject getJSONRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset, extraParams));
    }

    /** Request to get query repay record
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data
     * @return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset, extraParams)));
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as String
     * **/
    public String getInterestHistory() throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory() throws Exception {
        return new JSONObject(getInterestHistory());
    }

    /** Request to get interest history
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory() throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory()));
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as String
     * **/
    public String getInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(extraParams));
    }

    /** Request to get interest history
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(extraParams)));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as String
     * **/
    public String getInterestHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as JsonObject
     * **/
    public JSONObject getJSONInterestHistory(String asset) throws Exception {
        return new JSONObject(getInterestHistory(asset));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset)));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as String
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
     * @return interest history response as String
     * **/
    public JSONObject getJSONInterestHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(asset,extraParams));
    }

    /** Request to get interest history
     * @param #asset: asset used to request es BTC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset,extraParams)));
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as String
     * **/
    public String getMarginForceLiquidation() throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as JsonObject
     * **/
    public JSONObject getJSONMarginForceLiquidation() throws Exception {
        return new JSONObject(getMarginForceLiquidation());
    }

    /** Request to get margin force liquidation
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation() throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation()));
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as String
     * **/
    public String getMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as JsonObject
     * **/
    public JSONObject getJSONMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getMarginForceLiquidation(extraParams));
    }

    /** Request to get margin force liquidation
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data
     * @return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation(extraParams)));
    }

    /** Request to get cross margin account details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as String
     * **/
    public String getCrossMarginAccountDetails() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get cross margin account details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as JSONObject
     * **/
    public JSONObject getJSONCrossMarginAccountDetails() throws Exception {
        return new JSONObject(getCrossMarginAccountDetails());
    }

    /** Request to get cross margin account details
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as {@link CrossMarginAccountDetails} object
     * **/
    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails() throws Exception {
        return assembleCrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails()));
    }

    /** Request to get cross margin account details
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as String
     * **/
    public String getCrossMarginAccountDetails(long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get cross margin account details
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as JSONObject
     * **/
    public JSONObject getJSONCrossMarginAccountDetails(long recvWindow) throws Exception {
        return new JSONObject(getCrossMarginAccountDetails(recvWindow));
    }

    /** Request to get cross margin account details
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data
     * @return cross margin account details response as {@link CrossMarginAccountDetails} object
     * **/
    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails(long recvWindow) throws Exception {
        return assembleCrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails(recvWindow)));
    }

    /** Method to assemble a CrossMarginAccountDetails object
     * @param #jsonObject: obtained from Binance's request
     * @return a CrossMarginAccountDetails object
     * **/
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

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as String
     * **/
    public String getMarginOrderStatus(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as JSONObject
     * **/
    public JSONObject getJSONMarginOrderStatus(String symbol) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol));
    }

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as {@link MarginOrderStatus} object
     * **/
    public MarginOrderStatus getObjectMarginOrderStatus(String symbol) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol)));
    }

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as String
     * **/
    public String getMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as JSONObject
     * **/
    public JSONObject getJSONMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol,extraParams));
    }

    /** Request to get margin status order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data
     * @return margin status order response as {@link MarginOrderStatus} object
     * **/
    public MarginOrderStatus getObjectMarginOrderStatus(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol,extraParams)));
    }

    /** Request to get all margin open orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as String
     * **/
    public String getAllMarginOpenOrders() throws Exception {
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get all margin open orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOpenOrders() throws Exception {
        return new JSONArray(getAllMarginOpenOrders());
    }

    /** Request to get all margin open orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList() throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders()));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as String
     * **/
    public String getAllMarginOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated=true";
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOpenOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol)));
    }

    /** Request to get all margin open orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as String
     * **/
    public String getAllMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all margin open orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(extraParams));
    }

    /** Request to get all margin open orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(extraParams)));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as String
     * **/
    public String getAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated=true"+"&recvWindow";
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol, recvWindow));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol, long recvWindow) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol, recvWindow)));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as String
     * **/
    public String getAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol)));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as String
     * **/
    public String getAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as JSONArray
     * **/
    public JSONArray getJSONAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol, extraParams));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data
     * @return all margin orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol, extraParams)));
    }

    /** Method to assemble a MarginOrderStatus list
     * @param #jsonArray: obtained from Binance's request
     * @return a list as ArrayList<MarginOrderStatus>
     * **/
    private ArrayList<MarginOrderStatus> assembleMarginOrdersList(JSONArray jsonArray) {
        ArrayList<MarginOrderStatus> marginOrderStatus = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            marginOrderStatus.add(assembleMarginOrderStatus(jsonArray.getJSONObject(j)));
        return marginOrderStatus;
    }

    /** Method to assemble a MarginOrderStatus object
     * @param #jsonObject: obtained from Binance's request
     * @return a MarginOrderStatus object
     * **/
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

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as String
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&quantity="+quantity+"&price="+price+
                "&stopPrice="+stopPrice;
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as JSONObject
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice) throws Exception {
        return new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                                  double stopPrice) throws Exception {
        return assembleOCOMarginOrder(new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice)));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as String
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        HashMap<String, Object> extraParams ) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&quantity="+quantity+"&price="+price+
                "&stopPrice="+stopPrice;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as JSONObject
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                                HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, extraParams));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                      double stopPrice, HashMap<String, Object> extraParams) throws Exception {
        return assembleOCOMarginOrder(new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice,extraParams)));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as String
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        double stopLimitPrice, String stopLimitTimeInForce) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&quantity="+quantity+"&price="+price+
                "&stopPrice="+stopPrice+"&stopLimitPrice="+stopLimitPrice+"&stopLimitTimeInForce="+stopLimitTimeInForce;
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as JSONObject
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                                double stopLimitPrice, String stopLimitTimeInForce) throws Exception {
        return new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, stopLimitPrice, stopLimitTimeInForce));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                                  double stopPrice, double stopLimitPrice,
                                                                  String stopLimitTimeInForce) throws Exception {
        return assembleOCOMarginOrder(new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice,
                stopLimitPrice, stopLimitTimeInForce)));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as String
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        double stopLimitPrice, String stopLimitTimeInForce,
                                        HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&quantity="+quantity+"&price="+price+
                "&stopPrice="+stopPrice+"&stopLimitPrice="+stopLimitPrice+"&stopLimitTimeInForce="+stopLimitTimeInForce;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT,params,POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as JSONObject
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                                double stopLimitPrice, String stopLimitTimeInForce,
                                                HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, stopLimitPrice,
                stopLimitTimeInForce, extraParams));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),see official Binance's documentation to implement in the right combination
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                      double stopPrice, double stopLimitPrice,
                                                      String stopLimitTimeInForce, HashMap<String, Object> extraParams) throws Exception {
        return assembleOCOMarginOrder(new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice,
                stopLimitPrice, stopLimitTimeInForce, extraParams)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as String
     * **/
    public String cancelOCOMarginOrder(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as JSONObject
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, long orderListId) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, orderListId));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, long orderListId) throws Exception {
        return assembleComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, orderListId)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as String
     * **/
    public String cancelOCOMarginOrder(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as JSONObject
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, String listClientOrderId ) throws Exception {
        return assembleComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as String
     * **/
    public String cancelOCOMarginOrder(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as JSONObject
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, orderListId, extraParams));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, long orderListId,
                                                                 HashMap<String, Object> extraParams) throws Exception {
        return assembleComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, orderListId, extraParams)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as String
     * **/
    public String cancelOCOMarginOrder(String symbol, String listClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&listClientOrderId="+listClientOrderId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as JSONObject
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, String listClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId, extraParams));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, String listClientOrderId,
                                                                 HashMap<String, Object> extraParams) throws Exception {
        return assembleComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
       return new JSONObject(getOCOMarginOrderStatus(symbol, orderListId));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, orderListId)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId +"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId)));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(long orderListId) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(long orderListId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(orderListId));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(long orderListId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(orderListId)));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(origClientOrderId));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(origClientOrderId)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, orderListId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, long orderListId,
                                                                  HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, orderListId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String symbol, String origClientOrderId,HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId +"&isIsolated="+true;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, String origClientOrderId,
                                                                  HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(orderListId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(orderListId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as String
     * **/
    public String getOCOMarginOrderStatus(String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as JSONObject
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(origClientOrderId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String origClientOrderId,
                                                                  HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(origClientOrderId, extraParams)));
    }

    /** Method to assemble a MarginOrderStatusDetails object
     * @param #marginOrder: obtained from Binance's request
     * @return a MarginOrderStatusDetails object
     * **/
    private MarginOrderStatusDetails assembleMarginOrderStatusDetails(JSONObject marginOrder){
        return new MarginOrderStatusDetails(marginOrder.getLong("orderListId"),
                marginOrder.getString("contingencyType"),
                marginOrder.getString("listStatusType"),
                marginOrder.getString("listOrderStatus"),
                marginOrder.getString("listClientOrderId"),
                marginOrder.getLong("transactionTime"),
                marginOrder.getString("symbol"),
                marginOrder,
                marginOrder.getBoolean("isIsolated")
        );
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId, long fromId,
                                                                         String keyTime, long valueTime) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                        long valueTime) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&isIsolated="+true+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                               long valueTime) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         long fromId, String keyTime, long valueTime) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(long orderListId) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                        long valueTime) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId+"&"+keyTime+"="+valueTime+"&fromId="+fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                               long valueTime) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId, long fromId, String keyTime,
                                                                         long valueTime) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime,
                valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                        long valueTime) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                               long valueTime) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId, long fromId, String keyTime,
                                                                         long valueTime) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId,
                keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,origClientOrderId.startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId, extraParams));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId,
                                                                         HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId, extraParams)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&isIsolated="+true;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId, extraParams));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                extraParams)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                        HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&orderListId="+orderListId+"&isIsolated="+true+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                               HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId, long fromId,
                                                                         String keyTime, long valueTime,
                                                                         HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                        long valueTime, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&origClientOrderId="+origClientOrderId+"&isIsolated="+true+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                               long valueTime, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         long fromId, String keyTime, long valueTime,
                                                                         HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId,
                                                                         HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                        long valueTime, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&orderListId="+orderListId+"&"+keyTime+"="+valueTime+"&fromId="+fromId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                               long valueTime, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId, long fromId, String keyTime,
                                                                         long valueTime, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime,
                valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as String
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                        long valueTime, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&origClientOrderId="+origClientOrderId+
                "&"+keyTime+"="+valueTime+"&fromId="+fromId;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                               long valueTime, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId, long fromId, String keyTime,
                                                                         long valueTime, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId,
                keyTime, valueTime)));
    }

    /** Method to assemble a MarginOrderStatusDetails list
     * @param #jsonArray: obtained from Binance's request
     * @return a list as ArrayList<MarginOrderStatusDetails>
     * **/
    private ArrayList<MarginOrderStatusDetails> assembleMarginOrderStatusDetailsList(JSONArray jsonArray) {
        ArrayList<MarginOrderStatusDetails> marginOrderStatusDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            marginOrderStatusDetails.add(assembleMarginOrderStatusDetails(jsonArray.getJSONObject(j)));
        return marginOrderStatusDetails;
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as String
     * **/
    public String getAllOCOMarginOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(String symbol) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(symbol));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(String symbol) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(symbol)));
    }

    /** Request to get all open OCO margin orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as String
     * **/
    public String getAllOCOMarginOpenOrders() throws Exception {
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders() throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders());
    }

    /** Request to get all open OCO margin orders
     * any params required
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList() throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders()));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as String
     * **/
    public String getAllOCOMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&isIsolated="+true;
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(symbol, recvWindow));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(String symbol, long recvWindow) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(symbol, recvWindow)));
    }

    /** Request to get all open OCO margin orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as String
     * **/
    public String getAllOCOMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        String params = requestManager.assembleExtraParams(getParamTimestamp(),extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as JSONArray
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(extraParams));
    }

    /** Request to get all open OCO margin orders
     * @param #extraParams: extra params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(extraParams)));
    }

    public String getMarginTradeList(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONMarginTradeList(String symbol) throws Exception {
        return new JSONArray(getMarginTradeList(symbol));
    }

    public ArrayList<MarginAccountTrade> getMarginTradesList(String symbol) throws Exception {
        return assembleMarginTradesList(new JSONArray(getMarginTradeList(symbol)));
    }

    public String getMarginTradeList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT,params,GET_METHOD);
    }

    public JSONArray getJSONMarginTradeList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(getMarginTradeList(symbol, extraParams));
    }

    public ArrayList<MarginAccountTrade> getMarginTradesList(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginTradesList(new JSONArray(getMarginTradeList(symbol, extraParams)));
    }

    private ArrayList<MarginAccountTrade> assembleMarginTradesList(JSONArray jsonArray) {
        ArrayList<MarginAccountTrade> marginAccountTrades = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject marginTrade = jsonArray.getJSONObject(j);
            marginAccountTrades.add(new MarginAccountTrade(marginTrade.getDouble("commission"),
                    marginTrade.getDouble("commissionAsset"),
                    marginTrade.getLong("id"),
                    marginTrade.getBoolean("isBestMatch"),
                    marginTrade.getBoolean("isBuyer"),
                    marginTrade.getBoolean("isMaker"),
                    marginTrade.getLong("orderId"),
                    marginTrade.getDouble("price"),
                    marginTrade.getDouble("qty"),
                    marginTrade.getString("symbol"),
                    marginTrade.getBoolean("isIsolated"),
                    marginTrade.getLong("time")
            ));
        }
        return marginAccountTrades;
    }

    public String getMaxBorrow(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMaxBorrow(String asset) throws Exception {
        return new JSONObject(getMaxBorrow(asset));
    }

    public MarginMaxBorrow getObjectMarginBorrow(String asset) throws Exception {
        return assembleMarginMaxBorrowObject(new JSONObject(getMaxBorrow(asset)));
    }

    public String getMaxBorrow(String asset, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMaxBorrow(String asset, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getMaxBorrow(asset, extraParams));
    }

    public MarginMaxBorrow getObjectMarginBorrow(String asset, HashMap<String, Object> extraParams) throws Exception {
        return assembleMarginMaxBorrowObject(new JSONObject(getMaxBorrow(asset, extraParams)));
    }

    private MarginMaxBorrow assembleMarginMaxBorrowObject(JSONObject maxBorrow){
        return new MarginMaxBorrow(maxBorrow.getDouble("amount"),
                maxBorrow.getDouble("borrowLimit")
        );
    }

    public String getMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&symbol="+symbol+"&transFrom="+transFrom+"&transTo="+transTo
                +"&amount="+amount;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject getJSONMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        return new JSONObject(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount));
    }

    public long getMarginIsolatedTransferId(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        return getTransactionId(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount));
    }

    public String getMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                             double amount, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&symbol="+symbol+"&transFrom="+transFrom+"&transTo="+transTo
                +"&amount="+amount+"&recvWindow="+recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject getJSONMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                                    double amount, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount, recvWindow));
    }

    public long getMarginIsolatedTransferId(String asset, String symbol, String transFrom, String transTo,
                                            double amount, long recvWindow) throws Exception {
        return getTransactionId(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount, recvWindow));
    }

    public String getMarginIsolatedTransferHistory(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedTransferHistory(String symbol) throws Exception {
        return new JSONObject(getMarginIsolatedTransferHistory(symbol));
    }

    public MarginIsolatedTransferHistory getObjectMarginIsolatedTransferHistory(String symbol) throws Exception {
        return new MarginIsolatedTransferHistory(new JSONObject(getMarginIsolatedTransferHistory(symbol)));
    }

    public String getMarginIsolatedTransferHistory(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params, extraParams);
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedTransferHistory(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(getMarginIsolatedTransferHistory(symbol, extraParams));
    }

    public MarginIsolatedTransferHistory getObjectMarginIsolatedTransferHistory(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new MarginIsolatedTransferHistory(new JSONObject(getMarginIsolatedTransferHistory(symbol, extraParams)));
    }

    public String getMarginIsolatedAccountInfo() throws Exception {
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedAccountInfo() throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo());
    }

    public ComposedIMarginAccountInfo getObjectMarginIsolatedAccount() throws Exception {
        return assembleComposedIMarginAccountInfoObject(new JSONObject(getMarginIsolatedAccountInfo()));
    }

    public String getMarginIsolatedAccountInfo(long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&recvWindow="+recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedAccountInfo(long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(recvWindow));
    }

    public ComposedIMarginAccountInfo getObjectMarginIsolatedAccount(long recvWindow) throws Exception {
        return assembleComposedIMarginAccountInfoObject(new JSONObject(getMarginIsolatedAccountInfo(recvWindow)));
    }

    private ComposedIMarginAccountInfo assembleComposedIMarginAccountInfoObject(JSONObject jsonObject){
        return new ComposedIMarginAccountInfo(jsonObject.getDouble("totalAssetOfBtc"),
                jsonObject.getDouble("totalLiabilityOfBtc"),
                jsonObject.getDouble("totalNetAssetOfBtc"),
                jsonObject.getJSONArray("assets")
        );
    }

    public String getMarginIsolatedAccountInfo(ArrayList<String> symbols) throws Exception {
        String params = getParamTimestamp()+"&symbols="+requestManager.assembleSymbolsParams(symbols);
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedAccountInfo(ArrayList<String> symbols) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(symbols));
    }

    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(ArrayList<String> symbols) throws Exception {
        return assembleIsolatedMarginAccountInfoList(new JSONObject(getMarginIsolatedAccountInfo(symbols))
                .getJSONArray("assets"));
    }

    public String getMarginIsolatedAccountInfo(String[] symbols) throws Exception {
       return getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)));
    }

    public JSONObject getJSONMarginIsolatedAccountInfo(String[] symbols) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols))));
    }

    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(String[] symbols) throws Exception {
        jsonArray = new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols))))
                .getJSONArray("assets");
        return assembleIsolatedMarginAccountInfoList(jsonArray);
    }

    public String getMarginIsolatedAccountInfo(ArrayList<String> symbols, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&symbols="+requestManager.assembleSymbolsParams(symbols)+
                "&recvWindow="+recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONMarginIsolatedAccountInfo(ArrayList<String> symbols, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(symbols, recvWindow));
    }

    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(ArrayList<String> symbols, long recvWindow) throws Exception {
        jsonArray = new JSONObject(getMarginIsolatedAccountInfo(symbols, recvWindow)).getJSONArray("assets");
        return assembleIsolatedMarginAccountInfoList(jsonArray);
    }

    public String getMarginIsolatedAccountInfo(String[] symbols, long recvWindow) throws Exception {
        return getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)), recvWindow);
    }

    public JSONObject getJSONMarginIsolatedAccountInfo(String[] symbols, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)), recvWindow));
    }

    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(String[] symbols, long recvWindow) throws Exception {
        jsonArray = new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)), recvWindow))
                .getJSONArray("assets");
        return assembleIsolatedMarginAccountInfoList(jsonArray);
    }

}

