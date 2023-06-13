package com.tecknobit.binancemanager.managers.signedmanagers.mining.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code MiningResponse} class is useful to create a mining response structure
 *
 * @param <T> data contained by the structure
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-algorithm-market_data">
 *             Acquiring Algorithm (MARKET_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
 *             Acquiring CoinName (MARKET_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-detail-miner-list-user_data">
 *             Request for Detail Miner List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
 *             Request for Miner List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
 *             Earnings List(USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
 *             Extra Bonus List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
 *             Hashrate Resale List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-detail-user_data">
 *             Hashrate Resale Detail (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-request-user_data">
 *             Hashrate Resale Request (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-hashrate-resale-configuration-user_data">
 *             Cancel hashrate resale configuration(USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#statistic-list-user_data">
 *             Statistic List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#account-list-user_data">
 *             Account List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#mining-account-earning-user_data">
 *             Mining Account Earning (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceResponse
 */
@Structure
public abstract class MiningResponse<T> extends BinanceItem implements BinanceResponse {

    /**
     * {@code HashRateType} list of available hashrate types
     */
    public enum HashRateType {

        /**
         * {@code H_hashrate} hourly hashrate type
         */
        H_hashrate,

        /**
         * {@code D_hashrate} daily hashrate type
         */
        D_hashrate

    }

    /**
     * {@code data} contained by the structure
     */
    protected T data;

    /**
     * Constructor to init {@link MiningResponse} object
     *
     * @param data: data contained by the structure
     */
    public MiningResponse(T data) {
        super(null);
        this.data = data;
    }

    /**
     * Constructor to init {@link MiningResponse} object
     *
     * @param jMiningResponse: mining response details as {@link JSONObject}
     */
    public MiningResponse(JSONObject jMiningResponse) {
        super(jMiningResponse);
    }

    /**
     * Method to get {@link #data} instance <br>
     * No-any params required
     *
     * @return {@link #data} instance as {@link T}
     */
    public T getData() {
        return data;
    }

    /**
     * Method to get error code <br>
     * No-any params required
     *
     * @return code of error as int
     * *
     * @implSpec if code error is not present in {@code "Binance"}'s response will be returned -1 as default
     */
    @Override
    public int getCode() {
        if (hItem != null)
            return hItem.getInt("code", 0);
        return 0;
    }

    /**
     * Method to get error message <br>
     * No-any params required
     *
     * @return message of error as {@link String}
     * *
     * @implSpec if message error is not present in {@code "Binance"}'s response will be returned null as default
     */
    @Override
    public String getMessage() {
        if (hItem != null)
            return hItem.getString("msg");
        return null;
    }

}
