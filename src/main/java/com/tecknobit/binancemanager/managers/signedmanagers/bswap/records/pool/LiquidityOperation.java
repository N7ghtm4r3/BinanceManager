package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code LiquidityOperation} class is useful to format a liquidity operation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-liquidity-operation-record-user_data">
 * Get Liquidity Operation Record (USER_DATA)</a>
 * @see BinanceItem
 * @see PoolStructure
 **/
public class LiquidityOperation extends PoolStructure {

    /**
     * {@code Operation} list of available liquidity operations
     **/
    public enum Operation {

        /**
         * {@code ADD} liquidity operation
         **/
        ADD,

        /**
         * {@code REMOVE} liquidity operation
         **/
        REMOVE

    }

    /**
     * {@code operationId} id of the liquidity operation
     **/
    private final long operationId;

    /**
     * {@code operation} of the liquidity
     **/
    private final Operation operation;

    /**
     * {@code status} of the liquidity operation
     **/
    private final BSwapStatus status;

    /**
     * {@code updateTime} update time of the liquidity operation
     **/
    private final long updateTime;

    /**
     * {@code shareAmount} share amount of the liquidity operation
     **/
    private final double shareAmount;

    /**
     * Constructor to init {@link LiquidityOperation} object
     *
     * @param poolId:      id of the liquidity operation
     * @param poolName:    name of the liquidity operation
     * @param operationId: id of the liquidity operation
     * @param operation:   operation of the liquidity
     * @param status:      status of the liquidity operation
     * @param updateTime:  update time of the liquidity operation
     * @param shareAmount: share amount of the liquidity operation
     **/
    public LiquidityOperation(long poolId, String poolName, long operationId, Operation operation,
                              BSwapStatus status, long updateTime, double shareAmount) {
        super(poolId, poolName);
        this.operationId = operationId;
        this.operation = operation;
        this.status = status;
        this.updateTime = updateTime;
        this.shareAmount = shareAmount;
    }

    /**
     * Constructor to init {@link LiquidityOperation} object
     *
     * @param jLiquidityOperation: liquidity operation details as {@link JSONObject}
     **/
    public LiquidityOperation(JSONObject jLiquidityOperation) {
        super(jLiquidityOperation);
        operationId = hItem.getLong("operationId", 0);
        operation = Operation.valueOf(hItem.getString("operation"));
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
        updateTime = hItem.getLong("updateTime", 0);
        shareAmount = hItem.getDouble("shareAmount", 0);
    }

    /**
     * Method to get {@link #operationId} instance <br>
     * No-any params required
     *
     * @return {@link #operationId} instance as long
     **/
    public long getOperationId() {
        return operationId;
    }

    /**
     * Method to get {@link #operation} instance <br>
     * No-any params required
     *
     * @return {@link #operation} instance as {@link Operation}
     **/
    public Operation getOperation() {
        return operation;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link BSwapStatus}
     **/
    public BSwapStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to get {@link #shareAmount} instance <br>
     * No-any params required
     *
     * @return {@link #shareAmount} instance as double
     **/
    public double getShareAmount() {
        return shareAmount;
    }

    /**
     * Method to get {@link #shareAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #shareAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getShareAmount(int decimals) {
        return roundValue(shareAmount, decimals);
    }

}
