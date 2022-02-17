package com.tecknobit.binancemanager.Managers.Wallet.Records.API;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  The {@code APIStatus} class is useful to manage APIStatus Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-api-trading-status-user_data
 * **/

public class APIStatus {

    private final boolean isLocked;
    private final int plannedRecoverTime;
    private final HashMap<String, Integer> triggerCondition;
    private final long updateTime;

    public APIStatus(boolean isLocked, int plannedRecoverTime, HashMap<String, Integer> triggerCondition, long updateTime) {
        this.isLocked = isLocked;
        this.plannedRecoverTime = plannedRecoverTime;
        this.triggerCondition = triggerCondition;
        this.updateTime = updateTime;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int plannedRecoverTime() {
        return plannedRecoverTime;
    }

    public HashMap<String, Integer> triggerCondition() {
        return triggerCondition;
    }

    public ArrayList<String> getTriggerConditionKeys() {
        return new ArrayList<>(triggerCondition.keySet());
    }

    public ArrayList<Integer> getTriggerConditionValues() {
        return new ArrayList<>(triggerCondition.values());
    }

    public String getTriggerConditionKey(int index) {
        return new ArrayList<>(triggerCondition.keySet()).get(index);
    }

    public Integer getTriggerConditionValue(String key) {
        return triggerCondition.get(key);
    }

    public Integer getTriggerConditionValue(int index) {
        return new ArrayList<>(triggerCondition.values()).get(index);
    }

    public long updateTime() {
        return updateTime;
    }

}
