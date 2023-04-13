package com.tecknobit.binancemanager.managers.signedmanagers.mining.records.lists.profit;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Profit extends BinanceItem {

    public enum ProfitStatus {

        unpaid(0),
        paying(1),
        paid(2);

        private final int status;

        /**
         * {@code VALUES} list of the types
         **/
        private static final List<ProfitStatus> VALUES = Arrays.stream(ProfitStatus.values()).toList();

        ProfitStatus(int status) {
            this.status = status;
        }

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

        @Override
        public String toString() {
            return status + "";
        }

    }

    protected final long time;
    protected final int type;
    protected final double profitAmount;
    protected final String coinName;
    protected final ProfitStatus status;

    public Profit(long time, int type, double profitAmount, String coinName, ProfitStatus status) {
        super(null);
        this.time = time;
        this.type = type;
        this.profitAmount = profitAmount;
        this.coinName = coinName;
        this.status = status;
    }

    public Profit(JSONObject jProfit) {
        super(jProfit);
        time = hItem.getLong("time", 0);
        type = hItem.getInt("type", 0);
        profitAmount = hItem.getDouble("profitAmount", 0);
        coinName = hItem.getString("coinName");
        status = ProfitStatus.reachEnumConstant(hItem.getInt("status", 0));
    }

    public long getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public double getProfitAmount() {
        return profitAmount;
    }

    public String getCoinName() {
        return coinName;
    }

    public ProfitStatus getStatus() {
        return status;
    }

}
