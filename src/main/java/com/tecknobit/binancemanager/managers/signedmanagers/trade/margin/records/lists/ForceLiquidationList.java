package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceList;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.Side;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.commons.Order.TimeInForce;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists.ForceLiquidationList.Liquidation;

/**
 * The {@code ForceLiquidationList} class is useful to format a {@code "Binance"}'s margin force liquidation
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-force-liquidation-record-user_data">
 * Get Force Liquidation Record (USER_DATA)</a>
 * @see BinanceList
 **/
public class ForceLiquidationList extends BinanceList<Liquidation> {

    /**
     * Constructor to init {@link ForceLiquidationList} object
     *
     * @param total:                 total size of force liquidations
     * @param liquidationAssetsList: list of {@link Liquidation}
     **/
    public ForceLiquidationList(int total, ArrayList<Liquidation> liquidationAssetsList) {
        super(total, liquidationAssetsList);
    }

    /**
     * Constructor to init {@link ForceLiquidationList} object
     *
     * @param jsonLiquidation: margin force liquidation details as {@link JSONObject}
     **/
    public ForceLiquidationList(JSONObject jsonLiquidation) {
        super(jsonLiquidation);
        JSONArray jList = hList.getJSONArray("rows", new JSONArray());
        for (int j = 0; j < jList.length(); j++)
            rows.add(new Liquidation(jList.getJSONObject(j)));
    }

    /**
     * The {@code Liquidation} class is useful to create a force liquidation type
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class Liquidation {

        /**
         * {@code orderId} is instance that memorizes identifier of order
         **/
        private final long orderId;
        /**
         * {@code symbol} is instance that memorizes symbol of order
         **/
        private final String symbol;
        /**
         * {@code timeInForce} is instance that memorizes time in force of order
         **/
        private final TimeInForce timeInForce;

        /**
         * {@code price} is instance that memorizes price of order
         **/
        private double price;

        /**
         * {@code qty} is instance that memorizes quantity of order
         **/
        private double qty;
        /**
         * {@code avgPrice} is instance that memorizes average price of liquidation asset
         **/
        private double avgPrice;
        /**
         * {@code executedQty} is instance that memorizes executed quantity of liquidation asset
         **/
        private double executedQty;
        /**
         * {@code side} is instance that memorizes side of order
         **/
        private Side side;

        /**
         * {@code isIsolated} is instance that memorizes if order is isolated
         **/
        private boolean isIsolated;

        /**
         * {@code updatedTime} is instance that memorizes time of order
         **/
        private long updatedTime;

        /**
         * Constructor to init {@link Liquidation} object
         *
         * @param avgPrice:    average price of liquidation asset
         * @param executedQty: executed quantity of liquidation asset
         * @param orderId:     identifier of order
         * @param price:       price of order
         * @param qty:         quantity of order
         * @param side:        side of order
         * @param symbol:      symbol of order
         * @param timeInForce: time in force of order
         * @param isIsolated:  order is isolated
         * @param updatedTime: time of order
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Liquidation(double avgPrice, double executedQty, long orderId, double price, double qty,
                           Side side, String symbol, TimeInForce timeInForce, boolean isIsolated,
                           long updatedTime) {
            if (avgPrice < 0)
                throw new IllegalArgumentException("Average price value cannot be less than 0");
            else
                this.avgPrice = avgPrice;
            if (executedQty < 0)
                throw new IllegalArgumentException("Executed quantity value cannot be less than 0");
            else
                this.executedQty = executedQty;
            this.orderId = orderId;
            if (price < 0)
                throw new IllegalArgumentException("Price value cannot be less than 0");
            else
                this.price = price;
            if (qty < 0)
                throw new IllegalArgumentException("Quantity value cannot be less than 0");
            else
                this.qty = qty;
            this.side = side;
            this.symbol = symbol;
            this.timeInForce = timeInForce;
            this.isIsolated = isIsolated;
            if (updatedTime < 0)
                throw new IllegalArgumentException("Updated time value cannot be less than 0");
            else
                this.updatedTime = updatedTime;
        }

        /**
         * Constructor to init {@link Liquidation} object
         *
         * @param forceLiquidationAsset: force liquidation asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public Liquidation(JSONObject forceLiquidationAsset) {
            this(forceLiquidationAsset.getDouble("avgPrice"), forceLiquidationAsset.getDouble("executedQty"),
                    forceLiquidationAsset.getLong("orderId"), forceLiquidationAsset.getDouble("price"),
                    forceLiquidationAsset.getDouble("qty"), Side.valueOf(forceLiquidationAsset.getString("side")),
                    forceLiquidationAsset.getString("symbol"),
                    TimeInForce.valueOf(forceLiquidationAsset.getString("timeInForce")),
                    forceLiquidationAsset.getBoolean("isIsolated"), forceLiquidationAsset.getLong("updatedTime"));
        }

        /**
         * Method to get {@link #avgPrice} instance <br>
         * Any params required
         *
         * @return {@link #avgPrice} instance as double
         **/
        public double getAvgPrice() {
            return avgPrice;
        }

        /**
         * Method to set {@link #avgPrice}
         *
         * @param avgPrice: average price of liquidation asset
         * @throws IllegalArgumentException when average price of liquidation asset value is less than 0
         **/
        public void setAvgPrice(double avgPrice) {
            if (avgPrice < 0)
                throw new IllegalArgumentException("Average price value cannot be less than 0");
            this.avgPrice = avgPrice;
        }

        /**
         * Method to get {@link #avgPrice} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #avgPrice} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getAvgPrice(int decimals) {
            return roundValue(avgPrice, decimals);
        }

        /**
         * Method to get {@link #executedQty} instance <br>
         * Any params required
         *
         * @return {@link #executedQty} instance as double
         **/
        public double getExecutedQty() {
            return executedQty;
        }

        /**
         * Method to set {@link #executedQty}
         *
         * @param executedQty: executed quantity of liquidation asset
         * @throws IllegalArgumentException when executed quantity of liquidation asset value is less than 0
         **/
        public void setExecutedQty(double executedQty) {
            if (executedQty < 0)
                throw new IllegalArgumentException("Executed quantity value cannot be less than 0");
            this.executedQty = executedQty;
        }

        /**
         * Method to get {@link #executedQty} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #executedQty} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getExecutedQty(int decimals) {
            return roundValue(executedQty, decimals);
        }

        /**
         * Method to get {@link #orderId} instance <br>
         * Any params required
         *
         * @return {@link #orderId} instance as double
         **/
        public long getOrderId() {
            return orderId;
        }

        /**
         * Method to get {@link #price} instance <br>
         * Any params required
         *
         * @return {@link #price} instance as double
         **/
        public double getPrice() {
            return price;
        }

        /**
         * Method to set {@link #price}
         *
         * @param price: price of order
         * @throws IllegalArgumentException when price of order value is less than 0
         **/
        public void setPrice(double price) {
            if (price < 0)
                throw new IllegalArgumentException("Price value cannot be less than 0");
            this.price = price;
        }

        /**
         * Method to get {@link #price} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #price} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPrice(int decimals) {
            return roundValue(price, decimals);
        }

        /**
         * Method to get {@link #qty} instance <br>
         * Any params required
         *
         * @return {@link #qty} instance as double
         **/
        public double getQty() {
            return qty;
        }

        /**
         * Method to set {@link #qty}
         *
         * @param qty: quantity of order
         * @throws IllegalArgumentException when quantity of order value is less than 0
         **/
        public void setQty(double qty) {
            if (qty < 0)
                throw new IllegalArgumentException("Quantity value cannot be less than 0");
            this.qty = qty;
        }

        /**
         * Method to get {@link #qty} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #qty} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getQty(int decimals) {
            return roundValue(qty, decimals);
        }

        /**
         * Method to get {@link #side} instance <br>
         * Any params required
         *
         * @return {@link #side} instance as {@link Side}
         **/
        public Side getSide() {
            return side;
        }

        /**
         * Method to set {@link #side}
         *
         * @param side: side of order
         * @throws IllegalArgumentException when side is not valid
         **/
        public void setSide(Side side) {
            this.side = side;
        }

        /**
         * Method to get {@link #symbol} instance <br>
         * Any params required
         *
         * @return {@link #symbol} instance as {@link String}
         **/
        public String getSymbol() {
            return symbol;
        }

        /**
         * Method to get {@link #timeInForce} instance <br>
         * Any params required
         *
         * @return {@link #timeInForce} instance as {@link TimeInForce}
         **/
        public TimeInForce getTimeInForce() {
            return timeInForce;
        }

        /**
         * Method to get {@link #isIsolated} instance <br>
         * Any params required
         *
         * @return {@link #isIsolated} instance as boolean
         **/
        public boolean isIsolated() {
            return isIsolated;
        }

        /**
         * Method to set {@link #isIsolated}
         *
         * @param isolated: if order is isolated
         **/
        public void setIsolated(boolean isolated) {
            isIsolated = isolated;
        }

        /**
         * Method to get {@link #updatedTime} instance <br>
         * Any params required
         *
         * @return {@link #updatedTime} instance as long
         **/
        public long getUpdatedTime() {
            return updatedTime;
        }

        /**
         * Method to set {@link #updatedTime}
         *
         * @param updatedTime: time of order
         * @throws IllegalArgumentException when time of order value is less than 0
         **/
        public void setUpdatedTime(long updatedTime) {
            if (updatedTime < 0)
                throw new IllegalArgumentException("Updated time value cannot be less than 0");
            this.updatedTime = updatedTime;
        }

        /**
         * Method to get {@link #updatedTime} instance <br>
         * Any params required
         *
         * @return {@link #updatedTime} instance as {@link Date}
         **/
        public Date getUpdatedDate() {
            return TimeFormatter.getDate(updatedTime);
        }

        /**
         * Returns a string representation of the object <br>
         * Any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
