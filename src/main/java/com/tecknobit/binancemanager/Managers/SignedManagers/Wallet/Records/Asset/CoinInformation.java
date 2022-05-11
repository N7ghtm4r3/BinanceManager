package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Asset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code CoinInformation} class is useful to manage AllCoins Binance request
 *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
 * **/

public class CoinInformation {

    private final String coin;
    private boolean depositAllEnable;
    private double free;
    private double freeze;
    private double ipoable;
    private double ipoing;
    private boolean isLegalMoney;
    private double locked;
    private final String name;
    private ArrayList<NetworkItem> networkItemsList;
    private double storage;
    private boolean trading;
    private boolean withdrawAllEnable;
    private double withdrawing;

    public CoinInformation(String coin, boolean depositAllEnable, double free, double freeze, double ipoable,
                           double ipoing, boolean isLegalMoney, double locked, String name, ArrayList<NetworkItem> networkItems,
                           double storage, boolean trading, boolean withdrawAllEnable, double withdrawing) {
        this.coin = coin;
        this.depositAllEnable = depositAllEnable;
        this.free = free;
        this.freeze = freeze;
        this.ipoable = ipoable;
        this.ipoing = ipoing;
        this.isLegalMoney = isLegalMoney;
        this.locked = locked;
        this.name = name;
        this.networkItemsList = networkItems;
        this.storage = storage;
        this.trading = trading;
        this.withdrawAllEnable = withdrawAllEnable;
        this.withdrawing = withdrawing;
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

    public void setFree(double free) {
        if(free < 0)
            throw new IllegalArgumentException("Free value cannot be less than 0");
        this.free = free;
    }

    public double getFreeze() {
        return freeze;
    }

    public void setFreeze(double freeze) {
        if(freeze < 0)
            throw new IllegalArgumentException("Freeze value cannot be less than 0");
        this.freeze = freeze;
    }

    public double getIpoable() {
        return ipoable;
    }

    public void setIpoable(double ipoable) {
        if(ipoable < 0)
            throw new IllegalArgumentException("Ipoable value cannot be less than 0");
        this.ipoable = ipoable;
    }

    public double getIpoing() {
        return ipoing;
    }

    public void setIpoing(double ipoing) {
        if(ipoing < 0)
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

    public void setLocked(double locked) {
        if(locked < 0)
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

    public boolean removeNetworkItem(NetworkItem networkItem){
        return networkItemsList.remove(networkItem);
    }

    public NetworkItem getNetWorkItem(int index){
        try{
            return networkItemsList.get(index);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException(index);
        }
    }

    public double getStorage() {
        return storage;
    }

    public void setStorage(double storage) {
        if(storage < 0)
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

    public void setWithdrawing(double withdrawing) {
        if(withdrawing < 0)
            throw new IllegalArgumentException("Withdrawing value cannot be less than 0");
        this.withdrawing = withdrawing;
    }

    /**
     *  The {@code NetworkItem} class is useful to obtain and format NetworkItem object
     *  @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
     * **/

    public static class NetworkItem {

        private final String addressRegex;
        private final String coin;
        private final String depositDesc;
        private final boolean depositEnable;
        private final boolean isDefault;
        private final String memoRegex;
        private final int minConfirm;
        private final String name;
        private final String network;
        private final boolean resetAddressStatus;
        private final String specialTips;
        private final int unLockConfirm;
        private final String withdrawDesc;
        private final boolean withdrawEnable;
        private final double withdrawFee;
        private final double withdrawIntegerMultiple;
        private final double withdrawMax;
        private final double withdrawMin;
        private final boolean sameAddress;


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

        /** Method to assemble an AssetDribbletsDetails list
         * @param #networkList: jsonArray obtain by AllCoins Binance request
         * @apiNote see official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data">https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data</a>
         * @return networkItemsList list as  ArrayList<NetworkItem>
         * **/
        public static ArrayList<NetworkItem> getNetworkList(JSONArray networkList) {
            ArrayList<NetworkItem> networkItems = new ArrayList<>();
            for (int j=0; j < networkList.length(); j++){
                JSONObject networkItem = networkList.getJSONObject(j);
                String specialTips;
                try {
                    specialTips = networkItem.getString("specialTips");
                }catch (JSONException e){
                    specialTips = "Any special tips";
                }
                networkItems.add(new NetworkItem(networkItem.getString("addressRegex"),
                        networkItem.getString("coin"),
                        networkItem.getString("depositDesc"),
                        networkItem.getBoolean("depositEnable"),
                        networkItem.getBoolean("isDefault"),
                        networkItem.getString("memoRegex"),
                        networkItem.getInt("minConfirm"),
                        networkItem.getString("name"),
                        networkItem.getString("network"),
                        networkItem.getBoolean("resetAddressStatus"),
                        specialTips,
                        networkItem.getInt("unLockConfirm"),
                        networkItem.getString("withdrawDesc"),
                        networkItem.getBoolean("withdrawEnable"),
                        networkItem.getDouble("withdrawFee"),
                        networkItem.getDouble("withdrawIntegerMultiple"),
                        networkItem.getDouble("withdrawMax"),
                        networkItem.getDouble("withdrawMin"),
                        networkItem.getBoolean("sameAddress")
                ));
            }
            return networkItems;
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

        public double getWithdrawIntegerMultiple() {
            return withdrawIntegerMultiple;
        }

        public double getWithdrawMax() {
            return withdrawMax;
        }

        public double getWithdrawMin() {
            return withdrawMin;
        }

        public boolean isSameAddress() {
            return sameAddress;
        }

    }

}
