package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit.Profit;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.hashrateresale.HashrateResaleList.HashrateResale;

/**
 * The {@code HashrateResaleList} class is useful to create a hash rate resale list
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#hashrate-resale-list-user_data">
 * Hashrate Resale List (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceManager.BinanceResponse
 * @see MiningResponse
 */
public class HashrateResaleList extends MiningResponse<HashrateResale> {

    /**
     * Constructor to init {@link HashrateResaleList} object
     *
     * @param data: hash rate resale list
     */
    public HashrateResaleList(HashrateResale data) {
        super(data);
    }

    /**
     * Constructor to init {@link HashrateResaleList} object
     *
     * @param jHashrateResaleList: hash rate resale list details as {@link JSONObject}
     */
    public HashrateResaleList(JSONObject jHashrateResaleList) {
        super(jHashrateResaleList);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new HashrateResale(jData);
        else
            data = null;
    }

    /**
     * The {@code HashrateResale} class is useful to create a hash rate resale
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     */
    public static class HashrateResale extends DataListItem {

        /**
         * {@code configDetails} config details list
         */
        private final ArrayList<ConfigDetail> configDetails;

        /**
         * Constructor to init {@link HashrateResale} object
         *
         * @param totalNum: total num of the config details
         * @param pageSize: page size of the config details
         * @param configDetails: config details list
         */
        public HashrateResale(int totalNum, int pageSize, ArrayList<ConfigDetail> configDetails) {
            super(totalNum, pageSize);
            this.configDetails = configDetails;
        }

        /**
         * Constructor to init {@link HashrateResale} object
         *
         * @param jHashrateResale: hashrate resale details as {@link JSONObject}
         */
        public HashrateResale(JSONObject jHashrateResale) {
            super(jHashrateResale);
            configDetails = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("configDetails");
            if (jList != null)
                for (JSONObject config : jList)
                    configDetails.add(new ConfigDetail(config));
        }

        /**
         * Method to get {@link #configDetails} instance <br>
         * No-any params required
         *
         * @return {@link #configDetails} instance as {@link ArrayList} of {@link ConfigDetail}
         */
        public ArrayList<ConfigDetail> getConfigDetails() {
            return configDetails;
        }

        /**
         * The {@code ConfigDetail} class is useful to create a config detail
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         * @see HashrateResaleStructure
         */
        public static class ConfigDetail extends HashrateResaleStructure {

            /**
             * {@code configDetails} list of available config statuses
             */
            public enum ConfigStatus {

                /**
                 * {@code Processing} config status
                 */
                Processing(0),

                /**
                 * {@code Cancelled} config status
                 */
                Cancelled(1),

                /**
                 * {@code Terminated} config status
                 */
                Terminated(2);

                /**
                 * {@code status} config status value
                 */
                private final int status;

                /**
                 * {@code VALUES} list of the types
                 */
                private static final List<ConfigStatus> VALUES = Arrays.stream(ConfigStatus.values()).toList();

                /**
                 * Constructor to init {@link ConfigStatus} object
                 *
                 * @param status: config status value
                 */
                ConfigStatus(int status) {
                    this.status = status;
                }

                /**
                 * Method to get {@link #status} instance <br>
                 * No-any params required
                 *
                 * @return {@link #status} instance as int
                 */
                public int getStatus() {
                    return status;
                }

                /**
                 * Method to reach the enum constant <br>
                 *
                 * @param value: value to reach
                 * @return enum constant as {@link Profit.ProfitStatus}
                 */
                public static ConfigStatus reachEnumConstant(int value) {
                    return VALUES.get(value);
                }

                /**
                 * Method to get {@link #status} instance <br>
                 * No-any params required
                 *
                 * @return {@link #status} instance as {@link String}
                 */
                @Override
                public String toString() {
                    return status + "";
                }

            }

            /**
             * {@code configId} mining ID
             */
            private final long configId;

            /**
             * {@code startDay} start date
             */
            private final long startDay;

            /**
             * {@code endDay} end date
             */
            private final long endDay;

            /**
             * {@code status} config status value
             */
            private final ConfigStatus status;

            /**
             * Constructor to init {@link ConfigDetail} object
             *
             * @param poolUsername: transfer out of subaccount
             * @param toPoolUsername: transfer into subaccount
             * @param algoName: transfer algorithm
             * @param hashRate: transferred hashrate quantity
             * @param configId: mining ID
             * @param startDay: start date
             * @param endDay: end date
             * @param status: config status value
             */
            public ConfigDetail(String poolUsername, String toPoolUsername, String algoName, long hashRate, long configId,
                                long startDay, long endDay, ConfigStatus status) {
                super(poolUsername, toPoolUsername, algoName, hashRate);
                this.configId = configId;
                this.startDay = startDay;
                this.endDay = endDay;
                this.status = status;
            }

            /**
             * Constructor to init {@link ConfigDetail} object
             *
             * @param jConfigDetail: config detail as {@link JSONObject}
             */
            public ConfigDetail(JSONObject jConfigDetail) {
                super(jConfigDetail);
                configId = hItem.getLong("configId", 0);
                startDay = hItem.getLong("startDay", 0);
                endDay = hItem.getLong("endDay", 0);
                status = ConfigStatus.reachEnumConstant(hItem.getInt("status", 0));
            }

            /**
             * Method to get {@link #configId} instance <br>
             * No-any params required
             *
             * @return {@link #configId} instance as long
             */
            public long getConfigId() {
                return configId;
            }

            /**
             * Method to get {@link #startDay} instance <br>
             * No-any params required
             *
             * @return {@link #startDay} instance as long
             */
            public long getStartDay() {
                return startDay;
            }

            /**
             * Method to get {@link #startDay} instance <br>
             * No-any params required
             *
             * @return {@link #startDay} instance as {@link Date}
             */
            public Date getStartDayDate() {
                return TimeFormatter.getDate(startDay);
            }

            /**
             * Method to get {@link #endDay} instance <br>
             * No-any params required
             *
             * @return {@link #endDay} instance as long
             */
            public long getEndDay() {
                return endDay;
            }

            /**
             * Method to get {@link #endDay} instance <br>
             * No-any params required
             *
             * @return {@link #endDay} instance as {@link Date}
             */
            public Date getEndDayDate() {
                return TimeFormatter.getDate(endDay);
            }

            /**
             * Method to get {@link #status} instance <br>
             * No-any params required
             *
             * @return {@link #status} instance as {@link ConfigStatus}
             */
            public ConfigStatus getStatus() {
                return status;
            }

        }

    }

}
