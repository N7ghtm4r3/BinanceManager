package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.apimanager.formatters.TimeFormatter;
import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tecknobit.apimanager.trading.TradingTools.roundValue;

/**
 * The {@code Profit} class is useful to create a mining profit
 *
 * @author N7ghtm4r3 - Tecknobit
 * @see BinanceItem
 **/
public class Profit extends BinanceItem {

    /**
     * {@code ProfitType} list of available profit types
     **/
    public enum ProfitType {

        /**
         * {@code unpaid} profit type
         **/
        Mining_Wallet(0),

        /**
         * {@code paying} profit type
         **/
        Mining_Address(5),

        /**
         * {@code unpaid} profit type
         **/
        Pool_Savings(7),

        /**
         * {@code paying} profit type
         **/
        Transferred(8),

        /**
         * {@code unpaid} profit type
         **/
        Income_Transfer(31),

        /**
         * {@code paying} profit type
         **/
        Hashrate_Resale_Mining_Wallet(32),

        /**
         * {@code unpaid} profit type
         **/
        Hashrate_Resale_Pool_Savings(33);

        /**
         * {@code type} profit type value
         **/
        private final int type;

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<ProfitType> VALUES = Arrays.stream(ProfitType.values()).toList();

        /**
         * Constructor to init {@link ProfitType} object
         *
         * @param type: profit type value
         **/
        ProfitType(int type) {
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
         * @return enum constants as {@link ProfitType}
         **/
        public static ProfitType reachEnumConstant(int value) {
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
     * {@code ProfitStatus} list of available profit statuses
     **/
    public enum ProfitStatus {

        /**
         * {@code unpaid} profit type
         **/
        unpaid(0),

        /**
         * {@code paying} profit type
         **/
        paying(1),

        /**
         * {@code paid} profit type
         **/
        paid(2);

        /**
         * {@code type} profit type value
         **/
        private final int status;

        /**
         * {@code VALUES} list of the statuses
         **/
        private static final List<ProfitStatus> VALUES = Arrays.stream(ProfitStatus.values()).toList();

        /**
         * Constructor to init {@link ProfitStatus} object
         *
         * @param status: profit type value
         **/
        ProfitStatus(int status) {
            this.status = status;
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as int
         **/
        public int getStatus() {
            return status;
        }

        /**
         * Method to reach the enum constant <br>
         *
         * @param value: value to reach
         * @return enum constants as {@link ProfitStatus}
         **/
        public static ProfitStatus reachEnumConstant(int value) {
            return VALUES.get(value);
        }

        /**
         * Method to get {@link #status} instance <br>
         * No-any params required
         *
         * @return {@link #status} instance as {@link String}
         **/
        @Override
        public String toString() {
            return status + "";
        }

    }

    /**
     * {@code time} mining date
     **/
    protected final long time;

    /**
     * {@code type} of the profit
     **/
    protected final ProfitType type;

    /**
     * {@code profitAmount} earnings amount
     **/
    protected final double profitAmount;

    /**
     * {@code coinName} coin type
     **/
    protected final String coinName;

    /**
     * {@code type} profit type value
     **/
    protected final ProfitStatus status;

    /**
     * Constructor to init {@link Profit} object
     *
     * @param time:         mining date
     * @param type:         type of the profit
     * @param profitAmount: earnings amount
     * @param coinName:     coin type
     * @param status:       profit type value
     **/
    public Profit(long time, ProfitType type, double profitAmount, String coinName, ProfitStatus status) {
        super(null);
        this.time = time;
        this.type = type;
        this.profitAmount = profitAmount;
        this.coinName = coinName;
        this.status = status;
    }

    /**
     * Constructor to init {@link Profit} object
     *
     * @param jProfit: profit details as {@link JSONObject}
     **/
    public Profit(JSONObject jProfit) {
        super(jProfit);
        time = hItem.getLong("time", 0);
        type = ProfitType.reachEnumConstant(hItem.getInt("type", 0));
        profitAmount = hItem.getDouble("profitAmount", 0);
        coinName = hItem.getString("coinName");
        status = ProfitStatus.reachEnumConstant(hItem.getInt("type", 0));
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
     * Method to get {@link #type} instance <br>
     * No-any params required
     *
     * @return {@link #type} instance as {@link ProfitType}
     **/
    public ProfitType getType() {
        return type;
    }

    /**
     * Method to get {@link #profitAmount} instance <br>
     * No-any params required
     *
     * @return {@link #profitAmount} instance as double
     **/
    public double getProfitAmount() {
        return profitAmount;
    }

    /**
     * Method to get {@link #profitAmount} instance
     *
     * @param decimals: number of digits to round final value
     * @return {@link #profitAmount} instance rounded with decimal digits inserted
     * @throws IllegalArgumentException if decimalDigits is negative
     **/
    public double getProfitAmount(int decimals) {
        return roundValue(profitAmount, decimals);
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
     * Method to get {@link #status} instance <br>
     * No-any params required
     *
     * @return {@link #status} instance as {@link ProfitStatus}
     **/
    public ProfitStatus getStatus() {
        return status;
    }

}
