package gui;

import service.LibraryService;
import model.Book;
import model.Item;
import exception.DuplicateException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookPanel extends JPanel {
    private LibraryService service;
    private DefaultTableModel tableModel;
    private JTextField idField, titleField, authorField, isbnField;

    public BookPanel(LibraryService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Book"));

        idField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        isbnField = new JTextField();

        formPanel.add(new JLabel("Book ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);

        JButton addButton = new JButton("Add Book");
        formPanel.add(addButton);

        add(formPanel, BorderLayout.NORTH);

        // Table Panel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "ISBN", "Status"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                Book book = new Book(idField.getText(), titleField.getText(), authorField.getText(), isbnField.getText());
                service.addItem(book);
                refreshTable();
                clearFields();
            } catch (DuplicateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : service.getAllItems()) {
            if (item instanceof Book) {
                Book b = (Book) item;
                tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.isAvailable() ? "Available" : "Borrowed"});
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
    }
}