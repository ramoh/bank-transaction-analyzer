package com.rajesh.bank.core;

import java.time.LocalDate;
import java.util.Objects;

public class BankTransaction {
    private final LocalDate date;
    private final double amount;
    private final String description;
    private final double balance;

    public BankTransaction(final LocalDate date, final double amount, final String description,final Double balance) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("BankTransaction {date=%s, amount=%.2f, description='%s', balance='%s'}", date, amount,
                description,balance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BankTransaction other = (BankTransaction) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
