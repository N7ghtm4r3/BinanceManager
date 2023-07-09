package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records.plans.lists.IndexPlansList.IndexPlan;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code PlansListStructure} class is useful to format a {@code Binance}'s plans list structure
 *
 * @param <T> item for the list
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-list-of-plans-user_data">
 * Get list of plans (USER_DATA)</a>
 * @see BinanceItem
 */
public class PlansListStructure<T extends IndexPlan> extends BinanceItem {

    /**
     * {@code planValueInUSD} the value of the plan in USD
     */
    protected final double planValueInUSD;

    /**
     * {@code planValueInBTC} the value of the plan in BTC
     */
    protected final double planValueInBTC;

    /**
     * {@code plans} list of the plans
     */
    protected final ArrayList<T> plans;

    /**
     * {@code jPlans} list of the plans
     */
    protected final ArrayList<JSONObject> jPlans;

    /**
     * Constructor to init a {@link PlansListStructure} object
     *
     * @param planValueInUSD: the value of the plan in USD
     * @param planValueInBTC: the value of the plan in BTC
     * @param plans:          list of the plans
     */
    public PlansListStructure(double planValueInUSD, double planValueInBTC, ArrayList<T> plans) {
        super(null);
        this.planValueInUSD = planValueInUSD;
        this.planValueInBTC = planValueInBTC;
        this.plans = plans;
        jPlans = null;
    }

    /**
     * Constructor to init a {@link PlansListStructure} object
     *
     * @param jPlansListStructure: plans list structure details as {@link JSONObject}
     */
    public PlansListStructure(JSONObject jPlansListStructure) {
        super(jPlansListStructure);
        planValueInUSD = hItem.getDouble("planValueInUSD", 0);
        planValueInBTC = hItem.getDouble("planValueInBTC", 0);
        plans = new ArrayList<>();
        jPlans = hItem.fetchList("plans", new ArrayList<>());
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
     * Method to get {@link #planValueInBTC} instance <br>
     * No-any params required
     *
     * @return {@link #planValueInBTC} instance as double
     */
    public double getPlanValueInBTC() {
        return planValueInBTC;
    }

    /**
     * Method to get {@link #planValueInBTC} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #planValueInBTC} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getPlanValueInBTC(int decimals) {
        return roundValue(planValueInBTC, decimals);
    }

    /**
     * Method to get {@link #plans} instance <br>
     * No-any params required
     *
     * @return {@link #plans} instance as {@link ArrayList} of {@link T}
     */
    public ArrayList<T> getPlans() {
        return plans;
    }

}
