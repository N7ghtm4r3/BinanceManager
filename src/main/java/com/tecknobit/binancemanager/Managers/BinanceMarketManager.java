package com.tecknobit.binancemanager.Managers;

import com.tecknobit.binancemanager.Exceptions.SystemException;

import java.io.IOException;

public class BinanceMarketManager extends BinanceManager{

    public BinanceMarketManager(String baseEndpoint) throws SystemException, IOException {
        super(baseEndpoint);
    }

    //Asset Detail (USER_DATA)
    //Trade Fee (USER_DATA)

}
