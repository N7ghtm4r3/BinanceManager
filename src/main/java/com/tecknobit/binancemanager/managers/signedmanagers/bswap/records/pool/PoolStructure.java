package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code PoolStructure} class is useful to format a pool structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#list-all-swap-pools-market_data">
 *             List All Swap Pools (MARKET_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-information-of-a-pool-user_data">
 *             Get liquidity information of a pool (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
 *             Get Liquidity Operation Record (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-pool-configure-user_data">
 *             Get Pool Configure (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
 *             Get Claimed History (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
@Structure
public abstract class PoolStructure extends BinanceItem {

    /**
     * {@code BSwapStatus} list of available BSwap statuses
     */
    public enum BSwapStatus {

        /**
         * {@code pending} BSwap status
         */
        pending(0),

        /**
         * {@code success} BSwap status
         */
        success(1),

        /**
         * {@code failed} BSwap status
         */
        failed(2);

        /**
         * {@code status} value
         */
        private final int status;

        /**
         * {@code VALUES} list of the statuses
         */
        private static final List<BSwapStatus> VALUES = Arrays.stream(BSwapStatus.values()).toList();

        /**
         * Constructor to init {@link BSwapStatus} object
         *
         * @param status: status value
         */
        BSwapStatus(int status) {
            this.status = status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as int
         */
        public int getStatus() {
            return status;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param status: status to reach
         * @return enum constant as {@link BSwapStatus}
         */
        public static BSwapStatus reachEnumConstant(int status) {
            return VALUES.get(status);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        @Override
        public String toString() {
            return status + "";
        }

    }

    /**
     * {@code PoolType} list of available pool types
     */
    public enum PoolType {

        /**
         * {@code SINGLE} pool type
         */
        SINGLE,

        /**
         * {@code COMBINATION} pool type
         */
        COMBINATION

    }

    /**
     * {@code poolId} id of the pool
     */
    protected final long poolId;

    /**
     * {@code poolName} name of the pool
     */
    protected final String poolName;

    /**
     * Constructor to init {@link PoolStructure} object
     *
     * @param poolId:   id of the pool
     * @param poolName: name of the pool
     */
    public PoolStructure(long poolId, String poolName) {
        super(null);
        this.poolId = poolId;
        this.poolName = poolName;
    }

    /**
     * Constructor to init {@link PoolStructure} object
     *
     * @param jPoolStructure: pool structure details as {@link JSONObject}
     */
    public PoolStructure(JSONObject jPoolStructure) {
        super(jPoolStructure);
        poolId = hItem.getLong("poolId", 0);
        poolName = hItem.getString("poolName");
    }

    /**
     * Method to get {@link #poolId} instance <br>
     * No-any params required
     *
     * @return {@link #poolId} instance as long
     */
    public long getPoolId() {
        return poolId;
    }

    /**
     * Method to get {@link #poolName} instance <br>
     * No-any params required
     *
     * @return {@link #poolName} instance as {@link String}
     */
    public String getPoolName() {
        return poolName;
    }

}
