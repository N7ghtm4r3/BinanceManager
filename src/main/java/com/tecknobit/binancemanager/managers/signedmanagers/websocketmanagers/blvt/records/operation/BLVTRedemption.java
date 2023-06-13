package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.BLVTStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTRedemption} class is useful to format a BLVT redemption
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
 * Query Redemption Record (USER_DATA)</a>
 * @see BinanceItem
 * @see BLVTStructure
 * @see BLVTOperation
 */
public class BLVTRedemption extends BLVTOperation {

    /**
     * {@code netProceed} net proceed of the BLVT redemption
     */
    private final double netProceed;

    /**
     * Constructor to init {@link BLVTRedemption} object
     *
     * @param id:         id of the BLVT redemption
     * @param tokenName:  token name of the BLVT redemption
     * @param amount:     amount of the BLVT redemption
     * @param timestamp:  timestamp of the BLVT redemption
     * @param nav:        nav of the BLVT redemption
     * @param fee:        fee of the BLVT redemption
     * @param netProceed: net proceed of the BLVT redemption
     */
    public BLVTRedemption(long id, String tokenName, double amount, long timestamp, double nav, double fee,
                          double netProceed) {
        super(id, tokenName, amount, timestamp, nav, fee);
        this.netProceed = netProceed;
    }

    /**
     * Constructor to init {@link BLVTRedemption} object
     *
     * @param jBLVTRedemption: BLVT redemption details as {@link JSONObject}
     */
    public BLVTRedemption(JSONObject jBLVTRedemption) {
        super(jBLVTRedemption);
        netProceed = hItem.getDouble("netProceed", 0);
    }

    /**
     * Method to get {@link #netProceed} instance <br>
     * No-any params required
     *
     * @return {@link #netProceed} instance as double
     */
    public double getNetProceed() {
        return netProceed;
    }

    /**
     * Method to get {@link #netProceed} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #netProceed} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getNetProceed(int decimals) {
        return roundValue(netProceed, decimals);
    }

}
