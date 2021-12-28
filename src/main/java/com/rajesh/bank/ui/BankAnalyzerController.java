package com.rajesh.bank.ui;

import java.io.IOException;
import java.time.Month;

public interface BankAnalyzerController {

    public void readCSVFile(String absolutePath) throws IOException;

    public void search(Month byMonth, String byDesc, Double greaterThanAmount);

    public boolean canBeAnalysed();
}
