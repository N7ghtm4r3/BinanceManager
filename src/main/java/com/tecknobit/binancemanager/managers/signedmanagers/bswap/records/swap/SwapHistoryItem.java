package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.swap;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool.PoolStructure.BSwapStatus;
import org.json.JSONObject;

import java.util.Date;

/**
 * The {@code SwapHistoryItem} class is useful to format a swap history item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-swap-history-user_data">
 * Get Swap History (USER_DATA)</a>
 * @see BinanceItem
 * @see SwapBaseStructure
 * @see SwapStructure
 */
public class SwapHistoryItem extends SwapStructure {

    /**
     * {@code swapId} id of the swap
     */
    private final long swapId;

    /**
     * {@code swapTime} time of the swap
     */
    private final long swapTime;

    /**
     * {@code status} of the swap
     */
    private final BSwapStatus status;

    /**
     * Constructor to init {@link SwapStructure} object
     *
     * @param quoteAsset:    quote asset of the swap
     * @param baseAsset:     base asset of the swap
     * @param price:         price of the swap
     * @param fee:           fee of the swap
     * @param quoteQuantity: quote quantity of the swap
     * @param baseQuantity:  quote quantity of the swap
     * @param swapId:        id of the swap
     * @param swapTime:      time of the swap
     * @param status:        status of the swap
     */
    public SwapHistoryItem(String quoteAsset, String baseAsset, double price, double fee, double quoteQuantity,
                           double baseQuantity, long swapId, long swapTime, BSwapStatus status) {
        super(quoteAsset, baseAsset, price, fee, quoteQuantity, baseQuantity);
        this.swapId = swapId;
        this.swapTime = swapTime;
        this.status = status;
    }

    /**
     * Constructor to init {@link SwapHistoryItem} object
     *
     * @param jSwapHistoryItem: swap history item details as {@link JSONObject}
     */
    public SwapHistoryItem(JSONObject jSwapHistoryItem) {
        super(jSwapHistoryItem);
        swapId = hItem.getLong("swapId", 0);
        swapTime = hItem.getLong("swapTime", 0);
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
    }

    /**
     * Method to get {@link #swapId} instance <br>
     * No-any params required
     *
     * @return {@link #swapId} instance as long
     */
    public long getSwapId() {
        return swapId;
    }

    /**
     * Method to get {@link #swapTime} instance <br>
     * No-any params required
     *
     * @return {@link #swapTime} instance as long
     */
    public long getSwapTime() {
        return swapTime;
    }

    /**
     * Method to get {@link #swapTime} instance <br>
     * No-any params required
     *
     * @return {@link #swapTime} instance as {@link Date}
     */
    public Date getSwapDate() {
        return TimeFormatter.getDate(swapTime);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link BSwapStatus}
     */
    public BSwapStatus getStatus() {
        return status;
    }

}
