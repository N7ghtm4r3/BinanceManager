package com.tecknobit.binancemanager.managers.signedmanagers.mining.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code DataListItem} class is useful to create a data list item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#request-for-miner-list-user_data">
 *             Request for Miner List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#earnings-list-user_data">
 *             Earnings List(USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#extra-bonus-list-user_data">
 *             Extra Bonus List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
 *             Hashrate Resale List (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-detail-user_data">
 *             Hashrate Resale Detail (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#mining-account-earning-user_data">
 *             Mining Account Earning (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 **/
public abstract class DataListItem extends BinanceItem {

    /**
     * {@code totalNum} total num of the items
     **/
    protected final int totalNum;

    /**
     * {@code pageSize} page size of the items
     **/
    protected final int pageSize;

    /**
     * Constructor to init {@link DataListItem} object
     *
     * @param totalNum: total num of the items
     * @param pageSize: page size of the items
     **/
    public DataListItem(int totalNum, int pageSize) {
        super(null);
        this.totalNum = totalNum;
        this.pageSize = pageSize;
    }

    /**
     * Constructor to init {@link DataListItem} object
     *
     * @param jDataListItem: data list item details as {@link JSONObject}
     **/
    public DataListItem(JSONObject jDataListItem) {
        super(jDataListItem);
        totalNum = hItem.getInt("totalNum", 0);
        pageSize = hItem.getInt("pageSize", 0);
    }

    /**
     * Method to get {@link #totalNum} instance <br>
     * No-any params required
     *
     * @return {@link #totalNum} instance as int
     **/
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * Method to get {@link #pageSize} instance <br>
     * No-any params required
     *
     * @return {@link #pageSize} instance as int
     **/
    public int getPageSize() {
        return pageSize;
    }

}
