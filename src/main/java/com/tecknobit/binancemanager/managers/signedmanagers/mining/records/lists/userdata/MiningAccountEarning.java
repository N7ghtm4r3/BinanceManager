package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.MiningAccountEarning.AccountEarningDetails;

public class MiningAccountEarning extends MiningResponse<AccountEarningDetails> {

    public MiningAccountEarning(AccountEarningDetails data) {
        super(data);
    }

    public MiningAccountEarning(JSONObject jMiningAccountEarning) {
        super(jMiningAccountEarning);
        data = new AccountEarningDetails(hItem.getJSONObject("data"));
    }

    public static class AccountEarningDetails extends DataListItem {

        private final ArrayList<AccountEarning> accountProfits;

        public AccountEarningDetails(int totalNum, int pageSize, ArrayList<AccountEarning> accountProfits) {
            super(totalNum, pageSize);
            this.accountProfits = accountProfits;
        }

        public AccountEarningDetails(JSONObject jAccountEarningDetails) {
            super(jAccountEarningDetails);
            accountProfits = new ArrayList<>();
            for (Object earning : hItem.fetchList("accountProfits"))
                accountProfits.add(new AccountEarning((JSONObject) earning));
        }

        public ArrayList<AccountEarning> getAccountProfits() {
            return accountProfits;
        }

        public static class AccountEarning extends BinanceItem {

            public enum EarningType {

                Referral(0),
                Refund(1),
                Rebate(2);

                private final int type;

                /**
                 * {@code VALUES} list of the types
                 **/
                private static final List<EarningType> VALUES = Arrays.stream(EarningType.values()).toList();

                EarningType(int type) {
                    this.type = type;
                }

                public int getType() {
                    return type;
                }

                /**
                 * Method to reach the enum constant <br>
                 *
                 * @param value: value to reach
                 * @return enum constants as {@link EarningType}
                 **/
                public static EarningType reachEnumConstant(int value) {
                    return VALUES.get(value);
                }

                @Override
                public String toString() {
                    return type + "";
                }

            }

            private final long time;
            private final String coinName;
            private final EarningType type;
            private final long puid;
            private final String subName;
            private final double amount;

            public AccountEarning(int totalNum, int pageSize, long time, String coinName, EarningType type, long puid,
                                  String subName, double amount) {
                super(null);
                this.time = time;
                this.coinName = coinName;
                this.type = type;
                this.puid = puid;
                this.subName = subName;
                this.amount = amount;
            }

            public AccountEarning(JSONObject jAccountEarning) {
                super(jAccountEarning);
                time = hItem.getLong("time", 0);
                coinName = hItem.getString("coinName");
                type = EarningType.reachEnumConstant(hItem.getInt("type", 0));
                puid = hItem.getLong("puid", 0);
                subName = hItem.getString("subName");
                amount = hItem.getDouble("amount", 0);
            }

            public long getTime() {
                return time;
            }

            public String getCoinName() {
                return coinName;
            }

            public EarningType getType() {
                return type;
            }

            public long getPuid() {
                return puid;
            }

            public String getSubName() {
                return subName;
            }

            public double getAmount() {
                return amount;
            }

        }

    }

}
