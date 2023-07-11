package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.CollateralHistory.CollateralRecord;

public class CollateralHistory extends BinanceRowsList<CollateralRecord> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param rows : list of the items
     */
    public CollateralHistory(ArrayList<CollateralRecord> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public CollateralHistory(int total, ArrayList<CollateralRecord> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jCollateralHistory : list details as {@link JSONObject}
     */
    public CollateralHistory(JSONObject jCollateralHistory) {
        super(jCollateralHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new CollateralRecord((JSONObject) row));
    }

    public static class CollateralRecord extends BinanceItem {

        private final double amount;

        private final String productId;

        private final String asset;

        private final long createTime;

        private final String type;

        private final String productName;

        private final long orderId;

        public CollateralRecord(double amount, String productId, String asset, long createTime, String type,
                                String productName, long orderId) {
            super(null);
            this.amount = amount;
            this.productId = productId;
            this.asset = asset;
            this.createTime = createTime;
            this.type = type;
            this.productName = productName;
            this.orderId = orderId;
        }

        public CollateralRecord(JSONObject jCollateralRecord) {
            super(jCollateralRecord);
            amount = hItem.getDouble("amount", 0);
            productId = hItem.getString("productId");
            asset = hItem.getString("asset");
            createTime = hItem.getLong("createTime", -1);
            type = hItem.getString("type");
            productName = hItem.getString("productName");
            orderId = hItem.getLong("orderId", 0);
        }

        public double getAmount() {
            return amount;
        }

        public String getProductId() {
            return productId;
        }

        public String getAsset() {
            return asset;
        }

        public long getCreateTime() {
            return createTime;
        }

        public String getType() {
            return type;
        }

        public String getProductName() {
            return productName;
        }

        public long getOrderId() {
            return orderId;
        }

    }

}
