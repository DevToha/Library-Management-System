package gui;

import exception.NotAvailableException;
import exception.NotFoundException;
import model.Transaction;
import service.LibraryService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class IssueReturnPanel extends JPanel {

    private LibraryService service;
    private DefaultTableModel tableModel;

    private JTextField issueItemIdField;
    private JTextField issueMemberIdField;
    private JTextField returnItemIdField;

    private static final Color HEADER_BG = new Color(37, 49, 89);
    private static final Color BORDER_COLOR = new Color(218, 225, 236);
    private static final Color FIELD_BORDER = new Color(205, 212, 224);

    public IssueReturnPanel(LibraryService service) {
        this.service = service;

        setBackground(new Color(243, 245, 249));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        JLabel title = new JLabel("Issue / Return Items");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(HEADER_BG);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel mainPanel = new JPanel(new BorderLayout(18, 0));
        mainPanel.setOpaque(false);

        mainPanel.add(createFormPanel(), BorderLayout.WEST);
        mainPanel.add(createListPanel(), BorderLayout.CENTER);

        add(title, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        loadTransactions();
    }

    private JPanel createFormPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(340, 0));
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                "Issue / Return",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                HEADER_BG
        ));

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setOpaque(false);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));

        issueItemIdField = createTextField();
        issueMemberIdField = createTextField();
        returnItemIdField = createTextField();

        fieldsPanel.add(createField("Issue - Item ID", issueItemIdField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("Issue - Member ID", issueMemberIdField));
        fieldsPanel.add(Box.createVerticalStrut(14));
        fieldsPanel.add(createField("Return - Item ID", returnItemIdField));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 16, 16, 16));

        JButton issueButton = createButton("Issue", new Color(40, 140, 80));
        JButton returnButton = createButton("Return", new Color(44, 102, 191));

        issueButton.addActionListener(e -> issueItem());
        returnButton.addActionListener(e -> returnItem());

        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);

        card.add(fieldsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createListPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 14));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                "Transaction History",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14),
                HEADER_BG
        ));

        JPanel inner = new JPanel(new BorderLayout(0, 14));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(10, 16, 16, 16));

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

        card.add(inner, BorderLayout.CENTER);

        return card;
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(new Color(70, 80, 100));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setPreferredSize(new Dimension(0, 36));
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(FIELD_BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        return field;
    }

    private JButton createButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void issueItem() {
        try {
            service.issueItem(issueItemIdField.getText(), issueMemberIdField.getText());
            JOptionPane.showMessageDialog(this, "Item issued successfully!");
            issueItemIdField.setText("");
            issueMemberIdField.setText("");
            loadTransactions();
        } catch (NotFoundException | NotAvailableException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void returnItem() {
        try {
            service.returnItem(returnItemIdField.getText());
            JOptionPane.showMessageDialog(this, "Item returned successfully!");
            returnItemIdField.setText("");
            loadTransactions();
        } catch (NotFoundException | NotAvailableException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
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