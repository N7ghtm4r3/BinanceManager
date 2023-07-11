package com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.locked;

import com.tecknobit.binancemanager.managers.signedmanagers.simpleearn.records.flexible.FlexibleProductSubscription;
import org.json.JSONObject;

public class LockedProductSubscription extends FlexibleProductSubscription {

    private final long positionId;

    public LockedProductSubscription(long purchaseId, boolean success, long positionId) {
        super(purchaseId, success);
        this.positionId = positionId;
    }

    public LockedProductSubscription(JSONObject jLockedProductSubscription) {
        super(jLockedProductSubscription);
        this.positionId = hItem.getLong("positionId", 0);
    }

    public long getPositionId() {
        return positionId;
    }

}

