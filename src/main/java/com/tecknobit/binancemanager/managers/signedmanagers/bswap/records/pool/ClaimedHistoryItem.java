package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records.pool;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code ClaimedHistoryItem} class is useful to format a claimed history item
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#get-claimed-history-user_data">
 * Get Claimed History (USER_DATA)</a>
 * @see BinanceItem
 * @see PoolStructure
 **/
public class ClaimedHistoryItem extends PoolStructure {

    /**
     * {@code assetRewards} asset rewards of the item
     **/
    private final String assetRewards;

    /**
     * {@code claimTime} claim time of the item
     **/
    private final long claimTime;

    /**
     * {@code claimAmount} claim amount of the item
     **/
    private final double claimAmount;

    /**
     * {@code status} of the item
     **/
    private final BSwapStatus status;

    /**
     * Constructor to init {@link ClaimedHistoryItem} object
     *
     * @param poolId:       id of the item
     * @param poolName:     name of the item
     * @param assetRewards: asset rewards of the item
     * @param claimTime:    claim time of the item
     * @param claimAmount:  claim amount of the item
     * @param status:       status of the item
     **/
    public ClaimedHistoryItem(long poolId, String poolName, String assetRewards, long claimTime, double claimAmount,
                              BSwapStatus status) {
        super(poolId, poolName);
        this.assetRewards = assetRewards;
        this.claimTime = claimTime;
        this.claimAmount = claimAmount;
        this.status = status;
    }

    /**
     * Constructor to init {@link ClaimedHistoryItem} object
     *
     * @param jClaimedHistoryItem: claimed history item details as {@link JSONObject}
     **/
    public ClaimedHistoryItem(JSONObject jClaimedHistoryItem) {
        super(jClaimedHistoryItem);
        assetRewards = hItem.getString("assetRewards");
        claimTime = hItem.getLong("claimTime", 0);
        claimAmount = hItem.getDouble("claimAmount", 0);
        status = BSwapStatus.reachEnumConstant(hItem.getInt("status", 0));
    }

    /**
     * Method to get {@link #assetRewards} instance <br>
     * No-any params required
     *
     * @return {@link #assetRewards} instance as {@link String}
     **/
    public String getAssetRewards() {
        return assetRewards;
    }

    /**
     * Method to get {@link #claimTime} instance <br>
     * No-any params required
     *
     * @return {@link #claimTime} instance as long
     **/
    public long getClaimTime() {
        return claimTime;
    }

    /**
     * Method to get {@link #claimTime} instance <br>
     * No-any params required
     *
     * @return {@link #claimTime} instance as {@link Date}
     **/
    public Date getClaimDate() {
        return TimeFormatter.getDate(claimTime);
    }

    /**
     * Method to get {@link #claimAmount} instance <br>
     * No-any params required
     *
     * @return {@link #claimAmount} instance as double
     **/
    public double getClaimAmount() {
        return claimAmount;
    }

    /**
     * Method to get {@link #claimAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #claimAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getClaimAmount(int decimals) {
        return roundValue(claimAmount, decimals);
    }

    /**
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link BSwapStatus}
     **/
    public BSwapStatus getStatus() {
        return status;
    }

}
