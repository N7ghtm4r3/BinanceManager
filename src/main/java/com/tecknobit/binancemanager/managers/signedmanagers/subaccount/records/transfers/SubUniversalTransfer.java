package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer.TransferStatus;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

public class SubUniversalTransfer extends SubTransferStructure {

    private final String fromEmail;
    private final String toEmail;
    private final double amount;
    private final long createTimestamp;
    private final String clientTranId;

    public SubUniversalTransfer(long tranId, String asset, PrincipalAccountType fromAccountType,
                                PrincipalAccountType toAccountType, TransferStatus status, String fromEmail,
                                String toEmail, double amount, long createTimestamp, String clientTranId) {
        super(tranId, asset, fromAccountType, toAccountType, status);
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.amount = amount;
        this.createTimestamp = createTimestamp;
        this.clientTranId = clientTranId;
    }

    public SubUniversalTransfer(JSONObject jSubUniversalTransfer) {
        super(jSubUniversalTransfer);
        fromEmail = hItem.getString("fromEmail");
        toEmail = hItem.getString("toEmail");
        amount = hItem.getDouble("amount", 0);
        createTimestamp = hItem.getLong("createTimeStamp", 0);
        clientTranId = hItem.getString("clientTranId");
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public double getAmount() {
        return amount;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public String getClientTranId() {
        return clientTranId;
    }

}
