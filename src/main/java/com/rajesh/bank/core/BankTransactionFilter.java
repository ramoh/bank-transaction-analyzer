package com.rajesh.bank.core;

@FunctionalInterface
public interface BankTransactionFilter {

    boolean test(BankTransaction bankTransaction);
}
