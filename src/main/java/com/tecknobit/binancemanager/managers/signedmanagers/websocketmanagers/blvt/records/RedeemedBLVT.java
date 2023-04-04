package com.tecknobit.binancemanager.managers.signedmanagers.websocketmanagers.blvt.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code RedeemedBLVT} class is useful to format a redeemed BLVT
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#redeem-blvt-user_data">
 * Redeem BLVT (USER_DATA)</a>
 * @see BinanceItem
 * @see BLVTStructure
 **/
public class RedeemedBLVT extends BLVTStructure {

    /**
     * {@code status} of the redeemed BLVT
     **/
    private final BLVTStatus status;

    /**
     * {@code redeemAmount} amount of the redeemed BLVT
     **/
    private final double redeemAmount;

    /**
     * Constructor to init {@link RedeemedBLVT} object
     *
     * @param id:           id of the BLVT
     * @param tokenName:    token name of the BLVT
     * @param amount:       amount of the BLVT
     * @param timestamp:    timestamp of the BLVT
     * @param status:       status of the redeemed BLVT
     * @param redeemAmount: amount of the redeemed BLVT
     **/
    public RedeemedBLVT(long id, String tokenName, double amount, long timestamp, BLVTStatus status, double redeemAmount) {
        super(id, tokenName, amount, timestamp);
        this.status = status;
        this.redeemAmount = redeemAmount;
    }

    /**
     * Constructor to init {@link RedeemedBLVT} object
     *
     * @param jRedeemedBLVT: redeemed BLVT details as {@link JSONObject}
     **/
    public RedeemedBLVT(JSONObject jRedeemedBLVT) {
        super(jRedeemedBLVT);
        status = BLVTStatus.valueOf(hItem.getString("status"));
        redeemAmount = hItem.getDouble("redeemAmount", 0);
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
     * Method to get {@link #redeemAmount} instance <br>
     * No-any params required
     *
     * @return {@link #redeemAmount} instance as double
     **/
    public double getRedeemAmount() {
        return redeemAmount;
    }

    /**
     * Method to get {@link #redeemAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #redeemAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getRedeemAmount(int decimals) {
        return roundValue(redeemAmount, decimals);
    }

}
