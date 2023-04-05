package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import org.json.JSONObject;

public class LiquidityOperation extends PoolStructure {

    public enum Operation {

        ADD,
        REMOVE

    }

    private final long operationId;
    private final Operation operation;
    private final BSwapStatus status;
    private final long updateTime;
    private final double shareAmount;

    public LiquidityOperation(long poolId, String poolName, long operationId, Operation operation,
                              BSwapStatus status, long updateTime, double shareAmount) {
        super(poolId, poolName);
        this.operationId = operationId;
        this.operation = operation;
        this.status = status;
        this.updateTime = updateTime;
        this.shareAmount = shareAmount;
    }

    public LiquidityOperation(JSONObject jLiquidityOperation) {
        super(jLiquidityOperation);
        operationId = hItem.getLong("operationId", 0);
        operation = Operation.valueOf(hItem.getString("operation"));
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
        updateTime = hItem.getLong("updateTime", 0);
        shareAmount = hItem.getDouble("shareAmount", 0);
    }

    public long getOperationId() {
        return operationId;
    }

    public Operation getOperation() {
        return operation;
    }

    public BSwapStatus getStatus() {
        return status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public double getShareAmount() {
        return shareAmount;
    }

}
