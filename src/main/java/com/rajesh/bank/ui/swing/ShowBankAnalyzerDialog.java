package com.rajesh.bank.ui.swing;

import com.rajesh.bank.ui.BankAnalyzerModel;

public class ShowBankAnalyzerDialog {

    public static void main(String[] args) {
        BankAnalyzerDialog dialog = new BankAnalyzerDialog();
        BankAnalyzerModel model = new BankAnalyzerModel();
        model.initialize(dialog);
        dialog.initialize(model);
    }
}
