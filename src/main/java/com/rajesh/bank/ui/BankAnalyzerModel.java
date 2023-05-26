package com.rajesh.bank.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.rajesh.bank.core.BankStatementCSVParser;
import com.rajesh.bank.core.BankStatementParser;
import com.rajesh.bank.core.BankStatementProcessor;
import com.rajesh.bank.core.BankTransaction;

public class BankAnalyzerModel implements BankAnalyzerController {
    private BankAnalyzerView bankAnalyzerView;
    private BankStatementProcessor bankStatementProcessor;

    public void initialize(final BankAnalyzerView view) {
        this.bankAnalyzerView = view;

    }

    @Override
    public void search(final Month byMonth, final String byDesc, final Double greaterThanAmount) {
        if (canBeAnalysed()) {
            List<BankTransaction> result = new ArrayList<>();
            result.addAll(this.bankStatementProcessor.findTransactions(bankTranasction -> {
                boolean flag = true;
                if (byMonth != null) {
                    flag = bankTranasction.getDate().getMonth().equals(byMonth);
                }
                if (byDesc != null && byDesc.length() > 0 && flag) {
                    flag = bankTranasction.getDescription().toLowerCase().contains(byDesc.toLowerCase());
                }
                if (greaterThanAmount != null && flag) {
                    flag = Math.abs(bankTranasction.getAmount()) >= Math.abs(greaterThanAmount.doubleValue());
                }
                return flag;
            }));
            this.bankAnalyzerView.setBankTransactions(result);
        }
    }

    @Override
    public void readCSVFile(String absolutePath) {

        final List<BankTransaction> result = new ArrayList<>();
        final Path path = Paths.get(absolutePath);
        try {
            final List<String> lines = Files.readAllLines(path);
            final BankStatementParser parser = new BankStatementCSVParser();
            final List<BankTransaction> bankTransactions = parser.parseLinesFrom( lines.subList(2,lines.size()-1));
            this.bankStatementProcessor = new BankStatementProcessor(bankTransactions);
            result.addAll(bankTransactions);
        } catch (IOException e) {
            // Some IO Exception happened set the bank statement processor to null
            this.bankStatementProcessor = null;

        } finally {
            this.bankAnalyzerView.setBankTransactions(result);
        }
    }

    @Override
    public boolean canBeAnalysed() {
        return this.bankStatementProcessor != null;
    }

}
