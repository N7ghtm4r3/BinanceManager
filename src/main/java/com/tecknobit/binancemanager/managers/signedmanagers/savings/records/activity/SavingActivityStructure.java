package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SavingActivityStructure} class is useful to format a saving activity structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
 *             Get Fixed and Activity Project List(USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-activity-project-position-user_data">
 *             Get Fixed/Activity Project Position (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see SavingStructure
 */
@Structure
public abstract class SavingActivityStructure extends SavingStructure {

    /**
     * {@code SavingActivityType} list of available saving activity types
     */
    public enum SavingActivityType {

        /**
         * {@code DAILY} saving activity type
         */
        DAILY,

        /**
         * {@code ACTIVITY} saving activity type
         */
        ACTIVITY,

        /**
         * {@code CUSTOMIZED_FIXED} saving activity type
         */
        CUSTOMIZED_FIXED

    }

    /**
     * {@code duration} of the saving activity
     */
    protected final int duration;

    /**
     * {@code interestRate} interest rate of the saving activity
     */
    protected final double interestRate;

    /**
     * {@code projectId} project id of the saving activity
     */
    protected final String projectId;

    /**
     * {@code projectName} project name of the saving activity
     */
    protected final String projectName;

    /**
     * {@code status} of the saving activity
     */

    protected final SavingStatus status;

    /**
     * {@code type} of the saving activity
     */
    protected final SavingActivityType type;

    /**
     * Constructor to init {@link SavingActivityStructure} object
     *
     * @param asset:            asset of the saving activity
     * @param duration:         of the saving activity
     * @param interestRate:     interest rate of the saving activity
     * @param projectId:project id of the saving activity
     * @param projectName:      project name of the saving activity
     * @param status:           status of the saving activity
     * @param type:             type of the saving activity
     */
    public SavingActivityStructure(String asset, int duration, double interestRate, String projectId, String projectName,
                                   SavingStatus status, SavingActivityType type) {
        super(asset);
        this.duration = duration;
        this.interestRate = interestRate;
        this.projectId = projectId;
        this.projectName = projectName;
        this.status = status;
        this.type = type;
    }

    /**
     * Constructor to init {@link SavingActivityStructure} object
     *
     * @param jSavingProductStructure: saving activity structure details as {@link JSONObject}
     */
    public SavingActivityStructure(JSONObject jSavingProductStructure) {
        super(jSavingProductStructure);
        duration = hItem.getInt("duration", 0);
        interestRate = hItem.getDouble("interestRate", 0);
        projectId = hItem.getString("projectId");
        projectName = hItem.getString("projectName");
        status = SavingStatus.valueOf(hItem.getString("status"));
        type = SavingActivityType.valueOf(hItem.getString("type"));
    }

    /**
     * Method to get {@link #duration} instance <br>
     * No-any params required
     *
     * @return {@link #duration} instance as int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Method to get {@link #interestRate} instance <br>
     * No-any params required
     *
     * @return {@link #interestRate} instance as double
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Method to get {@link #interestRate} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interestRate} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getInterestRate(int decimals) {
        return roundValue(interestRate, decimals);
    }

    /**
     * Method to get {@link #projectId} instance <br>
     * No-any params required
     *
     * @return {@link #projectId} instance as {@link String}
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Method to get {@link #projectName} instance <br>
     * No-any params required
     *
     * @return {@link #projectName} instance as {@link String}
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link SavingStatus}
     */
    public SavingStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link SavingActivityType}
     */
    public SavingActivityType getType() {
        return type;
    }

}
