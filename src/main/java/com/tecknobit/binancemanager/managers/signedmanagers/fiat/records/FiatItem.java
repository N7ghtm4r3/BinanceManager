package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code FiatItem} class is useful to format a fiat item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data">
 *            Get Fiat Deposit/Withdraw History (USER_DATA)</a>
 *     </li>
 *      <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data">
 *           Get Fiat Payments History (USER_DATA)</a>
 *     </li>
 * </ul>
 **/
@Structure
public abstract class FiatItem extends BinanceItem {

    /**
     * {@code FiatStatus} list of available fiat statuses
     **/
    public enum FiatStatus {

        /**
         * {@code Expired} fiat status
         **/
        Expired("Expired"),

        /**
         * {@code Processing} fiat status
         **/
        Processing("Processing"),

        /**
         * {@code Failed} fiat status
         **/
        Failed("Failed"),

        /**
         * {@code Successful} fiat status
         **/
        Successful("Successful"),

        /**
         * {@code Finished} fiat status
         **/
        Finished("Finished"),

        /**
         * {@code Refunding} fiat status
         **/
        Refunding("Refunding"),

        /**
         * {@code Refunded} fiat status
         **/
        Refunded("Refunded"),

        /**
         * {@code Refund Failed} fiat status
         **/
        Refund_Failed("Refund Failed"),

        /**
         * {@code Order Partial credit Stopped} fiat status
         **/
        Order_Partial_Credit_Stopped("Order Partial credit Stopped");

        /**
         * {@code status} of fiat item
         **/
        private final String status;

        /**
         * Constructor to init {@link FiatStatus}
         *
         * @param status: fiat status
         **/
        FiatStatus(String status) {
            this.status = status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         **/
        @Override
        public String toString() {
            return status;
        }

    }

    /**
     * {@code orderNo} order number
     **/
    protected final String orderNo;

    /**
     * {@code fiatCurrency} fiat token
     **/
    protected final String fiatCurrency;

    /**
     * {@code totalFee} trade fee
     **/
    protected final double totalFee;

    /**
     * {@code status} fiat item status
     **/
    protected final FiatStatus status;

    /**
     * {@code createTime} creation time of the fiat item
     **/
    protected final long createTime;

    /**
     * {@code updateTime} update time of the fiat item
     **/
    protected final long updateTime;

    /**
     * Constructor to init {@link FiatItem} object
     *
     * @param orderNo:      order number
     * @param fiatCurrency: fiat token
     * @param totalFee:     trade fee
     * @param status:       fiat item status
     * @param createTime:   creation time of the fiat item
     * @param updateTime:   update time of the fiat item
     **/
    public FiatItem(String orderNo, String fiatCurrency, double totalFee, FiatStatus status, long createTime,
                    long updateTime) {
        super(null);
        this.orderNo = orderNo;
        this.fiatCurrency = fiatCurrency;
        this.totalFee = totalFee;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * Constructor to init {@link FiatItem} object
     *
     * @param jFiatItem: fiat item details as {@link JSONObject}
     **/
    public FiatItem(JSONObject jFiatItem) {
        super(jFiatItem);
        orderNo = hItem.getString("orderNo");
        fiatCurrency = hItem.getString("fiatCurrency");
        totalFee = hItem.getDouble("totalFee", 0);
        status = FiatStatus.valueOf(hItem.getString("status"));
        createTime = hItem.getLong("createTime", 0);
        updateTime = hItem.getLong("updateTime", 0);
    }

    /**
     * Method to get {@link #orderNo} instance <br>
     * No-any params required
     *
     * @return {@link #orderNo} instance as {@link String}
     **/
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Method to get {@link #fiatCurrency} instance <br>
     * No-any params required
     *
     * @return {@link #fiatCurrency} instance as {@link String}
     **/
    public String getFiatCurrency() {
        return fiatCurrency;
    }

    /**
     * Method to get {@link #totalFee} instance <br>
     * No-any params required
     *
     * @return {@link #totalFee} instance as double
     **/
    public double getTotalFee() {
        return totalFee;
    }

    /**
     * Method to get {@link #totalFee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalFee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTotalFee(int decimals) {
        return roundValue(totalFee, decimals);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link FiatStatus}
     **/
    public FiatStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as long
     **/
    public long getCreateTime() {
        return createTime;
    }

    /**
     * Method to get {@link #createTime} instance <br>
     * No-any params required
     *
     * @return {@link #createTime} instance as {@link Date}
     **/
    public Date getCreateDate() {
        return TimeFormatter.getDate(createTime);
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     **/
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     **/
    public Date getUdpateDate() {
        return TimeFormatter.getDate(updateTime);
    }

}
