package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

/**
 * The {@code HashrateResaleRequest} class is useful to create a hashrate resale request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-request-user_data">
 * Hashrate Resale Request (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class HashrateResaleRequest extends MiningResponse<Integer> {

    /**
     * Constructor to init {@link HashrateResaleRequest} object
     *
     * @param data: hashrate resale request
     */
    public HashrateResaleRequest(int data) {
        super(data);
    }

    /**
     * Constructor to init {@link HashrateResaleRequest} object
     *
     * @param jHashRateResaleRequest: hashrate resale request details as {@link JSONObject}
     */
    public HashrateResaleRequest(JSONObject jHashRateResaleRequest) {
        super(jHashRateResaleRequest);
        data = hItem.getInt("data", 0);
    }

}
