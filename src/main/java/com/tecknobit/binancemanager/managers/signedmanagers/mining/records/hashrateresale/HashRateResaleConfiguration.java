package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.hashrateresale;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

/**
 * The {@code HashRateResaleConfiguration} class is useful to create a hashrate resale configuration
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-hashrate-resale-configuration-user_data">
 * Cancel hashrate resale configuration(USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 **/
public class HashRateResaleConfiguration extends MiningResponse<Boolean> {

    /**
     * Constructor to init {@link HashRateResaleConfiguration} object
     *
     * @param data: hashrate resale configuration result
     **/
    public HashRateResaleConfiguration(boolean data) {
        super(data);
    }

    /**
     * Constructor to init {@link HashRateResaleConfiguration} object
     *
     * @param jHashRateResaleConfiguration: hashrate resale configuration details as {@link JSONObject}
     **/
    public HashRateResaleConfiguration(JSONObject jHashRateResaleConfiguration) {
        super(jHashRateResaleConfiguration);
        data = hItem.getBoolean("data");
    }

}
