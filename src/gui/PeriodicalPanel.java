package gui;

import service.LibraryService;
import model.Periodical;
import model.Item;
import exception.DuplicateException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PeriodicalPanel extends JPanel {
    private LibraryService service;
    private DefaultTableModel tableModel;
    private JTextField idField, titleField, issueField, categoryField;

    public PeriodicalPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add Periodical / Magazine"));

        idField = new JTextField();
        titleField = new JTextField();
        issueField = new JTextField();
        categoryField = new JTextField();

        formPanel.add(new JLabel("Periodical ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Issue No:"));
        formPanel.add(issueField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);

        JButton addButton = new JButton("Add Periodical");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Issue No", "Category", "Status"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                Periodical p = new Periodical(idField.getText(), titleField.getText(), issueField.getText(), categoryField.getText());
                service.addItem(p);
                refreshTable();
                idField.setText(""); titleField.setText(""); issueField.setText(""); categoryField.setText("");
            } catch (DuplicateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : service.getAllItems()) {
            if (item instanceof Periodical) {
                Periodical p = (Periodical) item;
                tableModel.addRow(new Object[]{p.getId(), p.getTitle(), p.getIssueNumber(), p.getCategory(), p.isAvailable() ? "Available" : "Borrowed"});
            }
        }
    }
}