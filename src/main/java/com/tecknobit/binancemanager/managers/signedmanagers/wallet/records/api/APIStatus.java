package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.api;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.orders.details.SpotOrderDetails;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The {@code APIStatus} class is useful to format a {@code "Binance"} API status
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
 * Account API Trading Status (USER_DATA)</a>
 **/
public class APIStatus {

    /**
     * {@code isLocked} is instance that memorizes if api status is locked
     * **/
    private boolean isLocked;

    /**
     * {@code plannedRecoverTime} is instance that memorizes planned recover time
     * **/
    private int plannedRecoverTime;

    /**
     * {@code triggerConditions} is instance that memorizes triggers condition values
     **/
    private HashMap<String, Integer> triggerConditions;

    /**
     * {@code updateTime} is instance that memorizes update time value
     * **/
    private long updateTime;

    /**
     * {@code keys} is instance that memorizes keys values
     * **/
    private ArrayList<String> keys;

    /**
     * {@code values} is instance that memorizes values
     * **/
    private ArrayList<Integer> values;

    /**
     * Constructor to init {@link APIStatus} object
     *
     * @param isLocked:           api status is locked
     * @param plannedRecoverTime: planned recover time
     * @param triggerConditions:  triggers condition values
     * @param updateTime:         update time value
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public APIStatus(boolean isLocked, int plannedRecoverTime, HashMap<String, Integer> triggerConditions, long updateTime) {
        this.isLocked = isLocked;
        if (plannedRecoverTime < 0)
            throw new IllegalArgumentException("Planned recover time value cannot be less than 0");
        else
            this.plannedRecoverTime = plannedRecoverTime;
        this.triggerConditions = triggerConditions;
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        else
            this.updateTime = updateTime;
        loadListsValues();
    }

    /**
     * Constructor to init {@link APIStatus} object
     *
     * @param apiStatus: api status details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public APIStatus(JSONObject apiStatus) {
        isLocked = apiStatus.getBoolean("isLocked");
        plannedRecoverTime = apiStatus.getInt("plannedRecoverTime");
        if (plannedRecoverTime < 0)
            throw new IllegalArgumentException("Planned recover time value cannot be less than 0");
        updateTime = apiStatus.getLong("updateTime");
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        triggerConditions = new HashMap<>();
        JSONObject jTriggerConditions = apiStatus.getJSONObject("triggerCondition");
        for (String key : jTriggerConditions.keySet())
            triggerConditions.put(key, jTriggerConditions.getInt(key));
        loadListsValues();
    }

    /**
     * Method to get {@link #isLocked} instance <br>
     * Any params required
     *
     * @return {@link #isLocked} instance as boolean
     **/
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Method to set {@link #isLocked}
     *
     * @param locked: if api status is locked
     **/
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * Method to get {@link #plannedRecoverTime} instance <br>
     * Any params required
     *
     * @return {@link #plannedRecoverTime} instance as int
     **/
    public int plannedRecoverTime() {
        return plannedRecoverTime;
    }

    /**
     * Method to set {@link #plannedRecoverTime}
     *
     * @param plannedRecoverTime: planned recover time
     * @throws IllegalArgumentException when planned recover time value is less than 0
     **/
    public void setPlannedRecoverTime(int plannedRecoverTime) {
        if (plannedRecoverTime < 0)
            throw new IllegalArgumentException("Planned recover time value cannot be less than 0");
        this.plannedRecoverTime = plannedRecoverTime;
    }

    /**
     * Method to get {@link #triggerConditions} instance <br>
     * Any params required
     *
     * @return {@link #triggerConditions} instance as {@link HashMap} of {@link Integer}
     **/
    public HashMap<String, Integer> triggerCondition() {
        return triggerConditions;
    }

    /**
     * Method to set {@link #triggerConditions}
     *
     * @param triggerConditions: trigger condition
     **/
    public void setTriggerConditions(HashMap<String, Integer> triggerConditions) {
        this.triggerConditions = triggerConditions;
        loadListsValues();
    }

    /**
     * Method to get {@link #keys} instance <br>
     * Any params required
     *
     * @return {@link #keys} instance as {@link ArrayList} of {@link String}
     **/
    public ArrayList<String> getTriggerConditionKeys() {
        return keys;
    }

    /**
     * Method to get {@link #values} instance <br>
     * Any params required
     *
     * @return {@link #values} instance as {@link ArrayList} of {@link Integer}
     **/
    public ArrayList<Integer> getTriggerConditionValues() {
        return values;
    }

    /**
     * Method to get a trigger condition key from {@link #keys} list
     *
     * @param index: index to fetch the trigger condition key
     * @return trigger condition key as {@link String}
     **/
    public String getTriggerConditionKey(int index) {
        return keys.get(index);
    }

    /**
     * Method to get a trigger condition value from {@link #triggerConditions} list
     *
     * @param key: key from fetch the trigger condition value
     * @return trigger condition value as {@link Integer}
     **/
    public Integer getTriggerConditionValue(String key) {
        return triggerConditions.get(key);
    }

    /**
     * Method to get a spot order details from {@link #values} list
     *
     * @param index: index to fetch the composed spot order details
     * @return spot order details as {@link SpotOrderDetails}
     **/
    public Integer getTriggerConditionValue(int index) {
        return values.get(index);
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * Any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to set {@link #updateTime}
     *
     * @param updateTime: update time value
     * @throws IllegalArgumentException when update time value is less than 0
     **/
    public void setUpdateTime(long updateTime) {
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * Any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to set load triggers list  <br>
     * Any params required
     **/
    private void loadListsValues() {
        keys = new ArrayList<>(triggerConditions.keySet());
        values = new ArrayList<>(triggerConditions.values());
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
