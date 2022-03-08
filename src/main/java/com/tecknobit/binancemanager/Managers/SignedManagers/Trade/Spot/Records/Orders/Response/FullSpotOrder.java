package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Orders.Response;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.Fill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code FullOrder} class is useful to format all SpotOrder Binance request in FullOrder format
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#new-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class FullSpotOrder extends ResultSpotOrder {

    private ArrayList<FillSpot> fills;

    public FullSpotOrder(String symbol, long orderId, long orderListId, String clientOrderId, long transactTime, double price,
                         double origQty, double executedQty, double cummulativeQuoteQty, String status, String timeInForce,
                         String type, String side, JSONArray fills) {
        super(symbol, orderId, orderListId, clientOrderId, transactTime, price, origQty, executedQty, cummulativeQuoteQty,
                status, timeInForce, type, side);
        loadFills(fills);
    }

    /** Method to load fills list
     * @param #fillsArray: obtained from Binance's request
     * @return an ArrayList<OrderStatus> with response data
     * **/
    private void loadFills(JSONArray fillsArray) {
        fills = new ArrayList<>();
        for(int j=0; j < fillsArray.length(); j++){
            JSONObject fill = fillsArray.getJSONObject(j);
            fills.add(new FillSpot(fill.getDouble("price"),
                    fill.getDouble("qty"),
                    fill.getDouble("commission"),
                    fill.getString("commissionAsset"),
                    fill.getLong("tradeId")
            ));
        }
    }

    public ArrayList<FillSpot> getFills() {
        return fills;
    }

    public FillSpot getFill(int index){
        return fills.get(index);
    }

    public static class FillSpot extends Fill {

        private final long tradeId;

        public FillSpot(double price, double qty, double commission, String commissionAsset, long tradeId) {
            super(price, qty, commission, commissionAsset);
            this.tradeId = tradeId;
        }

        public long getTradeId() {
            return tradeId;
        }

    }

}

