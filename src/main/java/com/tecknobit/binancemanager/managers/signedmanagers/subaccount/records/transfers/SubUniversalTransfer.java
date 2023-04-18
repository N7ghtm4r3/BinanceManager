package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class SubUniversalTransfer extends BinanceItem {

    private final long tranId;
    private final String clientTranId;

    public SubUniversalTransfer(long tranId, String clientTranId) {
        super(null);
        this.tranId = tranId;
        this.clientTranId = clientTranId;
    }

    public SubUniversalTransfer(JSONObject jSubUniversalTransfer) {
        super(jSubUniversalTransfer);
        tranId = hItem.getLong("tranId", 0);
        clientTranId = hItem.getString("clientTranId");
    }

    public long getTranId() {
        return tranId;
    }

    public String getClientTranId() {
        return clientTranId;
    }

}
