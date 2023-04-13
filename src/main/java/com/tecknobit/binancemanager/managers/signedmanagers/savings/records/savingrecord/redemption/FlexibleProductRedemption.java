package com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.redemption;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.SavingStructure;
import com.tecknobit.binancemanager.managers.signedmanagers.savings.records.savingrecord.SavingRecordStructure;
import org.json.JSONObject;

/**
 * The {@code FlexibleProductRedemption} class is useful to format a flexible product redemption
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-redemption-record-user_data">
 * Get Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see SavingStructure
 * @see SavingRecordStructure
 * @see RedemptionRecord
 **/
public class FlexibleProductRedemption extends RedemptionRecord {

    /**
     * {@code RedemptionType} list of available redemption types
     **/
    public enum RedemptionType {

        /**
         * {@code FAST} redemption type
         **/
        FAST,

        /**
         * {@code NORMAL} redemption type
         **/
        NORMAL

    }

    /**
     * {@code type} type of the flexible product redemption
     **/
    private final RedemptionType type;

    /**
     * Constructor to init {@link FlexibleProductRedemption} object
     *
     * @param asset:       asset of the flexible product redemption
     * @param amount:      amount of the flexible product redemption
     * @param createTime:  create time of the flexible product redemption
     * @param status:      status of the flexible product redemption
     * @param principal:   principal of the flexible product redemption
     * @param projectId:   project id of the flexible product redemption
     * @param projectName: project name of the flexible product redemption
     * @param type:        type of the flexible product redemption
     **/
    public FlexibleProductRedemption(String asset, double amount, long createTime, String status, double principal,
                                     String projectId, String projectName, RedemptionType type) {
        super(asset, amount, createTime, status, principal, projectId, projectName);
        this.type = type;
    }

    /**
     * Constructor to init {@link FlexibleProductRedemption} object
     *
     * @param jFlexibleProductRedemption: flexible product redemption details as {@link JSONObject}
     **/
    public FlexibleProductRedemption(JSONObject jFlexibleProductRedemption) {
        super(jFlexibleProductRedemption);
        this.type = RedemptionType.valueOf(hItem.getString("type"));
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link RedemptionType}
     **/
    public RedemptionType getType() {
        return type;
    }

}
