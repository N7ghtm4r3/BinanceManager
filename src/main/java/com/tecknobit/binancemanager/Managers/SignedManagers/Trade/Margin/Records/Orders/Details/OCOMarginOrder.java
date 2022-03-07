package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import org.json.JSONObject;

public class OCOMarginOrder extends ComposedMarginOrderDetails {

    private final double marginBuyBorrowAmount;
    private final double marginBuyBorrowAsset;

    public OCOMarginOrder(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                          String listClientOrderId, long transactionTime, String symbol, boolean isIsolated,
                          double marginBuyBorrowAmount, double marginBuyBorrowAsset, JSONObject jsonObject) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                isIsolated, jsonObject);
        this.marginBuyBorrowAmount = marginBuyBorrowAmount;
        this.marginBuyBorrowAsset = marginBuyBorrowAsset;
    }

    public double getMarginBuyBorrowAmount() {
        return marginBuyBorrowAmount;
    }

    public double getMarginBuyBorrowAsset() {
        return marginBuyBorrowAsset;
    }

    public static OCOMarginOrder assembleOCOMarginOrder(JSONObject ocoOrder){
        return new OCOMarginOrder(ocoOrder.getLong("orderListId"),
                ocoOrder.getString("contingencyType"),
                ocoOrder.getString("listStatusType"),
                ocoOrder.getString("listOrderStatus"),
                ocoOrder.getString("listClientOrderId"),
                ocoOrder.getLong("transactionTime"),
                ocoOrder.getString("symbol"),
                ocoOrder.getBoolean("isIsolated"),
                ocoOrder.getDouble("marginBuyBorrowAmount"),
                ocoOrder.getDouble("marginBuyBorrowAsset"),
                ocoOrder
        );
    }

}
