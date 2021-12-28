package com.rajesh.bank.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

public class BankTransactionAnalyzer {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void analyze(final String filePath, BankStatementParser parser, Exporter exporter) throws IOException {
        final Path path = Paths.get(filePath);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = parser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);
        final SummaryStatistics statistics = bankStatementProcessor.summarizeTransactions();

        collectSummary(bankStatementProcessor);
        System.out.println(exporter.export(statistics));
    }

    private void collectSummary(BankStatementProcessor bankStatementProcessor) {
        System.out.println(
                String.format("The total for all transactions is %.2f", bankStatementProcessor.calculateTotalAmount()));

        System.out.println(String.format("The total for transactions in November is %.2f",
                bankStatementProcessor.calculateTotalInMonth(Month.NOVEMBER)));
        System.out.println(String.format("The total for transactions in October is %.2f",
                bankStatementProcessor.calculateTotalInMonth(Month.OCTOBER)));

        final String searchTerm = "ORA SAL";

        System.out.println(String.format("Search Term: %s and total is  %.2f", searchTerm,
                bankStatementProcessor.calculateTotalForCategory(searchTerm)));
        String color = ANSI_RESET;

        for (final BankTransaction transaction : bankStatementProcessor
                .findTransactions(bankTransaction -> bankTransaction.getDescription().contains(searchTerm))) {

            if (transaction.getAmount() > 0) {
                color = ANSI_GREEN;
            } else if (transaction.getAmount() < -2000.00)
                color = ANSI_RED;
            System.out.println(color);
            System.out.println(transaction);
            color = ANSI_RESET;

        }
        System.out.println(color);
        System.out.println("\nSearching my criteria");

        List<BankTransaction> result = bankStatementProcessor
                .findTransactions(bankTransaction -> bankTransaction.getDate().getMonth() == Month.NOVEMBER
                        && Math.abs(bankTransaction.getAmount()) > 2000);
        result.forEach(item -> System.out.println(item));
    }

}
