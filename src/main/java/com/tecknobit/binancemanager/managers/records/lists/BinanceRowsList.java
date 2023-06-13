package com.tecknobit.binancemanager.managers.records.lists;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Status;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code BinanceRowsList} class is useful to create a {@code "Binance"}'s rows list
 *
 * @param <T> type of the item to insert in the list
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 */
@Structure
public abstract class BinanceRowsList<T> extends BinanceItem {

    /**
     * {@code total} number of items
     */
    protected int total;

    /**
     * {@code rows} list of the items
     */
    protected final ArrayList<T> rows;

    /**
     * Constructor to init {@link BinanceRowsList} object
     *
     * @param total: number of items
     * @param rows:  list of the items
     */
    public BinanceRowsList(int total, ArrayList<T> rows) {
        super(null);
        this.total = total;
        this.rows = rows;
    }

    /**
     * Constructor to init {@link BinanceRowsList}
     *
     * @param jList: list details as {@link JSONObject}
     */
    public BinanceRowsList(JSONObject jList) {
        super(jList);
        total = hItem.getInt("total", 0);
        rows = new ArrayList<>();
    }

    /**
     * Method to get {@link #total} instance <br>
     * No-any params required
     *
     * @return {@link #total} instance as int
     */
    public int getTotal() {
        return total;
    }

    /**
     * Method to get {@link #rows} instance <br>
     * No-any params required
     *
     * @return {@link #rows} instance as {@link ArrayList} of {@link T}
     */
    public ArrayList<T> getRows() {
        return rows;
    }

    /**
     * Method to add an item to {@link #rows}
     *
     * @param row: row to add
     */
    public void addRow(T row) {
        if (!rows.contains(row)) {
            rows.add(row);
            total += 1;
        }
    }

    /**
     * Method to remove a row from {@link #rows}
     *
     * @param row: row to remove
     * @return result of operation as boolean
     */
    public boolean removeRow(T row) {
        boolean removed = rows.remove(row);
        if (removed)
            total -= 1;
        return removed;
    }

    /**
     * Method to get a row from {@link #rows} list
     *
     * @param index: index to fetch the row
     * @return row as {@link T}
     */
    public T getRow(int index) {
        return rows.get(index);
    }

    /**
     * The {@code MarginListItem} class is useful to create a {@code "Binance"}'s margin list item
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public static class MarginListItem {

        /**
         * {@code symbol} is instance that memorizes asset
         */
        protected final String asset;

        /**
         * {@code txId} is instance that memorizes identifier of transaction
         */
        protected final long txId;

        /**
         * {@code timestamp} is instance that memorizes timestamp of transaction
         */
        protected final long timestamp;

        /**
         * {@code status} is instance that memorizes status of transaction
         */
        protected final Status status;

        /**
         * Constructor to init {@link MarginListItem} object
         *
         * @param asset:     asset
         * @param txId:      identifier of transaction
         * @param timestamp: timestamp of transaction
         * @param status:    status of transaction
         * @throws IllegalArgumentException if parameters range is not respected
         */
        public MarginListItem(String asset, long txId, long timestamp, Status status) {
            this.asset = asset;
            this.txId = txId;
            if (timestamp < 0)
                throw new IllegalArgumentException("Timestamp value cannot be less than 0");
            else
                this.timestamp = timestamp;
            this.status = status;
        }

        /**
         * Constructor to init {@link MarginListItem} object
         *
         * @param marginList: margin assets list details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         */
        public MarginListItem(JSONObject marginList) {
            this(marginList.getString("asset"), marginList.getLong("txId"), marginList.getLong("timestamp"),
                    Status.valueOf(marginList.getString("status")));
        }

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         */
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #txId} instance <br>
         * No-any params required
         *
         * @return {@link #txId} instance as long
         */
        public long getTxId() {
            return txId;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as long
         */
        public long getTimestamp() {
            return timestamp;
        }

        /**
         * Method to get {@link #timestamp} instance <br>
         * No-any params required
         *
         * @return {@link #timestamp} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(timestamp);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link Status}
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
