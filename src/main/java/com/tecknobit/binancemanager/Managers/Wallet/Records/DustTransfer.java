package com.tecknobit.binancemanager.Managers.Wallet.Records;

import java.util.ArrayList;

public record DustTransfer (double totalServiceCharge, double totalTransfered, ArrayList<TransferResult> transferResults) {


    public record TransferResult () {

    }

}
