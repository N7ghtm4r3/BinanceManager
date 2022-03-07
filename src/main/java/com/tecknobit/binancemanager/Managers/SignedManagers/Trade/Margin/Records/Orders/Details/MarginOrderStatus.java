package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Orders.Details;

import com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common.OrderDetails;
import org.json.JSONObject;

public class MarginOrderStatus extends OrderDetails {

    private final boolean isIsolated;

    public MarginOrderStatus(long orderListId, String contingencyType, String listStatusType, String listOrderStatus,
                             String listClientOrderId, long transactionTime, String symbol, JSONObject jsonObject,
                             boolean isIsolated) {
        super(orderListId, contingencyType, listStatusType, listOrderStatus, listClientOrderId, transactionTime, symbol, jsonObject);
        this.isIsolated = isIsolated;
    }

    public boolean isIsolated() {
        return isIsolated;
    }

}
