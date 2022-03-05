package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.MarginInterestHistory;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.MarginLoan;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.MarginRepay;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.MarginTransferHistory;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginAsset;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPair;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.MarginPriceIndex;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.CancelMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.ComposedMarginOrderDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Cancel.OpenMarginOrders;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.ACKMarginOrder;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Response.FullMarginOrder;
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

    public String executeCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+asset+"&type="+type;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type));
    }

    public double getExecuteCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset,amount,type));
    }

    public String executeCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+asset+"&type="+type+"&recvWindow="+recvWindow;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    public double getExecuteCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    public String applyMarginAccountBorrow(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount));
    }

    public double getApplyMarginAccountBorrow(String asset, double amount) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount));
    }

    public String applyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    public double getApplyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    public String repeayMarginAccountLoan(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject repeayJSONMarginAccountLoan(String asset, double amount) throws Exception {
        return new JSONObject(repeayMarginAccountLoan(asset, amount));
    }

    public double getRepeayMarginAccountLoan(String asset, double amount) throws Exception {
        return getTransactionId(repeayMarginAccountLoan(asset, amount));
    }

    public String repeayMarginAccountLoan(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject repeayJSONMarginAccountLoan(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(repeayMarginAccountLoan(asset, amount, extraParams));
    }

    public double getRepeayMarginAccountLoan(String asset, double amount, HashMap<String, Object> extraParams) throws Exception {
        return getTransactionId(repeayMarginAccountLoan(asset, amount, extraParams));
    }

    private double getTransactionId(String stringSource){
        jsonObject = new JSONObject(stringSource);
        return jsonObject.getDouble("tranId");
    }

    public String queryMarginAsset(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject queryJSONMarginAsset(String asset) throws Exception {
        return new JSONObject(queryMarginAsset(asset));
    }

    public MarginAsset queryObjectMarginAsset(String asset) throws Exception {
        return assembleMarginAssetObject(new JSONObject(queryMarginAsset(asset)));
    }

    public String queryAllMarginAssets() throws Exception {
        return sendSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT,"",GET_METHOD);
    }

    public JSONArray queryJSONAllMarginAssets() throws Exception {
        return new JSONArray(queryAllMarginAssets());
    }

    public ArrayList<MarginAsset> queryAllMarginAssetsList() throws Exception {
        ArrayList<MarginAsset> marginAssets = new ArrayList<>();
        jsonArray = new JSONArray(queryAllMarginAssets());
        for (int j=0; j < jsonArray.length(); j++)
            marginAssets.add(assembleMarginAssetObject(jsonArray.getJSONObject(j)));
        return marginAssets;
    }

    private MarginAsset assembleMarginAssetObject(JSONObject jsonObject){
        return new MarginAsset(jsonObject.getString("assetFullName"),
                jsonObject.getString("assetName"),
                jsonObject.getBoolean("isBorrowable"),
                jsonObject.getBoolean("isMortgageable"),
                jsonObject.getDouble("userMinBorrow"),
                jsonObject.getDouble("userMinRepay")
        );
    }

    public String queryCrossMarginPair(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject queryJSONCrossMarginPair(String asset) throws Exception {
        return new JSONObject(queryCrossMarginPair(asset));
    }

    public MarginPair queryObjectCrossMarginPair(String asset) throws Exception {
        return assembleMarginPairObject(new JSONObject(queryCrossMarginPair(asset)));
    }

    public String queryAllMarginPairs() throws Exception {
        return sendSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT,"",GET_METHOD);
    }

    public JSONArray queryJSONAllMarginPairs() throws Exception {
        return new JSONArray(queryAllMarginPairs());
    }

    public ArrayList<MarginPair> queryAllMarginPairsList() throws Exception {
        ArrayList<MarginPair> marginAssets = new ArrayList<>();
        jsonArray = new JSONArray(queryAllMarginPairs());
        for (int j=0; j < jsonArray.length(); j++)
            marginAssets.add(assembleMarginPairObject(jsonArray.getJSONObject(j)));
        return marginAssets;
    }

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

    public String getMarginPriceIndex(String symbol) throws Exception {
        return sendSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT,"?symbol="+symbol,GET_METHOD);
    }

    public JSONObject getJSONMarginPriceIndex(String symbol) throws Exception {
        return new JSONObject(getMarginPriceIndex(symbol));
    }

    public MarginPriceIndex getObjectMarginPriceIndex(String symbol) throws Exception {
        jsonObject = new JSONObject(getMarginPriceIndex(symbol));
        return new MarginPriceIndex(jsonObject.getLong("calcTime"),
                jsonObject.getDouble("price"),
                jsonObject.getString("symbol")
        );
    }

    public String sendNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, extraParams));
    }

    public ACKMarginOrder sendObjectNewMarginOrder(String symbol, String side, String type,
                                                   HashMap<String, Object> extraParams) throws Exception {
        jsonObject = new JSONObject(sendJSONNewMarginOrder(symbol, side, type, extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return getFullOrderResponse(jsonObject);
        else
            return getACKResponse(jsonObject);
    }

    public String sendNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams,
                                     String newOrderRespType) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol+"&side="+side+"&type="+type+"&newOrderRespType="+newOrderRespType;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, HashMap<String, Object> extraParams,
                                             String newOrderRespType) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, extraParams, newOrderRespType));
    }

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

    public String cancelMarginOrder(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONObject cancelJSONMarginOrder(String symbol) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol));
    }

    public CancelMarginOrder cancelObjectMarginOrder(String symbol) throws Exception {
        return CancelMarginOrder.assembleCancelMarginOrderObject(new JSONObject(cancelMarginOrder(symbol)));
    }

    public String cancelMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONObject cancelJSONMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol, extraParams));
    }

    public CancelMarginOrder cancelObjectMarginOrder(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return CancelMarginOrder.assembleCancelMarginOrderObject(new JSONObject(cancelMarginOrder(symbol,extraParams)));
    }

    public String cancelAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONArray cancelJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol));
    }

    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol)));
    }

    public String cancelAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&symbol="+symbol;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT,params,DELETE_METHOD);
    }

    public JSONArray cancelJSONAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol,extraParams));
    }

    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol, HashMap<String, Object> extraParams) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol,extraParams)));
    }

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

    public String getCrossMarginTransferHistory() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONCrossMarginTransferHistory() throws Exception {
        return new JSONObject(getCrossMarginTransferHistory());
    }

    public MarginTransferHistory getObjectCrossMarginTransferHistory() throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory()));
    }

    public String getCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    public JSONObject getJSONCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(extraParams));
    }

    public MarginTransferHistory getObjectCrossMarginTransferHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(extraParams)));
    }

    public String getCrossMarginTransferHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONCrossMarginTransferHistory(String asset) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset));
    }

    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset)));
    }

    public String getCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset,extraParams));
    }

    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset,extraParams)));
    }

    public String getQueryLoanRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONQueryLoanRecord(String asset) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset));
    }

    public MarginLoan getObjectQueryLoanRecord(String asset) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset)));
    }

    public String getQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset, extraParams));
    }

    public MarginLoan getObjectQueryLoanRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset, extraParams)));
    }

    public String getQueryRepayRecord(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONRepayRecord(String asset) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset));
    }

    public MarginRepay getObjectRepayRecord(String asset) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset)));
    }

    public String getQueryRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset, extraParams));
    }

    public MarginRepay getObjectRepayRecord(String asset, HashMap<String,Object> extraParams) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset, extraParams)));
    }

    public String getInterestHistory() throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONInterestHistory() throws Exception {
        return new JSONObject(getInterestHistory());
    }

    public MarginInterestHistory getObjectInterestHistory() throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory()));
    }

    public String getInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    public JSONObject getJSONInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(extraParams));
    }

    public MarginInterestHistory getObjectInterestHistory(HashMap<String,Object> extraParams) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(extraParams)));
    }

    public String getInterestHistory(String asset) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONInterestHistory(String asset) throws Exception {
        return new JSONObject(getInterestHistory(asset));
    }

    public MarginInterestHistory getObjectInterestHistory(String asset) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset)));
    }

    public String getInterestHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset;
        params = requestManager.assembleExtraParams(params,extraParams);
        return sendSignedRequest(MARGIN_INTERST_HISTORY_ENDPOINT,params,GET_METHOD);
    }

    public JSONObject getJSONInterestHistory(String asset,HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getInterestHistory(asset,extraParams));
    }

    public MarginInterestHistory getObjectInterestHistory(String asset,HashMap<String,Object> extraParams)
            throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset,extraParams)));
    }

    public String getMarginForceLiquidation() throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    public JSONObject getJSONMarginForceLiquidation() throws Exception {
        return new JSONObject(getMarginForceLiquidation());
    }

    public MarginTransferHistory getObjectMarginForceLiquidation() throws Exception {
        return new MarginTransferHistory(new JSONObject(getMarginForceLiquidation()));
    }

    public String getMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT,
                requestManager.assembleExtraParams(getParamTimestamp(),extraParams),GET_METHOD);
    }

    public JSONObject getJSONMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(getMarginForceLiquidation(extraParams));
    }

    public MarginTransferHistory getObjectMarginForceLiquidation(HashMap<String,Object> extraParams) throws Exception {
        return new MarginTransferHistory(new JSONObject(getMarginForceLiquidation(extraParams)));
    }
}

