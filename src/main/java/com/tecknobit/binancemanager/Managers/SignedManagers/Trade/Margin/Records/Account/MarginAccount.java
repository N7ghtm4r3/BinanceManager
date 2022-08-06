package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Margin.Records.Account;

/**
 *  The {@code MarginAccount} class is useful to format Binance Margin Account
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#query-cross-margin-account-details-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class MarginAccount {

    /**
     * {@code totalAssetOfBtc} is instance that memorizes total asset of Bitcoin
     * **/
    protected double totalAssetOfBtc;

    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total liability of Bitcoin
     * **/
    protected double totalLiabilityOfBtc;

    /**
     * {@code totalLiabilityOfBtc} is instance that memorizes total net asset of Bitcoin
     * **/
    protected double totalNetAssetOfBtc;

    /** Constructor to init {@link MarginAccount} object
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public MarginAccount(double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc) {
        if(totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        else
            this.totalAssetOfBtc = totalAssetOfBtc;
        if(totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        else
            this.totalLiabilityOfBtc = totalLiabilityOfBtc;
        if(totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
        else
            this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    }

    /** Method to set {@link #totalAssetOfBtc}
     * @param totalAssetOfBtc: total asset of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     * **/
    public void setTotalAssetOfBtc(double totalAssetOfBtc) {
        if(totalAssetOfBtc < 0)
            throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
        this.totalAssetOfBtc = totalAssetOfBtc;
    }

    public double getTotalLiabilityOfBtc() {
        return totalLiabilityOfBtc;
    }

    /** Method to set {@link #totalLiabilityOfBtc}
     * @param totalLiabilityOfBtc: total liability of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     * **/
    public void setTotalLiabilityOfBtc(double totalLiabilityOfBtc) {
        if(totalLiabilityOfBtc < 0)
            throw new IllegalArgumentException("Total liability asset of BTC value cannot be less than 0");
        this.totalLiabilityOfBtc = totalLiabilityOfBtc;
    }

    public double getTotalNetAssetOfBtc() {
        return totalNetAssetOfBtc;
    }

    /** Method to set {@link #totalNetAssetOfBtc}
     * @param totalNetAssetOfBtc: total net asset of Bitcoin
     * @throws IllegalArgumentException when number value is less than 0
     * **/
    public void setTotalNetAssetOfBtc(double totalNetAssetOfBtc) {
        if(totalNetAssetOfBtc < 0)
            throw new IllegalArgumentException("Total net asset of BTC value cannot be less than 0");
        this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    }

    @Override
    public String toString() {
        return "MarginAccount{" +
                "totalAssetOfBtc=" + totalAssetOfBtc +
                ", totalLiabilityOfBtc=" + totalLiabilityOfBtc +
                ", totalNetAssetOfBtc=" + totalNetAssetOfBtc +
                '}';
    }

}
