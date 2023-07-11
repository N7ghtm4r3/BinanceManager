package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.SimpleEarnProductStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.TierAnnualPercentageRate.getInstance;
import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.SimpleEarnFlexibleProductList.SimpleEarnFlexibleProduct;

public class SimpleEarnFlexibleProductList extends BinanceRowsList<SimpleEarnFlexibleProduct> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public SimpleEarnFlexibleProductList(ArrayList<SimpleEarnFlexibleProduct> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public SimpleEarnFlexibleProductList(int total, ArrayList<SimpleEarnFlexibleProduct> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jSimpleEarnFlexibleProductList : list details as {@link JSONObject}
     */
    public SimpleEarnFlexibleProductList(JSONObject jSimpleEarnFlexibleProductList) {
        super(jSimpleEarnFlexibleProductList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new SimpleEarnFlexibleProduct((JSONObject) row));
    }

    public static class SimpleEarnFlexibleProduct extends SimpleEarnProductStructure {

        private final double latestAnnualPercentageRate;

        private final TierAnnualPercentageRate tierAnnualPercentageRate;

        private final double airDropPercentageRate;

        private final boolean canPurchase;

        private final boolean canRedeem;

        private final boolean hot;

        private final double minPurchaseAmount;

        private final String productId;

        public SimpleEarnFlexibleProduct(String asset, boolean isSoldOut, SimpleEarnStatus status, long subscriptionStartTime,
                                         double latestAnnualPercentageRate, TierAnnualPercentageRate tierAnnualPercentageRate,
                                         double airDropPercentageRate, boolean canPurchase, boolean canRedeem, boolean hot,
                                         double minPurchaseAmount, String productId) {
            super(asset, isSoldOut, status, subscriptionStartTime);
            this.latestAnnualPercentageRate = latestAnnualPercentageRate;
            this.tierAnnualPercentageRate = tierAnnualPercentageRate;
            this.airDropPercentageRate = airDropPercentageRate;
            this.canPurchase = canPurchase;
            this.canRedeem = canRedeem;
            this.hot = hot;
            this.minPurchaseAmount = minPurchaseAmount;
            this.productId = productId;
        }

        public SimpleEarnFlexibleProduct(JSONObject jSimpleEarnFlexibleProduct) {
            super(jSimpleEarnFlexibleProduct);
            latestAnnualPercentageRate = hItem.getDouble("latestAnnualPercentageRate", 0);
            tierAnnualPercentageRate = getInstance(hItem.getJSONObject("tierAnnualPercentageRate"));
            airDropPercentageRate = hItem.getDouble("airDropPercentageRate", 0);
            canPurchase = hItem.getBoolean("canPurchase");
            canRedeem = hItem.getBoolean("canRedeem");
            hot = hItem.getBoolean("hot");
            minPurchaseAmount = hItem.getDouble("minPurchaseAmount", 0);
            productId = hItem.getString("productId");
        }

        public double getLatestAnnualPercentageRate() {
            return latestAnnualPercentageRate;
        }

        public TierAnnualPercentageRate getTierAnnualPercentageRate() {
            return tierAnnualPercentageRate;
        }

        public double getAirDropPercentageRate() {
            return airDropPercentageRate;
        }

        public boolean isCanPurchase() {
            return canPurchase;
        }

        public boolean isCanRedeem() {
            return canRedeem;
        }

        public boolean isHot() {
            return hot;
        }

        public double getMinPurchaseAmount() {
            return minPurchaseAmount;
        }

        public String getProductId() {
            return productId;
        }

    }

}
