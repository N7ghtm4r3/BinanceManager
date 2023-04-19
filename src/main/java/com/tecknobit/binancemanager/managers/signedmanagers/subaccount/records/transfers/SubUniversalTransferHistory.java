package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.transfers;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubUniversalTransferHistory extends BinanceItem {

    private final int totalCount;
    private final ArrayList<SubUniversalTransfer> universalTransfers;

    public SubUniversalTransferHistory(int totalCount, ArrayList<SubUniversalTransfer> universalTransfers) {
        super(null);
        this.totalCount = totalCount;
        this.universalTransfers = universalTransfers;
    }

    public SubUniversalTransferHistory(JSONObject jSubUniversalTransferHistory) {
        super(jSubUniversalTransferHistory);
        totalCount = hItem.getInt("totalCount", 0);
        universalTransfers = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("result");
        if (jList != null)
            for (JSONObject item : jList)
                universalTransfers.add(new SubUniversalTransfer(item));
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ArrayList<SubUniversalTransfer> getUniversalTransfers() {
        return universalTransfers;
    }

}
