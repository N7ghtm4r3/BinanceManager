package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Common;

/**
 *  The {@code Fill} class is useful to obtain and format Fill object
 *  @implNote used by BinanceSpotManager, BinanceMarginManager
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#introduction">
 *      https://binance-docs.github.io/apidocs/spot/en/#introduction</a>
 * **/

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
        if(qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        else
            this.qty = qty;
        if(commission < 0)
            throw new IllegalArgumentException("Commission value cannot be less than 0");
        else
            this.commission = commission;
        this.commissionAsset = commissionAsset;
    }

    public double getPrice() {
        return price;
    }

    /** Method to set {@link #price}
     * @param price: price of a fill
     * @throws IllegalArgumentException when price value is less than 0
     * **/
    public void setPrice(double price) {
        if(price < 0)
            throw new IllegalArgumentException("Price value cannot be less than 0");
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    /** Method to set {@link #qty}
     * @param qty: price of a fill
     * @throws IllegalArgumentException when quantity value is less than 0
     * **/
    public void setQty(double qty) {
        if(qty < 0)
            throw new IllegalArgumentException("Quantity value cannot be less than 0");
        this.qty = qty;
    }

    public double getCommission() {
        return commission;
    }

    /** Method to set {@link #commission}
     * @param commission: price of a fill
     * @throws IllegalArgumentException when commission value is less than 0
     * **/
    public void setCommission(double commission) {
        if(commission < 0)
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
