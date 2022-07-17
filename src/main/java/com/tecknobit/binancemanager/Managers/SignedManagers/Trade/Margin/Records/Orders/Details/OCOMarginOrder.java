package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import org.json.JSONObject;

/**
 *  The {@code OCOMarginOrder} class is useful to format all Binance Margin OCO Order request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class OCOMarginOrder extends ComposedMarginOrderDetails {

    /**
     * {@code SIDE_EFFECT_NO_SIDE_EFFECT} is constant for no side effect option
     * **/
    public static final String SIDE_EFFECT_NO_SIDE_EFFECT = "NO_SIDE_EFFECT";

    /**
     * {@code SIDE_EFFECT_MARGIN_BUY} is constant for margin buy option
     * **/
    public static final String SIDE_EFFECT_MARGIN_BUY = "MARGIN_BUY";

    /**
     * {@code SIDE_EFFECT_AUTO_REPAY} is constant for margin auto repay option
     * **/
    public static final String SIDE_EFFECT_AUTO_REPAY = "AUTO_REPAY";

    /**
     * {@code marginBuyBorrowAmount} is instance that memorizes margin buy borrow amount
     * **/
    private final double marginBuyBorrowAmount;

    /**
     * {@code marginBuyBorrowAsset} is instance that memorizes margin buy borrow asset
     * **/
    private final String marginBuyBorrowAsset;

    /** Constructor to init {@link OCOMarginOrder} object
     * @param orderListId: list order identifier
     * @param contingencyType: contingency type of the order
     * @param listStatusType: list status type of the order
     * @param listOrderStatus: list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime: transaction time of the order
     * @param symbol: symbol used in the order
     * @param jsonOrder: order details in JSON format
     * @param isIsolated: is isolated
     * @param marginBuyBorrowAmount: margin buy borrow amount
     * @param marginBuyBorrowAsset: margin buy borrow asset
     * **/
    public OCOMarginOrder(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                          String listClientOrderId, long transactionTime, String symbol, boolean isIsolated,
                          double marginBuyBorrowAmount, String marginBuyBorrowAsset, JSONObject jsonOrder) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                isIsolated, jsonOrder);
        this.marginBuyBorrowAmount = marginBuyBorrowAmount;
        this.marginBuyBorrowAsset = marginBuyBorrowAsset;
    }

    public double getMarginBuyBorrowAmount() {
        return marginBuyBorrowAmount;
    }

    public String getMarginBuyBorrowAsset() {
        return marginBuyBorrowAsset;
    }

    /** Method to assemble a OCOMarginOrder
     * @param ocoOrder: obtained from Binance's request
     * @return {@link OCOMarginOrder} object
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
                ocoOrder.getString("marginBuyBorrowAsset"),
                ocoOrder
        );
    }

}
