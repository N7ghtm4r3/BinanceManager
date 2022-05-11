package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot.BalanceSpot;
import static com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot.getBalancesSpot;

/**
 *  The {@code SpotAccountInformation} class is useful to format SpotAccountInformation object
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotAccountInformation {

    private double makerCommission;
    private double takerCommission;
    private double buyerCommission;
    private double sellerCommission;
    private boolean canTrade;
    private boolean canWithdraw;
    private boolean canDeposit;
    private long updateTime;
    private final String accountType;
    private ArrayList<BalanceSpot> balanceSpotsList;
    private ArrayList<Permission> permissionsList;

    public SpotAccountInformation(double makerCommission, double takerCommission, double buyerCommission, double sellerCommission,
                                  boolean canTrade, boolean canWithdraw, boolean canDeposit, long updateTime, String accountType,
                                  JSONObject jsonObject) {
        this.makerCommission = makerCommission;
        this.takerCommission = takerCommission;
        this.buyerCommission = buyerCommission;
        this.sellerCommission = sellerCommission;
        this.canTrade = canTrade;
        this.canWithdraw = canWithdraw;
        this.canDeposit = canDeposit;
        this.updateTime = updateTime;
        this.accountType = accountType;
        balanceSpotsList = getBalancesSpot(jsonObject.getJSONArray("balances"));
        loadPermissionList(jsonObject.getJSONArray("permissionsList"));
    }

    private void loadPermissionList(JSONArray jsonArray){
        permissionsList = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            permissionsList.add(new Permission(jsonArray.getString(j)));
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    public void setMakerCommission(double makerCommission) {
        if(buyerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        this.makerCommission = makerCommission;
    }

    public double getTakerCommission() {
        return takerCommission;
    }

    public void setTakerCommission(double takerCommission) {
        if(buyerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        this.takerCommission = takerCommission;
    }

    public double getBuyerCommission() {
        return buyerCommission;
    }

    public void setBuyerCommission(double buyerCommission) {
        if(buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        this.buyerCommission = buyerCommission;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    public void setSellerCommission(double sellerCommission) {
        if(sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        this.sellerCommission = sellerCommission;
    }

    public boolean isCanTrade() {
        return canTrade;
    }

    public void setCanTrade(boolean canTrade) {
        this.canTrade = canTrade;
    }

    public boolean isCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public boolean isCanDeposit() {
        return canDeposit;
    }

    public void setCanDeposit(boolean canDeposit) {
        this.canDeposit = canDeposit;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        if(updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public ArrayList<AccountSnapshotSpot.BalanceSpot> getBalancesSpotsList() {
        return balanceSpotsList;
    }

    public void setBalancesSpotsList(ArrayList<AccountSnapshotSpot.BalanceSpot> balanceSpotsList) {
        this.balanceSpotsList = balanceSpotsList;
    }

    public void insertBalanceSpot(AccountSnapshotSpot.BalanceSpot balanceSpot){
        if(!balanceSpotsList.contains(balanceSpot))
            balanceSpotsList.add(balanceSpot);
    }

    public boolean removeBalanceSpot(AccountSnapshotSpot.BalanceSpot balanceSpot){
        return balanceSpotsList.remove(balanceSpot);
    }

    public AccountSnapshotSpot.BalanceSpot getBalanceSpot(int index){
        try{
            return balanceSpotsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
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
        try{
            return permissionsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * The {@code Permission} class is useful to obtain and format Permission object
     * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data</a>
     * **/

    public static class Permission {

        private final String permission;

        public Permission(String permission) {
            this.permission = permission;
        }

        public String getPermission() {
            return permission;
        }

    }

}
