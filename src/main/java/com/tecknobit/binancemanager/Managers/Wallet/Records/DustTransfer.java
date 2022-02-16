package com.tecknobit.binancemanager.Managers.Wallet.Records;

import java.util.ArrayList;

public record DustTransfer (double totalServiceCharge, double totalTransfered, ArrayList<TransferResult> transferResults) {

    @Override
    public double totalServiceCharge() {
        return totalServiceCharge;
    }

    @Override
    public double totalTransfered() {
        return totalTransfered;
    }

    @Override
    public ArrayList<TransferResult> transferResults() {
        return transferResults;
    }

    public record TransferResult (double amount, String fromAsset, long operateTime, double serviceChargeAmount,
                                  long tranId, double transferedAmount) {

        @Override
        public double amount() {
            return amount;
        }

        @Override
        public String fromAsset() {
            return fromAsset;
        }

        @Override
        public long operateTime() {
            return operateTime;
        }

        @Override
        public double serviceChargeAmount() {
            return serviceChargeAmount;
        }

        @Override
        public long tranId() {
            return tranId;
        }

        @Override
        public double transferedAmount() {
            return transferedAmount;
        }

    }

}
