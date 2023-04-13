package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.EarningsList.Earning;

public class EarningsList extends MiningResponse<Earning> {

    public EarningsList(Earning data) {
        super(data);
    }

    public EarningsList(JSONObject jEarningsList) {
        super(jEarningsList);
        data = new Earning(hItem.getJSONObject("data"));
    }

    public static class Earning extends DataListItem {

        private final ArrayList<AccountProfit> accountProfits;

        public Earning(int totalNum, int pageSize, ArrayList<AccountProfit> accountProfits) {
            super(totalNum, pageSize);
            this.accountProfits = accountProfits;
        }

        public Earning(JSONObject jEarning) {
            super(jEarning);
            accountProfits = new ArrayList<>();
            for (Object profit : hItem.fetchList("accountProfits"))
                accountProfits.add(new AccountProfit((JSONObject) profit));
        }

        public ArrayList<AccountProfit> getAccountProfits() {
            return accountProfits;
        }

        public static class AccountProfit extends Profit {

            private final long dayHashRate;
            private final long hashTransfer;
            private final double transferAmount;

            public AccountProfit(long time, int type, double profitAmount, String coinName, ProfitStatus status,
                                 long dayHashRate, long hashTransfer, double transferAmount) {
                super(time, type, profitAmount, coinName, status);
                this.dayHashRate = dayHashRate;
                this.hashTransfer = hashTransfer;
                this.transferAmount = transferAmount;
            }

            public AccountProfit(JSONObject jAccountProfit) {
                super(jAccountProfit);
                dayHashRate = hItem.getLong("dayHashRate", 0);
                hashTransfer = hItem.getLong("hashTransfer", 0);
                transferAmount = hItem.getDouble("transferAmount", 0);
            }

            public long getDayHashRate() {
                return dayHashRate;
            }

            public long getHashTransfer() {
                return hashTransfer;
            }

            public double getTransferAmount() {
                return transferAmount;
            }

        }

    }

}
