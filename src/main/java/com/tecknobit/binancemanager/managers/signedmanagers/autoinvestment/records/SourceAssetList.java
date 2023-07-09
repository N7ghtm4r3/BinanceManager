package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SourceAssetList} class is useful to format a {@code Binance}'s source asset list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-source-asset-list-user_data">
 * Query source asset list(USER_DATA)</a>
 * @see BinanceItem
 */
public class SourceAssetList extends BinanceItem {

    /**
     * {@code feeRate} rate of the fee
     */
    private final double feeRate;

    /**
     * {@code sourceAssets} the source assets list
     */
    private final ArrayList<SourceAsset> sourceAssets;

    /**
     * Constructor to init a {@link SourceAssetList} object
     *
     * @param feeRate:      rate of the fee
     * @param sourceAssets: the source assets list
     */
    public SourceAssetList(double feeRate, ArrayList<SourceAsset> sourceAssets) {
        super(null);
        this.feeRate = feeRate;
        this.sourceAssets = sourceAssets;
    }

    /**
     * Constructor to init a {@link SourceAssetList} object
     *
     * @param jSourceAssetList: source asset list details as {@link JSONObject}
     */
    public SourceAssetList(JSONObject jSourceAssetList) {
        super(jSourceAssetList);
        feeRate = hItem.getDouble("feeRate", 0);
        sourceAssets = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("sourceAssets", new ArrayList<>());
        for (JSONObject item : jList)
            sourceAssets.add(new SourceAsset(item));
    }

    /**
     * Method to get {@link #feeRate} instance <br>
     * No-any params required
     *
     * @return {@link #feeRate} instance as double
     */
    public double getFeeRate() {
        return feeRate;
    }

    /**
     * Method to get {@link #feeRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #feeRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getFeeRate(int decimals) {
        return roundValue(feeRate, decimals);
    }

    /**
     * Method to get {@link #sourceAssets} instance <br>
     * No-any params required
     *
     * @return {@link #sourceAssets} instance as {@link ArrayList} of {@link SourceAsset}
     */
    public ArrayList<SourceAsset> getSourceAssets() {
        return sourceAssets;
    }

    /**
     * The {@code SourceAsset} class is useful to format a {@code Binance}'s source asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class SourceAsset extends BinanceItem {

        /**
         * {@code sourceAsset} source asset value
         */
        private final String sourceAsset;

        /**
         * {@code assetMinAmount} min amount of the asset
         */
        private final double assetMinAmount;

        /**
         * {@code assetMaxAmount} max amount of the asset
         */
        private final double assetMaxAmount;

        /**
         * {@code scale} value
         */
        private final int scale;

        /**
         * {@code flexibleAmount} flexible amount of the asset
         */
        private final double flexibleAmount;

        /**
         * Constructor to init a {@link SourceAsset} object
         *
         * @param sourceAsset:    source asset value
         * @param assetMinAmount: min amount of the asset
         * @param assetMaxAmount: max amount of the asset
         * @param scale:          scale value
         * @param flexibleAmount: flexible amount of the asset
         */
        public SourceAsset(String sourceAsset, double assetMinAmount, double assetMaxAmount, int scale, double flexibleAmount) {
            super(null);
            this.sourceAsset = sourceAsset;
            this.assetMinAmount = assetMinAmount;
            this.assetMaxAmount = assetMaxAmount;
            this.scale = scale;
            this.flexibleAmount = flexibleAmount;
        }

        /**
         * Constructor to init a {@link SourceAsset} object
         *
         * @param jSourceAsset: source asset details as {@link JSONObject}
         */
        public SourceAsset(JSONObject jSourceAsset) {
            super(jSourceAsset);
            sourceAsset = hItem.getString("sourceAsset");
            assetMinAmount = hItem.getDouble("assetMinAmount", 0);
            assetMaxAmount = hItem.getDouble("assetMaxAmount", 0);
            scale = hItem.getInt("scale", 0);
            flexibleAmount = hItem.getDouble("flexibleAmount", 0);
        }

        /**
         * Method to get {@link #sourceAsset} instance <br>
         * No-any params required
         *
         * @return {@link #sourceAsset} instance as {@link String}
         */
        public String getSourceAsset() {
            return sourceAsset;
        }

        /**
         * Method to get {@link #assetMinAmount} instance <br>
         * No-any params required
         *
         * @return {@link #assetMinAmount} instance as double
         */
        public double getAssetMinAmount() {
            return assetMinAmount;
        }

        /**
         * Method to get {@link #assetMinAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #assetMinAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAssetMinAmount(int decimals) {
            return roundValue(assetMinAmount, decimals);
        }

        /**
         * Method to get {@link #assetMaxAmount} instance <br>
         * No-any params required
         *
         * @return {@link #assetMaxAmount} instance as double
         */
        public double getAssetMaxAmount() {
            return assetMaxAmount;
        }

        /**
         * Method to get {@link #assetMaxAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #assetMaxAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAssetMaxAmount(int decimals) {
            return roundValue(assetMaxAmount, decimals);
        }

        /**
         * Method to get {@link #scale} instance <br>
         * No-any params required
         *
         * @return {@link #scale} instance as int
         */
        public int getScale() {
            return scale;
        }

        /**
         * Method to get {@link #flexibleAmount} instance <br>
         * No-any params required
         *
         * @return {@link #flexibleAmount} instance as double
         */
        public double getFlexibleAmount() {
            return flexibleAmount;
        }

        /**
         * Method to get {@link #flexibleAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #flexibleAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getFlexibleAmount(int decimals) {
            return roundValue(flexibleAmount, decimals);
        }

    }

}
