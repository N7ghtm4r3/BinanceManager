package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code AssetTransfer} class is useful to format an asset transfer
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-spot-asset-transfer-history-for-master-account">
 *             Query Sub-account Spot Asset Transfer History (For Master Account)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-futures-asset-transfer-history-for-master-account">
 *             Query Sub-account Futures Asset Transfer History (For Master Account)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
public class AssetTransfer extends BinanceItem {

    /**
     * {@code from} of the asset transfer
     */
    protected final String from;

    /**
     * {@code to} of the asset transfer
     */
    protected final String to;

    /**
     * {@code asset} of the transfer
     */
    protected final String asset;

    /**
     * {@code qty} quantity of the asset transfer
     */
    protected final double qty;

    /**
     * {@code tranId} transaction id of the asset transfer
     */
    protected final long tranId;

    /**
     * {@code time} of the asset transfer
     */
    protected final long time;

    /**
     * Constructor to init {@link AssetTransfer} object
     *
     * @param from   : from of the asset transfer
     * @param to     : to of the asset transfer
     * @param asset  : asset of the transfer
     * @param qty    : quantity of the asset transfer
     * @param tranId : transaction id of the asset transfer
     * @param time   : time of the asset transfer
     */
    public AssetTransfer(String from, String to, String asset, double qty, long tranId, long time) {
        super(null);
        this.from = from;
        this.to = to;
        this.asset = asset;
        this.qty = qty;
        this.tranId = tranId;
        this.time = time;
    }

    /**
     * Constructor to init {@link AssetTransfer} object
     *
     * @param jAssetTransfer: asset transfer details as {@link JSONObject}
     */
    public AssetTransfer(JSONObject jAssetTransfer) {
        super(jAssetTransfer);
        from = hItem.getString("from");
        to = hItem.getString("to");
        asset = hItem.getString("asset");
        qty = hItem.getDouble("qty", 0);
        tranId = hItem.getLong("tranId", 0);
        time = hItem.getLong("time", 0);
    }

    /**
     * Method to get {@link #from} instance <br>
     * No-any params required
     *
     * @return {@link #from} instance as {@link String}
     */
    public String getFrom() {
        return from;
    }

    /**
     * Method to get {@link #to} instance <br>
     * No-any params required
     *
     * @return {@link #to} instance as {@link String}
     */
    public String getTo() {
        return to;
    }

    /**
     * Method to get {@link #asset} instance <br>
     * No-any params required
     *
     * @return {@link #asset} instance as {@link String}
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method to get {@link #qty} instance <br>
     * No-any params required
     *
     * @return {@link #qty} instance as double
     */
    public double getQty() {
        return qty;
    }

    /**
     * Method to get {@link #qty} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #qty} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getQty(int decimals) {
        return roundValue(qty, decimals);
    }

    /**
     * Method to get {@link #tranId} instance <br>
     * No-any params required
     *
     * @return {@link #tranId} instance as long
     */
    public long getTranId() {
        return tranId;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as long
     */
    public long getTime() {
        return time;
    }

    /**
     * Method to get {@link #time} instance <br>
     * No-any params required
     *
     * @return {@link #time} instance as {@link Date}
     */
    public Date getDate() {
        return TimeFormatter.getDate(time);
    }

}
