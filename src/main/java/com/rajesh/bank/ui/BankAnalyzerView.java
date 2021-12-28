package com.rajesh.bank.ui;

import java.util.List;

import com.rajesh.bank.core.BankTransaction;

public interface BankAnalyzerView {

    public void setBankTransactions(List<BankTransaction> bankTranasction);

    public void clearSearch();

    public void search();

    public void uploadFile();

}
