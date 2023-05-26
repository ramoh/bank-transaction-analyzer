package com.rajesh.bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCSVParser implements BankStatementParser {

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yy");

    @Override
    public BankTransaction parseFrom(String line) {
        final String[] columns = line.split(",");
        final boolean credit = Double.parseDouble(columns[4]) > 0.00 ? true : false;
        BankTransactionValidator validator = new BankTransactionValidator(columns[1], columns[0],
                credit ? columns[4] : columns[3],columns[6]);
        Notification notification = validator.validate();
        if (notification.hasError()) {
            throw new IllegalStateException(notification.errorMessage());
        }
        final double amount = credit ? Double.parseDouble(columns[4]) : Double.parseDouble(columns[3]) * -1;
        final String description = columns[1].trim();
        final LocalDate date = LocalDate.parse(columns[0].trim(), DATE_PATTERN);
        final double balance = Double.parseDouble(columns[6]);
        return new BankTransaction(date, amount, description,balance);
    }

    @Override
    public List<BankTransaction> parseLinesFrom(List<String> lines) {
        final List<BankTransaction> bankTransactions = new ArrayList<>();
        for (final String line : lines) {
            bankTransactions.add(parseFrom(line));
        }
        return bankTransactions;
    }
}
