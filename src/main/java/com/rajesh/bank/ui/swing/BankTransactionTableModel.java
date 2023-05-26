package com.rajesh.bank.ui.swing;

import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

import com.rajesh.bank.core.BankTransaction;

class BankTransactionTableModel extends AbstractTableModel {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd MMM uuuu");
    private final BankTransaction[] bankTransactions;
    private String[] columnNames = new String[] { "Date", "Description", "Amount","Balance" };

    public BankTransactionTableModel(BankTransaction[] bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    @Override
    public int getRowCount() {
        return this.bankTransactions.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return this.bankTransactions[row].getDate().format(DATE_PATTERN);
            case 1:
                return this.bankTransactions[row].getDescription();
            case 2:
                return this.bankTransactions[row].getAmount();
            case 3:
                return this.bankTransactions[row].getBalance();    
            default:
                return null;
        }
    }

}
