package com.tecknobit.binancemanager.Managers.SignedManagers.Wallet.Records.Dust;

/**
 *  The {@code UniversalTransfer} class is useful to manage all UniversalTransfer Binance requests
 *  @apiNote see the official documentation at: <a href="https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data">
 *      https://binance-docs.github.io/apidocs/spot/en/#user-universal-transfer-user_data</a>
 * **/

public class UniversalTransfer {

    /**
     * {@code MAIN_UMFUTURE} is constant for MAIN_UMFUTURE transfer's type
     * **/
    public static final String MAIN_UMFUTURE = "MAIN_UMFUTURE";

    /**
     * {@code MAIN_CMFUTURE} is constant for MAIN_CMFUTURE transfer's type
     * **/
    public static final String MAIN_CMFUTURE = "MAIN_CMFUTURE";

    /**
     * {@code MAIN_MARGIN} is constant for MAIN_MARGIN transfer's type
     * **/
    public static final String MAIN_MARGIN = "MAIN_MARGIN";

    /**
     * {@code UMFUTURE_MAIN} is constant for UMFUTURE_MAIN transfer's type
     * **/
    public static final String UMFUTURE_MAIN = "UMFUTURE_MAIN";

    /**
     * {@code UMFUTURE_MARGIN} is constant for UMFUTURE_MARGIN transfer's type
     * **/
    public static final String UMFUTURE_MARGIN = "UMFUTURE_MARGIN";

    /**
     * {@code CMFUTURE_MAIN} is constant for CMFUTURE_MAIN transfer's type
     * **/
    public static final String CMFUTURE_MAIN = "CMFUTURE_MAIN";

    /**
     * {@code CMFUTURE_MARGIN} is constant for CMFUTURE_MARGIN transfer's type
     * **/
    public static final String CMFUTURE_MARGIN = "CMFUTURE_MARGIN";

    /**
     * {@code MARGIN_MAIN} is constant for MARGIN_MAIN transfer's type
     * **/
    public static final String MARGIN_MAIN = "MARGIN_MAIN";

    /**
     * {@code MARGIN_UMFUTURE} is constant for MARGIN_UMFUTURE transfer's type
     * **/
    public static final String MARGIN_UMFUTURE = "MARGIN_UMFUTURE";

    /**
     * {@code MARGIN_CMFUTURE} is constant for MARGIN_CMFUTURE transfer's type
     * **/
    public static final String MARGIN_CMFUTURE = "MARGIN_CMFUTURE";

    /**
     * {@code ISOLATEDMARGIN_MARGIN} is constant for ISOLATEDMARGIN_MARGIN transfer's type
     * **/
    public static final String ISOLATEDMARGIN_MARGIN = "ISOLATEDMARGIN_MARGIN";

    /**
     * {@code MARGIN_ISOLATEDMARGIN} is constant for MARGIN_ISOLATEDMARGIN transfer's type
     * **/
    public static final String MARGIN_ISOLATEDMARGIN = "MARGIN_ISOLATEDMARGIN";

    /**
     * {@code ISOLATEDMARGIN_ISOLATEDMARGIN} is constant for ISOLATEDMARGIN_ISOLATEDMARGIN transfer's type
     * **/
    public static final String ISOLATEDMARGIN_ISOLATEDMARGIN = "ISOLATEDMARGIN_ISOLATEDMARGIN";

    /**
     * {@code MAIN_FUNDING} is constant for MAIN_FUNDING transfer's type
     * **/
    public static final String MAIN_FUNDING = "MAIN_FUNDING";

    /**
     * {@code FUNDING_MAIN} is constant for FUNDING_MAIN transfer's type
     * **/
    public static final String FUNDING_MAIN = "FUNDING_MAIN";

    /**
     * {@code FUNDING_UMFUTURE} is constant for FUNDING_UMFUTURE transfer's type
     * **/
    public static final String FUNDING_UMFUTURE = "MAIN_UMFUTURE";

    /**
     * {@code UMFUTURE_FUNDING} is constant for UMFUTURE_FUNDING transfer's type
     * **/
    public static final String UMFUTURE_FUNDING = "UMFUTURE_FUNDING";

    /**
     * {@code MARGIN_FUNDING} is constant for MARGIN_FUNDING transfer's type
     * **/
    public static final String MARGIN_FUNDING = "MARGIN_FUNDING";

    /**
     * {@code FUNDING_MARGIN} is constant for FUNDING_MARGIN transfer's type
     * **/
    public static final String FUNDING_MARGIN = "FUNDING_MARGIN";

    /**
     * {@code FUNDING_CMFUTURE} is constant for FUNDING_CMFUTURE transfer's type
     * **/
    public static final String FUNDING_CMFUTURE = "FUNDING_CMFUTURE";

    /**
     * {@code CMFUTURE_FUNDING} is constant for CMFUTURE_FUNDING transfer's type
     * **/
    public static final String CMFUTURE_FUNDING = "CMFUTURE_FUNDING";

    /**
     * {@code asset} is instance that memorizes asset value
     * **/
    private final String asset;

    /**
     * {@code amount} is instance that memorizes amount value
     * **/
    private final double amount;

    /**
     * {@code type} is instance that memorizes type value
     * **/
    private final String type;

    /**
     * {@code status} is instance that memorizes status value
     * **/
    private final String status;

    /**
     * {@code tranId} is instance that memorizes transaction identifier value
     * **/
    private final long tranId;

    /**
     * {@code timestamp} is instance that memorizes timestamp value
     * **/
    private final long timestamp;

    /** Constructor to init {@link UniversalTransfer} object
     * @param asset: asset value
     * @param amount: amount value
     * @param type: type value
     * @param status: status value
     * @param tranId: transaction identifier value
     * @param timestamp: timestamp value
     * **/
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

    @Override
    public String toString() {
        return "UniversalTransfer{" +
                "asset='" + asset + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", tranId=" + tranId +
                ", timestamp=" + timestamp +
                '}';
    }

}
