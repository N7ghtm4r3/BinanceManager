package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment;

import com.tecknobit.apimanager.annotations.*;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.interfaces.Manager;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.*;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.SubscriptionTransaction.SubscriptionExecutionType;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.TargetAssetROI.ROIType;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.HoldingsPlanDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.HoldingsPlanDetails.PlanDetails;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.Plan;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.PlanStructure.PlanType;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.IndexPlansList;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.SinglePortfolioPlansList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;
import static com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.Plan.*;
import static com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.PlanStructure.PlanType.INDEX;

/**
 * The {@code BinanceAutoInvestmentManager} class is useful to manage all {@code "Binance"}'s Auto-Investment Endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#auto-invest-endpoints">
 * Auto-Invest Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 * @see Manager
 */
public class BinanceAutoInvestmentManager extends BinanceSignedManager {

    /**
     * {@code TARGET_ASSET_LIST_ENDPOINT} is constant for TARGET_ASSET_LIST_ENDPOINT's endpoint
     */
    public static final String TARGET_ASSET_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/target-asset/list";

    /**
     * {@code TARGET_ASSET_ROI_LIST_ENDPOINT} is constant for TARGET_ASSET_ROI_LIST_ENDPOINT's endpoint
     */
    public static final String TARGET_ASSET_ROI_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/target-asset/roi/list";

    /**
     * {@code ALL_ASSET_ENDPOINT} is constant for ALL_ASSET_ENDPOINT's endpoint
     */
    public static final String ALL_ASSET_ENDPOINT = "/sapi/v1/lending/auto-invest/all/asset";

    /**
     * {@code SOURCE_ASSET_LIST_ENDPOINT} is constant for SOURCE_ASSET_LIST_ENDPOINT's endpoint
     */
    public static final String SOURCE_ASSET_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/source-asset/list";

    /**
     * {@code PLAN_ADD_ENDPOINT} is constant for PLAN_ADD_ENDPOINT's endpoint
     */
    public static final String PLAN_ADD_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/add";

    /**
     * {@code PLAN_EDIT_ENDPOINT} is constant for PLAN_EDIT_ENDPOINT's endpoint
     */
    public static final String PLAN_EDIT_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/edit";

    /**
     * {@code PLAN_EDIT_STATUS_ENDPOINT} is constant for PLAN_EDIT_STATUS_ENDPOINT's endpoint
     */
    public static final String PLAN_EDIT_STATUS_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/edit-status";

    /**
     * {@code PLAN_LIST_ENDPOINT} is constant for PLAN_LIST_ENDPOINT's endpoint
     */
    public static final String PLAN_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/list";

    /**
     * {@code PLAN_ID_ENDPOINT} is constant for PLAN_ID_ENDPOINT's endpoint
     */
    public static final String PLAN_ID_ENDPOINT = "/sapi/v1/lending/auto-invest/plan/id";

    /**
     * {@code AUTO_INVEST_HISTORY_LIST_ENDPOINT} is constant for AUTO_INVEST_HISTORY_LIST_ENDPOINT's endpoint
     */
    public static final String AUTO_INVEST_HISTORY_LIST_ENDPOINT = "/sapi/v1/lending/auto-invest/history/list";

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String defaultErrorMessage, int timeout, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, int timeout, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     */
    public BinanceAutoInvestmentManager(String baseEndpoint, String apiKey,
                                        String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceAutoInvestmentManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceAutoInvestmentManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceSignedManager firstManager = new BinanceSignedManager("apiKey", "apiSecret");
     *        //You don't need to insert all credentials to make manager work
     *        BinanceSignedManager secondManager = new BinanceSignedManager(); //same credentials used
     *     }
     * </pre>
     */
    public BinanceAutoInvestmentManager() {
        super();
    }

    /**
     * Request to get target asset list
     *
     * @param targetAsset: the target asset from fetch the list
     * @param size:        size of the results to return
     * @return target asset list as {@link TargetAssetList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-list-user_data">
     * Get target asset list(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/list")
    public TargetAssetList getTargetAssetList(String targetAsset, int size) throws Exception {
        return getTargetAssetList(targetAsset, size, LIBRARY_OBJECT);
    }

    /**
     * Request to get target asset list
     *
     * @param targetAsset: the target asset from fetch the list
     * @param size:        size of the results to return
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return target asset list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-list-user_data">
     * Get target asset list(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/list")
    public <T> T getTargetAssetList(String targetAsset, int size, ReturnFormat format) throws Exception {
        return getTargetAssetList(targetAsset, size, null, format);
    }

    /**
     * Request to get target asset list
     *
     * @param targetAsset: the target asset from fetch the list
     * @param size:        size of the results to return
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "current"} -> current query page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return target asset list as {@link TargetAssetList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-list-user_data">
     * Get target asset list(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/list")
    public TargetAssetList getTargetAssetList(String targetAsset, int size, Params queryParams) throws Exception {
        return getTargetAssetList(targetAsset, size, queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get target asset list
     *
     * @param targetAsset: the target asset from fetch the list
     * @param size:        size of the results to return
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "current"} -> current query page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return target asset list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-list-user_data">
     * Get target asset list(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/list")
    public <T> T getTargetAssetList(String targetAsset, int size, Params queryParams, ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("targetAsset", targetAsset);
        queryParams.addParam("size", size);
        JSONObject response = new JSONObject(sendGetSignedRequest(TARGET_ASSET_LIST_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new TargetAssetList(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get ROI return list for target asset
     *
     * @param targetAsset: the target asset from fetch the list
     * @param hisRoiType:  ROI type
     * @return ROI return list as {@link ArrayList} of {@link TargetAssetROI} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-roi-data-user_data">
     * Get target asset ROI data(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/roi/list")
    public ArrayList<TargetAssetROI> getTargetAssetROIData(String targetAsset, ROIType hisRoiType) throws Exception {
        return getTargetAssetROIData(targetAsset, hisRoiType, LIBRARY_OBJECT);
    }

    /**
     * Request to get ROI return list for target asset
     *
     * @param targetAsset: the target asset from fetch the list
     * @param hisRoiType:  ROI type
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return ROI return list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-roi-data-user_data">
     * Get target asset ROI data(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/roi/list")
    public <T> T getTargetAssetROIData(String targetAsset, ROIType hisRoiType, ReturnFormat format) throws Exception {
        return getTargetAssetROIData(targetAsset, hisRoiType, -1, format);
    }

    /**
     * Request to get ROI return list for target asset
     *
     * @param targetAsset: the target asset from fetch the list
     * @param hisRoiType:  ROI type
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @return ROI return list as {@link ArrayList} of {@link TargetAssetROI} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-roi-data-user_data">
     * Get target asset ROI data(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/roi/list")
    public ArrayList<TargetAssetROI> getTargetAssetROIData(String targetAsset, ROIType hisRoiType,
                                                           long recvWindow) throws Exception {
        return getTargetAssetROIData(targetAsset, hisRoiType, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get ROI return list for target asset
     *
     * @param targetAsset: the target asset from fetch the list
     * @param hisRoiType:  ROI type
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return ROI return list as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-roi-data-user_data">
     * Get target asset ROI data(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/target-asset/roi/list")
    public <T> T getTargetAssetROIData(String targetAsset, ROIType hisRoiType, long recvWindow,
                                       ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("targetAsset", targetAsset);
        query.addParam("hisRoiType", hisRoiType);
        JSONArray data = new JSONArray(sendGetSignedRequest(TARGET_ASSET_ROI_LIST_ENDPOINT, query));
        return switch (format) {
            case JSON -> (T) data;
            case LIBRARY_OBJECT -> {
                ArrayList<TargetAssetROI> roiData = new ArrayList<>();
                for (int j = 0; j < data.length(); j++)
                    roiData.add(new TargetAssetROI(data.getJSONObject(j)));
                yield (T) roiData;
            }
            default -> (T) data.toString();
        };
    }

    /**
     * Request to get all source assets and target assets <br>
     * No-any params required
     *
     * @return all source assets and target assets as {@link SourceTargetAsset} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-source-asset-and-target-asset-user_data">
     * Query all source asset and target asset(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/all/asset")
    public SourceTargetAsset getAllSourceTargetAssets() throws Exception {
        return getAllSourceTargetAssets(LIBRARY_OBJECT);
    }

    /**
     * Request to get all source assets and target assets
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return all source assets and target assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-source-asset-and-target-asset-user_data">
     * Query all source asset and target asset(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/all/asset")
    public <T> T getAllSourceTargetAssets(ReturnFormat format) throws Exception {
        return getAllSourceTargetAssets(-1, format);
    }

    /**
     * Request to get all source assets and target assets
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return all source assets and target assets as {@link SourceTargetAsset} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-source-asset-and-target-asset-user_data">
     * Query all source asset and target asset(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/all/asset")
    public SourceTargetAsset getAllSourceTargetAssets(long recvWindow) throws Exception {
        return getAllSourceTargetAssets(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get all source assets and target assets
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return all source assets and target assets as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-source-asset-and-target-asset-user_data">
     * Query all source asset and target asset(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/all/asset")
    public <T> T getAllSourceTargetAssets(long recvWindow, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(ALL_ASSET_ENDPOINT, createTimestampPayload(null,
                recvWindow)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SourceTargetAsset(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get source asset to be used for investment
     *
     * @param usageType: usage type
     * @return source asset to be used for investment as {@link SourceAssetList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-source-asset-list-user_data">
     * Query source asset list(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/source-asset/list")
    public SourceAssetList getSourceAssetList(SubscriptionExecutionType usageType) throws Exception {
        return getSourceAssetList(usageType, LIBRARY_OBJECT);
    }

    /**
     * Request to get source asset to be used for investment
     *
     * @param usageType: usage type
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return source asset to be used for investment as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-source-asset-list-user_data">
     * Query source asset list(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/source-asset/list")
    public <T> T getSourceAssetList(SubscriptionExecutionType usageType, ReturnFormat format) throws Exception {
        return getSourceAssetList(usageType, null, format);
    }

    /**
     * Request to get source asset to be used for investment
     *
     * @param usageType:   usage type
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "targetAsset"} -> list of targets - [Array<STRING>]
     *                           </li>
     *                           <li>
     *                                {@code "indexId"} -> index identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return source asset to be used for investment as {@link SourceAssetList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-source-asset-list-user_data">
     * Query source asset list(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/source-asset/list")
    public SourceAssetList getSourceAssetList(SubscriptionExecutionType usageType, Params queryParams) throws Exception {
        return getSourceAssetList(usageType, queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get source asset to be used for investment
     *
     * @param usageType:   usage type
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "targetAsset"} -> list of targets - [Array<STRING>]
     *                           </li>
     *                           <li>
     *                                {@code "indexId"} -> index identifier - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                - [BOOLEAN]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return source asset to be used for investment as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-source-asset-list-user_data">
     * Query source asset list(USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/source-asset/list")
    public <T> T getSourceAssetList(SubscriptionExecutionType usageType, Params queryParams,
                                    ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("usageType", usageType);
        JSONObject response = new JSONObject(sendGetSignedRequest(SOURCE_ASSET_LIST_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new SourceAssetList(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to add an investment plan creation
     *
     * @param UID:                   UID value
     * @param sourceType:            source type
     * @param planType:              type of the plan
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param details:               details of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-creation-user_data">
     * Investment plan creation(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/add")
    public Plan createInvestmentPlan(String UID, SourceType sourceType, PlanType planType, double subscriptionAmount,
                                     SubscriptionCycle subscriptionCycle, int subscriptionStartTime, String sourceAsset,
                                     PlanDetails... details) throws Exception {
        return createInvestmentPlan(UID, sourceType, planType, subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, LIBRARY_OBJECT, details);
    }

    /**
     * Request to add an investment plan creation
     *
     * @param UID:                   UID value
     * @param sourceType:            source type
     * @param planType:              type of the plan
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param details:               details of the plan
     * @param format                 :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-creation-user_data">
     * Investment plan creation(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/add")
    public <T> T createInvestmentPlan(String UID, SourceType sourceType, PlanType planType, double subscriptionAmount,
                                      SubscriptionCycle subscriptionCycle, int subscriptionStartTime, String sourceAsset,
                                      ReturnFormat format, PlanDetails... details) throws Exception {
        return createInvestmentPlan(UID, sourceType, planType, subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, null, format, details);
    }

    /**
     * Request to add an investment plan creation
     *
     * @param UID:                   UID value
     * @param sourceType:            source type
     * @param planType:              type of the plan
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                          e.g: TR12354859 - [STRING]
     *                                     </li>
     *                                     <li>
     *                                          {@code "IndexId"} -> Only for planType = INDEX , value = 1 - [LONG]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-creation-user_data">
     * Investment plan creation(USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/add")
    public Plan createInvestmentPlan(String UID, SourceType sourceType, PlanType planType, double subscriptionAmount,
                                     SubscriptionCycle subscriptionCycle, int subscriptionStartTime, String sourceAsset,
                                     Params extraParams, PlanDetails... details) throws Exception {
        return createInvestmentPlan(UID, sourceType, planType, subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, extraParams, LIBRARY_OBJECT, details);
    }

    /**
     * Request to add an investment plan creation
     *
     * @param UID:                   UID value
     * @param sourceType:            source type
     * @param planType:              type of the plan
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                          e.g: TR12354859 - [STRING]
     *                                     </li>
     *                                     <li>
     *                                          {@code "IndexId"} -> Only for planType = INDEX , value = 1 - [LONG]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @param format                 :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-creation-user_data">
     * Investment plan creation(USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/add")
    public <T> T createInvestmentPlan(String UID, SourceType sourceType, PlanType planType, double subscriptionAmount,
                                      SubscriptionCycle subscriptionCycle, int subscriptionStartTime, String sourceAsset,
                                      Params extraParams, ReturnFormat format, PlanDetails... details) throws Exception {
        extraParams = createInvestmentPayload(subscriptionAmount, subscriptionCycle, subscriptionStartTime, sourceAsset,
                extraParams, details);
        extraParams.addParam("UID", UID);
        extraParams.addParam("sourceType", sourceType);
        extraParams.addParam("planType", planType);
        return returnPlan(sendPostSignedRequest(PLAN_ADD_ENDPOINT, extraParams), format);
    }

    /**
     * Request to edit an investment plan
     *
     * @param plan:                  plan to edit
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-adjustment-trade">
     * Investment plan adjustment (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit")
    public Plan adjustInvestmentPlan(Plan plan, double subscriptionAmount, SubscriptionCycle subscriptionCycle,
                                     int subscriptionStartTime, String sourceAsset, Params extraParams,
                                     PlanDetails... details) throws Exception {
        return adjustInvestmentPlan(plan.getPlanId(), subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, extraParams, LIBRARY_OBJECT, details);
    }

    /**
     * Request to edit an investment plan
     *
     * @param plan:                  plan to edit
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @param format                 :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-adjustment-trade">
     * Investment plan adjustment (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit")
    public <T> T adjustInvestmentPlan(Plan plan, double subscriptionAmount, SubscriptionCycle subscriptionCycle,
                                      int subscriptionStartTime, String sourceAsset, Params extraParams,
                                      ReturnFormat format, PlanDetails... details) throws Exception {
        return adjustInvestmentPlan(plan.getPlanId(), subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, extraParams, format, details);
    }

    /**
     * Request to edit an investment plan
     *
     * @param planId:                plan identifier to edit
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-adjustment-trade">
     * Investment plan adjustment (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit")
    public Plan adjustInvestmentPlan(long planId, double subscriptionAmount, SubscriptionCycle subscriptionCycle,
                                     int subscriptionStartTime, String sourceAsset, Params extraParams,
                                     PlanDetails... details) throws Exception {
        return adjustInvestmentPlan(planId, subscriptionAmount, subscriptionCycle, subscriptionStartTime,
                sourceAsset, extraParams, LIBRARY_OBJECT, details);
    }

    /**
     * Request to edit an investment plan
     *
     * @param planId:                plan identifier to edit
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request, keys accepted are:
     *                               <ul>
     *                                     <li>
     *                                          {@code "subscriptionStartDay"} -> cannot be sent when you open a position
     *                                          - [ENUM]
     *                                     </li>
     *                                     <li>
     *                                          {@code "subscriptionStartWeekday"} -> limit price of the order; If it is not sent, will place
     *                                          order by market price by default - [{@link SubscriptionStartWeekday}]
     *                                     </li>
     *                                     <li>
     *                                          {@code "flexibleAllowedToUse"} -> whether this plan is flexible allowed to be used
     *                                          - [BOOLEAN]
     *                                     </li>
     *                                     <li>
     *                                          {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                          - [LONG, default 5000]
     *                                     </li>
     *                               </ul>
     * @param details:               details of the plan
     * @param format                 :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#investment-plan-adjustment-trade">
     * Investment plan adjustment (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit")
    public <T> T adjustInvestmentPlan(long planId, double subscriptionAmount, SubscriptionCycle subscriptionCycle,
                                      int subscriptionStartTime, String sourceAsset, Params extraParams,
                                      ReturnFormat format, PlanDetails... details) throws Exception {
        extraParams = createInvestmentPayload(subscriptionAmount, subscriptionCycle, subscriptionStartTime, sourceAsset,
                extraParams, details);
        extraParams.addParam("planId", planId);
        return returnPlan(sendPostSignedRequest(PLAN_EDIT_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create the payload for an investment request
     *
     * @param subscriptionAmount:    amount of the subscription
     * @param subscriptionCycle:     cycle of the subscription
     * @param subscriptionStartTime: start time of the subscription
     * @param sourceAsset:           source asset of the plan
     * @param extraParams:           additional params of the request
     * @return payload as {@link Params}
     */
    private Params createInvestmentPayload(double subscriptionAmount, SubscriptionCycle subscriptionCycle,
                                           int subscriptionStartTime, String sourceAsset, Params extraParams,
                                           PlanDetails... details) {
        extraParams = createTimestampPayload(extraParams);
        extraParams.addParam("subscriptionAmount", subscriptionAmount);
        extraParams.addParam("subscriptionCycle", subscriptionCycle);
        extraParams.addParam("subscriptionStartTime", subscriptionStartTime);
        extraParams.addParam("sourceAsset", sourceAsset);
        extraParams.addParam("details", details);
        return extraParams;
    }

    /**
     * Request to change plan status
     *
     * @param plan:   plan to change plan status
     * @param status: the status of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public Plan changePlanStatus(Plan plan, PlanStatus status) throws Exception {
        return changePlanStatus(plan.getPlanId(), status, LIBRARY_OBJECT);
    }

    /**
     * Request to change plan status
     *
     * @param plan:   plan to change plan status
     * @param status: the status of the plan
     * @param format  :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public <T> T changePlanStatus(Plan plan, PlanStatus status, ReturnFormat format) throws Exception {
        return changePlanStatus(plan.getPlanId(), status, format);
    }

    /**
     * Request to change plan status
     *
     * @param planId: plan identifier to change plan status
     * @param status: the status of the plan
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public Plan changePlanStatus(long planId, PlanStatus status) throws Exception {
        return changePlanStatus(planId, status, LIBRARY_OBJECT);
    }

    /**
     * Request to change plan status
     *
     * @param planId: plan identifier to change plan status
     * @param status: the status of the plan
     * @param format  :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public <T> T changePlanStatus(long planId, PlanStatus status, ReturnFormat format) throws Exception {
        return changePlanStatus(planId, status, -1, format);
    }

    /**
     * Request to change plan status
     *
     * @param plan:       plan to change plan status
     * @param status:     the status of the plan
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public Plan changePlanStatus(Plan plan, PlanStatus status, long recvWindow) throws Exception {
        return changePlanStatus(plan.getPlanId(), status, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to change plan status
     *
     * @param plan:       plan to change plan status
     * @param status:     the status of the plan
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format      :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public <T> T changePlanStatus(Plan plan, PlanStatus status, long recvWindow, ReturnFormat format) throws Exception {
        return changePlanStatus(plan.getPlanId(), status, recvWindow, format);
    }

    /**
     * Request to change plan status
     *
     * @param planId:     plan identifier to change plan status
     * @param status:     the status of the plan
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return plan as {@link Plan} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public Plan changePlanStatus(long planId, PlanStatus status, long recvWindow) throws Exception {
        return changePlanStatus(planId, status, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to change plan status
     *
     * @param planId:     plan identifier to change plan status
     * @param status:     the status of the plan
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format      :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#change-plan-status-trade">
     * Change Plan Status (TRADE)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/lending/auto-invest/plan/edit-status")
    public <T> T changePlanStatus(long planId, PlanStatus status, long recvWindow, ReturnFormat format) throws Exception {
        Params payload = createTimestampPayload(null, recvWindow);
        payload.addParam("planId", planId);
        payload.addParam("status", status);
        return returnPlan(sendPostSignedRequest(PLAN_EDIT_STATUS_ENDPOINT, payload), format);
    }

    /**
     * Method to create a plan
     *
     * @param planResponse : obtained from Binance's response
     * @param format       :             return type formatter -> {@link ReturnFormat}
     * @return plan as {@code "format"} defines
     */
    @Returner
    private <T> T returnPlan(String planResponse, ReturnFormat format) {
        JSONObject response = new JSONObject(planResponse);
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new Plan(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get plan lists
     *
     * @param planType: plan type
     * @return plan lists as {@link IndexPlansList} or {@link SinglePortfolioPlansList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
     * Get list of plans (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/list")
    public <T> T getPlansList(PlanType planType) throws Exception {
        return getPlansList(planType, LIBRARY_OBJECT);
    }

    /**
     * Request to get plan lists
     *
     * @param planType: plan type
     * @param format    :             return type formatter -> {@link ReturnFormat}
     * @return plan lists as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
     * Get list of plans (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/list")
    public <T> T getPlansList(PlanType planType, ReturnFormat format) throws Exception {
        return getPlansList(planType, -1, format);
    }

    /**
     * Request to get plan lists
     *
     * @param planType:   plan type
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return plan lists as {@link IndexPlansList} or {@link SinglePortfolioPlansList} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
     * Get list of plans (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/list")
    public <T> T getPlansList(PlanType planType, long recvWindow) throws Exception {
        return getPlansList(planType, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to get plan lists
     *
     * @param planType:   plan type
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format      :             return type formatter -> {@link ReturnFormat}
     * @return plan lists as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
     * Get list of plans (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/list")
    public <T> T getPlansList(PlanType planType, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampPayload(null, recvWindow);
        query.addParam("planType", planType);
        JSONObject response = new JSONObject(sendGetSignedRequest(PLAN_LIST_ENDPOINT, query));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> {
                if (planType == INDEX)
                    yield (T) new IndexPlansList(response);
                else
                    yield (T) new SinglePortfolioPlansList(response);
            }
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get holding details of the plan
     *
     * @param plan: plan from fetch the holding details
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public HoldingsPlanDetails getHoldingsPlanDetails(Plan plan) throws Exception {
        return getHoldingsPlanDetails(plan.getPlanId(), LIBRARY_OBJECT);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param plan: plan from fetch the holding details
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public <T> T getHoldingsPlanDetails(Plan plan, ReturnFormat format) throws Exception {
        return getHoldingsPlanDetails(plan.getPlanId(), format);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param planId: plan identifier from fetch the holding details
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public HoldingsPlanDetails getHoldingsPlanDetails(long planId) throws Exception {
        return getHoldingsPlanDetails(planId, LIBRARY_OBJECT);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param planId: plan identifier from fetch the holding details
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public <T> T getHoldingsPlanDetails(long planId, ReturnFormat format) throws Exception {
        return getHoldingsPlanDetails(planId, null, format);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param plan:        plan from fetch the holding details
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                e.g: TR12354859 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @Wrapper
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public HoldingsPlanDetails getHoldingsPlanDetails(Plan plan, Params queryParams) throws Exception {
        return getHoldingsPlanDetails(plan.getPlanId(), queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param plan:        plan from fetch the holding details
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                e.g: TR12354859 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format       :             return type formatter -> {@link ReturnFormat}
     * @return holding details of the plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @WrappedRequest
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public <T> T getHoldingsPlanDetails(Plan plan, Params queryParams, ReturnFormat format) throws Exception {
        return getHoldingsPlanDetails(plan.getPlanId(), queryParams, format);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param planId:      plan identifier from fetch the holding details
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                e.g: TR12354859 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return holding details of the plan as {@link HoldingsPlanDetails} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public HoldingsPlanDetails getHoldingsPlanDetails(long planId, Params queryParams) throws Exception {
        return getHoldingsPlanDetails(planId, queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get holding details of the plan
     *
     * @param planId:      plan identifier from fetch the holding details
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "requestId"} -> if not null, must follow businessReference + unique string,
     *                                e.g: TR12354859 - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format       :             return type formatter -> {@link ReturnFormat}
     * @return holding details of the plan as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
     * Query holding details of the plan (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/plan/id")
    public <T> T getHoldingsPlanDetails(long planId, Params queryParams, ReturnFormat format) throws Exception {
        queryParams = createTimestampPayload(queryParams);
        queryParams.addParam("planId", planId);
        JSONObject response = new JSONObject(sendGetSignedRequest(PLAN_ID_ENDPOINT, queryParams));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> (T) new HoldingsPlanDetails(response);
            default -> (T) response.toString();
        };
    }

    /**
     * Request to get subscription transaction history of a plan <br>
     * No-any params required
     *
     * @return subscription transaction history as {@link ArrayList} of {@link SubscriptionTransaction} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-transaction-history-user_data">
     * Query subscription transaction history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/history/list")
    public ArrayList<SubscriptionTransaction> getSubscriptionTransactionHistory() throws Exception {
        return getSubscriptionTransactionHistory(LIBRARY_OBJECT);
    }

    /**
     * Request to get subscription transaction history of a plan
     *
     * @param format :             return type formatter -> {@link ReturnFormat}
     * @return subscription transaction history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-transaction-history-user_data">
     * Query subscription transaction history (USER_DATA)</a>
     */
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/history/list")
    public <T> T getSubscriptionTransactionHistory(ReturnFormat format) throws Exception {
        return getSubscriptionTransactionHistory(null, format);
    }

    /**
     * Request to get subscription transaction history of a plan
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "planId"} -> identifier of the plan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get results - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get results - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "targetAsset"} -> target asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "planType"} -> type of the plan - [{@link PlanType}]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current query page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return subscription transaction history as {@link ArrayList} of {@link SubscriptionTransaction} custom object
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-transaction-history-user_data">
     * Query subscription transaction history (USER_DATA)</a>
     */
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/history/list")
    public ArrayList<SubscriptionTransaction> getSubscriptionTransactionHistory(Params queryParams) throws Exception {
        return getSubscriptionTransactionHistory(queryParams, LIBRARY_OBJECT);
    }

    /**
     * Request to get subscription transaction history of a plan
     *
     * @param queryParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "planId"} -> identifier of the plan - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "startTime"} -> timestamp in ms to get results - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "endTime"} -> timestamp in ms to get results - [LONG]
     *                           </li>
     *                           <li>
     *                                {@code "targetAsset"} -> target asset value - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "planType"} -> type of the plan - [{@link PlanType}]
     *                           </li>
     *                           <li>
     *                                {@code "size"} -> size of the results, max 100 - [LONG, default 10]
     *                           </li>
     *                           <li>
     *                                {@code "current"} -> current query page - [LONG, default 1]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format       :             return type formatter -> {@link ReturnFormat}
     * @return subscription transaction history as {@code "format"} defines
     * @throws Exception when request has been go wrong -> you can use these methods to get more details about error:
     *                   <ul>
     *                       <li>
     *                           {@link #getErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #getJSONErrorResponse()}
     *                       </li>
     *                       <li>
     *                           {@link #printErrorResponse()}
     *                       </li>
     *                   </ul> using a {@code "try and catch statement"} during runtime, see how to do in {@code "README"} file
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-transaction-history-user_data">
     * Query subscription transaction history (USER_DATA)</a>
     */
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/lending/auto-invest/history/list")
    public <T> T getSubscriptionTransactionHistory(Params queryParams, ReturnFormat format) throws Exception {
        JSONObject response = new JSONObject(sendGetSignedRequest(AUTO_INVEST_HISTORY_LIST_ENDPOINT,
                createTimestampPayload(queryParams)));
        return switch (format) {
            case JSON -> (T) response;
            case LIBRARY_OBJECT -> {
                ArrayList<SubscriptionTransaction> transactions = new ArrayList<>();
                JSONArray list = JsonHelper.getJSONArray(response, "list");
                for (int j = 0; j < list.length(); j++)
                    transactions.add(new SubscriptionTransaction(list.getJSONObject(j)));
                yield (T) transactions;
            }
            default -> (T) response.toString();
        };
    }

}
