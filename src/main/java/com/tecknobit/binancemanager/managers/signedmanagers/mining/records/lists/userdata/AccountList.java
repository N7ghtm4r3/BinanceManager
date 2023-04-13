package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.miner.DetailMinerList.DetailMiner.HashrateData;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.AccountList.MiningAccount;

public class AccountList extends MiningResponse<ArrayList<MiningAccount>> {

    public AccountList(ArrayList<MiningAccount> data) {
        super(data);
    }

    public AccountList(JSONObject jMiningResponse) {
        super(jMiningResponse);
        data = new ArrayList<>();
        for (Object account : hItem.fetchList("data"))
            data.add(new MiningAccount((JSONObject) account));
    }

    public static class MiningAccount extends BinanceItem {

        private final HashRateType type;
        private final String userName;
        private final ArrayList<HashrateData> list;

        public MiningAccount(HashRateType type, String userName, ArrayList<HashrateData> list) {
            super(null);
            this.type = type;
            this.userName = userName;
            this.list = list;
        }

        public MiningAccount(JSONObject jMiningAccount) {
            super(jMiningAccount);
            type = HashRateType.valueOf(hItem.getString("type"));
            userName = hItem.getString("userName");
            list = new ArrayList<>();
            for (Object item : hItem.fetchList("list"))
                list.add(new HashrateData((JSONObject) item));
        }

        public HashRateType getType() {
            return type;
        }

        public String getUserName() {
            return userName;
        }

        public ArrayList<HashrateData> getList() {
            return list;
        }

    }

}
