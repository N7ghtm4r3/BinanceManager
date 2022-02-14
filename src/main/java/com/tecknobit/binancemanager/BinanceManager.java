package com.tecknobit.binancemanager;

import java.util.ArrayList;
import static java.util.Arrays.asList;

public class BinanceManager{

    //https://binance-docs.github.io/apidocs/spot/en/#general-api-information
    public static final ArrayList<String> BASE_ENDPOINTS = new ArrayList<>(asList("https://api.binance.com",
            "https://api1.binance.com","https://api2.binance.com","https://api3.binance.com"));
    private final String apiKey;
    private final String secretKey;

    public BinanceManager(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBaseEndpoint(int numberEndpoint){
        return BASE_ENDPOINTS.get(numberEndpoint);
    }

}
