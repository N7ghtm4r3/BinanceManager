package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.DetailMinerList.DetailMiner.HashrateData;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.AccountList.MiningAccount;

/**
 * The {@code AccountList} class is useful to create an account list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#account-list-user_data">
 * Account List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 */
public class AccountList extends MiningResponse<ArrayList<MiningAccount>> {

    /**
     * Constructor to init {@link AccountList} object
     *
     * @param data: account list
     */
    public AccountList(ArrayList<MiningAccount> data) {
        super(data);
    }

    /**
     * Constructor to init {@link AccountList} object
     *
     * @param jAccountList: account list details as {@link JSONObject}
     */
    public AccountList(JSONObject jAccountList) {
        super(jAccountList);
        data = new ArrayList<>();
        ArrayList<JSONObject> jList = hItem.fetchList("data");
        if (jList != null)
            for (JSONObject account : jList)
                data.add(new MiningAccount(account));
    }

    /**
     * The {@code MiningAccount} class is useful to create a mining account
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     */
    public static class MiningAccount extends BinanceItem {

        /**
         * {@code type} of the hashrate
         */
        private final HashRateType type;

        /**
         * {@code userName} mining account
         */
        private final String userName;

        /**
         * {@code list} hashrate data list
         */
        private final ArrayList<HashrateData> list;

        /**
         * Constructor to init {@link MiningAccount} object
         *
         * @param type: type of the hashrate
         * @param userName: mining account
         * @param list: hashrate data list
         */
        public MiningAccount(HashRateType type, String userName, ArrayList<HashrateData> list) {
            super(null);
            this.type = type;
            this.userName = userName;
            this.list = list;
        }

        /**
         * Constructor to init {@link MiningAccount} object
         *
         * @param jMiningAccount: mining account details as {@link JSONObject}
         */
        public MiningAccount(JSONObject jMiningAccount) {
            super(jMiningAccount);
            type = HashRateType.valueOf(hItem.getString("type"));
            userName = hItem.getString("userName");
            list = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("list");
            if (jList != null)
                for (JSONObject account : jList)
                    list.add(new HashrateData(account));
        }

        /**
         * Method to get {@link #type} instance <br>
         * No-any params required
         *
         * @return {@link #type} instance as {@link HashRateType}
         */
        public HashRateType getType() {
            return type;
        }

        /**
         * Method to get {@link #userName} instance <br>
         * No-any params required
         *
         * @return {@link #userName} instance as {@link String}
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Method to get {@link #list} instance <br>
         * No-any params required
         *
         * @return {@link #list} instance as {@link ArrayList} of {@link HashrateData}
         */
        public ArrayList<HashrateData> getList() {
            return list;
        }

    }

}
