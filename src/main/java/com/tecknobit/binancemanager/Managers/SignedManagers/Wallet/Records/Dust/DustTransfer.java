package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

import java.util.ArrayList;

/**
 *  The {@code DustTransfer} class is useful to manage DustTransfer Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
 * **/

public class DustTransfer {

    private final double totalServiceCharge;
    private final double totalTransfered;
    private ArrayList<TransferResult> transferResultsList;

    public DustTransfer(double totalServiceCharge, double totalTransfered, ArrayList<TransferResult> transferResults) {
        this.totalServiceCharge = totalServiceCharge;
        this.totalTransfered = totalTransfered;
        this.transferResultsList = transferResults;
    }

    public double totalServiceCharge() {
        return totalServiceCharge;
    }

    public double totalTransfered() {
        return totalTransfered;
    }

    public ArrayList<TransferResult> transferResults() {
        return transferResultsList;
    }

    public void setTransferResultsList(ArrayList<TransferResult> transferResultsList) {
        this.transferResultsList = transferResultsList;
    }

    public void insertTransferResult(TransferResult transferResult){
        if(!transferResultsList.contains(transferResult))
            transferResultsList.add(transferResult);
    }

    public boolean removeTransferResult(TransferResult transferResult){
        return transferResultsList.remove(transferResult);
    }

    public TransferResult getAssetDribbletDetails(int index){
        return transferResultsList.get(index);
    }

    /**
     *  The {@code TransferResult} class is useful to obtain and format TransferResult object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
     * **/

    public static class TransferResult {

        private final double amount;
        private final String fromAsset;
        private final long operateTime;
        private final double serviceChargeAmount;
        private final long tranId;
        private final double transferedAmount;

        public TransferResult(double amount, String fromAsset, long operateTime, double serviceChargeAmount,
                              long tranId, double transferedAmount) {
            this.amount = amount;
            this.fromAsset = fromAsset;
            this.operateTime = operateTime;
            this.serviceChargeAmount = serviceChargeAmount;
            this.tranId = tranId;
            this.transferedAmount = transferedAmount;
        }

        public double amount() {
            return amount;
        }

        public String fromAsset() {
            return fromAsset;
        }

        public long operateTime() {
            return operateTime;
        }

        public double serviceChargeAmount() {
            return serviceChargeAmount;
        }

        public long tranId() {
            return tranId;
        }

        public double transferedAmount() {
            return transferedAmount;
        }

    }

}
