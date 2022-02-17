package com.tecknobit.binancemanager.Managers.Wallet.Records.Dust;

public class UniversalTransfer {
    private final String asset;
    private final double amount;
    private final String type;
    private final String status;
    private final long tranId;
    private final long timestamp;

    public UniversalTransfer(String asset, double amount, String type, String status, long tranId, long timestamp) {
        this.asset = asset;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.tranId = tranId;
        this.timestamp = timestamp;
    }

    public String asset() {
        return asset;
    }

    public double amount() {
        return amount;
    }

    public String type() {
        return type;
    }

    public String status() {
        return status;
    }

    public long tranId() {
        return tranId;
    }

    public long timestamp() {
        return timestamp;
    }

}
