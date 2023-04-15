package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.BinanceManager.BinanceResponse;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.DataListItem;
import com.tecknobit.binancemanager.managers.signedmanagers.mining.records.MiningResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;
import static com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.userdata.MiningAccountEarning.AccountEarningDetails;

/**
 * The {@code MiningAccountEarning} class is useful to create a mining account earning
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#mining-account-earning-user_data">
 * Mining Account Earning (USER_DATA)</a>
 * @see BinanceItem
 * @see BinanceResponse
 * @see MiningResponse
 **/
public class MiningAccountEarning extends MiningResponse<AccountEarningDetails> {

    /**
     * Constructor to init {@link MiningAccountEarning} object
     *
     * @param data: mining account earning
     **/
    public MiningAccountEarning(AccountEarningDetails data) {
        super(data);
    }

    /**
     * Constructor to init {@link MiningAccountEarning} object
     *
     * @param jMiningAccountEarning: mining account earning details as {@link JSONObject}
     **/
    public MiningAccountEarning(JSONObject jMiningAccountEarning) {
        super(jMiningAccountEarning);
        JSONObject jData = hItem.getJSONObject("data");
        if (jData != null)
            data = new AccountEarningDetails(jData);
        else
            data = null;
    }

    /**
     * The {@code AccountEarningDetails} class is useful to create an account earning details
     *
     * @author N7ghtm4r3 - Tecknobit
     * @see BinanceItem
     * @see DataListItem
     **/
    public static class AccountEarningDetails extends DataListItem {

        /**
         * {@code accountProfits} account profits list
         **/
        private final ArrayList<AccountEarning> accountProfits;

        /**
         * Constructor to init {@link AccountEarningDetails} object
         *
         * @param totalNum: total num of the account profits
         * @param pageSize: page size of the account profits
         * @param accountProfits: account profits list
         **/
        public AccountEarningDetails(int totalNum, int pageSize, ArrayList<AccountEarning> accountProfits) {
            super(totalNum, pageSize);
            this.accountProfits = accountProfits;
        }

        /**
         * Constructor to init {@link AccountEarningDetails} object
         *
         * @param jAccountEarningDetails: account earning details as {@link JSONObject}
         **/
        public AccountEarningDetails(JSONObject jAccountEarningDetails) {
            super(jAccountEarningDetails);
            accountProfits = new ArrayList<>();
            ArrayList<JSONObject> jList = hItem.fetchList("accountProfits");
            if (jList != null)
                for (JSONObject earning : jList)
                    accountProfits.add(new AccountEarning(earning));
        }

        /**
         * Method to get {@link #accountProfits} instance <br>
         * No-any params required
         *
         * @return {@link #accountProfits} instance as {@link ArrayList} of {@link AccountEarning}
         **/
        public ArrayList<AccountEarning> getAccountProfits() {
            return accountProfits;
        }

        /**
         * The {@code AccountEarning} class is useful to create an account earning
         *
         * @author N7ghtm4r3 - Tecknobit
         * @see BinanceItem
         **/
        public static class AccountEarning extends BinanceItem {

            /**
             * {@code EarningType} list of available earning types
             **/
            public enum EarningType {

                /**
                 * {@code Referral} earning type
                 **/
                Referral(0),

                /**
                 * {@code Refund} earning type
                 **/
                Refund(1),

                /**
                 * {@code Rebate} earning type
                 **/
                Rebate(2);

                /**
                 * {@code type} earning type value
                 **/
                private final int type;

                /**
                 * {@code VALUES} list of the types
                 **/
                private static final List<EarningType> VALUES = Arrays.stream(EarningType.values()).toList();

                /**
                 * Constructor to init {@link EarningType} object
                 *
                 * @param type: earning type value
                 **/
                EarningType(int type) {
                    this.type = type;
                }

                /**
                 * Method to get {@link #type} instance <br>
                 * No-any params required
                 *
                 * @return {@link #type} instance as int
                 **/
                public int getType() {
                    return type;
                }

                /**
                 * Method to reach the enum constant <br>
                 *
                 * @param value: value to reach
                 * @return enum constant as {@link EarningType}
                 **/
                public static EarningType reachEnumConstant(int value) {
                    return VALUES.get(value);
                }

                /**
                 * Method to get {@link #type} instance <br>
                 * No-any params required
                 *
                 * @return {@link #type} instance as {@link String}
                 **/
                @Override
                public String toString() {
                    return type + "";
                }

            }

            /**
             * {@code time} of the account earning
             **/
            private final long time;

            /**
             * {@code coinName} coin
             **/
            private final String coinName;

            /**
             * {@code type} of the account earning
             **/
            private final EarningType type;

            /**
             * {@code puid} sub-account id
             **/
            private final long puid;

            /**
             * {@code subName} mining account
             **/
            private final String subName;

            /**
             * {@code amount} value
             **/
            private final double amount;

            /**
             * Constructor to init {@link AccountEarning} object
             *
             * @param totalNum: total num of the account profits
             * @param pageSize: page size of the account profits
             * @param time: time of the account earning
             * @param coinName: coin
             * @param type: type of the account earning
             * @param puid: sub-account id
             * @param subName: mining account
             * @param amount: amount value
             **/
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

            /**
             * Constructor to init {@link AccountEarning} object
             *
             * @param jAccountEarning: account earning details as {@link JSONObject}
             **/
            public AccountEarning(JSONObject jAccountEarning) {
                super(jAccountEarning);
                time = hItem.getLong("time", 0);
                coinName = hItem.getString("coinName");
                type = EarningType.reachEnumConstant(hItem.getInt("type", 0));
                puid = hItem.getLong("puid", 0);
                subName = hItem.getString("subName");
                amount = hItem.getDouble("amount", 0);
            }

            /**
             * Method to get {@link #time} instance <br>
             * No-any params required
             *
             * @return {@link #time} instance as long
             **/
            public long getTime() {
                return time;
            }

            /**
             * Method to get {@link #time} instance <br>
             * No-any params required
             *
             * @return {@link #time} instance as {@link Date}
             **/
            public Date getDate() {
                return TimeFormatter.getDate(time);
            }

            /**
             * Method to get {@link #coinName} instance <br>
             * No-any params required
             *
             * @return {@link #coinName} instance as {@link String}
             **/
            public String getCoinName() {
                return coinName;
            }

            /**
             * Method to get {@link #type} instance <br>
             * No-any params required
             *
             * @return {@link #type} instance as {@link EarningType}
             **/
            public EarningType getType() {
                return type;
            }

            /**
             * Method to get {@link #puid} instance <br>
             * No-any params required
             *
             * @return {@link #puid} instance as long
             **/
            public long getPuid() {
                return puid;
            }

            /**
             * Method to get {@link #subName} instance <br>
             * No-any params required
             *
             * @return {@link #subName} instance as {@link String}
             **/
            public String getSubName() {
                return subName;
            }

            /**
             * Method to get {@link #amount} instance <br>
             * No-any params required
             *
             * @return {@link #amount} instance as double
             **/
            public double getAmount() {
                return amount;
            }

            /**
             * Method to get {@link #amount} instance
             *
             * @param decimals: number of digits to round final value
             * @return {@link #amount} instance rounded with decimal digits inserted
             * @throws IllegalArgumentException if decimalDigits is negative
             **/
            public double getAmount(int decimals) {
                return roundValue(amount, decimals);
            }

        }

    }

}
