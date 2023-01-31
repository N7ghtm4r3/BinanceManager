package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.convert;

import org.json.JSONObject;

/**
 * The {@code BUSDConvert} class is useful to format a {@code "Binance"}'s BUSD conversion
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#busd-convert-trade">
 * BUSD Convert (TRADE)</a>
 **/
public class BUSDConvert {

    /**
     * {@code tranId} transaction id
     **/
    private final long tranId;
    /**
     * {@code status} status of the conversion
     **/
    private final String status;

    /**
     * Constructor to init {@link BUSDConvert} object
     *
     * @param tranId: transaction id
     * @param status: status of the conversion
     **/
    public BUSDConvert(long tranId, String status) {
        this.tranId = tranId;
        this.status = status;
    }

    /**
     * Constructor to init {@link BUSDConvert} object
     *
     * @param jConvert: BUSD convert details as {@link JSONObject}
     **/
    public BUSDConvert(JSONObject jConvert) {
        this(jConvert.getLong("tranId"), jConvert.getString("status"));
    }

    /**
     * Method to get {@link #tranId} instance <br>
     * No-any params required
     *
     * @return {@link #tranId} instance as long
     **/
    public long getTranId() {
        return tranId;
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link String}
     **/
    public String getStatus() {
        return status;
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code Type} list of available account type for a BUSD convert
     **/
    public enum Type {

        /**
         * {@code "MAIN"} account type
         **/
        MAIN,

        /**
         * {@code "CARD"} account type
         **/
        CARD

    }

}
