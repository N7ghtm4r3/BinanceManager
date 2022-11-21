package com.tecknobit.binancemanager.managers.signedmanagers.wallet.records.asset;

import com.tecknobit.apimanager.formatters.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code CoinInformation} class is useful to create a coin information object
 *
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">
 * https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
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
                           double ipoing, boolean isLegalMoney, double locked, String name, ArrayList<NetworkItem> networkItems,
                           double storage, boolean trading, boolean withdrawAllEnable, double withdrawing) {
        this.coin = coin;
        this.depositAllEnable = depositAllEnable;
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        else
            this.free = free;
        if(freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        else
            this.freeze = freeze;
        if(ipoable < 0)
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
     * @param coinInformation: coin information details as {@link JSONObject}
     * @throws IllegalArgumentException if parameters range is not respected
     **/
    public CoinInformation(JSONObject coinInformation) {
        coin = coinInformation.getString("coin");
        depositAllEnable = coinInformation.getBoolean("depositAllEnable");
        free = coinInformation.getDouble("free");
        if (free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        freeze = coinInformation.getDouble("freeze");
        if (freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        ipoable = coinInformation.getDouble("ipoable");
        if (ipoable < 0)
            throw new IllegalArgumentException("Ipoable value cannot be less than 0");
        ipoing = coinInformation.getDouble("ipoing");
        if (ipoing < 0)
            throw new IllegalArgumentException("Ipoing value cannot be less than 0");
        isLegalMoney = coinInformation.getBoolean("isLegalMoney");
        locked = coinInformation.getDouble("locked");
        if (locked < 0)
            throw new IllegalArgumentException("Locked value cannot be less than 0");
        name = coinInformation.getString("name");
        JSONArray networkList = JsonHelper.getJSONArray(coinInformation, "networkList", new JSONArray());
        networkItemsList = new ArrayList<>();
        for (int j = 0; j < networkList.length(); j++)
            networkItemsList.add(new NetworkItem(networkList.getJSONObject(j)));
        trading = coinInformation.getBoolean("trading");
        storage = coinInformation.getDouble("storage");
        if (storage < 0)
            throw new IllegalArgumentException("Storage value cannot be less than 0");
        withdrawAllEnable = coinInformation.getBoolean("withdrawAllEnable");
        withdrawing = coinInformation.getDouble("withdrawing");
        if (withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
    }

    public String getCoin() {
        return coin;
    }

    public boolean isDepositAllEnable() {
        return depositAllEnable;
    }

    public void setDepositAllEnable(boolean depositAllEnable) {
        this.depositAllEnable = depositAllEnable;
    }

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

    public boolean isLegalMoney() {
        return isLegalMoney;
    }

    public void setLegalMoney(boolean legalMoney) {
        isLegalMoney = legalMoney;
    }

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

    public String getName() {
        return name;
    }

    public ArrayList<NetworkItem> getNetworkItemsList() {
        return networkItemsList;
    }

    public void setNetworkItemsList(ArrayList<NetworkItem> networkItemsList) {
        this.networkItemsList = networkItemsList;
    }

    public void insertNetworkItem(NetworkItem networkItem){
        if(!networkItemsList.contains(networkItem))
            networkItemsList.add(networkItem);
    }

    public boolean removeNetworkItem(NetworkItem networkItem) {
        return networkItemsList.remove(networkItem);
    }

    public NetworkItem getNetWorkItem(int index) {
        return networkItemsList.get(index);
    }

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

    public boolean isTrading() {
        return trading;
    }

    public void setTrading(boolean trading) {
        this.trading = trading;
    }

    public boolean isWithdrawAllEnable() {
        return withdrawAllEnable;
    }

    public void setWithdrawAllEnable(boolean withdrawAllEnable) {
        this.withdrawAllEnable = withdrawAllEnable;
    }

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

    @Override
    public String toString() {
        return "CoinInformation{" +
                "coin='" + coin + '\'' +
                ", depositAllEnable=" + depositAllEnable +
                ", free=" + free +
                ", freeze=" + freeze +
                ", ipoable=" + ipoable +
                ", ipoing=" + ipoing +
                ", isLegalMoney=" + isLegalMoney +
                ", locked=" + locked +
                ", name='" + name + '\'' +
                ", networkItemsList=" + networkItemsList +
                ", storage=" + storage +
                ", trading=" + trading +
                ", withdrawAllEnable=" + withdrawAllEnable +
                ", withdrawing=" + withdrawing +
                '}';
    }

    /**
     * The {@code NetworkItem} class is useful to obtain and create a network item object
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
            minConfirm = hNetworkItem.getInt("minConfirm");
            name = hNetworkItem.getString("name");
            network = hNetworkItem.getString("network");
            resetAddressStatus = hNetworkItem.getBoolean("resetAddressStatus");
            specialTips = hNetworkItem.getString("specialTips", "Any special tips");
            unLockConfirm = hNetworkItem.getInt("unLockConfirm");
            withdrawDesc = hNetworkItem.getString("withdrawDesc");
            withdrawEnable = hNetworkItem.getBoolean("withdrawEnable");
            withdrawFee = hNetworkItem.getDouble("withdrawFee");
            withdrawIntegerMultiple = hNetworkItem.getDouble("withdrawIntegerMultiple");
            withdrawMax = hNetworkItem.getDouble("withdrawMax");
            withdrawMin = hNetworkItem.getDouble("withdrawMin");
            sameAddress = hNetworkItem.getBoolean("sameAddress");
        }

        public String getAddressRegex() {
            return addressRegex;
        }

        public String getCoin() {
            return coin;
        }

        public String getDepositDesc() {
            return depositDesc;
        }

        public boolean isDepositEnable() {
            return depositEnable;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public String getMemoRegex() {
            return memoRegex;
        }

        public int getMinConfirm() {
            return minConfirm;
        }

        public String getName() {
            return name;
        }

        public String getNetwork() {
            return network;
        }

        public boolean isResetAddressStatus() {
            return resetAddressStatus;
        }

        public String getSpecialTips() {
            return specialTips;
        }

        public int getUnLockConfirm() {
            return unLockConfirm;
        }

        public String getWithdrawDesc() {
            return withdrawDesc;
        }

        public boolean isWithdrawEnable() {
            return withdrawEnable;
        }

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

        public boolean isSameAddress() {
            return sameAddress;
        }

        @Override
        public String toString() {
            return "NetworkItem{" +
                    "addressRegex='" + addressRegex + '\'' +
                    ", coin='" + coin + '\'' +
                    ", depositDesc='" + depositDesc + '\'' +
                    ", depositEnable=" + depositEnable +
                    ", isDefault=" + isDefault +
                    ", memoRegex='" + memoRegex + '\'' +
                    ", minConfirm=" + minConfirm +
                    ", name='" + name + '\'' +
                    ", network='" + network + '\'' +
                    ", resetAddressStatus=" + resetAddressStatus +
                    ", specialTips='" + specialTips + '\'' +
                    ", unLockConfirm=" + unLockConfirm +
                    ", withdrawDesc='" + withdrawDesc + '\'' +
                    ", withdrawEnable=" + withdrawEnable +
                    ", withdrawFee=" + withdrawFee +
                    ", withdrawIntegerMultiple=" + withdrawIntegerMultiple +
                    ", withdrawMax=" + withdrawMax +
                    ", withdrawMin=" + withdrawMin +
                    ", sameAddress=" + sameAddress +
                    '}';
        }

    }

}