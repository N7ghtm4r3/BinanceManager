package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code SwapPool} class is useful to format a swap pool
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#list-all-swap-pools-market_data">
 * List All Swap Pools (MARKET_DATA)</a>
 * @see BinanceItem
 * @see PoolStructure
 */
public class SwapPool extends PoolStructure {

    /**
     * {@code assets} of the swap pool
     */
    private final ArrayList<String> assets;

    /**
     * Constructor to init {@link SwapPool} object
     *
     * @param poolId:   id of the swap pool
     * @param poolName: name of the swap pool
     * @param assets:   assets of the swap pool
     */
    public SwapPool(long poolId, String poolName, ArrayList<String> assets) {
        super(poolId, poolName);
        this.assets = assets;
    }

    /**
     * Constructor to init {@link SwapPool} object
     *
     * @param jSwapPool: swap pool details as {@link JSONObject}
     */
    public SwapPool(JSONObject jSwapPool) {
        super(jSwapPool);
        assets = new ArrayList<>();
        for (Object asset : hItem.fetchList("assets"))
            assets.add((String) asset);
    }

    /**
     * Method to get {@link #assets} instance <br>
     * No-any params required
     *
     * @return {@link #assets} instance as {@link ArrayList} of {@link String}
     */
    public ArrayList<String> getAssets() {
        return assets;
    }

}
