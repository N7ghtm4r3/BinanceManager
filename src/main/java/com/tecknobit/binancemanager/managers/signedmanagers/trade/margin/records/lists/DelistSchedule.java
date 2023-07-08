package com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.lists;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The {@code DelistSchedule} class is useful to format a {@code Binance}'s delist schedule
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-tokens-or-symbols-delist-schedule-for-cross-margin-and-isolated-margin-market_data">
 * Get tokens or symbols delist schedule for cross margin and isolated margin (MARKET_DATA)</a>
 * @see BinanceItem
 */
public class DelistSchedule extends BinanceItem {

    /**
     * {@code delistTime} time of the scheduled delist
     */
    private final long delistTime;

    /**
     * {@code crossMarginAssets} cross margin assets of the scheduled delist
     */
    private final ArrayList<String> crossMarginAssets;

    /**
     * {@code isolatedMarginSymbols} isolated margin symbols of the scheduled delist
     */
    private final ArrayList<String> isolatedMarginSymbols;

    /**
     * Constructor to init a {@link DelistSchedule} object
     *
     * @param delistTime:            time of the scheduled delist
     * @param crossMarginAssets:     cross margin assets of the scheduled delist
     * @param isolatedMarginSymbols: isolated margin symbols of the scheduled delist
     */
    public DelistSchedule(long delistTime, ArrayList<String> crossMarginAssets, ArrayList<String> isolatedMarginSymbols) {
        super(null);
        this.delistTime = delistTime;
        this.crossMarginAssets = crossMarginAssets;
        this.isolatedMarginSymbols = isolatedMarginSymbols;
    }

    /**
     * Constructor to init a {@link DelistSchedule} object
     *
     * @param jDelistSchedule: delist schedule details as {@link JSONObject}
     */
    public DelistSchedule(JSONObject jDelistSchedule) {
        super(jDelistSchedule);
        delistTime = hItem.getLong("delistTime");
        crossMarginAssets = returnStringsList(hItem.getJSONArray("crossMarginAssets"));
        isolatedMarginSymbols = returnStringsList(hItem.getJSONArray("isolatedMarginSymbols"));
    }

    /**
     * Method to get {@link #delistTime} instance <br>
     * No-any params required
     *
     * @return {@link #delistTime} instance as long
     */
    public long getDelistTime() {
        return delistTime;
    }

    /**
     * Method to get {@link #delistTime} instance <br>
     * No-any params required
     *
     * @return {@link #delistTime} instance as {@link Date}
     */
    public Date getDelistDate() {
        return new Date(delistTime);
    }

    /**
     * Method to get {@link #crossMarginAssets} instance <br>
     * No-any params required
     *
     * @return {@link #crossMarginAssets} instance as {@link ArrayList} of {@link String}
     */
    public ArrayList<String> getCrossMarginAssets() {
        return crossMarginAssets;
    }

    /**
     * Method to get {@link #isolatedMarginSymbols} instance <br>
     * No-any params required
     *
     * @return {@link #isolatedMarginSymbols} instance as {@link ArrayList} of {@link String}
     */
    public ArrayList<String> getIsolatedMarginSymbols() {
        return isolatedMarginSymbols;
    }

}
