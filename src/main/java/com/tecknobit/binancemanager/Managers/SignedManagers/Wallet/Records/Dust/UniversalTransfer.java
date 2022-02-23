package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

/**
 *  The {@code UniversalTransfer} class is useful to manage all UniversalTransfer Binance requests
 *  @apiNote see official documentation at: https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data
 * **/

public class UniversalTransfer {

    /**
     * All types paramaters for all Transfers Binance requests
     * **/

    public static final String MAIN_UMFUTURE = "MAIN_UMFUTURE";
    public static final String MAIN_CMFUTURE = "MAIN_CMFUTURE";
    public static final String MAIN_MARGIN = "MAIN_MARGIN";
    public static final String UMFUTURE_MAIN = "UMFUTURE_MAIN";
    public static final String UMFUTURE_MARGIN = "UMFUTURE_MARGIN";
    public static final String CMFUTURE_MAIN = "CMFUTURE_MAIN";
    public static final String CMFUTURE_MARGIN = "CMFUTURE_MARGIN";
    public static final String MARGIN_MAIN = "MARGIN_MAIN";
    public static final String MARGIN_UMFUTURE = "MARGIN_UMFUTURE";
    public static final String MARGIN_CMFUTURE = "MARGIN_CMFUTURE";
    public static final String ISOLATEDMARGIN_MARGIN = "ISOLATEDMARGIN_MARGIN";
    public static final String MARGIN_ISOLATEDMARGIN = "MARGIN_ISOLATEDMARGIN";
    public static final String ISOLATEDMARGIN_ISOLATEDMARGIN = "ISOLATEDMARGIN_ISOLATEDMARGIN";
    public static final String MAIN_FUNDING = "MAIN_FUNDING";
    public static final String FUNDING_MAIN = "FUNDING_MAIN";
    public static final String FUNDING_UMFUTURE = "MAIN_UMFUTURE";
    public static final String UMFUTURE_FUNDING = "UMFUTURE_FUNDING";
    public static final String MARGIN_FUNDING = "MARGIN_FUNDING";
    public static final String FUNDING_MARGIN = "FUNDING_MARGIN";
    public static final String FUNDING_CMFUTURE = "FUNDING_CMFUTURE";
    public static final String CMFUTURE_FUNDING = "CMFUTURE_FUNDING";
    private final String asset;
    private final double amount;
    private final String type;
    private final String status;
    private final long tranId;
    private final long timestamp;

    public UniversalTransfer(String asset, double amount, String type, String status, long tranId, long timestamp) {
        this.asset = asset;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.tranId = tranId;
        this.timestamp = timestamp;
    }

    public String asset() {
        return asset;
    }

    public double amount() {
        return amount;
    }

    public String type() {
        return type;
    }

    public String status() {
        return status;
    }

    public long tranId() {
        return tranId;
    }

    public long timestamp() {
        return timestamp;
    }

}
