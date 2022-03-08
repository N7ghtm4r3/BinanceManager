package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONObject;

/**
 *  The {@code MarginOrderStatusDetails} class is useful to format all Binance Margin Order Status request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#margin-account-cancel-order-trade
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginOrderStatusDetails extends OrderDetails {

    private final boolean isIsolated;

    public MarginOrderStatusDetails(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                                    String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject,
                                    boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
