package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TargetAssetList} class is useful to format a {@code Binance}'s target asset list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-target-asset-list-user_data">
 * Get target asset list(USER_DATA)</a>
 * @see BinanceItem
 */
public class TargetAssetList extends BinanceItem {

    /**
     * {@code targetAssets} list of the target assets
     */
    private final ArrayList<String> targetAssets;

    /**
     * {@code autoInvestAssetList} list of the auto invest asset list
     */
    private final ArrayList<AutoInvestAsset> autoInvestAssetList;

    /**
     * Constructor to init a {@link TargetAssetList} object
     *
     * @param targetAssets:        list of the target assets
     * @param autoInvestAssetList: list of the auto invest asset list
     */
    public TargetAssetList(ArrayList<String> targetAssets, ArrayList<AutoInvestAsset> autoInvestAssetList) {
        super(null);
        this.targetAssets = targetAssets;
        this.autoInvestAssetList = autoInvestAssetList;
    }

    /**
     * Constructor to init a {@link TargetAssetList} object
     *
     * @param jTargetAssetList: target asset list details as {@link JSONObject}
     */
    public TargetAssetList(JSONObject jTargetAssetList) {
        super(jTargetAssetList);
        targetAssets = returnStringsList(hItem.getJSONArray("targetAssets"));
        autoInvestAssetList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("autoInvestAssetList", new ArrayList<>());
        for (JSONObject item : jList)
            autoInvestAssetList.add(new AutoInvestAsset(item));
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
     * Method to get {@link #autoInvestAssetList} instance <br>
     * No-any params required
     *
     * @return {@link #autoInvestAssetList} instance as {@link ArrayList} of {@link AutoInvestAsset}
     */
    public ArrayList<AutoInvestAsset> getAutoInvestAssetList() {
        return autoInvestAssetList;
    }

    /**
     * The {@code AutoInvestAsset} class is useful to format a {@code Binance}'s auto invest asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class AutoInvestAsset extends BinanceItem {

        /**
         * {@code targetAsset} target asset value
         */
        private final String targetAsset;

        /**
         * {@code roiAndDimensionTypeList} roi and dimension type list
         */
        private final ArrayList<RoiAndDimensionType> roiAndDimensionTypeList;

        /**
         * Constructor to init a {@link AutoInvestAsset} object
         *
         * @param targetAsset:             target asset value
         * @param roiAndDimensionTypeList: roi and dimension type list
         */
        public AutoInvestAsset(String targetAsset, ArrayList<RoiAndDimensionType> roiAndDimensionTypeList) {
            super(null);
            this.targetAsset = targetAsset;
            this.roiAndDimensionTypeList = roiAndDimensionTypeList;
        }

        /**
         * Constructor to init a {@link AutoInvestAsset} object
         *
         * @param jAutoInvestAsset: auto invest asset details as {@link JSONObject}
         */
        public AutoInvestAsset(JSONObject jAutoInvestAsset) {
            super(jAutoInvestAsset);
            targetAsset = hItem.getString("targetAsset");
            roiAndDimensionTypeList = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("roiAndDimensionTypeList", new ArrayList<>());
            for (JSONObject item : jList)
                roiAndDimensionTypeList.add(new RoiAndDimensionType(item));
        }

        /**
         * Method to get {@link #targetAsset} instance <br>
         * No-any params required
         *
         * @return {@link #targetAsset} instance as {@link String}
         */
        public String getTargetAsset() {
            return targetAsset;
        }

        /**
         * Method to get {@link #roiAndDimensionTypeList} instance <br>
         * No-any params required
         *
         * @return {@link #roiAndDimensionTypeList} instance as {@link ArrayList} of {@link RoiAndDimensionType}
         */
        public ArrayList<RoiAndDimensionType> getRoiAndDimensionTypeList() {
            return roiAndDimensionTypeList;
        }

        /**
         * The {@code RoiAndDimensionType} class is useful to format a {@code Binance}'s roi and dimension type
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         */
        public static class RoiAndDimensionType extends BinanceItem {

            /**
             * {@code DimensionUnit} list of available dimension units
             */
            public enum DimensionUnit {

                /**
                 * {@code day} dimension unit
                 */
                day,

                /**
                 * {@code week} dimension unit
                 */
                week,

                /**
                 * {@code month} dimension unit
                 */
                month,

                /**
                 * {@code year} dimension unit
                 */
                year

            }

            /**
             * {@code simulateRoi} simulate ROI value
             */
            private final double simulateRoi;

            /**
             * {@code dimensionValue} dimension value
             */
            private final int dimensionValue;

            /**
             * {@code dimensionUnit} dimension unit
             */
            private final DimensionUnit dimensionUnit;

            /**
             * Constructor to init a {@link RoiAndDimensionType} object
             *
             * @param simulateRoi:    simulate ROI value
             * @param dimensionValue: dimension value
             * @param dimensionUnit:  dimension unit
             */
            public RoiAndDimensionType(double simulateRoi, int dimensionValue, DimensionUnit dimensionUnit) {
                super(null);
                this.simulateRoi = simulateRoi;
                this.dimensionValue = dimensionValue;
                this.dimensionUnit = dimensionUnit;
            }

            /**
             * Constructor to init a {@link RoiAndDimensionType} object
             *
             * @param jRoiAndDimensionType: roi and dimension type details as {@link JSONObject}
             */
            public RoiAndDimensionType(JSONObject jRoiAndDimensionType) {
                super(jRoiAndDimensionType);
                simulateRoi = hItem.getDouble("simulateRoi", 0);
                dimensionValue = hItem.getInt("dimensionValue", 0);
                dimensionUnit = DimensionUnit.valueOf(hItem.getString("dimensionUnit"));
            }

            /**
             * Method to get {@link #simulateRoi} instance <br>
             * No-any params required
             *
             * @return {@link #simulateRoi} instance as double
             */
            public double getSimulateRoi() {
                return simulateRoi;
            }

            /**
             * Method to get {@link #simulateRoi} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #simulateRoi} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             */
            public double getSimulateRoi(int decimals) {
                return roundValue(simulateRoi, decimals);
            }

            /**
             * Method to get {@link #dimensionValue} instance <br>
             * No-any params required
             *
             * @return {@link #dimensionValue} instance as int
             */
            public int getDimensionValue() {
                return dimensionValue;
            }

            /**
             * Method to get {@link #dimensionUnit} instance <br>
             * No-any params required
             *
             * @return {@link #dimensionUnit} instance as {@link DimensionUnit}
             */
            public DimensionUnit getDimensionUnit() {
                return dimensionUnit;
            }

        }

    }

}
