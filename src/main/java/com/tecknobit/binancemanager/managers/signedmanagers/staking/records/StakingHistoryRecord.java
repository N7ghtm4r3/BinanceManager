package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProductPosition.StakingPositionType;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code StakingHistoryRecord} class is useful to format a staking history record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-history-user_data">
 * Get Staking History(USER_DATA)</a>
 * @see BinanceItem
 **/
public class StakingHistoryRecord extends BinanceItem {

    /**
     * {@code TxnType} list of available txn types
     **/
    public enum TxnType {

        /**
         * {@code SUBSCRIPTION} txn type
         **/
        SUBSCRIPTION,

        /**
         * {@code REDEMPTION} txn type
         **/
        REDEMPTION,

        /**
         * {@code INTEREST} txn type
         **/
        INTEREST

    }

    /**
     * {@code positionId} position id of the staking history record
     **/
    private final long positionId;

    /**
     * {@code time} of the staking history record
     **/
    private final long time;

    /**
     * {@code asset} of the staking history record
     **/
    private final String asset;

    /**
     * {@code project} of the staking history record
     **/
    private final String project;

    /**
     * {@code amount} of the staking history record
     **/
    private final double amount;

    /**
     * {@code lockPeriod} lock period of the staking history record
     **/
    private final int lockPeriod;

    /**
     * {@code deliverDate} deliver date of the staking history record
     **/
    private final long deliverDate;

    /**
     * {@code type} of the staking history record
     **/
    private final StakingPositionType type;

    /**
     * {@code status} of the staking history record
     **/
    private final String status;

    /**
     * Constructor to init {@link StakingHistoryRecord} object
     *
     * @param positionId:     position id of the staking history record
     * @param time:           time of the staking history record
     * @param asset:          asset of the staking history record
     * @param project:project of the staking history record
     * @param amount:         amount of the staking history record
     * @param lockPeriod:     lock period of the staking history record
     * @param deliverDate:    deliver date of the staking history record
     * @param type:           type of the staking history record
     * @param status:         status of the staking history record
     **/
    public StakingHistoryRecord(long positionId, long time, String asset, String project, double amount, int lockPeriod,
                                long deliverDate, StakingPositionType type, String status) {
        super(null);
        this.positionId = positionId;
        this.time = time;
        this.asset = asset;
        this.project = project;
        this.amount = amount;
        this.lockPeriod = lockPeriod;
        this.deliverDate = deliverDate;
        this.type = type;
        this.status = status;
    }

    /**
     * Constructor to init {@link StakingHistoryRecord} object
     *
     * @param jStakingHistoryRecord: staking history record details as {@link JSONObject}
     **/
    public StakingHistoryRecord(JSONObject jStakingHistoryRecord) {
        super(jStakingHistoryRecord);
        positionId = hItem.getLong("positionId", 0);
        time = hItem.getLong("time", 0);
        asset = hItem.getString("asset");
        project = hItem.getString("project");
        amount = hItem.getDouble("amount", 0);
        lockPeriod = hItem.getInt("lockPeriod", 0);
        deliverDate = hItem.getLong("deliverDate", 0);
        String sType = hItem.getString("type");
        if (sType != null)
            type = StakingPositionType.valueOf(sType);
        else
            type = null;
        status = hItem.getString("status");
    }

    /**
     * Method to get {@link #positionId} instance <br>
     * No-any params required
     *
     * @return {@link #positionId} instance as long
     **/
    public long getPositionId() {
        return positionId;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     **/
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     **/
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     **/
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #project} instance <br>
     * No-any params required
     *
     * @return {@link #project} instance as {@link String}
     **/
    public String getProject() {
        return project;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     **/
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #lockPeriod} instance <br>
     * No-any params required
     *
     * @return {@link #lockPeriod} instance as int
     **/
    public int getLockPeriod() {
        return lockPeriod;
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as long
     **/
    public long getDeliverDate() {
        return deliverDate;
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as {@link Date}
     **/
    public Date getDeliver() {
        return TimeFormatter.getDate(deliverDate);
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link StakingPositionType}
     **/
    public StakingPositionType getType() {
        return type;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link String}
     **/
    public String getStatus() {
        return status;
    }

}
