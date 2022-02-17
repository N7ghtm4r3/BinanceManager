package com.tecknobit.binancemanager.Managers.Wallet.Records.Dust;

import java.util.ArrayList;

public class DustTransfer {

    private final double totalServiceCharge;
    private final double totalTransfered;
    private final ArrayList<TransferResult> transferResults;

    public DustTransfer(double totalServiceCharge, double totalTransfered, ArrayList<TransferResult> transferResults) {
        this.totalServiceCharge = totalServiceCharge;
        this.totalTransfered = totalTransfered;
        this.transferResults = transferResults;
    }

    public double totalServiceCharge() {
        return totalServiceCharge;
    }

    public double totalTransfered() {
        return totalTransfered;
    }

    public ArrayList<TransferResult> transferResults() {
        return transferResults;
    }

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
