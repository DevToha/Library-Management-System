package gui;

import model.Transaction;
import service.LibraryService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class ReportPanel extends JPanel {

    private LibraryService service;
    private DefaultTableModel tableModel;

    private static final Color HEADER_BG = new Color(37, 49, 89);
    private static final Color BORDER_COLOR = new Color(218, 225, 236);
    private static final Color FIELD_BORDER = new Color(205, 212, 224);
    private static final Color CARD_BORDER = new Color(218, 225, 236);
    private static final Color TITLE_COLOR = new Color(37, 49, 89);
    private static final Color LABEL_COLOR = new Color(110, 120, 140);

    public ReportPanel(LibraryService service) {
        this.service = service;

        setBackground(new Color(243, 245, 249));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(HEADER_BG);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        int totalItems = service.getAllItems().size();
        int totalMembers = service.getAllMembers().size();

        int issued = 0;
        int returned = 0;
        for (Transaction txn : service.getAllTransactions()) {
            if (txn.getStatus().equals("Issued")) {
                issued++;
            } else if (txn.getStatus().equals("Returned")) {
                returned++;
            }
        }

        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 14, 0));
        summaryPanel.setOpaque(false);

        summaryPanel.add(infoCard("Total Items", String.valueOf(totalItems), new Color(44, 102, 191)));
        summaryPanel.add(infoCard("Total Members", String.valueOf(totalMembers), new Color(40, 145, 80)));
        summaryPanel.add(infoCard("Issued", String.valueOf(issued), new Color(200, 70, 70)));
        summaryPanel.add(infoCard("Returned", String.valueOf(returned), new Color(240, 150, 40)));

        JButton exportButton = createButton("Export to File", new Color(44, 102, 191));
        exportButton.setPreferredSize(new Dimension(140, 34));
        exportButton.addActionListener(e -> exportReport());

        JPanel topPanel = new JPanel(new BorderLayout(0, 16));
        topPanel.setOpaque(false);
        topPanel.add(summaryPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(exportButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel tableCard = new JPanel();
        tableCard.setBackground(Color.WHITE);
        tableCard.setLayout(new BorderLayout(0, 12));
        tableCard.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                "Transaction History",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                HEADER_BG
        ));

        JPanel inner = new JPanel(new BorderLayout(0, 12));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(8, 14, 14, 14));

        tableModel = new DefaultTableModel(
                new Object[]{"Txn ID", "Item ID", "Member ID", "Issue Date", "Return Date", "Status"}, 0
        );

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(232, 236, 242));
        table.setSelectionBackground(new Color(220, 230, 246));

        JScrollPane tableScroll = new JScrollPane(table);
        table.setBackground(Color.WHITE);
        tableScroll.getViewport().setBackground(Color.WHITE);
        tableScroll.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 1));

        inner.add(tableScroll, BorderLayout.CENTER);
        tableCard.add(inner, BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(tableCard, BorderLayout.SOUTH);

        loadTransactions();
    }

    private JPanel infoCard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(CARD_BORDER, 1),
                        BorderFactory.createEmptyBorder(18, 16, 18, 16)
                )
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titleLabel.setForeground(LABEL_COLOR);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(TITLE_COLOR);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JButton createButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void exportReport() {
        try {
            FileWriter writer = new FileWriter("data/report.txt");

            writer.write("Library Management System Report\n");
            writer.write("================================\n\n");

            writer.write("Total Items: " + service.getAllItems().size() + "\n");
            writer.write("Total Members: " + service.getAllMembers().size() + "\n");

            int issued = 0;
            int returned = 0;
            for (Transaction txn : service.getAllTransactions()) {
                if (txn.getStatus().equals("Issued")) {
                    issued++;
                } else if (txn.getStatus().equals("Returned")) {
                    returned++;
                }
            }

            writer.write("Issued: " + issued + "\n");
            writer.write("Returned: " + returned + "\n\n");

            writer.write("Transactions:\n");
            for (Transaction txn : service.getAllTransactions()) {
                writer.write(txn.getTransactionId() + " | " + txn.getItemId() + " | "
                        + txn.getMemberId() + " | " + txn.getIssueDate() + " | "
                        + txn.getReturnDate() + " | " + txn.getStatus() + "\n");
            }

            writer.close();

            JOptionPane.showMessageDialog(this, "Report exported to data/report.txt");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);

        for (Transaction txn : service.getAllTransactions()) {
            tableModel.addRow(new Object[]{
                    txn.getTransactionId(),
                    txn.getItemId(),
                    txn.getMemberId(),
                    txn.getIssueDate(),
                    txn.getReturnDate(),
                    txn.getStatus()
            });
        }
    }
}