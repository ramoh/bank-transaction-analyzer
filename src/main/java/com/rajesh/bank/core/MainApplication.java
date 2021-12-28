package com.rajesh.bank.core;

import java.io.IOException;

public class MainApplication {
    private static final String RESOURCES = "src/main/resources/";

    public static void main(String[] args) throws IOException {
        final BankTransactionAnalyzer bankStatementAnalyzer = new BankTransactionAnalyzer();
        final BankStatementParser bankStatementParser = new BankStatementCSVParser();
        final Exporter exporter = new HtmlExporter();

        String filePath;
        if (args.length > 0 && args[0] != null) {
            filePath = args[0];
        } else {
            filePath = RESOURCES + "hdfc.csv";
        }

        bankStatementAnalyzer.analyze(filePath, bankStatementParser, exporter);
    }
}
