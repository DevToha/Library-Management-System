package gui;

import service.LibraryService;
import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportPanel extends JPanel {
    private LibraryService service;
    private DefaultTableModel tableModel;

    public ReportPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("Refresh Audit Log");
        headerPanel.add(refreshButton);

        add(headerPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"TX ID", "Member ID", "Item ID", "Issue Date", "Return Date"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refreshTable());

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Transaction tx : service.getAllTransactions()) {
            tableModel.addRow(new Object[]{
                    tx.getTransactionId(),
                    tx.getMemberId(),
                    tx.getItemId(),
                    tx.getIssueDate(),
                    tx.getReturnDate() == null ? "Active (Not Returned)" : tx.getReturnDate()
            });
        }
    }
}k