package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PoolConfigure} class is useful to format a pool configure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
 * Get Pool Configure (USER_DATA)</a>
 * @see BinanceItem
 * @see PoolStructure
 */
public class PoolConfigure extends PoolStructure {

    /**
     * {@code updateTime} update time of the pool configure
     */
    private final long updateTime;

    /**
     * {@code liquidity} liquidity of the pool configure
     */
    private final LiquidityConfigure liquidity;

    /**
     * {@code configures} of the pool configure
     */
    private final ArrayList<AssetConfigure> configures;

    /**
     * Constructor to init {@link PoolConfigure} object
     *
     * @param poolId: id of the pool configure
     * @param poolName: name of the pool configure
     * @param updateTime: update time of the pool configure
     * @param liquidity: liquidity of the pool configure
     * @param configures: configures of the pool configure
     */
    public PoolConfigure(long poolId, String poolName, long updateTime, LiquidityConfigure liquidity,
                         ArrayList<AssetConfigure> configures) {
        super(poolId, poolName);
        this.updateTime = updateTime;
        this.liquidity = liquidity;
        this.configures = configures;
    }

    /**
     * Constructor to init {@link PoolConfigure} object
     *
     * @param jPoolConfigure: pool configure details as {@link JSONObject}
     */
    public PoolConfigure(JSONObject jPoolConfigure) {
        super(jPoolConfigure);
        updateTime = hItem.getLong("updateTime", 0);
        liquidity = new LiquidityConfigure(hItem.getJSONObject("liquidity"));
        configures = new ArrayList<>();
        JSONObject jConfigure = hItem.getJSONObject("assetConfigure");
        for (String asset : jConfigure.keySet())
            configures.add(new AssetConfigure(jConfigure.getJSONObject(asset).put("asset", asset)));
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     */
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to get {@link #liquidity} instance <br>
     * No-any params required
     *
     * @return {@link #liquidity} instance as {@link LiquidityConfigure}
     */
    public LiquidityConfigure getLiquidity() {
        return liquidity;
    }

    /**
     * Method to get {@link #configures} instance <br>
     * No-any params required
     *
     * @return {@link #configures} instance as {@link ArrayList} of {@link AssetConfigure}
     */
    public ArrayList<AssetConfigure> getConfigures() {
        return configures;
    }

    /**
     * The {@code LiquidityConfigure} class is useful to format a liquidity configure
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class LiquidityConfigure extends BinanceItem {

        /**
         * {@code constantA} constant A of the liquidity configure
         */
        private final double constantA;

        /**
         * {@code minRedeemShare} min redeem share of the liquidity configure
         */
        private final double minRedeemShare;

        /**
         * {@code slippageTolerance} slippage tolerance of the liquidity configure
         */
        private final double slippageTolerance;

        /**
         * Constructor to init {@link LiquidityConfigure} object
         *
         * @param constantA: constant A of the liquidity configure
         * @param minRedeemShare: min redeem share of the liquidity configure
         * @param slippageTolerance: slippage tolerance of the liquidity configure
         */
        public LiquidityConfigure(double constantA, double minRedeemShare, double slippageTolerance) {
            super(null);
            this.constantA = constantA;
            this.minRedeemShare = minRedeemShare;
            this.slippageTolerance = slippageTolerance;
        }

        /**
         * Constructor to init {@link LiquidityConfigure} object
         *
         * @param jConfigureLiquidity: liquidity configure details as {@link JSONObject}
         */
        public LiquidityConfigure(JSONObject jConfigureLiquidity) {
            super(jConfigureLiquidity);
            constantA = hItem.getDouble("constantA", 0);
            minRedeemShare = hItem.getDouble("minRedeemShare", 0);
            slippageTolerance = hItem.getDouble("slippageTolerance", 0);
        }

        /**
         * Method to get {@link #constantA} instance <br>
         * No-any params required
         *
         * @return {@link #constantA} instance as double
         */
        public double getConstantA() {
            return constantA;
        }

        /**
         * Method to get {@link #constantA} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #constantA} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getConstantA(int decimals) {
            return roundValue(constantA, decimals);
        }

        /**
         * Method to get {@link #minRedeemShare} instance <br>
         * No-any params required
         *
         * @return {@link #minRedeemShare} instance as double
         */
        public double getMinRedeemShare() {
            return minRedeemShare;
        }

        /**
         * Method to get {@link #minRedeemShare} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minRedeemShare} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMinRedeemShare(int decimals) {
            return roundValue(minRedeemShare, decimals);
        }

        /**
         * Method to get {@link #slippageTolerance} instance <br>
         * No-any params required
         *
         * @return {@link #slippageTolerance} instance as double
         */
        public double getSlippageTolerance() {
            return slippageTolerance;
        }

        /**
         * Method to get {@link #slippageTolerance} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #slippageTolerance} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getSlippageTolerance(int decimals) {
            return roundValue(slippageTolerance, decimals);
        }

    }

    /**
     * The {@code AssetConfigure} class is useful to format a asset configure
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class AssetConfigure extends BinanceItem {

        /**
         * {@code asset} of the configuration
         */
        private final String asset;

        /**
         * {@code minAdd} min add of the asset configure
         */
        private final double minAdd;

        /**
         * {@code maxAdd} max add of the asset configure
         */
        private final double maxAdd;

        /**
         * {@code minSwap} min swap of the asset configure
         */
        private final double minSwap;

        /**
         * {@code maxSwap} max swap of the asset configure
         */
        private final double maxSwap;

        /**
         * Constructor to init {@link AssetConfigure} object
         *
         * @param asset: asset of the configuration
         * @param minAdd: min add of the asset configure
         * @param maxAdd: max add of the asset configure
         * @param minSwap: min swap of the asset configure
         * @param maxSwap: max swap of the asset configure
         */
        public AssetConfigure(String asset, double minAdd, double maxAdd, double minSwap, double maxSwap) {
            super(null);
            this.asset = asset;
            this.minAdd = minAdd;
            this.maxAdd = maxAdd;
            this.minSwap = minSwap;
            this.maxSwap = maxSwap;
        }

        /**
         * Constructor to init {@link AssetConfigure} object
         *
         * @param jAssetConfigure: asset configure details as {@link JSONObject}
         */
        public AssetConfigure(JSONObject jAssetConfigure) {
            super(jAssetConfigure);
            asset = hItem.getString("asset");
            minAdd = hItem.getDouble("minAdd", 0);
            maxAdd = hItem.getDouble("maxAdd", 0);
            minSwap = hItem.getDouble("minSwap", 0);
            maxSwap = hItem.getDouble("maxSwap", 0);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #minAdd} instance <br>
         * No-any params required
         *
         * @return {@link #minAdd} instance as double
         */
        public double getMinAdd() {
            return minAdd;
        }

        /**
         * Method to get {@link #minAdd} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minAdd} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMinAdd(int decimals) {
            return roundValue(minAdd, decimals);
        }

        /**
         * Method to get {@link #maxAdd} instance <br>
         * No-any params required
         *
         * @return {@link #maxAdd} instance as double
         */
        public double getMaxAdd() {
            return maxAdd;
        }

        /**
         * Method to get {@link #maxAdd} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxAdd} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaxAdd(int decimals) {
            return roundValue(maxAdd, decimals);
        }

        /**
         * Method to get {@link #minSwap} instance <br>
         * No-any params required
         *
         * @return {@link #minSwap} instance as double
         */
        public double getMinSwap() {
            return minSwap;
        }

        /**
         * Method to get {@link #minSwap} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #minSwap} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMinSwap(int decimals) {
            return roundValue(minSwap, decimals);
        }

        /**
         * Method to get {@link #maxSwap} instance <br>
         * No-any params required
         *
         * @return {@link #maxSwap} instance as double
         */
        public double getMaxSwap() {
            return maxSwap;
        }

        /**
         * Method to get {@link #maxSwap} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maxSwap} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaxSwap(int decimals) {
            return roundValue(maxSwap, decimals);
        }

    }

}

