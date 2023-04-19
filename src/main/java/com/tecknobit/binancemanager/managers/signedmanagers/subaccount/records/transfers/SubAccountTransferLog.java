package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset.SpotAssetTransfer;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.PrincipalAccountType;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubAccountTransferLog extends BinanceItem {

    private final int count;
    private final ArrayList<TransferLog> transferLogs;

    public SubAccountTransferLog(int count, ArrayList<TransferLog> transferLogs) {
        super(null);
        this.count = count;
        this.transferLogs = transferLogs;
    }

    public SubAccountTransferLog(JSONObject jSubAccountTransferLog) {
        super(jSubAccountTransferLog);
        count = hItem.getInt("count");
        transferLogs = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("managerSubTransferHistoryVos");
        if (jList != null)
            for (JSONObject item : jList)
                transferLogs.add(new TransferLog(item));
    }

    public int getCount() {
        return count;
    }

    public ArrayList<TransferLog> getTransferLogs() {
        return transferLogs;
    }

    public static class TransferLog extends SubTransferStructure {

        private final String fromEmail;
        private final String toEmail;
        private final double amount;
        private final long scheduledData;
        private final long createTime;

        public TransferLog(long tranId, String asset, PrincipalAccountType fromAccountType,
                           PrincipalAccountType toAccountType, SpotAssetTransfer.TransferStatus status, String fromEmail,
                           String toEmail, double amount, long scheduledData, long createTime) {
            super(tranId, asset, fromAccountType, toAccountType, status);
            this.fromEmail = fromEmail;
            this.toEmail = toEmail;
            this.amount = amount;
            this.scheduledData = scheduledData;
            this.createTime = createTime;
        }

        public TransferLog(JSONObject jTransferLog) {
            super(jTransferLog);
            fromEmail = hItem.getString("fromEmail");
            toEmail = hItem.getString("toEmail");
            amount = hItem.getDouble("amount", 0);
            scheduledData = hItem.getLong("scheduledData", 0);
            createTime = hItem.getLong("createTime", 0);
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

        public long getScheduledData() {
            return scheduledData;
        }

        public long getCreateTime() {
            return createTime;
        }

    }

}
