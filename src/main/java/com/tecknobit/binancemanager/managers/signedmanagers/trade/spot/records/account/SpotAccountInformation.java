package com.tecknobit.binancemanager.managers.signedmanagers.trade.spot.records.account;

import com.tecknobit.apimanager.formatters.JsonHelper;
import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.signedmanagers.trade.margin.records.orders.details.MarginOrderDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.SpotBalance;
import static com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.accountsnapshots.SpotAccountSnapshot.getBalancesSpot;

/**
 * The {@code SpotAccountInformation} class is useful to format a {@code "Binance"}'s spot account information
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
 * Account Information (USER_DATA)</a>
 */
public class SpotAccountInformation {

    /**
     * {@code makerCommission} is instance that memorizes maker commission
     */
    private double makerCommission;

    /**
     * {@code takerCommission} is instance that memorizes taker commission
     */
    private double takerCommission;

    /**
     * {@code buyerCommission} is instance that memorizes buyer commission
     */
    private double buyerCommission;

    /**
     * {@code sellerCommission} is instance that memorizes seller commission
     */
    private double sellerCommission;

    /**
     * {@code commissionRates} is instance that memorizes commission rates for the account
     */
    private CommissionRates commissionRates;

    /**
     * {@code canTrade} is instance that memorizes if account can trade
     */
    private boolean canTrade;

    /**
     * {@code canWithdraw} is instance that memorizes if account can withdraw
     */
    private boolean canWithdraw;

    /**
     * {@code canDeposit} is instance that memorizes if account can deposit
     */
    private boolean canDeposit;

    /**
     * {@code brokered} is flag that memorizes if account is brokered
     */
    private boolean brokered;

    /**
     * {@code updateTime} is instance that memorizes update time value
     */
    private long updateTime;

    /**
     * {@code accountType} is instance that memorizes account type value
     */
    private final String accountType;

    /**
     * {@code spotsListBalance} is instance that memorizes balance spot list
     */
    private ArrayList<SpotBalance> spotsListBalance;

    /**
     * {@code permissionsList} is instance that memorizes permissions list
     */
    private ArrayList<Permission> permissionsList;

    /**
     * {@code requireSelfTradePrevention} whether the spot account require self trade prevention
     */
    private boolean requireSelfTradePrevention;

    /**
     * Constructor to init {@link SpotAccountInformation} object
     *
     * @param makerCommission:            maker commission
     * @param takerCommission:            taker commission
     * @param buyerCommission:            buyer commission
     * @param sellerCommission:           seller commission
     * @param canTrade:                   can trade
     * @param canWithdraw:                can withdraw
     * @param canDeposit:                 can deposit
     * @param brokered:                   is brokered
     * @param updateTime:                 update time value
     * @param accountType:                account type value
     * @param spotsListBalance:           balance spot list
     * @param permissionsList:            permissions list
     * @param requireSelfTradePrevention: whether the spot account require self trade prevention
     * @throws IllegalArgumentException if parameters range is not respected
     */
    public SpotAccountInformation(double makerCommission, double takerCommission, double buyerCommission,
                                  double sellerCommission, CommissionRates commissionRates, boolean canTrade,
                                  boolean canWithdraw, boolean canDeposit, boolean brokered, long updateTime,
                                  String accountType, ArrayList<SpotBalance> spotsListBalance,
                                  ArrayList<Permission> permissionsList, boolean requireSelfTradePrevention) {
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
        this.commissionRates = commissionRates;
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
     */
    public SpotAccountInformation(JSONObject spotAccountInformation) {
        JsonHelper hSpotAccount = new JsonHelper(spotAccountInformation);
        makerCommission = hSpotAccount.getDouble("makerCommission", 0);
        if (makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        takerCommission = hSpotAccount.getDouble("takerCommission", 0);
        if (takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        buyerCommission = hSpotAccount.getDouble("buyerCommission", 0);
        if (buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        sellerCommission = hSpotAccount.getDouble("sellerCommission", 0);
        if (sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        commissionRates = new CommissionRates(hSpotAccount.getJSONObject("commissionRates", new JSONObject()));
        canTrade = hSpotAccount.getBoolean("canTrade");
        canWithdraw = hSpotAccount.getBoolean("canWithdraw");
        canDeposit = hSpotAccount.getBoolean("canDeposit");
        brokered = hSpotAccount.getBoolean("brokered");
        updateTime = hSpotAccount.getLong("updateTime", 0);
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        accountType = hSpotAccount.getString("accountType");
        spotsListBalance = getBalancesSpot(hSpotAccount.getJSONArray("balances", new JSONArray()));
        permissionsList = new ArrayList<>();
        JSONArray jPermissions = hSpotAccount.getJSONArray("permissionsList", new JSONArray());
        for (int j = 0; j < jPermissions.length(); j++)
            permissionsList.add(new Permission(jPermissions.getString(j)));
        requireSelfTradePrevention = hSpotAccount.getBoolean("requireSelfTradePrevention");
    }

    /**
     * Method to get {@link #makerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #makerCommission} instance as double
     */
    public double getMakerCommission() {
        return makerCommission;
    }

    /**
     * Method to get {@link #makerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #makerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getMakerCommission(int decimals) {
        return roundValue(makerCommission, decimals);
    }

    /**
     * Method to set {@link #makerCommission}
     *
     * @param makerCommission: maker commission
     * @throws IllegalArgumentException when maker commission value is less than 0
     */
    public void setMakerCommission(double makerCommission) {
        if (makerCommission < 0)
            throw new IllegalArgumentException("Maker commission value cannot be less than 0");
        this.makerCommission = makerCommission;
    }

    /**
     * Method to get {@link #takerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #takerCommission} instance as double
     */
    public double getTakerCommission() {
        return takerCommission;
    }

    /**
     * Method to get {@link #takerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #takerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getTakerCommission(int decimals) {
        return roundValue(takerCommission, decimals);
    }

    /**
     * Method to set {@link #takerCommission}
     *
     * @param takerCommission: taker commission
     * @throws IllegalArgumentException when taker commission value is less than 0
     */
    public void setTakerCommission(double takerCommission) {
        if (takerCommission < 0)
            throw new IllegalArgumentException("Taker commission value cannot be less than 0");
        this.takerCommission = takerCommission;
    }

    /**
     * Method to get {@link #buyerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #buyerCommission} instance as double
     */
    public double getBuyerCommission() {
        return buyerCommission;
    }

    /**
     * Method to get {@link #buyerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #buyerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getBuyerCommission(int decimals) {
        return roundValue(buyerCommission, decimals);
    }

    /**
     * Method to set {@link #buyerCommission}
     *
     * @param buyerCommission: buyer commission
     * @throws IllegalArgumentException when buyer commission value is less than 0
     */
    public void setBuyerCommission(double buyerCommission) {
        if (buyerCommission < 0)
            throw new IllegalArgumentException("Buyer commission value cannot be less than 0");
        this.buyerCommission = buyerCommission;
    }

    /**
     * Method to get {@link #sellerCommission} instance <br>
     * No-any params required
     *
     * @return {@link #sellerCommission} instance as double
     */
    public double getSellerCommission() {
        return sellerCommission;
    }

    /**
     * Method to get {@link #sellerCommission} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #sellerCommission} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     */
    public double getSellerCommission(int decimals) {
        return roundValue(sellerCommission, decimals);
    }

    /**
     * Method to set {@link #sellerCommission}
     *
     * @param sellerCommission: seller commission
     * @throws IllegalArgumentException when seller commission value is less than 0
     */
    public void setSellerCommission(double sellerCommission) {
        if (sellerCommission < 0)
            throw new IllegalArgumentException("Seller commission value cannot be less than 0");
        this.sellerCommission = sellerCommission;
    }

    /**
     * Method to get {@link #commissionRates} instance <br>
     * No-any params required
     *
     * @return {@link #commissionRates} instance as {@link CommissionRates}
     */
    public CommissionRates getCommissionRates() {
        return commissionRates;
    }

    /**
     * Method to set {@link #commissionRates}
     *
     * @param commissionRates: commission rates to set for the spot account
     */
    public void setCommissionRates(CommissionRates commissionRates) {
        this.commissionRates = commissionRates;
    }

    /**
     * Method to get {@link #canTrade} instance <br>
     * No-any params required
     *
     * @return {@link #canTrade} instance as boolean
     */
    public boolean canTrade() {
        return canTrade;
    }

    /**
     * Method to set {@link #canTrade} instance <br>
     *
     * @param canTrade: if account can trade
     */
    public void setCanTrade(boolean canTrade) {
        this.canTrade = canTrade;
    }

    /**
     * Method to get {@link #canWithdraw} instance <br>
     * No-any params required
     *
     * @return {@link #canWithdraw} instance as boolean
     */
    public boolean canWithdraw() {
        return canWithdraw;
    }

    /**
     * Method to set {@link #canWithdraw} instance <br>
     *
     * @param canWithdraw: if account can withdraw
     */
    public void setCanWithdraw(boolean canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    /**
     * Method to get {@link #canDeposit} instance <br>
     * No-any params required
     *
     * @return {@link #canDeposit} instance as boolean
     */
    public boolean canDeposit() {
        return canDeposit;
    }

    /**
     * Method to set {@link #canDeposit} instance <br>
     *
     * @param canDeposit: if account can deposit
     */
    public void setCanDeposit(boolean canDeposit) {
        this.canDeposit = canDeposit;
    }

    /**
     * Method to get {@link #brokered} instance <br>
     * No-any params required
     *
     * @return {@link #brokered} instance as boolean
     */
    public boolean isBrokered() {
        return brokered;
    }

    /**
     * Method to set {@link #brokered} instance <br>
     *
     * @param brokered: if account is brokered
     */
    public void setBrokered(boolean brokered) {
        this.brokered = brokered;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as long
     */
    public long getUpdateTime() {
        return updateTime;
    }

    /**
     * Method to get {@link #updateTime} instance <br>
     * No-any params required
     *
     * @return {@link #updateTime} instance as {@link Date}
     */
    public Date getUpdateDate() {
        return TimeFormatter.getDate(updateTime);
    }

    /**
     * Method to set {@link #updateTime}
     *
     * @param updateTime: update time value
     * @throws IllegalArgumentException when update time value is less than 0
     */
    public void setUpdateTime(long updateTime) {
        if (updateTime < 0)
            throw new IllegalArgumentException("Update time value cannot be less than 0");
        this.updateTime = updateTime;
    }

    /**
     * Method to get {@link #accountType} instance <br>
     * No-any params required
     *
     * @return {@link #accountType} instance as {@link String}
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Method to get {@link #spotsListBalance} instance <br>
     * No-any params required
     *
     * @return {@link #spotsListBalance} instance as {@link ArrayList} of {@link SpotBalance}
     */
    public ArrayList<SpotBalance> getBalancesSpotsList() {
        return spotsListBalance;
    }

    /**
     * Method to set {@link #spotsListBalance} instance <br>
     *
     * @param spotsListBalance: list of {@link SpotBalance} to set
     */
    public void setBalancesSpotsList(ArrayList<SpotBalance> spotsListBalance) {
        this.spotsListBalance = spotsListBalance;
    }

    /**
     * Method to add a balance spot to {@link #spotsListBalance}
     *
     * @param spotBalance: balance spot to add
     */
    public void insertBalanceSpot(SpotBalance spotBalance) {
        if (!spotsListBalance.contains(spotBalance))
            spotsListBalance.add(spotBalance);
    }

    /**
     * Method to balance spot from {@link #spotsListBalance}
     *
     * @param balanceSpot: balance spot to remove
     * @return result of operation as boolean
     */
    public boolean removeBalanceSpot(SpotBalance balanceSpot) {
        return spotsListBalance.remove(balanceSpot);
    }

    /**
     * Method to get a margin order details  from {@link #spotsListBalance} list
     *
     * @param index: index to fetch the margin order details
     * @return margin order details  as {@link MarginOrderDetails}
     */
    public SpotBalance getBalanceSpot(int index) {
        return spotsListBalance.get(index);
    }

    /**
     * Method to get {@link #permissionsList} instance <br>
     * No-any params required
     *
     * @return {@link #permissionsList} instance as {@link ArrayList} of {@link Permission}
     */
    public ArrayList<Permission> getPermissionsList() {
        return permissionsList;
    }

    /**
     * Method to set {@link #permissionsList} instance <br>
     *
     * @param permissionsList: list of {@link Permission} to set
     */
    public void setPermissionsList(ArrayList<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }

    /**
     * Method to add a permission  to {@link #permissionsList}
     *
     * @param permission: permission to add
     */
    public void insertBalanceSpot(Permission permission) {
        if (!permissionsList.contains(permission))
            permissionsList.add(permission);
    }

    /**
     * Method to remove a permission  from {@link #permissionsList}
     *
     * @param permission: permission  to remove
     * @return result of operation as boolean
     */
    public boolean removeBalanceSpot(Permission permission) {
        return permissionsList.remove(permission);
    }

    /**
     * Method to get a permission from {@link #permissionsList} list
     *
     * @param index: index to fetch the permission
     * @return permission as {@link Permission}
     */
    public Permission getPermission(int index) {
        return permissionsList.get(index);
    }

    /**
     * Method to get {@link #requireSelfTradePrevention} instance <br>
     * No-any params required
     *
     * @return {@link #requireSelfTradePrevention} instance as boolean
     */
    public boolean isRequiredSelfTradePrevention() {
        return requireSelfTradePrevention;
    }

    /**
     * Method to set {@link #requireSelfTradePrevention} instance <br>
     *
     * @param requireSelfTradePrevention: whether the spot account require self trade prevention
     */
    public void setRequireSelfTradePrevention(boolean requireSelfTradePrevention) {
        this.requireSelfTradePrevention = requireSelfTradePrevention;
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

    /**
     * The {@code Permission} class is useful to obtain and format a permission for {@link SpotAccountInformation}
     *
     * @author N7ghtm4r3 - Tecknobit
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     * Account Information (USER_DATA)</a>
     */
    public static class Permission {

        /**
         * {@code permission} is instance that memorizes permission value
         */
        private final String permission;

        /**
         * Constructor to init {@link Permission} object
         *
         * @param permission: permission value
         */
        public Permission(String permission) {
            this.permission = permission;
        }

        /**
         * Method to get {@link #permission} instance <br>
         * No-any params required
         *
         * @return {@link #permission} instance as {@link String}
         */
        public String getPermission() {
            return permission;
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
     * The {@code CommissionRates} class is useful to obtain and format a commission rates for {@link SpotAccountInformation}
     *
     * @author N7ghtm4r3 - Tecknobit
     * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-information-user_data">
     * Account Information (USER_DATA)</a>
     */
    public static class CommissionRates {

        /**
         * {@code "maker"} value
         */
        private final double maker;

        /**
         * {@code "taker"} value
         */
        private final double taker;

        /**
         * {@code "buyer"} value
         */
        private final double buyer;

        /**
         * {@code "seller"} value
         */
        private final double seller;

        /**
         * Constructor to init {@link CommissionRates} object
         *
         * @param maker:  maker value
         * @param taker:  taker value
         * @param buyer:  buyer value
         * @param seller: seller value
         */
        public CommissionRates(double maker, double taker, double buyer, double seller) {
            this.maker = maker;
            this.taker = taker;
            this.buyer = buyer;
            this.seller = seller;
        }

        /**
         * Constructor to init {@link CommissionRates} object
         *
         * @param jRates: commission rates details as {@link JSONObject}
         */
        public CommissionRates(JSONObject jRates) {
            JsonHelper hRates = new JsonHelper(jRates);
            maker = hRates.getDouble("maker", 0);
            taker = hRates.getDouble("taker", 0);
            buyer = hRates.getDouble("buyer", 0);
            seller = hRates.getDouble("seller", 0);
        }

        /**
         * Method to get {@link #maker} instance <br>
         * No-any params required
         *
         * @return {@link #maker} instance as double
         */
        public double getMaker() {
            return maker;
        }

        /**
         * Method to get {@link #maker} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #maker} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getMaker(int decimals) {
            return roundValue(maker, decimals);
        }

        /**
         * Method to get {@link #taker} instance <br>
         * No-any params required
         *
         * @return {@link #taker} instance as double
         */
        public double getTaker() {
            return taker;
        }

        /**
         * Method to get {@link #taker} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #taker} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getTaker(int decimals) {
            return roundValue(taker, decimals);
        }

        /**
         * Method to get {@link #buyer} instance <br>
         * No-any params required
         *
         * @return {@link #buyer} instance as double
         */
        public double getBuyer() {
            return buyer;
        }

        /**
         * Method to get {@link #buyer} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #buyer} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getBuyer(int decimals) {
            return roundValue(buyer, decimals);
        }

        /**
         * Method to get {@link #seller} instance <br>
         * No-any params required
         *
         * @return {@link #seller} instance as double
         */
        public double getSeller() {
            return seller;
        }

        /**
         * Method to get {@link #seller} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #seller} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         */
        public double getSeller(int decimals) {
            return roundValue(seller, decimals);
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
