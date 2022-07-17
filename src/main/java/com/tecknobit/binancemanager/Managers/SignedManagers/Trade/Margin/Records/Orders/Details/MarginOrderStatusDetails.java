package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONObject;

/**
 *  The {@code MarginOrderStatusDetails} class is useful to format all Binance Margin Order Status request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade">
 *      https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginOrderStatusDetails extends OrderDetails {

    /**
     * {@code isIsolated} is instance that memorizes if is isolated
     * **/
    private final boolean isIsolated;

    /** Constructor to init {@link OrderDetails} object
     * @param orderListId: list order identifier
     * @param contingencyType: contingency type of the order
     * @param listStatusType: list status type of the order
     * @param listOrderStatus: list order status
     * @param listClientOrderId: list client order id
     * @param transactionTime: transaction time of the order
     * @param symbol: symbol used in the order
     * @param jsonOrder: order details in JSON format
     * @param isIsolated: is isolated
     * **/
    public MarginOrderStatusDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonOrder,
                                    boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol,
                jsonOrder);
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
