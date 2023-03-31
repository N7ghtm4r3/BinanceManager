package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.TimeInForce;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records.SubAlgoOrdersList.SubAlgoOrder;

public class SubAlgoOrdersList extends BinanceRowsList<SubAlgoOrder> {

    private final double executedQty;
    private final double executedAmt;

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total     : number of items
     * @param subOrders :  list of the items
     **/
    public SubAlgoOrdersList(int total, ArrayList<SubAlgoOrder> subOrders, double executedQty, double executedAmt) {
        super(total, subOrders);
        this.executedQty = executedQty;
        this.executedAmt = executedAmt;
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jSubAlgoOrdersList : list details as {@link JSONObject}
     **/
    public SubAlgoOrdersList(JSONObject jSubAlgoOrdersList) {
        super(jSubAlgoOrdersList);
        executedQty = hItem.getDouble("executedQty", 0);
        executedAmt = hItem.getDouble("executedAmt", 0);
        for (Object row : hItem.fetchList("subOrders"))
            rows.add(new SubAlgoOrder((JSONObject) row));
    }

    public double getExecutedQty() {
        return executedQty;
    }

    public double getExecutedAmt() {
        return executedAmt;
    }

    public static class SubAlgoOrder extends AlgoOrderStructure {

        private final long orderId;
        private final Status orderStatus;
        private final double feeAmt;
        private final String feeAsset;
        private final long subId;
        private final TimeInForce timeInForce;
        private final double origQty;

        public SubAlgoOrder(long algoId, String symbol, Order.Side side, double executedQty, double executedAmt,
                            double avgPrice, long bookTime, long orderId, Status orderStatus, double feeAmt,
                            String feeAsset, long subId, TimeInForce timeInForce, double origQty) {
            super(algoId, symbol, side, executedQty, executedAmt, avgPrice, bookTime);
            this.orderId = orderId;
            this.orderStatus = orderStatus;
            this.feeAmt = feeAmt;
            this.feeAsset = feeAsset;
            this.subId = subId;
            this.timeInForce = timeInForce;
            this.origQty = origQty;
        }

        public SubAlgoOrder(JSONObject jSubAlgoOrder) {
            super(jSubAlgoOrder);
            orderId = hItem.getLong("orderId", 0);
            orderStatus = Status.valueOf(hItem.getString("orderStatus"));
            feeAmt = hItem.getDouble("feeAmt", 0);
            feeAsset = hItem.getString("feeAsset");
            subId = hItem.getLong("subId", 0);
            timeInForce = TimeInForce.valueOf(hItem.getString("timeInForce"));
            origQty = hItem.getDouble("origQty", 0);
        }

        public long getOrderId() {
            return orderId;
        }

        public Status getOrderStatus() {
            return orderStatus;
        }

        public double getFeeAmt() {
            return feeAmt;
        }

        public String getFeeAsset() {
            return feeAsset;
        }

        public long getSubId() {
            return subId;
        }

        public TimeInForce getTimeInForce() {
            return timeInForce;
        }

        public double getOrigQty() {
            return origQty;
        }

    }

}
