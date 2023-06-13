package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring;

import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.acquiring.AcquiringAlgorithm.AcquiringAlgorithmItem;

/**
 * The {@code AcquiringAlgorithm} class is useful to create an acquiring algorithm
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#acquiring-coinname-market_data">
 * Acquiring CoinName (MARKET_DATA)</a>
 * @see BinanceItem
 * @see BinanceManager.BinanceResponse
 * @see MiningResponse
 */
public class AcquiringAlgorithm extends MiningResponse<ArrayList<AcquiringAlgorithmItem>> {

    /**
     * Constructor to init {@link AcquiringAlgorithm} object
     *
     * @param data: acquiring algorithms
     */
    public AcquiringAlgorithm(ArrayList<AcquiringAlgorithmItem> data) {
        super(data);
    }

    /**
     * Constructor to init {@link AcquiringAlgorithm} object
     *
     * @param jAcquiringAlgorithm: acquiring algorithm details as {@link JSONObject}
     */
    public AcquiringAlgorithm(JSONObject jAcquiringAlgorithm) {
        super(jAcquiringAlgorithm);
        data = new ArrayList<>();
        for (Object rData : hItem.fetchList("data"))
            data.add(new AcquiringAlgorithmItem((JSONObject) rData));
    }

    /**
     * The {@code AcquiringAlgorithmItem} class is useful to create an acquiring algorithm item
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see AcquiringStructure
     */
    public static class AcquiringAlgorithmItem extends AcquiringStructure {

        /**
         * {@code unit} of the algorithm item
         */
        private final String unit;

        /**
         * Constructor to init {@link AcquiringAlgorithmItem} object
         *
         * @param algoName: algo name of the algorithm item
         * @param algoId: algo id of the algorithm item
         * @param poolIndex: pool index of the algorithm item
         * @param unit: unit of the algorithm item
         */
        public AcquiringAlgorithmItem(String algoName, long algoId, int poolIndex, String unit) {
            super(algoName, algoId, poolIndex);
            this.unit = unit;
        }

        /**
         * Constructor to init {@link AcquiringAlgorithmItem} object
         *
         * @param jAcquiringAlgorithm: acquiring algorithm item details as {@link JSONObject}
         */
        public AcquiringAlgorithmItem(JSONObject jAcquiringAlgorithm) {
            super(jAcquiringAlgorithm);
            unit = hItem.getString("unit");
        }

        /**
         * Method to get {@link #unit} instance <br>
         * No-any params required
         *
         * @return {@link #unit} instance as {@link String}
         */
        public String getUnit() {
            return unit;
        }

    }

}
