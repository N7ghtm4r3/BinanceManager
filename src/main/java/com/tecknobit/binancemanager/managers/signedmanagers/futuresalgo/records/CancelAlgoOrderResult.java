package com.tecknobit.binancemanager.managers.signedmanagers.futuresalgo.records;

import org.json.JSONObject;

public class CancelAlgoOrderResult extends AlgoOperationResult {

    private final long algoId;

    public CancelAlgoOrderResult(boolean success, long algoId) {
        super(success);
        this.algoId = algoId;
    }

    public CancelAlgoOrderResult(JSONObject jCancelAlgoOrderResult) {
        super(jCancelAlgoOrderResult);
        algoId = hItem.getLong("algoId", 0);
    }

    public long getAlgoId() {
        return algoId;
    }

}
