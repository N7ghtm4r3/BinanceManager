package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.OrderDetails;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code OCOMarginOrder} class is useful to format a {@code "Binance"}'s margin {@code "OCO"} order
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-oco-trade</a>
 * @see OrderDetails
 * @see ComposedMarginOrderDetails
 */
public class OCOMarginOrder extends ComposedMarginOrderDetails {

    /**
     * {@code marginBuyBorrowAmount} is instance that memorizes margin buy borrow amount
     */
    private final double marginBuyBorrowAmount;
    /**
     * {@code marginBuyBorrowAsset} is instance that memorizes margin buy borrow asset
     */
    private final String marginBuyBorrowAsset;

    /**
     * Constructor to init {@link OCOMarginOrder} object
     *
     * @param orderListId:             list order identifier
     * @param contingencyType:         contingency type of the order
     * @param listStatusType:          list status type of the order
     * @param listOrderStatus:         list order status
     * @param listClientOrderId:       list client order id
     * @param transactionTime:         transaction time of the order
     * @param symbol:                  symbol used in the order
     * @param orders:                  list of {@link Order}
     * @param marginOrdersListDetails: list of {@link MarginOrderDetails}
     * @param isIsolated:              is isolated
     * @param marginBuyBorrowAmount:   margin buy borrow amount
     * @param marginBuyBorrowAsset:    margin buy borrow asset
     */
    public OCOMarginOrder(long orderListId, String contingencyType, Status listStatusType, Status listOrderStatus,
                          String listClientOrderId, long transactionTime, String symbol, ArrayList<Order> orders,
                          ArrayList<MarginOrderDetails> marginOrdersListDetails, boolean isIsolated,
                          double marginBuyBorrowAmount, String marginBuyBorrowAsset) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                orders, marginOrdersListDetails, isIsolated);
        this.marginBuyBorrowAmount = marginBuyBorrowAmount;
        this.marginBuyBorrowAsset = marginBuyBorrowAsset;
    }

    /**
     * Constructor to init {@link OCOMarginOrder} object
     *
     * @param ocoMarginOrder: oco margin order details as {@link JSONObject}
     */
    public OCOMarginOrder(JSONObject ocoMarginOrder) {
        super(ocoMarginOrder);
        marginBuyBorrowAmount = hOrder.getDouble("marginBuyBorrowAmount", 0);
        marginBuyBorrowAsset = hOrder.getString("marginBuyBorrowAsset");
    }

    /**
     * Method to get {@link #marginBuyBorrowAmount} instance <br>
     * No-any params required
     *
     * @return {@link #marginBuyBorrowAmount} instance as double
     */
    public double getMarginBuyBorrowAmount() {
        return marginBuyBorrowAmount;
    }

    /**
     * Method to get {@link #marginBuyBorrowAsset} instance <br>
     * No-any params required
     *
     * @return {@link #marginBuyBorrowAsset} instance as {@link String}
     */
    public String getMarginBuyBorrowAsset() {
        return marginBuyBorrowAsset;
    }

    /**
     * Method to get {@link #marginBuyBorrowAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #marginBuyBorrowAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMarginBuyBorrowAmount(int decimals) {
        return roundValue(marginBuyBorrowAmount, decimals);
    }

    /**
     * {@code SideEffect} list of available side effects
     */
    public enum SideEffect {

        /**
         * {@code "NO_SIDE_EFFECT"} side effect
         */
        NO_SIDE_EFFECT,

        /**
         * {@code "MARGIN_BUY"} side effect
         */
        MARGIN_BUY,

        /**
         * {@code "AUTO_REPAY"} side effect
         */
        AUTO_REPAY

    }

}
