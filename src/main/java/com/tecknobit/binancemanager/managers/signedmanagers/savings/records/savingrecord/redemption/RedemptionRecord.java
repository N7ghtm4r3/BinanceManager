package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.SavingRecordStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RedemptionRecord} class is useful to format a redemption record
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
 * Get Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingRecordStructure
 **/
public abstract class RedemptionRecord extends SavingRecordStructure {

    /**
     * {@code principal} of the redemption record
     **/
    protected final double principal;

    /**
     * {@code projectId} project id of the redemption record
     **/
    protected final String projectId;

    /**
     * {@code projectName} project name of the redemption record
     **/
    protected final String projectName;

    /**
     * Constructor to init {@link RedemptionRecord} object
     *
     * @param asset:       asset of the saving redemption record
     * @param amount:      amount of the saving redemption record
     * @param createTime:  create time of the saving redemption record
     * @param status:      status of the saving redemption record
     * @param principal:   principal of the redemption record
     * @param projectId:   project id of the redemption record
     * @param projectName: project name of the redemption record
     **/
    public RedemptionRecord(String asset, double amount, long createTime, String status, double principal,
                            String projectId, String projectName) {
        super(asset, amount, createTime, status);
        this.principal = principal;
        this.projectId = projectId;
        this.projectName = projectName;
    }

    /**
     * Constructor to init {@link RedemptionRecord} object
     *
     * @param jRedemptionRecord: redemption record detailS as {@link JSONObject}
     **/
    public RedemptionRecord(JSONObject jRedemptionRecord) {
        super(jRedemptionRecord);
        principal = hItem.getDouble("principal", 0);
        projectId = hItem.getString("projectId");
        projectName = hItem.getString("projectName");
    }

    /**
     * Method to get {@link #principal} instance <br>
     * No-any params required
     *
     * @return {@link #principal} instance as double
     **/
    public double getPrincipal() {
        return principal;
    }

    /**
     * Method to get {@link #principal} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #principal} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getPrincipal(int decimals) {
        return roundValue(principal, decimals);
    }

    /**
     * Method to get {@link #projectId} instance <br>
     * No-any params required
     *
     * @return {@link #projectId} instance as {@link String}
     **/
    public String getProjectId() {
        return projectId;
    }

    /**
     * Method to get {@link #projectName} instance <br>
     * No-any params required
     *
     * @return {@link #projectName} instance as {@link String}
     **/
    public String getProjectName() {
        return projectName;
    }

}
