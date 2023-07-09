package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code SourceTargetAsset} class is useful to format a {@code Binance}'s source & target asset
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-all-source-asset-and-target-asset-user_data">
 * Query all source asset and target asset(USER_DATA)</a>
 * @see BinanceItem
 */
public class SourceTargetAsset extends BinanceItem {

    /**
     * {@code targetAssets} target assets list
     */
    private final ArrayList<String> targetAssets;

    /**
     * {@code sourceAssets} source assets list
     */
    private final ArrayList<String> sourceAssets;

    /**
     * Constructor to init a {@link SourceTargetAsset} object
     *
     * @param targetAssets: target assets list
     * @param sourceAssets: source assets list
     */
    public SourceTargetAsset(ArrayList<String> targetAssets, ArrayList<String> sourceAssets) {
        super(null);
        this.targetAssets = targetAssets;
        this.sourceAssets = sourceAssets;
    }

    /**
     * Constructor to init a {@link SourceTargetAsset} object
     *
     * @param jSourceTargetAsset: source & target asset details as {@link JSONObject}
     */
    public SourceTargetAsset(JSONObject jSourceTargetAsset) {
        super(jSourceTargetAsset);
        targetAssets = returnStringsList(hItem.getJSONArray("targetAssets"));
        sourceAssets = returnStringsList(hItem.getJSONArray("sourceAssets"));
    }

    /**
     * Method to get {@link #targetAssets} instance <br>
     * No-any params required
     *
     * @return {@link #targetAssets} instance as {@link ArrayList} of {@link String}
     */
    public ArrayList<String> getTargetAssets() {
        return targetAssets;
    }

    /**
     * Method to get {@link #sourceAssets} instance <br>
     * No-any params required
     *
     * @return {@link #sourceAssets} instance as {@link ArrayList} of {@link String}
     */
    public ArrayList<String> getSourceAssets() {
        return sourceAssets;
    }

}
