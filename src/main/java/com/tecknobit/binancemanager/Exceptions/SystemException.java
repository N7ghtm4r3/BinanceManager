package com.tecknobit.binancemanager.Exceptions;

public class SystemException extends Exception{

    /**
     *  The {@code SystemException} class is useful to catch if Binance API service is working or not,
     *  it throws an exception if service is not working.
     * **/

    public SystemException() {
        super("Service unavailable because Binance system is in maintenance");
    }

}
