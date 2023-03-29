package com.tecknobit.binancemanager.managers.signedmanagers.staking.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.staking.records.StakingProduct.StakingDetail;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code StakingProductPosition} class is useful to format a staking product position
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-staking-product-position-user_data">
 * Get Staking Product Position(USER_DATA)</a>
 * @see BinanceItem
 * @see StakingDetail
 **/
public class StakingProductPosition extends StakingDetail {

    /**
     * {@code StakingPositionType} list of available staking position types
     **/
    public enum StakingPositionType {

        /**
         * {@code AUTO} staking position type
         **/
        AUTO,

        /**
         * {@code NORMAL} staking position type
         **/
        NORMAL

    }

    /**
     * {@code positionId} staking position id
     **/
    private final long positionId;

    /**
     * {@code amount} locked Amount
     **/
    private final double amount;

    /**
     * {@code purchaseTime} subscription time
     **/
    private final long purchaseTime;

    /**
     * {@code accrualDays} accrue days
     **/
    private final int accrualDays;

    /**
     * {@code rewardAmt} earned amount
     **/
    private final double rewardAmt;

    /**
     * {@code extraRewardAsset} rewards assets of extra staking type
     **/
    private final String extraRewardAsset;

    /**
     * {@code extraRewardAPY} APY of extra staking type
     **/
    private final double extraRewardAPY;

    /**
     * {@code estExtraRewardAmt} rewards of extra staking type, distribute when order expires
     **/
    private final double estExtraRewardAmt;

    /**
     * {@code nextInterestPay} next estimated interest payment
     **/
    private final double nextInterestPay;

    /**
     * {@code nextInterestPayDate} next interest payment date
     **/
    private final long nextInterestPayDate;

    /**
     * {@code payInterestPeriod} interest cycle
     **/
    private final int payInterestPeriod;

    /**
     * {@code redeemAmountEarly} early redemption amount
     **/
    private final double redeemAmountEarly;

    /**
     * {@code interestEndDate} interest accrual end date
     **/
    private final long interestEndDate;

    /**
     * {@code deliverDate} redemption arrival time
     **/
    private final long deliverDate;

    /**
     * {@code redeemPeriod} redemption interval
     **/
    private final int redeemPeriod;

    /**
     * {@code redeemingAmt} amount under redemption
     **/
    private final double redeemingAmt;

    /**
     * {@code partialAmtDeliverDate} arrival time of partial redemption amount of order
     **/
    private final long partialAmtDeliverDate;

    /**
     * {@code canRedeemEarly} when it is true, early redemption can be operated
     **/
    private final boolean canRedeemEarly;

    /**
     * {@code type} order type is auto-staking or normal
     **/
    private final StakingPositionType type;

    /**
     * {@code status} of the staking position
     **/
    private final String status;

    /**
     * Constructor to init {@link StakingProductPosition} object
     *
     * @param asset:                 lock up asset
     * @param rewardAsset:           earn Asset
     * @param duration:              lock period(days)
     * @param renewable:             project supports renewal
     * @param apy:                   value of the staking product
     * @param positionId:            staking position id
     * @param amount:                locked Amount
     * @param purchaseTime:          subscription time
     * @param accrualDays:           accrue days
     * @param rewardAmt:             earned amount
     * @param extraRewardAsset:      rewards assets of extra staking type
     * @param extraRewardAPY:        APY of extra staking type
     * @param estExtraRewardAmt:     rewards of extra staking type, distribute when order expires
     * @param nextInterestPay:       next estimated interest payment
     * @param nextInterestPayDate:   next interest payment date
     * @param payInterestPeriod:     interest cycle
     * @param redeemAmountEarly:     early redemption amount
     * @param interestEndDate:       interest accrual end date
     * @param deliverDate:           redemption arrival time
     * @param redeemPeriod:          redemption interval
     * @param redeemingAmt:          amount under redemption
     * @param partialAmtDeliverDate: arrival time of partial redemption amount of order
     * @param canRedeemEarly:        when it is true, early redemption can be operated
     * @param type:                  order type is auto-staking or normal
     * @param status:                status of the staking position
     **/
    public StakingProductPosition(String asset, String rewardAsset, int duration, boolean renewable, double apy,
                                  long positionId, double amount, long purchaseTime, int accrualDays, double rewardAmt,
                                  String extraRewardAsset, double extraRewardAPY, double estExtraRewardAmt,
                                  double nextInterestPay, long nextInterestPayDate, int payInterestPeriod,
                                  double redeemAmountEarly, long interestEndDate, long deliverDate, int redeemPeriod,
                                  double redeemingAmt, long partialAmtDeliverDate, boolean canRedeemEarly,
                                  StakingPositionType type, String status) {
        super(asset, rewardAsset, duration, renewable, apy);
        this.positionId = positionId;
        this.amount = amount;
        this.purchaseTime = purchaseTime;
        this.accrualDays = accrualDays;
        this.rewardAmt = rewardAmt;
        this.extraRewardAsset = extraRewardAsset;
        this.extraRewardAPY = extraRewardAPY;
        this.estExtraRewardAmt = estExtraRewardAmt;
        this.nextInterestPay = nextInterestPay;
        this.nextInterestPayDate = nextInterestPayDate;
        this.payInterestPeriod = payInterestPeriod;
        this.redeemAmountEarly = redeemAmountEarly;
        this.interestEndDate = interestEndDate;
        this.deliverDate = deliverDate;
        this.redeemPeriod = redeemPeriod;
        this.redeemingAmt = redeemingAmt;
        this.partialAmtDeliverDate = partialAmtDeliverDate;
        this.canRedeemEarly = canRedeemEarly;
        this.type = type;
        this.status = status;
    }

    /**
     * Constructor to init {@link StakingProductPosition} object
     *
     * @param jStakingProductPosition: staking product position details as {@link JSONObject
     **/
    public StakingProductPosition(JSONObject jStakingProductPosition) {
        super(jStakingProductPosition);
        positionId = hItem.getLong("positionId", 0);
        amount = hItem.getDouble("amount", 0);
        purchaseTime = hItem.getLong("purchaseTime", 0);
        accrualDays = hItem.getInt("accrualDays", 0);
        rewardAmt = hItem.getDouble("rewardAmt", 0);
        extraRewardAsset = hItem.getString("extraRewardAsset");
        extraRewardAPY = hItem.getDouble("extraRewardAPY", 0);
        estExtraRewardAmt = hItem.getDouble("estExtraRewardAmt", 0);
        nextInterestPay = hItem.getDouble("nextInterestPay", 0);
        nextInterestPayDate = hItem.getLong("nextInterestPayDate", 0);
        payInterestPeriod = hItem.getInt("payInterestPeriod", 0);
        redeemAmountEarly = hItem.getDouble("redeemAmountEarly", 0);
        interestEndDate = hItem.getLong("interestEndDate", 0);
        deliverDate = hItem.getLong("deliverDate", 0);
        redeemPeriod = hItem.getInt("redeemPeriod", 0);
        redeemingAmt = hItem.getDouble("redeemingAmt", 0);
        partialAmtDeliverDate = hItem.getLong("partialAmtDeliverDate", 0);
        canRedeemEarly = hItem.getBoolean("canRedeemEarly");
        type = StakingPositionType.valueOf(hItem.getString("type"));
        status = hItem.getString("status");
    }

    /**
     * Method to get {@link #positionId} instance <br>
     * No-any params required
     *
     * @return {@link #positionId} instance as long
     **/
    public long getPositionId() {
        return positionId;
    }

    /**
     * Method to get {@link #amount} instance <br>
     * No-any params required
     *
     * @return {@link #amount} instance as double
     **/
    public double getAmount() {
        return amount;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getAmount(int decimals) {
        return roundValue(amount, decimals);
    }

    /**
     * Method to get {@link #purchaseTime} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseTime} instance as long
     **/
    public long getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * Method to get {@link #purchaseTime} instance <br>
     * No-any params required
     *
     * @return {@link #purchaseTime} instance as {@link Date}
     **/
    public Date getPurchaseDate() {
        return TimeFormatter.getDate(purchaseTime);
    }

    /**
     * Method to get {@link #accrualDays} instance <br>
     * No-any params required
     *
     * @return {@link #accrualDays} instance as int
     **/
    public int getAccrualDays() {
        return accrualDays;
    }

    /**
     * Method to get {@link #rewardAmt} instance <br>
     * No-any params required
     *
     * @return {@link #rewardAmt} instance as double
     **/
    public double getRewardAmt() {
        return rewardAmt;
    }

    /**
     * Method to get {@link #rewardAmt} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #rewardAmt} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRewardAmt(int decimals) {
        return roundValue(rewardAmt, decimals);
    }

    /**
     * Method to get {@link #extraRewardAsset} instance <br>
     * No-any params required
     *
     * @return {@link #extraRewardAsset} instance as {@link String}
     **/
    public String getExtraRewardAsset() {
        return extraRewardAsset;
    }

    /**
     * Method to get {@link #extraRewardAPY} instance <br>
     * No-any params required
     *
     * @return {@link #extraRewardAPY} instance as double
     **/
    public double getExtraRewardAPY() {
        return extraRewardAPY;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getExtraRewardAPY(int decimals) {
        return roundValue(extraRewardAPY, decimals);
    }

    /**
     * Method to get {@link #estExtraRewardAmt} instance <br>
     * No-any params required
     *
     * @return {@link #estExtraRewardAmt} instance as double
     **/
    public double getEstExtraRewardAmt() {
        return estExtraRewardAmt;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getEstExtraRewardAmt(int decimals) {
        return roundValue(estExtraRewardAmt, decimals);
    }

    /**
     * Method to get {@link #nextInterestPay} instance <br>
     * No-any params required
     *
     * @return {@link #nextInterestPay} instance as double
     **/
    public double getNextInterestPay() {
        return nextInterestPay;
    }

    /**
     * Method to get {@link #nextInterestPay} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #nextInterestPay} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getNextInterestPay(int decimals) {
        return roundValue(nextInterestPay, decimals);
    }

    /**
     * Method to get {@link #nextInterestPayDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextInterestPayDate} instance as long
     **/
    public long getNextInterestPayDate() {
        return nextInterestPayDate;
    }

    /**
     * Method to get {@link #nextInterestPayDate} instance <br>
     * No-any params required
     *
     * @return {@link #nextInterestPayDate} instance as {@link Date}
     **/
    public Date getDNextInterestPayDate() {
        return TimeFormatter.getDate(nextInterestPayDate);
    }

    /**
     * Method to get {@link #payInterestPeriod} instance <br>
     * No-any params required
     *
     * @return {@link #payInterestPeriod} instance as int
     **/
    public int getPayInterestPeriod() {
        return payInterestPeriod;
    }

    /**
     * Method to get {@link #redeemAmountEarly} instance <br>
     * No-any params required
     *
     * @return {@link #redeemAmountEarly} instance as double
     **/
    public double getRedeemAmountEarly() {
        return redeemAmountEarly;
    }

    /**
     * Method to get {@link #amount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #amount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRedeemAmountEarly(int decimals) {
        return roundValue(redeemAmountEarly, decimals);
    }

    /**
     * Method to get {@link #interestEndDate} instance <br>
     * No-any params required
     *
     * @return {@link #interestEndDate} instance as long
     **/
    public long getInterestEndDate() {
        return interestEndDate;
    }

    /**
     * Method to get {@link #interestEndDate} instance <br>
     * No-any params required
     *
     * @return {@link #interestEndDate} instance as {@link Date}
     **/
    public Date getInterestEnd() {
        return TimeFormatter.getDate(interestEndDate);
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as long
     **/
    public long getDeliverDate() {
        return deliverDate;
    }

    /**
     * Method to get {@link #deliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #deliverDate} instance as {@link Date}
     **/
    public Date getDeliver() {
        return TimeFormatter.getDate(deliverDate);
    }

    /**
     * Method to get {@link #redeemPeriod} instance <br>
     * No-any params required
     *
     * @return {@link #redeemPeriod} instance as int
     **/
    public int getRedeemPeriod() {
        return redeemPeriod;
    }

    /**
     * Method to get {@link #redeemingAmt} instance <br>
     * No-any params required
     *
     * @return {@link #redeemingAmt} instance as double
     **/
    public double getRedeemingAmt() {
        return redeemingAmt;
    }

    /**
     * Method to get {@link #redeemingAmt} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #redeemingAmt} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRedeemingAmt(int decimals) {
        return roundValue(redeemingAmt, decimals);
    }

    /**
     * Method to get {@link #partialAmtDeliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #partialAmtDeliverDate} instance as long
     **/
    public long getPartialAmtDeliverDate() {
        return partialAmtDeliverDate;
    }

    /**
     * Method to get {@link #partialAmtDeliverDate} instance <br>
     * No-any params required
     *
     * @return {@link #partialAmtDeliverDate} instance as {@link Date}
     **/
    public Date getDPartialAmtDeliverDate() {
        return TimeFormatter.getDate(partialAmtDeliverDate);
    }

    /**
     * Method to get {@link #canRedeemEarly} instance <br>
     * No-any params required
     *
     * @return {@link #canRedeemEarly} instance as boolean
     **/
    public boolean canRedeemEarly() {
        return canRedeemEarly;
    }

    /**
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link StakingPositionType}
     **/
    public StakingPositionType getType() {
        return type;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link String}
     **/
    public String getStatus() {
        return status;
    }

}
