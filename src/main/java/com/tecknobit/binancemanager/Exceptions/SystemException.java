package com.tecknobit.binancemanager.Exceptions;

public class SystemException extends Exception{

    public SystemException() {
        super("Service unavailable because Binance system is in maintenance");
    }

}
