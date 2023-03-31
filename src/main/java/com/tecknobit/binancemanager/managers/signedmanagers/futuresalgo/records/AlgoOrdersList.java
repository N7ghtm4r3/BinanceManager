package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records.AlgoOrdersList.AlgoOrder;

public class AlgoOrdersList extends BinanceRowsList<AlgoOrder> {

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total  : number of items
     * @param orders :  list of the items
     **/
    public AlgoOrdersList(int total, ArrayList<AlgoOrder> orders) {
        super(total, orders);
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jAlgoOrdersList : list details as {@link JSONObject}
     **/
    public AlgoOrdersList(JSONObject jAlgoOrdersList) {
        super(jAlgoOrdersList);
        for (Object row : hItem.fetchList("orders"))
            rows.add(new AlgoOrder((JSONObject) row));
    }

    public static class AlgoOrder extends AlgoOrderStructure {

        public enum PositionSide {

            BOTH,
            LONG,
            SHORT

        }

        public enum AlgoStatus {

            WORKING,
            CANCELLED

        }

        public enum Urgency {

            LOW,
            MEDIUM,
            HIGH

        }

        private final PositionSide positionSide;
        private final double totalQty;
        private final String clientAlgoId;
        private final long endTime;
        private final AlgoStatus algoStatus;
        private final String algoType;
        private final Urgency urgency;

        public AlgoOrder(long algoId, String symbol, Side side, double executedQty, double executedAmt, double avgPrice,
                         long bookTime, PositionSide positionSide, double totalQty, String clientAlgoId, long endTime,
                         AlgoStatus algoStatus, String algoType, Urgency urgency) {
            super(algoId, symbol, side, executedQty, executedAmt, avgPrice, bookTime);
            this.positionSide = positionSide;
            this.totalQty = totalQty;
            this.clientAlgoId = clientAlgoId;
            this.endTime = endTime;
            this.algoStatus = algoStatus;
            this.algoType = algoType;
            this.urgency = urgency;
        }

        public AlgoOrder(JSONObject jAlgoOrder) {
            super(jAlgoOrder);
            positionSide = PositionSide.valueOf(hItem.getString("positionSide"));
            totalQty = hItem.getDouble("totalQty", 0);
            clientAlgoId = hItem.getString("clientAlgoId");
            endTime = hItem.getLong("endTime", 0);
            algoStatus = AlgoStatus.valueOf(hItem.getString("algoStatus"));
            algoType = hItem.getString("algoType");
            urgency = Urgency.valueOf(hItem.getString("urgency"));
        }

        public PositionSide getPositionSide() {
            return positionSide;
        }

        public double getTotalQty() {
            return totalQty;
        }

        public String getClientAlgoId() {
            return clientAlgoId;
        }

        public long getEndTime() {
            return endTime;
        }

        public AlgoStatus getAlgoStatus() {
            return algoStatus;
        }

        public String getAlgoType() {
            return algoType;
        }

        public Urgency getUrgency() {
            return urgency;
        }

    }

}
