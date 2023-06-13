package com.tecknobit.binancemanager.managers.signedmanagers.nft.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * The {@code NFTAssetsList} class is useful to create a NFT assets list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-nft-asset-user_data">
 * Get NFT Asset (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class NFTAssetsList extends BinanceRowsList<NFTAsset> {

    /**
     * Constructor to init {@link NFTAssetsList} object
     *
     * @param total  : number of assets
     * @param assets :  list of the assets
     */
    public NFTAssetsList(int total, ArrayList<NFTAsset> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link NFTAssetsList}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public NFTAssetsList(JSONObject jList) {
        super(jList);
        for (Object asset : hItem.fetchList("list"))
            rows.add(new NFTAsset((JSONObject) asset));
    }

}
