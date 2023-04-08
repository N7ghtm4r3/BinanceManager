package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CollateralAssetsData.AssetData;

public class CollateralAssetsData extends BinanceRowsList<AssetData> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total  : number of items
     * @param assets :  list of the items
     **/
    public CollateralAssetsData(int total, ArrayList<AssetData> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList : list details as {@link JSONObject}
     **/
    public CollateralAssetsData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new AssetData((JSONObject) row));
    }

    public static class AssetData extends BinanceItem {

        private final String collateralCoin;
        private final double initialLTV;
        private final double marginCallLTV;
        private final double liquidationLTV;
        private final double maxLimit;
        private final int vipLevel;

        public AssetData(String collateralCoin, double initialLTV, double marginCallLTV, double liquidationLTV,
                         double maxLimit, int vipLevel) {
            super(null);
            this.collateralCoin = collateralCoin;
            this.initialLTV = initialLTV;
            this.marginCallLTV = marginCallLTV;
            this.liquidationLTV = liquidationLTV;
            this.maxLimit = maxLimit;
            this.vipLevel = vipLevel;
        }

        public AssetData(JSONObject jAssetData) {
            super(jAssetData);
            collateralCoin = hItem.getString("collateralCoin");
            initialLTV = hItem.getDouble("initialLTV", 0);
            marginCallLTV = hItem.getDouble("marginCallLTV", 0);
            liquidationLTV = hItem.getDouble("liquidationLTV", 0);
            maxLimit = hItem.getDouble("maxLimit", 0);
            vipLevel = hItem.getInt("vipLevel", 0);
        }

        public String getCollateralCoin() {
            return collateralCoin;
        }

        public double getInitialLTV() {
            return initialLTV;
        }

        public double getMarginCallLTV() {
            return marginCallLTV;
        }

        public double getLiquidationLTV() {
            return liquidationLTV;
        }

        public double getMaxLimit() {
            return maxLimit;
        }

        public int getVipLevel() {
            return vipLevel;
        }

    }

}
