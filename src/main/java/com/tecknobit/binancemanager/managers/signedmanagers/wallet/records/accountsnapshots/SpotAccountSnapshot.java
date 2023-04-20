package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.apimanager.annotations.Returner;
import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType.spot;

/**
 * The {@code SpotAccountSnapshot} class is useful to format a {@code "Binance"}'s spot account snapshot
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * Daily Account Snapshot (USER_DATA)</a>
 * @see AccountSnapshot
 **/
public class SpotAccountSnapshot extends AccountSnapshot {

    /**
     * {@code assetsSpotData} is instance that memorizes list of {@link SpotData}
     **/
    private ArrayList<SpotData> assetsSpotData;

    /**
     * Constructor to init {@link SpotAccountSnapshot} object
     *
     * @param code:           code of response
     * @param msg:            message of response
     * @param snapshotVos:    details as {@link JSONArray}
     * @param assetsSpotData: list of {@link SpotData}
     **/
    public SpotAccountSnapshot(int code, String msg, JSONArray snapshotVos, ArrayList<SpotData> assetsSpotData) {
        super(code, msg, spot, snapshotVos);
        this.assetsSpotData = assetsSpotData;
    }

    /**
     * Constructor to init {@link SpotAccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     **/
    public SpotAccountSnapshot(int code, String msg, String snapshotVos) {
        super(code, msg, spot, snapshotVos);
    }

    /**
     * Constructor to init {@link SpotAccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     **/
    public <T> SpotAccountSnapshot(int code, String msg, T snapshotVos) {
        super(code, msg, spot, snapshotVos);
    }

    /**
     * Constructor to init {@link SpotAccountSnapshot} object
     *
     * @param spotAccount: futures account snapshot details as {@link JSONObject}
     **/
    public SpotAccountSnapshot(JSONObject spotAccount) {
        super(spotAccount);
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
    @Returner
    public static ArrayList<SpotBalance> getBalancesSpot(JSONArray jsonBalances) {
        ArrayList<SpotBalance> spotBalances = new ArrayList<>();
        for (int j = 0; j < jsonBalances.length(); j++)
            spotBalances.add(new SpotBalance(jsonBalances.getJSONObject(j)));
        return spotBalances;
    }

    /**
     * Method to get {@link #assetsSpotData} instance <br>
     * No-any params required
     *
     * @return {@link #assetsSpotData} instance as {@link ArrayList} of {@link SpotData}
     **/
    public ArrayList<SpotData> getAssetsSpotData() {
        return assetsSpotData;
    }

    /**
     * Method to set {@link #assetsSpotData} instance <br>
     *
     * @param assetsSpotData: list of {@link SpotData} to set
     **/
    public void setAssetsSpotData(ArrayList<SpotData> assetsSpotData) {
        this.assetsSpotData = assetsSpotData;
    }

    /**
     * Method to add a spot data  to {@link #assetsSpotData}
     *
     * @param spotData: spot data to add
     **/
    public void insertDataFuture(SpotData spotData) {
        if (!this.assetsSpotData.contains(spotData))
            this.assetsSpotData.add(spotData);
    }

    /**
     * Method to remove a spot data  from {@link #assetsSpotData}
     *
     * @param spotData: spot data  to remove
     * @return result of operation as boolean
     **/
    public boolean removeDataFuture(SpotData spotData) {
        return assetsSpotData.remove(spotData);
    }

    /**
     * Method to get a spot data from {@link #assetsSpotData} list
     *
     * @param index: index to fetch the SpotData data
     * @return futures spot as {@link SpotData}
     **/
    public SpotData getAssetSpotData(int index) {
        return assetsSpotData.get(index);
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
         * {@code spotBalances} is instance that memorizes list of {@link SpotBalance}
         **/
        private ArrayList<SpotBalance> spotBalances;

        /**
         * Constructor to init {@link SpotData} object
         *
         * @param totalAssetOfBtc: total asset of Bitcoin
         * @param updateTime:      update time value
         * @param spotBalances:    list of {@link SpotBalance}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public SpotData(double totalAssetOfBtc, long updateTime, ArrayList<SpotBalance> spotBalances) {
            if (totalAssetOfBtc < 0)
                throw new IllegalArgumentException("Total asset of BTC value cannot be less than 0");
            else
                this.totalAssetOfBtc = totalAssetOfBtc;
            if (updateTime < 0)
                throw new IllegalArgumentException("Update time value cannot be less than 0");
            else
                this.updateTime = updateTime;
            this.spotBalances = spotBalances;
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
            spotBalances = getBalancesSpot(hSpotData.getJSONArray("balances", new JSONArray()));
        }

        /**
         * Method to get {@link #totalAssetOfBtc} instance <br>
         * No-any params required
         *
         * @return {@link #totalAssetOfBtc} instance as double
         **/
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

        /**
         * Method to get {@link #updateTime} instance <br>
         * No-any params required
         *
         * @return {@link #updateTime} instance as long
         **/
        public long getUpdateTime() {
            return updateTime;
        }

        /**
         * Method to get {@link #updateTime} instance <br>
         * No-any params required
         *
         * @return {@link #updateTime} instance as {@link Date}
         **/
        public Date getUpdateDate() {
            return TimeFormatter.getDate(updateTime);
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

        /**
         * Method to get {@link #spotBalances} instance <br>
         * No-any params required
         *
         * @return {@link #spotBalances} instance as {@link ArrayList} of {@link SpotBalance}
         **/
        public ArrayList<SpotBalance> getBalancesSpotList() {
            return spotBalances;
        }

        /**
         * Method to set {@link #spotBalances} instance <br>
         *
         * @param spotBalances: list of {@link SpotBalance} to set
         **/
        public void setSpotBalancesList(ArrayList<SpotBalance> spotBalances) {
            this.spotBalances = spotBalances;
        }

        /**
         * Method to add a spot balance  to {@link #spotBalances}
         *
         * @param spotBalance: spot balance to add
         **/
        public void insertSpotBalance(SpotBalance spotBalance) {
            if (!spotBalances.contains(spotBalance))
                spotBalances.add(spotBalance);
        }

        /**
         * Method to remove a spot balance  from {@link #spotBalances}
         *
         * @param spotBalance: spot balance  to remove
         * @return result of operation as boolean
         **/
        public boolean removeSpotBalance(SpotBalance spotBalance) {
            return spotBalances.remove(spotBalance);
        }

        /**
         * Method to get a spot balance from {@link #spotBalances} list
         *
         * @param index: index to fetch the spot balance
         * @return spot balance as {@link SpotBalance}
         **/
        public SpotBalance getSpotBalance(int index) {
            return spotBalances.get(index);
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

    }

    /**
     * The {@code SpotBalance} class is useful to format a spot balance
     *
     * @author N7ghtm4r3 - Tecknobit
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

        /**
         * Method to get {@link #asset} instance <br>
         * No-any params required
         *
         * @return {@link #asset} instance as {@link String}
         **/
        public String getAsset() {
            return asset;
        }

        /**
         * Method to get {@link #free} instance <br>
         * No-any params required
         *
         * @return {@link #free} instance as double
         **/
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

        /**
         * Method to get {@link #locked} instance <br>
         * No-any params required
         *
         * @return {@link #locked} instance as double
         **/
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

    }

}
