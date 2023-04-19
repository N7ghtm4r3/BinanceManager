package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;

public abstract class SubTransferStructure extends BinanceItem {

    protected final long tranId;
    protected final String asset;
    protected final PrincipalAccountType fromAccountType;
    protected final PrincipalAccountType toAccountType;
    protected final TransferStatus status;

    public SubTransferStructure(long tranId, String asset, PrincipalAccountType fromAccountType,
                                PrincipalAccountType toAccountType, TransferStatus status) {
        super(null);
        this.tranId = tranId;
        this.asset = asset;
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
        this.status = status;
    }

    public SubTransferStructure(JSONObject jSubAccountTransfer) {
        super(jSubAccountTransfer);
        asset = hItem.getString("asset");
        fromAccountType = PrincipalAccountType.valueOf(hItem.getString("fromAccountType"));
        toAccountType = PrincipalAccountType.valueOf(hItem.getString("toAccountType"));
        status = TransferStatus.valueOf(hItem.getString("status"));
        tranId = hItem.getLong("tranId", 0);
    }

    public long getTranId() {
        return tranId;
    }

    public String getAsset() {
        return asset;
    }

    public PrincipalAccountType getFromAccountType() {
        return fromAccountType;
    }

    public PrincipalAccountType getToAccountType() {
        return toAccountType;
    }

    public TransferStatus getStatus() {
        return status;
    }

}
