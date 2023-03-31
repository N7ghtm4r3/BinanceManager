package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import org.json.JSONObject;

public class AlgoNewOrderOperation extends AlgoOperationResult {

    private final String clientAlgoId;

    public AlgoNewOrderOperation(boolean success, String clientAlgoId) {
        super(success);
        this.clientAlgoId = clientAlgoId;
    }

    public AlgoNewOrderOperation(JSONObject jAlgoNewOrderOperation) {
        super(jAlgoNewOrderOperation);
        clientAlgoId = hItem.getString("clientAlgoId");
    }

    public String getClientAlgoId() {
        return clientAlgoId;
    }

}
