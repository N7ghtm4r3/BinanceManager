package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class PoolStructure extends BinanceItem {

    public enum BSwapStatus {

        pending(0),
        success(1),
        failed(2);

        private final int status;

        BSwapStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public static BSwapStatus reachEnumConstant(int status) {
            switch (status) {
                case 0:
                    return pending;
                case 1:
                    return success;
                default:
                    return failed;
            }
        }

        @Override
        public String toString() {
            return status + "";
        }
    }

    public enum PoolType {

        SINGLE,
        COMBINATION

    }

    protected final long poolId;
    protected final String poolName;

    public PoolStructure(long poolId, String poolName) {
        super(null);
        this.poolId = poolId;
        this.poolName = poolName;
    }

    public PoolStructure(JSONObject jPoolStructure) {
        super(jPoolStructure);
        poolId = hItem.getLong("poolId", 0);
        poolName = hItem.getString("poolName");
    }

    public long getPoolId() {
        return poolId;
    }

    public String getPoolName() {
        return poolName;
    }

}
