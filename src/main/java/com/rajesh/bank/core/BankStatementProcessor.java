package com.rajesh.bank.core;

import java.time.Month;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class BankStatementProcessor {

    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public SummaryStatistics summarizeTransactions() {
        final DoubleSummaryStatistics doubleSummaryStatistics = bankTransactions.stream()
                .mapToDouble(BankTransaction::getAmount)
                .summaryStatistics();

        return new SummaryStatistics(doubleSummaryStatistics.getCount(), doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getMin(), doubleSummaryStatistics.getAverage());
    }

    public double summarizeTransaction(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;
        for (final BankTransaction bankTransaction : bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }
        return result;
    }

    public double calculateTotalAmount() {
        return summarizeTransaction((acc, bankTransaction) -> acc + bankTransaction.getAmount());
    }

    public double calculateTotalForCategory(final String category) {
        return summarizeTransaction((acc, bankTransaction) -> bankTransaction.getDescription().contains(category)
                ? bankTransaction.getAmount() + acc
                : acc);
    }

    public double calculateTotalInMonth(Month month) {
        return summarizeTransaction((acc, bankTransaction) -> bankTransaction.getDate().getMonth() == month
                ? bankTransaction.getAmount() + acc
                : acc);
    }

    public List<BankTransaction> findTransactions(final BankTransactionFilter filter) {
        final List<BankTransaction> result = new ArrayList<>();

        for (BankTransaction bankTransaction : bankTransactions) {
            if (filter.test(bankTransaction)) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTranactionGreaterThanEqualTo(final double amount) {
        return findTransactions(bankTransaction -> Math.abs(bankTransaction.getAmount()) >= amount);
    }
}
