package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FixedActivityProjectPosition} class is useful to format a fixed activity/project position
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
 * Get Fixed/Activity Project Position (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingActivityStructure
 */
public class FixedActivityProjectPosition extends SavingActivityStructure {

    /**
     * {@code canTransfer} whether the fixed activity/project position can transfer
     */
    private final boolean canTransfer;

    /**
     * {@code createTimestamp} create timestamp of the fixed activity/project position
     */
    private final long createTimestamp;

    /**
     * {@code endTime} end time of the fixed activity/project position
     */
    private final long endTime;

    /**
     * {@code interest} of the fixed activity/project position
     */
    private final double interest;

    /**
     * {@code lot} of the fixed activity/project position
     */
    private final int lot;

    /**
     * {@code positionId} position id of the fixed activity/project position
     */
    private final long positionId;

    /**
     * {@code principal} of the fixed activity/project position
     */
    private final double principal;

    /**
     * {@code purchaseTime} purchase time of the fixed activity/project position
     */
    private final long purchaseTime;

    /**
     * {@code redeemDate} redeem date of the fixed activity/project position
     */
    private final String redeemDate;

    /**
     * {@code startTime} start time of the fixed activity/project position
     */
    private final long startTime;

    /**
     * Constructor to init {@link FixedActivityProjectPosition} object
     *
     * @param asset:            asset of the fixed activity/project position
     * @param duration:         of the fixed activity/project position
     * @param interestRate:     interest rate of the fixed activity/project position
     * @param projectId:project id of the fixed activity/project position
     * @param projectName:      project name of the fixed activity/project position
     * @param status:           status of the fixed activity/project position
     * @param type:             type of the fixed activity/project position
     * @param canTransfer:      whether the fixed activity/project position can transfer
     * @param createTimestamp:  create timestamp of the fixed activity/project position
     * @param endTime:          end time of the fixed activity/project position
     * @param interest:         interest of the fixed activity/project position
     * @param lot:              the lot of the fixed activity/project position
     * @param positionId:       position id of the fixed activity/project position
     * @param principal:        principal of the fixed activity/project position
     * @param purchaseTime:     purchase time of the fixed activity/project position
     * @param redeemDate:       redeem date of the fixed activity/project position
     * @param startTime:        start time of the fixed activity/project position
     */
    public FixedActivityProjectPosition(String asset, int duration, double interestRate, String projectId,
                                        String projectName, SavingStatus status, SavingActivityType type,
                                        boolean canTransfer, long createTimestamp, long endTime, double interest, int lot,
                                        long positionId, double principal, long purchaseTime, String redeemDate,
                                        long startTime) {
        super(asset, duration, interestRate, projectId, projectName, status, type);
        this.canTransfer = canTransfer;
        this.createTimestamp = createTimestamp;
        this.endTime = endTime;
        this.interest = interest;
        this.lot = lot;
        this.positionId = positionId;
        this.principal = principal;
        this.purchaseTime = purchaseTime;
        this.redeemDate = redeemDate;
        this.startTime = startTime;
    }

    /**
     * Constructor to init {@link FixedActivityProjectPosition} object
     *
     * @param jFixedActivityProjectPosition: fixed activity/project position details as {@link JSONObject
     *                                       }
     */
    public FixedActivityProjectPosition(JSONObject jFixedActivityProjectPosition) {
        super(jFixedActivityProjectPosition);
        canTransfer = hItem.getBoolean("canTransfer");
        createTimestamp = hItem.getLong("createTimestamp", 0);
        endTime = hItem.getLong("endTime", 0);
        interest = hItem.getDouble("interest", 0);
        lot = hItem.getInt("lot", 0);
        positionId = hItem.getLong("positionId", 0);
        principal = hItem.getDouble("principal", 0);
        purchaseTime = hItem.getLong("purchaseTime", 0);
        redeemDate = hItem.getString("redeemDate");
        startTime = hItem.getLong("startTime", 0);
    }

    /**
     * Method to get {@link #canTransfer} instance <br>
     * No-any params required
     *
     * @return {@link #canTransfer} instance as boolean
     */
    public boolean canTransfer() {
        return canTransfer;
    }

    /**
     * Method to get {@link #createTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #createTimestamp} instance as long
     */
    public long getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * Method to get {@link #createTimestamp} instance <br>
     * No-any params required
     *
     * @return {@link #createTimestamp} instance as {@link Date}
     */
    public Date getCreateDate() {
        return TimeFormatter.getDate(createTimestamp);
    }

    /**
     * Method to get {@link #endTime} instance <br>
     * No-any params required
     *
     * @return {@link #endTime} instance as long
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Method to get {@link #endTime} instance <br>
     * No-any params required
     *
     * @return {@link #endTime} instance as {@link Date}
     */
    public Date getEndDate() {
        return TimeFormatter.getDate(endTime);
    }

    /**
     * Method to get {@link #interest} instance <br>
     * No-any params required
     *
     * @return {@link #interest} instance as double
     */
    public double getInterest() {
        return interest;
    }

    /**
     * Method to get {@link #interest} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interest} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getInterest(int decimals) {
        return roundValue(interest, decimals);
    }

    /**
     * Method to get {@link #lot} instance <br>
     * No-any params required
     *
     * @return {@link #lot} instance as int
     */
    public int getLot() {
        return lot;
    }

    /**
     * Method to get {@link #positionId} instance <br>
     * No-any params required
     *
     * @return {@link #positionId} instance as long
     */
    public long getPositionId() {
        return positionId;
    }

    /**
     * Method to get {@link #principal} instance <br>
     * No-any params required
     *
     * @return {@link #principal} instance as double
     */
    public double getPrincipal() {
        return principal;
    }

    /**
     * Method to get {@link #principal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #principal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPrincipal(int decimals) {
        return roundValue(principal, decimals);
    }

    /**
     * Method to get {@link #purchaseTime} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseTime} instance as long
     */
    public long getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * Method to get {@link #purchaseTime} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseTime} instance as {@link Date}
     */
    public Date getPurchaseDate() {
        return TimeFormatter.getDate(purchaseTime);
    }

    /**
     * Method to get {@link #redeemDate} instance <br>
     * No-any params required
     *
     * @return {@link #redeemDate} instance as {@link String}
     */
    public String getRedeemDate() {
        return redeemDate;
    }

    /**
     * Method to get {@link #redeemDate} instance <br>
     * No-any params required
     *
     * @return {@link #redeemDate} instance as long
     */
    public long getRedeemTimestamp() {
        return TimeFormatter.getDateTimestamp(redeemDate, "yyyy-MM-dd");
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as long
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as {@link Date}
     */
    public Date getStartDate() {
        return TimeFormatter.getDate(startTime);
    }

}
