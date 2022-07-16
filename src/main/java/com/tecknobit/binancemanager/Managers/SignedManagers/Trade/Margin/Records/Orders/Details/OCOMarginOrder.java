package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import org.json.JSONObject;

/**
 *  The {@code OCOMarginOrder} class is useful to format all Binance Margin OCO Order request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OCOMarginOrder extends ComposedMarginOrderDetails {

    public static final String SIDE_EFFECT_NO_SIDE_EFFECT = "NO_SIDE_EFFECT";
    public static final String SIDE_EFFECT_MARGIN_BUY = "MARGIN_BUY";
    public static final String SIDE_EFFECT_AUTO_REPAY = "AUTO_REPAY";
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

    /** Method to assemble a OCOMarginOrder
     * @param ocoOrder: obtained from Binance's request
     * retrun OCOMarginOrder object
     * **/
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
