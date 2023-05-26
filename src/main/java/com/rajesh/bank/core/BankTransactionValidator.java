package com.rajesh.bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class BankTransactionValidator {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yy");

    private final String description;
    private final String date;
    private final String amount;
    private final String balance;

    public BankTransactionValidator(String description, String date, String amount,String balance) {
        this.description = Objects.requireNonNull(description.trim());
        this.date = Objects.requireNonNull(date.trim());
        this.amount = Objects.requireNonNull(amount.trim());
        this.balance = Objects.requireNonNull(balance.trim());
    }

    public Notification validate() {
        final Notification notification = new Notification();
        if (this.description.length() > 200) {
            notification.addError("The description is too long: '" + this.description + "'");
        }

        final LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(this.date, DATE_PATTERN);
            if (parsedDate.isAfter(LocalDate.now())) {
                notification.addError("date cannot be in the future: '" + this.date + " '");
            }
        } catch (DateTimeParseException e) {
            notification.addError("Invalid format for date: '" + this.date + ".");
        }

        try {
            Double.parseDouble(this.amount);
        } catch (NumberFormatException e) {
            notification.addError("Invalid format for amount: '" + this.amount + "'");
        }

        try {
            Double.parseDouble(this.balance);
        } catch (NumberFormatException e) {
            notification.addError("Invalid format for amount: '" + this.amount + "'");
        }
        return notification;
    }

}
