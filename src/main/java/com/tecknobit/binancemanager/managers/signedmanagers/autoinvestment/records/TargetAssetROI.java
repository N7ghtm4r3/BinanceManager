package com.tecknobit.binancemanager.managers.signedmanagers.autoinvestment.records;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code TargetAssetROI} class is useful to format a {@code Binance}'s target asset ROI
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="">
 * </a>
 * @see BinanceItem
 */
public class TargetAssetROI extends BinanceItem {

    /**
     * {@code ROIType} list of available ROI types
     */
    public enum ROIType {

        /**
         * {@code FIVE_YEAR} ROI type
         */
        FIVE_YEAR,

        /**
         * {@code THREE_YEAR} ROI type
         */
        THREE_YEAR,

        /**
         * {@code ONE_YEAR} ROI type
         */
        ONE_YEAR,

        /**
         * {@code SIX_MONTH} ROI type
         */
        SIX_MONTH,

        /**
         * {@code THREE_MONTH} ROI type
         */
        THREE_MONTH,

        /**
         * {@code SEVEN_DAY} ROI type
         */
        SEVEN_DAY

    }

    /**
     * {@code date} date of ROI
     */
    private final long date;

    /**
     * {@code simulateRoi} the ROI simulated value
     */
    private final double simulateRoi;

    /**
     * Constructor to init a {@link TargetAssetROI} object
     *
     * @param date:        date of ROI
     * @param simulateRoi: the ROI simulated value
     */
    public TargetAssetROI(long date, double simulateRoi) {
        super(null);
        this.date = date;
        this.simulateRoi = simulateRoi;
    }

    /**
     * Constructor to init a {@link TargetAssetROI} object
     *
     * @param jTargetAssetROI: target asset ROI details as {@link JSONObject}
     */
    public TargetAssetROI(JSONObject jTargetAssetROI) {
        super(jTargetAssetROI);
        date = hItem.getLong("date");
        simulateRoi = hItem.getDouble("simulateRoi");
    }

    /**
     * Method to get {@link #date} instance <br>
     * No-any params required
     *
     * @return {@link #date} instance as long
     */
    public long getDateTime() {
        return date;
    }

    /**
     * Method to get {@link #date} instance <br>
     * No-any params required
     *
     * @return {@link #date} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(date);
    }

    /**
     * Method to get {@link #simulateRoi} instance <br>
     * No-any params required
     *
     * @return {@link #simulateRoi} instance as double
     */
    public double getSimulateRoi() {
        return simulateRoi;
    }

    /**
     * Method to get {@link #simulateRoi} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #simulateRoi} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getSimulateRoi(int decimals) {
        return roundValue(simulateRoi, decimals);
    }

}
