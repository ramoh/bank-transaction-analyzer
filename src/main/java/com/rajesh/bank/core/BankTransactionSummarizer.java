package com.rajesh.bank.core;

@FunctionalInterface
public interface BankTransactionSummarizer {

    double summarize(double accumulator, BankTransaction bankTransaction);
}
