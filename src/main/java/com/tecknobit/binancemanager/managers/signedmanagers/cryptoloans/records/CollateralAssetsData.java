package com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.cryptoloans.records.CollateralAssetsData.AssetData;

/**
 * The {@code CollateralAssetsData} class is useful to create a collateral assets data details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-assets-data-user_data">
 * Get Collateral Assets Data (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class CollateralAssetsData extends BinanceRowsList<AssetData> {

    /**
     * Constructor to init {@link CollateralAssetsData} object
     *
     * @param total  : number of assets
     * @param assets :  list of the assets
     */
    public CollateralAssetsData(int total, ArrayList<AssetData> assets) {
        super(total, assets);
    }

    /**
     * Constructor to init {@link CollateralAssetsData}
     *
     * @param jList : list details as {@link JSONObject}
     */
    public CollateralAssetsData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new AssetData((JSONObject) row));
    }

    /**
     * The {@code AssetData} class is useful to create an asset data
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class AssetData extends BinanceItem {

        /**
         * {@code collateralCoin} collateral coin of the asset data
         */
        private final String collateralCoin;

        /**
         * {@code initialLTV} initial LTV of the asset data
         */
        private final double initialLTV;

        /**
         * {@code marginCallLTV} margin call LTV of the asset data
         */
        private final double marginCallLTV;

        /**
         * {@code liquidationLTV} liquidation LTV of the asset data
         */
        private final double liquidationLTV;

        /**
         * {@code maxLimit} max limit of the asset data
         */
        private final double maxLimit;

        /**
         * {@code vipLevel} vip level of the asset data
         */
        private final int vipLevel;

        /**
         * Constructor to init {@link AssetData} object
         *
         * @param collateralCoin: collateral coin of the asset data
         * @param initialLTV:     initial LTV of the asset data
         * @param marginCallLTV:  margin call LTV of the asset data
         * @param liquidationLTV: liquidation LTV of the asset data
         * @param maxLimit:       max limit of the asset data
         * @param vipLevel:       vip level of the asset data
         */
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

        /**
         * Constructor to init {@link AssetData} object
         *
         * @param jAssetData: asset data details as {@link JSONObject}
         */
        public AssetData(JSONObject jAssetData) {
            super(jAssetData);
            collateralCoin = hItem.getString("collateralCoin");
            initialLTV = hItem.getDouble("initialLTV", 0);
            marginCallLTV = hItem.getDouble("marginCallLTV", 0);
            liquidationLTV = hItem.getDouble("liquidationLTV", 0);
            maxLimit = hItem.getDouble("maxLimit", 0);
            vipLevel = hItem.getInt("vipLevel", 0);
        }

        /**
         * Method to get {@link #collateralCoin} instance <br>
         * No-any params required
         *
         * @return {@link #collateralCoin} instance as {@link String}
         */
        public String getCollateralCoin() {
            return collateralCoin;
        }

        /**
         * Method to get {@link #initialLTV} instance <br>
         * No-any params required
         *
         * @return {@link #initialLTV} instance as double
         */
        public double getInitialLTV() {
            return initialLTV;
        }

        /**
         * Method to get {@link #initialLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #initialLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getInitialLTV(int decimals) {
            return roundValue(initialLTV, decimals);
        }

        /**
         * Method to get {@link #marginCallLTV} instance <br>
         * No-any params required
         *
         * @return {@link #marginCallLTV} instance as double
         */
        public double getMarginCallLTV() {
            return marginCallLTV;
        }

        /**
         * Method to get {@link #marginCallLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginCallLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMarginCallLTV(int decimals) {
            return roundValue(marginCallLTV, decimals);
        }

        /**
         * Method to get {@link #marginCallLTV} instance <br>
         * No-any params required
         *
         * @return {@link #marginCallLTV} instance as double
         */
        public double getLiquidationLTV() {
            return liquidationLTV;
        }

        /**
         * Method to get {@link #marginCallLTV} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginCallLTV} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getLiquidationLTV(int decimals) {
            return roundValue(marginCallLTV, decimals);
        }

        /**
         * Method to get {@link #maxLimit} instance <br>
         * No-any params required
         *
         * @return {@link #maxLimit} instance as double
         */
        public double getMaxLimit() {
            return maxLimit;
        }

        /**
         * Method to get {@link #maxLimit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxLimit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaxLimit(int decimals) {
            return roundValue(maxLimit, decimals);
        }

        /**
         * Method to get {@link #vipLevel} instance <br>
         * No-any params required
         *
         * @return {@link #vipLevel} instance as int
         */
        public int getVipLevel() {
            return vipLevel;
        }

    }

}
