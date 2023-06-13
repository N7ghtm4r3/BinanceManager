package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.BLVTStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTSubscription} class is useful to format a BLVT subscription
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
 * Query Subscription Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BLVTStructure
 * @see BLVTOperation
 */
public class BLVTSubscription extends BLVTOperation {

    /**
     * {@code totalCharge} total charge of the BLVT subscription
     */
    private final double totalCharge;

    /**
     * Constructor to init {@link BLVTSubscription} object
     *
     * @param id:          id of the BLVT redemption
     * @param tokenName:   token name of the BLVT redemption
     * @param amount:      amount of the BLVT redemption
     * @param timestamp:   timestamp of the BLVT redemption
     * @param nav:         nav of the BLVT redemption
     * @param fee:         fee of the BLVT redemption
     * @param totalCharge: total charge of the BLVT subscription
     */
    public BLVTSubscription(long id, String tokenName, double amount, long timestamp, double nav, double fee,
                            double totalCharge) {
        super(id, tokenName, amount, timestamp, nav, fee);
        this.totalCharge = totalCharge;
    }

    /**
     * Constructor to init {@link BLVTSubscription} object
     *
     * @param jBLVTSubscription: BLVT subscription details as {@link JSONObject}
     */
    public BLVTSubscription(JSONObject jBLVTSubscription) {
        super(jBLVTSubscription);
        totalCharge = hItem.getDouble("totalCharge", 0);
    }

    /**
     * Method to get {@link #totalCharge} instance <br>
     * No-any params required
     *
     * @return {@link #totalCharge} instance as double
     */
    public double getTotalCharge() {
        return totalCharge;
    }

    /**
     * Method to get {@link #totalCharge} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #totalCharge} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTotalCharge(int decimals) {
        return roundValue(totalCharge, decimals);
    }

}
