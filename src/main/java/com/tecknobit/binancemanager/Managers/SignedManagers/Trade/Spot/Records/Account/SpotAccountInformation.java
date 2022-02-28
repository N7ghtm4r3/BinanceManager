package com.tecknobit.binancemanager.Managers.SignedManagers.Trade.Spot.Records.Account;

import com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.AccountSnapshots.AccountSnapshotSpot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code SpotAccountInformation} class is useful to format SpotAccountInformation object
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
 *  @author N7ghtm4r3 - Tecknobit
 * **/

public class SpotAccountInformation {

    private final double makerCommission;
    private final double takerCommission;
    private final double buyerCommission;
    private final double sellerCommission;
    private final boolean canTrade;
    private final boolean canWithdraw;
    private final boolean canDeposit;
    private final long updateTime;
    private final String accountType;
    private final ArrayList<AccountSnapshotSpot.BalancesSpot> balancesSpots;
    private ArrayList<Permission> permissions;

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
        balancesSpots = AccountSnapshotSpot.getBalancesSpot(jsonObject.getJSONArray("balances"));
        loadPermissionList(jsonObject.getJSONArray("permissions"));
    }

    private void loadPermissionList(JSONArray jsonArray){
        permissions = new ArrayList<>();
        for (int j=0; j < jsonArray.length(); j++)
            permissions.add(new Permission(jsonArray.getString(j)));
    }

    public double getMakerCommission() {
        return makerCommission;
    }

    public double getTakerCommission() {
        return takerCommission;
    }

    public double getBuyerCommission() {
        return buyerCommission;
    }

    public double getSellerCommission() {
        return sellerCommission;
    }

    public boolean isCanTrade() {
        return canTrade;
    }

    public boolean isCanWithdraw() {
        return canWithdraw;
    }

    public boolean isCanDeposit() {
        return canDeposit;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public ArrayList<AccountSnapshotSpot.BalancesSpot> getBalancesSpots() {
        return balancesSpots;
    }

    public AccountSnapshotSpot.BalancesSpot getBalanceSpot(int index){
        return balancesSpots.get(index);
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public Permission getPermission(int index){
        return permissions.get(index);
    }

    /**
     * The {@code Permission} class is useful to obtain and format Permission object
     * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data
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
