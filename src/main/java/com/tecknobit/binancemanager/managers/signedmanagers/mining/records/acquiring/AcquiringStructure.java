package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

/**
 * The {@code AcquiringStructure} class is useful to create an acquiring structure
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-algorithm-market_data">
 *             Acquiring Algorithm (MARKET_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
 *             Acquiring CoinName (MARKET_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 */
@Structure
public abstract class AcquiringStructure extends BinanceItem {

    /**
     * {@code algoName} algo name of the acquiring structure
     */
    protected final String algoName;

    /**
     * {@code algoId} algo id of the acquiring structure
     */
    protected final long algoId;

    /**
     * {@code poolIndex} pool index of the acquiring structure
     */
    protected final int poolIndex;

    /**
     * Constructor to init {@link AcquiringStructure} object
     *
     * @param algoName:  algo name of the acquiring structure
     * @param algoId:    algo id of the acquiring structure
     * @param poolIndex: pool index of the acquiring structure
     */
    public AcquiringStructure(String algoName, long algoId, int poolIndex) {
        super(null);
        this.algoName = algoName;
        this.algoId = algoId;
        this.poolIndex = poolIndex;
    }

    /**
     * Constructor to init {@link AcquiringStructure} object
     *
     * @param jAcquiringStructure: acquiring structure details as {@link JSONObject}
     */
    public AcquiringStructure(JSONObject jAcquiringStructure) {
        super(jAcquiringStructure);
        algoName = hItem.getString("algoName");
        algoId = hItem.getLong("algoId", 0);
        poolIndex = hItem.getInt("poolIndex", 0);
    }

    /**
     * Method to get {@link #algoName} instance <br>
     * No-any params required
     *
     * @return {@link #algoName} instance as {@link String}
     */
    public String getAlgoName() {
        return algoName;
    }

    /**
     * Method to get {@link #algoId} instance <br>
     * No-any params required
     *
     * @return {@link #algoId} instance as long
     */
    public long getAlgoId() {
        return algoId;
    }

    /**
     * Method to get {@link #poolIndex} instance <br>
     * No-any params required
     *
     * @return {@link #poolIndex} instance as int
     */
    public int getPoolIndex() {
        return poolIndex;
    }

}
