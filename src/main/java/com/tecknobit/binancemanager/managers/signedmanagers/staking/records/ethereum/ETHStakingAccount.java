package com.tecknobit.binancemanager.managers.signedmanagers.staking.records.ethereum;

import com.tecknobit.binancemanager.managers.records.BinanceItem;
import org.json.JSONObject;

public class ETHStakingAccount extends BinanceItem {

    private final double cumulativeProfitInBETH;

    private final double lastDayProfitInBETH;

    public ETHStakingAccount(double cumulativeProfitInBETH, double lastDayProfitInBETH) {
        super(null);
        this.cumulativeProfitInBETH = cumulativeProfitInBETH;
        this.lastDayProfitInBETH = lastDayProfitInBETH;
    }

    public ETHStakingAccount(JSONObject jETHStakingAccount) {
        super(jETHStakingAccount);
        this.cumulativeProfitInBETH = hItem.getDouble("cumulativeProfitInBETH", 0);
        this.lastDayProfitInBETH = hItem.getDouble("lastDayProfitInBETH", 0);
    }

    public double getCumulativeProfitInBETH() {
        return cumulativeProfitInBETH;
    }

    public double getLastDayProfitInBETH() {
        return lastDayProfitInBETH;
    }

}
