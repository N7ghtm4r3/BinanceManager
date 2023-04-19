package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.MarginOrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.AccountSnapshot.AccountType.futures;

/**
 * The {@code FuturesAccountSnapshot} class is useful to format a {@code "Binance"}'s futures account snapshot
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * Daily Account Snapshot (USER_DATA)</a>
 * @see AccountSnapshot
 **/
public class FuturesAccountSnapshot extends AccountSnapshot {

    /**
     * {@code futuresData} is instance that memorizes list of {@link FuturesData}
     **/
    private ArrayList<FuturesData> futuresData;

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param snapshotVos: details as {@link JSONArray}
     * @param futuresData: list of {@link FuturesData}
     **/
    public FuturesAccountSnapshot(int code, String msg, JSONArray snapshotVos, ArrayList<FuturesData> futuresData) {
        super(code, msg, futures, snapshotVos);
        this.futuresData = futuresData;
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     **/
    public FuturesAccountSnapshot(int code, String msg, String snapshotVos) {
        super(code, msg, futures, snapshotVos);
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param code:        code of response
     * @param msg:         message of response
     * @param snapshotVos: details as {@link String}
     * @apiNote this constructor is useful to pass a {@code "JSON"} array in {@link String} format
     **/
    public <T> FuturesAccountSnapshot(int code, String msg, T snapshotVos) {
        super(code, msg, futures, snapshotVos);
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param futuresAccount: futures account snapshot details as {@link JSONObject}
     **/
    public FuturesAccountSnapshot(JSONObject futuresAccount) {
        super(futuresAccount);
        futuresData = new ArrayList<>();
        for (int j = 0; j < snapshotVos.length(); j++)
            futuresData.add(new FuturesData(snapshotVos.getJSONObject(j)));
    }

    /**
     * Method to get {@link #futuresData} instance <br>
     * No-any params required
     *
     * @return {@link #futuresData} instance as {@link ArrayList} of {@link MarginOrderDetails}
     **/
    public ArrayList<FuturesData> getDataFutures() {
        return futuresData;
    }

    /**
     * Method to set {@link #futuresData} instance <br>
     *
     * @param futuresData: list of {@link FuturesData} to set
     **/
    public void setDataFuturesList(ArrayList<FuturesData> futuresData) {
        this.futuresData = futuresData;
    }

    /**
     * Method to add a futures data  to {@link #futuresData}
     *
     * @param futuresData: futures data to add
     **/
    public void insertDataFuture(FuturesData futuresData) {
        if (!this.futuresData.contains(futuresData))
            this.futuresData.add(futuresData);
    }

    /**
     * Method to remove a futures data  from {@link #futuresData}
     *
     * @param futuresData: futures data  to remove
     * @return result of operation as boolean
     **/
    public boolean removeDataFuture(FuturesData futuresData) {
        return this.futuresData.remove(futuresData);
    }

    /**
     * Method to get a futures data from {@link #futuresData} list
     *
     * @param index: index to fetch the futures data
     * @return futures data as {@link FuturesData}
     **/
    public FuturesData getDataFuture(int index) {
        return futuresData.get(index);
    }

    /**
     * The {@code FuturesData} class is useful to create a data futures object
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class FuturesData {

        /**
         * {@code updateTime} is instance that memorizes update time value
         **/
        private final long updateTime;

        /**
         * {@code futuresAssets} is instance that memorizes list of {@link FuturesAsset}
         **/
        private ArrayList<FuturesAsset> futuresAssets;

        /**
         * {@code futuresPositions} is instance that memorizes list of {@link FuturesPosition}
         **/
        private ArrayList<FuturesPosition> futuresPositions;

        /**
         * Constructor to init {@link FuturesData} object
         *
         * @param updateTime:      update time value
         * @param futureAssets:    list of {@link FuturesAsset}
         * @param futurePositions: list of {@link FuturesPosition}
         **/
        public FuturesData(long updateTime, ArrayList<FuturesAsset> futureAssets, ArrayList<FuturesPosition> futurePositions) {
            this.updateTime = updateTime;
            this.futuresAssets = futureAssets;
            this.futuresPositions = futurePositions;
        }

        /**
         * Constructor to init {@link FuturesData} object
         *
         * @param dataFutures: futures data details as {@link JSONObject}
         **/
        public FuturesData(JSONObject dataFutures) {
            JsonHelper hFutures = new JsonHelper(dataFutures);
            updateTime = dataFutures.getLong("updateTime");
            futuresAssets = new ArrayList<>();
            JSONArray jAssets = hFutures.getJSONArray("assets", new JSONArray());
            for (int j = 0; j < jAssets.length(); j++)
                futuresAssets.add(new FuturesAsset(jAssets.getJSONObject(j)));
            futuresPositions = new ArrayList<>();
            JSONArray jPositions = hFutures.getJSONArray("position", new JSONArray());
            for (int j = 0; j < jPositions.length(); j++)
                futuresPositions.add(new FuturesPosition(jPositions.getJSONObject(j)));
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
         * Method to get {@link #futuresAssets} instance <br>
         * No-any params required
         *
         * @return {@link #futuresAssets} instance as {@link ArrayList} of {@link FuturesAsset}
         **/
        public ArrayList<FuturesAsset> getAssetFuturesList() {
            return futuresAssets;
        }

        /**
         * Method to set {@link #futuresData} instance <br>
         *
         * @param futuresAssets: list of {@link FuturesAsset} to set
         **/
        public void setAssetFuturesList(ArrayList<FuturesAsset> futuresAssets) {
            this.futuresAssets = futuresAssets;
        }

        /**
         * Method to add a futures asset  to {@link #futuresAssets}
         *
         * @param futuresAsset: futures asset to add
         **/
        public void insertAssetFuture(FuturesAsset futuresAsset) {
            if (!futuresAssets.contains(futuresAsset))
                futuresAssets.add(futuresAsset);
        }

        /**
         * Method to remove a futures asset  from {@link #futuresAssets}
         *
         * @param futuresAsset: futures asset  to remove
         * @return result of operation as boolean
         **/
        public boolean removeAssetFuture(FuturesAsset futuresAsset) {
            return futuresAssets.remove(futuresAsset);
        }

        /**
         * Method to get a futures asset from {@link #futuresAssets} list
         *
         * @param index: index to fetch the futures asset
         * @return futures asset as {@link FuturesAsset}
         **/
        public FuturesAsset getAssetFutures(int index) {
            return futuresAssets.get(index);
        }

        /**
         * Method to get {@link #futuresPositions} instance <br>
         * No-any params required
         *
         * @return {@link #futuresData} instance as {@link ArrayList} of {@link MarginOrderDetails}
         **/
        public ArrayList<FuturesPosition> getPositionFuturesList() {
            return futuresPositions;
        }

        /**
         * Method to set {@link #futuresPositions} instance <br>
         *
         * @param futuresPositions: list of {@link FuturesPosition} to set
         **/
        public void setPositionFuturesList(ArrayList<FuturesPosition> futuresPositions) {
            this.futuresPositions = futuresPositions;
        }

        /**
         * Method to add a futures position  to {@link #futuresData}
         *
         * @param futuresPosition: futures position to add
         **/
        public void insertAssetFuture(FuturesPosition futuresPosition) {
            if (!futuresPositions.contains(futuresPosition))
                futuresPositions.add(futuresPosition);
        }

        /**
         * Method to remove a futures position  from {@link #futuresData}
         *
         * @param futuresPosition: futures position  to remove
         * @return result of operation as boolean
         **/
        public boolean removeAssetFuture(FuturesPosition futuresPosition) {
            return futuresPositions.remove(futuresPosition);
        }

        /**
         * Method to get a futures position from {@link #futuresPositions} list
         *
         * @param index: index to fetch the futures position
         * @return position as {@link FuturesPosition}
         **/
        public FuturesPosition getPositionFutures(int index) {
            return futuresPositions.get(index);
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
     * The {@code FuturesAsset} class is useful to create a futures asset
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class FuturesAsset {

        /**
         * {@code asset} is instance that memorizes asset value
         **/
        private final String asset;

        /**
         * {@code marginBalance} is instance that memorizes margin balance value
         **/
        private double marginBalance;

        /**
         * {@code walletBalance} is instance that memorizes wallet balance value
         **/
        private double walletBalance;

        /**
         * Constructor to init {@link FuturesAsset} object
         *
         * @param asset:         update time value
         * @param marginBalance: list of {@link FuturesAsset}
         * @param walletBalance: list of {@link FuturesPosition}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public FuturesAsset(String asset, double marginBalance, double walletBalance) {
            this.asset = asset;
            if (marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            else
                this.marginBalance = marginBalance;
            if (walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
            else
                this.walletBalance = walletBalance;
        }

        /**
         * Constructor to init {@link FuturesAsset} object
         *
         * @param futuresAsset: futures asset details as {@link JSONObject}
         * @throws IllegalArgumentException if parameters range is not respected
         **/
        public FuturesAsset(JSONObject futuresAsset) {
            this(futuresAsset.getString("asset"), futuresAsset.getDouble("marginBalance"),
                    futuresAsset.getDouble("walletBalance"));
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
         * Method to get {@link #marginBalance} instance <br>
         * No-any params required
         *
         * @return {@link #marginBalance} instance as double
         **/
        public double getMarginBalance() {
            return marginBalance;
        }

        /**
         * Method to set {@link #marginBalance}
         *
         * @param marginBalance: margin balance value
         * @throws IllegalArgumentException when margin balance value is less than 0
         **/
        public void setMarginBalance(double marginBalance) {
            if (marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            this.marginBalance = marginBalance;
        }

        /**
         * Method to get {@link #marginBalance} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #marginBalance} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMarginBalance(int decimals) {
            return roundValue(marginBalance, decimals);
        }

        /**
         * Method to get {@link #walletBalance} instance <br>
         * No-any params required
         *
         * @return {@link #walletBalance} instance as double
         **/
        public double getWalletBalance() {
            return walletBalance;
        }

        /**
         * Method to set {@link #walletBalance}
         *
         * @param walletBalance: wallet balance value
         * @throws IllegalArgumentException when wallet balance value is less than 0
         **/
        public void setWalletBalance(double walletBalance) {
            if (walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
            this.walletBalance = walletBalance;
        }

        /**
         * Method to get {@link #walletBalance} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #walletBalance} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getWalletBalance(int decimals) {
            return roundValue(walletBalance, decimals);
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
     * The {@code FuturesPosition} class is useful to create a futures position
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class FuturesPosition {

        /**
         * {@code symbol} is instance that memorizes symbol value
         **/
        private final String symbol;

        /**
         * {@code entryPrice} is instance that memorizes entry price value
         **/
        private double entryPrice;

        /**
         * {@code markPrice} is instance that memorizes mark price value
         **/
        private double markPrice;

        /**
         * {@code positionAmt} is instance that memorizes position amt value
         **/
        private double positionAmt;

        /**
         * {@code unRealizedProfit} is instance that unrealize profit value
         **/
        private double unRealizedProfit;

        /**
         * Constructor to init {@link FuturesPosition} object
         *
         * @param entryPrice:       entry price value
         * @param markPrice:        mark price value
         * @param positionAmt:      position amt value
         * @param symbol:           symbol value
         * @param unRealizedProfit: unrealize profit value
         **/
        public FuturesPosition(double entryPrice, double markPrice, double positionAmt, String symbol,
                               double unRealizedProfit) {
            this.entryPrice = entryPrice;
            this.markPrice = markPrice;
            this.positionAmt = positionAmt;
            this.symbol = symbol;
            this.unRealizedProfit = unRealizedProfit;
        }

        /**
         * Constructor to init {@link FuturesPosition} object
         *
         * @param futuresPosition: futures position details as {@link JSONObject}
         **/
        public FuturesPosition(JSONObject futuresPosition) {
            entryPrice = futuresPosition.getDouble("entryPrice");
            markPrice = futuresPosition.getDouble("markPrice");
            positionAmt = futuresPosition.getDouble("positionAmt");
            symbol = futuresPosition.getString("symbol");
            unRealizedProfit = futuresPosition.getDouble("unRealizedProfit");
        }

        /**
         * Method to get {@link #entryPrice} instance <br>
         * No-any params required
         *
         * @return {@link #entryPrice} instance as double
         **/
        public double getEntryPrice() {
            return entryPrice;
        }

        /**
         * Method to set {@link #entryPrice}
         *
         * @param entryPrice: entry price value
         * @throws IllegalArgumentException when entry price value is less than 0
         **/
        public void setEntryPrice(double entryPrice) {
            if (entryPrice < 0)
                throw new IllegalArgumentException("Entry price value cannot be less than 0");
            this.entryPrice = entryPrice;
        }

        /**
         * Method to get {@link #entryPrice} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #entryPrice} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getEntryPrice(int decimals) {
            return roundValue(entryPrice, decimals);
        }

        /**
         * Method to get {@link #markPrice} instance <br>
         * No-any params required
         *
         * @return {@link #markPrice} instance as double
         **/
        public double getMarkPrice() {
            return markPrice;
        }

        /**
         * Method to set {@link #markPrice}
         *
         * @param markPrice: mark price value
         * @throws IllegalArgumentException when mark price value is less than 0
         **/
        public void setMarkPrice(double markPrice) {
            if (markPrice < 0)
                throw new IllegalArgumentException("Mark price value cannot be less than 0");
            this.markPrice = markPrice;
        }

        /**
         * Method to get {@link #markPrice} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #markPrice} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getMarkPrice(int decimals) {
            return roundValue(markPrice, decimals);
        }

        /**
         * Method to get {@link #positionAmt} instance <br>
         * No-any params required
         *
         * @return {@link #positionAmt} instance as double
         **/
        public double getPositionAmt() {
            return positionAmt;
        }

        /**
         * Method to set {@link #positionAmt}
         *
         * @param positionAmt: unrealize profit value
         * @throws IllegalArgumentException when unrealize profit value is less than 0
         **/
        public void setPositionAmt(double positionAmt) {
            if (positionAmt < 0)
                throw new IllegalArgumentException("Position amt value cannot be less than 0");
            this.positionAmt = positionAmt;
        }

        /**
         * Method to get {@link #positionAmt} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #positionAmt} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getPositionAmt(int decimals) {
            return roundValue(positionAmt, decimals);
        }

        /**
         * Method to get {@link #symbol} instance <br>
         * No-any params required
         *
         * @return {@link #symbol} instance as {@link String}
         **/
        public String getSymbol() {
            return symbol;
        }

        /**
         * Method to get {@link #unRealizedProfit} instance <br>
         * No-any params required
         *
         * @return {@link #unRealizedProfit} instance as double
         **/
        public double getUnRealizedProfit() {
            return unRealizedProfit;
        }

        /**
         * Method to set {@link #unRealizedProfit}
         *
         * @param unRealizedProfit: position amt value
         * @throws IllegalArgumentException when mark position amt value is less than 0
         **/
        public void setUnRealizedProfit(double unRealizedProfit) {
            if (unRealizedProfit < 0)
                throw new IllegalArgumentException("Unrealize profit value cannot be less than 0");
            this.unRealizedProfit = unRealizedProfit;
        }

        /**
         * Method to get {@link #unRealizedProfit} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #unRealizedProfit} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getUnRealizedProfit(int decimals) {
            return roundValue(unRealizedProfit, decimals);
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
