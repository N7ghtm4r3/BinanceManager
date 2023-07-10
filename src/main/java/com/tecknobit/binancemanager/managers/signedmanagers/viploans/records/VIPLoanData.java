package com.tecknobit.binancemanager.managers.signedmanagers.viploans.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tecknobit.binancemanager.managers.signedmanagers.viploans.records.VIPLoanData.VIPCollateralAsset;

/**
 * The {@code VIPLoanData} class is useful to format a {@code Binance}'s VIP loan data
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:
 * <ul>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#get-collateral-asset-data-user_data">
 *             Get Collateral Asset Data (USER_DATA)</a>
 *     </li>
 *     <li>
 *         <a href="https://binance-docs.github.io/apidocs/spot/en/#query-application-status-user_data">
 *             Query Application Status (USER_DATA)</a>
 *     </li>
 * </ul>
 * @see BinanceItem
 * @see BinanceRowsList
 */
public class VIPLoanData extends BinanceRowsList<VIPCollateralAsset> {

    /**
     * Constructor to init {@link VIPLoanData} object
     *
     * @param rows :  list of the items
     */
    public VIPLoanData(ArrayList<VIPCollateralAsset> rows) {
        super(rows);
    }

    /**
     * Constructor to init {@link VIPLoanData} object
     *
     * @param total : number of items
     * @param rows  :  list of the items
     */
    public VIPLoanData(int total, ArrayList<VIPCollateralAsset> rows) {
        super(total, rows);
    }

    /**
     * Constructor to init {@link VIPLoanData}
     *
     * @param jList : VIP loan data details as {@link JSONObject}
     */
    public VIPLoanData(JSONObject jList) {
        super(jList);
        for (Object row : hItem.fetchList("rows"))
            rows.add(new VIPCollateralAsset((JSONObject) row));
    }

    /**
     * The {@code VIPCollateralAsset} class is useful to format a {@code Binance}'s VIP collateral asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class VIPCollateralAsset extends BinanceItem {

        /**
         * {@code collateralCoin} collateral coin value
         */
        private final String collateralCoin;

        /**
         * {@code collateralRatios} all the collateral ratios
         */
        private final HashMap<String, String> collateralRatios;

        /**
         * {@code collateralRanges} all the collateral ranges
         */
        private final HashMap<String, String> collateralRanges;

        /**
         * Constructor to init a {@link VIPCollateralAsset} object
         *
         * @param collateralCoin:   collateral coin value
         * @param collateralRatios: all the collateral ratios
         * @param collateralRanges: all the collateral ranges
         */
        public VIPCollateralAsset(String collateralCoin, HashMap<String, String> collateralRatios,
                                  HashMap<String, String> collateralRanges) {
            super(null);
            this.collateralCoin = collateralCoin;
            this.collateralRatios = collateralRatios;
            this.collateralRanges = collateralRanges;
        }

        /**
         * Constructor to init a {@link VIPCollateralAsset} object
         *
         * @param jVIPCollateralAsset: VIP collateral asset details as {@link JSONObject}
         */
        public VIPCollateralAsset(JSONObject jVIPCollateralAsset) {
            super(jVIPCollateralAsset);
            collateralCoin = hItem.getString("collateralCoin");
            collateralRatios = new HashMap<>();
            collateralRanges = new HashMap<>();
            for (String key : jVIPCollateralAsset.keySet()) {
                if (key.contains("Ratio"))
                    collateralRatios.put(key, hItem.getString(key));
                else if (key.contains("Range"))
                    collateralRanges.put(key, hItem.getString(key));
            }
        }

        /**
         * Method to get {@link #collateralCoin} instance <br>
         * No-any params required
         *
         * @return {@link #collateralCoin} instance as {@link String}
         */
        public String getCollateralCoin() {
            return collateralCoin;
        }

        /**
         * Method to get {@link #collateralRatios} instance <br>
         * No-any params required
         *
         * @return {@link #collateralRatios} instance as {@link HashMap} of {@link String}, {@link String}
         */
        public HashMap<String, String> getCollateralRatios() {
            return collateralRatios;
        }

        /**
         * Method to get {@link #collateralRanges} instance <br>
         * No-any params required
         *
         * @return {@link #collateralRanges} instance as {@link HashMap} of {@link String}, {@link String}
         */
        public HashMap<String, String> getCollateralRanges() {
            return collateralRanges;
        }

    }

}
