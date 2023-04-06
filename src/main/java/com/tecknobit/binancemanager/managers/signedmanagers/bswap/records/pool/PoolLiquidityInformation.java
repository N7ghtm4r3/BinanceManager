package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PoolLiquidityInformation} class is useful to format a pool liquidity information
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
 * Get liquidity information of a pool (USER_DATA)</a>
 * @see BinanceItem
 * @see PoolStructure
 **/
public class PoolLiquidityInformation extends PoolStructure {

    /**
     * {@code updateTime} update time of the pool liquidity information
     **/
    private final long updateTime;

    /**
     * {@code liquidity} of the pool liquidity information
     **/
    private final Liquidity liquidity;

    /**
     * {@code share} of the pool liquidity information
     **/
    private final Share share;

    /**
     * Constructor to init {@link PoolLiquidityInformation} object
     *
     * @param poolId: id of the pool liquidity information
     * @param poolName: name of the pool liquidity information
     * @param updateTime: asset rewards of the pool liquidity information
     * @param liquidity: liquidity of the pool liquidity information
     * @param share: share of the pool liquidity information
     **/
    public PoolLiquidityInformation(long poolId, String poolName, long updateTime, Liquidity liquidity, Share share) {
        super(poolId, poolName);
        this.updateTime = updateTime;
        this.liquidity = liquidity;
        this.share = share;
    }

    /**
     * Constructor to init {@link PoolLiquidityInformation} object
     *
     * @param jPoolLiquidityInformation: pool liquidity information details as {@link JSONObject}
     **/
    public PoolLiquidityInformation(JSONObject jPoolLiquidityInformation) {
        super(jPoolLiquidityInformation);
        updateTime = hItem.getLong("updateTime", 0);
        liquidity = new Liquidity(hItem.getJSONObject("liquidity"));
        share = new Share(hItem.getJSONObject("share"));
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to get {@link #liquidity} instance <br>
     * No-any params required
     *
     * @return {@link #liquidity} instance as {@link Liquidity}
     **/
    public Liquidity getLiquidity() {
        return liquidity;
    }

    /**
     * Method to get {@link #share} instance <br>
     * No-any params required
     *
     * @return {@link #share} instance as {@link Share}
     **/
    public Share getShare() {
        return share;
    }

    /**
     * The {@code Liquidity} class is useful to format a liquidity for the pool information
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class Liquidity extends BinanceItem {

        /**
         * {@code baseAsset} base asset of the liquidity
         **/
        private final String baseAsset;

        /**
         * {@code quoteAsset} quote asset of the liquidity
         **/
        private final String quoteAsset;

        /**
         * {@code baseLiquidity} base of the liquidity
         **/
        private final double baseLiquidity;

        /**
         * {@code quoteLiquidity} quote of the liquidity
         **/
        private final double quoteLiquidity;

        /**
         * Constructor to init {@link Liquidity} object
         *
         * @param baseAsset: base asset of the liquidity
         * @param quoteAsset: quote asset of the liquidity
         * @param baseLiquidity: base of the liquidity
         * @param quoteLiquidity: quote of the liquidity
         **/
        public Liquidity(String baseAsset, String quoteAsset, double baseLiquidity, double quoteLiquidity) {
            super(null);
            this.baseAsset = baseAsset;
            this.quoteAsset = quoteAsset;
            this.baseLiquidity = baseLiquidity;
            this.quoteLiquidity = quoteLiquidity;
        }

        /**
         * Constructor to init {@link Liquidity} object
         *
         * @param jLiquidity: liquidity details as {@link JSONObject}
         **/
        public Liquidity(JSONObject jLiquidity) {
            super(jLiquidity);
            Iterator<String> keys = jLiquidity.keys();
            baseAsset = keys.next();
            quoteAsset = keys.next();
            baseLiquidity = hItem.getDouble(baseAsset, 0);
            quoteLiquidity = hItem.getDouble(quoteAsset, 0);
        }

        /**
         * Method to get {@link #baseAsset} instance <br>
         * No-any params required
         *
         * @return {@link #baseAsset} instance as {@link String}
         **/
        public String getBaseAsset() {
            return baseAsset;
        }

        /**
         * Method to get {@link #quoteAsset} instance <br>
         * No-any params required
         *
         * @return {@link #quoteAsset} instance as {@link String}
         **/
        public String getQuoteAsset() {
            return quoteAsset;
        }

        /**
         * Method to get {@link #baseLiquidity} instance <br>
         * No-any params required
         *
         * @return {@link #baseLiquidity} instance as double
         **/
        public double getBaseLiquidity() {
            return baseLiquidity;
        }

        /**
         * Method to get {@link #baseLiquidity} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #baseLiquidity} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getBaseLiquidity(int decimals) {
            return roundValue(baseLiquidity, decimals);
        }

        /**
         * Method to get {@link #quoteLiquidity} instance <br>
         * No-any params required
         *
         * @return {@link #quoteLiquidity} instance as double
         **/
        public double getQuoteLiquidity() {
            return quoteLiquidity;
        }

        /**
         * Method to get {@link #quoteLiquidity} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #quoteLiquidity} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getQuoteLiquidity(int decimals) {
            return roundValue(quoteLiquidity, decimals);
        }

    }

    /**
     * The {@code Share} class is useful to format a Share for the pool information
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     **/
    public static class Share extends BinanceItem {

        /**
         * {@code shareAmount} amount of the share
         **/
        private final double shareAmount;

        /**
         * {@code sharePercentage} share percentage of the share
         **/
        private final double sharePercentage;

        /**
         * {@code asset} asset of the share
         **/
        private final Liquidity asset;

        /**
         * Constructor to init {@link Share} object
         *
         * @param shareAmount: amount of the share
         * @param sharePercentage: share percentage of the share
         * @param asset: asset of the share
         **/
        public Share(double shareAmount, double sharePercentage, Liquidity asset) {
            super(null);
            this.shareAmount = shareAmount;
            this.sharePercentage = sharePercentage;
            this.asset = asset;
        }

        /**
         * Constructor to init {@link Share} object
         *
         * @param jShare: share details as {@link JSONObject}
         **/
        public Share(JSONObject jShare) {
            super(jShare);
            shareAmount = hItem.getDouble("shareAmount", 0);
            sharePercentage = hItem.getDouble("sharePercentage", 0);
            asset = new Liquidity(hItem.getJSONObject("asset"));
        }

        /**
         * Method to get {@link #shareAmount} instance <br>
         * No-any params required
         *
         * @return {@link #shareAmount} instance as double
         **/
        public double getShareAmount() {
            return shareAmount;
        }

        /**
         * Method to get {@link #shareAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #shareAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getShareAmount(int decimals) {
            return roundValue(shareAmount, decimals);
        }

        /**
         * Method to get {@link #sharePercentage} instance <br>
         * No-any params required
         *
         * @return {@link #sharePercentage} instance as double
         **/
        public double getSharePercentage() {
            return sharePercentage;
        }

        /**
         * Method to get {@link #sharePercentage} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #sharePercentage} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getSharePercentage(int decimals) {
            return roundValue(sharePercentage, decimals);
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link Liquidity}
         **/
        public Liquidity getAsset() {
            return asset;
        }

    }

}
