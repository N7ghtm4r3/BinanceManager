package com.tecknobit.binancemanager.Managers.Wallet.Records.Asset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  The {@code CoinInformation} class is useful to manage AllCoins Binance request
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
 * **/

public class CoinInformation {

    private final String coin;
    private final boolean depositAllEnable;
    private final double free;
    private final double freeze;
    private final double ipoable;
    private final double ipoing;
    private final boolean isLegalMoney;
    private final double locked;
    private final String name;
    private ArrayList<NetworkItem> networkItems;
    private final double storage;
    private final boolean trading;
    private final boolean withdrawAllEnable;
    private final double withdrawing;

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
        this.networkItems = networkItems;
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

    public double getFree() {
        return free;
    }

    public double getFreeze() {
        return freeze;
    }

    public double getIpoable() {
        return ipoable;
    }

    public double getIpoing() {
        return ipoing;
    }

    public boolean isLegalMoney() {
        return isLegalMoney;
    }

    public double getLocked() {
        return locked;
    }

    public String getName() {
        return name;
    }

    public void setNetworkItems(ArrayList<NetworkItem> networkItems) {
        this.networkItems = networkItems;
    }

    public ArrayList<NetworkItem> getNetworkItems() {
        return networkItems;
    }

    public double getStorage() {
        return storage;
    }

    public boolean isTrading() {
        return trading;
    }

    public boolean isWithdrawAllEnable() {
        return withdrawAllEnable;
    }

    public double getWithdrawing() {
        return withdrawing;
    }

    /**
     *  The {@code NetworkItem} class is useful to obtain and format NetworkItem object
     *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
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
         * @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#all-coins-39-information-user_data
         * return networkItems list as  ArrayList<NetworkItem>
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
