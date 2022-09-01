package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.Tools.Trading.TradingTools.roundValue;

/**
 * The {@code FuturesAccountSnapshot} class is useful to obtain and format FuturesAccountSnapshot object
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data</a>
 **/

public class FuturesAccountSnapshot extends AccountSnapshot {

    /**
     * {@code futuresDataList} is instance that memorizes list of {@link FuturesData}
     **/
    private ArrayList<FuturesData> futuresDataList;

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param code:            code of response
     * @param msg:             message of response
     * @param type:            type of account
     * @param accountDetails:  details as {@link JSONObject}
     * @param futuresDataList: list of {@link FuturesData}
     **/
    public FuturesAccountSnapshot(int code, String msg, String type, JSONArray accountDetails,
                                  ArrayList<FuturesData> futuresDataList) {
        super(code, msg, type, accountDetails);
        this.futuresDataList = futuresDataList;
    }

    /**
     * Constructor to init {@link FuturesAccountSnapshot} object
     *
     * @param futuresAccount: futures account snapshot details as {@link JSONObject}
     **/
    public FuturesAccountSnapshot(JSONObject futuresAccount) {
        super(futuresAccount, FUTURES);
        futuresDataList = new ArrayList<>();
        for (int j = 0; j < snapshotVos.length(); j++)
            futuresDataList.add(new FuturesData(snapshotVos.getJSONObject(j)));
    }

    public ArrayList<FuturesData> getDataFuturesList() {
        return futuresDataList;
    }

    public void setDataFuturesList(ArrayList<FuturesData> futuresDataList) {
        this.futuresDataList = futuresDataList;
    }

    public void insertDataFuture(FuturesData futuresData) {
        if (!futuresDataList.contains(futuresData))
            futuresDataList.add(futuresData);
    }

    public boolean removeDataFuture(FuturesData futuresData) {
        return futuresDataList.remove(futuresData);
    }

    public FuturesData getDataFuture(int index) {
        return futuresDataList.get(index);
    }

    @Override
    public String toString() {
        return "FuturesAccountSnapshot{" +
                "futuresDataList=" + futuresDataList +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * The {@code FuturesData} class is useful to create a data futures object
     **/

    public static class FuturesData {

        /**
         * {@code updateTime} is instance that memorizes update time value
         **/
        private final long updateTime;

        /**
         * {@code futuresAssetList} is instance that memorizes list of {@link FuturesAsset}
         **/
        private ArrayList<FuturesAsset> futuresAssetList;

        /**
         * {@code futuresPositionList} is instance that memorizes list of {@link FuturesPosition}
         **/
        private ArrayList<FuturesPosition> futuresPositionList;

        /**
         * Constructor to init {@link FuturesData} object
         *
         * @param updateTime:      update time value
         * @param futureAssets:    list of {@link FuturesAsset}
         * @param futurePositions: list of {@link FuturesPosition}
         **/
        public FuturesData(long updateTime, ArrayList<FuturesAsset> futureAssets, ArrayList<FuturesPosition> futurePositions) {
            this.updateTime = updateTime;
            this.futuresAssetList = futureAssets;
            this.futuresPositionList = futurePositions;
        }

        /**
         * Constructor to init {@link FuturesData} object
         *
         * @param dataFutures: futures data details as {@link JSONObject}
         **/
        public FuturesData(JSONObject dataFutures) {
            updateTime = dataFutures.getLong("updateTime");
            futuresAssetList = getAssetsList(dataFutures);
            futuresPositionList = getPositionsList(dataFutures);
        }

        /**
         * Method to assemble an {@link FuturesAsset} list
         *
         * @param assets: list of assets obtain by AccountSnapshot Binance request
         * @return list as {@link ArrayList} of {@link FuturesAsset}
         **/
        private ArrayList<FuturesAsset> getAssetsList(JSONObject assets) {
            JSONArray assetsList = JsonHelper.getJSONArray(assets, "assets", new JSONArray());
            ArrayList<FuturesAsset> futureAssets = new ArrayList<>();
            for (int j = 0; j < assetsList.length(); j++)
                futureAssets.add(new FuturesAsset(assetsList.getJSONObject(j)));
            return futureAssets;
        }

        /**
         * Method to assemble a {@link FuturesPosition} list
         *
         * @param positions: list of positions obtain by AccountSnapshot Binance request
         * @return list as {@link ArrayList} of {@link FuturesPosition}
         **/
        private ArrayList<FuturesPosition> getPositionsList(JSONObject positions) {
            JSONArray positionsList = JsonHelper.getJSONArray(positions, "position", new JSONArray());
            ArrayList<FuturesPosition> futurePositions = new ArrayList<>();
            for (int j = 0; j < positionsList.length(); j++)
                futurePositions.add(new FuturesPosition(positionsList.getJSONObject(j)));
            return futurePositions;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public ArrayList<FuturesAsset> getAssetFuturesList() {
            return futuresAssetList;
        }

        public void setAssetFuturesList(ArrayList<FuturesAsset> futuresAssetList) {
            this.futuresAssetList = futuresAssetList;
        }

        public void insertAssetFuture(FuturesAsset futuresAsset) {
            if (!futuresAssetList.contains(futuresAsset))
                futuresAssetList.add(futuresAsset);
        }

        public boolean removeAssetFuture(FuturesAsset futuresAsset) {
            return futuresAssetList.remove(futuresAsset);
        }

        public FuturesAsset getAssetFutures(int index) {
            return futuresAssetList.get(index);
        }

        public ArrayList<FuturesPosition> getPositionFuturesList() {
            return futuresPositionList;
        }

        public void setPositionFuturesList(ArrayList<FuturesPosition> futuresPositionList) {
            this.futuresPositionList = futuresPositionList;
        }

        public void insertAssetFuture(FuturesPosition futuresPosition) {
            if (!futuresPositionList.contains(futuresPosition))
                futuresPositionList.add(futuresPosition);
        }

        public boolean removeAssetFuture(FuturesPosition futuresPosition) {
            return futuresPositionList.remove(futuresPosition);
        }

        public FuturesPosition getPositionFuturesList(int index) {
            return futuresPositionList.get(index);
        }

        @Override
        public String toString() {
            return "FuturesData{" +
                    "updateTime=" + updateTime +
                    ", futuresAssetList=" + futuresAssetList +
                    ", futuresPositionList=" + futuresPositionList +
                    '}';
        }

    }

    /**
     * The {@code FuturesAsset} class is useful to create a futures asset type
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
            asset = futuresAsset.getString("asset");
            marginBalance = futuresAsset.getDouble("marginBalance");
            if (marginBalance < 0)
                throw new IllegalArgumentException("Margin balance value cannot be less than 0");
            walletBalance = futuresAsset.getDouble("walletBalance");
            if (walletBalance < 0)
                throw new IllegalArgumentException("Wallet balance value cannot be less than 0");
        }

        public String getAsset() {
            return asset;
        }

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

        @Override
        public String toString() {
            return "FuturesAsset{" +
                    "asset='" + asset + '\'' +
                    ", marginBalance=" + marginBalance +
                    ", walletBalance=" + walletBalance +
                    '}';
        }

    }

    /**
     * The {@code FuturesPosition} class is useful to create a futures position object
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
        public FuturesPosition(double entryPrice, double markPrice, double positionAmt, String symbol, double unRealizedProfit) {
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

        public String getSymbol() {
            return symbol;
        }

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

        @Override
        public String toString() {
            return "FuturesPosition{" +
                    "entryPrice=" + entryPrice +
                    ", markPrice=" + markPrice +
                    ", positionAmt=" + positionAmt +
                    ", symbol='" + symbol + '\'' +
                    ", unRealizedProfit=" + unRealizedProfit +
                    '}';
        }

    }

}
