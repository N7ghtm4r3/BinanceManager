package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.marginlist;

import org.json.JSONObject;

import static com.tecknobit.binancemanager.managers.signedmanagers.trade.common.Order.*;

/**
 * The {@code MarginAssetList} class is useful to create Binance's base margin lists
 *
 * @apiNote see the official documentation at:
 * <ul>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-isolated-margin-transfer-history-user_data</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-loan-record-user_data</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#query-repay-record-user_data</a>
 * </li>
 * <li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade">
 * https://binance-docs.github.io/apidocs/spot/en/#cancel-order-trade</a>
 * </li><li>
 * <a href="https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#get-cross-margin-transfer-history-user_data</a>
 * </li>
 * </ul>
 *  @author N7ghtm4r3 - Tecknobit
 **/

public class MarginAssetList {

    /**
     * {@code symbol} is instance that memorizes asset
     * **/
    protected final String asset;

    /**
     * {@code txId} is instance that memorizes identifier of transaction
     * **/
    protected final long txId;

    /**
     * {@code timestamp} is instance that memorizes timestamp of transaction
     * **/
    protected long timestamp;

    /**
     * {@code status} is instance that memorizes status of transaction
     * **/
    protected String status;

    /** Constructor to init {@link MarginAssetList} object
     * @param asset: asset
     * @param txId: identifier of transaction
     * @param timestamp: timestamp of transaction
     * @param status: status of transaction
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginAssetList(String asset, long txId, long timestamp, String status) {
        this.asset = asset;
        this.txId = txId;
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        else
            this.timestamp = timestamp;
        if (checkStatusValidity(status))
            this.status = status;
        else
            throw new IllegalArgumentException("Status can be only CONFIRMED, PENDING or FAILED");
    }

    /**
     * Constructor to init {@link MarginAssetList} object
     *
     * @param marginList: margin assets list details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public MarginAssetList(JSONObject marginList) {
        asset = marginList.getString("asset");
        txId = marginList.getLong("txId");
        timestamp = marginList.getLong("timestamp");
        if (timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        status = marginList.getString("status");
        if (!checkStatusValidity(status))
            throw new IllegalArgumentException("Status can be only CONFIRMED, PENDING or FAILED");
    }

    public String getAsset() {
        return asset;
    }

    public long getTxId() {
        return txId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /** Method to set {@link #timestamp}
     * @param timestamp: timestamp of transaction
     * @throws IllegalArgumentException when timestamp value is less than 0
     * **/
    public void setTimestamp(long timestamp) {
        if(timestamp < 0)
            throw new IllegalArgumentException("Timestamp value cannot be less than 0");
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    /** Method to set {@link #status}
     * @param status: status of transaction
     * @throws IllegalArgumentException when status of transaction is not valid
     * **/
    public void setStatus(String status) {
        if(checkStatusValidity(status))
            this.status = status;
        else
            throw new IllegalArgumentException("Status can be only CONFIRMED, PENDING or FAILED");
    }

    /** Method to check {@link #status} validity
     * @param status: status of transaction
     * **/
    private boolean checkStatusValidity(String status){
        return status.equals(STATUS_CONFIRMED) || status.equals(STATUS_PENDING) || status.equals(STATUS_FAILED);
    }

    @Override
    public String toString() {
        return "MarginAssetList{" +
                "asset='" + asset + '\'' +
                ", txId=" + txId +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }

}
