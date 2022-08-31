package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin;

import com.tecknobit.binancemanager.Exceptions.SystemException;
import com.tecknobit.binancemanager.Managers.SignedManagers.BinanceSignedManager;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.CrossMarginAccountDetails;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginAccountTrade;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account.MarginMaxBorrow;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.ComposedIMarginAccountInfo;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountLimit;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountStatus;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties.BNBBurn;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties.IsolatedMarginFee;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties.IsolatedMarginSymbol;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Properties.IsolatedMarginTierData;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginList.*;
import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.MarginProperties.*;
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

import static com.tecknobit.apimanager.Manager.APIRequest.*;
import static com.tecknobit.binancemanager.Constants.EndpointsList.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.TradeConstants.*;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Isolated.Account.IsolatedMarginAccountInfo.createIsolatedMarginAccountInfoList;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details.OCOMarginOrder.assembleOCOMarginOrder;

/**
 *  The {@code BinanceMarginManager} class is useful to manage all Binance Margin Endpoints
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class BinanceMarginManager extends BinanceSignedManager {

    /**
     * {@code MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} is constant for transfer from main account to cross margin account
     * **/
    public static final int MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT = 1;

    /**
     * {@code CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT} is constant for transfer from cross margin account to main account
     * **/
    public static final int CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT = 2;

    /** Constructor to init BinanceMarginManager
     * @param baseEndpoint base endpoint to work on
     * @param apiKey your api key
     * @param secretKey your secret key
     * **/
    public BinanceMarginManager(String baseEndpoint, String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /** Constructor to init BinanceMarginManager
     * @param apiKey your api key
     * @param secretKey your secret key
     * automatically set a working endpoint
     * **/
    public BinanceMarginManager(String apiKey, String secretKey) throws SystemException, IOException {
        super(null, apiKey, secretKey);
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer as {@link String}
     * **/
    public String executeCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&amount=" + asset + "&type=" + type;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer as {@link JSONObject}
     * **/
    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type));
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer's tranId as long
     * **/
    public long getExecuteCrossMarginAccountTransfer(String asset, double amount, int type) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset,amount,type));
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer as {@link String}
     * **/
    public String executeCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&amount=" + amount+ "&type=" + type +
                "&recvWindow=" + recvWindow;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer as {@link JSONObject}
     * **/
    public JSONObject executeJSONCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return new JSONObject(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    /** Request to execute a transfer
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount to be transferred
     * @param type: {@link #MAIN_ACCOUNT_TO_CROSS_MARGIN_ACCOUNT} or {@link #CROSS_MARGIN_ACCOUNT_MAIN_ACCOUNT}
     * it defines type of transfer
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#cross-margin-account-transfer-margin</a>
     * @return result transfer's tranId as long
     * **/
    public long getExecuteCrossMarginAccountTransfer(String asset, double amount, int type, long recvWindow) throws Exception {
        return getTransactionId(executeCrossMarginAccountTransfer(asset, amount, type, recvWindow));
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result of margin account borrow request as {@link String}
     * **/
    public String applyMarginAccountBorrow(String asset, double amount) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&amount=" +amount;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result of account borrow request as {@link JSONObject}
     * **/
    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount));
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result account borrow's tranId as long
     * **/
    public long getApplyMarginAccountBorrow(String asset, double amount) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount));
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result of margin account borrow request as {@link String}
     * **/
    public String applyMarginAccountBorrow(String asset, double amount, Params extraParams) throws Exception {
        String params = getParamTimestamp()+ "&asset=" + asset + "&amount=" +amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result of margin account borrow request as {@link JSONObject}
     * **/
    public JSONObject applyJSONMarginAccountBorrow(String asset, double amount, Params extraParams) throws Exception {
        return new JSONObject(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    /** Request to execute a margin account borrow
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount borrowed
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-borrow-margin</a>
     * @return result account borrow's tranId as long
     * **/
    public long getApplyMarginAccountBorrow(String asset, double amount, Params extraParams) throws Exception {
        return getTransactionId(applyMarginAccountBorrow(asset, amount, extraParams));
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result of repay margin account request as {@link String}
     * **/
    public String repayMarginAccount(String asset, double amount) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset +" &amount="+ amount;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result of repay margin account request as {@link JSONObject}
     * **/
    public JSONObject repayJSONMarginAccount(String asset, double amount) throws Exception {
        return new JSONObject(repayMarginAccount(asset, amount));
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result margin repay's tranId as long
     * **/
    public long getRepayMarginAccount(String asset, double amount) throws Exception {
        return getTransactionId(repayMarginAccount(asset, amount));
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result of repay margin account request as {@link String}
     * **/
    public String repayMarginAccount(String asset, double amount, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&amount=" + amount;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT, params, POST_METHOD);
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result of repay margin account request as {@link JSONObject}
     * **/
    public JSONObject repayJSONMarginAccount(String asset, double amount, Params extraParams) throws Exception {
        return new JSONObject(repayMarginAccount(asset, amount, extraParams));
    }

    /** Request to execute an repay margin account request
     * @param asset: asset used in the request es. BTC
     * @param amount: the amount repaied
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-repay-margin</a>
     * @return result margin repay's tranId as long
     * **/
    public long getRepayMarginAccount(String asset, double amount, Params extraParams) throws Exception {
        return getTransactionId(repayMarginAccount(asset, amount, extraParams));
    }

    /** Method to get tranId value
     * @param stringSource: obtained from Binance's request
     * @return tranId value as long
     * **/
    private long getTransactionId(String stringSource){
        JSONObject transaction = new JSONObject(stringSource);
        return transaction.getLong("tranId");
    }

    /** Request to get margin asset details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data</a>
     * @return detail's asset as {@link String}
     * **/
    public String queryMarginAsset(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(QUERY_MARGIN_ASSET_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin asset details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data</a>
     * @return detail's asset as {@link JSONObject}
     * **/
    public JSONObject queryJSONMarginAsset(String asset) throws Exception {
        return new JSONObject(queryMarginAsset(asset));
    }

    /** Request to get margin asset details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-asset-market_data</a>
     * @return detail's asset as {@link MarginAsset} object
     * **/
    public MarginAsset queryObjectMarginAsset(String asset) throws Exception {
        return new MarginAsset(new JSONObject(queryMarginAsset(asset)));
    }

    /** Request to get all margin asset details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
     * @return all asset details as {@link String}
     * **/
    public String queryAllMarginAssets() throws Exception {
        return sendSignedRequest(QUERY_ALL_MARGIN_ASSETS_ENDPOINT, "", GET_METHOD);
    }

    /** Request to get all margin asset details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
     * @return all asset details as {@link JSONArray}
     * **/
    public JSONArray queryJSONAllMarginAssets() throws Exception {
        return new JSONArray(queryAllMarginAssets());
    }

    /** Request to get margin asset details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-margin-assets-market_data</a>
     * @return detail's asset as ArrayList<{@link MarginAsset}>
     * **/
    public ArrayList<MarginAsset> queryAllMarginAssetsList() throws Exception {
        ArrayList<MarginAsset> marginAssets = new ArrayList<>();
        JSONArray assets = new JSONArray(queryAllMarginAssets());
        for (int j = 0; j < assets.length(); j++)
            marginAssets.add(new MarginAsset(assets.getJSONObject(j)));
        return marginAssets;
    }

    /** Request to get cross margin pair details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data</a>
     * @return cross margin pair as {@link String}
     * **/
    public String queryCrossMarginPair(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(QUERY_CROSS_MARGIN_PAIR_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get cross margin pair details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data</a>
     * @return cross margin pair as {@link JSONObject}
     * **/
    public JSONObject queryJSONCrossMarginPair(String asset) throws Exception {
        return new JSONObject(queryCrossMarginPair(asset));
    }

    /** Request to get cross margin pair details
     * @param asset: asset used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-pair-market_data</a>
     * @return cross margin pair as {@link MarginPair} object
     * **/
    public MarginPair queryObjectCrossMarginPair(String asset) throws Exception {
        return new MarginPair(new JSONObject(queryCrossMarginPair(asset)));
    }

    /** Request to get all cross margin pair details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data</a>
     * @return all cross margin pair as {@link String}
     * **/
    public String queryAllMarginPairs() throws Exception {
        return sendSignedRequest(QUERY_ALL_CROSS_MARGIN_PAIRS_ENDPOINT, "", GET_METHOD);
    }

    /** Request to get all cross margin pair details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data</a>
     * @return all cross margin pair as {@link JSONArray}
     * **/
    public JSONArray queryJSONAllMarginPairs() throws Exception {
        return new JSONArray(queryAllMarginPairs());
    }

    /** Request to get all cross margin pair details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-cross-margin-pairs-market_data</a>
     * @return all cross margin pair as ArrayList<{@link MarginPair}>
     * **/
    public ArrayList<MarginPair> queryAllMarginPairsList() throws Exception {
        ArrayList<MarginPair> marginAssets = new ArrayList<>();
        JSONArray pairs = new JSONArray(queryAllMarginPairs());
        for (int j = 0; j < pairs.length(); j++)
            marginAssets.add(new MarginPair(pairs.getJSONObject(j)));
        return marginAssets;
    }

    /** Request to get priceIndex of a symbol
     * @param symbol: symbol used to get price index
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data</a>
     * @return priceIndex of a symbol as {@link String}
     * **/
    public String getMarginPriceIndex(String symbol) throws Exception {
        return sendSignedRequest(MARGIN_PRICE_INDEX_ENDPOINT, "?symbol=" + symbol, GET_METHOD);
    }

    /** Request to get priceIndex of a symbol
     * @param symbol: symbol used to get price index
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data</a>
     * @return priceIndex of a symbol as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginPriceIndex(String symbol) throws Exception {
        return new JSONObject(getMarginPriceIndex(symbol));
    }

    /** Request to get priceIndex of a symbol
     * @param symbol: symbol used to get price index
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data"
     * >https://binance-docs.github.io/apidocs/spot/en/#query-margin-priceindex-market_data</a>
     * @return priceIndex of a symbol as {@link MarginPriceIndex} object
     * **/
    public MarginPriceIndex getObjectMarginPriceIndex(String symbol) throws Exception {
        return new MarginPriceIndex(new JSONObject(getMarginPriceIndex(symbol)));
    }

    /** Request to send a new margin order
     * @param symbol: symbol used to the order es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendNewMarginOrder(String symbol, String side, String type, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side +"&type=" + type;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send a new margin order
     * @param symbol: symbol used to the order es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, Params extraParams) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, extraParams));
    }

    /** Request to send a spot order
     * @param symbol: symbol used in the request es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order
     * @implNote if type LIMIT or MARKET will be must cast in {@link FullMarginOrder} object
     * @implNote with other types will be an {@link ACKMarginOrder} object
     * **/
    public <T extends ACKMarginOrder> T sendObjectNewMarginOrder(String symbol, String side, String type,
                                                   Params extraParams) throws Exception {
        JSONObject order = new JSONObject(sendJSONNewMarginOrder(symbol, side, type, extraParams));
        if(type.equals(LIMIT) || type.equals(MARKET))
            return (T) getFullOrderResponse(order);
        else
            return (T) getACKResponse(order);
    }

    /** Request to send a new margin order
     * @param symbol: symbol used to the order es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order as {@link String}
     * **/
    public String sendNewMarginOrder(String symbol, String side, String type, String newOrderRespType,
                                     Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&type=" + type + "&newOrderRespType="
                + newOrderRespType;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send a new margin order
     * @param symbol: symbol used to the order es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order as {@link JSONObject}
     * **/
    public JSONObject sendJSONNewMarginOrder(String symbol, String side, String type, Params extraParams,
                                             String newOrderRespType) throws Exception {
        return new JSONObject(sendNewMarginOrder(symbol, side, type, newOrderRespType, extraParams));
    }

    /** Request to send a new margin order
     * @param symbol: symbol used to the order es. BTCBUSD
     * @param side: BUY or SELL order
     * @param type: LIMIT, MARKET,STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT, LIMIT_MAKER
     * @param newOrderRespType: format response of the order request (ACK, RESULT,FULL)
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are timeInForce,quantity,quoteOrderQty,price,newClientOrderId,stopPrice,icebergQty,
     * newOrderRespType,sideEffectType,recvWindow), see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade</a>
     * @return result of the order
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_RESULT object will be {@link ResultMarginOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_FULL object will be {@link FullMarginOrder}
     * @implNote if newOrderRespType = NEW_ORDER_RESP_TYPE_ACK object will be {@link ACKMarginOrder}
     * **/
    public <T extends ACKMarginOrder> T sendObjectNewMarginOrder(String symbol, String side, String type,
                                                                 Params extraParams,
                                                                 String newOrderRespType) throws Exception {
        JSONObject order = new JSONObject(sendJSONNewMarginOrder(symbol, side, type, extraParams, newOrderRespType));
        switch (newOrderRespType){
            case NEW_ORDER_RESP_TYPE_RESULT:
                return (T) new ResultMarginOrder(order.getString("symbol"),
                        order.getLong("orderId"),
                        order.getString("clientOrderId"),
                        order.getLong("transactTime"),
                        order.getBoolean("isIsolated"),
                        order.getDouble("price"),
                        order.getDouble("origQty"),
                        order.getDouble("executedQty"),
                        order.getDouble("cumulativeQuoteQty"),
                        order.getString("status"),
                        order.getString("timeInForce"),
                        order.getString("type"),
                        order.getString("side")
                );
            case NEW_ORDER_RESP_TYPE_FULL:
                return (T) getFullOrderResponse(order);
            default:
                return (T) getACKResponse(order);
        }
    }

    /** Method to assemble an ACKMarginOrder object
     * @param jsonResponse: obtained from Binance's request
     * @return an {@link ACKMarginOrder} object with jsonResponse data
     * **/
    private ACKMarginOrder getACKResponse(JSONObject jsonResponse){
        return new ACKMarginOrder(jsonResponse.getString("symbol"),
                jsonResponse.getLong("orderId"),
                jsonResponse.getString("clientOrderId"),
                jsonResponse.getLong("transactTime"),
                jsonResponse.getBoolean("isIsolated")
        );
    }

    /** Method to assemble an FullMarginOrder object
     * @param jsonResponse: obtained from Binance's request
     * @return a {@link FullMarginOrder} object with response data
     * **/
    private FullMarginOrder getFullOrderResponse(JSONObject jsonResponse){
        return new FullMarginOrder(jsonResponse.getString("symbol"),
                jsonResponse.getLong("orderId"),
                jsonResponse.getString("clientOrderId"),
                jsonResponse.getLong("transactTime"),
                jsonResponse.getBoolean("isIsolated"),
                jsonResponse.getDouble("price"),
                jsonResponse.getDouble("origQty"),
                jsonResponse.getDouble("executedQty"),
                jsonResponse.getDouble("cummulativeQuoteQty"),
                jsonResponse.getString("status"),
                jsonResponse.getString("timeInForce"),
                jsonResponse.getString("type"),
                jsonResponse.getString("side"),
                jsonResponse.getJSONArray("fills")

        );
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link String}
     * **/
    public String cancelMarginOrder(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol));
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link DetailMarginOrder} object
     * **/
    public DetailMarginOrder cancelObjectMarginOrder(String symbol) throws Exception {
        return DetailMarginOrder.assembleDetailMarginOrderObject(new JSONObject(cancelMarginOrder(symbol)));
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link String}
     * **/
    public String cancelMarginOrder(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONMarginOrder(String symbol, Params extraParams) throws Exception {
        return new JSONObject(cancelMarginOrder(symbol, extraParams));
    }

    /** Request to get cancel a margin order
     * @param symbol: symbol used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
     * @return cancel a margin order response as {@link DetailMarginOrder} object
     * **/
    public DetailMarginOrder cancelObjectMarginOrder(String symbol, Params extraParams) throws Exception {
        return DetailMarginOrder.assembleDetailMarginOrderObject(new JSONObject(cancelMarginOrder(symbol,extraParams)));
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as {@link String}
     * **/
    public String cancelAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see the official documentation at:<a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as {@link JSONArray}
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol));
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as {@link OpenMarginOrders} object
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol)));
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as {@link String}
     * **/
    public String cancelAllMarginOrders(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as {@link JSONObject}
     * **/
    public JSONArray cancelJSONAllMarginOrders(String symbol, Params extraParams) throws Exception {
        return new JSONArray(cancelAllMarginOrders(symbol, extraParams));
    }

    /** Request to get cancel all a margin orders
     * @param symbol: symbol used in the orders es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-all-open-orders-on-a-symbol-trade</a>
     * @return cancel all margin order response as ArrayList<{@link OpenMarginOrders}>
     * **/
    public OpenMarginOrders cancelObjectAllMarginOrders(String symbol, Params extraParams) throws Exception {
        return assembleOpenMarginOrdersObject(new JSONArray(cancelAllMarginOrders(symbol, extraParams)));
    }

    /** Method to assemble an {@link OpenMarginOrders} object
     * @param jsonArray: obtained from Binance's request
     * @return a {@link OpenMarginOrders} list as ArrayList
     * **/
    private OpenMarginOrders assembleOpenMarginOrdersObject(JSONArray jsonArray){
        ArrayList<DetailMarginOrder> detailMarginOrders = new ArrayList<>();
        ArrayList<ComposedMarginOrderDetails> composedMarginOrderDetails = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject openMarginOrder = jsonArray.getJSONObject(j);
            if(!openMarginOrder.getString("contingencyType").isEmpty())
                composedMarginOrderDetails.add(new ComposedMarginOrderDetails(openMarginOrder));
            else
                detailMarginOrders.add(DetailMarginOrder.assembleDetailMarginOrderObject(openMarginOrder));
        }
        return new OpenMarginOrders(detailMarginOrders,composedMarginOrderDetails);
    }

    /** Request to get cross margin transfer history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link String}
     * **/
    public String getCrossMarginTransferHistory() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT,getParamTimestamp(),GET_METHOD);
    }

    /** Request to get cross margin transfer history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginTransferHistory() throws Exception {
        return new JSONObject(getCrossMarginTransferHistory());
    }

    /** Request to get cross margin transfer history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory() throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory()));
    }

    /** Request to get cross margin transfer history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link String}
     * **/
    public String getCrossMarginTransferHistory(Params extraParams) throws Exception {
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, apiRequest.encodeAdditionalParams(getParamTimestamp(),
                extraParams), GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(Params extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(extraParams));
    }

    /** Request to get cross margin transfer history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(Params extraParams) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(extraParams)));
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link String}
     * **/
    public String getCrossMarginTransferHistory(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset));
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at:
     * <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset) throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset)));
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link String}
     * **/
    public String getCrossMarginTransferHistory(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params,extraParams);
        return sendSignedRequest(CROSS_MARGIN_TRANSFERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginTransferHistory(String asset, Params extraParams) throws Exception {
        return new JSONObject(getCrossMarginTransferHistory(asset, extraParams));
    }

    /** Request to get cross margin transfer history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,type,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
     * @return cross margin transfer history response as {@link MarginTransferHistory} object
     * **/
    public MarginTransferHistory getObjectCrossMarginTransferHistory(String asset, Params extraParams)
            throws Exception {
        return new MarginTransferHistory(new JSONObject(getCrossMarginTransferHistory(asset, extraParams)));
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link String}
     * **/
    public String getQueryLoanRecord(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link JSONObject}
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset));
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset)));
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link String}
     * **/
    public String getQueryLoanRecord(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_LOAN_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link JSONObject}
     * **/
    public JSONObject getJSONQueryLoanRecord(String asset, Params extraParams) throws Exception {
        return new JSONObject(getQueryLoanRecord(asset, extraParams));
    }

    /** Request to get query loan record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
     * @return query loan record as {@link MarginLoan} object
     * **/
    public MarginLoan getObjectQueryLoanRecord(String asset, Params extraParams) throws Exception {
        return new MarginLoan(new JSONObject(getQueryLoanRecord(asset, extraParams)));
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link String}
     * **/
    public String getQueryRepayRecord(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link JSONObject}
     * **/
    public JSONObject getJSONRepayRecord(String asset) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset));
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset)));
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link String}
     * **/
    public String getQueryRepayRecord(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_REPAY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link JSONObject}
     * **/
    public JSONObject getJSONRepayRecord(String asset, Params extraParams) throws Exception {
        return new JSONObject(getQueryRepayRecord(asset, extraParams));
    }

    /** Request to get query repay record
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
     * @return query repay record as {@link MarginRepay} object
     * **/
    public MarginRepay getObjectRepayRecord(String asset, Params extraParams) throws Exception {
        return new MarginRepay(new JSONObject(getQueryRepayRecord(asset, extraParams)));
    }

    /** Request to get interest history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link String}
     * **/
    public String getInterestHistory() throws Exception {
        return sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get interest history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link JSONObject}
     * **/
    public JSONObject getJSONInterestHistory() throws Exception {
        return new JSONObject(getInterestHistory());
    }

    /** Request to get interest history <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory() throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory()));
    }

    /** Request to get interest history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link String}
     * **/
    public String getInterestHistory(Params extraParams) throws Exception {
        return sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, apiRequest.encodeAdditionalParams(getParamTimestamp(),
                extraParams), GET_METHOD);
    }

    /** Request to get interest history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link JSONObject}
     * **/
    public JSONObject getJSONInterestHistory(Params extraParams) throws Exception {
        return new JSONObject(getInterestHistory(extraParams));
    }

    /** Request to get interest history
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(Params extraParams) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(extraParams)));
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link String}
     * **/
    public String getInterestHistory(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link JSONObject}
     * **/
    public JSONObject getJSONInterestHistory(String asset) throws Exception {
        return new JSONObject(getInterestHistory(asset));
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset)));
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link String}
     * **/
    public String getInterestHistory(String asset,Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_INTEREST_HISTORY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link String}
     * **/
    public JSONObject getJSONInterestHistory(String asset, Params extraParams) throws Exception {
        return new JSONObject(getInterestHistory(asset, extraParams));
    }

    /** Request to get interest history
     * @param asset: asset used to request es BTC
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are asset,isolatedSymbol,txId,startTime,endTime,current,size,archived,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data</a>
     * @return interest history response as {@link MarginInterestHistory} object
     * **/
    public MarginInterestHistory getObjectInterestHistory(String asset, Params extraParams) throws Exception {
        return new MarginInterestHistory(new JSONObject(getInterestHistory(asset, extraParams)));
    }

    /** Request to get margin force liquidation <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link String}
     * **/
    public String getMarginForceLiquidation() throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get margin force liquidation <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginForceLiquidation() throws Exception {
        return new JSONObject(getMarginForceLiquidation());
    }

    /** Request to get margin force liquidation <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation() throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation()));
    }

    /** Request to get margin force liquidation
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link String}
     * **/
    public String getMarginForceLiquidation(Params extraParams) throws Exception {
        return sendSignedRequest(MARGIN_FORCE_LIQUIDATION_ENDPOINT, apiRequest.encodeAdditionalParams(getParamTimestamp(),
                extraParams), GET_METHOD);
    }

    /** Request to get margin force liquidation
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginForceLiquidation(Params extraParams) throws Exception {
        return new JSONObject(getMarginForceLiquidation(extraParams));
    }

    /** Request to get margin force liquidation
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isolatedSymbol,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data</a>
     * @return margin force liquidation response as {@link MarginForceLiquidation} object
     * **/
    public MarginForceLiquidation getObjectMarginForceLiquidation(Params extraParams) throws Exception {
        return new MarginForceLiquidation(new JSONObject(getMarginForceLiquidation(extraParams)));
    }

    /** Request to get cross margin account details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link String}
     * **/
    public String getCrossMarginAccountDetails() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get cross margin account details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginAccountDetails() throws Exception {
        return new JSONObject(getCrossMarginAccountDetails());
    }

    /** Request to get cross margin account details <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link CrossMarginAccountDetails} object
     * **/
    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails() throws Exception {
        return new CrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails()));
    }

    /** Request to get cross margin account details
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link String}
     * **/
    public String getCrossMarginAccountDetails(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(CROSS_MARGIN_ACCOUNT_DETAILS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get cross margin account details
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link JSONObject}
     * **/
    public JSONObject getJSONCrossMarginAccountDetails(long recvWindow) throws Exception {
        return new JSONObject(getCrossMarginAccountDetails(recvWindow));
    }

    /** Request to get cross margin account details
     * @param recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
     * @return cross margin account details response as {@link CrossMarginAccountDetails} object
     * **/
    public CrossMarginAccountDetails getObjectCrossMarginAccountDetails(long recvWindow) throws Exception {
        return new CrossMarginAccountDetails(new JSONObject(getCrossMarginAccountDetails(recvWindow)));
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link String}
     * **/
    public String getMarginOrderStatus(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginOrderStatus(String symbol) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol));
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link MarginOrderStatus} object
     * **/
    public MarginOrderStatus getObjectMarginOrderStatus(String symbol) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol)));
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link String}
     * **/
    public String getMarginOrderStatus(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_ORDER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginOrderStatus(String symbol, Params extraParams) throws Exception {
        return new JSONObject(getMarginOrderStatus(symbol, extraParams));
    }

    /** Request to get margin status order
     * @param symbol: used in the order es. BTCBUSD
     * @param extraParams: extra params of the request
     * @implSpec (keys accepted are isIsolated,orderId,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-order-user_data</a>
     * @return margin status order response as {@link MarginOrderStatus} object
     * **/
    public MarginOrderStatus getObjectMarginOrderStatus(String symbol, Params extraParams) throws Exception {
        return assembleMarginOrderStatus(new JSONObject(getMarginOrderStatus(symbol, extraParams)));
    }

    /** Request to get all margin open orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link String}
     * **/
    public String getAllMarginOpenOrders() throws Exception {
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get all margin open orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOpenOrders() throws Exception {
        return new JSONArray(getAllMarginOpenOrders());
    }

    /** Request to get all margin open orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList() throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders()));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link String}
     * **/
    public String getAllMarginOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&isIsolated=true";
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOpenOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol)));
    }

    /** Request to get all margin open orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link String}
     * **/
    public String getAllMarginOpenOrders(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all margin open orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOpenOrders(Params extraParams) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(extraParams));
    }

    /** Request to get all margin open orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,symbol,recvWindow)
     * @apiNote see the official documentation at:
     * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(Params extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(extraParams)));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link String}
     * **/
    public String getAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&isIsolated=true" + "&recvWindow=" + recvWindow;
        return sendSignedRequest(MARGIN_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        return new JSONArray(getAllMarginOpenOrders(symbol, recvWindow));
    }

    /** Request to get all margin open orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-orders-user_data</a>
     * @return all margin open orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOpenOrdersList(String symbol, long recvWindow) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOpenOrders(symbol, recvWindow)));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as {@link String}
     * **/
    public String getAllMarginOrders(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOrders(String symbol) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol)));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as {@link String}
     * **/
    public String getAllMarginOrders(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as {@link JSONArray}
     * **/
    public JSONArray getJSONAllMarginOrders(String symbol, Params extraParams) throws Exception {
        return new JSONArray(getAllMarginOrders(symbol, extraParams));
    }

    /** Request to get all margin orders on a symbol
     * @param #symbol: used in the order es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-orders-user_data</a>
     * @return all margin orders as ArrayList<{@link MarginOrderStatus}>
     * **/
    public ArrayList<MarginOrderStatus> getAllMarginOrdersList(String symbol, Params extraParams) throws Exception {
        return assembleMarginOrdersList(new JSONArray(getAllMarginOrders(symbol, extraParams)));
    }

    /** Method to assemble a MarginOrderStatus list
     * @param #jsonOrder: obtained from Binance's request
     * @return a list as ArrayList<MarginOrderStatus>
     * **/
    private ArrayList<MarginOrderStatus> assembleMarginOrdersList(JSONArray jsonOrder) {
        ArrayList<MarginOrderStatus> marginOrderStatus = new ArrayList<>();
        for (int j = 0; j < jsonOrder.length(); j++)
            marginOrderStatus.add(assembleMarginOrderStatus(jsonOrder.getJSONObject(j)));
        return marginOrderStatus;
    }

    /** Method to assemble a MarginOrderStatus object
     * @param #jsonOrder: obtained from Binance's request
     * @return a MarginOrderStatus object
     * **/
    private MarginOrderStatus assembleMarginOrderStatus(JSONObject jsonOrder){
        return new MarginOrderStatus(jsonOrder.getString("symbol"),
                jsonOrder.getLong("orderId"),
                jsonOrder.getString("clientOrderId"),
                jsonOrder.getLong("updateTime"),
                jsonOrder.getBoolean("isIsolated"),
                jsonOrder.getDouble("price"),
                jsonOrder.getDouble("origQty"),
                jsonOrder.getDouble("executedQty"),
                jsonOrder.getDouble("cummulativeQuoteQty"),
                jsonOrder.getString("status"),
                jsonOrder.getString("timeInForce"),
                jsonOrder.getString("type"),
                jsonOrder.getString("side"),
                jsonOrder.getDouble("icebergQty"),
                jsonOrder.getBoolean("isWorking"),
                jsonOrder.getDouble("stopPrice"),
                jsonOrder.getLong("time")
        );
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link String}
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity
                + "&price=" + price + "&stopPrice=" + stopPrice;
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link JSONObject}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
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
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link String}
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        Params extraParams ) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                                Params extraParams) throws Exception {
        return new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice, extraParams));
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopLimitPrice,stopIcebergQty,stopLimitTimeInForce,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                      double stopPrice, Params extraParams) throws Exception {
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link String}
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        double stopLimitPrice, String stopLimitTimeInForce) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol+ "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice + "&stopLimitPrice=" + stopLimitPrice +
                "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link JSONObject}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
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
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link String}
     * **/
    public String sendNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                        double stopLimitPrice, String stopLimitTimeInForce,
                                        Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&side=" + side + "&quantity=" + quantity +
                "&price=" + price + "&stopPrice=" + stopPrice + "&stopLimitPrice=" + stopLimitPrice +
                "&stopLimitTimeInForce=" + stopLimitTimeInForce;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to send new OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #side: BUY or SELL
     * @param #quantity: used in the order es. 0.00542
     * @param #price: used in the order, price of symbol es. BTC = {39016.21} BUSD
     * @param #stopPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD
     * @param #stopLimitPrice: used to SELL if price reaches the target price es. BTC = {40000} BUSD (limit)
     * @param #stopLimitTimeInForce: GTC, FOK or IOC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),
     * see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject sendJSONNewOCOMarginOrder(String symbol, String side, double quantity, double price, double stopPrice,
                                                double stopLimitPrice, String stopLimitTimeInForce,
                                                Params extraParams) throws Exception {
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
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,limitClientOrderId,limitIcebergQty,stopClientOrderId,
     * stopIcebergQty,newOrderRespType,sideEffectType,recvWindow),see official Binance's documentation to implement in the right combination
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
     * @return new OCO margin order response as {@link OCOMarginOrder} object
     * **/
    public OCOMarginOrder sendObjectNewOCOMarginOrder(String symbol, String side, double quantity, double price,
                                                      double stopPrice, double stopLimitPrice,
                                                      String stopLimitTimeInForce, Params extraParams) throws Exception {
        return assembleOCOMarginOrder(new JSONObject(sendNewOCOMarginOrder(symbol, side, quantity, price, stopPrice,
                stopLimitPrice, stopLimitTimeInForce, extraParams)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link String}
     * **/
    public String cancelOCOMarginOrder(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, long orderListId) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, orderListId));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, long orderListId) throws Exception {
        return new ComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, orderListId)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link String}
     * **/
    public String cancelOCOMarginOrder(String symbol, String listClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, String listClientOrderId) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, String listClientOrderId ) throws Exception {
        return new ComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link String}
     * **/
    public String cancelOCOMarginOrder(String symbol, long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, long orderListId, Params extraParams) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, orderListId, extraParams));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,listClientOrderId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, long orderListId,
                                                                 Params extraParams) throws Exception {
        return new ComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, orderListId, extraParams)));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link String}
     * **/
    public String cancelOCOMarginOrder(String symbol, String listClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&listClientOrderId=" + listClientOrderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, DELETE_METHOD);
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link JSONObject}
     * **/
    public JSONObject cancelJSONOCOMarginOrder(String symbol, String listClientOrderId, Params extraParams) throws Exception {
        return new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId, extraParams));
    }

    /** Request to cancel OCO margin order
     * @param #symbol: used in the order es. BTCBUSD
     * @param #listClientOrderId: identifier od client order list es. C3wyj4WVEktd7u9aVBRXcN
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,newClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-oco-trade</a>
     * @return cancel OCO margin order response as {@link ComposedMarginOrderDetails} object
     * **/
    public ComposedMarginOrderDetails cancelObjectOCOMarginOrder(String symbol, String listClientOrderId,
                                                                 Params extraParams) throws Exception {
        return new ComposedMarginOrderDetails(new JSONObject(cancelOCOMarginOrder(symbol, listClientOrderId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
       return new JSONObject(getOCOMarginOrderStatus(symbol, orderListId));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, long orderListId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, orderListId)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp()+ "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId +
                "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId)));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(long orderListId) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(long orderListId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(orderListId));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(long orderListId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(orderListId)));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(origClientOrderId));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String origClientOrderId) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(origClientOrderId)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String symbol, long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp()+ "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, long orderListId, Params extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, orderListId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, long orderListId,
                                                                  Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, orderListId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String symbol, String origClientOrderId,Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId +
                "&isIsolated=" + true;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String symbol, String origClientOrderId,
                                                                  Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(symbol, origClientOrderId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(long orderListId, Params extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(orderListId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(long orderListId, Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(orderListId, extraParams)));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link String}
     * **/
    public String getOCOMarginOrderStatus(String origClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link JSONObject}
     * **/
    public JSONObject getJSONOCOMarginOrderStatus(String origClientOrderId, Params extraParams) throws Exception {
        return new JSONObject(getOCOMarginOrderStatus(origClientOrderId, extraParams));
    }

    /** Request to get OCO margin order status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-oco-user_data</a>
     * @return OCO margin order status response as {@link MarginOrderStatusDetails} object
     * **/
    public MarginOrderStatusDetails getObjectOCOMarginOrderStatus(String origClientOrderId,
                                                                  Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetails(new JSONObject(getOCOMarginOrderStatus(origClientOrderId, extraParams)));
    }

    /** Method to assemble a MarginOrderStatusDetails object
     * @param #jsonMarginOrder: obtained from Binance's request
     * @return a MarginOrderStatusDetails object
     * **/
    private MarginOrderStatusDetails assembleMarginOrderStatusDetails(JSONObject jsonMarginOrder){
        return new MarginOrderStatusDetails(jsonMarginOrder.getLong("orderListId"),
                jsonMarginOrder.getString("contingencyType"),
                jsonMarginOrder.getString("listStatusType"),
                jsonMarginOrder.getString("listOrderStatus"),
                jsonMarginOrder.getString("listClientOrderId"),
                jsonMarginOrder.getLong("transactionTime"),
                jsonMarginOrder.getString("symbol"),
                jsonMarginOrder,
                jsonMarginOrder.getBoolean("isIsolated")
        );
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId
                + "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true
                + "&"+ keyTime + "=" + valueTime + "&fromId=" + fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                        long valueTime) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId
                + "&isIsolated=" + true + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         long fromId, String keyTime, long valueTime) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(long orderListId) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(long orderListId, long fromId, String keyTime, long valueTime) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime
                 + "&fromId=" + fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT,params,GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, long fromId, String keyTime, long valueTime) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                        long valueTime) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId + "&" + keyTime + "=" + valueTime
                 + "&fromId=" + fromId;
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
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
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,origClientOrderId.startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId, extraParams));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId,
                                                                         Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId, extraParams)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId
                 + "&isIsolated=" + true;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId, extraParams));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                extraParams)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                        Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&orderListId=" + orderListId + "&isIsolated=" + true
                 + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, long orderListId, long fromId, String keyTime, long valueTime,
                                               Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,origClientOrderId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, long orderListId, long fromId,
                                                                         String keyTime, long valueTime,
                                                                         Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, orderListId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                        long valueTime, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&origClientOrderId=" + origClientOrderId
                + "&isIsolated=" + true + "&" + keyTime + "=" + valueTime + "&fromId=" + fromId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String symbol, String origClientOrderId, long fromId, String keyTime,
                                               long valueTime, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #symbol: used in the order es. BTCBUSD
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,orderListId,fromId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String symbol, String origClientOrderId,
                                                                         long fromId, String keyTime, long valueTime,
                                                                         Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(symbol, origClientOrderId,
                fromId, keyTime, valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(long orderListId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId, Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,fromId,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId,
                                                                         Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId)));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                        long valueTime, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&orderListId=" + orderListId + "&" + keyTime + "=" + valueTime +
                "&fromId=" + fromId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(long orderListId, long fromId, String keyTime,
                                               long valueTime, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #orderListId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,origClientOrderId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(long orderListId, long fromId, String keyTime,
                                                                         long valueTime, Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(orderListId, fromId, keyTime,
                valueTime)));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link String}
     * **/
    public String getAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                        long valueTime, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&origClientOrderId=" + origClientOrderId+ "&" + keyTime + "=" + valueTime
                 + "&fromId=" + fromId;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOrders(String origClientOrderId, long fromId, String keyTime,
                                               long valueTime, Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId, keyTime, valueTime));
    }

    /** Request to get all OCO margin orders status
     * @param #origClientOrderId: identifier od order list es. 1
     * @param #fromId: order from start request es. 124564
     * @param #keyTime: startTime or endTime key filter for the research
     * @param #valueTime: value of keyTime es. 152221
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,orderListId,startTime,endTime,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-all-oco-user_data</a>
     * @return all OCO margin orders status response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOrdersList(String origClientOrderId, long fromId, String keyTime,
                                                                         long valueTime, Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOrders(origClientOrderId, fromId,
                keyTime, valueTime)));
    }

    /** Method to assemble a MarginOrderStatusDetails list
     * @param #jsonOrderStatus: obtained from Binance's request
     * @return a list as ArrayList<MarginOrderStatusDetails>
     * **/
    private ArrayList<MarginOrderStatusDetails> assembleMarginOrderStatusDetailsList(JSONArray jsonOrderStatus) {
        ArrayList<MarginOrderStatusDetails> marginOrderStatusDetails = new ArrayList<>();
        for (int j = 0; j < jsonOrderStatus.length(); j++)
            marginOrderStatusDetails.add(assembleMarginOrderStatusDetails(jsonOrderStatus.getJSONObject(j)));
        return marginOrderStatusDetails;
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link String}
     * **/
    public String getAllOCOMarginOpenOrders(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(String symbol) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(symbol));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(String symbol) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(symbol)));
    }

    /** Request to get all open OCO margin orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link String}
     * **/
    public String getAllOCOMarginOpenOrders() throws Exception {
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get all open OCO margin orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders() throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders());
    }

    /** Request to get all open OCO margin orders <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList() throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders()));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link String}
     * **/
    public String getAllOCOMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&isIsolated=" + true;
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(String symbol, long recvWindow) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(symbol, recvWindow));
    }

    /** Request to get all open OCO margin orders
     * @param #symbol: used in the order es. BTCBUSD
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(String symbol, long recvWindow) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(symbol, recvWindow)));
    }

    /** Request to get all open OCO margin orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link String}
     * **/
    public String getAllOCOMarginOpenOrders(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(MARGIN_OCO_ALL_OPEN_ORDERS_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get all open OCO margin orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as {@link JSONArray}
     * **/
    public JSONArray getJSONAllOCOMarginOpenOrders(Params extraParams) throws Exception {
        return new JSONArray(getAllOCOMarginOpenOrders(extraParams));
    }

    /** Request to get all open OCO margin orders
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are symbol,isIsolated,recvWindow)
     * @apiNote see the official documentation at:<a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data">https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-open-oco-user_data</a>
     * @return all open OCO margin orders response as ArrayList<{@link MarginOrderStatusDetails}>
     * **/
    public ArrayList<MarginOrderStatusDetails> getAllOCOMarginOpenOrdersList(Params extraParams) throws Exception {
        return assembleMarginOrderStatusDetailsList(new JSONArray(getAllOCOMarginOpenOrders(extraParams)));
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     * @return margin trade list as {@link String}
     * **/
    public String getMarginTradeList(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     * @return margin trade list as {@link JSONArray}
     * **/
    public JSONArray getJSONMarginTradeList(String symbol) throws Exception {
        return new JSONArray(getMarginTradeList(symbol));
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     * @return margin trade list as ArrayList<{@link MarginAccountTrade}>
     * **/
    public ArrayList<MarginAccountTrade> getMarginTradesList(String symbol) throws Exception {
        return assembleMarginTradesList(new JSONArray(getMarginTradeList(symbol)));
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     * @return margin trade list as {@link String}
     * **/
    public String getMarginTradeList(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_TRADES_LIST_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin trade list
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,startTime,endTime,fromId,limit,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     * @return margin trade list as {@link JSONArray}
     * **/
    public JSONArray getJSONMarginTradeList(String symbol, Params extraParams) throws Exception {
        return new JSONArray(getMarginTradeList(symbol, extraParams));
    }

    /**
     * Request to get margin trade list
     *
     * @param #symbol:     used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @return margin trade list as ArrayList<{@link MarginAccountTrade}>
     * @implSpec (keys accepted are isIsolated, startTime, endTime, fromId, limit, recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#query-margin-account-39-s-trade-list-user_data</a>
     **/
    public ArrayList<MarginAccountTrade> getMarginTradesList(String symbol, Params extraParams) throws Exception {
        return assembleMarginTradesList(new JSONArray(getMarginTradeList(symbol, extraParams)));
    }

    /**
     * Method to assemble a {@link MarginAccountTrade} list
     *
     * @param #jsonMarginTrades: obtained from Binance's request
     * @return a list as {@link ArrayList} of {@link MarginAccountTrade}
     **/
    private ArrayList<MarginAccountTrade> assembleMarginTradesList(JSONArray jsonMarginTrades) {
        ArrayList<MarginAccountTrade> marginAccountTrades = new ArrayList<>();
        for (int j = 0; j < jsonMarginTrades.length(); j++)
            marginAccountTrades.add(new MarginAccountTrade(jsonMarginTrades.getJSONObject(j)));
        return marginAccountTrades;
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link String}
     * **/
    public String getMaxBorrow(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link JSONObject}
     * **/
    public JSONObject getJSONMaxBorrow(String asset) throws Exception {
        return new JSONObject(getMaxBorrow(asset));
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link MarginMaxBorrow}
     * **/
    public MarginMaxBorrow getObjectMarginBorrow(String asset) throws Exception {
        return new MarginMaxBorrow(new JSONObject(getMaxBorrow(asset)));
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link String}
     * **/
    public String getMaxBorrow(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(GET_MAX_MARGIN_BORROW_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link JSONObject}
     * **/
    public JSONObject getJSONMaxBorrow(String asset, Params extraParams) throws Exception {
        return new JSONObject(getMaxBorrow(asset, extraParams));
    }

    /** Request to get max borrow
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-borrow-user_data</a>
     * @return max borrow as {@link MarginMaxBorrow}
     * **/
    public MarginMaxBorrow getObjectMarginBorrow(String asset, Params extraParams) throws Exception {
        return new MarginMaxBorrow(new JSONObject(getMaxBorrow(asset, extraParams)));
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as {@link String}
     * **/
    public String getMaxTransferOutAmount(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as {@link JSONObject}
     * **/
    public JSONObject getJSONMaxTransferOutAmount(String asset) throws Exception {
        return new JSONObject(getMaxTransferOutAmount(asset));
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as double
     * **/
    public double getMaxTransferOutAmountValue(String asset) throws Exception {
        return getMaxTransferAmountValue(getMaxTransferOutAmount(asset));
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as {@link String}
     * **/
    public String getMaxTransferOutAmount(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(GET_MAX_MARGIN_TRANSFER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as {@link JSONObject}
     * **/
    public JSONObject getJSONMaxTransferOutAmount(String asset, Params extraParams) throws Exception {
        return new JSONObject(getMaxTransferOutAmount(asset, extraParams));
    }

    /** Request to get max transfer out amount
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are isIsolated,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-max-transfer-out-amount-user_data</a>
     * @return max transfer out amount as double
     * **/
    public double getMaxTransferOutAmountValue(String asset, Params extraParams) throws Exception {
        return getMaxTransferAmountValue(getMaxTransferOutAmount(asset, extraParams));
    }

    /** Method to get amount value
     * @param #stringSource: obtained from Binance's request
     * @return amount value as double
     * **/
    private double getMaxTransferAmountValue(String stringSource){
        JSONObject maxTransfer = new JSONObject(stringSource);
        return maxTransfer.getDouble("amount");
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transfer as {@link String}
     * **/
    public String getMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&symbol=" + symbol + "&transFrom=" + transFrom
                 + "&transTo=" + transTo + "&amount=" + amount;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transfer as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        return new JSONObject(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount));
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transferId as long
     * **/
    public long getMarginIsolatedTransferId(String asset, String symbol, String transFrom, String transTo,
                                            double amount) throws Exception {
        return getTransactionId(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount));
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transfer as {@link String}
     * **/
    public String getMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                             double amount, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset + "&symbol=" + symbol + "&transFrom=" + transFrom
                 + "&transTo=" + transTo + "&amount=" + amount + "&recvWindow=" + recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transfer as {@link String}
     * **/
    public JSONObject getJSONMarginIsolatedTransfer(String asset, String symbol, String transFrom, String transTo,
                                                    double amount, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount, recvWindow));
    }

    /** Request to get margin isolated transfer
     * @param #asset: used in the request es. BTC
     * @param #symbol: used in the request es. BTCBUSD
     * @param #transFrom: SPOT or ISOLATED_MARGIN
     * @param #transTo: SPOT or ISOLATED_MARGIN
     * @param #amount: used in the request to transfer es. 1
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin">
     *     https://binance-docs.github.io/apidocs/spot/en/#isolated-margin-account-transfer-margin</a>
     * @return margin isolated transfer as {@link String}
     * **/
    public long getMarginIsolatedTransferId(String asset, String symbol, String transFrom, String transTo,
                                            double amount, long recvWindow) throws Exception {
        return getTransactionId(getMarginIsolatedTransfer(asset, symbol, transFrom, transTo, amount, recvWindow));
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link String}
     * **/
    public String getMarginIsolatedTransferHistory(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedTransferHistory(String symbol) throws Exception {
        return new JSONObject(getMarginIsolatedTransferHistory(symbol));
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link MarginIsolatedTransferHistory} object
     * **/
    public MarginIsolatedTransferHistory getObjectMarginIsolatedTransferHistory(String symbol) throws Exception {
        return new MarginIsolatedTransferHistory(new JSONObject(getMarginIsolatedTransferHistory(symbol)));
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,transFrom,transTo,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link String}
     * **/
    public String getMarginIsolatedTransferHistory(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(ISOLATED_MARGIN_TRANSFER_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,transFrom,transTo,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedTransferHistory(String symbol, Params extraParams) throws Exception {
        return new JSONObject(getMarginIsolatedTransferHistory(symbol, extraParams));
    }

    /** Request to get margin isolated transfer history
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are asset,transFrom,transTo,startTime,endTime,current,size,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
     * @return margin isolated transfer history as {@link MarginIsolatedTransferHistory} object
     * **/
    public MarginIsolatedTransferHistory getObjectMarginIsolatedTransferHistory(String symbol, Params extraParams) throws Exception {
        return new MarginIsolatedTransferHistory(new JSONObject(getMarginIsolatedTransferHistory(symbol, extraParams)));
    }

    /** Request to get margin isolated account info <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo() throws Exception {
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get margin isolated account info <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo() throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo());
    }

    /** Request to get margin isolated account info <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link ComposedIMarginAccountInfo} object
     * **/
    public ComposedIMarginAccountInfo getObjectMarginIsolatedAccount() throws Exception {
        return new ComposedIMarginAccountInfo(new JSONObject(getMarginIsolatedAccountInfo()));
    }

    /** Request to get margin isolated account info
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin isolated account info
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo(long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(recvWindow));
    }

    /** Request to get margin isolated account info
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link ComposedIMarginAccountInfo} object
     * **/
    public ComposedIMarginAccountInfo getObjectMarginIsolatedAccount(long recvWindow) throws Exception {
        return new ComposedIMarginAccountInfo(new JSONObject(getMarginIsolatedAccountInfo(recvWindow)));
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo(ArrayList<String> symbols) throws Exception {
        String params = getParamTimestamp() + "&symbols=" + assembleSymbolsList(symbols);
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo(ArrayList<String> symbols) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(symbols));
    }

    /** Request to get margin isolated account info list
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as ArrayList<{@link IsolatedMarginAccountInfo}>
     * **/
    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(ArrayList<String> symbols) throws Exception {
        return createIsolatedMarginAccountInfoList(new JSONObject(getMarginIsolatedAccountInfo(symbols))
                .getJSONArray("assets"));
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo(String[] symbols) throws Exception {
       return getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)));
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo(String[] symbols) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols))));
    }

    /** Request to get margin isolated account info list
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as ArrayList<{@link IsolatedMarginAccountInfo}>
     * **/
    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(String[] symbols) throws Exception {
        JSONArray marginAccountList = new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols))))
                .getJSONArray("assets");
        return createIsolatedMarginAccountInfoList(marginAccountList);
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo(ArrayList<String> symbols, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbols=" + assembleSymbolsList(symbols) + "&recvWindow=" + recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo(ArrayList<String> symbols, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(symbols, recvWindow));
    }

    /** Request to get margin isolated account info list
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as ArrayList<{@link IsolatedMarginAccountInfo}>
     * **/
    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(ArrayList<String> symbols, long recvWindow) throws Exception {
        JSONArray jsonArray = new JSONObject(getMarginIsolatedAccountInfo(symbols, recvWindow)).getJSONArray("assets");
        return createIsolatedMarginAccountInfoList(jsonArray);
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link String}
     * **/
    public String getMarginIsolatedAccountInfo(String[] symbols, long recvWindow) throws Exception {
        return getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)), recvWindow);
    }

    /** Request to get margin isolated account info
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as {@link JSONObject}
     * **/
    public JSONObject getJSONMarginIsolatedAccountInfo(String[] symbols, long recvWindow) throws Exception {
        return new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)), recvWindow));
    }

    /** Request to get margin isolated account info list
     * @param #symbols: symbols used in the request es. BTCUSDT,BNBUSDT,ADAUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-account-info-user_data</a>
     * @return margin account info as ArrayList<{@link IsolatedMarginAccountInfo}>
     * **/
    public ArrayList<IsolatedMarginAccountInfo> getMarginIsolatedAccountList(String[] symbols, long recvWindow) throws Exception {
        JSONArray marginAccountList = new JSONObject(getMarginIsolatedAccountInfo(new ArrayList<>(Arrays.asList(symbols)),
                recvWindow)).getJSONArray("assets");
        return createIsolatedMarginAccountInfoList(marginAccountList);
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link String}
     * **/
    public String switchIMarginAccountStatus(String symbol, boolean enableIsolated) throws Exception {
        String method = DELETE_METHOD;
        if(enableIsolated)
            method = POST_METHOD;
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, method);
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link JSONObject}
     * **/
    public JSONObject switchJSONIMarginAccountStatus(String symbol, boolean enableIsolated) throws Exception {
        return new JSONObject(switchIMarginAccountStatus(symbol, enableIsolated));
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link IsolatedMarginAccountStatus}
     * **/
    public IsolatedMarginAccountStatus switchObjectIMarginAccountStatus(String symbol, boolean enableIsolated) throws Exception {
        return new IsolatedMarginAccountStatus(new JSONObject(switchIMarginAccountStatus(symbol, enableIsolated)));
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link String}
     * **/
    public String switchIMarginAccountStatus(String symbol, long recvWindow, boolean enableIsolated) throws Exception {
        String method = DELETE_METHOD;
        if(enableIsolated)
            method = POST_METHOD;
        String params = getParamTimestamp() + "&symbol=" + symbol + "&recvWindow=" + recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_INFO_ENDPOINT, params, method);
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link JSONObject}
     * **/
    public JSONObject switchJSONIMarginAccountStatus(String symbol, long recvWindow, boolean enableIsolated) throws Exception {
        return new JSONObject(switchIMarginAccountStatus(symbol, recvWindow, enableIsolated));
    }

    /** Request to enable or disable isolated margin account status
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #enableIsolated: enable or disable isolated margin account status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade">
     *     https://binance-docs.github.io/apidocs/spot/en/#disable-isolated-margin-account-trade</a>
     * @return result of enable or disable isolated margin account status as {@link IsolatedMarginAccountStatus}
     * **/
    public IsolatedMarginAccountStatus switchObjectIMarginAccountStatus(String symbol, long recvWindow, boolean enableIsolated) throws Exception {
        return new IsolatedMarginAccountStatus(new JSONObject(switchIMarginAccountStatus(symbol, recvWindow, enableIsolated)));
    }

    /** Request to get isolate margin account limit <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link String}
     * **/
    public String getIsolateMarginAccountLimit() throws Exception {
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get isolate margin account limit <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link JSONObject}
     * **/
    public JSONObject getJSONIsolateMarginAccountLimit() throws Exception {
        return new JSONObject(getIsolateMarginAccountLimit());
    }

    /** Request to get isolate margin account limit <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link IsolatedMarginAccountLimit} object
     * **/
    public IsolatedMarginAccountLimit getObjectIsolateMarginAccountLimit() throws Exception {
        return new IsolatedMarginAccountLimit(new JSONObject(getIsolateMarginAccountLimit()));
    }

    /** Request to get isolate margin account limit
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link String}
     * **/
    public String getIsolateMarginAccountLimit(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(ISOLATED_MARGIN_ACCOUNT_LIMIT_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolate margin account limit
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link JSONObject}
     * **/
    public JSONObject getJSONIsolateMarginAccountLimit(long recvWindow) throws Exception {
        return new JSONObject(getIsolateMarginAccountLimit(recvWindow));
    }

    /** Request to get isolate margin account limit
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-enabled-isolated-margin-account-limit-user_data</a>
     * @return isolate margin account limit as {@link IsolatedMarginAccountLimit} object
     * **/
    public IsolatedMarginAccountLimit getObjectIsolateMarginAccountLimit(long recvWindow) throws Exception {
        return new IsolatedMarginAccountLimit(new JSONObject(getIsolateMarginAccountLimit(recvWindow)));
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link String}
     * **/
    public String getIsolatedMarginSymbol(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link JSONObject}
     * **/
    public JSONObject getJSONIsolatedMarginSymbol(String symbol) throws Exception {
        return new JSONObject(getIsolatedMarginSymbol(symbol));
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link IsolatedMarginSymbol} object
     * **/
    public IsolatedMarginSymbol getObjectIsolatedMarginSymbol(String symbol) throws Exception {
        return new IsolatedMarginSymbol(new JSONObject(getIsolatedMarginSymbol(symbol)));
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link String}
     * **/
    public String getIsolatedMarginSymbol(String symbol, long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol + "&recvWindow=" + recvWindow;
        return sendSignedRequest(QUERY_ISOLATED_MARGIN_SYMBOL_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link JSONObject}
     * **/
    public JSONObject getJSONIsolatedMarginSymbol(String symbol, long recvWindow) throws Exception {
        return new JSONObject(getIsolatedMarginSymbol(symbol, recvWindow));
    }

    /** Request to get isolate margin symbol
     * @param #symbol: symbol used in the request es. BTCUSDT
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-symbol-user_data</a>
     * @return isolate margin symbol as {@link IsolatedMarginSymbol} object
     * **/
    public IsolatedMarginSymbol getObjectIsolatedMarginSymbol(String symbol, long recvWindow) throws Exception {
        return new IsolatedMarginSymbol(new JSONObject(getIsolatedMarginSymbol(symbol, recvWindow)));
    }

    /** Request to get all isolate margin symbols <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     * @return all isolate margin symbols as {@link String}
     * **/
    public String getAllIsolatedMarginSymbol() throws Exception {
        return sendSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get all isolate margin symbols <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     * @return all isolate margin symbols as {@link JSONArray}
     * **/
    public JSONArray getJSONAllIsolatedMarginSymbol() throws Exception {
        return new JSONArray(getAllIsolatedMarginSymbol());
    }

    /** Request to get all isolate margin symbols <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     * @return all isolate margin symbols as ArrayList<{@link IsolatedMarginSymbol}>
     * **/
    public ArrayList<IsolatedMarginSymbol> getAllIsolatedMarginSymbolList() throws Exception {
        return assembleAllIMarginSymbolList(new JSONArray(getAllIsolatedMarginSymbol()));
    }

    /** Request to get all isolate margin symbols
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     * @return all isolate margin symbols as {@link String}
     * **/
    public String getAllIsolatedMarginSymbol(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(QUERY_ALL_ISOLATED_MARGIN_SYMBOL_ENDPOINT, params, GET_METHOD);
    }

    /**
     * Request to get all isolate margin symbols
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return all isolate margin symbols as {@link JSONArray}
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     **/
    public JSONArray getJSONAllIsolatedMarginSymbol(long recvWindow) throws Exception {
        return new JSONArray(getAllIsolatedMarginSymbol(recvWindow));
    }

    /**
     * Request to get all isolate margin symbols
     *
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @return all isolate margin symbols as ArrayList<{@link IsolatedMarginSymbol}>
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data">
     * https://binance-docs.github.io/apidocs/spot/en/#get-all-isolated-margin-symbol-user_data</a>
     **/
    public ArrayList<IsolatedMarginSymbol> getAllIsolatedMarginSymbolList(long recvWindow) throws Exception {
        return assembleAllIMarginSymbolList(new JSONArray(getAllIsolatedMarginSymbol(recvWindow)));
    }

    /**
     * Method to assemble an {@link IsolatedMarginSymbol} list
     *
     * @param #symbolsList: obtained from Binance's request
     * @return list as {@link ArrayList} of {@link IsolatedMarginSymbol}
     **/
    private ArrayList<IsolatedMarginSymbol> assembleAllIMarginSymbolList(JSONArray symbolsList) {
        ArrayList<IsolatedMarginSymbol> isolatedMarginSymbols = new ArrayList<>();
        for (int j = 0; j < symbolsList.length(); j++)
            isolatedMarginSymbols.add(new IsolatedMarginSymbol(symbolsList.getJSONObject(j)));
        return isolatedMarginSymbols;
    }

    /** Request to get toggle BNB on trade interest <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link String}
     * **/
    public String toggleBNBOnTradeInterest() throws Exception {
        return sendSignedRequest(MARGIN_BNB_ENDPOINT, getParamTimestamp(), POST_METHOD);
    }

    /** Request to get toggle BNB on trade interest <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link JSONObject}
     * **/
    public JSONObject toggleJSONBNBOnTradeInterest() throws Exception {
        return new JSONObject(toggleBNBOnTradeInterest());
    }

    /** Request to get toggle BNB on trade interest <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link BNBBurn} object
     * **/
    public BNBBurn toggleObjectBNBOnTradeInterest() throws Exception {
        return new BNBBurn(new JSONObject(toggleBNBOnTradeInterest()));
    }

    /** Request to get toggle BNB on trade interest
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are spotBNBBurn,interestBNBBurn,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link String}
     * **/
    public String toggleBNBOnTradeInterest(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(MARGIN_BNB_ENDPOINT, params, POST_METHOD);
    }

    /** Request to get toggle BNB on trade interest
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are spotBNBBurn,interestBNBBurn,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link JSONObject}
     * **/
    public JSONObject toggleJSONBNBOnTradeInterest(Params extraParams) throws Exception {
        return new JSONObject(toggleBNBOnTradeInterest(extraParams));
    }

    /** Request to get toggle BNB on trade interest
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are spotBNBBurn,interestBNBBurn,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#toggle-bnb-burn-on-spot-trade-and-margin-interest-user_data</a>
     * @return toggle BNB on trade interest as {@link BNBBurn} object
     * **/
    public BNBBurn toggleObjectBNBOnTradeInterest(Params extraParams) throws Exception {
        return new BNBBurn(new JSONObject(toggleBNBOnTradeInterest(extraParams)));
    }

    /** Request to get BNB burn status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link String}
     * **/
    public String getBNBBurnStatus() throws Exception {
        return sendSignedRequest(MARGIN_BNB_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get BNB burn status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link JSONObject}
     * **/
    public JSONObject getJSONBNBBurnStatus() throws Exception {
        return new JSONObject(toggleBNBOnTradeInterest());
    }

    /** Request to get BNB burn status <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link BNBBurn} object
     * **/
    public BNBBurn getObjectBNBBurnStatus() throws Exception {
        return new BNBBurn(new JSONObject(getBNBBurnStatus()));
    }

    /** Request to get BNB burn status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link String}
     * **/
    public String getBNBBurnStatus(long recvWindow) throws Exception {
        String params = getParamTimestamp() + "&recvWindow=" + recvWindow;
        return sendSignedRequest(MARGIN_BNB_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get BNB burn status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link JSONObject}
     * **/
    public JSONObject getJSONBNBBurnStatus(long recvWindow) throws Exception {
        return new JSONObject(getBNBBurnStatus(recvWindow));
    }

    /** Request to get BNB burn status
     * @param #recvWindow: time to keep alive request, then rejected. Max value is 60000
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#get-bnb-burn-status-user_data</a>
     * @return BNB burn status as {@link BNBBurn} object
     * **/
    public BNBBurn getObjectBNBBurnStatus(long recvWindow) throws Exception {
        return new BNBBurn(new JSONObject(getBNBBurnStatus(recvWindow)));
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as {@link String}
     * **/
    public String getMarginInterestRateHistory(String asset) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        return sendSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as {@link JSONArray}
     * **/
    public JSONArray getJSONMarginInterestRateHistory(String asset) throws Exception {
        return new JSONArray(getMarginInterestRateHistory(asset));
    }

    /** Request to get margin interest rate history list
     * @param #asset: used in the request es. BTC
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as ArrayList<{@link MarginInterestRate}>
     * **/
    public ArrayList<MarginInterestRate> getMarginInterestRateHistoryList(String asset) throws Exception {
        return assembleMarginIRateHistoryList(new JSONArray(getMarginInterestRateHistory(asset)));
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as {@link String}
     * **/
    public String getMarginInterestRateHistory(String asset, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&asset=" + asset;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(MARGIN_INTEREST_RATE_HISTORY_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as {@link JSONArray}
     * **/
    public JSONArray getJSONMarginInterestRateHistory(String asset, Params extraParams) throws Exception {
        return new JSONArray(getMarginInterestRateHistory(asset, extraParams));
    }

    /** Request to get margin interest rate history
     * @param #asset: used in the request es. BTC
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,startTime,endTime,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-margin-interest-rate-history-user_data</a>
     * @return margin interest rate history as ArrayList<{@link MarginInterestRate}>
     * **/
    public ArrayList<MarginInterestRate> getMarginInterestRateHistoryList(String asset, Params extraParams) throws Exception {
        return assembleMarginIRateHistoryList(new JSONArray(getMarginInterestRateHistory(asset, extraParams)));
    }

    /** Method to assemble a MarginInterestRate list
     * @param #jsonRate: obtained from Binance's request
     * @return as ArrayList<{@link MarginInterestRate}>
     * **/
    private ArrayList<MarginInterestRate> assembleMarginIRateHistoryList(JSONArray jsonRate) {
        ArrayList<MarginInterestRate> marginInterestRates = new ArrayList<>();
        for (int j = 0; j < jsonRate.length(); j++)
            marginInterestRates.add(new MarginInterestRate(jsonRate.getJSONObject(j)));
        return marginInterestRates;
    }

    /** Request to get cross margin fee data <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as {@link String}
     * **/
    public String getCrossMarginFeeData() throws Exception {
        return sendSignedRequest(CROSS_MARGIN_DATA_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get cross margin fee data <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as {@link JSONArray}
     * **/
    public JSONArray getJSONCrossMarginFeeData() throws Exception {
        return new JSONArray(getCrossMarginFeeData());
    }

    /** Request to get cross margin fee data list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as ArrayList<{@link CrossMarginFee}>
     * **/
    public ArrayList<CrossMarginFee> getCrossMarginFeesList() throws Exception {
        return assembleCrossMarginFeesList(new JSONArray(getCrossMarginFeeData()));
    }

    /** Request to get cross margin fee data
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,coin,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as {@link String}
     * **/
    public String getCrossMarginFeeData(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(CROSS_MARGIN_DATA_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get cross margin fee data
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,coin,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as {@link JSONArray}
     * **/
    public JSONArray getJSONCrossMarginFeeData(Params extraParams) throws Exception {
        return new JSONArray(getCrossMarginFeeData(extraParams));
    }

    /** Request to get cross margin fee data list
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,coin,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-fee-data-user_data</a>
     * @return cross margin fee data as ArrayList<{@link CrossMarginFee}>
     * **/
    public ArrayList<CrossMarginFee> getCrossMarginFeesList(Params extraParams) throws Exception {
        return assembleCrossMarginFeesList(new JSONArray(getCrossMarginFeeData(extraParams)));
    }

    /** Method to assemble a CrossMarginFee list
     * @param #jsonFees: obtained from Binance's request
     * @return as ArrayList<{@link CrossMarginFee}>
     * **/
    private ArrayList<CrossMarginFee> assembleCrossMarginFeesList(JSONArray jsonFees) {
        ArrayList<CrossMarginFee> crossMarginFees = new ArrayList<>();
        for (int j = 0; j < jsonFees.length(); j++)
            crossMarginFees.add(new CrossMarginFee(jsonFees.getJSONObject(j)));
        return crossMarginFees;
    }

    /** Request to get isolated margin fee data <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as {@link String}
     * **/
    public String getIsolatedMarginFee() throws Exception {
        return sendSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT, getParamTimestamp(), GET_METHOD);
    }

    /** Request to get isolated margin fee data <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as {@link JSONArray}
     * **/
    public JSONArray getJSONIsolatedMarginFee() throws Exception {
        return new JSONArray(getIsolatedMarginFee());
    }

    /** Request to get isolated margin fee data list <br>
     * Any params required
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as ArrayList<{@link IsolatedMarginFee}>
     * **/
    public ArrayList<IsolatedMarginFee> getIsolatedMarginFeesList() throws Exception {
        return assembleIsolatedMarginFeesList(new JSONArray(getIsolatedMarginFee()));
    }

    /** Request to get isolated margin fee data
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as {@link String}
     * **/
    public String getIsolatedMarginFee(Params extraParams) throws Exception {
        String params = apiRequest.encodeAdditionalParams(getParamTimestamp(), extraParams);
        return sendSignedRequest(ISOLATED_MARGIN_DATA_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolated margin fee data
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as {@link JSONArray}
     * **/
    public JSONArray getJSONIsolatedMarginFee(Params extraParams) throws Exception {
        return new JSONArray(getIsolatedMarginFee(extraParams));
    }

    /** Request to get isolated margin fee data list
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are vipLevel,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-fee-data-user_data</a>
     * @return isolated margin fee data as ArrayList<{@link IsolatedMarginFee}>
     * **/
    public ArrayList<IsolatedMarginFee> getIsolatedMarginFeesList(Params extraParams) throws Exception {
        return assembleIsolatedMarginFeesList(new JSONArray(getIsolatedMarginFee(extraParams)));
    }

    /** Method to assemble an {@link IsolatedMarginFee} list
     * @param #jsonFees: obtained from Binance's request
     * @return list as {@link ArrayList} of {@link IsolatedMarginFee}
     * **/
    private ArrayList<IsolatedMarginFee> assembleIsolatedMarginFeesList(JSONArray jsonFees) {
        ArrayList<IsolatedMarginFee> isolatedMarginFees = new ArrayList<>();
        for (int j = 0; j < jsonFees.length(); j++)
            isolatedMarginFees.add(new IsolatedMarginFee(jsonFees.getJSONObject(j)));
        return isolatedMarginFees;
    }

    /** Request to get isolated margin tier data
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as {@link String}
     * **/
    public String getIsolatedMarginTierData(String symbol) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        return sendSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolated margin tier data
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as {@link JSONArray}
     * **/
    public JSONArray getJSONIsolatedMarginTierData(String symbol) throws Exception {
        return new JSONArray(getIsolatedMarginTierData(symbol));
    }

    /** Request to get isolated margin tier data list
     * @param #symbol: used in the request es. BTCBUSD
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as ArrayList<{@link IsolatedMarginTierData}>
     * **/
    public ArrayList<IsolatedMarginTierData> getIsolatedMarginTierDataList(String symbol) throws Exception {
        return assembleIsolatedMarginTierDataList(new JSONArray(getIsolatedMarginTierData(symbol)));
    }

    /** Request to get isolated margin tier data
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are tier,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as {@link String}
     * **/
    public String getIsolatedMarginTierData(String symbol, Params extraParams) throws Exception {
        String params = getParamTimestamp() + "&symbol=" + symbol;
        params = apiRequest.encodeAdditionalParams(params, extraParams);
        return sendSignedRequest(ISOLATED_MARGIN_TIER_DATA_ENDPOINT, params, GET_METHOD);
    }

    /** Request to get isolated margin tier data
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are tier,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as {@link JSONArray}
     * **/
    public JSONArray getJSONIsolatedMarginTierData(String symbol, Params extraParams) throws Exception {
        return new JSONArray(getIsolatedMarginTierData(symbol, extraParams));
    }

    /** Request to get isolated margin tier data list
     * @param #symbol: used in the request es. BTCBUSD
     * @param extraParams: additional params of the request
     * @implSpec (keys accepted are tier,symbol,recvWindow)
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#query-isolated-margin-tier-data-user_data</a>
     * @return isolated margin tier data as ArrayList<{@link IsolatedMarginTierData}>
     * **/
    public ArrayList<IsolatedMarginTierData> getIsolatedMarginTierDataList(String symbol, Params extraParams) throws Exception {
        return assembleIsolatedMarginTierDataList(new JSONArray(getIsolatedMarginTierData(symbol, extraParams)));
    }

    /** Method to assemble an {@link IsolatedMarginTierData} list
     * @param #jsonTierData: obtained from Binance's request
     * @return list as {@link ArrayList} of {@link IsolatedMarginTierData}
     * **/
    private ArrayList<IsolatedMarginTierData> assembleIsolatedMarginTierDataList(JSONArray jsonTierData) {
        ArrayList<IsolatedMarginTierData> isolatedMarginTierData = new ArrayList<>();
        for (int j = 0; j < jsonTierData.length(); j++)
            isolatedMarginTierData.add(new IsolatedMarginTierData(jsonTierData.getJSONObject(j)));
        return isolatedMarginTierData;
    }

}