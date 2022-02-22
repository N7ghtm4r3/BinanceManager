package com.tecknobit.binancemanager.Exceptions;

/**
 *  The {@code SystemException} class is useful to catch if Binance API service is working or not,
 *  it throws an exception if service is not working.
 * **/


public class SystemException extends Exception{

    public SystemException() {
        super("Service unavailable because Binance system is in maintenance");
    }

}
