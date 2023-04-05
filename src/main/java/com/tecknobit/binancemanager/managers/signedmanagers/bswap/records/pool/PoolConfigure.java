package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class PoolConfigure extends PoolStructure {

    private final long updateTime;
    private final ConfigureLiquidity liquidity;
    private final ArrayList<AssetConfigure> configures;

    public PoolConfigure(long poolId, String poolName, long updateTime, ConfigureLiquidity liquidity, ArrayList<AssetConfigure> configures) {
        super(poolId, poolName);
        this.updateTime = updateTime;
        this.liquidity = liquidity;
        this.configures = configures;
    }

    public PoolConfigure(JSONObject jPoolConfigure) {
        super(jPoolConfigure);
        updateTime = hItem.getLong("updateTime", 0);
        liquidity = new ConfigureLiquidity(hItem.getJSONObject("liquidity"));
        configures = new ArrayList<>();
        JSONObject jConfigure = hItem.getJSONObject("assetConfigure");
        for (String asset : jConfigure.keySet())
            configures.add(new AssetConfigure(jConfigure.getJSONObject(asset).put("asset", asset)));
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public ConfigureLiquidity getLiquidity() {
        return liquidity;
    }

    public ArrayList<AssetConfigure> getConfigures() {
        return configures;
    }

    public static class ConfigureLiquidity extends BinanceItem {

        private final double constantA;
        private final double minRedeemShare;
        private final double slippageTolerance;

        public ConfigureLiquidity(double constantA, double minRedeemShare, double slippageTolerance) {
            super(null);
            this.constantA = constantA;
            this.minRedeemShare = minRedeemShare;
            this.slippageTolerance = slippageTolerance;
        }

        public ConfigureLiquidity(JSONObject jConfigureLiquidity) {
            super(jConfigureLiquidity);
            constantA = hItem.getDouble("constantA", 0);
            minRedeemShare = hItem.getDouble("minRedeemShare", 0);
            slippageTolerance = hItem.getDouble("slippageTolerance", 0);
        }

        public double getConstantA() {
            return constantA;
        }

        public double getMinRedeemShare() {
            return minRedeemShare;
        }

        public double getSlippageTolerance() {
            return slippageTolerance;
        }

    }

    public static class AssetConfigure extends BinanceItem {

        private final String asset;
        private final double minAdd;
        private final double maxAdd;
        private final double minSwap;
        private final double maxSwap;

        public AssetConfigure(String asset, double minAdd, double maxAdd, double minSwap, double maxSwap) {
            super(null);
            this.asset = asset;
            this.minAdd = minAdd;
            this.maxAdd = maxAdd;
            this.minSwap = minSwap;
            this.maxSwap = maxSwap;
        }

        public AssetConfigure(JSONObject jAssetConfigure) {
            super(jAssetConfigure);
            asset = hItem.getString("asset");
            minAdd = hItem.getDouble("minAdd", 0);
            maxAdd = hItem.getDouble("maxAdd", 0);
            minSwap = hItem.getDouble("minSwap", 0);
            maxSwap = hItem.getDouble("maxSwap", 0);
        }

        public String getAsset() {
            return asset;
        }

        public double getMinAdd() {
            return minAdd;
        }

        public double getMaxAdd() {
            return maxAdd;
        }

        public double getMinSwap() {
            return minSwap;
        }

        public double getMaxSwap() {
            return maxSwap;
        }

    }

}

