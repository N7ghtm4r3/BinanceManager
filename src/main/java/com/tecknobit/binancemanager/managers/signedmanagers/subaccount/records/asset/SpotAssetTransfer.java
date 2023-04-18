package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import org.json.JSONObject;

public class SpotAssetTransfer extends AssetTransfer {

    public enum TransferStatus {

        PROCESS,
        SUCCESS,
        FAILURE

    }

    private final TransferStatus status;

    public SpotAssetTransfer(String from, String to, String asset, double qty, long tranId, long time,
                             TransferStatus status) {
        super(from, to, asset, qty, tranId, time);
        this.status = status;
    }

    public SpotAssetTransfer(JSONObject jSpotAssetTransfer) {
        super(jSpotAssetTransfer);
        status = TransferStatus.valueOf(hItem.getString("status"));
    }

    public TransferStatus getStatus() {
        return status;
    }

}
