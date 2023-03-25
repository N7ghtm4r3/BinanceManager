package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConvertTradeHistory extends BinanceItem {

    private final ArrayList<Convert> list;
    private final long startTime;
    private final long endTime;
    private final int limit;
    private final boolean moreData;

    public ConvertTradeHistory(ArrayList<Convert> list, long startTime, long endTime, int limit, boolean moreData) {
        super(null);
        this.list = list;
        this.startTime = startTime;
        this.endTime = endTime;
        this.limit = limit;
        this.moreData = moreData;
    }

    public ConvertTradeHistory(JSONObject jConvertTradeHistory) {
        super(jConvertTradeHistory);
        list = new ArrayList<>();
        JSONArray jList = hItem.getJSONArray("list", new JSONArray());
        for (int j = 0; j < jList.length(); j++)
            list.add(new Convert(jList.getJSONObject(j)));
        startTime = hItem.getLong("startTime", 0);
        endTime = hItem.getLong("endTime", 0);
        limit = hItem.getInt("limit", 0);
        moreData = hItem.getBoolean("moreData");
    }

    public ArrayList<Convert> getList() {
        return list;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isMoreData() {
        return moreData;
    }

}
