package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.response;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code MarginOrder} class is useful to format a {@code "Binance"}'s margin order
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-trade">
 *             Margin Account/Trade</a>
 *     </li>
 *      <li>
 *          <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-new-order-trade">
 *              Margin Account New Order (TRADE)</a>
 *      </li>
 *      <li>
 *          <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 *              Query Cross Margin Account Details (USER_DATA)</a>
 *      </li>
 * </ul>
 * @see Order
 */
@Structure
public abstract class MarginOrder extends Order {

    /**
     * {@code transactTime} is instance that memorizes transaction time
     */
    protected final long transactTime;

    /**
     * Constructor to init {@link MarginOrder} object
     *
     * @param symbol:        symbol used in the order
     * @param orderId:       order identifier
     * @param clientOrderId: client order identifier
     * @param transactTime:  transaction time
     */
    public MarginOrder(String symbol, long orderId, String clientOrderId, long transactTime) {
        super(symbol, orderId, clientOrderId);
        this.transactTime = transactTime;
    }

    /**
     * Constructor to init {@link MarginOrder} object
     *
     * @param marginOrder: margin order details as {@link JSONObject}
     */
    public MarginOrder(JSONObject marginOrder) {
        super(marginOrder);
        transactTime = marginOrder.getLong("transactTime");
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as long
     */
    public long getTransactTime() {
        return transactTime;
    }

    /**
     * Method to get {@link #transactTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactTime} instance as {@link Date}
     */
    public Date getTransactDate() {
        return TimeFormatter.getDate(transactTime);
    }

    /**
     * {@code SideEffectType} list of available side effect types
     */
    public enum SideEffectType {

        /**
         * {@code "NO_SIDE_EFFECT"} side effect type
         */
        NO_SIDE_EFFECT,

        /**
         * {@code "MARGIN_BUY"} side effect type
         */
        MARGIN_BUY,

        /**
         * {@code "AUTO_REPAY"} side effect type
         */
        AUTO_REPAY

    }

}
