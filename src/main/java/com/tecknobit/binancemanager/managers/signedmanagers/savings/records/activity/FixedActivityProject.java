package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.activity;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FixedActivityProject} class is useful to format a fixed activity/project
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fixed-and-activity-project-list-user_data">
 * Get Fixed and Activity Project List(USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingActivityStructure
 **/
public class FixedActivityProject extends SavingActivityStructure {

    /**
     * {@code SortBy} list of available sorters
     **/
    public enum SortBy {

        /**
         * {@code START_TIME} sorter
         **/
        START_TIME,

        /**
         * {@code LOT_SIZE} sorter
         **/
        LOT_SIZE,

        /**
         * {@code INTEREST_RATE} sorter
         **/
        INTEREST_RATE,

        /**
         * {@code DURATION} sorter
         **/
        DURATION

    }

    /**
     * {@code displayPriority} display priority of the fixed activity/project
     **/
    private final int displayPriority;

    /**
     * {@code interestPerLot} interest per-lot of the fixed activity/project
     **/
    private final double interestPerLot;

    /**
     * {@code lotSize} lot size of the fixed activity/project
     **/
    private final double lotSize;

    /**
     * {@code lotsLowLimit} lots low limit of the fixed activity/project
     **/
    private final int lotsLowLimit;

    /**
     * {@code lotsPurchased} lots purchased of the fixed activity/project
     **/
    private final int lotsPurchased;

    /**
     * {@code lotsUpLimit} lots up limit of the fixed activity/project
     **/
    private final int lotsUpLimit;

    /**
     * {@code maxLotsPerUser} max lots per user of the fixed activity/project
     **/
    private final int maxLotsPerUser;

    /**
     * {@code needKyc} whether the fixed activity/project need kyc
     **/
    private final boolean needKyc;

    /**
     * {@code withAreaLimitation} whether the fixed activity/project is with area limitation
     **/
    private final boolean withAreaLimitation;

    /**
     * Constructor to init {@link FixedActivityProject} object
     *
     * @param asset:              asset of the fixed activity/project
     * @param duration:           of the fixed activity/project
     * @param interestRate:       interest rate of the fixed activity/project
     * @param projectId:project   id of the fixed activity/project
     * @param projectName:        project name of the fixed activity/project
     * @param status:             status of the fixed activity/project
     * @param type:               type of the fixed activity/project
     * @param displayPriority:    display priority of the fixed activity/project
     * @param interestPerLot:     interest per-lot of the fixed activity/project
     * @param lotSize:            lot size of the fixed activity/project
     * @param lotsLowLimit:       lots low limit of the fixed activity/project
     * @param lotsPurchased:      lots purchased of the fixed activity/project
     * @param lotsUpLimit:        lots up limit of the fixed activity/project
     * @param maxLotsPerUser:     max lots per user of the fixed activity/project
     * @param needKyc:            whether the fixed activity/project need kyc
     * @param withAreaLimitation: whether the fixed activity/project is with area limitation
     **/
    public FixedActivityProject(String asset, int duration, double interestRate, String projectId, String projectName,
                                SavingStatus status, SavingActivityType type, int displayPriority, double interestPerLot,
                                double lotSize, int lotsLowLimit, int lotsPurchased, int lotsUpLimit, int maxLotsPerUser,
                                boolean needKyc, boolean withAreaLimitation) {
        super(asset, duration, interestRate, projectId, projectName, status, type);
        this.displayPriority = displayPriority;
        this.interestPerLot = interestPerLot;
        this.lotSize = lotSize;
        this.lotsLowLimit = lotsLowLimit;
        this.lotsPurchased = lotsPurchased;
        this.lotsUpLimit = lotsUpLimit;
        this.maxLotsPerUser = maxLotsPerUser;
        this.needKyc = needKyc;
        this.withAreaLimitation = withAreaLimitation;
    }

    /**
     * Constructor to init {@link FixedActivityProject} object
     *
     * @param jFixedActivityProject: fixed activity/project details as {@link JSONObject}
     **/
    public FixedActivityProject(JSONObject jFixedActivityProject) {
        super(jFixedActivityProject);
        displayPriority = hItem.getInt("displayPriority", 0);
        interestPerLot = hItem.getDouble("interestPerLot", 0);
        lotSize = hItem.getDouble("lotSize", 0);
        lotsLowLimit = hItem.getInt("lotsLowLimit", 0);
        lotsPurchased = hItem.getInt("lotsPurchased", 0);
        lotsUpLimit = hItem.getInt("lotsUpLimit", 0);
        maxLotsPerUser = hItem.getInt("maxLotsPerUser", 0);
        needKyc = hItem.getBoolean("needKyc");
        withAreaLimitation = hItem.getBoolean("withAreaLimitation");
    }

    /**
     * Method to get {@link #displayPriority} instance <br>
     * No-any params required
     *
     * @return {@link #displayPriority} instance as int
     **/
    public int getDisplayPriority() {
        return displayPriority;
    }

    /**
     * Method to get {@link #interestPerLot} instance <br>
     * No-any params required
     *
     * @return {@link #interestPerLot} instance as double
     **/
    public double getInterestPerLot() {
        return interestPerLot;
    }

    /**
     * Method to get {@link #interestPerLot} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #interestPerLot} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getInterestPerLot(int decimals) {
        return roundValue(interestPerLot, decimals);
    }

    /**
     * Method to get {@link #lotSize} instance <br>
     * No-any params required
     *
     * @return {@link #lotSize} instance as double
     **/
    public double getLotSize() {
        return lotSize;
    }

    /**
     * Method to get {@link #lotSize} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #lotSize} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getLotSize(int decimals) {
        return roundValue(lotSize, decimals);
    }

    /**
     * Method to get {@link #lotsLowLimit} instance <br>
     * No-any params required
     *
     * @return {@link #lotsLowLimit} instance as int
     **/
    public int getLotsLowLimit() {
        return lotsLowLimit;
    }

    /**
     * Method to get {@link #lotsPurchased} instance <br>
     * No-any params required
     *
     * @return {@link #lotsPurchased} instance as int
     **/
    public int getLotsPurchased() {
        return lotsPurchased;
    }

    /**
     * Method to get {@link #lotsUpLimit} instance <br>
     * No-any params required
     *
     * @return {@link #lotsUpLimit} instance as int
     **/
    public int getLotsUpLimit() {
        return lotsUpLimit;
    }

    /**
     * Method to get {@link #maxLotsPerUser} instance <br>
     * No-any params required
     *
     * @return {@link #maxLotsPerUser} instance as int
     **/
    public int getMaxLotsPerUser() {
        return maxLotsPerUser;
    }

    /**
     * Method to get {@link #needKyc} instance <br>
     * No-any params required
     *
     * @return {@link #needKyc} instance as boolean
     **/
    public boolean isNeededKyc() {
        return needKyc;
    }

    /**
     * Method to get {@link #withAreaLimitation} instance <br>
     * No-any params required
     *
     * @return {@link #withAreaLimitation} instance as boolean
     **/
    public boolean isWithAreaLimitation() {
        return withAreaLimitation;
    }

}
