package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code SpotAccountSnapshot} class is useful to obtain and format SpotAccountSnapshot object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 **/

public class SpotAccountSnapshot extends AccountSnapshot {

    /**
     * {@code assetsSpotData} is instance that memorizes list of {@link SpotData}
     **/
    private ArrayList<SpotData> assetsSpotData;

    /**
     * Constructor to init {@link MarginAccountSnapshot} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param type:           type of account
     * @param accountDetails: details as {@link JSONObject}
     * @param assetsSpotData: list of {@link SpotData}
     **/
    public SpotAccountSnapshot(int code, String msg, String type, JSONArray accountDetails, ArrayList<SpotData> assetsSpotData) {
        super(code, msg, type, accountDetails);
        this.assetsSpotData = assetsSpotData;
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param spotAccount: futures account snapshot details as {@link JSONObject}
     **/
    public SpotAccountSnapshot(JSONObject spotAccount) {
        super(spotAccount, SPOT);
        assetsSpotData = new ArrayList<>();
        for (int j = 0; j < snapshotVos.length(); j++)
            assetsSpotData.add(new SpotData(snapshotVos.getJSONObject(j)));
    }

    /**
     * Method to assemble a {@link SpotBalance} list
     *
     * @param jsonBalances: snapshotVos obtain by AccountSnapshot {@code "Binance"} request
     * @return list as {@link ArrayList} of {@link SpotBalance}
     **/
    public static ArrayList<SpotBalance> getBalancesSpot(JSONArray jsonBalances) {
        ArrayList<SpotBalance> spotBalances = new ArrayList<>();
        for (int j = 0; j < jsonBalances.length(); j++)
            spotBalances.add(new SpotBalance(jsonBalances.getJSONObject(j)));
        return spotBalances;
    }

    public ArrayList<SpotData> getAssetsSpotData() {
        return assetsSpotData;
    }

    public void setAssetsSpotData(ArrayList<SpotData> assetsSpotData) {
        this.assetsSpotData = assetsSpotData;
    }

    public SpotData getAssetSpotData(int index) {
        return assetsSpotData.get(index);
    }

    @Override
    public String toString() {
        return "SpotAccountSnapshot{" +
                "assetsSpotData=" + assetsSpotData +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * The {@code SpotData} class is useful to create a spot data object
     **/

    public static class SpotData {

        /**
         * {@code totalAssetOfBtc} is instance that memorizes total asset of Bitcoin
         **/
        private double totalAssetOfBtc;

        /**
         * {@code updateTime} is instance that memorizes update time value
         **/
        private long updateTime;

        /**
         * {@code spotBalanceList} is instance that memorizes list of {@link SpotBalance}
         **/
        private ArrayList<SpotBalance> spotBalanceList;

        /**
         * Constructor to init {@link SpotData} object
         *
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @param updateTime:      update time value
         * @param spotBalance:     list of {@link SpotBalance}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SpotData(double totalAssetOfBtc, long updateTime, ArrayList<SpotBalance> spotBalance) {
            if (totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            else
                this.totalAssetOfBtc = totalAssetOfBtc;
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            else
                this.updateTime = updateTime;
            this.spotBalanceList = spotBalance;
        }

        /**
         * Constructor to init {@link SpotData} object
         *
         * @param spotData: spot data details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SpotData(JSONObject spotData) {
            JsonHelper hSpotData = new JsonHelper(spotData);
            totalAssetOfBtc = hSpotData.getDouble("totalAssetOfBtc", 0);
            if (totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            updateTime = hSpotData.getLong("updateTime", 0);
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            spotBalanceList = getBalancesSpot(hSpotData.getJSONArray("balances", new JSONArray()));
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        /**
         * Method to get {@link #totalAssetOfBtc} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalAssetOfBtc} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalAssetOfBtc(int decimals) {
            return roundValue(totalAssetOfBtc, decimals);
        }

        /**
         * Method to set {@link #totalAssetOfBtc}
         *
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @throws IllegalArgumentException when total asset of Bitcoin value is less than 0
         **/
        public void setTotalAssetOfBtc(double totalAssetOfBtc) {
            if (totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            this.totalAssetOfBtc = totalAssetOfBtc;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Method to set {@link #updateTime}
         *
         * @param updateTime: update time value
         * @throws IllegalArgumentException when update time value is less than 0
         **/
        public void setUpdateTime(long updateTime) {
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            this.updateTime = updateTime;
        }

        public ArrayList<SpotBalance> getBalancesSpotList() {
            return spotBalanceList;
        }

        public void setBalancesSpotList(ArrayList<SpotBalance> spotBalanceList) {
            this.spotBalanceList = spotBalanceList;
        }

        public void insertBalanceSpot(SpotBalance spotBalance) {
            if (!spotBalanceList.contains(spotBalance))
                spotBalanceList.add(spotBalance);
        }

        public boolean removeBalanceSpot(SpotBalance spotBalance) {
            return spotBalanceList.remove(spotBalance);
        }

        public SpotBalance getBalanceSpot(int index) {
            return spotBalanceList.get(index);
        }

        @Override
        public String toString() {
            return "SpotData{" +
                    "totalAssetOfBtc=" + totalAssetOfBtc +
                    ", updateTime=" + updateTime +
                    ", spotBalanceList=" + spotBalanceList +
                    '}';
        }

    }

    /**
     * The {@code SpotBalance} class is useful to create a balance spot object
     **/

    public static class SpotBalance {

        /**
         * {@code asset} is instance that memorizes asset
         **/
        protected final String asset;

        /**
         * {@code free} is instance that memorizes free amount of asset
         **/
        protected double free;

        /**
         * {@code locked} is instance that memorizes amount locked for asset
         **/
        protected double locked;

        /**
         * Constructor to init {@link SpotBalance} object
         *
         * @param asset:  asset
         * @param free:   free amount of asset
         * @param locked: amount locked for asset
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SpotBalance(String asset, double free, double locked) {
            this.asset = asset;
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            else
                this.free = free;
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            else
                this.locked = locked;
        }

        /**
         * Constructor to init {@link SpotBalance} object
         *
         * @param balanceSpot: balance spot details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SpotBalance(JSONObject balanceSpot) {
            asset = balanceSpot.getString("asset");
            free = balanceSpot.getDouble("free");
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            locked = balanceSpot.getDouble("locked");
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
        }

        public double getFree() {
            return free;
        }

        /**
         * Method to get {@link #free} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #free} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getFree(int decimals) {
            return roundValue(free, decimals);
        }

        /**
         * Method to set {@link #free}
         *
         * @param free: free amount of asset
         * @throws IllegalArgumentException when free value is less than 0
         **/
        public void setFree(double free) {
            if (free < 0)
                throw new IllegalArgumentException("Free value cannot be less than 0");
            this.free = free;
        }

        public double getLocked() {
            return locked;
        }

        /**
         * Method to get {@link #locked} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #locked} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getLocked(int decimals) {
            return roundValue(locked, decimals);
        }

        /**
         * Method to set {@link #locked}
         *
         * @param locked: amount locked for asset
         * @throws IllegalArgumentException when amount locked value is less than 0
         **/
        public void setLocked(double locked) {
            if (locked < 0)
                throw new IllegalArgumentException("Locked value cannot be less than 0");
            this.locked = locked;
        }

        @Override
        public String toString() {
            return "SpotBalance{" +
                    "asset='" + asset + '\'' +
                    ", free=" + free +
                    ", locked=" + locked +
                    '}';
        }

    }

}
