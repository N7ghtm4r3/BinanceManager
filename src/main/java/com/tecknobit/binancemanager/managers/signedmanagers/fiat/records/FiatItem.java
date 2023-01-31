package com.tecknobit.binancemanager.managers.signedmanagers.fiat.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class FiatItem extends BinanceItem {

    public enum FiatStatus {

        Expired("Expired"),
        Processing("Processing"),
        Failed("Failed"),
        Successful("Successful"),
        Finished("Finished"),
        Refunding("Refunding"),
        Refunded("Refunded"),
        Refund_Failed("Refund Failed"),
        Order_Partial_Credit_Stopped("Order Partial credit Stopped");

        private final String status;

        FiatStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }

    }

    protected final String orderNo;
    protected final String fiatCurrency;
    protected final double totalFee;
    protected final FiatStatus status;
    protected final long createTime;
    protected final long updateTime;

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

    public FiatItem(JSONObject jFiatItem) {
        super(jFiatItem);
        orderNo = hItem.getString("orderNo");
        fiatCurrency = hItem.getString("fiatCurrency");
        totalFee = hItem.getDouble("totalFee", 0);
        status = FiatStatus.valueOf(hItem.getString("status"));
        createTime = hItem.getLong("createTime", 0);
        updateTime = hItem.getLong("updateTime", 0);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getFiatCurrency() {
        return fiatCurrency;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public FiatStatus getStatus() {
        return status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

}
