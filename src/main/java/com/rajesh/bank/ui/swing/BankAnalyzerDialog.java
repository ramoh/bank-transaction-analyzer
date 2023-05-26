package com.rajesh.bank.ui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import javax.lang.model.util.ElementScanner14;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.rajesh.bank.core.BankTransaction;
import com.rajesh.bank.ui.BankAnalyzerController;
import com.rajesh.bank.ui.BankAnalyzerView;

public class BankAnalyzerDialog implements BankAnalyzerView {

    private BankAnalyzerController bankAnalyzerController;
    private JTable table;
    private JTextField descField = new JTextField(null, 45);
    private JTextField amountField = new JTextField(null, 45);
    private JTextField monthField = new JTextField(null, 45);
    private JButton searchButton = new JButton("Search");
    private JButton clearSearchButton = new JButton("Clear");
    private JTextField filePath = new JTextField(null, 180);

    public void initialize(final BankAnalyzerController controller) {

        this.bankAnalyzerController = controller;
        setFonts();
        initializeTable();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel searchPanel = initializeSearchPanel();
        JScrollPane tablePanel = initializeTabelPanel();

        mainPanel.add(searchPanel);
        mainPanel.add(tablePanel);
        display("Bank Transactions Analyzer", mainPanel);
        this.setBankTransactions(Collections.emptyList());

    }

    private JScrollPane initializeTabelPanel() {
        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        return tablePanel;
    }

    private JPanel initializeSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JButton uploadButton = new JButton("Upload CSV");

        uploadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

        filePath.setEnabled(false);
        searchPanel.add(wrapComponents(uploadButton, filePath));
        searchPanel.add(wrapComponents(new JLabel("Description should contain: "), descField));
        searchPanel.add(wrapComponents(new JLabel("Month should be: "), monthField));
        searchPanel.add(wrapComponents(new JLabel("Amount should be equal to or greater than:"), amountField));
        searchPanel.add(wrapComponents(searchButton, clearSearchButton));

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        clearSearchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearSearch();

            }
        });

        return searchPanel;
    }

    private Component wrapComponents(Component... c) {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        panel.setLayout(boxLayout);
        for (Component item : c) {

            panel.add(item);
        }
        return panel;
    }

    private void initializeTable() {
        // initialize table
        this.table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // Alternate row color
                double amount = (double) getModel().getValueAt(row, 2);
                if (!isRowSelected(row)) {
                    c.setBackground(amount < 0 ? getBackground() : Color.green);
                    if (amount > 0)
                        c.setBackground(new Color(102, 255, 102));
                    else if (amount < -1500)
                        c.setBackground(new Color(255, 102, 102));
                    else
                        c.setBackground(getBackground());
                }

                return c;
            }
        };

        table.setRowHeight(36);
        table.setModel(getModel(Collections.emptyList()));
    }

    private void display(String title, Component c) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private BankTransactionTableModel getModel(List<BankTransaction> bankTransactions) {
        BankTransaction trans[] = bankTransactions.toArray(new BankTransaction[bankTransactions.size()]);
        return new BankTransactionTableModel(trans);
    }

    private void setFonts() {
        Font font = new Font("Dialog", Font.PLAIN, 18);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
    }

    @Override
    public void setBankTransactions(List<BankTransaction> bankTranasctions) {
        this.table.setModel(getModel(bankTranasctions));
        refreshTable(this.table);
        refreshSearchFields();

    }

    private void refreshSearchFields() {
        boolean flag = this.bankAnalyzerController.canBeAnalysed();
        this.searchButton.setEnabled(flag);
        this.clearSearchButton.setEnabled(flag);
        this.amountField.setEnabled(flag);
        this.descField.setEnabled(flag);
        this.monthField.setEnabled(flag);
    }

    private void refreshTable(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(1200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);

        DefaultTableCellRenderer amountRender = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, String.format("%9.2f", value), isSelected,
                        hasFocus, row, column);
                super.setHorizontalAlignment(JLabel.RIGHT);
                return c;
            }

        };
        table.getColumnModel().getColumn(2).setCellRenderer(amountRender);
        table.getColumnModel().getColumn(3).setCellRenderer(amountRender);
        DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(2).setHeaderRenderer(cellRenderer);

    }

    @Override
    public void clearSearch() {
        this.monthField.setText(null);
        this.amountField.setText(null);
        this.descField.setText(null);
        this.bankAnalyzerController.search(null, null, null);
    }

    @Override
    public void search() {
        String monthStr = this.monthField.getText();
        String descStr = this.descField.getText();
        String amountStr = this.amountField.getText();
        Month month = null;
        if (monthStr.length() > 0) {
            try {
                month = Month.valueOf(monthStr.toUpperCase());
            } catch (Exception ex) {
                month = null;
                this.monthField.setText(null);
            }
        }

        String desc = descStr.length() > 0 ? descStr : null;

        Double amount = null;
        if (amountStr.length() > 0) {
            try {
                amount = Double.valueOf(amountStr);
            } catch (NumberFormatException ex) {
                amount = null;
                this.amountField.setText(null);
            }

        }

        this.bankAnalyzerController.search(month, desc, amount);

    }

    @Override
    public void uploadFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select the bank's CSV file");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();

            try {
                this.bankAnalyzerController.readCSVFile(selectedFile.getAbsolutePath());
                this.filePath.setText(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                // file can not be read
                System.out.println("File can not be read");
            }
        }
    }

}
