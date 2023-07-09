package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code HoldingsPlanDetails} class is useful to format a {@code Binance}'s holdings plan details
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-holding-details-of-the-plan-user_data">
 * Query holding details of the plan (USER_DATA)</a>
 * @see BinanceItem
 * @see Plan
 * @see PlanStructure
 */
public class HoldingsPlanDetails extends PlanStructure {

    /**
     * {@code planValueInUSD} market value of the plan
     */
    private final double planValueInUSD;

    /**
     * {@code pnlInUSD} PNL of the plan in USD
     */
    private final double pnlInUSD;

    /**
     * {@code roi} ROI of the plan
     */
    private final double roi;

    /**
     * {@code details} of the plan
     */
    private final ArrayList<PlanDetails> details;

    /**
     * Constructor to init a {@link HoldingsPlanDetails} object
     *
     * @param planId:                 for success creation, planId is associated. PlanId remains constant when plan is being updated
     * @param nextExecutionDateTime:  next execution date time
     * @param status:                 status of the plan
     * @param planType:               this is the plan type
     * @param editAllowed:            whether this plan is allowed to be modified
     * @param flexibleAllowedToUse:   whether this plan is flexible allowed to be used
     * @param creationDateTime:       date time that this plan is created
     * @param firstExecutionDateTime: first subscription date time
     * @param lastUpdateDateTime:     last update date time
     * @param targetAsset:            target asset of the plan created
     * @param sourceAsset:            source asset of the plan created
     * @param totalInvestedInUSD:     total source asset invested in equivalent of USD
     * @param planValueInUSD:         market value of the plan
     * @param pnlInUSD:               PNL of the plan in USD
     * @param roi:                    ROI of the plan
     * @param details:                details of the plan
     */
    public HoldingsPlanDetails(long planId, long nextExecutionDateTime, PlanStatus status, PlanType planType,
                               boolean editAllowed, boolean flexibleAllowedToUse, long creationDateTime,
                               long firstExecutionDateTime, long lastUpdateDateTime, String targetAsset, String sourceAsset,
                               double totalInvestedInUSD, double planValueInUSD, double pnlInUSD, double roi,
                               ArrayList<PlanDetails> details) {
        super(planId, nextExecutionDateTime, status, planType, editAllowed, flexibleAllowedToUse, creationDateTime,
                firstExecutionDateTime, lastUpdateDateTime, targetAsset, sourceAsset, totalInvestedInUSD);
        this.planValueInUSD = planValueInUSD;
        this.pnlInUSD = pnlInUSD;
        this.roi = roi;
        this.details = details;
    }

    /**
     * Constructor to init a {@link HoldingsPlanDetails} object
     *
     * @param jHoldingsPlanDetails: holdings plan details as {@link JSONObject}
     */
    public HoldingsPlanDetails(JSONObject jHoldingsPlanDetails) {
        super(jHoldingsPlanDetails);
        planValueInUSD = hItem.getDouble("planValueInUSD", 0);
        pnlInUSD = hItem.getDouble("pnlInUSD", 0);
        roi = hItem.getDouble("roi", 0);
        details = new ArrayList<>();
        ArrayList<JSONObject> jDetails = hItem.fetchList("details", new ArrayList<>());
        for (JSONObject item : jDetails)
            details.add(new PlanDetails(item));
    }

    /**
     * Method to get {@link #planValueInUSD} instance <br>
     * No-any params required
     *
     * @return {@link #planValueInUSD} instance as double
     */
    public double getPlanValueInUSD() {
        return planValueInUSD;
    }

    /**
     * Method to get {@link #planValueInUSD} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #planValueInUSD} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPlanValueInUSD(int decimals) {
        return roundValue(planValueInUSD, decimals);
    }

    /**
     * Method to get {@link #pnlInUSD} instance <br>
     * No-any params required
     *
     * @return {@link #pnlInUSD} instance as double
     */
    public double getPnlInUSD() {
        return pnlInUSD;
    }

    /**
     * Method to get {@link #pnlInUSD} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #pnlInUSD} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPnlInUSD(int decimals) {
        return roundValue(pnlInUSD, decimals);
    }

    /**
     * Method to get {@link #roi} instance <br>
     * No-any params required
     *
     * @return {@link #roi} instance as double
     */
    public double getRoi() {
        return roi;
    }

    /**
     * Method to get {@link #roi} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #roi} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getRoi(int decimals) {
        return roundValue(roi, decimals);
    }

    /**
     * Method to get {@link #details} instance <br>
     * No-any params required
     *
     * @return {@link #details} instance as {@link ArrayList} of {@link PlanDetails}
     */
    public ArrayList<PlanDetails> getDetails() {
        return details;
    }

    /**
     * The {@code PlanDetails} class is useful to format a {@code Binance}'s plan details
     *
     * @author N7ghtm4r3 - Tecknobit
     */
    public static class PlanDetails extends BinanceItem {

        /**
         * {@code AssetStatus} list of available asset statuses
         */
        public enum AssetStatus {

            /**
             * {@code ACTIVE} asset status
             */
            ACTIVE,

            /**
             * {@code INACTIVE} asset status
             */
            INACTIVE

        }

        /**
         * {@code targetAsset} target asset value
         */
        private final String targetAsset;

        /**
         * {@code averagePriceInUSD} average price of the asset in USD
         */
        private final double averagePriceInUSD;

        /**
         * {@code totalInvestedInUSD} total source asset invested for this target asset in equivilent of USD
         */
        private final double totalInvestedInUSD;

        /**
         * {@code purchasedAmount} purchased amount of target asset
         */
        private final double purchasedAmount;

        /**
         * {@code purchasedAmountUnit} purchased amount unit
         */
        private final String purchasedAmountUnit;

        /**
         * {@code pnlInUSD} PNL denominated in USD
         */
        private final double pnlInUSD;

        /**
         * {@code roi} ROI calculated in decimal
         */
        private final double roi;

        /**
         * {@code percentage} asset allocation in the plan. If it's single plan, then it's 100
         */
        private final double percentage;

        /**
         * {@code assetStatus} whether this asset is still being subscribed in this plan
         */
        private final AssetStatus assetStatus;

        /**
         * {@code availableAmount} available amount value
         *
         * @apiNote only for {@link PlanType#INDEX}
         */
        private final double availableAmount;

        /**
         * {@code availableAmountUnit} available amount unit
         *
         * @apiNote only for {@link PlanType#INDEX}
         */
        private final String availableAmountUnit;

        /**
         * {@code redeemedAmount} redeemed amount
         *
         * @apiNote only for {@link PlanType#INDEX}
         */
        private final double redeemedAmount;

        /**
         * {@code redeemedAmountUnit} redeemed amount unit
         *
         * @apiNote only for {@link PlanType#INDEX}
         */
        private final String redeemedAmountUnit;

        /**
         * {@code assetValueInUSD} asset value in USD
         *
         * @apiNote only for {@link PlanType#INDEX}
         */
        private final double assetValueInUSD;

        /**
         * Constructor to init a {@link PlanDetails} object
         *
         * @param targetAsset: target asset value
         * @param percentage:  asset allocation in the plan. If it's single plan, then it's 100
         * @apiNote this constructor is useful to create and pass this object as request parameter
         */
        public PlanDetails(String targetAsset, double percentage) {
            this(targetAsset, 0, 0, 0, null, 0, 0, percentage, null, 0, null, 0, null, 0);
        }

        /**
         * Constructor to init a {@link PlanDetails} object
         *
         * @param targetAsset:         target asset value
         * @param averagePriceInUSD:   average price of the asset in USD
         * @param totalInvestedInUSD:  total source asset invested for this target asset in equivalent of USD
         * @param purchasedAmount:     purchased amount of target asset
         * @param purchasedAmountUnit: purchased amount unit
         * @param pnlInUSD:            PNL denominated in USD
         * @param roi:                 ROI calculated in decimal
         * @param percentage:          asset allocation in the plan. If it's single plan, then it's 100
         * @param assetStatus:         whether this asset is still being subscribed in this plan
         * @param availableAmount:     available amount value
         * @param availableAmountUnit: available amount unit
         * @param redeemedAmount:      redeemed amount
         * @param redeemedAmountUnit:  redeemed amount unit
         * @param assetValueInUSD:     asset value in USD
         */
        public PlanDetails(String targetAsset, double averagePriceInUSD, double totalInvestedInUSD, double purchasedAmount,
                           String purchasedAmountUnit, double pnlInUSD, double roi, double percentage,
                           AssetStatus assetStatus, double availableAmount, String availableAmountUnit,
                           double redeemedAmount, String redeemedAmountUnit, double assetValueInUSD) {
            super(null);
            this.targetAsset = targetAsset;
            this.averagePriceInUSD = averagePriceInUSD;
            this.totalInvestedInUSD = totalInvestedInUSD;
            this.purchasedAmount = purchasedAmount;
            this.purchasedAmountUnit = purchasedAmountUnit;
            this.pnlInUSD = pnlInUSD;
            this.roi = roi;
            this.percentage = percentage;
            this.assetStatus = assetStatus;
            this.availableAmount = availableAmount;
            this.availableAmountUnit = availableAmountUnit;
            this.redeemedAmount = redeemedAmount;
            this.redeemedAmountUnit = redeemedAmountUnit;
            this.assetValueInUSD = assetValueInUSD;
        }

        /**
         * Constructor to init a {@link PlanDetails} object
         *
         * @param jPlanDetails: plan details as {@link JSONObject}
         */
        public PlanDetails(JSONObject jPlanDetails) {
            super(jPlanDetails);
            targetAsset = hItem.getString("targetAsset");
            averagePriceInUSD = hItem.getDouble("averagePriceInUSD", 0);
            totalInvestedInUSD = hItem.getDouble("totalInvestedInUSD", 0);
            purchasedAmount = hItem.getDouble("purchasedAmount", 0);
            purchasedAmountUnit = hItem.getString("purchasedAmountUnit");
            pnlInUSD = hItem.getDouble("pnlInUSD", 0);
            roi = hItem.getDouble("roi", 0);
            percentage = hItem.getDouble("percentage", 0);
            assetStatus = AssetStatus.valueOf(hItem.getString("assetStatus"));
            availableAmount = hItem.getDouble("availableAmount", 0);
            availableAmountUnit = hItem.getString("availableAmountUnit");
            redeemedAmount = hItem.getDouble("redeemedAmount", 0);
            redeemedAmountUnit = hItem.getString("redeemedAmountUnit");
            assetValueInUSD = hItem.getDouble("assetValueInUSD", 0);
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
         * Method to get {@link #averagePriceInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #averagePriceInUSD} instance as double
         */
        public double getAveragePriceInUSD() {
            return averagePriceInUSD;
        }

        /**
         * Method to get {@link #averagePriceInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #averagePriceInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAveragePriceInUSD(int decimals) {
            return roundValue(averagePriceInUSD, decimals);
        }

        /**
         * Method to get {@link #totalInvestedInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #totalInvestedInUSD} instance as double
         */
        public double getTotalInvestedInUSD() {
            return totalInvestedInUSD;
        }

        /**
         * Method to get {@link #totalInvestedInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalInvestedInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalInvestedInUSD(int decimals) {
            return roundValue(totalInvestedInUSD, decimals);
        }

        /**
         * Method to get {@link #purchasedAmount} instance <br>
         * No-any params required
         *
         * @return {@link #purchasedAmount} instance as double
         */
        public double getPurchasedAmount() {
            return purchasedAmount;
        }

        /**
         * Method to get {@link #purchasedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #purchasedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPurchasedAmount(int decimals) {
            return roundValue(purchasedAmount, decimals);
        }

        /**
         * Method to get {@link #purchasedAmountUnit} instance <br>
         * No-any params required
         *
         * @return {@link #purchasedAmountUnit} instance as {@link String}
         */
        public String getPurchasedAmountUnit() {
            return purchasedAmountUnit;
        }

        /**
         * Method to get {@link #pnlInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #pnlInUSD} instance as double
         */
        public double getPnlInUSD() {
            return pnlInUSD;
        }

        /**
         * Method to get {@link #pnlInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #pnlInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPnlInUSD(int decimals) {
            return roundValue(pnlInUSD, decimals);
        }

        /**
         * Method to get {@link #roi} instance <br>
         * No-any params required
         *
         * @return {@link #roi} instance as double
         */
        public double getRoi() {
            return roi;
        }

        /**
         * Method to get {@link #roi} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #roi} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRoi(int decimals) {
            return roundValue(roi, decimals);
        }

        /**
         * Method to get {@link #percentage} instance <br>
         * No-any params required
         *
         * @return {@link #percentage} instance as double
         */
        public double getPercentage() {
            return percentage;
        }

        /**
         * Method to get {@link #percentage} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #percentage} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getPercentage(int decimals) {
            return roundValue(percentage, decimals);
        }

        /**
         * Method to get {@link #assetStatus} instance <br>
         * No-any params required
         *
         * @return {@link #assetStatus} instance as {@link AssetStatus}
         */
        public AssetStatus getAssetStatus() {
            return assetStatus;
        }

        /**
         * Method to get {@link #availableAmount} instance <br>
         * No-any params required
         *
         * @return {@link #availableAmount} instance as double
         */
        public double getAvailableAmount() {
            return availableAmount;
        }

        /**
         * Method to get {@link #availableAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #availableAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAvailableAmount(int decimals) {
            return roundValue(availableAmount, decimals);
        }

        /**
         * Method to get {@link #availableAmountUnit} instance <br>
         * No-any params required
         *
         * @return {@link #availableAmountUnit} instance as {@link String}
         */
        public String getAvailableAmountUnit() {
            return availableAmountUnit;
        }

        /**
         * Method to get {@link #redeemedAmount} instance <br>
         * No-any params required
         *
         * @return {@link #redeemedAmount} instance as double
         */
        public double getRedeemedAmount() {
            return redeemedAmount;
        }

        /**
         * Method to get {@link #redeemedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #redeemedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getRedeemedAmount(int decimals) {
            return roundValue(redeemedAmount, decimals);
        }

        /**
         * Method to get {@link #redeemedAmountUnit} instance <br>
         * No-any params required
         *
         * @return {@link #redeemedAmountUnit} instance as {@link String}
         */
        public String getRedeemedAmountUnit() {
            return redeemedAmountUnit;
        }

        /**
         * Method to get {@link #assetValueInUSD} instance <br>
         * No-any params required
         *
         * @return {@link #assetValueInUSD} instance as double
         */
        public double getAssetValueInUSD() {
            return assetValueInUSD;
        }

        /**
         * Method to get {@link #assetValueInUSD} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #assetValueInUSD} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getAssetValueInUSD(int decimals) {
            return roundValue(assetValueInUSD, decimals);
        }

    }

}
