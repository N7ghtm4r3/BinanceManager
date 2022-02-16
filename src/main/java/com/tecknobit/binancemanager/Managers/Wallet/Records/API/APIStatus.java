package com.tecknobit.binancemanager.Managers.Wallet.Records.API;

import java.util.ArrayList;
import java.util.HashMap;

public record APIStatus (boolean isLocked, int plannedRecoverTime, HashMap<String,Integer> triggerCondition, long updateTime) {

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public int plannedRecoverTime() {
        return plannedRecoverTime;
    }

    @Override
    public HashMap<String, Integer> triggerCondition() {
        return triggerCondition;
    }

    public ArrayList<String> getTriggerConditionKeys(){
        return new ArrayList<>(triggerCondition.keySet());
    }

    public ArrayList<Integer> getTriggerConditionValues(){
        return new ArrayList<>(triggerCondition.values());
    }

    public String getTriggerConditionKey(int index){
        return new ArrayList<>(triggerCondition.keySet()).get(index);
    }

    public Integer getTriggerConditionValue(String key){
        return triggerCondition.get(key);
    }

    public Integer getTriggerConditionValue(int index){
        return new ArrayList<>(triggerCondition.values()).get(index);
    }

    @Override
    public long updateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "APIStatus{" +
                "isLocked=" + isLocked +
                ", plannedRecoverTime=" + plannedRecoverTime +
                ", triggerCondition=" + triggerCondition +
                ", updateTime=" + updateTime +
                '}';
    }

}
