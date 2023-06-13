package com.tecknobit.binancemanager.managers.signedmanagers.bswap.records;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code UnclaimedRewards} class is useful to format an unclaimed rewards
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at:  <a href="https://binance-docs.github.io/apidocs/spot/en/#get-unclaimed-rewards-record-user_data">
 * Get Unclaimed Rewards Record (USER_DATA)</a>
 * @see BinanceItem
 */
public class UnclaimedRewards extends BinanceItem {

    /**
     * {@code RewardsType} list of available rewards types
     */
    public enum RewardsType {

        /**
         * {@code SWAP_REWARDS} rewards type
         */
        SWAP_REWARDS(0),

        /**
         * {@code LIQUIDITY_REWARD} rewards type
         */
        LIQUIDITY_REWARD(1);

        /**
         * {@code type} rewards type
         */
        private final int type;

        /**
         * Constructor to init {@link RewardsType} object
         *
         * @param type: rewards type
         */
        RewardsType(int type) {
            this.type = type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as int
         */
        public int getType() {
            return type;
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link String}
         */
        @Override
        public String toString() {
            return type + "";
        }

    }

    /**
     * {@code totalUnclaimedRewards} total unclaimed rewards value
     */
    private final HashMap<String, Double> totalUnclaimedRewards;

    /**
     * {@code details} details of the unclaimed rewards
     */
    private final ArrayList<Detail> details;

    /**
     * Constructor to init {@link UnclaimedRewards} object
     *
     * @param totalUnclaimedRewards: total unclaimed rewards value
     * @param details: details of the unclaimed rewards
     */
    public UnclaimedRewards(HashMap<String, Double> totalUnclaimedRewards, ArrayList<Detail> details) {
        super(null);
        this.totalUnclaimedRewards = totalUnclaimedRewards;
        this.details = details;
    }

    /**
     * Constructor to init {@link UnclaimedRewards} object
     *
     * @param jUnclaimedRewards: unclaimed rewards details as {@link JSONObject}
     */
    public UnclaimedRewards(JSONObject jUnclaimedRewards) {
        super(jUnclaimedRewards);
        totalUnclaimedRewards = new HashMap<>();
        JSONObject jItem = hItem.getJSONObject("totalUnclaimedRewards", new JSONObject());
        for (String asset : jItem.keySet())
            totalUnclaimedRewards.put(asset, jItem.getDouble(asset));
        details = new ArrayList<>();
        jItem = hItem.getJSONObject("details");
        for (String pair : jItem.keySet())
            details.add(new Detail(jItem.getJSONObject(pair).put("pair", pair)));
    }

    /**
     * Method to get {@link #totalUnclaimedRewards} instance <br>
     * No-any params required
     *
     * @return {@link #totalUnclaimedRewards} instance as {@link HashMap} of {@link String} and {@link Double}
     */
    public HashMap<String, Double> getTotalUnclaimedRewards() {
        return totalUnclaimedRewards;
    }

    /**
     * Method to get {@link #details} instance <br>
     * No-any params required
     *
     * @return {@link #details} instance as {@link ArrayList} of {@link Detail}
     */
    public ArrayList<Detail> getDetails() {
        return details;
    }

    /**
     * The {@code Detail} class is useful to format a detail for the unclaimed rewards
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class Detail extends BinanceItem {

        /**
         * {@code pair} of the detail
         */
        private final String pair;

        /**
         * {@code rewards} of the detail
         */
        private final HashMap<String, Double> rewards;

        /**
         * Constructor to init {@link Detail} object
         *
         * @param pair: pair of the detail
         * @param rewards: rewards of the detail
         */
        public Detail(String pair, HashMap<String, Double> rewards) {
            super(null);
            this.pair = pair;
            this.rewards = rewards;
        }

        /**
         * Constructor to init {@link Detail} object
         *
         * @param jDetail: details as {@link JSONObject}
         */
        public Detail(JSONObject jDetail) {
            super(jDetail);
            pair = hItem.getString("pair");
            jDetail.remove("pair");
            rewards = new HashMap<>();
            for (String key : jDetail.keySet())
                rewards.put(key, jDetail.getDouble(key));
        }

        /**
         * Method to get {@link #pair} instance <br>
         * No-any params required
         *
         * @return {@link #pair} instance as {@link String}
         */
        public String getPair() {
            return pair;
        }

        /**
         * Method to get {@link #rewards} instance <br>
         * No-any params required
         *
         * @return {@link #rewards} instance as {@link HashMap} of {@link String} and {@link Double}
         */
        public HashMap<String, Double> getRewards() {
            return rewards;
        }

    }

}
