package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code FullOrder} class is useful to format all Order Binance request in FullOrder format
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class FullOrder extends ResultOrder{

    private ArrayList<Fill> fills;

    public FullOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime, double price,
                     double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                     String type, String side, JSONArray fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFills(fills);
    }

    /** Method to load fills list
     * @param #fillsArray: obtained from Binance's request
     * return an ArrayList<OrderStatus> with response data
     * **/
    private void loadFills(JSONArray fillsArray) {
        fills = new ArrayList<>();
        for(int j=0; j < fillsArray.length(); j++){
            JSONObject fill = fillsArray.getJSONObject(j);
            fills.add(new Fill(fill.getDouble("price"),
                    fill.getDouble("qty"),
                    fill.getDouble("commission"),
                    fill.getString("commissionAsset"),
                    fill.getLong("tradeId")
            ));
        }
    }

    public ArrayList<Fill> getFills() {
        return fills;
    }

    public Fill getFill(int index){
        return fills.get(index);
    }

    /**
     *  The {@code Fill} class is useful to obtain and format Fill object
     *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
     * **/

    public static class Fill {

        private final double price;
        private final double qty;
        private final double commission;
        private final String commissionAsset;
        private final long tradeId;

        public Fill(double price, double qty, double commission, String commissionAsset, long tradeId) {
            this.price = price;
            this.qty = qty;
            this.commission = commission;
            this.commissionAsset = commissionAsset;
            this.tradeId = tradeId;
        }

        public double getPrice() {
            return price;
        }

        public double getQty() {
            return qty;
        }

        public double getCommission() {
            return commission;
        }

        public String getCommissionAsset() {
            return commissionAsset;
        }

        public long getTradeId() {
            return tradeId;
        }

    }

}

