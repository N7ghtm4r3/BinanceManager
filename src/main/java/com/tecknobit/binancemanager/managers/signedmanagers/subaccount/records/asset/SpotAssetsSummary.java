package com.tecknobit.binancemanager.managers.signedmanagers.subaccount.records.asset;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SpotAssetsSummary} class is useful to format a spot assets summary
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#query-sub-account-assets-for-master-account">
 * Query Sub-account Spot Assets Summary (For Master Account)</a>
 * @see BinanceItem
 * @see AssetTransfer
 */
public class SpotAssetsSummary extends BinanceItem {

    /**
     * {@code totalCount} total count of the spot assets summary
     */
    private final int totalCount;

    /**
     * {@code masterAccountTotalAsset} master account total asset of the spot assets summary
     */
    private final double masterAccountTotalAsset;

    /**
     * {@code spotSubUserAssetBtcVoList} spot sub-user asset Btc vo list of the spot assets summary
     */
    private final ArrayList<SpotSubUserAsset> spotSubUserAssetBtcVoList;

    /**
     * Constructor to init {@link SpotAssetsSummary} object
     *
     * @param totalCount     : total count of the spot assets summary
     * @param masterAccountTotalAsset     : master account total asset of the spot assets summary
     * @param spotSubUserAssetBtcVoList : spot sub-user asset Btc vo list of the spot assets summary
     */
    public SpotAssetsSummary(int totalCount, double masterAccountTotalAsset,
                             ArrayList<SpotSubUserAsset> spotSubUserAssetBtcVoList) {
        super(null);
        this.totalCount = totalCount;
        this.masterAccountTotalAsset = masterAccountTotalAsset;
        this.spotSubUserAssetBtcVoList = spotSubUserAssetBtcVoList;
    }

    /**
     * Constructor to init {@link SpotAssetsSummary} object
     *
     * @param jSpotAssetsSummary: spot assets summary details as {@link JSONObject}
     */
    public SpotAssetsSummary(JSONObject jSpotAssetsSummary) {
        super(jSpotAssetsSummary);
        totalCount = hItem.getInt("totalCount", 0);
        masterAccountTotalAsset = hItem.getDouble("masterAccountTotalAsset", 0);
        spotSubUserAssetBtcVoList = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("spotSubUserAssetBtcVoList");
        if (jList != null)
            for (JSONObject item : jList)
                spotSubUserAssetBtcVoList.add(new SpotSubUserAsset(item));
    }

    /**
     * Method to get {@link #totalCount} instance <br>
     * No-any params required
     *
     * @return {@link #totalCount} instance as int
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Method to get {@link #masterAccountTotalAsset} instance <br>
     * No-any params required
     *
     * @return {@link #masterAccountTotalAsset} instance as double
     */
    public double getMasterAccountTotalAsset() {
        return masterAccountTotalAsset;
    }

    /**
     * Method to get {@link #masterAccountTotalAsset} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #masterAccountTotalAsset} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMasterAccountTotalAsset(int decimals) {
        return roundValue(masterAccountTotalAsset, decimals);
    }

    /**
     * Method to get {@link #spotSubUserAssetBtcVoList} instance <br>
     * No-any params required
     *
     * @return {@link #spotSubUserAssetBtcVoList} instance as {@link ArrayList} of {@link SpotSubUserAsset}
     */
    public ArrayList<SpotSubUserAsset> getSpotSubUserAssetBtcVoList() {
        return spotSubUserAssetBtcVoList;
    }

    /**
     * The {@code SpotSubUserAsset} class is useful to format a spot sub-user asset
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see AssetTransfer
     */
    public static class SpotSubUserAsset extends BinanceItem {

        /**
         * {@code email} of the spot sub-user asset
         */
        private final String email;

        /**
         * {@code totalAsset} total asset of the spot sub-user asset
         */
        private final double totalAsset;

        /**
         * Constructor to init {@link SpotSubUserAsset} object
         *
         * @param email: email of the spot sub-user asset
         * @param totalAsset: total asset of the spot sub-user asset
         */
        public SpotSubUserAsset(String email, double totalAsset) {
            super(null);
            this.email = email;
            this.totalAsset = totalAsset;
        }

        /**
         * Constructor to init {@link SpotSubUserAsset} object
         *
         * @param jSpotSubUserAsset: spot sub-user asset details as {@link JSONObject}
         */
        public SpotSubUserAsset(JSONObject jSpotSubUserAsset) {
            super(jSpotSubUserAsset);
            email = hItem.getString("email");
            totalAsset = hItem.getDouble("totalAsset", 0);
        }

        /**
         * Method to get {@link #email} instance <br>
         * No-any params required
         *
         * @return {@link #email} instance as {@link String}
         */
        public String getEmail() {
            return email;
        }

        /**
         * Method to get {@link #totalAsset} instance <br>
         * No-any params required
         *
         * @return {@link #totalAsset} instance as double
         */
        public double getTotalAsset() {
            return totalAsset;
        }

        /**
         * Method to get {@link #totalAsset} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalAsset} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTotalAsset(int decimals) {
            return roundValue(totalAsset, decimals);
        }

    }

}
