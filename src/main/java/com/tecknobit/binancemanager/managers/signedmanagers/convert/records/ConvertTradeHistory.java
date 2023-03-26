package com.tecknobit.binancemanager.managers.signedmanagers.convert.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code ConvertTradeHistory} class is useful to format a convert trade history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-convert-trade-history-user_data">
 * Get Convert Trade History (USER_DATA)</a>
 * @see BinanceItem
 **/
public class ConvertTradeHistory extends BinanceItem {

    /**
     * {@code list} of {@link Convert} of the history
     **/
    private final ArrayList<Convert> list;

    /**
     * {@code startTime} start time of the history
     **/
    private final long startTime;

    /**
     * {@code endTime} end time of the history
     **/
    private final long endTime;

    /**
     * {@code limit} of the history
     **/
    private final int limit;

    /**
     * {@code moreData} whether the history has more data
     **/
    private final boolean moreData;

    /**
     * Constructor to init {@link ConvertTradeHistory} object
     *
     * @param list:      list of {@link Convert} of the history
     * @param startTime: start time of the history
     * @param endTime:   end time of the history
     * @param limit:     limit of the history
     * @param moreData:  whether the history has more data
     **/
    public ConvertTradeHistory(ArrayList<Convert> list, long startTime, long endTime, int limit, boolean moreData) {
        super(null);
        this.list = list;
        this.startTime = startTime;
        this.endTime = endTime;
        this.limit = limit;
        this.moreData = moreData;
    }

    /**
     * Constructor to init {@link ConvertTradeHistory} object
     *
     * @param jConvertTradeHistory: convert trade history details as {@link JSONObject}
     **/
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

    /**
     * Method to get {@link #list} instance <br>
     * No-any params required
     *
     * @return {@link #list} instance as {@link ArrayList} of {@link Convert}
     **/
    public ArrayList<Convert> getList() {
        return list;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as long
     **/
    public long getStartTime() {
        return startTime;
    }

    /**
     * Method to get {@link #startTime} instance <br>
     * No-any params required
     *
     * @return {@link #startTime} instance as {@link Date}
     **/
    public Date getStartDate() {
        return TimeFormatter.getDate(startTime);
    }

    /**
     * Method to get {@link #endTime} instance <br>
     * No-any params required
     *
     * @return {@link #endTime} instance as long
     **/
    public long getEndTime() {
        return endTime;
    }

    /**
     * Method to get {@link #endTime} instance <br>
     * No-any params required
     *
     * @return {@link #endTime} instance as {@link Date}
     **/
    public Date getEndDate() {
        return TimeFormatter.getDate(endTime);
    }

    /**
     * Method to get {@link #limit} instance <br>
     * No-any params required
     *
     * @return {@link #limit} instance as int
     **/
    public int getLimit() {
        return limit;
    }

    /**
     * Method to get {@link #moreData} instance <br>
     * No-any params required
     *
     * @return {@link #moreData} instance as boolean
     **/
    public boolean isMoreData() {
        return moreData;
    }

}
