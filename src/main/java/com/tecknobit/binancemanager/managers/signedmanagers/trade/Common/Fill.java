package com.tecknobit.binancemanager.managers.signedmanagers.trade.common;

import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.BinanceMarginManager;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.BinanceSpotManager;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Fill} class is useful to obtain and format Fill object
 *
 * @implNote used by {@link BinanceSpotManager} and {@link BinanceMarginManager}
 **/

public class Fill {

    /**
     * {@code price} is instance that memorizes price of a fill
     * **/
    protected double price;

    /**
     * {@code qty} is instance that memorizes quantity of a fill
     * **/
    protected double qty;

    /**
     * {@code commission} is instance that memorizes commission of a fill
     * **/
    protected double commission;

    /**
     * {@code commissionAsset} is instance that memorizes commission asset of a fill
     * **/
    protected String commissionAsset;

    /** Constructor to init {@link Fill} object
     * @param price: price of a fill
     * @param qty: quantity of a fill
     * @param commission: commission of a fill
     * @param commissionAsset: commission asset of a fill
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public Fill(double price, double qty, double commission, String commissionAsset) {
        if(price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        else
            this.price = price;
        if (qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        else
            this.qty = qty;
        if (commission < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        else
            this.commission = commission;
        this.commissionAsset = commissionAsset;
    }

    /**
     * Constructor to init {@link Fill} object
     *
     * @param fill: fill details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public Fill(JSONObject fill) {
        price = fill.getDouble("price");
        if (price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        qty = fill.getDouble("qty");
        if (qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        commission = fill.getDouble("commission");
        if (commission < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        commissionAsset = fill.getString("commissionAsset");
    }

    public double getPrice() {
        return price;
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
     * Method to set {@link #price}
     *
     * @param price: price of a fill
     * @throws IllegalArgumentException when price value is less than 0
     **/
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        this.price = price;
    }

    public double getQty() {
        return qty;
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
     * Method to set {@link #qty}
     *
     * @param qty: price of a fill
     * @throws IllegalArgumentException when quantity value is less than 0
     **/
    public void setQty(double qty) {
        if (qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        this.qty = qty;
    }

    public double getCommission() {
        return commission;
    }

    /**
     * Method to get {@link #commission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #commission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCommission(int decimals) {
        return roundValue(commission, decimals);
    }

    /**
     * Method to set {@link #commission}
     *
     * @param commission: price of a fill
     * @throws IllegalArgumentException when commission value is less than 0
     **/
    public void setCommission(double commission) {
        if (commission < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        this.commission = commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

    @Override
    public String toString() {
        return "Fill{" +
                "price=" + price +
                ", qty=" + qty +
                ", commission=" + commission +
                ", commissionAsset='" + commissionAsset + '\'' +
                '}';
    }

}
