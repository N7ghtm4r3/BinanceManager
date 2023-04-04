package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVT} class is useful to format a BLVT
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#subscribe-blvt-user_data">
 * Subscribe BLVT (USER_DATA)</a>
 * @see BinanceItem
 * @see BLVTStructure
 **/
public class BLVT extends BLVTStructure {

    /**
     * {@code status} of the BLVT
     **/
    private final BLVTStatus status;

    /**
     * {@code cost} of the BLVT
     **/
    private final double cost;

    /**
     * Constructor to init {@link BLVT} object
     *
     * @param id:        id of the BLVT
     * @param tokenName: token name of the BLVT
     * @param amount:    amount of the BLVT
     * @param timestamp: timestamp of the BLVT
     * @param status:    status of the BLVT
     * @param cost:      cost of the BLVT
     **/
    public BLVT(long id, String tokenName, double amount, long timestamp, BLVTStatus status, double cost) {
        super(id, tokenName, amount, timestamp);
        this.status = status;
        this.cost = cost;
    }

    /**
     * Constructor to init {@link BLVT} object
     *
     * @param jBLVT: BLVT details as {@link JSONObject}
     **/
    public BLVT(JSONObject jBLVT) {
        super(jBLVT);
        status = BLVTStatus.valueOf(hItem.getString("status"));
        cost = hItem.getDouble("cost", 0);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link BLVTStatus}
     **/
    public BLVTStatus getStatus() {
        return status;
    }

    /**
     * Method to get {@link #cost} instance <br>
     * No-any params required
     *
     * @return {@link #cost} instance as double
     **/
    public double getCost() {
        return cost;
    }

    /**
     * Method to get {@link #cost} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #cost} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getCost(int decimals) {
        return roundValue(cost, decimals);
    }

}
