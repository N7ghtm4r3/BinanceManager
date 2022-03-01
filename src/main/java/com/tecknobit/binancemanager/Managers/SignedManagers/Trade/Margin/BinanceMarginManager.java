package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginAsset;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginPair;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginPriceIndex;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.GET_METHOD;
import static com.tecknobit.binancemanager.Helpers.Request.RequestManager.POST_METHOD;

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
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_TRANSFER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type));
    }

    public double getExecuteCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset,amount,type));
    }

    public String executeCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+asset+"&type="+type+"&recvWindow="+recvWindow;
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_TRANSFER_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    public double getExecuteCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    public String applyMarginAccountBorrow(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_ACCOUNT_BORROW_ENDPOINT,params,POST_METHOD);
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
        return sendSignedRequest(MARGIN_ACCOUNT_BORROW_ENDPOINT,params,POST_METHOD);
    }

    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    public double getApplyMarginAccountBorrow(String asset, double amount, HashMap<String,Object> extraParams) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    public String repeayMarginAccountLoan(String asset, double amount) throws Exception {
        String params = getParamTimestamp()+"&asset="+asset+"&amount="+amount;
        return sendSignedRequest(MARGIN_ACCOUNT_REPAY_ENDPOINT,params,POST_METHOD);
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
        return sendSignedRequest(MARGIN_ACCOUNT_REPAY_ENDPOINT,params,POST_METHOD);
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

}
