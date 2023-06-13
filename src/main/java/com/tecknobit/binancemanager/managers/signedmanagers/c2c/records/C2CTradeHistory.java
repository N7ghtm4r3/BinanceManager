package com.tecknobit.binancemanager.managers.signedmanagers.c2c.records;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceDataList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.c2c.records.C2CTradeHistory.C2CTrade;

/**
 * The {@code C2CTradeHistory} class is useful to format a C2C trade history
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-c2c-trade-history-user_data">
 * Get C2C Trade History (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceDataList
 * @see BinanceResponse
 */
public class C2CTradeHistory extends BinanceDataList<C2CTrade> {

    /**
     * Constructor to init {@link C2CTradeHistory} object
     *
     * @param total   : number of items
     * @param success :  whether the list is created with success
     * @param data    :  {@link C2CTrade} in the list
     */
    public C2CTradeHistory(int total, boolean success, ArrayList<C2CTrade> data) {
        super(total, success, data);
    }

    /**
     * Constructor to init {@link C2CTradeHistory} object
     *
     * @param jC2CTrades : C2C Trades list details as {@link JSONObject}
     */
    public C2CTradeHistory(JSONObject jC2CTrades) {
        super(jC2CTrades);
        JSONArray jData = hItem.getJSONArray("data", new JSONArray());
        for (int j = 0; j < jData.length(); j++)
            data.add(new C2CTrade(jData.getJSONObject(j)));
    }

    /**
     * The {@code C2CTrade} class is useful to format a C2C Trade
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class C2CTrade extends BinanceItem {

        /**
         * {@code C2CStatus} list of available C2C statuses
         */
        public enum C2CStatus {

            /**
             * {@code PENDING} C2C status
             */
            PENDING,

            /**
             * {@code TRADING} C2C status
             */
            TRADING,

            /**
             * {@code BUYER_PAYED} C2C status
             */
            BUYER_PAYED,

            /**
             * {@code DISTRIBUTING} C2C status
             */
            DISTRIBUTING,

            /**
             * {@code COMPLETED} C2C status
             */
            COMPLETED,

            /**
             * {@code IN_APPEAL} C2C status
             */
            IN_APPEAL,

            /**
             * {@code CANCELLED} C2C status
             */
            CANCELLED,

            /**
             * {@code CANCELLED_BY_SYSTEM} C2C status
             */
            CANCELLED_BY_SYSTEM

        }

        /**
         * {@code orderNumber} order number of the C2C trade
         */
        private final String orderNumber;

        /**
         * {@code advNo} adv number of the C2C trade
         */
        private final String advNo;

        /**
         * {@code traderType} trader type of the C2C trade
         */
        private final Side traderType;

        /**
         * {@code asset} of the C2C trade
         */
        private final String asset;

        /**
         * {@code fiat} fiat used int the C2C trade
         */
        private final String fiat;

        /**
         * {@code fiatSymbol} symbol of {@link #fiat}
         */
        private final String fiatSymbol;

        /**
         * {@code amount} quantity (in Crypto)
         */
        private final double amount;

        /**
         * {@code totalPrice} total price in the C2C trade
         */
        private final double totalPrice;

        /**
         * {@code unitPrice} unit Price (in Fiat)
         */
        private final double unitPrice;

        /**
         * {@code orderStatus} status of the C2C trade
         */
        private final C2CStatus orderStatus;

        /**
         * {@code createTime} creation time of the C2C trade
         */
        private final long createTime;

        /**
         * {@code commission} transaction Fee (in Crypto)
         */
        private final double commission;

        /**
         * {@code counterPartNickName} counter part nickname of the C2C trade
         */
        private final String counterPartNickName;

        /**
         * {@code advertisementRole} advertisement role of the C2C trade
         */
        private final String advertisementRole;

        /**
         * Constructor to init {@link C2CTrade} object
         *
         * @param orderNumber:         order number of the C2C trade
         * @param advNo:               adv number of the C2C trade
         * @param traderType:          trader type of the C2C trade
         * @param asset:               asset of the C2C trade
         * @param fiat:                fiat used int the C2C trade
         * @param fiatSymbol:          symbol of {@link #fiat}
         * @param amount:              quantity (in Crypto)
         * @param totalPrice:          total price in the C2C trade
         * @param unitPrice:           unit Price (in Fiat)
         * @param orderStatus:         status of the C2C trade
         * @param createTime:          creation time of the C2C trade
         * @param commission:          transaction Fee (in Crypto)
         * @param counterPartNickName: counterpart nickname of the C2C trade
         * @param advertisementRole:   advertisement role of the C2C trade
         */
        public C2CTrade(String orderNumber, String advNo, Side traderType, String asset, String fiat, String fiatSymbol,
                        double amount, double totalPrice, double unitPrice, C2CStatus orderStatus, long createTime,
                        double commission, String counterPartNickName, String advertisementRole) {
            super(null);
            this.orderNumber = orderNumber;
            this.advNo = advNo;
            this.traderType = traderType;
            this.asset = asset;
            this.fiat = fiat;
            this.fiatSymbol = fiatSymbol;
            this.amount = amount;
            this.totalPrice = totalPrice;
            this.unitPrice = unitPrice;
            this.orderStatus = orderStatus;
            this.createTime = createTime;
            this.commission = commission;
            this.counterPartNickName = counterPartNickName;
            this.advertisementRole = advertisementRole;
        }

        /**
         * Constructor to init {@link C2CTrade} object
         *
         * @param jC2CTrade: C2C trade details as {@link JSONObject}
         */
        public C2CTrade(JSONObject jC2CTrade) {
            super(jC2CTrade);
            orderNumber = hItem.getString("orderNumber");
            advNo = hItem.getString("advNo");
            traderType = Side.valueOf(hItem.getString("tradeType"));
            asset = hItem.getString("asset");
            fiat = hItem.getString("fiat");
            fiatSymbol = hItem.getString("fiatSymbol");
            amount = hItem.getDouble("amount", 0);
            totalPrice = hItem.getDouble("totalPrice", 0);
            unitPrice = hItem.getDouble("unitPrice", 0);
            orderStatus = C2CStatus.valueOf(hItem.getString("orderStatus"));
            createTime = hItem.getLong("createTime", 0);
            commission = hItem.getDouble("commission", 0);
            counterPartNickName = hItem.getString("counterPartNickName");
            advertisementRole = hItem.getString("advertisementRole");
        }

        /**
         * Method to get {@link #orderNumber} instance <br>
         * No-any params required
         *
         * @return {@link #orderNumber} instance as {@link String}
         */
        public String getOrderNumber() {
            return orderNumber;
        }

        /**
         * Method to get {@link #advNo} instance <br>
         * No-any params required
         *
         * @return {@link #advNo} instance as {@link String}
         */
        public String getAdvNo() {
            return advNo;
        }

        /**
         * Method to get {@link #traderType} instance <br>
         * No-any params required
         *
         * @return {@link #traderType} instance as {@link Side}
         */
        public Side getTraderType() {
            return traderType;
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
         * Method to get {@link #fiat} instance <br>
         * No-any params required
         *
         * @return {@link #fiat} instance as {@link String}
         */
        public String getFiat() {
            return fiat;
        }

        /**
         * Method to get {@link #fiatSymbol} instance <br>
         * No-any params required
         *
         * @return {@link #fiatSymbol} instance as {@link String}
         */
        public String getFiatSymbol() {
            return fiatSymbol;
        }

        /**
         * Method to get {@link #amount} instance <br>
         * No-any params required
         *
         * @return {@link #amount} instance as double
         */
        public double getAmount() {
            return amount;
        }

        /**
         * Method to get {@link #amount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #amount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAmount(int decimals) {
            return roundValue(amount, decimals);
        }

        /**
         * Method to get {@link #totalPrice} instance <br>
         * No-any params required
         *
         * @return {@link #totalPrice} instance as double
         */
        public double getTotalPrice() {
            return totalPrice;
        }

        /**
         * Method to get {@link #totalPrice} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalPrice} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalPrice(int decimals) {
            return roundValue(totalPrice, decimals);
        }

        /**
         * Method to get {@link #unitPrice} instance <br>
         * No-any params required
         *
         * @return {@link #unitPrice} instance as double
         */
        public double getUnitPrice() {
            return unitPrice;
        }

        /**
         * Method to get {@link #unitPrice} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #unitPrice} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getUnitPrice(int decimals) {
            return roundValue(unitPrice, decimals);
        }

        /**
         * Method to get {@link #orderStatus} instance <br>
         * No-any params required
         *
         * @return {@link #orderStatus} instance as {@link C2CStatus}
         */
        public C2CStatus getOrderStatus() {
            return orderStatus;
        }

        /**
         * Method to get {@link #commission} instance <br>
         * No-any params required
         *
         * @return {@link #commission} instance as double
         */
        public double getCommission() {
            return commission;
        }

        /**
         * Method to get {@link #commission} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #commission} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getCommission(int decimals) {
            return roundValue(commission, decimals);
        }

        /**
         * Method to get {@link #counterPartNickName} instance <br>
         * No-any params required
         *
         * @return {@link #counterPartNickName} instance as {@link String}
         */
        public String getCounterPartNickName() {
            return counterPartNickName;
        }

        /**
         * Method to get {@link #advertisementRole} instance <br>
         * No-any params required
         *
         * @return {@link #advertisementRole} instance as {@link String}
         */
        public String getAdvertisementRole() {
            return advertisementRole;
        }

    }

}
