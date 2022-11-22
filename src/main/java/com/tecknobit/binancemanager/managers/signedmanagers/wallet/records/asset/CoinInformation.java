package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CoinInformation} class is useful to create a {@code "Binance"}'s coin information
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
 * All Coins' Information (USER_DATA)</a>
 **/
public class CoinInformation {

    /**
     * {@code coin} is instance that memorizes coin value
     * **/
    private final String coin;

    /**
     * {@code depositAllEnable} is instance that memorizes if all deposits are enabled
     * **/
    private boolean depositAllEnable;

    /**
     * {@code free} is instance that memorizes free value
     * **/
    private double free;

    /**
     * {@code freeze} is instance that memorizes freeze value
     * **/
    private double freeze;

    /**
     * {@code ipoable} is instance that memorizes ipoable value
     * **/
    private double ipoable;

    /**
     * {@code ipoing} is instance that memorizes ipoing value
     * **/
    private double ipoing;

    /**
     * {@code isLegalMoney} is instance that memorizes if is legal money value (Fiat currency)
     * **/
    private boolean isLegalMoney;

    /**
     * {@code locked} is instance that memorizes locked value
     * **/
    private double locked;

    /**
     * {@code name} is instance that memorizes name value
     * **/
    private final String name;

    /**
     * {@code networkItemsList} is instance that memorizes list of {@link NetworkItem}
     * **/
    private ArrayList<NetworkItem> networkItemsList;

    /**
     * {@code storage} is instance that memorizes storage value
     * **/
    private double storage;

    /**
     * {@code trading} is instance that memorizes if is trading
     * **/
    private boolean trading;

    /**
     * {@code withdrawAllEnable} is instance that memorizes if all withdrawals are enabled
     * **/
    private boolean withdrawAllEnable;

    /**
     * {@code storage} is instance that memorizes withdrawing value
     * **/
    private double withdrawing;

    /** Constructor to init {@link CoinInformation} object
     * @param coin: coin value
     * @param depositAllEnable: all deposits are enabled
     * @param free: free value
     * @param freeze: freeze value
     * @param ipoable: ipoable value
     * @param ipoing: ipoing value
     * @param isLegalMoney: is legal money value (Fiat currency)
     * @param locked: locked value
     * @param name: name value
     * @param networkItems: list of {@link NetworkItem}
     * @param storage: storage value
     * @param trading: is trading
     * @param withdrawAllEnable: all withdrawals are enabled
     * @param withdrawing: withdrawing value
     * @throws IllegalArgumentException if parameters range is not respected
     * **/
    public CoinInformation(String coin, boolean depositAllEnable, double free, double freeze, double ipoable,
                           double ipoing, boolean isLegalMoney, double locked, String name,
                           ArrayList<NetworkItem> networkItems, double storage, boolean trading, boolean withdrawAllEnable,
                           double withdrawing) {
        this.coin = coin;
        this.depositAllEnable = depositAllEnable;
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        else
            this.free = free;
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        else
            this.freeze = freeze;
        if (ipoable < 0)
            throw new IllegalArgumentException("Ipoable value cannot be less than 0");
        else
            this.ipoable = ipoable;
        if(ipoing < 0)
            throw new IllegalArgumentException("Ipoing value cannot be less than 0");
        else
            this.ipoing = ipoing;
        this.isLegalMoney = isLegalMoney;
        if(locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        else
            this.locked = locked;
        this.name = name;
        this.networkItemsList = networkItems;
        if(storage < 0)
            throw new IllegalArgumentException("Storage value cannot be less than 0");
        else
            this.storage = storage;
        this.trading = trading;
        this.withdrawAllEnable = withdrawAllEnable;
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        else
            this.withdrawing = withdrawing;
    }

    /**
     * Constructor to init {@link CoinInformation} object
     *
     * @param jCoin: coin information details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public CoinInformation(JSONObject jCoin) {
        coin = jCoin.getString("coin");
        depositAllEnable = jCoin.getBoolean("depositAllEnable");
        free = jCoin.getDouble("free");
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        freeze = jCoin.getDouble("freeze");
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        ipoable = jCoin.getDouble("ipoable");
        if (ipoable < 0)
            throw new IllegalArgumentException("Ipoable value cannot be less than 0");
        ipoing = jCoin.getDouble("ipoing");
        if (ipoing < 0)
            throw new IllegalArgumentException("Ipoing value cannot be less than 0");
        isLegalMoney = jCoin.getBoolean("isLegalMoney");
        locked = jCoin.getDouble("locked");
        if (locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        name = jCoin.getString("name");
        JSONArray networkList = JsonHelper.getJSONArray(jCoin, "networkList", new JSONArray());
        networkItemsList = new ArrayList<>();
        for (int j = 0; j < networkList.length(); j++)
            networkItemsList.add(new NetworkItem(networkList.getJSONObject(j)));
        trading = jCoin.getBoolean("trading");
        storage = jCoin.getDouble("storage");
        if (storage < 0)
            throw new IllegalArgumentException("Storage value cannot be less than 0");
        withdrawAllEnable = jCoin.getBoolean("withdrawAllEnable");
        withdrawing = jCoin.getDouble("withdrawing");
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
    }

    /**
     * Method to get {@link #coin} instance <br>
     * Any params required
     *
     * @return {@link #coin} instance as {@link String}
     **/
    public String getCoin() {
        return coin;
    }

    /**
     * Method to get {@link #depositAllEnable} instance <br>
     * Any params required
     *
     * @return {@link #depositAllEnable} instance as boolean
     **/
    public boolean areDepositsAllEnabled() {
        return depositAllEnable;
    }

    /**
     * Method to set {@link #depositAllEnable}
     *
     * @param depositAllEnable: whether all deposits are enabled
     **/
    public void setDepositAllEnable(boolean depositAllEnable) {
        this.depositAllEnable = depositAllEnable;
    }

    /**
     * Method to get {@link #free} instance <br>
     * Any params required
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
     * @param free: free value
     * @throws IllegalArgumentException when free value is less than 0
     **/
    public void setFree(double free) {
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        this.free = free;
    }

    /**
     * Method to get {@link #freeze} instance <br>
     * Any params required
     *
     * @return {@link #freeze} instance as double
     **/
    public double getFreeze() {
        return freeze;
    }

    /**
     * Method to get {@link #freeze} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #freeze} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getFreeze(int decimals) {
        return roundValue(freeze, decimals);
    }

    /**
     * Method to set {@link #freeze}
     *
     * @param freeze: freeze value
     * @throws IllegalArgumentException when freeze value is less than 0
     **/
    public void setFreeze(double freeze) {
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        this.freeze = freeze;
    }

    /**
     * Method to get {@link #ipoable} instance <br>
     * Any params required
     *
     * @return {@link #ipoable} instance as double
     **/
    public double getIpoable() {
        return ipoable;
    }

    /**
     * Method to get {@link #ipoable} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #ipoable} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIpoable(int decimals) {
        return roundValue(ipoable, decimals);
    }

    /**
     * Method to set {@link #ipoable}
     *
     * @param ipoable: freeze value
     * @throws IllegalArgumentException when ipoable value is less than 0
     **/
    public void setIpoable(double ipoable) {
        if (ipoable < 0)
            throw new IllegalArgumentException("Ipoable value cannot be less than 0");
        this.ipoable = ipoable;
    }

    /**
     * Method to get {@link #ipoing} instance <br>
     * Any params required
     *
     * @return {@link #ipoing} instance as double
     **/
    public double getIpoing() {
        return ipoing;
    }

    /**
     * Method to get {@link #ipoing} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #ipoing} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getIpoing(int decimals) {
        return roundValue(ipoing, decimals);
    }

    /**
     * Method to set {@link #ipoing}
     *
     * @param ipoing: ipoing value
     * @throws IllegalArgumentException when ipoing value is less than 0
     **/
    public void setIpoing(double ipoing) {
        if (ipoing < 0)
            throw new IllegalArgumentException("Ipoing value cannot be less than 0");
        this.ipoing = ipoing;
    }

    /**
     * Method to get {@link #isLegalMoney} instance <br>
     * Any params required
     *
     * @return {@link #isLegalMoney} instance as boolean
     **/
    public boolean isLegalMoney() {
        return isLegalMoney;
    }

    /**
     * Method to set {@link #isLegalMoney}
     *
     * @param legalMoney: whether is a legal money
     **/
    public void setLegalMoney(boolean legalMoney) {
        isLegalMoney = legalMoney;
    }

    /**
     * Method to get {@link #locked} instance <br>
     * Any params required
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
     * @param locked: locked value
     * @throws IllegalArgumentException when locked value is less than 0
     **/
    public void setLocked(double locked) {
        if (locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        this.locked = locked;
    }

    /**
     * Method to get {@link #name} instance <br>
     * Any params required
     *
     * @return {@link #name} instance as {@link String}
     **/
    public String getName() {
        return name;
    }

    /**
     * Method to get {@link #networkItemsList} instance <br>
     * Any params required
     *
     * @return {@link #networkItemsList} instance as {@link ArrayList} of {@link NetworkItem}
     **/
    public ArrayList<NetworkItem> getNetworkItemsList() {
        return networkItemsList;
    }

    /**
     * Method to set {@link #networkItemsList} instance <br>
     *
     * @param networkItemsList: list of {@link NetworkItem} to set
     **/
    public void setNetworkItemsList(ArrayList<NetworkItem> networkItemsList) {
        this.networkItemsList = networkItemsList;
    }

    /**
     * Method to add a network item  to {@link #networkItemsList}
     *
     * @param networkItem: network item to add
     **/
    public void insertNetworkItem(NetworkItem networkItem) {
        if (!networkItemsList.contains(networkItem))
            networkItemsList.add(networkItem);
    }

    /**
     * Method to remove a network item  from {@link #networkItemsList}
     *
     * @param networkItem: network item  to remove
     * @return result of operation as boolean
     **/
    public boolean removeNetworkItem(NetworkItem networkItem) {
        return networkItemsList.remove(networkItem);
    }

    /**
     * Method to get a network item  from {@link #networkItemsList} list
     *
     * @param index: index to fetch the network item
     * @return network item  as {@link NetworkItem}
     **/
    public NetworkItem getNetWorkItem(int index) {
        return networkItemsList.get(index);
    }

    /**
     * Method to get {@link #storage} instance <br>
     * Any params required
     *
     * @return {@link #storage} instance as double
     **/
    public double getStorage() {
        return storage;
    }

    /**
     * Method to get {@link #storage} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #storage} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getStorage(int decimals) {
        return roundValue(storage, decimals);
    }

    /**
     * Method to set {@link #storage}
     *
     * @param storage: storage value
     * @throws IllegalArgumentException when storage value is less than 0
     **/
    public void setStorage(double storage) {
        if (storage < 0)
            throw new IllegalArgumentException("Storage value cannot be less than 0");
        this.storage = storage;
    }

    /**
     * Method to get {@link #trading} instance <br>
     * Any params required
     *
     * @return {@link #trading} instance as boolean
     **/
    public boolean canTrading() {
        return trading;
    }

    /**
     * Method to set {@link #trading}
     *
     * @param trading: whether the trading is enabled
     **/
    public void setTrading(boolean trading) {
        this.trading = trading;
    }

    /**
     * Method to get {@link #withdrawAllEnable} instance <br>
     * Any params required
     *
     * @return {@link #withdrawAllEnable} instance as boolean
     **/
    public boolean isWithdrawAllEnable() {
        return withdrawAllEnable;
    }

    /**
     * Method to set {@link #withdrawAllEnable}
     *
     * @param withdrawAllEnable: whether the withdrawals are enabled
     **/
    public void setWithdrawAllEnabled(boolean withdrawAllEnable) {
        this.withdrawAllEnable = withdrawAllEnable;
    }

    /**
     * Method to get {@link #withdrawing} instance <br>
     * Any params required
     *
     * @return {@link #withdrawing} instance as double
     **/
    public double getWithdrawing() {
        return withdrawing;
    }

    /**
     * Method to get {@link #withdrawing} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #withdrawing} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getWithdrawing(int decimals) {
        return roundValue(withdrawing, decimals);
    }

    /**
     * Method to set {@link #withdrawing}
     *
     * @param withdrawing: withdrawing value
     * @throws IllegalArgumentException when withdrawing value is less than 0
     **/
    public void setWithdrawing(double withdrawing) {
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        this.withdrawing = withdrawing;
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * The {@code NetworkItem} class is useful to format a network item
     *
     * @author N7ghtm4r3 - Tecknobit
     **/
    public static class NetworkItem {

        /**
         * {@code addressRegex} is instance that memorizes address regex value
         * **/
        private final String addressRegex;

        /**
         * {@code coin} is instance that memorizes coin value
         * **/
        private final String coin;

        /**
         * {@code depositDesc} is instance that memorizes deposit description value
         * **/
        private final String depositDesc;

        /**
         * {@code depositEnable} is instance that memorizes if deposit is enabled
         * **/
        private final boolean depositEnable;

        /**
         * {@code isDefault} is instance that memorizes if is default
         * **/
        private final boolean isDefault;

        /**
         * {@code memoRegex} is instance that memorizes memo regex value
         * **/
        private final String memoRegex;

        /**
         * {@code minConfirm} is instance that memorizes minimum confirms value
         * **/
        private final int minConfirm;

        /**
         * {@code name} is instance that memorizes name value
         * **/
        private final String name;

        /**
         * {@code network} is instance that memorizes network value
         * **/
        private final String network;

        /**
         * {@code resetAddressStatus} is instance that memorizes if is reset address status
         * **/
        private final boolean resetAddressStatus;

        /**
         * {@code specialTips} is instance that memorizes special tips value
         * **/
        private final String specialTips;

        /**
         * {@code unLockConfirm} is instance that memorizes unLock confirm value
         * **/
        private final int unLockConfirm;

        /**
         * {@code withdrawDesc} is instance that memorizes withdraw desc value
         * **/
        private final String withdrawDesc;

        /**
         * {@code withdrawEnable} is instance that memorizes if withdraw is enabled
         * **/
        private final boolean withdrawEnable;

        /**
         * {@code withdrawFee} is instance that memorizes withdraw fee value
         * **/
        private final double withdrawFee;

        /**
         * {@code withdrawIntegerMultiple} is instance that memorizes withdraw integer multiple value
         * **/
        private final double withdrawIntegerMultiple;

        /**
         * {@code withdrawMax} is instance that memorizes withdraw max value
         * **/
        private final double withdrawMax;

        /**
         * {@code withdrawMin} is instance that memorizes withdraw min value
         * **/
        private final double withdrawMin;

        /**
         * {@code sameAddress} is instance that memorizes if is the same address
         * **/
        private final boolean sameAddress;

        /** Constructor to init {@link CoinInformation} object
         * @param addressRegex: address regex value
         * @param coin: coin value
         * @param depositDesc: deposit description value
         * @param depositEnable: deposit is enabled
         * @param isDefault: is default
         * @param memoRegex: memo regex value
         * @param minConfirm: minimum confirms value
         * @param name: name value
         * @param network: network value
         * @param resetAddressStatus: is reset address status
         * @param specialTips: special tips value
         * @param unLockConfirm: unLock confirm value
         * @param withdrawDesc: withdraw desc value
         * @param withdrawEnable: withdraw is enabled
         * @param withdrawFee: withdraw fee value
         * @param withdrawIntegerMultiple: withdraw integer multiple value
         * @param withdrawMax: withdraw max value
         * @param withdrawMin: withdraw min value
         * @param sameAddress: is the same address
         * **/
        public NetworkItem(String addressRegex, String coin, String depositDesc, boolean depositEnable,
                            boolean isDefault, String memoRegex, int minConfirm, String name, String network,
                            boolean resetAddressStatus, String specialTips, int unLockConfirm, String withdrawDesc,
                            boolean withdrawEnable, double withdrawFee, double withdrawIntegerMultiple, double withdrawMax,
                            double withdrawMin, boolean sameAddress) {
            this.addressRegex = addressRegex;
            this.coin = coin;
            this.depositDesc = depositDesc;
            this.depositEnable = depositEnable;
            this.isDefault = isDefault;
            this.memoRegex = memoRegex;
            this.minConfirm = minConfirm;
            this.name = name;
            this.network = network;
            this.resetAddressStatus = resetAddressStatus;
            this.specialTips = specialTips;
            this.unLockConfirm = unLockConfirm;
            this.withdrawDesc = withdrawDesc;
            this.withdrawEnable = withdrawEnable;
            this.withdrawFee = withdrawFee;
            this.withdrawIntegerMultiple = withdrawIntegerMultiple;
            this.withdrawMax = withdrawMax;
            this.withdrawMin = withdrawMin;
            this.sameAddress = sameAddress;
        }

        /**
         * Constructor to init {@link CoinInformation} object
         *
         * @param networkItem: network item details as {@link JSONObject}
         **/
        public NetworkItem(JSONObject networkItem) {
            JsonHelper hNetworkItem = new JsonHelper(networkItem);
            addressRegex = hNetworkItem.getString("addressRegex");
            coin = hNetworkItem.getString("coin");
            depositDesc = hNetworkItem.getString("depositDesc");
            depositEnable = hNetworkItem.getBoolean("depositEnable");
            isDefault = hNetworkItem.getBoolean("isDefault");
            memoRegex = hNetworkItem.getString("memoRegex");
            minConfirm = hNetworkItem.getInt("minConfirm", 0);
            name = hNetworkItem.getString("name");
            network = hNetworkItem.getString("network");
            resetAddressStatus = hNetworkItem.getBoolean("resetAddressStatus");
            specialTips = hNetworkItem.getString("specialTips", "Any special tips");
            unLockConfirm = hNetworkItem.getInt("unLockConfirm", 0);
            withdrawDesc = hNetworkItem.getString("withdrawDesc");
            withdrawEnable = hNetworkItem.getBoolean("withdrawEnable");
            withdrawFee = hNetworkItem.getDouble("withdrawFee", 0);
            withdrawIntegerMultiple = hNetworkItem.getDouble("withdrawIntegerMultiple", 0);
            withdrawMax = hNetworkItem.getDouble("withdrawMax", 0);
            withdrawMin = hNetworkItem.getDouble("withdrawMin", 0);
            sameAddress = hNetworkItem.getBoolean("sameAddress");
        }

        /**
         * Method to get {@link #addressRegex} instance <br>
         * Any params required
         *
         * @return {@link #addressRegex} instance as {@link String}
         **/
        public String getAddressRegex() {
            return addressRegex;
        }

        /**
         * Method to get {@link #coin} instance <br>
         * Any params required
         *
         * @return {@link #coin} instance as {@link String}
         **/
        public String getCoin() {
            return coin;
        }

        /**
         * Method to get {@link #depositDesc} instance <br>
         * Any params required
         *
         * @return {@link #depositDesc} instance as {@link String}
         **/
        public String getDepositDesc() {
            return depositDesc;
        }

        /**
         * Method to get {@link #depositEnable} instance <br>
         * Any params required
         *
         * @return {@link #depositEnable} instance as boolean
         **/
        public boolean isDepositEnabled() {
            return depositEnable;
        }

        /**
         * Method to get {@link #isDefault} instance <br>
         * Any params required
         *
         * @return {@link #isDefault} instance as boolean
         **/
        public boolean isDefault() {
            return isDefault;
        }

        /**
         * Method to get {@link #memoRegex} instance <br>
         * Any params required
         *
         * @return {@link #memoRegex} instance as {@link String}
         **/
        public String getMemoRegex() {
            return memoRegex;
        }

        /**
         * Method to get {@link #minConfirm} instance <br>
         * Any params required
         *
         * @return {@link #minConfirm} instance as int
         **/
        public int getMinConfirm() {
            return minConfirm;
        }

        /**
         * Method to get {@link #name} instance <br>
         * Any params required
         *
         * @return {@link #name} instance as {@link String}
         **/
        public String getName() {
            return name;
        }

        /**
         * Method to get {@link #network} instance <br>
         * Any params required
         *
         * @return {@link #network} instance as {@link String}
         **/
        public String getNetwork() {
            return network;
        }

        /**
         * Method to get {@link #resetAddressStatus} instance <br>
         * Any params required
         *
         * @return {@link #resetAddressStatus} instance as boolean
         **/
        public boolean isResetAddressStatus() {
            return resetAddressStatus;
        }

        /**
         * Method to get {@link #specialTips} instance <br>
         * Any params required
         *
         * @return {@link #specialTips} instance as {@link String}
         **/
        public String getSpecialTips() {
            return specialTips;
        }

        /**
         * Method to get {@link #unLockConfirm} instance <br>
         * Any params required
         *
         * @return {@link #unLockConfirm} instance as int
         **/
        public int getUnLockConfirm() {
            return unLockConfirm;
        }

        /**
         * Method to get {@link #withdrawDesc} instance <br>
         * Any params required
         *
         * @return {@link #withdrawDesc} instance as {@link String}
         **/
        public String getWithdrawDesc() {
            return withdrawDesc;
        }

        /**
         * Method to get {@link #withdrawEnable} instance <br>
         * Any params required
         *
         * @return {@link #withdrawEnable} instance as boolean
         **/
        public boolean isWithdrawEnabled() {
            return withdrawEnable;
        }

        /**
         * Method to get {@link #withdrawFee} instance <br>
         * Any params required
         *
         * @return {@link #withdrawFee} instance as double
         **/
        public double getWithdrawFee() {
            return withdrawFee;
        }

        /**
         * Method to get {@link #withdrawFee} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #withdrawFee} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getWithdrawFee(int decimals) {
            return roundValue(withdrawFee, decimals);
        }

        /**
         * Method to get {@link #withdrawIntegerMultiple} instance <br>
         * Any params required
         *
         * @return {@link #withdrawIntegerMultiple} instance as double
         **/
        public double getWithdrawIntegerMultiple() {
            return withdrawIntegerMultiple;
        }

        /**
         * Method to get {@link #withdrawIntegerMultiple} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #withdrawIntegerMultiple} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getWithdrawIntegerMultiple(int decimals) {
            return roundValue(withdrawIntegerMultiple, decimals);
        }

        /**
         * Method to get {@link #withdrawMax} instance <br>
         * Any params required
         *
         * @return {@link #withdrawMax} instance as double
         **/
        public double getWithdrawMax() {
            return withdrawMax;
        }

        /**
         * Method to get {@link #withdrawMax} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #withdrawMax} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getWithdrawMax(int decimals) {
            return roundValue(withdrawMax, decimals);
        }

        /**
         * Method to get {@link #withdrawMin} instance <br>
         * Any params required
         *
         * @return {@link #withdrawMin} instance as double
         **/
        public double getWithdrawMin() {
            return withdrawMin;
        }

        /**
         * Method to get {@link #withdrawMin} instance
         *
         * @param decimals: number of digits to round final value
         * @return {@link #withdrawMin} instance rounded with decimal digits inserted
         * @throws IllegalArgumentException if decimalDigits is negative
         **/
        public double getWithdrawMin(int decimals) {
            return roundValue(withdrawMin, decimals);
        }

        /**
         * Method to get {@link #sameAddress} instance <br>
         * Any params required
         *
         * @return {@link #sameAddress} instance as boolean
         **/
        public boolean isSameAddress() {
            return sameAddress;
        }

        /**
         * Returns a string representation of the object <br>
         * Any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return new JSONObject(this).toString();
        }

    }

}
