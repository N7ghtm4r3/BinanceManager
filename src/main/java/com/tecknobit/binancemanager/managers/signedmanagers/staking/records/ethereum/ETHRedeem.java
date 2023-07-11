package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code ETHRedeem} class is useful to format a {@code Binance}'s ETH redeem
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-eth-trade">
 * Redeem ETH (TRADE)</a>
 * @see BinanceItem
 */
public class ETHRedeem extends BinanceItem {

    /**
     * {@code success} whether the redeem has been successful
     */
    private final boolean success;

    /**
     * {@code arrivalTime} the arrival time of the redeem
     */
    private final long arrivalTime;

    /**
     * Constructor to init a {@link ETHRedeem} object
     *
     * @param success:     whether the redeem has been successful
     * @param arrivalTime: the arrival time of the redeem
     */
    public ETHRedeem(boolean success, long arrivalTime) {
        super(null);
        this.success = success;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Constructor to init a {@link ETHRedeem} object
     *
     * @param jETHRedeem: ETH redeem details as {@link JSONObject}
     */
    public ETHRedeem(JSONObject jETHRedeem) {
        super(jETHRedeem);
        success = hItem.getBoolean("success");
        arrivalTime = hItem.getLong("arrivalTime", 0);
    }

    /**
     * Method to get {@link #success} instance <br>
     * No-any params required
     *
     * @return {@link #success} instance as boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method to get {@link #arrivalTime} instance <br>
     * No-any params required
     *
     * @return {@link #arrivalTime} instance as long
     */
    public long getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Method to get {@link #arrivalTime} instance <br>
     * No-any params required
     *
     * @return {@link #arrivalTime} instance as {@link Date}
     */
    public Date getArrivalDate() {
        return TimeFormatter.getDate(arrivalTime);
    }

}
