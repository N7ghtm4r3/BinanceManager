package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code OCOMarginOrder} class is useful to format all Binance Margin OCO Order request
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
 **/

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

    /**
     * Constructor to init {@link OCOMarginOrder} object
     *
     * @param orderListId:            list order identifier
     * @param contingencyType:        contingency type of the order
     * @param listStatusType:         list status type of the order
     * @param listOrderStatus:        list order status
     * @param listClientOrderId:      list client order id
     * @param transactionTime:        transaction time of the order
     * @param symbol:                 symbol used in the order
     * @param orderValues:            list of {@link OrderValues}
     * @param detailMarginOrdersList: list of {@link DetailMarginOrder}
     * @param isIsolated:             is isolated
     * @param marginBuyBorrowAmount:  margin buy borrow amount
     * @param marginBuyBorrowAsset:   margin buy borrow asset
     **/
    public OCOMarginOrder(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                          String listClientOrderId, long transactionTime, String symbol, ArrayList<OrderValues> orderValues,
                          ArrayList<DetailMarginOrder> detailMarginOrdersList, boolean isIsolated, double marginBuyBorrowAmount,
                          String marginBuyBorrowAsset) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orderValues, detailMarginOrdersList, isIsolated);
        this.marginBuyBorrowAmount = marginBuyBorrowAmount;
        this.marginBuyBorrowAsset = marginBuyBorrowAsset;
    }

    /**
     * Constructor to init {@link OCOMarginOrder} object
     *
     * @param ocoMarginOrder: oco margin order details as {@link JSONObject}
     **/
    public OCOMarginOrder(JSONObject ocoMarginOrder) {
        super(ocoMarginOrder);
        marginBuyBorrowAmount = ocoMarginOrder.getDouble("marginBuyBorrowAmount");
        marginBuyBorrowAsset = ocoMarginOrder.getString("marginBuyBorrowAsset");
    }

    public double getMarginBuyBorrowAmount() {
        return marginBuyBorrowAmount;
    }

    /**
     * Method to get {@link #marginBuyBorrowAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginBuyBorrowAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMarginBuyBorrowAmount(int decimals) {
        return roundValue(marginBuyBorrowAmount, decimals);
    }

    public String getMarginBuyBorrowAsset() {
        return marginBuyBorrowAsset;
    }

    @Override
    public String toString() {
        return "OCOMarginOrder{" +
                "marginBuyBorrowAmount=" + marginBuyBorrowAmount +
                ", marginBuyBorrowAsset='" + marginBuyBorrowAsset + '\'' +
                ", orderListId=" + orderListId +
                ", contingencyType='" + contingencyType + '\'' +
                ", listStatusType='" + listStatusType + '\'' +
                ", listOrderStatus='" + listOrderStatus + '\'' +
                ", listClientOrderId='" + listClientOrderId + '\'' +
                ", transactionTime=" + transactionTime +
                ", symbol='" + symbol + '\'' +
                ", orderValues=" + orderValues +
                '}';
    }

}
