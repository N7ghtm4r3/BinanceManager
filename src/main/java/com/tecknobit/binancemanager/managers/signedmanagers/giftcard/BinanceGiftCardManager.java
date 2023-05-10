package com.tecknobit.binancemanager.managers.signedmanagers.giftcard;

import com.tecknobit.apimanager.annotations.RequestPath;
import com.tecknobit.apimanager.annotations.RequestWeight;
import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.binancemanager.exceptions.SystemException;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.signedmanagers.BinanceSignedManager;
import com.tecknobit.binancemanager.managers.signedmanagers.giftcard.records.*;
import org.json.JSONObject;

import java.io.IOException;

import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.GET;
import static com.tecknobit.apimanager.apis.APIRequest.RequestMethod.POST;
import static com.tecknobit.binancemanager.managers.BinanceManager.ReturnFormat.LIBRARY_OBJECT;

/**
 * The {@code BinanceGiftCardManager} class is useful to manage gift card endpoints
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#binance-gift-card-endpoints">
 * Binance Gift Card Endpoints</a>
 * @see BinanceManager
 * @see BinanceSignedManager
 **/
public class BinanceGiftCardManager extends BinanceSignedManager {

    /**
     * {@code GIFTCARD_CREATE_CODE_ENDPOINT} is constant for GIFTCARD_CREATE_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_CREATE_CODE_ENDPOINT = "/sapi/v1/giftcard/createCode";

    /**
     * {@code GIFTCARD_BUY_CODE_ENDPOINT} is constant for GIFTCARD_BUY_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_BUY_CODE_ENDPOINT = "/sapi/v1/giftcard/buyCode";

    /**
     * {@code GIFTCARD_REDEEM_CODE_ENDPOINT} is constant for GIFTCARD_REDEEM_CODE_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_REDEEM_CODE_ENDPOINT = "/sapi/v1/giftcard/redeemCode";

    /**
     * {@code GIFTCARD_VERIFY_ENDPOINT} is constant for GIFTCARD_VERIFY_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_VERIFY_ENDPOINT = "/sapi/v1/giftcard/verify";

    /**
     * {@code GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT} is constant for GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_RSA_PUBLIC_KEY_ENDPOINT = "/sapi/v1/giftcard/cryptography/rsa-public-key";

    /**
     * {@code GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT} is constant for GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT's endpoint
     **/
    public static final String GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT = "/sapi/v1/giftcard/buyCode/token-limit";

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param timeout             :             custom timeout for request
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String defaultErrorMessage, int timeout,
                                  String apiKey, String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint        base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param defaultErrorMessage : custom error to show when is not a request error
     * @param apiKey              your api key
     * @param secretKey           your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String defaultErrorMessage, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, defaultErrorMessage, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param timeout      :             custom timeout for request
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, int timeout, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, timeout, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager}
     *
     * @param baseEndpoint base endpoint to work on, insert {@code "null"} to auto-search that's working
     * @param apiKey       your api key
     * @param secretKey    your secret key
     **/
    public BinanceGiftCardManager(String baseEndpoint, String apiKey,
                                  String secretKey) throws SystemException, IOException {
        super(baseEndpoint, apiKey, secretKey);
    }

    /**
     * Constructor to init a {@link BinanceGiftCardManager} <br>
     * No-any params required
     *
     * @throws IllegalArgumentException when a parameterized constructor has not been called before this constructor
     * @apiNote this constructor is useful to instantiate a new {@link BinanceSignedManager}'s manager without re-insert
     * the credentials and is useful in those cases if you need to use different manager at the same time:
     * <pre>
     *     {@code
     *        //You need to insert all credentials requested
     *        BinanceSignedManager firstManager = new BinanceSignedManager("apiKey", "apiSecret");
     *        //You don't need to insert all credentials to make manager work
     *        BinanceSignedManager secondManager = new BinanceSignedManager(); //same credentials used
     *     }
     * </pre>
     **/
    public BinanceGiftCardManager() {
        super();
    }

    /**
     * Request to create a Binance giftcard
     *
     * @param token:  the token type contained in the Binance Gift Card
     * @param amount: the amount of the token contained in the Binance Gift Card
     * @return Binance giftcard as {@link TokenGiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
     * Create a single-token gift card (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/createCode")
    public TokenGiftCard createSingleTokenGiftCard(String token, double amount) throws Exception {
        return createSingleTokenGiftCard(token, amount, LIBRARY_OBJECT);
    }

    /**
     * Request to create a Binance giftcard
     *
     * @param token:  the token type contained in the Binance Gift Card
     * @param amount: the amount of the token contained in the Binance Gift Card
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
     * Create a single-token gift card (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/createCode")
    public <T> T createSingleTokenGiftCard(String token, double amount, ReturnFormat format) throws Exception {
        return createSingleTokenGiftCard(token, amount, -1, format);
    }

    /**
     * Request to create a Binance giftcard
     *
     * @param token:      the token type contained in the Binance Gift Card
     * @param amount:     the amount of the token contained in the Binance Gift Card
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return Binance giftcard as {@link TokenGiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
     * Create a single-token gift card (USER_DATA)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/createCode")
    public TokenGiftCard createSingleTokenGiftCard(String token, double amount, long recvWindow) throws Exception {
        return createSingleTokenGiftCard(token, amount, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to create a Binance giftcard
     *
     * @param token:      the token type contained in the Binance Gift Card
     * @param amount:     the amount of the token contained in the Binance Gift Card
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-single-token-gift-card-user_data">
     * Create a single-token gift card (USER_DATA)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/createCode")
    public <T> T createSingleTokenGiftCard(String token, double amount, long recvWindow,
                                           ReturnFormat format) throws Exception {
        Params payload = new Params();
        payload.addParam("token", token);
        payload.addParam("amount", amount);
        if (recvWindow != -1)
            payload.addParam("recvWindow", recvWindow);
        return returnTokenGiftCard(sendPostSignedRequest(GIFTCARD_CREATE_CODE_ENDPOINT, payload), format);
    }

    /**
     * Request to create a dual-token
     *
     * @param baseToken:       the token you want to pay
     * @param faceToken:       the token you want to buy
     * @param baseTokenAmount: the base token asset quantity
     * @return dual-token as {@link TokenGiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
     * Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/buyCode")
    public TokenGiftCard createDualTokenGiftCard(String baseToken, String faceToken,
                                                 double baseTokenAmount) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, LIBRARY_OBJECT);
    }

    /**
     * Request to create a dual-token
     *
     * @param baseToken:       the token you want to pay
     * @param faceToken:       the token you want to buy
     * @param baseTokenAmount: the base token asset quantity
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return dual-token as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
     * Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/buyCode")
    public <T> T createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount,
                                         ReturnFormat format) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, null, format);
    }

    /**
     * Request to create a dual-token
     *
     * @param baseToken:       the token you want to pay
     * @param faceToken:       the token you want to buy
     * @param baseTokenAmount: the base token asset quantity
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                               <li>
     *                                    {@code "discount"} -> stablecoin-denominated card discount percentage, scale
     *                                    should be less than 6 - [DOUBLE]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                    - [LONG, default 5000]
     *                               </li>
     *                         </ul>
     * @return dual-token as {@link TokenGiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
     * Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/buyCode")
    public TokenGiftCard createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount,
                                                 Params extraParams) throws Exception {
        return createDualTokenGiftCard(baseToken, faceToken, baseTokenAmount, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to create a dual-token
     *
     * @param baseToken:       the token you want to pay
     * @param faceToken:       the token you want to buy
     * @param baseTokenAmount: the base token asset quantity
     * @param extraParams:     additional params of the request, keys accepted are:
     *                         <ul>
     *                               <li>
     *                                    {@code "discount"} -> stablecoin-denominated card discount percentage, scale
     *                                    should be less than 6 - [DOUBLE]
     *                               </li>
     *                               <li>
     *                                    {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                    - [LONG, default 5000]
     *                               </li>
     *                         </ul>
     * @param format:          return type formatter -> {@link ReturnFormat}
     * @return dual-token as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#create-a-dual-token-gift-card-fixed-value-discount-feature-trade">
     * Create a dual-token gift card (fixed value, discount feature) (TRADE)</a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/buyCode")
    public <T> T createDualTokenGiftCard(String baseToken, String faceToken, double baseTokenAmount, Params extraParams,
                                         ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("baseToken", baseToken);
        extraParams.addParam("faceToken", faceToken);
        extraParams.addParam("baseTokenAmount", baseTokenAmount);
        return returnTokenGiftCard(sendPostSignedRequest(GIFTCARD_BUY_CODE_ENDPOINT, extraParams), format);
    }

    /**
     * Method to create a token giftcard
     *
     * @param tokenGiftCardResponse: obtained from Binance's response
     * @param format:                return type formatter -> {@link ReturnFormat}
     * @return token giftcard as {@code "format"} defines
     **/
    @Returner
    private <T> T returnTokenGiftCard(String tokenGiftCardResponse, ReturnFormat format) {
        switch (format) {
            case JSON:
                return (T) new JSONObject(tokenGiftCardResponse);
            case LIBRARY_OBJECT:
                return (T) new TokenGiftCard(new JSONObject(tokenGiftCardResponse));
            default:
                return (T) tokenGiftCardResponse;
        }
    }

    /**
     * Request to redeem a Binance giftcard
     *
     * @param code: redemption code of Binance Gift Card to be redeemed
     * @return Binance giftcard as {@link GiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
     * Redeem a Binance Gift Card (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/redeemCode")
    public GiftCard redeemBinanceGiftCard(String code) throws Exception {
        return redeemBinanceGiftCard(code, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a Binance giftcard
     *
     * @param code:   redemption code of Binance Gift Card to be redeemed
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
     * Redeem a Binance Gift Card (USER_DATA) </a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/redeemCode")
    public <T> T redeemBinanceGiftCard(String code, ReturnFormat format) throws Exception {
        return redeemBinanceGiftCard(code, null, format);
    }

    /**
     * Request to redeem a Binance giftcard
     *
     * @param code:        redemption code of Binance Gift Card to be redeemed
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "externalUid"} -> each external unique ID represents a unique user on the
     *                                partner platform. The function helps you to identify the redemption behavior of
     *                                different users, such as redemption frequency and amount. It also helps risk and
     *                                limit control of a single account, such as daily limit on redemption volume,
     *                                frequency, and incorrect number of entries. This will also prevent a single user
     *                                account reach the partner's daily redemption limits. We strongly recommend you to
     *                                use this feature and transfer us the User ID of your users if you have different
     *                                users redeeming Binance Gift Cards on your platform. To protect user data privacy,
     *                                you may choose to transfer the user id in any desired format
     *                                (max. 400 characters) - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @return Binance giftcard as {@link GiftCard} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
     * Redeem a Binance Gift Card (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/redeemCode")
    public GiftCard redeemBinanceGiftCard(String code, Params extraParams) throws Exception {
        return redeemBinanceGiftCard(code, extraParams, LIBRARY_OBJECT);
    }

    /**
     * Request to redeem a Binance giftcard
     *
     * @param code:        redemption code of Binance Gift Card to be redeemed
     * @param extraParams: additional params of the request, keys accepted are:
     *                     <ul>
     *                           <li>
     *                                {@code "externalUid"} -> each external unique ID represents a unique user on the
     *                                partner platform. The function helps you to identify the redemption behavior of
     *                                different users, such as redemption frequency and amount. It also helps risk and
     *                                limit control of a single account, such as daily limit on redemption volume,
     *                                frequency, and incorrect number of entries. This will also prevent a single user
     *                                account reach the partner's daily redemption limits. We strongly recommend you to
     *                                use this feature and transfer us the User ID of your users if you have different
     *                                users redeeming Binance Gift Cards on your platform. To protect user data privacy,
     *                                you may choose to transfer the user id in any desired format
     *                                (max. 400 characters) - [STRING]
     *                           </li>
     *                           <li>
     *                                {@code "recvWindow"} -> request is valid for in ms, must be less than 60000
     *                                - [LONG, default 5000]
     *                           </li>
     *                     </ul>
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-a-binance-gift-card-user_data">
     * Redeem a Binance Gift Card (USER_DATA) </a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = POST, path = "/sapi/v1/giftcard/redeemCode")
    public <T> T redeemBinanceGiftCard(String code, Params extraParams, ReturnFormat format) throws Exception {
        if (extraParams == null)
            extraParams = new Params();
        extraParams.addParam("code", code);
        String giftCardResponse = sendPostSignedRequest(GIFTCARD_REDEEM_CODE_ENDPOINT, extraParams);
        switch (format) {
            case JSON:
                return (T) new JSONObject(giftCardResponse);
            case LIBRARY_OBJECT:
                return (T) new GiftCard(new JSONObject(giftCardResponse));
            default:
                return (T) giftCardResponse;
        }
    }

    /**
     * Request to verify whether the Binance Gift Card is valid or not by entering Gift Card Number
     *
     * @param referenceNo: the giftcard number
     * @return Binance giftcard verification as {@link GiftCardVerification} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
     * Verify Binance Gift Card by Gift Card Number (USER_DATA) </a>
     * @implNote please note that if you enter the wrong Gift Card Number 5 times within an hour, you will no longer be
     * able to verify any Gift Card Number for that hour
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/verify")
    public GiftCardVerification verifyBinanceGiftCard(String referenceNo) throws Exception {
        return verifyBinanceGiftCard(referenceNo, LIBRARY_OBJECT);
    }

    /**
     * Request to verify whether the Binance Gift Card is valid or not by entering Gift Card Number
     *
     * @param referenceNo: the giftcard number
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard verification as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
     * Verify Binance Gift Card by Gift Card Number (USER_DATA) </a>
     * @implNote please note that if you enter the wrong Gift Card Number 5 times within an hour, you will no longer be
     * able to verify any Gift Card Number for that hour
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/verify")
    public <T> T verifyBinanceGiftCard(String referenceNo, ReturnFormat format) throws Exception {
        return verifyBinanceGiftCard(referenceNo, -1, LIBRARY_OBJECT);
    }

    /**
     * Request to verify whether the Binance Gift Card is valid or not by entering Gift Card Number
     *
     * @param referenceNo: the giftcard number
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @return Binance giftcard verification as {@link GiftCardVerification} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
     * Verify Binance Gift Card by Gift Card Number (USER_DATA) </a>
     * @implNote please note that if you enter the wrong Gift Card Number 5 times within an hour, you will no longer be
     * able to verify any Gift Card Number for that hour
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/verify")
    public GiftCardVerification verifyBinanceGiftCard(String referenceNo, long recvWindow) throws Exception {
        return verifyBinanceGiftCard(referenceNo, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to verify whether the Binance Gift Card is valid or not by entering Gift Card Number
     *
     * @param referenceNo: the giftcard number
     * @param recvWindow:  request is valid for in ms, must be less than 60000
     * @param format:      return type formatter -> {@link ReturnFormat}
     * @return Binance giftcard verification as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#verify-binance-gift-card-by-gift-card-number-user_data">
     * Verify Binance Gift Card by Gift Card Number (USER_DATA) </a>
     * @implNote please note that if you enter the wrong Gift Card Number 5 times within an hour, you will no longer be
     * able to verify any Gift Card Number for that hour
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/verify")
    public <T> T verifyBinanceGiftCard(String referenceNo, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampQuery(recvWindow);
        query.addParam("referenceNo", referenceNo);
        String verifyResponse = sendGetSignedRequest(GIFTCARD_VERIFY_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(verifyResponse);
            case LIBRARY_OBJECT:
                return (T) new GiftCardVerification(new JSONObject(verifyResponse));
            default:
                return (T) verifyResponse;
        }
    }

    /**
     * Request to fetch the RSA Public Key <br>
     * No-any params required
     *
     * @return Binance RSA Public Key as {@link BinanceRSAPublicKey} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
     * Fetch RSA Public Key (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/cryptography/rsa-public-key")
    public BinanceRSAPublicKey fetchRSAPublicKey() throws Exception {
        return fetchRSAPublicKey(LIBRARY_OBJECT);
    }

    /**
     * Request to fetch the RSA Public Key
     *
     * @param format: return type formatter -> {@link ReturnFormat}
     * @return Binance RSA Public Key as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
     * Fetch RSA Public Key (USER_DATA) </a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/cryptography/rsa-public-key")
    public <T> T fetchRSAPublicKey(ReturnFormat format) throws Exception {
        return fetchRSAPublicKey(-1, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch the RSA Public Key
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return Binance RSA Public Key as {@link BinanceRSAPublicKey} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
     * Fetch RSA Public Key (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/cryptography/rsa-public-key")
    public BinanceRSAPublicKey fetchRSAPublicKey(long recvWindow) throws Exception {
        return fetchRSAPublicKey(recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to fetch the RSA Public Key
     *
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return Binance RSA Public Key as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-rsa-public-key-user_data">
     * Fetch RSA Public Key (USER_DATA) </a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/cryptography/rsa-public-key")
    public <T> T fetchRSAPublicKey(long recvWindow, ReturnFormat format) throws Exception {
        String keyResponse = sendGetSignedRequest(GIFTCARD_VERIFY_ENDPOINT, createTimestampQuery(recvWindow));
        switch (format) {
            case JSON:
                return (T) new JSONObject(keyResponse);
            case LIBRARY_OBJECT:
                return (T) new BinanceRSAPublicKey(new JSONObject(keyResponse));
            default:
                return (T) keyResponse;
        }
    }

    /**
     * Request to verify which tokens are available for you to create Stablecoin-Denominated gift cards as mentioned in
     * section 2 and its limitation
     *
     * @param baseToken: the token you want to pay
     * @return token limits as {@link TokenLimits} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
     * Fetch Token Limit (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/buyCode/token-limit")
    public TokenLimits fetchTokenLimit(String baseToken) throws Exception {
        return fetchTokenLimit(baseToken, LIBRARY_OBJECT);
    }

    /**
     * Request to verify which tokens are available for you to create Stablecoin-Denominated gift cards as mentioned in
     * section 2 and its limitation
     *
     * @param baseToken: the token you want to pay
     * @param format:    return type formatter -> {@link ReturnFormat}
     * @return token limits as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
     * Fetch Token Limit (USER_DATA) </a>
     **/
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/buyCode/token-limit")
    public <T> T fetchTokenLimit(String baseToken, ReturnFormat format) throws Exception {
        return fetchTokenLimit(baseToken, -1, LIBRARY_OBJECT);
    }

    /**
     * Request to verify which tokens are available for you to create Stablecoin-Denominated gift cards as mentioned in
     * section 2 and its limitation
     *
     * @param baseToken:  the token you want to pay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @return token limits as {@link TokenLimits} custom object
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
     * Fetch Token Limit (USER_DATA) </a>
     **/
    @Wrapper
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/buyCode/token-limit")
    public TokenLimits fetchTokenLimit(String baseToken, long recvWindow) throws Exception {
        return fetchTokenLimit(baseToken, recvWindow, LIBRARY_OBJECT);
    }

    /**
     * Request to verify which tokens are available for you to create Stablecoin-Denominated gift cards as mentioned in
     * section 2 and its limitation
     *
     * @param baseToken:  the token you want to pay
     * @param recvWindow: request is valid for in ms, must be less than 60000
     * @param format:     return type formatter -> {@link ReturnFormat}
     * @return token limits as {@code "format"} defines
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
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#fetch-token-limit-user_data">
     * Fetch Token Limit (USER_DATA) </a>
     **/
    @Returner
    @RequestWeight(weight = "1(IP)")
    @RequestPath(method = GET, path = "/sapi/v1/giftcard/buyCode/token-limit")
    public <T> T fetchTokenLimit(String baseToken, long recvWindow, ReturnFormat format) throws Exception {
        Params query = createTimestampQuery(recvWindow);
        query.addParam("baseToken", baseToken);
        String limitsResponse = sendGetSignedRequest(GIFTCARD_BUY_CODE_TOKEN_LIMIT_ENDPOINT, query);
        switch (format) {
            case JSON:
                return (T) new JSONObject(limitsResponse);
            case LIBRARY_OBJECT:
                return (T) new TokenLimits(new JSONObject(limitsResponse));
            default:
                return (T) limitsResponse;
        }
    }

    /**
     * Method to create a query string with {@code "timestamp"} and in some cases also the {@code "recvWindow"}
     *
     * @param recvWindow: will be inserted in the query only if different from {@code "-1"}
     * @return query as {@link Params}
     **/
    private Params createTimestampQuery(long recvWindow) {
        Params query = new Params();
        if (recvWindow != -1)
            query.addParam("recvWindow", recvWindow);
        query.addParam("timestamp", getServerTime());
        return query;
    }

}
