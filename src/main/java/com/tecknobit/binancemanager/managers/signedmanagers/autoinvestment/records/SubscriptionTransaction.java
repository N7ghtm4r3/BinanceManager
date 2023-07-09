package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.Plan.SourceWallet;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.PlanStructure.PlanType;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SubscriptionTransaction} class is useful to format a {@code Binance}'s subscription transaction
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-transaction-history-user_data">
 * Query subscription transaction history (USER_DATA)</a>
 * @see BinanceItem
 */
public class SubscriptionTransaction extends BinanceItem {

    /**
     * {@code TransactionStatus} list of available transaction statuses
     */
    public enum TransactionStatus {

        /**
         * {@code SUCCESS} transaction status
         */
        SUCCESS,

        /**
         * {@code FAILED} transaction status
         */
        FAILED,

        /**
         * {@code PENDING} transaction status
         */
        PENDING

    }

    /**
     * The {@code FailedType} list of available failed types
     */
    public enum FailedType {

        /**
         * {@code FAILED} failed type
         */
        FAILED("FAILED"),

        /**
         * {@code INSUFFICIENT_BALANCE} failed type
         */
        INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE"),

        /**
         * {@code REJECT} failed type
         */
        REJECT("TRANSACTION_REJECT/GCC_RJECT");

        /**
         * {@code type} value
         */
        private final String type;


        /**
         * Constructor to init a {@link FailedType} object
         *
         * @param type: type value
         */
        FailedType(String type) {
            this.type = type;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return type;
        }

    }

    /**
     * The {@code SubscriptionExecutionType} list of available subscription execution types
     */
    public enum SubscriptionExecutionType {

        /**
         * {@code ONE_TIME} subscription execution type
         */
        ONE_TIME,

        /**
         * {@code RECURRING} subscription execution type
         */
        RECURRING

    }

    /**
     * {@code id} of the transaction
     */
    private final long id;

    /**
     * {@code targetAsset} name of the asset
     */
    private final String targetAsset;

    /**
     * {@code planType} plan type of which this transaction is from
     */
    private final PlanType planType;

    /**
     * {@code planName} plan name of which this transaction is from
     */
    private final String planName;

    /**
     * {@code planId} plan identifier of which this transaction is from
     */
    private final long planId;

    /**
     * {@code transactionDateTime} transaction timestamp
     */
    private final long transactionDateTime;

    /**
     * {@code transactionStatus} status of the transaction
     */
    private final TransactionStatus transactionStatus;

    /**
     * {@code failedType} only show when transaction failed
     */
    private final FailedType failedType;

    /**
     * {@code sourceAsset} source asset of the transaction
     */
    private final String sourceAsset;

    /**
     * {@code sourceAssetAmount} amount of source asset used
     */
    private final double sourceAssetAmount;

    /**
     * {@code targetAssetAmount} purchased amount of the asset
     */
    private final double targetAssetAmount;

    /**
     * {@code sourceWallet} source wallet
     */
    private final SourceWallet sourceWallet;

    /**
     * {@code flexibleUsed} whether simple earn wallet is used
     */
    private final boolean flexibleUsed;

    /**
     * {@code transactionFee} transaction fee amount
     */
    private final double transactionFee;

    /**
     * {@code transactionFeeUnit} denominated coin of the transaction fee
     */
    private final String transactionFeeUnit;

    /**
     * {@code executionPrice} price of the subscription price. It's amount of source asset equivalent of 1 unit of target asset
     */
    private final double executionPrice;

    /**
     * {@code executionType} execution type
     */
    private final SubscriptionExecutionType executionType;

    /**
     * Constructor to init a {@link SubscriptionTransaction} object
     *
     * @param id:                  id of the transaction
     * @param targetAsset:         name of the asset
     * @param planType:            plan type of which this transaction is from
     * @param planName:            plan name of which this transaction is from
     * @param planId:              plan identifier of which this transaction is from
     * @param transactionDateTime: status of the transaction
     * @param transactionStatus:   status of the transaction
     * @param failedType:          only show when transaction failed
     * @param sourceAsset:         source asset of the transaction
     * @param sourceAssetAmount:   amount of source asset used
     * @param targetAssetAmount:   purchased amount of the asset
     * @param sourceWallet:        source wallet
     * @param flexibleUsed:        whether simple earn wallet is used
     * @param transactionFee:      transaction fee amount
     * @param transactionFeeUnit:  denominated coin of the transaction fee
     * @param executionPrice:      price of the subscription price. It's amount of source asset equivalent of 1 unit of target asset
     * @param executionType:       execution type
     */
    public SubscriptionTransaction(long id, String targetAsset, PlanType planType, String planName, long planId,
                                   long transactionDateTime, TransactionStatus transactionStatus, FailedType failedType,
                                   String sourceAsset, double sourceAssetAmount, double targetAssetAmount,
                                   SourceWallet sourceWallet, boolean flexibleUsed, double transactionFee,
                                   String transactionFeeUnit, double executionPrice,
                                   SubscriptionExecutionType executionType) {
        super(null);
        this.id = id;
        this.targetAsset = targetAsset;
        this.planType = planType;
        this.planName = planName;
        this.planId = planId;
        this.transactionDateTime = transactionDateTime;
        this.transactionStatus = transactionStatus;
        this.failedType = failedType;
        this.sourceAsset = sourceAsset;
        this.sourceAssetAmount = sourceAssetAmount;
        this.targetAssetAmount = targetAssetAmount;
        this.sourceWallet = sourceWallet;
        this.flexibleUsed = flexibleUsed;
        this.transactionFee = transactionFee;
        this.transactionFeeUnit = transactionFeeUnit;
        this.executionPrice = executionPrice;
        this.executionType = executionType;
    }

    /**
     * Constructor to init a {@link SubscriptionTransaction} object
     *
     * @param jSubscriptionTransaction: subscription transaction details as {@link JSONObject}
     */
    public SubscriptionTransaction(JSONObject jSubscriptionTransaction) {
        super(jSubscriptionTransaction);
        id = hItem.getLong("id", 0);
        targetAsset = hItem.getString("targetAsset");
        planType = PlanType.valueOf(hItem.getString("planType"));
        planName = hItem.getString("planName");
        planId = hItem.getLong("planId", 0);
        transactionDateTime = hItem.getLong("transactionDateTime", 0);
        transactionStatus = TransactionStatus.valueOf(hItem.getString("transactionStatus"));
        failedType = FailedType.valueOf(hItem.getString("failedType"));
        sourceAsset = hItem.getString("sourceAsset");
        sourceAssetAmount = hItem.getDouble("sourceAssetAmount", 0);
        targetAssetAmount = hItem.getDouble("targetAssetAmount", 0);
        sourceWallet = SourceWallet.valueOf(hItem.getString("sourceWallet"));
        flexibleUsed = hItem.getBoolean("flexibleUsed");
        transactionFee = hItem.getDouble("transactionFee", 0);
        transactionFeeUnit = hItem.getString("transactionFeeUnit");
        executionPrice = hItem.getDouble("executionPrice", 0);
        executionType = SubscriptionExecutionType.valueOf(hItem.getString("executionType"));
    }

    /**
     * Method to get {@link #id} instance <br>
     * No-any params required
     *
     * @return {@link #id} instance as long
     */
    public long getId() {
        return id;
    }

    /**
     * Method to get {@link #targetAsset} instance <br>
     * No-any params required
     *
     * @return {@link #targetAsset} instance as {@link String}
     */
    public String getTargetAsset() {
        return targetAsset;
    }

    /**
     * Method to get {@link #planType} instance <br>
     * No-any params required
     *
     * @return {@link #planType} instance as {@link PlanType}
     */
    public PlanType getPlanType() {
        return planType;
    }

    /**
     * Method to get {@link #planName} instance <br>
     * No-any params required
     *
     * @return {@link #planName} instance as {@link String}
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Method to get {@link #planId} instance <br>
     * No-any params required
     *
     * @return {@link #planId} instance as long
     */
    public long getPlanId() {
        return planId;
    }

    /**
     * Method to get {@link #transactionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactionDateTime} instance as long
     */
    public long getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * Method to get {@link #transactionDateTime} instance <br>
     * No-any params required
     *
     * @return {@link #transactionDateTime} instance as {@link Date}
     */
    public Date getTransactionDate() {
        return TimeFormatter.getDate(transactionDateTime);
    }

    /**
     * Method to get {@link #transactionStatus} instance <br>
     * No-any params required
     *
     * @return {@link #transactionStatus} instance as {@link TransactionStatus}
     */
    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Method to get {@link #failedType} instance <br>
     * No-any params required
     *
     * @return {@link #failedType} instance as {@link FailedType}
     */
    public FailedType getFailedType() {
        return failedType;
    }

    /**
     * Method to get {@link #sourceAsset} instance <br>
     * No-any params required
     *
     * @return {@link #sourceAsset} instance as {@link String}
     */
    public String getSourceAsset() {
        return sourceAsset;
    }

    /**
     * Method to get {@link #sourceAssetAmount} instance <br>
     * No-any params required
     *
     * @return {@link #sourceAssetAmount} instance as double
     */
    public double getSourceAssetAmount() {
        return sourceAssetAmount;
    }

    /**
     * Method to get {@link #sourceAssetAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #sourceAssetAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getSourceAssetAmount(int decimals) {
        return roundValue(sourceAssetAmount, decimals);
    }

    /**
     * Method to get {@link #targetAssetAmount} instance <br>
     * No-any params required
     *
     * @return {@link #targetAssetAmount} instance as double
     */
    public double getTargetAssetAmount() {
        return targetAssetAmount;
    }

    /**
     * Method to get {@link #targetAssetAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #targetAssetAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTargetAssetAmount(int decimals) {
        return roundValue(targetAssetAmount, decimals);
    }

    /**
     * Method to get {@link #sourceWallet} instance <br>
     * No-any params required
     *
     * @return {@link #sourceWallet} instance as {@link SourceWallet}
     */
    public SourceWallet getSourceWallet() {
        return sourceWallet;
    }

    /**
     * Method to get {@link #flexibleUsed} instance <br>
     * No-any params required
     *
     * @return {@link #flexibleUsed} instance as boolean
     */
    public boolean isFlexibleUsed() {
        return flexibleUsed;
    }

    /**
     * Method to get {@link #transactionFee} instance <br>
     * No-any params required
     *
     * @return {@link #transactionFee} instance as double
     */
    public double getTransactionFee() {
        return transactionFee;
    }

    /**
     * Method to get {@link #transactionFee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #transactionFee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTransactionFee(int decimals) {
        return roundValue(transactionFee, decimals);
    }

    /**
     * Method to get {@link #transactionFeeUnit} instance <br>
     * No-any params required
     *
     * @return {@link #transactionFeeUnit} instance as {@link String}
     */
    public String getTransactionFeeUnit() {
        return transactionFeeUnit;
    }

    /**
     * Method to get {@link #executionPrice} instance <br>
     * No-any params required
     *
     * @return {@link #executionPrice} instance as double
     */
    public double getExecutionPrice() {
        return executionPrice;
    }

    /**
     * Method to get {@link #executionPrice} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #executionPrice} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getExecutionPrice(int decimals) {
        return roundValue(executionPrice, decimals);
    }

    /**
     * Method to get {@link #executionType} instance <br>
     * No-any params required
     *
     * @return {@link #executionType} instance as {@link SubscriptionExecutionType}
     */
    public SubscriptionExecutionType getExecutionType() {
        return executionType;
    }

}

