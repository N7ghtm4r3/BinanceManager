package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.lists.BinanceRowsList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustItem.getListDribbletsDetails;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.dust.DustLogList.AssetDribblets;

/**
 * The {@code DustLogList} class is useful to format a {@code "Binance"}'s dust log
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data">
 * DustLogList(USER_DATA)</a>
 * @see BinanceRowsList
 **/
public class DustLogList extends BinanceRowsList<AssetDribblets> {

    /**
     * Constructor to init {@link DustLogList} object
     *
     * @param total:              total size of {@link #rows}
     * @param userAssetDribblets: list of {@link AssetDribblets}
     **/
    public DustLogList(int total, ArrayList<AssetDribblets> userAssetDribblets) {
        super(total, userAssetDribblets);
    }

    /**
     * Constructor to init {@link DustLogList} object
     *
     * @param jDustLog: dust log details as {@link JSONObject}
     **/
    public DustLogList(JSONObject jDustLog) {
        super(jDustLog);
        JSONArray jDribblets = jDustLog.getJSONArray("userAssetDribblets");
        for (int j = 0; j < jDribblets.length(); j++)
            rows.add(new AssetDribblets(jDribblets.getJSONObject(j)));
    }

    /**
     * The {@code AssetDribblets} class is useful to format a {@code "Binance"}'s asset dribblets
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class AssetDribblets {

        /**
         * {@code operateTime} is instance that memorizes operate time value
         * **/
        private final long operateTime;

        /**
         * {@code totalTransferedAmount} is instance that memorizes total transfered amount value
         * **/
        private final double totalTransferedAmount;

        /**
         * {@code totalServiceChargeAmount} is instance that memorizes total service charge amount value
         * **/
        private final double totalServiceChargeAmount;

        /**
         * {@code transId} is instance that memorizes transaction identifier value
         * **/
        private final long transId;

        /**
         * {@code assetDribbletsDetailsList} is instance that memorizes list of {@link DustItem}
         * **/
        private final ArrayList<DustItem> assetDribbletsDetailsList;

        /** Constructor to init {@link AssetDribblets} object
         * @param operateTime: operate time value
         * @param totalTransferedAmount: total transfered amount value
         * @param totalServiceChargeAmount: total service charge amount value
         * @param transId: transaction identifier value
         * @param assetDribbletsDetails: list of {@link DustItem}
         * **/
        public AssetDribblets(long operateTime, double totalTransferedAmount, double totalServiceChargeAmount,
                              long transId, ArrayList<DustItem> assetDribbletsDetails) {
            this.operateTime = operateTime;
            this.totalTransferedAmount = totalTransferedAmount;
            this.totalServiceChargeAmount = totalServiceChargeAmount;
            this.transId = transId;
            this.assetDribbletsDetailsList = assetDribbletsDetails;
        }

        /**
         * Constructor to init {@link AssetDribblets} object
         *
         * @param assetDribblets: asset dribblets details as {@link JSONObject}
         **/
        public AssetDribblets(JSONObject assetDribblets) {
            operateTime = assetDribblets.getLong("operateTime");
            totalTransferedAmount = assetDribblets.getDouble("totalTransferedAmount");
            totalServiceChargeAmount = assetDribblets.getDouble("totalServiceChargeAmount");
            transId = assetDribblets.getLong("transId");
            assetDribbletsDetailsList = getListDribbletsDetails(assetDribblets.getJSONArray("userAssetDribbletDetails"));
        }

        /**
         * Method to get {@link #operateTime} instance <br>
         * No-any params required
         *
         * @return {@link #operateTime} instance as long
         **/
        public long getOperateTime() {
            return operateTime;
        }

        /**
         * Method to get {@link #operateTime} instance <br>
         * No-any params required
         *
         * @return {@link #operateTime} instance as {@link Date}
         **/
        public Date getOperateDate() {
            return TimeFormatter.getDate(operateTime);
        }

        /**
         * Method to get {@link #totalTransferedAmount} instance <br>
         * No-any params required
         *
         * @return {@link #totalTransferedAmount} instance as double
         **/
        public double getTotalTransferedAmount() {
            return totalTransferedAmount;
        }

        /**
         * Method to get {@link #totalTransferedAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalTransferedAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalTransferedAmount(int decimals) {
            return roundValue(totalTransferedAmount, decimals);
        }

        /**
         * Method to get {@link #totalServiceChargeAmount} instance <br>
         * No-any params required
         *
         * @return {@link #totalServiceChargeAmount} instance as double
         **/
        public double getTotalServiceChargeAmount() {
            return totalServiceChargeAmount;
        }

        /**
         * Method to get {@link #totalServiceChargeAmount} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #totalServiceChargeAmount} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getTotalServiceChargeAmount(int decimals) {
            return roundValue(totalServiceChargeAmount, decimals);
        }

        /**
         * Method to get {@link #transId} instance <br>
         * No-any params required
         *
         * @return {@link #transId} instance as long
         **/
        public long getTransId() {
            return transId;
        }

        /**
         * Method to get {@link #assetDribbletsDetailsList} instance <br>
         * No-any params required
         *
         * @return {@link #assetDribbletsDetailsList} instance as {@link ArrayList} of {@link DustItem}
         **/
        public ArrayList<DustItem> getAssetDribbletsDetails() {
            return assetDribbletsDetailsList;
        }

        /**
         * Method to add an asset dribblets details  to {@link #assetDribbletsDetailsList}
         *
         * @param assetDribbletsDetails: asset dribblets details to add
         **/
        public void insertAssetDribbletDetails(DustItem assetDribbletsDetails) {
            if (!assetDribbletsDetailsList.contains(assetDribbletsDetails))
                assetDribbletsDetailsList.add(assetDribbletsDetails);
        }

        /**
         * Method to remove an asset dribblets details  from {@link #assetDribbletsDetailsList}
         *
         * @param assetDribbletsDetails: asset dribblets details  to remove
         * @return result of operation as boolean
         **/
        public boolean removeAssetDribbletDetails(DustItem assetDribbletsDetails) {
            return assetDribbletsDetailsList.remove(assetDribbletsDetails);
        }

        /**
         * Method to get a asset dribblets details from {@link #assetDribbletsDetailsList} list
         *
         * @param index: index to fetch the composed asset dribblets details
         * @return asset dribblets details as {@link DustItem}
         **/
        public DustItem getAssetDribbletDetails(int index) {
            return assetDribbletsDetailsList.get(index);
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
