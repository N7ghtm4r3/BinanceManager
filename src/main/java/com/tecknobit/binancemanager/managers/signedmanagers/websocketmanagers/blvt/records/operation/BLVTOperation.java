package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.operation;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records.BLVTStructure;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code BLVTOperation} class is useful to format a BLVT structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-subscription-record-user_data">
 *             Query Subscription Record (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-redemption-record-user_data">
 *             Query Redemption Record (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BLVTStructure
 **/
public abstract class BLVTOperation extends BLVTStructure {

    /**
     * {@code nav} of the BLVT operation
     **/
    protected final double nav;

    /**
     * {@code fee} of the BLVT operation
     **/
    protected final double fee;

    /**
     * Constructor to init {@link BLVTOperation} object
     *
     * @param id:        id of the BLVT operation
     * @param tokenName: token name of the BLVT operation
     * @param amount:    amount of the BLVT operation
     * @param timestamp: timestamp of the BLVT operation
     * @param nav:       nav of the BLVT operation
     * @param fee:       fee of the BLVT operation
     **/
    public BLVTOperation(long id, String tokenName, double amount, long timestamp, double nav, double fee) {
        super(id, tokenName, amount, timestamp);
        this.nav = nav;
        this.fee = fee;
    }

    /**
     * Constructor to init {@link BLVTOperation} object
     *
     * @param jBLVTOperation: BLVT operation details as {@link JSONObject}
     **/
    public BLVTOperation(JSONObject jBLVTOperation) {
        super(jBLVTOperation);
        nav = hItem.getDouble("nav", 0);
        fee = hItem.getDouble("fee", 0);
    }

    /**
     * Method to get {@link #nav} instance <br>
     * No-any params required
     *
     * @return {@link #nav} instance as double
     **/
    public double getNav() {
        return nav;
    }

    /**
     * Method to get {@link #nav} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #nav} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getNav(int decimals) {
        return roundValue(nav, decimals);
    }

    /**
     * Method to get {@link #fee} instance <br>
     * No-any params required
     *
     * @return {@link #fee} instance as double
     **/
    public double getFee() {
        return fee;
    }

    /**
     * Method to get {@link #fee} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #fee} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFee(int decimals) {
        return roundValue(fee, decimals);
    }

}
