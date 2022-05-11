package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.API;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code APIStatus} class is useful to manage APIStatus Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class APIStatus {

    private boolean isLocked;
    private int plannedRecoverTime;
    private HashMap<String, Integer> triggerCondition;
    private long updateTime;
    private ArrayList<String> keys;
    private ArrayList<Integer> values;

    public APIStatus(boolean isLocked, int plannedRecoverTime, HashMap<String, Integer> triggerCondition, long updateTime) {
        this.isLocked = isLocked;
        this.plannedRecoverTime = plannedRecoverTime;
        this.triggerCondition = triggerCondition;
        this.updateTime = updateTime;
        loadListValue();
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
        loadListValue();
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

    public void setUpdateTime(long updateTime) {
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    private void loadListValue(){
        keys = new ArrayList<>(triggerCondition.keySet());
        values = new ArrayList<>(triggerCondition.values());
    }

}
