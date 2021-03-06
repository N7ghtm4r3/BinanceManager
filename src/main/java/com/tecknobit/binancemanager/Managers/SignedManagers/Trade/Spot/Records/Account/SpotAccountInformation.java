package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

import com.tecknobit.apimanager.Tools.Formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot.BalanceSpot;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot.getBalancesSpot;

/**
 *  The {@code SpotAccountInformation} class is useful to format SpotAccountInformation object
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
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
     * {@code updateTime} is instance that memorizes update time value
     * **/
    private long updateTime;

    /**
     * {@code accountType} is instance that memorizes account type value
     * **/
    private final String accountType;

    /**
     * {@code balanceSpotsList} is instance that memorizes balance spot list
     * **/
    private ArrayList<BalanceSpot> balanceSpotsList;

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
     * @param updateTime: update time value
     * @param accountType: account type value
     * @param jsonAccount: account details information in JSON format
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public SpotAccountInformation(double makerCommission, double takerCommission, double buyerCommission, double sellerCommission,
                                  boolean canTrade, boolean canWithdraw, boolean canDeposit, long updateTime, String accountType,
                                  JSONObject jsonAccount) {
        if(makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        else
            this.makerCommission = makerCommission;
        if(takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        else
            this.takerCommission = takerCommission;
        if(buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        else
            this.buyerCommission = buyerCommission;
        if(sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        else
            this.sellerCommission = sellerCommission;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.canDeposit = canDeposit;
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        else
            this.updateTime = updateTime;
        this.accountType = accountType;
        JsonHelper jsonHelper = new JsonHelper(jsonAccount);
        balanceSpotsList = getBalancesSpot(jsonHelper.getJSONArray("balances"));
        loadPermissionList(jsonHelper.getJSONArray("permissionsList"));
    }

    /** Method to load Permissions list
     * @param jsonPermissions: obtained from Binance's request
     * any return
     * **/
    private void loadPermissionList(JSONArray jsonPermissions){
        permissionsList = new ArrayList<>();
        if(jsonPermissions != null)
            for (int j = 0; j < jsonPermissions.length(); j++)
                permissionsList.add(new Permission(jsonPermissions.getString(j)));
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    /** Method to set {@link #makerCommission}
     * @param makerCommission: maker commission
     * @throws IllegalArgumentException when maker commission value is less than 0
     * **/
    public void setMakerCommission(double makerCommission) {
        if(makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        this.makerCommission = makerCommission;
    }

    public double getTakerCommission() {
        return takerCommission;
    }

    /** Method to set {@link #takerCommission}
     * @param takerCommission: taker commission
     * @throws IllegalArgumentException when taker commission value is less than 0
     * **/
    public void setTakerCommission(double takerCommission) {
        if(takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        this.takerCommission = takerCommission;
    }

    public double getBuyerCommission() {
        return buyerCommission;
    }

    /** Method to set {@link #buyerCommission}
     * @param buyerCommission: buyer commission
     * @throws IllegalArgumentException when buyer commission value is less than 0
     * **/
    public void setBuyerCommission(double buyerCommission) {
        if(buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        this.buyerCommission = buyerCommission;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    /** Method to set {@link #sellerCommission}
     * @param sellerCommission: seller commission
     * @throws IllegalArgumentException when seller commission value is less than 0
     * **/
    public void setSellerCommission(double sellerCommission) {
        if(sellerCommission < 0)
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

    public long getUpdateTime() {
        return updateTime;
    }

    /** Method to set {@link #updateTime}
     * @param updateTime: update time value
     * @throws IllegalArgumentException when update time value is less than 0
     * **/
    public void setUpdateTime(long updateTime) {
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public ArrayList<BalanceSpot> getBalancesSpotsList() {
        return balanceSpotsList;
    }

    public void setBalancesSpotsList(ArrayList<BalanceSpot> balanceSpotsList) {
        this.balanceSpotsList = balanceSpotsList;
    }

    public void insertBalanceSpot(BalanceSpot balanceSpot){
        if(!balanceSpotsList.contains(balanceSpot))
            balanceSpotsList.add(balanceSpot);
    }

    public boolean removeBalanceSpot(BalanceSpot balanceSpot){
        return balanceSpotsList.remove(balanceSpot);
    }

    public BalanceSpot getBalanceSpot(int index){
        return balanceSpotsList.get(index);
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

    /**
     * The {@code Permission} class is useful to obtain and format Permission object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
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

    }

}
