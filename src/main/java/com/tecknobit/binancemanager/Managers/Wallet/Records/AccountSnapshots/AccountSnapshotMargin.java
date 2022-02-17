package com.tecknobit.binancemanager.Managers.Wallet.Records.AccountSnapshots;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code AccountSnapshotMargin} class is useful to obtain and format AccountSnapshotMargin object
 * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
 * **/

public class AccountSnapshotMargin extends AccountSnapshot{

    private final int code;
    private final String msg;
    private final String type;
    private JSONArray jsonArray;
    private ArrayList<DataMargin> dataMargins;

    public AccountSnapshotMargin(int code, String msg, String type, JSONArray jsonArray) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
    }

    public AccountSnapshotMargin(int code, String msg, String type, JSONArray jsonArray, ArrayList<DataMargin> dataMargins) {
        super(code, msg, type, jsonArray);
        this.code = code;
        this.msg = msg;
        this.type = type;
        this.jsonArray = jsonArray;
        this.dataMargins = dataMargins;
    }

    /** Method to get getAccountSnapshotMargin object
     * any params required
     * return AccountSnapshotMargin object then to cast
     * **/
    public AccountSnapshotMargin getAccountSnapshotMargin() {
        dataMargins = new ArrayList<>();
        if(jsonArray != null) {
            for (int j=0; j < jsonArray.length(); j++){
                  JSONObject dataRow = jsonArray.getJSONObject(j);
                  long updateTime = dataRow.getLong("updateTime");
                  dataRow = dataRow.getJSONObject("data");
                  double marginLevel = dataRow.getDouble("marginLevel");;
                  double totalAssetOfBtc = dataRow.getDouble("totalAssetOfBtc");;
                  double totalLiabilityOfBtc = dataRow.getDouble("totalLiabilityOfBtc");;
                  double totalNetAssetOfBtc = dataRow.getDouble("totalNetAssetOfBtc");
                  dataMargins.add(new DataMargin(marginLevel,
                          totalAssetOfBtc,
                          totalLiabilityOfBtc,
                          totalNetAssetOfBtc,
                          updateTime,
                          getUserAssetsList(dataRow.getJSONArray("userAssets"))
                  ));
            }
        }
        return new AccountSnapshotMargin(code,msg,type,jsonArray,dataMargins);
    }

    /** Method to assemble an UserAssetMargin list
     * @param #jsonArray: jsonArray obtain by AccountSnapshot Binance request
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
     * return userAssetMargin list as ArrayList<UserAssetMargin>
     * **/
    private ArrayList<UserAssetMargin> getUserAssetsList(JSONArray jsonArray) {
        ArrayList<UserAssetMargin> userAssetMargins = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++){
            JSONObject userAsset = jsonArray.getJSONObject(j);
            userAssetMargins.add(new UserAssetMargin(userAsset.getString("asset"),
                    userAsset.getDouble("borrowed"),
                    userAsset.getDouble("free"),
                    userAsset.getDouble("interest"),
                    userAsset.getDouble("locked"),
                    userAsset.getDouble("netAsset")
            ));
        }
        return userAssetMargins;
    }

    public ArrayList<DataMargin> getDataMargins() {
        return dataMargins;
    }

    /**
     *  The {@code DataMargin} class is useful to obtain and format DataMargin object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
     * **/

    public static class DataMargin {

        private final double marginLevel;
        private final double totalAssetOfBtc;
        private final double totalLiabilityOfBtc;
        private final double totalNetAssetOfBtc;
        private final long updateTime;
        private final ArrayList<UserAssetMargin> userAssetMargins;

        public DataMargin(double marginLevel, double totalAssetOfBtc, double totalLiabilityOfBtc, double totalNetAssetOfBtc,
                          long updateTime, ArrayList<UserAssetMargin> userAssetMargins) {
            this.marginLevel = marginLevel;
            this.totalAssetOfBtc = totalAssetOfBtc;
            this.totalLiabilityOfBtc = totalLiabilityOfBtc;
            this.totalNetAssetOfBtc = totalNetAssetOfBtc;
            this.updateTime = updateTime;
            this.userAssetMargins = userAssetMargins;
        }

        public double getMarginLevel() {
            return marginLevel;
        }

        public double getTotalAssetOfBtc() {
            return totalAssetOfBtc;
        }

        public double getTotalLiabilityOfBtc() {
            return totalLiabilityOfBtc;
        }

        public double getTotalNetAssetOfBtc() {
            return totalNetAssetOfBtc;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public ArrayList<UserAssetMargin> getUserAssetMargins() {
            return userAssetMargins;
        }

    }

    /**
     *  The {@code UserAssetMargin} class is useful to obtain and format UserAssetMargin object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#daily-account-snapshot-user_data
     * **/

    public static class UserAssetMargin{

        private final String asset;
        private final double borrowed;
        private final double free;
        private final double interest;
        private final double locked;
        private final double netAsset;

        public UserAssetMargin(String asset, double borrowed, double free, double interest, double locked, double netAsset) {
            this.asset = asset;
            this.borrowed = borrowed;
            this.free = free;
            this.interest = interest;
            this.locked = locked;
            this.netAsset = netAsset;
        }

        public String getAsset() {
            return asset;
        }

        public double getBorrowed() {
            return borrowed;
        }

        public double getFree() {
            return free;
        }

        public double getInterest() {
            return interest;
        }

        public double getLocked() {
            return locked;
        }

        public double getNetAsset() {
            return netAsset;
        }

    }

}
