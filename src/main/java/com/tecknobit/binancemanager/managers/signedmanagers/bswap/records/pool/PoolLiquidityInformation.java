package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Iterator;

public class PoolLiquidityInformation extends PoolStructure {

    private final long updateTime;
    private final Liquidity liquidity;
    private final Share share;

    public PoolLiquidityInformation(long poolId, String poolName, long updateTime, Liquidity liquidity, Share share) {
        super(poolId, poolName);
        this.updateTime = updateTime;
        this.liquidity = liquidity;
        this.share = share;
    }

    public PoolLiquidityInformation(JSONObject jPoolLiquidityInformation) {
        super(jPoolLiquidityInformation);
        updateTime = hItem.getLong("updateTime", 0);
        liquidity = new Liquidity(hItem.getJSONObject("liquidity"));
        share = new Share(hItem.getJSONObject("share"));
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public Liquidity getLiquidity() {
        return liquidity;
    }

    public Share getShare() {
        return share;
    }

    public static class Liquidity extends BinanceItem {

        private final String baseAsset;
        private final String quoteAsset;
        private final double baseLiquidity;
        private final double quoteLiquidity;

        public Liquidity(String baseAsset, String quoteAsset, double baseLiquidity, double quoteLiquidity) {
            super(null);
            this.baseAsset = baseAsset;
            this.quoteAsset = quoteAsset;
            this.baseLiquidity = baseLiquidity;
            this.quoteLiquidity = quoteLiquidity;
        }

        public Liquidity(JSONObject jLiquidity) {
            super(jLiquidity);
            Iterator<String> keys = jLiquidity.keys();
            baseAsset = keys.next();
            quoteAsset = keys.next();
            baseLiquidity = hItem.getDouble(baseAsset, 0);
            quoteLiquidity = hItem.getDouble(quoteAsset, 0);
        }

        public String getBaseAsset() {
            return baseAsset;
        }

        public String getQuoteAsset() {
            return quoteAsset;
        }

        public double getBaseLiquidity() {
            return baseLiquidity;
        }

        public double getQuoteLiquidity() {
            return quoteLiquidity;
        }

    }

    public static class Share extends BinanceItem {

        private final double shareAmount;
        private final double sharePercentage;
        private final Liquidity asset;

        public Share(double shareAmount, double sharePercentage, Liquidity asset) {
            super(null);
            this.shareAmount = shareAmount;
            this.sharePercentage = sharePercentage;
            this.asset = asset;
        }

        public Share(JSONObject jShare) {
            super(jShare);
            shareAmount = hItem.getDouble("shareAmount", 0);
            sharePercentage = hItem.getDouble("sharePercentage", 0);
            asset = new Liquidity(hItem.getJSONObject("asset"));
        }

        public double getShareAmount() {
            return shareAmount;
        }

        public double getSharePercentage() {
            return sharePercentage;
        }

        public Liquidity getAsset() {
            return asset;
        }

    }

}
