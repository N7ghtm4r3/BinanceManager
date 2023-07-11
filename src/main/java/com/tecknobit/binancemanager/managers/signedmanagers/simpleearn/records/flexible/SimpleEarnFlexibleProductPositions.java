package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.SimpleEarnFlexibleProductPositions.SimpleEarnFlexibleProductPosition;

public class SimpleEarnFlexibleProductPositions extends BinanceRowsList<SimpleEarnFlexibleProductPosition> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public SimpleEarnFlexibleProductPositions(ArrayList<SimpleEarnFlexibleProductPosition> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public SimpleEarnFlexibleProductPositions(int total, ArrayList<SimpleEarnFlexibleProductPosition> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jSimpleEarnFlexibleProductPositions : list details as {@link JSONObject}
     */
    public SimpleEarnFlexibleProductPositions(JSONObject jSimpleEarnFlexibleProductPositions) {
        super(jSimpleEarnFlexibleProductPositions);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new SimpleEarnFlexibleProductPosition((JSONObject) row));
    }

    public static class SimpleEarnFlexibleProductPosition extends BinanceItem {

        private final double totalAmount;

        private final TierAnnualPercentageRate tierAnnualPercentageRate;

        private final double latestAnnualPercentageRate;

        private final double yesterdayAirdropPercentageRate;

        private final String asset;

        private final String airDropAsset;

        private final boolean canRedeem;

        private final double collateralAmount;

        private final String productId;

        private final double yesterdayRealTimeRewards;

        private final double cumulativeBonusRewards;

        private final double cumulativeRealTimeRewards;

        private final double cumulativeTotalRewards;

        private final boolean autoSubscribe;

        public SimpleEarnFlexibleProductPosition(double totalAmount, TierAnnualPercentageRate tierAnnualPercentageRate,
                                                 double latestAnnualPercentageRate, double yesterdayAirdropPercentageRate,
                                                 String asset, String airDropAsset, boolean canRedeem,
                                                 double collateralAmount, String productId,
                                                 double yesterdayRealTimeRewards, double cumulativeBonusRewards,
                                                 double cumulativeRealTimeRewards, double cumulativeTotalRewards,
                                                 boolean autoSubscribe) {
            super(null);
            this.totalAmount = totalAmount;
            this.tierAnnualPercentageRate = tierAnnualPercentageRate;
            this.latestAnnualPercentageRate = latestAnnualPercentageRate;
            this.yesterdayAirdropPercentageRate = yesterdayAirdropPercentageRate;
            this.asset = asset;
            this.airDropAsset = airDropAsset;
            this.canRedeem = canRedeem;
            this.collateralAmount = collateralAmount;
            this.productId = productId;
            this.yesterdayRealTimeRewards = yesterdayRealTimeRewards;
            this.cumulativeBonusRewards = cumulativeBonusRewards;
            this.cumulativeRealTimeRewards = cumulativeRealTimeRewards;
            this.cumulativeTotalRewards = cumulativeTotalRewards;
            this.autoSubscribe = autoSubscribe;
        }

        public SimpleEarnFlexibleProductPosition(JSONObject jSimpleEarnFlexibleProductPosition) {
            super(jSimpleEarnFlexibleProductPosition);
            totalAmount = hItem.getDouble("totalAmount", 0);
            tierAnnualPercentageRate = new TierAnnualPercentageRate(hItem.getJSONObject("tierAnnualPercentageRate"));
            latestAnnualPercentageRate = hItem.getDouble("latestAnnualPercentageRate", 0);
            yesterdayAirdropPercentageRate = hItem.getDouble("yesterdayAirdropPercentageRate", 0);
            asset = hItem.getString("asset");
            airDropAsset = hItem.getString("airDropAsset");
            canRedeem = hItem.getBoolean("canRedeem");
            collateralAmount = hItem.getDouble("collateralAmount", 0);
            productId = hItem.getString("productId");
            yesterdayRealTimeRewards = hItem.getDouble("yesterdayRealTimeRewards", 0);
            cumulativeBonusRewards = hItem.getDouble("cumulativeBonusRewards", 0);
            cumulativeRealTimeRewards = hItem.getDouble("cumulativeRealTimeRewards", 0);
            cumulativeTotalRewards = hItem.getDouble("cumulativeTotalRewards", 0);
            autoSubscribe = hItem.getBoolean("autoSubscribe");
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public TierAnnualPercentageRate getTierAnnualPercentageRate() {
            return tierAnnualPercentageRate;
        }

        public double getLatestAnnualPercentageRate() {
            return latestAnnualPercentageRate;
        }

        public double getYesterdayAirdropPercentageRate() {
            return yesterdayAirdropPercentageRate;
        }

        public String getAsset() {
            return asset;
        }

        public String getAirDropAsset() {
            return airDropAsset;
        }

        public boolean isCanRedeem() {
            return canRedeem;
        }

        public double getCollateralAmount() {
            return collateralAmount;
        }

        public String getProductId() {
            return productId;
        }

        public double getYesterdayRealTimeRewards() {
            return yesterdayRealTimeRewards;
        }

        public double getCumulativeBonusRewards() {
            return cumulativeBonusRewards;
        }

        public double getCumulativeRealTimeRewards() {
            return cumulativeRealTimeRewards;
        }

        public double getCumulativeTotalRewards() {
            return cumulativeTotalRewards;
        }

        public boolean isAutoSubscribe() {
            return autoSubscribe;
        }

    }

}
