package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code APIStatus} class is useful to manage APIStatus Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

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
     * {@code triggerCondition} is instance that memorizes triggers condition values
     * **/
    private HashMap<String, Integer> triggerCondition;

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

    /** Constructor to init {@link APIStatus} object
     * @param isLocked: api status is locked
     * @param plannedRecoverTime: planned recover time
     * @param triggerCondition: triggers condition values
     * @param updateTime: update time value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public APIStatus(boolean isLocked, int plannedRecoverTime, HashMap<String, Integer> triggerCondition, long updateTime) {
        this.isLocked = isLocked;
        if(plannedRecoverTime < 0)
            throw new IllegalArgumentException("Planned recover time value cannot be less than 0");
        else
            this.plannedRecoverTime = plannedRecoverTime;
        this.triggerCondition = triggerCondition;
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        else
            this.updateTime = updateTime;
        loadListsValues();
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int plannedRecoverTime() {
        return plannedRecoverTime;
    }

    /** Method to set {@link #plannedRecoverTime}
     * @param plannedRecoverTime: planned recover time
     * @throws IllegalArgumentException when planned recover time value is less than 0
     * **/
    public void setPlannedRecoverTime(int plannedRecoverTime) {
        if(plannedRecoverTime < 0)
            throw new IllegalArgumentException("Planned recover time value cannot be less than 0");
        this.plannedRecoverTime = plannedRecoverTime;
    }

    public HashMap<String, Integer> triggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(HashMap<String, Integer> triggerCondition) {
        this.triggerCondition = triggerCondition;
        loadListsValues();
    }

    public ArrayList<String> getTriggerConditionKeys() {
        return keys;
    }

    public ArrayList<Integer> getTriggerConditionValues() {
        return values;
    }

    public String getTriggerConditionKey(int index) {
        return keys.get(index);
    }

    public Integer getTriggerConditionValue(String key) {
        return triggerCondition.get(key);
    }

    public Integer getTriggerConditionValue(int index) {
        return values.get(index);
    }

    public long updateTime() {
        return updateTime;
    }

    /** Method to set {@link #updateTime}
     * @param updateTime: update time value
     * @throws IllegalArgumentException when update time value is less than 0
     * **/
    public void setUpdateTime(long updateTime) {
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    /** Method to set load triggers list <br>
     * Any params required
     * **/
    private void loadListsValues(){
        keys = new ArrayList<>(triggerCondition.keySet());
        values = new ArrayList<>(triggerCondition.values());
    }

}
