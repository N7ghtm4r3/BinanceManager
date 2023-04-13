package com.tecknobit.binancemanager.managers.signedmanagers.mining.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public abstract class DataListItem extends BinanceItem {

    protected final int totalNum;
    protected final int pageSize;

    public DataListItem(int totalNum, int pageSize) {
        super(null);
        this.totalNum = totalNum;
        this.pageSize = pageSize;
    }

    public DataListItem(JSONObject jDataListItem) {
        super(jDataListItem);
        totalNum = hItem.getInt("totalNum", 0);
        pageSize = hItem.getInt("pageSize", 0);
    }

    public int getTotalNum() {
        return totalNum;
    }

    public int getPageSize() {
        return pageSize;
    }

}
