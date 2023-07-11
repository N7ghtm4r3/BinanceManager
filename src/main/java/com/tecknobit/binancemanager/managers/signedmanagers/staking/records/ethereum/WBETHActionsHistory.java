package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum.WBETHActionsHistory.WBETHAction;

/**
 * The {@code WBETHActionsHistory} class is useful to format a {@code Binance}'s WBETH wrap/unwrap history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-wrap-history-user_data">
 *             Get WBETH wrap history (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-wbeth-unwrap-history-user_data">
 *             Get WBETH unwrap history (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class WBETHActionsHistory extends BinanceRowsList<WBETHAction> {

    /**
     * Constructor to init {@link WBETHActionsHistory} object
     *
     * @param rows : list of the items
     */
    public WBETHActionsHistory(ArrayList<WBETHAction> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link WBETHActionsHistory} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public WBETHActionsHistory(int total, ArrayList<WBETHAction> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link WBETHActionsHistory}
     *
     * @param jWBETHActionsHistory : list details as {@link JSONObject}
     */
    public WBETHActionsHistory(JSONObject jWBETHActionsHistory) {
        super(jWBETHActionsHistory);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new WBETHAction((JSONObject) row));
    }

    /**
     * The {@code WBETHAction} class is useful to format a {@code Binance}'s WBETH wrap/unwrap item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class WBETHAction extends BinanceItem {

        /**
         * {@code time} of the action
         */
        private final long time;

        /**
         * {@code fromAsset} from asset value
         */
        private final String fromAsset;

        /**
         * {@code fromAmount} from amount value
         */
        private final double fromAmount;

        /**
         * {@code toAsset} to asset value
         */
        private final String toAsset;

        /**
         * {@code toAmount} to amount value
         */
        private final double toAmount;

        /**
         * {@code exchangeRate} BETH value per 1 WBETH
         */
        private final double exchangeRate;

        /**
         * {@code status} of the action
         */
        private final String status;

        /**
         * Constructor to init a {@link WBETHAction} object
         * @param time: time of the action
         * @param fromAsset: from asset value
         * @param fromAmount: from amount value
         * @param toAsset: to asset value
         * @param toAmount: to amount value
         * @param exchangeRate: BETH value per 1 WBETH
         * @param status: status of the action
         *
         */
        public WBETHAction(long time, String fromAsset, double fromAmount, String toAsset, double toAmount,
                           double exchangeRate, String status) {
            super(null);
            this.time = time;
            this.fromAsset = fromAsset;
            this.fromAmount = fromAmount;
            this.toAsset = toAsset;
            this.toAmount = toAmount;
            this.exchangeRate = exchangeRate;
            this.status = status;
        }

        /**
         * Constructor to init a {@link WBETHAction} object
         * @param jWBETHAction: WBETH wrap/unwrap item details as {@link JSONObject}
         *
         */
        public WBETHAction(JSONObject jWBETHAction) {
            super(jWBETHAction);
            time = hItem.getLong("time", 0);
            fromAsset = hItem.getString("fromAsset");
            fromAmount = hItem.getDouble("fromAmount", 0);
            toAsset = hItem.getString("toAsset");
            toAmount = hItem.getDouble("toAmount", 0);
            exchangeRate = hItem.getDouble("exchangeRate", 0);
            status = hItem.getString("status");
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as long
         */
        public long getTime() {
            return time;
        }

        /**
         * Method to get {@link #time} instance <br>
         * No-any params required
         *
         * @return {@link #time} instance as {@link Date}
         */
        public Date getDate() {
            return TimeFormatter.getDate(time);
        }

        /**
         * Method to get {@link #fromAsset} instance <br>
         * No-any params required
         *
         * @return {@link #fromAsset} instance as {@link String}
         */
        public String getFromAsset() {
            return fromAsset;
        }

        /**
         * Method to get {@link #fromAmount} instance <br>
         * No-any params required
         *
         * @return {@link #fromAmount} instance as double
         */
        public double getFromAmount() {
            return fromAmount;
        }

        /**
         * Method to get {@link #fromAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #fromAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getFromAmount(int decimals) {
            return roundValue(fromAmount, decimals);
        }

        /**
         * Method to get {@link #toAsset} instance <br>
         * No-any params required
         *
         * @return {@link #toAsset} instance as {@link String}
         */
        public String getToAsset() {
            return toAsset;
        }

        /**
         * Method to get {@link #toAmount} instance <br>
         * No-any params required
         *
         * @return {@link #toAmount} instance as double
         */
        public double getToAmount() {
            return toAmount;
        }

        /**
         * Method to get {@link #toAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #toAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getToAmount(int decimals) {
            return roundValue(toAmount, decimals);
        }

        /**
         * Method to get {@link #exchangeRate} instance <br>
         * No-any params required
         *
         * @return {@link #exchangeRate} instance as double
         */
        public double getExchangeRate() {
            return exchangeRate;
        }

        /**
         * Method to get {@link #exchangeRate} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #exchangeRate} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getExchangeRate(int decimals) {
            return roundValue(exchangeRate, decimals);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         */
        public String getStatus() {
            return status;
        }

    }

}
