package com.tecknobit.binancemanager.Managers.Wallet.Records;

public record UniversalTransfer (String asset, double amount, String type, String status, long tranId, long timestamp) {

    @Override
    public String asset() {
        return asset;
    }

    @Override
    public double amount() {
        return amount;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public String status() {
        return status;
    }

    @Override
    public long tranId() {
        return tranId;
    }

    @Override
    public long timestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "UniversalTransfer{" +
                "asset='" + asset + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", tranId=" + tranId +
                ", timestamp=" + timestamp +
                '}';
    }

}
