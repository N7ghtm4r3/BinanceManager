package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.Profit;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashrateResaleList.HashrateResale;

public class HashrateResaleList extends MiningResponse<HashrateResale> {

    public HashrateResaleList(HashrateResale data) {
        super(data);
    }

    public HashrateResaleList(JSONObject jHashrateResaleList) {
        super(jHashrateResaleList);
        data = new HashrateResale(hItem.getJSONObject("data"));
    }

    public static class HashrateResale extends DataListItem {

        private final ArrayList<ConfigDetail> configDetails;

        public HashrateResale(int totalNum, int pageSize, ArrayList<ConfigDetail> configDetails) {
            super(totalNum, pageSize);
            this.configDetails = configDetails;
        }

        public HashrateResale(JSONObject jHashrateResale) {
            super(jHashrateResale);
            configDetails = new ArrayList<>();
            for (Object config : hItem.fetchList("configDetails"))
                configDetails.add(new ConfigDetail((JSONObject) config));
        }

        public ArrayList<ConfigDetail> getConfigDetails() {
            return configDetails;
        }

        public static class ConfigDetail extends HashrateResaleStructure {

            public enum ConfigStatus {

                Processing(0),
                Cancelled(1),
                Terminated(2);

                private final int status;

                /**
                 * {@code VALUES} list of the types
                 **/
                private static final List<ConfigStatus> VALUES = Arrays.stream(ConfigStatus.values()).toList();

                ConfigStatus(int status) {
                    this.status = status;
                }

                public int getStatus() {
                    return status;
                }

                /**
                 * Method to reach the enum constant <br>
                 *
                 * @param value: value to reach
                 * @return enum constants as {@link Profit.ProfitStatus}
                 **/
                public static ConfigStatus reachEnumConstant(int value) {
                    return VALUES.get(value);
                }

                @Override
                public String toString() {
                    return status + "";
                }

            }

            private final long configId;
            private final long startDay;
            private final long endDay;
            private final ConfigStatus status;

            public ConfigDetail(String poolUsername, String toPoolUsername, String algoName, long hashRate, long configId,
                                long startDay, long endDay, ConfigStatus status) {
                super(poolUsername, toPoolUsername, algoName, hashRate);
                this.configId = configId;
                this.startDay = startDay;
                this.endDay = endDay;
                this.status = status;
            }

            public ConfigDetail(JSONObject jConfigDetail) {
                super(jConfigDetail);
                configId = hItem.getLong("configId", 0);
                startDay = hItem.getLong("startDay", 0);
                endDay = hItem.getLong("endDay", 0);
                status = ConfigStatus.reachEnumConstant(hItem.getInt("status", 0));
            }

            public long getConfigId() {
                return configId;
            }

            public long getStartDay() {
                return startDay;
            }

            public long getEndDay() {
                return endDay;
            }

            public ConfigStatus getStatus() {
                return status;
            }

        }

    }

}
