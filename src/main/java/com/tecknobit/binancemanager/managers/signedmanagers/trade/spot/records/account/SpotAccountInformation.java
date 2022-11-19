package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.SpotBalance;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.getBalancesSpot;

/**
 *  The {@code SpotAccountInformation} class is useful to format SpotAccountInformation object
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotAccountInformation {

    /**
     * {@code makerCommission} is instance that memorizes maker commission
     * **/
    private double makerCommission;

    /**
     * {@code takerCommission} is instance that memorizes taker commission
     * **/
    private double takerCommission;

    /**
     * {@code buyerCommission} is instance that memorizes buyer commission
     * **/
    private double buyerCommission;

    /**
     * {@code sellerCommission} is instance that memorizes seller commission
     * **/
    private double sellerCommission;

    /**
     * {@code canTrade} is instance that memorizes if account can trade
     * **/
    private boolean canTrade;

    /**
     * {@code canWithdraw} is instance that memorizes if account can withdraw
     * **/
    private boolean canWithdraw;

    /**
     * {@code canDeposit} is instance that memorizes if account can deposit
     * **/
    private boolean canDeposit;

    /**
     * {@code brokered} is flag that memorizes if account is brokered
     * **/
    private boolean brokered;

    /**
     * {@code updateTime} is instance that memorizes update time value
     * **/
    private long updateTime;

    /**
     * {@code accountType} is instance that memorizes account type value
     * **/
    private final String accountType;

    /**
     * {@code spotsListBalance} is instance that memorizes balance spot list
     **/
    private ArrayList<SpotAccountSnapshot.SpotBalance> spotsListBalance;

    /**
     * {@code permissionsList} is instance that memorizes permissions list
     * **/
    private ArrayList<Permission> permissionsList;

    /** Constructor to init {@link SpotAccountInformation} object
     * @param makerCommission: maker commission
     * @param takerCommission: taker commission
     * @param buyerCommission: buyer commission
     * @param sellerCommission: seller commission
     * @param canTrade: can trade
     * @param canWithdraw: can withdraw
     * @param canDeposit: can deposit
     * @param brokered: is brokered
     * @param updateTime: update time value
     * @param accountType: account type value
     * @param spotsListBalance: balance spot list
     * @param permissionsList: permissions list
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public SpotAccountInformation(double makerCommission, double takerCommission, double buyerCommission, double sellerCommission,
                                  boolean canTrade, boolean canWithdraw, boolean canDeposit, boolean brokered,
                                  long updateTime, String accountType, ArrayList<SpotBalance> spotsListBalance,
                                  ArrayList<Permission> permissionsList) {
        if (makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        else
            this.makerCommission = makerCommission;
        if (takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        else
            this.takerCommission = takerCommission;
        if (buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        else
            this.buyerCommission = buyerCommission;
        if (sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        else
            this.sellerCommission = sellerCommission;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.canDeposit = canDeposit;
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        else
            this.updateTime = updateTime;
        this.accountType = accountType;
        this.spotsListBalance = spotsListBalance;
        this.permissionsList = permissionsList;
    }

    /**
     * Constructor to init {@link SpotAccountInformation} object
     *
     * @param spotAccountInformation: spot account details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public SpotAccountInformation(JSONObject spotAccountInformation) {
        makerCommission = spotAccountInformation.getDouble("makerCommission");
        if (makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        takerCommission = spotAccountInformation.getDouble("takerCommission");
        if (takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        buyerCommission = spotAccountInformation.getDouble("buyerCommission");
        if (buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        sellerCommission = spotAccountInformation.getDouble("sellerCommission");
        if (sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        canTrade = spotAccountInformation.getBoolean("canTrade");
        canWithdraw = spotAccountInformation.getBoolean("canWithdraw");
        canDeposit = spotAccountInformation.getBoolean("canDeposit");
        brokered = spotAccountInformation.getBoolean("brokered");
        updateTime = spotAccountInformation.getLong("updateTime");
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        accountType = spotAccountInformation.getString("accountType");
        JsonHelper hSpotAccount = new JsonHelper(spotAccountInformation);
        spotsListBalance = getBalancesSpot(hSpotAccount.getJSONArray("balances", new JSONArray()));
        loadPermissionList(hSpotAccount.getJSONArray("permissionsList", new JSONArray()));
    }

    /**
     * Method to load Permissions list
     *
     * @param jsonPermissions: obtained from Binance's request
     *                         any return
     **/
    private void loadPermissionList(JSONArray jsonPermissions) {
        permissionsList = new ArrayList<>();
        if (jsonPermissions != null)
            for (int j = 0; j < jsonPermissions.length(); j++)
                permissionsList.add(new Permission(jsonPermissions.getString(j)));
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    /**
     * Method to get {@link #makerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #makerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getMakerCommission(int decimals) {
        return roundValue(makerCommission, decimals);
    }

    /**
     * Method to set {@link #makerCommission}
     *
     * @param makerCommission: maker commission
     * @throws IllegalArgumentException when maker commission value is less than 0
     **/
    public void setMakerCommission(double makerCommission) {
        if (makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        this.makerCommission = makerCommission;
    }

    public double getTakerCommission() {
        return takerCommission;
    }

    /**
     * Method to get {@link #takerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getTakerCommission(int decimals) {
        return roundValue(takerCommission, decimals);
    }

    /**
     * Method to set {@link #takerCommission}
     *
     * @param takerCommission: taker commission
     * @throws IllegalArgumentException when taker commission value is less than 0
     **/
    public void setTakerCommission(double takerCommission) {
        if (takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        this.takerCommission = takerCommission;
    }

    public double getBuyerCommission() {
        return buyerCommission;
    }

    /**
     * Method to get {@link #buyerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #buyerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getBuyerCommission(int decimals) {
        return roundValue(buyerCommission, decimals);
    }

    /**
     * Method to set {@link #buyerCommission}
     *
     * @param buyerCommission: buyer commission
     * @throws IllegalArgumentException when buyer commission value is less than 0
     **/
    public void setBuyerCommission(double buyerCommission) {
        if (buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        this.buyerCommission = buyerCommission;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    /**
     * Method to get {@link #sellerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #sellerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getSellerCommission(int decimals) {
        return roundValue(sellerCommission, decimals);
    }

    /**
     * Method to set {@link #sellerCommission}
     *
     * @param sellerCommission: seller commission
     * @throws IllegalArgumentException when seller commission value is less than 0
     **/
    public void setSellerCommission(double sellerCommission) {
        if (sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        this.sellerCommission = sellerCommission;
    }

    public boolean canTrade() {
        return canTrade;
    }

    public void setCanTrade(boolean canTrade) {
        this.canTrade = canTrade;
    }

    public boolean canWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public boolean canDeposit() {
        return canDeposit;
    }

    public void setCanDeposit(boolean canDeposit) {
        this.canDeposit = canDeposit;
    }

    public boolean isBrokered() {
        return brokered;
    }

    public void setBrokered(boolean brokered) {
        this.brokered = brokered;
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

    public String getAccountType() {
        return accountType;
    }

    public ArrayList<SpotAccountSnapshot.SpotBalance> getBalancesSpotsList() {
        return spotsListBalance;
    }

    public void setBalancesSpotsList(ArrayList<SpotAccountSnapshot.SpotBalance> spotsListBalance) {
        this.spotsListBalance = spotsListBalance;
    }

    public void insertBalanceSpot(SpotAccountSnapshot.SpotBalance spotBalance) {
        if (!spotsListBalance.contains(spotBalance))
            spotsListBalance.add(spotBalance);
    }

    public boolean removeBalanceSpot(SpotAccountSnapshot.SpotBalance spotBalance) {
        return spotsListBalance.remove(spotBalance);
    }

    public SpotAccountSnapshot.SpotBalance getBalanceSpot(int index) {
        return spotsListBalance.get(index);
    }

    public ArrayList<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(ArrayList<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public void insertBalanceSpot(Permission permission){
        if(!permissionsList.contains(permission))
            permissionsList.add(permission);
    }

    public boolean removeBalanceSpot(Permission permission){
        return permissionsList.remove(permission);
    }

    public Permission getPermission(int index){
        return permissionsList.get(index);
    }

    @Override
    public String toString() {
        return "SpotAccountInformation{" +
                "makerCommission=" + makerCommission +
                ", takerCommission=" + takerCommission +
                ", buyerCommission=" + buyerCommission +
                ", sellerCommission=" + sellerCommission +
                ", canTrade=" + canTrade +
                ", canWithdraw=" + canWithdraw +
                ", canDeposit=" + canDeposit +
                ", updateTime=" + updateTime +
                ", accountType='" + accountType + '\'' +
                ", spotsListBalance=" + spotsListBalance +
                ", permissionsList=" + permissionsList +
                '}';
    }

    /**
     * The {@code Permission} class is useful to obtain and format Permission object
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     *     https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * **/

    public static class Permission {

        /**
         * {@code permission} is instance that memorizes permission value
         * **/
        private final String permission;

        /** Constructor to init {@link Permission} object
         * @param permission: permission value
         * **/
        public Permission(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }

        @Override
        public String toString() {
            return "Permission{" +
                    "permission='" + permission + '\'' +
                    '}';
        }

    }

}
